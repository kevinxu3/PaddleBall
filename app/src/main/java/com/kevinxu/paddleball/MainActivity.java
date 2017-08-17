package com.kevinxu.paddleball;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        setContentView(view);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        int pointerCount = event.getPointerCount();
//        Log.i("Main", "Pointer Count: " + pointerCount);
        for(int i = 0; i < pointerCount; i++) {
            if (event.getX(i) > SCREEN_WIDTH / 2) view.update(false, (int) event.getY(i));
            if (event.getX(i) < SCREEN_WIDTH / 2) view.update(true, (int) event.getY(i));
        }
        return false;
    }

}
