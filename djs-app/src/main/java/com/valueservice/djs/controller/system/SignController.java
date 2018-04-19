package com.valueservice.djs.controller.system;

import com.github.pagehelper.PageInfo;
import com.valueservice.djs.controller.BaseController;
import com.valueservice.djs.db.entity.mini.MiniSignWasteBook;
import com.valueservice.djs.service.mini.MiniSignService;
import com.valueservice.djs.service.mini.MiniUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by weiying on 2018/4/13.
 */
@Controller
@RequestMapping("/system/miniSign")
public class SignController extends BaseController{
    @Resource
    private MiniSignService miniSignService;

    @RequestMapping("/list")
    public String list(ModelMap modelMap, String pageIndex){
        PageInfo<MiniSignWasteBook> page = null;
        try{
            page = miniSignService.selectNotCheckWasteBook(pageIndex,null);
        }catch(Exception e){
            e.printStackTrace();
        }
        modelMap.addAttribute("page",page);
        return "system/miniSign/list";
    }

    @RequestMapping("/checkImage")
    public @ResponseBody Boolean checkImage(String id){
        Boolean result = true;
        try {
            miniSignService.checkImage(id);
        }catch (Exception e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
}
