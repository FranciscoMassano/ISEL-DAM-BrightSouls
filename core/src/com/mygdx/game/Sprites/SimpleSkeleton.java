package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Screens.PlayScreen;

public class SimpleSkeleton extends Enemy {

    private float stateTime;
    private Animation walk;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    float directionTime;
    private boolean runRight;

    public SimpleSkeleton(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setCategoryFilter(BrightSouls.ENEMY_BIT);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/simple_skeleton_walk.png")),i*60,0,60,60));
        }
        walk = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(0,0,120,120);

        setToDestroy = false;
        destroyed = false;
    }

    public void update(float delta){
        stateTime += delta;
        directionTime += delta;
        if (setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero.png")),16*60,0,54,54));
            stateTime = 0;
        }

        else if (!destroyed){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth()/2-15, b2body.getPosition().y - getHeight()/2+20);
            setRegion((TextureRegion) walk.getKeyFrame(stateTime, true));
        }
        if (directionTime > 10){
            reverseVelocity(true,false);
            directionTime = 0;
        }

    }
    public void draw(Batch batch){
        if (!destroyed || stateTime < 1){
            super.draw(batch);
        }
    }

    @Override
    public void defineEnemy() {
        bodyDef = new BodyDef();
        bodyDef.position.set(getX() , getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30f);
        fixtureDef.filter.categoryBits = BrightSouls.ENEMY_BIT;
        fixtureDef.filter.maskBits = BrightSouls.GROUND_BIT | BrightSouls.ENEMY_BIT | BrightSouls.HERO_BIT | BrightSouls.SWORD_BIT ;

        fixtureDef.shape = shape;
        //b2body.createFixture(fixtureDef);
        b2body.createFixture(fixtureDef).setUserData("enemy");





//        EdgeShape feet = new EdgeShape();
//        feet.set(new Vector2(-24, -30), new Vector2(24, -30));
//        fixtureDef.shape = feet;
//        fixtureDef.isSensor = true;
//
        b2body.createFixture(fixtureDef).setUserData("feet");
    }

    @Override
    public String gettingHit() {
        setToDestroy = true;
        return "hit";
    }

    @Override
    public void setCategoryFilter(short filterBit) {
        fixtureDef.filter.categoryBits = filterBit;
    }


}
