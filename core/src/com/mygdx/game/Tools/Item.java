package com.mygdx.game.Tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;
import com.sun.media.sound.SF2LayerRegion;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 vel;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.world;
        setPosition(x,y);
        setBounds(getX(), getY(), 50,50);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    public  abstract void defineItem();
    public abstract void useItem();
    public void draw(Batch batch){
        if (!destroyed){
            super.draw(batch);
        }
    }

    public void update(float delta){
        if (toDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
        }
    }

    public void  destroy(){
        toDestroy = true;
    }

}
