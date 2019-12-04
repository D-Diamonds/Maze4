package com.example.maze4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MazeState {

    private int screenWidth;
    private int screenHeight;
    private View view;
    private Context context;
    private Bitmap maze;
    private Ball player;
    private int time;
    private boolean foundGoal;


    public MazeState(View view, Context context, Bitmap maze) {
        this.view = view;
        this.context = context;
        this.screenWidth = view.findViewById(R.id.mazeView).getWidth();
        this.screenHeight = view.findViewById(R.id.mazeView).getHeight();
        this.maze = maze;
        this.player = new Ball(this.screenWidth, this.screenHeight);
        this.time = 0;
        this.foundGoal = false;

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

    public Bitmap getMaze() {
        return this.maze;
    }

    public void win() {
        System.out.println("WIN!");
        this.foundGoal = true;
        updateTime(this.time/ 100, this.foundGoal);
        this.player.setDirection("null");

    }

    public void updateTime(final int currentTime, final boolean foundGoal) {
        ((Activity) this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!foundGoal)
                    ((TextView) ((Activity) context).findViewById(R.id.timer)).setText("Time: " + currentTime);
                else
                    ((TextView) ((Activity) context).findViewById(R.id.timer)).setText("You won in " + currentTime + " seconds!");
            }
        });
    }

    public void update(float dt) {
        this.player.update(dt);
        if (!foundGoal) {
            this.time += dt;
            updateTime(this.time / 100, this.foundGoal);
        }
        //System.out.println(this.time);


        // ensure player stays in view
        if (this.player.getX() < 0) {
            this.player.setPosition(this.player.getRadius() + 2, this.player.getY());
            this.player.setDirection("right");
        }
        else if (this.player.getX() > this.screenWidth) {
            this.player.setPosition(this.screenWidth - this.player.getRadius() - 2, this.player.getY());
            this.player.setDirection("left");
        }
        else if (this.player.getY() < 0) {
            this.player.setPosition(this.player.getX(), this.player.getRadius() + 2);
            this.player.setDirection("down");
        }
        else if (this.player.getY() > this.screenHeight) {
            this.player.setPosition(this.player.getX(), this.screenHeight - this.player.getRadius() - 2);
            this.player.setDirection("up");
        }

        // collision detection
        if (this.player.getX() - this.player.getRadius() > 0 && this.player.getX() + this.player.getRadius() < this.screenWidth && this.player.getY() - this.player.getRadius() > 0 && this.player.getY() + this.player.getRadius() < this.screenHeight) {
            if (this.player.getDirection().equals("right")) {
                int pixel = this.maze.getPixel((int) (this.player.getX() + this.player.getRadius()), (int) this.player.getY());
                // with wall
                if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    this.player.setPosition(this.player.getX() - 2, this.player.getY());
                    this.player.setDirection("left");
                }
                // with goal
                if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                    win();

            } else if (this.player.getDirection().equals("left")) {
                int pixel = this.maze.getPixel((int) (this.player.getX() - this.player.getRadius()), (int) this.player.getY());
                // with wall
                if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    this.player.setPosition(this.player.getX() + 2, this.player.getY());
                    this.player.setDirection("right");
                }
                // with goal
                if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                    win();
            } else if (this.player.getDirection().equals("up")) {
                int pixel = this.maze.getPixel((int) this.player.getX(), (int) (this.player.getY() - this.player.getRadius()));
                // with wall
                if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    this.player.setPosition(this.player.getX(), this.player.getY() + 2);
                    this.player.setDirection("down");
                }
                // with goal
                if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                    win();
            } else if (this.player.getDirection().equals("down")) {
                int pixel = this.maze.getPixel((int) this.player.getX(), (int) (this.player.getY() + this.player.getRadius()));
                // with wall
                if (Color.red(pixel) == 0 && Color.green(pixel) == 0 && Color.blue(pixel) == 0) {
                    this.player.setPosition(this.player.getX(), this.player.getY() - 2);
                    this.player.setDirection("up");
                }
                // with goal
                if (Color.red(pixel) == 255 && Color.green(pixel) == 0 && Color.blue(pixel) == 0)
                    win();
            }
        }

    }


    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.maze, 0, 0, paint);
        this.player.draw(canvas, paint);
    }
}
