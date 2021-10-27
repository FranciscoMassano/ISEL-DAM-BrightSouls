package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Tools.Scrollable;
import com.mygdx.game.Tools.AssetsLoader;

public class MenuScreen implements InputProcessor, Screen {

    BrightSouls main;
    OrthographicCamera camera;
    SpriteBatch batch;
    AssetsLoader assetsLoader;
    ShapeRenderer shapeRenderer;
    //background fields
    Texture layerOneTex, layerTwoTex, layerThreeTex, layerSkyTex, BSLogo;
    Texture playTexture, playPressedTexture, playShadowsTexture;
    Texture optionsTexture, optionsPressedTexture, optionsShadowTexture;
    Texture aboutTexture, aboutPressedTexture, aboutShadowTexture;
    Scrollable layerOneScroll, layerTwoScroll, layerThreeScroll, layerSkyScroll;
    Rectangle playButtonTouchTrigger, optionsButtonTouchTrigger, aboutButtonTouchTrigger;
    float L1ScrollTime, L2ScrollTime, L3ScrollTime;
    int sW, sH;



    public MenuScreen(BrightSouls main) {
        this.main = main;
        this.batch = main.batch;
        this.assetsLoader = main.assetsLoader;

        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();

        sW = main.screenW;
        sH = main.screenH;
        L1ScrollTime = -20f;
        L2ScrollTime = -45f;
        L3ScrollTime = -60f;

        //Definir logo
        BSLogo = assetsLoader.BrightSoulsLogo;

        //definir botoes play/options/about
        playPressedTexture = assetsLoader.playButtonPressed;
        playShadowsTexture = assetsLoader.playButtonShadow;
        playTexture = playShadowsTexture;

        //option button texture
        optionsPressedTexture = assetsLoader.optionButtonPressed;
        optionsShadowTexture = assetsLoader.optionButtonShadow;
        optionsTexture = optionsShadowTexture;

        //about button textures
        aboutPressedTexture = assetsLoader.aboutButtonPressed;
        aboutShadowTexture = assetsLoader.aboutButtonShadow;
        aboutTexture = aboutShadowTexture;

        //rectangulos para onTouch dos botoes
        playButtonTouchTrigger = new Rectangle( sW/15.5f,  sH/2.8f, assetsLoader.playButtonPressed.getWidth()/2.5f , assetsLoader.playButtonPressed.getHeight()/2.5f);
        optionsButtonTouchTrigger = new Rectangle( sW - (sW / 1.55f),  sH/3.2f, assetsLoader.playButtonPressed.getWidth()/2.5f , assetsLoader.playButtonPressed.getHeight()/2.5f);
        aboutButtonTouchTrigger = new Rectangle( sW- (sW / 1.55f),  sH/16f, assetsLoader.playButtonPressed.getWidth()/2.5f , assetsLoader.playButtonPressed.getHeight()/2.5f);

        //definir as Textures para o BG Parallax
        layerOneTex = assetsLoader.layerOne;
        layerTwoTex = assetsLoader.layerTwo;
        layerThreeTex = assetsLoader.layerThree;
        layerSkyTex = assetsLoader.layerSky;

        //definir os objectos Parallaz
        layerOneScroll = new Scrollable(layerOneTex , 0, 0, sW, sH-(sH/4), L1ScrollTime);
        layerTwoScroll = new Scrollable(layerTwoTex , 0, 0, sW, (int) (sH-(sH/12)), L2ScrollTime);
        layerThreeScroll = new Scrollable(layerThreeTex , 0, 0, sW, sH, L3ScrollTime);
        layerSkyScroll = new Scrollable(layerSkyTex , 0, 0, sW, sH, 0f);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    @Override
    public void render(float delta) {

        layerOneScroll.update(delta );
        layerTwoScroll.update(delta );
        layerThreeScroll.update(delta );
        layerSkyScroll.update(delta );

        //batch para BG
        batch.begin();
        layerSkyScroll.draw(batch);
        layerThreeScroll.draw(batch);
        layerTwoScroll.draw(batch);
        layerOneScroll.draw(batch);
        batch.end();

        //batch para o conteudo
        batch.begin();
        //logo
        batch.draw(BSLogo, sW/4f,sH - (sH/2.4f), sW/2f, sH/2.5f);
        //buttons
        batch.draw(playTexture,  sW/16f,sH/2.8f, sW/2.8f, sH/4.2f);
        batch.draw(optionsTexture, sW/1.7f,sH/2.8f, sW/2.8f, sH/4.2f);
        batch.draw(aboutTexture, sW/3f,sH/20f, sW/2.8f, sH/4.2f);
        batch.end();

        //rectangulos para os onTouch
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        //play
        //shapeRenderer.rect(playButtonTouchTrigger.x, playButtonTouchTrigger.y, playButtonTouchTrigger.width, playButtonTouchTrigger.height);
        //option
        //shapeRenderer.setColor(Color.GREEN);
        //shapeRenderer.rect(optionsButtonTouchTrigger.x, optionsButtonTouchTrigger.y, optionsButtonTouchTrigger.width, optionsButtonTouchTrigger.height);
        //about
        //shapeRenderer.setColor(Color.BLUE);
        //shapeRenderer.rect(aboutButtonTouchTrigger.x, aboutButtonTouchTrigger.y, aboutButtonTouchTrigger.width, aboutButtonTouchTrigger.height);
        shapeRenderer.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = Gdx.graphics.getHeight() - screenY;
        if (playButtonTouchTrigger.contains(screenX, screenY)) {
            //main.log("Ecra Menu -> Botao Play");
            playTexture = playPressedTexture;
        }
        if (optionsButtonTouchTrigger.contains(screenX, screenY)) {
            //main.log("Ecra Menu -> Botao Options");
            //TODO -> Mais opcoes
            optionsTexture = optionsPressedTexture;
        }
        if (aboutButtonTouchTrigger.contains(screenX, screenY)) {
           //main.log("Ecra Menu -> Botao About");
            //TODO -> Ecra sobre mim
            aboutTexture = aboutPressedTexture;

        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        screenY = Gdx.graphics.getHeight() - screenY;
        if (playButtonTouchTrigger.contains(screenX, screenY)) {
            //TODO -> Mudar para o jogo
            playTexture = playShadowsTexture;
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    main.log("Ecra Menu -> Botao Play");
                    main.setScreen(new PlayScreen(main));
                }
            }, 0.2f);
        }
        if (optionsButtonTouchTrigger.contains(screenX, screenY)) {
            //main.log("Ecra Menu -> Botao Options");
            //TODO -> Mais opcoes
            optionsTexture = optionsShadowTexture;
        }
        if (aboutButtonTouchTrigger.contains(screenX, screenY)) {
            //main.log("Ecra Menu -> Botao About");
            //TODO -> Ecra sobre mim
            aboutTexture = aboutShadowTexture;
        }
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            main.setScreen(new LoginScreen(main));
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }




    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void show() {

    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
