package com.kevinxu.paddleball;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kevin on 8/3/2017.
 */

public class Ball {

    private Rect ball;
    private int x, y, xv, yv, length;
    private Paint paint;
    private int SCREEN_HEIGHT, SCREEN_WIDTH;
    private boolean onScreen, scored;

    public Ball(int x, int y, int angle, int length, int speed, int color){
        this.x = x;
        this.y = y;
        xv = (int)(Math.cos(Math.toRadians(angle))*speed);
        yv = -(int)(Math.sin(Math.toRadians(angle))*speed);
        this.length = length;
        ball = new Rect(this.x-(length/2), this.y-(length/2), this.x+(length/2), this.y+length/2);
        paint = new Paint();
        paint.setColor(color);
        SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels+50;
        onScreen = false;
    }

    public void update(){
        x += xv;
        y += yv;
        ball = new Rect(this.x-(length/2), this.y-(length/2), this.x+(length/2), this.y+length/2);
        if(x > 0 && x < SCREEN_WIDTH && y > 0 && y < SCREEN_HEIGHT) onScreen = true;
        else onScreen = false;
    }

    public boolean checkCollision(Rect left, Rect right){
//        if (ball.intersect(left.left, left.top, left.right, left.top+1)
//                || ball.intersect(right.left, right.top, right.right, right.top+1)
//                || ball.intersect(left.left, left.bottom-1, left.right, left.bottom)
//                || ball.intersect(right.left, right.bottom-1, right.right, right.bottom)){
//            bounce(0);
//            bounce(1);
//        }
        if (y + length / 2 >= SCREEN_HEIGHT || y - length / 2 <= 0) bounce(0);
        if (ball.intersect(left) || ball.intersect(right)){
            bounce(1);
            if(!scored) {
                scored = true;
                return true;
            }
            else {
                if(ball.intersect(left)) x = left.right + length/2 + 1;
                if(ball.intersect(right)) x = right.left - length/2 - 1;
            }
        }
        else scored = false;
        return false;
    }

    // 0 -> UP/DOWN
    // 1 -> RIGHT/LEFT
    public void bounce(int direction){
        if(direction == 0) yv *= -1;
        if(direction == 1) xv *= -1;
    }

    public boolean onScreen(){
        return onScreen;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, length/2, paint);
    }
}
