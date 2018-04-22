package com.valueservice.djs.service.mini;

import com.valueservice.djs.db.bean.MiniImage;
import com.valueservice.djs.db.dao.mini.PostPicItemMapper;
import com.valueservice.djs.db.entity.mini.PostPicItem;
import com.valueservice.djs.db.utils.PicProcess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class PicIntegrationService {

    @Resource
    PostPicItemMapper postPicItemMapper;

    @Value("file.path")
    private String filePath;

    @Value("context.path")
    private String contextPath;

    @Value("file.static.url")
    private String staticFileUrl;

    public String inviteImg(Integer userId){
        //模板文件
        String inviteTmpPic = filePath + "/inviteTmp.jpg";
        String inviteQrcode = getInviteQrcode(userId);
        BufferedImage inviteTmpBuff = PicProcess.loadImageLocal(inviteTmpPic);
        Long time = System.currentTimeMillis();
        PostPicItem postItem = queryPostPic();
        if(postItem == null){
            return null;
        }
        BufferedImage postBuff = PicProcess.loadImageLocal(postItem.getLocalUri());
        BufferedImage inviteQrcodeBuff = PicProcess.loadImageLocal(inviteQrcode);
        MiniImage post = new MiniImage(163, 394, 440, 616);
        post.setImage(postBuff);
        MiniImage qrcode = new MiniImage(303, 1124, 150, 150);
        qrcode.setImage(inviteQrcodeBuff);
        PicProcess.writePngImg(filePath + "/" + time + ".png",inviteTmpBuff,post,qrcode);
        String imgUrl = contextPath + "/" + time + ".png";
        return staticFileUrl + time + ".png";
    }

    /**
     * 获取个人邀请二维码
     * @param userId
     * @return
     */
    private String getInviteQrcode(Integer userId){
        return null;
    }

    /**
     * 从数据库中随机取一个海报图片
     * @return
     */
    private PostPicItem queryPostPic(){
        List<PostPicItem> items = postPicItemMapper.selectByType("invite");
        if(items.size() > 0){
            return getRadom(items);
        }else {
            return null;
        }
    }

    /**
     * 从海报数组中随机取一个图片
     * @param items
     * @return
     */
    private PostPicItem getRadom(List<PostPicItem> items){
        int index = (int) (Math.random() * items.size());
        return items.get(index);
    }
}
