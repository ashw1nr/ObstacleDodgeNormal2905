package org.ashcode.obstacledodgenormal2905;

import android.graphics.Paint;

public class MovingRectangle {

    private Paint rectanglePaint;
    private int rectangleX;
    private int rectangleY;
    private int rectangleVelocity;
    private int rectangleWidth ;
    private int rectangleHeight ;


    public void setRectangleProps(Paint rectanglePaint, int rectangleVelocity, int rectangleHeight, int rectangleWidth){
        this.rectanglePaint = rectanglePaint;
        this.rectangleHeight = rectangleHeight;
        this.rectangleWidth = rectangleWidth;
        this.rectangleVelocity = rectangleVelocity;
    }

    public void setRectangleX(int rectangleX) {
        this.rectangleX = rectangleX;
    }

    public void setRectangleY(int rectangleY) {
        this.rectangleY = rectangleY;
    }

    public Paint getRectanglePaint() {
        return rectanglePaint;
    }

    public int getRectangleX() {
        return rectangleX;
    }

    public int getRectangleY() {
        return rectangleY;
    }

    public int getRectangleVelocity() {
        return rectangleVelocity;
    }

    public int getRectangleWidth() {
        return rectangleWidth;
    }

    public int getRectangleHeight() {
        return rectangleHeight;
    }
}
