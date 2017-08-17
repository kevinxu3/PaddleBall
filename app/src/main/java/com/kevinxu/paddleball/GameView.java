package com.kevinxu.paddleball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kevin on 8/3/2017.
 */

public class GameView extends View{

    private final int SCREEN_WIDTH, SCREEN_HEIGHT;
    private Paddle left, right;
    private BallManager ballManager;
    private Timer leftTimer, rightTimer;
    private Paint paint;
    private boolean gameOver, requestLeftBall, requestRightBall;

    public GameView(Context context){
        super(context);
        SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels+50;
        left = new Paddle(20, SCREEN_HEIGHT/2, 10, 200, 10, ContextCompat.getColor(context, R.color.paddle));
        right = new Paddle(SCREEN_WIDTH-30, SCREEN_HEIGHT/2, 10, 200, 10, ContextCompat.getColor(context, R.color.paddle));
        ballManager = new BallManager();
        requestLeftBall = false;
        requestRightBall = false;

        leftTimer = new Timer();
        leftTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                requestLeftBall = true;
            }
        }, 0, 2100);
        rightTimer = new Timer();
        rightTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                requestRightBall = true;
            }
        }, 1000, 2000);

        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(32);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        gameOver = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        left.onDraw(canvas);
        right.onDraw(canvas);
        if(requestLeftBall){
            ballManager.addBall(true, ContextCompat.getColor(getContext(), R.color.ball), 5);
            requestLeftBall = false;
        }
        if(requestRightBall) {
            ballManager.addBall(false, ContextCompat.getColor(getContext(), R.color.ball), 5);
            requestRightBall = false;
        }
        ballManager.onDraw(canvas);
        ballManager.update();
//        Log.i("Size", "Size: " + ballManager.size());
        ballManager.checkCollision(left.getPaddle(), right.getPaddle());
        if(ballManager.getLives() <= 0){
            leftTimer.cancel();
            rightTimer.cancel();
            gameOver = true;
        }
//        Log.i("Size", "Size: " + ballManager.size());
        if(gameOver) canvas.drawText("Lives: 0", SCREEN_WIDTH-230, 50, paint);
        else canvas.drawText("Lives: " + ballManager.getLives(), SCREEN_WIDTH-230, 50, paint);
        canvas.drawText("Score: " + ballManager.getScore(), 210, 50, paint);
        invalidate();
    }

    public void update(boolean left, int y){
        if(left) this.left.update(y);
        if(!left) right.update(y);
    }

}
