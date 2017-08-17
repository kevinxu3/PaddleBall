package com.kevinxu.paddleball;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Kevin on 8/3/2017.
 */

public class BallManager extends ArrayList<Ball>{

    private int lives;
    private int score;

    public BallManager(){
        lives = 100;
    }

    public void addBall(boolean left, int color, int speed){
        int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels+50;
        int length = 20;
        if(left){
//            Log.i("Angles", "Left Angles: " + (int)(Math.random() * (246 - 195) + 195));
            int angle = (int)((Math.random()*51)+195);
//            int angle = (int)(Math.random() * (246 - 195) + 195);
            add(new Ball(SCREEN_WIDTH*3/4, length/2+1, angle, length, speed, color));
        }
        else {
//            Log.i("Angles", "Right Angles: " + Math.random()*51+195);
            int angle = (int) ((Math.random()*51)+15);
            add(new Ball(SCREEN_WIDTH/4, SCREEN_HEIGHT-length/2-1, angle, length, speed, color));
        }
    }

    public void update(){
        Iterator<Ball> iterator = this.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
//            Log.i("Iterator", "Update Running");
            ball.update();
            if (!ball.onScreen()) {
                lives--;
                iterator.remove();
            }
        }
//        for(Ball ball : this){
//            ball.update();
//            if(!ball.getOnScreen()){
//                lives--;
//                remove(ball);
//            }
//        }
    }

    public void checkCollision(Rect left, Rect right){
        Iterator<Ball> iterator = this.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
//            Log.i("Iterator", "Collision Running");
            if(ball.checkCollision(left, right) && lives > 0) score++;
        }
//        for(Ball ball : this) ball.checkCollision(left, right);
    }

    public int getLives(){
        return lives;
    }

    public int getScore(){ return score; }

    protected void onDraw(Canvas canvas) {
        Iterator<Ball> iterator = this.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            // Log.i("Iterator", "Draw Running");
            ball.onDraw(canvas);
        }
//        for(Ball ball : this) ball.onDraw(canvas);
    }

}
