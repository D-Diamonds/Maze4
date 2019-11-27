package com.example.maze4;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maze4.R;

public class MazeState {

    private int screenWidth;
    private int screenHeight;
    private View view;
    private Context context;
    private ImageView background;


    public MazeState(View view, Context context) {
        this.view = view;
        this.context = context;
        this.screenWidth = view.findViewById(R.id.mazeView).getWidth();
        this.screenHeight = view.findViewById(R.id.mazeView).getHeight();
        this.background = view.findViewById(R.id.imageView);
        this.background.setImageResource(R.drawable.maze1);
        this.background.getLayoutParams().width = this.screenWidth;
        this.background.getLayoutParams().height = this.screenWidth;
        System.out.println("DIMENSIONS: " + this.screenWidth + " * " + this.screenHeight);
        reset();

    }

    public void reset() {
    }



    public void update(float dt) {

    }


    public void setDirection(String direction) {
    }

    public void draw(Canvas canvas, Paint paint) {
    }
}
