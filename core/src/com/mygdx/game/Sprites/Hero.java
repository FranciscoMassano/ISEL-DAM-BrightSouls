package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.AssetsLoader;

public class Hero extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, DYING};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    public PlayScreen level;
    private TextureRegion hero;

    private Animation<TextureRegion> heroAttack, heroJump, heroRun, heroDeath, heroIdle;
    private boolean runRight;
    private float stateTime;
    public boolean isAttacking;



    public Hero(PlayScreen screen){
        this.world = screen.getWorld();
        this.level = screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTime = 0;
        runRight = true;
        isAttacking = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //loop for idle
        for (int i = 0; i < 4 ; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero_idle.png")),i*54,0,54,54));
        }
        heroIdle = new Animation<TextureRegion>(0.3f,frames);
        frames.clear();

        //loop for jump anim
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero_jump.png")),i*54,0,54,54));
        }
        heroJump = new Animation<TextureRegion>(.3f,frames);
        frames.clear();

        //loop for death anim
        for (int i = 0; i < 4 ; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero_death.png")),i*54,0,54,54));
        }
        heroDeath = new Animation<TextureRegion>(.3f,frames);
        frames.clear();

        //loop for attack
        for (int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero_attack.png")),i*54,0,54,54));
        }
        heroAttack = new Animation<TextureRegion>(.1f,frames);
        frames.clear();

        //loop for running
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("Characters/hero_run.png")),i*54,0,54,54));
        }
        heroRun = new Animation<TextureRegion>(.2f,frames);
        frames.clear();

        defineHero();
        setBounds(0,0,120,120);
    }

    private void defineHero(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(800, 500);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30f);
        fixtureDef.filter.categoryBits = BrightSouls.HERO_BIT;
        fixtureDef.filter.maskBits = BrightSouls.GROUND_BIT | BrightSouls.ENEMY_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);


        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-24, -32), new Vector2(24, -32));
        fixtureDef.shape = feet;
        fixtureDef.isSensor = true;

        b2body.createFixture(fixtureDef).setUserData("feet");

        EdgeShape sword = new EdgeShape();
        sword.set(new Vector2(50, 30), new Vector2(50, -30));
        fixtureDef.filter.categoryBits = BrightSouls.SWORD_BIT;
        fixtureDef.filter.maskBits = BrightSouls.ENEMY_BIT;


        fixtureDef.shape = sword;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("swordRight");

        sword = new EdgeShape();
        sword.set(new Vector2(-50, 30), new Vector2(-50, -30));
        fixtureDef.filter.categoryBits = BrightSouls.SWORD_BIT;
        fixtureDef.filter.maskBits = BrightSouls.ENEMY_BIT;


        fixtureDef.shape = sword;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData("swordLeft");


    }

    public void update(float delta){

        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2); // adjust sprite to body
        setRegion(getFrame(delta));//get current frame
    }

    public TextureRegion getFrame(float delta){
        //1. Get current state
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = heroJump.getKeyFrame(stateTime, true);
                break;
            case RUNNING:
                region = heroRun.getKeyFrame(stateTime, true);
                break;
            case DYING:
                region = heroDeath.getKeyFrame(stateTime);
                break;
            case ATTACKING:
                region = heroAttack.getKeyFrame(stateTime,true);
                break;
            case FALLING:
                region = heroIdle.getKeyFrame(stateTime);
                break;
            case STANDING:
            default:
                region = heroIdle.getKeyFrame(stateTime,true);
                break;
        }

        //check running left, and check region direction
        if ( (b2body.getLinearVelocity().x < 0 || !runRight) && !region.isFlipX()){
            region.flip(true,false);
            runRight = false;
        }
        //facing left and running rigt
        else if ((b2body.getLinearVelocity().x > 0 || runRight) && region.isFlipX()){
            region.flip(true, false);
            runRight = true;
        }

        //in case we change state, reset timer, otherwise increment

        stateTime = currentState == previousState ? stateTime + delta : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        //we use our body to find the state

        //going up, is jumping
        if (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) {
            return State.JUMPING;
        }
        //going down, is falling
        else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
        //moving
        else if (b2body.getLinearVelocity().x != 0 && level.lives > 0){
            return State.RUNNING;
        }
        //get lives from level
        else if (level.lives == 0){
            //level.world.destroyBody(b2body);
            return State.DYING;
        }
        else if ( isAttacking){
            return State.ATTACKING;
        }
        //if nothing else, hes standing still
        else{
            return State.STANDING;
        }

    }


}
