package com.example.maze4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private float screenWidth;
    private float screenHeight;
    private float radius;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private String directionReal = "";
    private static String direction = "";

    private final float SPEED = 5;

    public Ball(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.radius = (float) (this.screenHeight * .01);
        this.x = -9999;
        this.y = -9999;
        this.dx = 0;
        this.dy = 0;
    }

    public static String getDirection() {
        return direction;
    }

    public static void setDirection(String direction) {
        direction = direction;
    }

    public void setDirectionReal(String directionReal) {
        this.directionReal = directionReal;
        updateDirection();
    }

    public String getDirectionReal() {
        return this.directionReal;
    }

    public void updateDirection() {
        if (directionReal.equals("right")) {
            this.dx = SPEED;
            this.dy = 0;
        }
        else if (directionReal.equals("left")) {
            this.dx = -SPEED;
            this.dy = 0;
        }
        else if (directionReal.equals("up")) {
            this.dx = 0;
            this.dy = -SPEED;
        }
        else if (directionReal.equals("down")) {
            this.dx = 0;
            this.dy = SPEED;
        }

        System.out.println("Dx: " + this.dx);
        System.out.println("Dy: " + this.dy);

    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        System.out.println("Ball placed at (" + this.x + ", " + this.y + ")");
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getRadius() {
        return this.radius;
    }


    public void update(float dt) {
        this.x += this.dx * dt;
        this.y += this.dy * dt;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }
}
