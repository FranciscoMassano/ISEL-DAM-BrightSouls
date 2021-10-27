package com.mygdx.game.Screens;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Hero;

public class WorldContactListener implements ContactListener {

    private final PlayScreen screen;
    private  boolean isOnGround;
    private boolean isAttacking;
    public WorldContactListener(PlayScreen screen){
        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        if(fixA.getUserData() == "feet" && fixB.getUserData() == null) {
            isOnGround = true;
        }
        else if(fixB.getUserData() == "feet" && fixA.getUserData() == null) {
            isOnGround = true;
        }

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
//        BrightSouls.log("A: " + fixA.getUserData() + "      " + fixA.getFilterData().categoryBits);
//        BrightSouls.log("B: " + fixB.getUserData() + "      " + fixB.getFilterData().categoryBits);
//        BrightSouls.log("c: " + cDef);

        switch (cDef){

            case BrightSouls.ENEMY_BIT | BrightSouls.HERO_BIT:
                if(fixA.getFilterData().categoryBits == BrightSouls.HERO_BIT && fixB.getFilterData().categoryBits == BrightSouls.ENEMY_BIT ) {
                    BrightSouls.log("hit on Hero");
                    if (!screen.invencible){
                        screen.lives -=1;
                        screen.timeSinceStruck = 0;
                        screen.invencible = true;
                    }

                }
                else if(fixB.getFilterData().categoryBits == BrightSouls.HERO_BIT && fixA.getFilterData().categoryBits == BrightSouls.ENEMY_BIT) {
                    BrightSouls.log("hit on Hero");
                    if (!screen.invencible){
                        screen.lives -=1;
                        screen.timeSinceStruck = 0;
                        screen.invencible = true;
                    }
                }

            case BrightSouls.ENEMY_BIT | BrightSouls.SWORD_BIT:
                if ((fixA.getUserData() == "swordLeft" || fixA.getUserData() == "swordRight") && fixB.getUserData() == null && isAttacking) {
                    BrightSouls.log("hit on enemy top");
                    BrightSouls.log( ((Enemy) fixB.getUserData()).gettingHit() + "");

                }  else if ((fixB.getUserData() == "swordLeft" || fixB.getUserData() == "swordRight") && fixA.getUserData() == null && isAttacking) {
                    BrightSouls.log("hit on enemy bot");
                }
                break;


        }




    }

    public boolean isPlayerOnGround(){return isOnGround;}

    @Override
    public void endContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits;


        switch (cDef){

            //jumping
            case BrightSouls.HERO_BIT | BrightSouls.GROUND_BIT:
                if(fixA.getUserData() == "feet") {
                    isOnGround = false;
                }

                else if(fixB.getUserData() == "feet") {
                    isOnGround = false;
                }
                break;

        }

        if (fixA.getUserData() == null && fixB.getUserData() == "feet"){
            isOnGround = false;
        }
        if (fixB.getUserData() == null && fixA.getUserData() == "feet"){
            isOnGround = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits;

        switch (cDef) {


            case BrightSouls.ENEMY_BIT | BrightSouls.SWORD_BIT:
                if ((fixA.getUserData() == "swordLeft" || fixA.getUserData() == "swordRight") && fixB.getUserData() == null && isAttacking) {
                    BrightSouls.log("hit on enemy");
                } else if ((fixB.getUserData() == "swordLeft" || fixB.getUserData() == "swordRight") && fixA.getUserData() == null && isAttacking) {
                    BrightSouls.log("hit on enemy");
                }
                break;
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public void isAttacking(boolean b) {
        isAttacking = b;
    }
}
