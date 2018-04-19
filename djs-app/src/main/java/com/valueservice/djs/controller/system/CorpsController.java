package com.valueservice.djs.controller.system;

import com.github.pagehelper.PageInfo;
import com.valueservice.djs.controller.BaseController;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.service.mini.MiniCorpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weiying on 2018/4/11.
 * 战队管理
 */
@Controller
@RequestMapping("/system/corps")
public class CorpsController extends BaseController {
    @Resource
    private MiniCorpService miniCorpService;


    @RequestMapping("/list")
    public String list(ModelMap modelMap,String corpsName,String pageIndex){
        PageInfo<MiniCorps> page = null;
        try{
            page = miniCorpService.selectCorpsList(corpsName,pageIndex,null);
        }catch(Exception e){
            e.printStackTrace();
        }
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("userName",corpsName);
        return "system/corps/list";
    }

    @RequestMapping("/updateCorps")
    @ResponseBody
    public Map<String,Object> updateCorps(MiniCorps miniCorps){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",false);
        try{
            boolean result = true;
            if(miniCorps.getCorpsId()==null){
                MiniCorps corps = miniCorpService.selectByCorpsName(miniCorps.getCorpsName());
                if(corps !=null) {
                    resultMap.put("msg", "战队名已存在");
                    return resultMap;
                }
                miniCorps.setPoint(0L);
                result = miniCorpService.saveMiniCorps(miniCorps);
            }else{
                result = miniCorpService.updateMiniCorps(miniCorps);
            }
            resultMap.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("msg", "保存失败");
        }
        return resultMap;
    }

    @RequestMapping("/getCorpsInfo")
    @ResponseBody
    public MiniCorps getCorpsInfo(String corpsName){
        MiniCorps miniCorps = null;
        try{
            miniCorps = miniCorpService.selectByCorpsName(corpsName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return miniCorps;
    }

    @RequestMapping("/deleteCorps")
    @ResponseBody
    public Boolean deleteCorps(String corpsId){
        Boolean result = true;
        try{
            result = miniCorpService.deleteCorps(Long.valueOf(corpsId));
        }catch (Exception e){
            result=false;
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/corpsTop")
    @ResponseBody
    public Boolean corpsTop(String corpsId){
        Boolean result = true;
        try{
            result = miniCorpService.corpsTop(corpsId);
        }catch (Exception e){
            result=false;
            e.printStackTrace();
        }
        return result;
    }
}
