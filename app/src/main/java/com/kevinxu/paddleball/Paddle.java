package com.kevinxu.paddleball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Kevin on 8/3/2017.
 */

public class Paddle {

    private Rect paddle;
    private int x, y, width, length, maxSpeed;
    private Paint paint;

    public Paddle(int x, int y, int width, int length, int maxSpeed, int color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        paddle = new Rect(this.x-(width/2), this.y-(length/2), this.x+width, this.y+length/2);
        this.maxSpeed = maxSpeed;
        paint = new Paint();
        paint.setColor(color);
    }

    public void update(int y){
//        if(this.y > y) this.y -= maxSpeed;
//        else this.y += maxSpeed;
        paddle = new Rect(x-width/2, y-length/2, x+width, y+length/2);
    }

    public Rect getPaddle(){
        return paddle;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawRect(paddle, paint);
    }
}
