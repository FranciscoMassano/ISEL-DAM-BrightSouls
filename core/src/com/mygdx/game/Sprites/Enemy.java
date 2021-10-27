package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    public final Vector2 velocity;
    public World world;
    public Screen screen;
    public Body b2body;

    public Enemy (PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;

        setPosition(x,y);
        defineEnemy();

        velocity = new Vector2(-50,-50);
    }

    public abstract void defineEnemy();
    public abstract String gettingHit();
    public abstract void setCategoryFilter(short filterBit);
    public abstract void update(float delta);

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;

        if (y)
            velocity.y = - velocity.y;
    }
}
