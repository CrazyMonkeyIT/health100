package com.valueservice.djs.controller.system;

import com.valueservice.djs.bean.BaseResult;
import com.valueservice.djs.db.entity.system.ResourcesDO;
import com.valueservice.djs.db.entity.system.UserInfoDO;
import com.valueservice.djs.service.system.ResourceService;
import com.valueservice.djs.service.system.UserInfoService;
import com.valueservice.djs.util.Des3Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.valueservice.djs.util.IpUtil;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * 登录
 * @author xiaxin
 * @date 2017-10-10
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * 进入登录页
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv){
		//OfficeConvert.docToPdf("C:\\Users\\Administrator\\Desktop\\财经直播小程序需求文档.docx", "C:\\Users\\Administrator\\Desktop/file/logback.pdf");
		mv.addObject("DES_SECRET_KEY", Des3Util.SECRET_KEY);
		mv.setViewName("login");
		return mv;
	}

	/**
	 * 登录
	 * @param req
	 * @param loginname
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
	@ResponseBody
	public BaseResult loginSubmit(HttpServletRequest req,String loginname, String password){
		BaseResult result = new BaseResult(false,"account_error");
		Subject subject;
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(loginname, Des3Util.decode(password));
			subject = SecurityUtils.getSubject();
			subject.login(token);
		} catch (UnknownAccountException uae) {
			logger.error("登录失败：用户名或密码错误");
			return result;
		} catch (IncorrectCredentialsException ice) {
			logger.error("登录失败：密码错误");
			return result;
		} catch (LockedAccountException lae) {
			logger.error("登录失败：账户冻结");
			result.setMessage("account_lock");
			return result;
		} catch (AuthenticationException ae) {
			if("account_invalid".equals(ae.getMessage())){
				result.setMessage("account_invalid");
				return result;
			}
			logger.error("登录失败，账户失效",ae);
			return result;
		} catch(Exception e){
			logger.error("登录失败：",e);
			return result;
		}
		
		UserInfoDO loginUser = userInfoService.selectByLoginName(loginname);
		subject.getSession().setAttribute("currUser",loginUser);
		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		loginUser.setLastLoginTime(updateTime);
        loginUser.setUpdateTime(updateTime);
        loginUser.setLastLoginIp(IpUtil.getRemoteIP(req));
        userInfoService.insertOrUpdate(loginUser);

        //如果当前用户没有父账户，那么就是顶级账户，顶级账户会加载所有资源
		List<ResourcesDO> userResources;
		if(loginUser.getParentId() != null) {
			userResources = resourceService.selectByUserId(loginUser.getUserId());
		}else{
			userResources = resourceService.selectAll();
		}
        userResources = resourcesOper(userResources);
        if(userResources.size() == 0){
        	result.setMessage("no_permission");
        	return result;
        }
        req.getSession().setAttribute("menuList", userResources);
		result.setResult(true);
		result.setMessage("success");
		return result;
	}

	/**
	 * 资源菜单设置
	 * @param userResources
	 * @return
	 */
	private List<ResourcesDO> resourcesOper(List<ResourcesDO> userResources){
		for (ResourcesDO re : userResources) {
			if(re.getParentId() != null && re.getParentId() != 0){
				for (ResourcesDO res : userResources) {
					if(res.getResourceId().equals(re.getParentId())){
						res.setHasChild(true);
					}
				}
			}
		}
		return userResources;
	}
	/**
	 * 更新菜单选中状态
	 */
	@RequestMapping("/updateMenuStatus")
	@ResponseBody
	public Boolean updateMenuStatus(HttpServletRequest request, Integer oneId, Integer twoId, String oneName, String twoName){
		request.getSession().setAttribute("SelectOneLevelId",oneId);
		request.getSession().setAttribute("SelectTwoLevelId",twoId);
		request.getSession().setAttribute("SelectOneLevelName",oneName);
		request.getSession().setAttribute("SelectTwoLevelName",twoName);
		return true;
	}
	/**
	 * 更新客户选中状态
	 */
	@RequestMapping("/updateSelectCust")
	@ResponseBody
	public Boolean updateSelectCust(HttpServletRequest request, Integer custId, String custName, String searchCustName){
		if(custId != null) {
			request.getSession().setAttribute("selectCustId", custId);
			request.getSession().setAttribute("selectCustName", custName);
		}
		if(searchCustName != null){
			request.getSession().setAttribute("searchCustName", searchCustName);
		}
		return true;
	}
}
