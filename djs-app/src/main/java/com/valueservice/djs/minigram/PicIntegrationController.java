package com.valueservice.djs.minigram;

import com.valueservice.djs.params.InviteImageDO;
import com.valueservice.djs.result.BaseResult;
import com.valueservice.djs.service.mini.PicIntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/mini/pic")
public class PicIntegrationController {

    @Resource
    private PicIntegrationService picIntegrationService;

    @RequestMapping("/invite/{userId}")
    public BaseResult inviteImage(@PathVariable Integer userId){
        String picUrl = picIntegrationService.inviteImg(userId);

        return null;
    }

}
