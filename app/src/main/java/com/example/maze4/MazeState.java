package com.example.maze4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MazeState {

    private int screenWidth;
    private int screenHeight;
    private View view;
    private Context context;
    private Bitmap maze;
    private Ball player;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    public MazeState(View view, Context context) {
        this.view = view;
        this.context = context;
        this.screenWidth = view.findViewById(R.id.mazeView).getWidth();
        this.screenHeight = view.findViewById(R.id.mazeView).getHeight();
        this.maze = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.maze1);
        this.maze = Bitmap.createScaledBitmap(maze, this.screenWidth, this.screenHeight, false);
        this.player = new Ball(this.screenWidth, this.screenHeight);

//        this.view.findViewById(R.id.mazeView).setOnTouchListener(new OnSwipeListener(this.context){
//            public void onSwipeRight() {
//                Ball.setDirection("right");
//            }
//
//            public void onSwipeLeft() {
//                Ball.setDirection("left");
//            }
//
//            public void onSwipeTop() {
//                Ball.setDirection("up");
//            }
//
//            public void onSwipeBottom() {
//                Ball.setDirection("down");
//            }
//        });


        // Initializes ball to wherever the green box is
        boolean foundStart = false;
        float startX = 0;
        float endX = 0;
        float startY = 0;
        float endY = 0;
        for (int i = 0; i < this.maze.getWidth(); i++) {
            for (int j = 0; j < this.maze.getHeight(); j++) {
                int pixel = this.maze.getPixel(i, j);
                if (Color.red(pixel) == 0 && Color.green(pixel) == 255 && Color.blue(pixel) == 0) {
                    if (!foundStart) {
                        startX = i;
                        startY = j;
                    }
                    endX = i;
                    endY = j;
                    foundStart = true;
                }
            }
        }
        System.out.println();
        this.player.setPosition((endX + startX) / 2, (endY + startY) / 2);


        System.out.println("DIMENSIONS: " + this.screenWidth + " * " + this.screenHeight);
        reset();

    }

    public void reset() {
    }

    public Ball getPlayer() {
        return this.player;
    }

    public void win() {
        System.out.println("WIN!");
    }

    public void update(float dt) {
        this.player.update(dt);

        // collision detection
        if (this.player.getDirection().equals("right") ) {
            int pixel = this.maze.getPixel((int) (this.player.getX() + this.player.getRadius()), (int) this.player.getY());
            // with wall
            if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                this.player.setPosition(this.player.getX() - this.player.getRadius(), this.player.getY());
            // with goal
            if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                win();

        }
        else if (this.player.getDirection().equals("left") ) {
            int pixel = this.maze.getPixel((int) (this.player.getX() - this.player.getRadius()), (int) this.player.getY());
            // with wall
            if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                this.player.setPosition(this.player.getX() + this.player.getRadius(), this.player.getY());
            // with goal
            if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                win();
        }
        else if (this.player.getDirection().equals("up") ) {
            int pixel = this.maze.getPixel((int) this.player.getX(), (int) (this.player.getY()+ this.player.getRadius()));
            // with wall
            if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                this.player.setPosition(this.player.getX(), this.player.getY() - this.player.getRadius());
            // with goal
            if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                win();
        }
        else if (this.player.getDirection().equals("down") ) {
            int pixel = this.maze.getPixel((int) this.player.getX(), (int) (this.player.getY() - + this.player.getRadius()));
            // with wall
            if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                this.player.setPosition(this.player.getX(), this.player.getY() + this.player.getRadius());
            // with goal
            if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                win();
        }

    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.maze, 0, 0, paint);
        this.player.draw(canvas, paint);
    }
}
