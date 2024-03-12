package com.mygdx.game.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Knight extends Actor {

    public static final int KNIGHT = 0;

    private Vector2 position;
    private int width, height;
    private int direction;
    private Rectangle collisionRect;

    public Vector2 getPosition() {
        return position;
    }
    public float getX() {
        return position.x;
    }
    public float getY() {
        return position.y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public Knight(float x, float y, int width, int height) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        direction = KNIGHT;
        collisionRect = new Rectangle();
    }


}
