package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Tools.AssetsLoader;


public class Hud implements Disposable {
    private final SpriteBatch batch;
    private final AssetsLoader assetsLoader;
    private PlayScreen level;
    private final int sH, sW;

    //animation no hud
    private final Animation<TextureRegion> soulsAnimation, flamesAnimation;

    public Hud(SpriteBatch batch, PlayScreen level){
        //main objects
        this.batch = batch;
        this.level = level;
        assetsLoader = level.assetsLoader;

        //screen sizes
        sH = level.main.screenH;
        sW = level.main.screenW;

        //animation souls
        soulsAnimation = assetsLoader.soulsAnimation;

        //flames animation
        flamesAnimation = assetsLoader.flamesAnimation;


    }

    public void render(float delta) {

        //draw score anim and text
        batch.begin();

        //soul animation
        TextureRegion soul = soulsAnimation.getKeyFrame(delta*1.5f, true);
        batch.draw(soul, 0,sH/1.28f, Math.abs(sW/6.5f), Math.abs(sH/4));

        //score display
        assetsLoader.pridaBlack.draw(batch,Integer.toString(level.score), sW/7.5f, sH - (sH/13.5f),sH/6f , Align.center, false);
        assetsLoader.pridaRegular.draw(batch,  "x", sW/13f, sH - (sH/10f),sH/6f , Align.center, false);

        batch.end();

        //draw lives accordinglly
        batch.begin();
        TextureRegion flame = flamesAnimation.getKeyFrame(delta*1.5f, true);
        switch (level.lives){
            case 1:
                batch.draw(flame, sW/2f - sW/98f,sH - (sH/5.8f), sW/12f, sH/7f);// third
                break;

            case 2:
                batch.draw(flame, sW/2.1f - sW/98f,sH - (sH/5.8f), sW/12f, sH/7f);// third
                batch.draw(flame, sW/1.9f - sW/98f ,sH - (sH/5.8f), sW/12f, sH/7f);// second
                break;
            case 3:
                batch.draw(flame, sW/2.25f - sW/98f,sH - (sH/5.8f), sW/12f, sH/7f);// third
                batch.draw(flame, sW/2f - sW/98f,sH - (sH/5.8f), sW/12f, sH/7f);// second
                batch.draw(flame, sW/1.8f - sW/98f ,sH - (sH/5.8f), sW/12f, sH/7f);// first
                break;
        }
        batch.end();

        //batch para botoes
        batch.begin();
        batch.draw(assetsLoader.leftArrow, level.left.x - level.left.radius, level.left.y - level.left.radius, level.left.radius*2, level.left.radius*2);
        batch.draw(assetsLoader.rightArrow, level.right.x - level.right.radius, level.right.y - level.right.radius, level.right.radius*2, level.right.radius*2);
        batch.draw(assetsLoader.upArrow, level.up.x - level.up.radius, level.up.y - level.up.radius, level.up.radius*2, level.up.radius*2);
        batch.draw(assetsLoader.attack, level.attack.x - level.attack.radius, level.attack.y - level.attack.radius, level.attack.radius*2, level.attack.radius*2);
        batch.draw(assetsLoader.settings, level.settings.x - level.settings.radius, level.settings.y - level.settings.radius, level.settings.radius*2, level.settings.radius*2);

        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
