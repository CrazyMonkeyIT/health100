package com.valueservice.djs.utils;

import com.valueservice.djs.db.bean.MiniImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicProcess {

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
            g.drawImage(i.getImage(), i.getX(), i.getY(), i.getWidth(), i.getHeight(), null);
        }
        g.dispose();
        return mainPic;
    }

    public static void writeImg(BufferedImage mainPic,MiniImage ... img){
        Long time = System.currentTimeMillis();
        BufferedImage resultImg = ImageTogeterAny(mainPic,img);
        try {
            File outputfile = new File("/Users/maowankui/Documents/testImg/" + time+".png");
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

        BufferedImage mainPic = loadImageLocal("/Users/maowankui/Documents/testImg/mainPic.jpg");
        BufferedImage mini1 = loadImageLocal("/Users/maowankui/Documents/testImg/post.png");
        BufferedImage mini2 = loadImageLocal("/Users/maowankui/Documents/testImg/uPic.png");
        MiniImage post = new MiniImage(163, 394, 440, 616);
        post.setImage(mini1);
        MiniImage qrcode = new MiniImage(303, 1124, 150, 150);
        qrcode.setImage(mini2);
        writeImg(mainPic,post,qrcode);



    }

}
