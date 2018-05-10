package com.valueservice.djs.service.mini;

import com.valueservice.djs.db.bean.MiniImage;
import com.valueservice.djs.db.dao.mini.MiniCorpsMapper;
import com.valueservice.djs.db.dao.mini.MiniSignMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.dao.mini.PostPicItemMapper;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniUser;
import com.valueservice.djs.db.entity.mini.PostPicItem;
import com.valueservice.djs.db.utils.MiniUtils;
import com.valueservice.djs.db.utils.PicProcess;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class PicIntegrationService {

    private static Logger logger = LoggerFactory.getLogger(PicIntegrationService.class);

    @Resource
    private MiniUserDOMapper miniUserDOMapper;

    @Resource
    private MiniCorpsMapper miniCorpsMapper;

    @Resource
    private MiniSignMapper miniSignMapper;

    @Resource
    PostPicItemMapper postPicItemMapper;

    @Value("${file.path}")
    private String filePath;

    @Value("${context.path}")
    private String contextPath;

    @Value("${file.static.url}")
    private String staticFileUrl;

    @Value("${invite.post.pics}")
    private String invitePostPics ;

    /**
     * 获取邀请卡图片
     * @param userId
     * @return
     */
    public String inviteImg(Integer userId){

        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        //模板文件
        String inviteTmpPic = filePath + "/invite_main.png";
        String inviteQrcode = getInviteQrcode(miniUser);
        BufferedImage inviteTmpBuff = PicProcess.loadImageLocal(inviteTmpPic);
        Long time = System.currentTimeMillis();

        BufferedImage postBuff = queryPostPic();
        if(postBuff == null || inviteQrcode == null){
            return null;
        }

        BufferedImage inviteQrcodeBuff = PicProcess.loadImageLocal(inviteQrcode);
        MiniImage post = new MiniImage(160, 365, 430, 600);//163, 394, 440, 616
        post.setImage(postBuff);
        MiniImage qrcode = new MiniImage(500, 1020, 105, 105);//303 - 1124 - 150 - 150
        qrcode.setImage(inviteQrcodeBuff);
        PicProcess.writePngImg(filePath + "/" + time + ".png",inviteTmpBuff,post,qrcode,getFontContent(miniUser));
        return contextPath + "minifile/" + time + ".png";
    }

    /**
     * 获取成就卡图片
     * @param userId
     * @return
     */
    public String achivementImg(Integer userId){
        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        //模板文件
        String achiveBackPic = filePath + "/achive_back.png";
        BufferedImage achiveBackBuff = PicProcess.loadImageLocal(achiveBackPic);
        //二维码位置
        String inviteQrcode = getInviteQrcode(miniUser);
        BufferedImage inviteTmpBuff = PicProcess.loadImageLocal(inviteQrcode);
        MiniImage qrcode = new MiniImage(310, 870, 130, 130);
        qrcode.setImage(inviteTmpBuff);

        //头像位置
        BufferedImage circle = PicProcess.loadImageUrl(miniUser.getAvatarUrl());
        BufferedImage circlepic = PicProcess.ellipseImage(circle);
        MiniImage achive_circle_image = new MiniImage(305,138,140,140);
        achive_circle_image.setImage(circlepic);

        //签到天数
        MiniImage singDaysCount = getSignDays(miniUser);

        //全国排名
        MiniImage rank = getUserRank(miniUser);

        //捐献午餐数
        MiniImage lunch = getLunch(miniUser);

        Long time = System.currentTimeMillis();
        PicProcess.writePngImg(filePath + "/" + time + ".png",achiveBackBuff,qrcode,
                achive_circle_image,singDaysCount,rank,lunch);
        return contextPath + "minifile/" + time + ".png";
    }


    /**
     * 获取首次打开时要弹出的图片
     * @param userId
     * @return
     */
    public String firstClickImg(Integer userId){
        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        //模板文件
        String firstClickBackPic = filePath + "/first_click.png";
        BufferedImage firstClickBackBuff = PicProcess.loadImageLocal(firstClickBackPic);
        BufferedImage postBuff = queryPostPic();
        String inviteQrcode = getInviteQrcode(miniUser);
        if(postBuff == null || inviteQrcode == null){
            return null;
        }
        BufferedImage inviteQrcodeBuff = PicProcess.loadImageLocal(inviteQrcode);
        MiniImage post = new MiniImage(108,266,531,741);
        post.setImage(postBuff);
        MiniImage qrcode = new MiniImage(310, 1085, 130, 130);//303 - 1124 - 150 - 150
        qrcode.setImage(inviteQrcodeBuff);
        Long time = System.currentTimeMillis();
        PicProcess.writePngImg(filePath + "/" + time + ".png",firstClickBackBuff,post,qrcode);
        String first_click_img_url = contextPath + "minifile/" + time + ".png";
        miniUser.setFirstSignPost(first_click_img_url);
        miniUserDOMapper.updateByPrimaryKeySelective(miniUser);
        return first_click_img_url;
    }


    private MiniImage getLunch(MiniUser miniUser){
        MiniImage lunch = new MiniImage(210,1070,0,0);
        lunch.setImageType(1);
        String lanchContent = miniUser.getPoint()/200 + "";
        lunch.setX(300);
        lunch.setY(742);
        lunch.setContent(lanchContent);
        lunch.setFont(new Font(".PingFang SC", Font.BOLD, 32));
        lunch.setColor(new Color(202,0,191,255));
        return lunch;
    }

    /**
     * 获取全国排名
     * @param miniUser
     * @return
     */
    private MiniImage getUserRank(MiniUser miniUser){
        Integer rankInt = miniUserDOMapper.selectRank(miniUser.getId());
        String rankContent = "";
        if(rankInt > 9999){
            rankContent = rankInt/10000 + "W+";
        }else if(rankInt > 999999){
            rankContent = rankInt/1000000 + "百万+";
        }else {
            rankContent = rankInt +"";
        }

        MiniImage rank = new MiniImage(210,1070,0,0);
        rank.setImageType(1);
        rank.setX(500);
        rank.setY(485);
        rank.setContent(rankContent);
        rank.setFont(new Font(".PingFang SC", Font.BOLD, 38));
        rank.setColor(new Color(202,0,191,255));
        return rank;
    }

    /**
     * 获取连续签到天数的MiniImage
     * @param miniUser
     * @return
     */
    private MiniImage getSignDays(MiniUser miniUser){
        MiniSign miniSign = miniSignMapper.selectByMiniUserId(miniUser.getId());
        MiniImage signDaysCount = new MiniImage(210,1070,0,0);
        signDaysCount.setImageType(1);
        Integer countDays = (miniSign == null?0:miniSign.getCountDays());
        String content =  countDays + "天";
        signDaysCount.setX(235);
        signDaysCount.setY(485);
        signDaysCount.setContent(content);
        signDaysCount.setFont(new Font(".PingFang SC", Font.BOLD, 38));
        signDaysCount.setColor(new Color(202,0,191,255));
        return signDaysCount;
    }


    /**
     * 获取邀请卡的文字水印
     * @param miniUser
     * @return
     */
    private MiniImage getFontContent(MiniUser miniUser){
        MiniImage image = new MiniImage(210,1070,0,0);
        image.setImageType(1);
        image.setFont(new Font(".PingFang SC", Font.PLAIN, 24));
        image.setX(250);//380
        if(miniUser.getCorpsId() == null){
            image.setContent("打败肉肉，你不是一个人在战斗！");
        }else{
            MiniCorps miniCorps = miniCorpsMapper.selectByPrimaryKey(miniUser.getCorpsId());
            image.setContent("我在"+miniCorps.getCorpsName()+"战队等你一起科学减重！");

        }

        logger.info("邀请文字内容：" + image.getContent());

        return image;
    }

    /**
     * 获取个人邀请二维码的远程地址
     * @param miniUser
     * @return
     */
    private String getInviteQrcode(MiniUser miniUser){
        if(StringUtils.isNotEmpty(miniUser.getQrcodeUrl())){
            return miniUser.getQrcodeUrl();
        }else{
            Long time = System.currentTimeMillis();

            String fileName = filePath + time + ".png";
            boolean result = MiniUtils.getSceneQrcodeLocal(miniUser.getId()+"","pages/index/index",430,fileName);
            if(result){
                miniUser.setQrcodeUrl(fileName);
                miniUserDOMapper.updateByPrimaryKeySelective(miniUser);
                return fileName;
            }else {
                return null;
            }
        }
    }

    /**
     * 从数据库中随机取一个海报图片
     * @return
     */
    private BufferedImage queryPostPic(){
        String[] items = invitePostPics.split(",");
        if(items.length > 0){
            return PicProcess.loadImageLocal(getRadom(items));
        }else {
            return null;
        }
    }

    /**
     * 从海报数组中随机取一个图片
     * @param items
     * @return
     */
    private String getRadom(String[] items){
        int index = (int) (Math.random() * items.length);
        return items[index];
    }
}
