package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.SimpleSkeleton;

public class B2WorldCreator {


    public Array<SimpleSkeleton> skeletons;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map  = screen.getMap();
        skeletons = new Array<SimpleSkeleton>();


        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2), (rect.getY() + rect.getHeight()/2));
            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth()/2), (rect.getHeight()/2));
            fdef.shape = shape;
            body.createFixture(fdef);

        }

        //skellies
        for (MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            skeletons.add(new SimpleSkeleton(screen, rect.getX(), rect.getY()));
        }

    }

    public Array<SimpleSkeleton> getSkeletons() {
        return skeletons;
    }


}
