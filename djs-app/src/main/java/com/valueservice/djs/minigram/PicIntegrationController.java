package com.valueservice.djs.minigram;

import com.valueservice.djs.db.utils.MiniUtils;
import com.valueservice.djs.params.InviteImageDO;
import com.valueservice.djs.result.BaseResult;
import com.valueservice.djs.service.mini.PicIntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/minigram/pic")
public class PicIntegrationController {

    @Resource
    private PicIntegrationService picIntegrationService;

    @RequestMapping("/invite/{userId}")
    @ResponseBody
    public BaseResult inviteImage(@PathVariable Integer userId){
        String picUrl = picIntegrationService.inviteImg(userId);
        BaseResult result = new BaseResult();
        if(picUrl != null){
            result.setResult(true);
            result.setMessage(picUrl);
        }
        return result;
    }

    @RequestMapping("/achive/{userId}")
    @ResponseBody
    public BaseResult achiveImage(@PathVariable Integer userId){
        String picUrl = picIntegrationService.achivementImg(userId);
        BaseResult result = new BaseResult();
        if(picUrl != null){
            result.setResult(true);
            result.setMessage(picUrl);
        }
        return result;
    }

    @RequestMapping("/firstclick/{userId}")
    @ResponseBody
    public BaseResult firstclick(@PathVariable Integer userId){
        String picUrl = picIntegrationService.firstClickImg(userId);
        BaseResult result = new BaseResult();
        if(picUrl != null){
            result.setResult(true);
            result.setMessage(picUrl);
        }
        return result;
    }

    @RequestMapping("/test")
    @ResponseBody
    public BaseResult test(){

        BaseResult result = new BaseResult();

        MiniUtils.getAccessToken();
        return result;
    }

}
