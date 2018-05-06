package com.valueservice.djs.minigram;

import com.valueservice.djs.bean.BaseResult;
import com.valueservice.djs.bean.EncryptUserInfo;
import com.valueservice.djs.db.bean.MiniUserVO;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniSignWasteBook;
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
import java.util.Map;
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
     * 用户信息
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
     * 获取用户战队信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getMiniUserCorps",method = RequestMethod.GET)
    public @ResponseBody BaseResult getMiniUserCorps(Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniUser user = miniUserService.selectByPrimaryKey(userId);
            MiniCorps corps = miniCorpService.getCorps(user.getCorpsId());
            if(!Objects.isNull(corps)){
                result.setResult(true);
                result.setObj(corps);
                result.setMessage("获取用户信息成功！");
            }
        }catch (Exception ex){
            LOGGER.error("获取小程序用户异常",ex);
        }
        return result;
    }

    /**
     * 用户连续打卡天数成就展示
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getMiniUserCountDay",method = RequestMethod.GET)
    public @ResponseBody BaseResult getMiniUserCountDay(Integer userId){
        BaseResult result = new BaseResult();
        try{

        }catch (Exception e){
            LOGGER.error("用户连续打卡成就展示失败",e);
        }
        return result;
    }

    @RequestMapping(value = "/getMiniUserAchieve",method = RequestMethod.GET)
    public @ResponseBody BaseResult getMiniUserAchieve(Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniUserVO userVO = miniUserService.getMiniUserByOpenId(userId);
            if(!Objects.isNull(userVO)){
                result.setResult(true);
                result.setObj(userVO);
                result.setMessage("获取成就信息成功！");
            }
        }catch (Exception ex){
            LOGGER.error("获取成就异常",ex);
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

    @RequestMapping(value = "/checkTodaySign",method = RequestMethod.GET)
    public @ResponseBody BaseResult checkTodaySign(@RequestParam Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniSign miniSign = miniSignService.checkTodaySign(userId);
            if(miniSign != null) {
                result.setResult(true);
            }
            result.setMessage("获取个人排行成功~~");
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
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
            LOGGER.error("获取战队排行异常==userId:" + userId,e);
        }
        return result;
    }

    /**
     * 获取战队队员排行
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getCropsUserRanking",method = RequestMethod.GET)
    public @ResponseBody BaseResult getCropsUserRanking(Integer userId){
        BaseResult result = new BaseResult();
        try {
            List<MiniUser> corpsPanking = miniUserService.selectCorpsUserPanking(userId);
            result.setResult(true);
            result.setObj(corpsPanking);
            result.setMessage("获取战队排行成功~~");
        }catch (Exception e){
            LOGGER.error("获取战队排行异常==userId:" + userId,e);
        }
        return result;
    }

    /**
     * 获取所有的战队信息
     * @return
     */
    @RequestMapping(value = "/corpsList",method = RequestMethod.GET)
    public @ResponseBody BaseResult getCorpsList(){
        BaseResult result = new BaseResult();
        try {
            List<MiniCorps> corps = miniCorpService.selectAllCorpsList();
            result.setResult(true);
            result.setObj(corps);
            result.setMessage("获取战队排行成功~~");
        }catch (Exception e){
            LOGGER.error("获取战队排行异常",e);
        }
        return result;
    }

    /**
     * 获取手动置顶的战队或排行第一的
     * 修改为获取用户的战队
     * 修改回积分抢占和手动置顶
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getTop1Corps",method = RequestMethod.GET)
    public @ResponseBody BaseResult getTop1Corps(Integer userId){
        BaseResult result = new BaseResult();
        try {
            MiniCorps corps = miniCorpService.selectTop1Corps(userId);
            result.setResult(true);
            result.setObj(corps);
            result.setMessage("获取top1战队成功~~");
        }catch (Exception e){
            LOGGER.error("获取top1战队异常",e);
        }
        return result;
    }
    /**
     * 加入战队
     * @param corpsId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/joinCorps",method = RequestMethod.GET)
    public @ResponseBody BaseResult joinCorps(Long corpsId,Integer userId){
        BaseResult result = new BaseResult();
        try{
            String msg = miniCorpService.joinCorps(corpsId,userId);
            if(msg.equals("success")){
                result.setResult(true);
                result.setMessage("加入战队成功~~");
            }else{
                result.setResult(false);
                result.setMessage(msg);
            }
        }catch (Exception e){
            LOGGER.error("加入战队异常",e);
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
     * 传图打卡
     * @param userId
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/imageSign",method = RequestMethod.GET)
    public @ResponseBody BaseResult imageSign(Integer userId,String filePath){
        BaseResult result = new BaseResult();
        try {
            Map<String,Object> map = miniSignService.imageSign(userId,filePath);
            if(map.get("result").equals("success")){
                result.setResult(true);
                result.setObj(map.get("point"));
            }else{
                result.setResult(false);
            }
            result.setMessage(String.valueOf(map.get("result")));
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
        }
        return result;
    }

    /**
     * 一键打卡
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userSign",method = RequestMethod.GET)
    public @ResponseBody BaseResult userSign(Integer userId){
        BaseResult result = new BaseResult();
        try {
            Map<String,Object> map = miniSignService.userSign(userId);
            if(map.get("result").equals("success")){
                result.setResult(true);
                result.setObj(map.get("point"));
            }else{
                result.setResult(false);
            }
            result.setMessage(String.valueOf(map.get("result")));
        }catch (Exception e){
            LOGGER.error("校验用户打卡状态失败",e);
        }
        return result;
    }

    /**
     * 获取用户打卡图片  审核通过的图片
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getSignImage",method = RequestMethod.GET)
    public @ResponseBody BaseResult getSignImage(Integer userId){
        BaseResult result = new BaseResult();
        try{
            List<MiniSignWasteBook> miniSignWasteBooks = miniUserService.selectUserSignImage(userId);
            if (!Objects.isNull(miniSignWasteBooks)) {
                result.setResult(true);
                result.setObj(miniSignWasteBooks);
                result.setMessage("获取用户打卡图片成功");
            }
        }catch (Exception e){
            LOGGER.error("获取打卡图片异常",e);
        }
        return result;
    }
}
