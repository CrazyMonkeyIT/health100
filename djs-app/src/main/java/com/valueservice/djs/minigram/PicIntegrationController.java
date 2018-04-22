package com.valueservice.djs.minigram;

import com.valueservice.djs.params.InviteImageDO;
import com.valueservice.djs.result.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mini/pic")
public class PicIntegrationController {

    public BaseResult inviteImage(@RequestBody InviteImageDO inviteImage){


        return null;
    }

}
