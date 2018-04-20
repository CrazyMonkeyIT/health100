package com.valueservice.djs.db.bean;

import java.awt.image.BufferedImage;

public class MiniImage {

    BufferedImage image ;//图片缓存

    Integer width;//图片宽度

    Integer height ;//图片高度

    Integer x ;//图片顶点的x轴坐标

    Integer y ;//图片顶点的y轴坐标

    public MiniImage(Integer x,Integer y,Integer width,Integer height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
