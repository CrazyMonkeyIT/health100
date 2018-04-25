package com.valueservice.djs.minigram;

import com.valueservice.djs.bean.BaseResult;
import com.valueservice.djs.bean.EncryptUserInfo;
import com.valueservice.djs.db.bean.MiniUserVO;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniUser;
import com.valueservice.djs.service.mini.MiniCorpService;
import com.valueservice.djs.service.mini.MiniSignService;
import com.valueservice.djs.service.mini.MiniUserService;
import com.valueservice.djs.util.WechatUserEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/minigram")
public class MiniGramController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MiniGramController.class);

    @Resource
    private MiniUserService miniUserService;

    @Resource
    private MiniCorpService miniCorpService;

    @Resource
    private MiniSignService miniSignService;

    @RequestMapping(value = "/getauth", method = RequestMethod.GET)
    public @ResponseBody EncryptUserInfo getUserInfo(String code, String iv, String encryptedData){
        return WechatUserEncrypt.getEncryptUserInfo(code,iv,encryptedData);
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public @ResponseBody BaseResult saveMiniUser(@RequestBody MiniUser miniUserDO){
        BaseResult result = new BaseResult();
        try {
            MiniUser record = miniUserService.saveOrUpdate(miniUserDO);
            if (!Objects.isNull(record)) {
                result.setResult(true);
                result.setObj(record);
                result.setMessage("小程序用户:" + miniUserDO.getOpenId() + "保存成功~~");
            }
        }catch (Exception ex){
            LOGGER.error("保存小程序用户异常",ex);
        }
        return result;
    }

    /**
     * 主页面用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getMiniUser",method = RequestMethod.GET)
    public @ResponseBody BaseResult getMiniUser(Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniUserVO userVO = miniUserService.getMiniUserByOpenId(userId);
            if(!Objects.isNull(userVO)){
                result.setResult(true);
                result.setObj(userVO);
                result.setMessage("获取用户信息成功！");
            }
        }catch (Exception ex){
            LOGGER.error("获取小程序用户异常",ex);
        }
        return result;
    }

    /**
     * 获取战队排行 top20
     * 根据用户的战队  如果用户的战队的战队是特殊排行 获取特殊的排行
     * 用户没有战队或不是特殊排行的战队 获取正常的战队排行
     * @return
     */
    @RequestMapping(value = "/corpsPanking",method = RequestMethod.GET)
    public @ResponseBody BaseResult getCropsRanking(Integer userId){
        BaseResult result = new BaseResult();
        try {
            List<MiniCorps> corpsPanking = miniCorpService.selectCorpsPanking(userId);
            result.setResult(true);
            result.setObj(corpsPanking);
            result.setMessage("获取战队排行成功~~");
        }catch (Exception e){
            LOGGER.error("获取战队排行异常",e);
        }
        return result;
    }

    /**
     * 获取个人排行 top30
     * @return
     */
    @RequestMapping(value = "/usersPanking",method = RequestMethod.GET)
    public @ResponseBody BaseResult getUserRanking(){
        BaseResult result = new BaseResult();
        try {
            List<MiniUser> corpsPanking = miniUserService.selectUserPanking();
            result.setResult(true);
            result.setObj(corpsPanking);
            result.setMessage("获取个人排行成功~~");
        }catch (Exception e){
            LOGGER.error("获取个人排行异常",e);
        }
        return result;
    }

    /**
     * 获取用户打卡类型
     * 第一天 和 第100天 只能传图打卡
     * @param userId
     * @return
     */
    @RequestMapping(value = "/checkSign",method = RequestMethod.GET)
    public @ResponseBody BaseResult checkSign(@RequestParam Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniSign miniSign = miniSignService.checkSign(userId);
            result.setResult(true);
            result.setObj(miniSign);
            result.setMessage("获取个人排行成功~~");
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
        }
        return result;
    }

    /**
     * 传图打卡
     * @param userId
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/imageSign",method = RequestMethod.GET)
    public @ResponseBody BaseResult imageSign(Integer userId,String filePath){
        BaseResult result = new BaseResult();
        try {
            String msg = miniSignService.imageSign(userId,filePath);
            if(msg.equals("success")){
                result.setResult(true);
            }else{
                result.setResult(false);
            }
            result.setMessage(msg);
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
        }
        return result;
    }
    @RequestMapping(value = "/userSign",method = RequestMethod.GET)
    public @ResponseBody BaseResult userSign(Integer userId){
        BaseResult result = new BaseResult();
        try {
            String msg = miniSignService.userSign(userId);
            if(msg.equals("success")){
                result.setResult(true);
            }else{
                result.setResult(false);
            }
            result.setMessage(msg);
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
        }
        return result;
    }
    /**
     * 获取战队成员信息
     * @return
     */
    @RequestMapping(value = "/aaa",method = RequestMethod.GET)
    public @ResponseBody BaseResult getCropsInfo(){
        BaseResult result = new BaseResult();

        return result;
    }

    /**
     * 加入战队
     * 加入成功返回战队信息
     * @param openId
     * @param corpsId
     * @return
     */
    @RequestMapping(value = "/initCorps",method = RequestMethod.GET)
    public @ResponseBody BaseResult joinCorps(String openId,Long corpsId){
        BaseResult result = new BaseResult();
        try {
            Integer res = miniUserService.initCorps(openId,corpsId);
            if(res>0){
                MiniCorps corps = miniCorpService.getCorps(corpsId);
                result.setResult(true);
                result.setObj(corps);
                result.setMessage("加入战队成功~~");
            }
        }catch (Exception e){
            LOGGER.error("打卡异常",e);
        }
        return result;
    }

    /**
     * 打卡 （一键打卡）
     * @param openId
     * @return
     */
    @RequestMapping(value = "/puchClock",method = RequestMethod.GET)
    public @ResponseBody BaseResult puchClock(String openId){
        BaseResult result = new BaseResult();
        /*try{
            MiniUser miniUserDO = miniUserService.puchClock(openId);
            if(miniUserDO!=null){
                result.setResult(true);
                result.setObj(miniUserDO);
                result.setMessage("打卡成功~~");
            }
        }catch (Exception e){
            LOGGER.error("打卡异常",e);
        }*/
        return result;
    }
}
