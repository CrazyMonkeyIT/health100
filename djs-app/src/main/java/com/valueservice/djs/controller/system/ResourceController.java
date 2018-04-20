package com.valueservice.djs.controller.system;

import com.alibaba.druid.support.json.JSONUtils;
import com.valueservice.djs.controller.BaseController;
import com.valueservice.djs.db.entity.system.ResourcesDO;
import com.valueservice.djs.service.system.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源管理
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourcesService;

	/**
	 * 资源列表
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mv){
		List<ResourcesDO> resources = resourcesService.selectAll();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		//转换为json格式数据，方便zTree树的实现
		for (ResourcesDO res : resources) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", res.getResourceId());
			map.put("pId", res.getParentId());
			map.put("name", res.getResourceName());
			map.put("order", res.getOrderNo());
			if(res.getParentId() == 0){
				map.put("open", "true");
			}
			mapList.add(map);
		}
		mv.addObject("data", JSONUtils.toJSONString(mapList));
		mv.addObject("resourceList",resources);
		mv.setViewName("system/resource/list");
		return mv;
	}

	/**
	 * 获取一条资源信息
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value="/getResourceInfo",method= RequestMethod.POST)
	public @ResponseBody
	ResourcesDO preEdit(Long resourceId){
		ResourcesDO resource = null;
		try {
			resource = resourcesService.selectByPrimaryKey(resourceId);
		} catch (Exception e) {
			logger.error("获取资源信息异常",e);
		}
		return resource;
	}

	/**
	 * 删除资源信息
	 * @return
	 */
	@RequestMapping(value="/deleteResource",method= RequestMethod.POST)
	public @ResponseBody
    boolean deleteResource(Long resourceId){
		boolean result = false;
		try {
			result = resourcesService.deleteResource(resourceId);
		} catch (Exception e) {
			logger.error("删除资源异常",e);
		}
		return result;
	}
	/**
	 * 更新资源信息
	 * @return
	 */
	@RequestMapping(value="/editResource",method= RequestMethod.POST)
	public @ResponseBody
    boolean addResource(ResourcesDO resource){
		boolean result = false;
		try {
			result = resourcesService.insertOrUpdate(resource);
		} catch (Exception e) {
			logger.error("添加资源异常",e);
		}
		return result;
	}
	/**
	 * 菜单位置变更
	 * @return
	 */
	@RequestMapping(value="/changeResource",method= RequestMethod.POST)
	@ResponseBody
	public boolean changeResource(ResourcesDO resource,Long upId,Integer upOrder){
		boolean result = false;
		if(resource != null){
			try {
				result = resourcesService.updateResourcePosition(resource, upId, upOrder);
			} catch (Exception e) {
				logger.error("菜单位置变更异常",e);
			}
		}
		return result;
	}
}
