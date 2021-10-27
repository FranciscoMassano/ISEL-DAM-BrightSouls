package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.AssetsLoader;
import com.mygdx.game.Tools.Item;

public class Soul extends Item {
    private Animation animation;
    private AssetsLoader assetsLoader;
    public Soul(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        assetsLoader = screen.assetsLoader;

        animation = assetsLoader.soulsAnimation;

        //stateTime = 0;
        setBounds(0,0,120,120);

        //setToDestroy = false;
        destroyed = false;


    }

    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() , getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30f);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

    }

    @Override
    public void useItem() {

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        body.setLinearVelocity(new Vector2(0,0));
    }
}
