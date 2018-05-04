package com.valueservice.djs.db.utils;

import com.valueservice.djs.db.bean.MiniImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PicProcess {

    public static int color_range = 210;

    /**
     * 导入本地图片到缓冲区
     */
    public static BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public static BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static BufferedImage modifyImagetogeter(BufferedImage mainPic, BufferedImage uPic) {
        Graphics2D g = null;
        try {
            g = mainPic.createGraphics();
            g.drawImage(uPic, 163, 394, 440, 616, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return mainPic;
    }

    public static BufferedImage ImageTogeterAny(BufferedImage mainPic, MiniImage ... img ){
        Graphics2D g = mainPic.createGraphics();
        for(MiniImage i : img){
            if(i.getImageType() == 0){
                g.drawImage(i.getImage(), i.getX(), i.getY(), i.getWidth(), i.getHeight(), null);
            }else if(i.getImageType() == 1){
                g.setColor(i.getColor()); //根据图片的背景设置水印颜色
                g.setFont(i.getFont());
                FontMetrics fm = g.getFontMetrics(i.getFont());
                int textWidth = fm.stringWidth(i.getContent());
                int widthX = i.getX()-textWidth/2;
                g.drawString(i.getContent(),widthX, i.getY());
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 计算文字长度，计算居中的x点坐标

            }
        }
        g.dispose();
        return mainPic;
    }

    /**
     * 图片截圆形
     * @param image
     * @return
     */
    public static BufferedImage ellipseImage(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int max = (width > height)?width:height;
        boolean widthMax = (width > height)?true:false;
        int getDiameter = (width < height)?width:height;
        int offset = (max - getDiameter)/2;

        //按照要求缩放图片
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image, 0, 0,getDiameter, getDiameter, null);
        image = tag;
        //将图片裁减成正方形
        image = image.getSubimage(widthMax?offset:0, widthMax?0:offset,getDiameter, getDiameter);

        //生成最终的图片
        boolean rs = false;
        Graphics2D g2 = null;
        try {
            tag = new BufferedImage(getDiameter, getDiameter, BufferedImage.TYPE_INT_ARGB);
            g2 = tag.createGraphics();
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(0, 0, getDiameter, getDiameter, getDiameter,getDiameter));
            g2.setComposite(AlphaComposite.SrcAtop);
            g2.drawImage(image, 0, 0, null);
        }finally{
            if(g2 != null){
                g2.dispose();
            }
        }

        return tag;
    }

    private static boolean colorInRange(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        if (red >= color_range && green >= color_range && blue >= color_range)
            return true;
        return false;
    }

    /**
     * 根据传入的主图片及需要附加的图片，将他们整合成一张图片后存储成png图片
     * @param pngName
     * @param mainPic
     * @param img
     */
    public static void writePngImg(String pngName,BufferedImage mainPic,MiniImage ... img){

        BufferedImage resultImg = ImageTogeterAny(mainPic,img);
        try {
            File outputfile = new File(pngName);
            ImageIO.write(resultImg, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        /**
         * 二维位置 x:303 y:1124  Height :150 width:150
         */
        /*BufferedImage mainPic = loadImageLocal("/Users/maowankui/Documents/testImg/mainPic.jpg");
        BufferedImage uPic = loadImageLocal("/Users/maowankui/Documents/testImg/uPic.png");
        BufferedImage res= modifyImagetogeter(mainPic,uPic);

        writeImageLocal("/Users/maowankui/Documents/testImg/result.jpg",res);*/

        /**
         * 中间海报位置 x:163, y:394, width:440, height:616
         */
        /*BufferedImage mainPic = loadImageLocal("/Users/maowankui/Documents/testImg/mainPic.jpg");
        BufferedImage uPic = loadImageLocal("/Users/maowankui/Documents/testImg/post.png");
        BufferedImage res= modifyImagetogeter(mainPic,uPic);
        writeImageLocal("/Users/maowankui/Documents/testImg/result1.jpg",res);*/

        /*BufferedImage mainPic = loadImageLocal("/Users/maowankui/Documents/testImg/post_main.png");
        BufferedImage mini1 = loadImageLocal("/Users/maowankui/Documents/testImg/post.png");
        BufferedImage mini2 = loadImageLocal("/Users/maowankui/Documents/testImg/uPic.png");
        //MiniImage post = new MiniImage(163, 394, 440, 616);
        MiniImage post = new MiniImage(160, 365, 430, 600);
        post.setImage(mini1);
        MiniImage qrcode = new MiniImage(500, 1020, 105, 105);
        qrcode.setImage(mini2);
        Long time = System.currentTimeMillis();
        MiniImage image = new MiniImage(210,1070,0,0);
        image.setImageType(1);
        String teamName = "呆地在地夺在";
        String content = "我在"+teamName+"战队等你一起科学减重！";
        image.setX(250);
        image.setFont(new Font("苹方黑体", Font.PLAIN, 24));
        image.setContent(content);
        //image.setContent("打败肉肉，你不是一个人在战斗！");
        String pngName = "/Users/maowankui/Documents/" + time + ".png";
        writePngImg(pngName,mainPic,post,qrcode,image);*/
       /* BufferedImage achive_back = loadImageLocal("/Users/maowankui/Desktop/minifile/achive_back.png");

        BufferedImage mainPic = loadImageLocal("/Users/maowankui/Documents/WechatIMG1.jpeg");
        BufferedImage circlepic = ellipseImage(mainPic);
        //头像位置
        MiniImage achive_circle_image = new MiniImage(305,138,140,140);
        achive_circle_image.setImage(circlepic);
        BufferedImage mini2 = loadImageLocal("/Users/maowankui/Desktop/minifile/upic.png");
        //二维码位置
        MiniImage qrcode = new MiniImage(310, 870, 130, 130);
        qrcode.setImage(mini2);
        //签到天数
        MiniImage image = new MiniImage(210,1070,0,0);
        image.setImageType(1);
        String content = "0";
        image.setX(235);
        image.setY(485);
        image.setContent(content);
        image.setFont(new Font("宋体", Font.BOLD, 38));
        image.setColor(new Color(202,0,191,255));
        //签到天数
        MiniImage rank = new MiniImage(210,1070,0,0);
        rank.setImageType(1);
        String rankContent = "第 445 名";
        rank.setX(500);
        rank.setY(485);
        rank.setContent(rankContent);
        rank.setFont(new Font("宋体", Font.BOLD, 38));
        rank.setColor(new Color(202,0,191,255));

        //签到天数
        MiniImage lanch = new MiniImage(210,1070,0,0);
        lanch.setImageType(1);
        String lanchContent = "3456";
        lanch.setX(300);
        lanch.setY(742);
        lanch.setContent(lanchContent);
        lanch.setFont(new Font("宋体", Font.BOLD, 32));
        lanch.setColor(new Color(202,0,191,255));

        Long time = System.currentTimeMillis();
        String pngName = "/Users/maowankui/Documents/" + time + ".png";
        writePngImg(pngName,achive_back,achive_circle_image,qrcode,image,rank,lanch);*/

        BufferedImage achive_back = loadImageLocal("/Users/maowankui/Desktop/minifile/first_click.png");

        BufferedImage post_pic = loadImageLocal("/Users/maowankui/Desktop/minifile/invite_post_1.png");
        //头像位置
        MiniImage post_mini = new MiniImage(100,240,450,630);
        post_mini.setImage(post_pic);
        BufferedImage mini2 = loadImageLocal("/Users/maowankui/Desktop/minifile/upic.png");
        //二维码位置
        MiniImage qrcode = new MiniImage(270, 935, 112, 112);
        qrcode.setImage(mini2);

        Long time = System.currentTimeMillis();
        String pngName = "/Users/maowankui/Documents/" + time + ".png";
        writePngImg(pngName,achive_back,post_mini,qrcode);

    }

}
