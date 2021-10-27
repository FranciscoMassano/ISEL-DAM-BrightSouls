package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Tools.Scrollable;
import com.mygdx.game.Tools.AssetsLoader;

public class LoginScreen extends Actor implements Screen, InputProcessor {

    BrightSouls main;
    OrthographicCamera camera;
    Stage stage;
    SpriteBatch batch;
    Skin skin;
    AssetsLoader assetsLoader;
    ShapeRenderer shapeRenderer;
    //background fields
    Texture layerOneTex, layerTwoTex, layerThreeTex, layerSkyTex, BSLogo, emailTexture, usernameTexture, passwordTexture;
    Texture loginTexture, loginPressedTexture, loginShadowTexture;
    Texture registerTexture, registerPressedTexture, registerShadowTexture;
    Scrollable layerOneScroll, layerTwoScroll, layerThreeScroll, layerSkyScroll;
    I18NBundle myBundle;
    TextButton registerTextButton, loginTextButton;
    TextField username, password;
    float L1ScrollTime, L2ScrollTime, L3ScrollTime;
    int sW, sH;


    public LoginScreen(final BrightSouls main) {
        this.main = main;
        batch = main.batch;
        assetsLoader = main.assetsLoader;

        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        FileHandle baseFileHandle = Gdx.files.internal("Strings//strings");
        myBundle = I18NBundle.createBundle(baseFileHandle);

        //screen sizes
        sW = main.screenW;
        sH = main.screenH;

        //temporizacoes
        L1ScrollTime = -20f;
        L2ScrollTime = -45f;
        L3ScrollTime = -60f;

        //Definir logo
        BSLogo = assetsLoader.BrightSoulsLogo;

        //Definir icones
        emailTexture = assetsLoader.emailIcon;
        usernameTexture = assetsLoader.usernameIcon;
        passwordTexture = assetsLoader.passwordIcon;

        //definir botao login
        loginPressedTexture = assetsLoader.loginButtonPressed;
        loginShadowTexture = assetsLoader.loginButtonShadow;
        loginTexture = loginShadowTexture;

        //botao registo
        registerPressedTexture = assetsLoader.registerButtonPressed;
        registerShadowTexture = assetsLoader.registerButtonShadow;
        registerTexture = registerShadowTexture;

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


        //skin for the register
        skin = new Skin(Gdx.files.internal("Skins//uiskin.json"));

        username = new TextField("", skin);
        username.setMessageText("Username");
        username.setPosition(sH/6f, sH/2.6f);
        username.setSize(sW/2.4f,sH/6.5f);
        username.setAlignment(Align.center);

        password = new TextField("", skin);
        password.setMessageText("Password");
        password.setPosition(sH/6f, sH/8f);
        password.setSize(sW/2.4f,sH/6.5f);
        password.setAlignment(Align.center);

        loginTextButton = new TextButton("", skin);
        loginTextButton.setPosition(sW - sW / 3.12f, sH/2.1f);
        loginTextButton.setSize(assetsLoader.loginButtonPressed.getWidth()/3.4f, assetsLoader.loginButtonPressed.getHeight()/3f);
        loginTextButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (loginTextButton.isPressed()){
                    //TODO -> Fazer login, Firebase, setScreen(Menu)
                    loginTexture = loginPressedTexture;
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            //main.log("Ecra Login -> Botao Login");
                            main.setScreen(new MenuScreen(main));
                        }
                    }, 0.1f);
                }
                else {
                    loginTexture = loginShadowTexture;
                }
                return false;
            }
        });
        loginTextButton.setColor(1,1,1,0f);


        registerTextButton = new TextButton("", skin);
        registerTextButton.setPosition(sW - sW / 3.12f, sH/4f);
        registerTextButton.setSize(assetsLoader.registerButtonPressed.getWidth()/3.4f, assetsLoader.registerButtonPressed.getHeight()/3f);
        registerTextButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (registerTextButton.isPressed()){
                    registerTexture = registerPressedTexture;
                    //TODO -> Fazer registo na Firebase, setScreen(Menu)
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            //main.log("Ecra Login -> Botao Registo");
                            main.setScreen( new RegisterScreen(main));
                        }
                    }, 0.1f);
                }else {
                    registerTexture = registerShadowTexture;
                }
                return false;
            }
        });
        registerTextButton.setColor(1,1,1,0f);


        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(loginTextButton);
        stage.addActor(registerTextButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        layerOneScroll.update(delta);
        layerTwoScroll.update(delta);
        layerThreeScroll.update(delta );
        layerSkyScroll.update(delta );

        //batch para BG
        batch.begin();
        layerSkyScroll.draw(batch);
        layerThreeScroll.draw(batch);
        layerTwoScroll.draw(batch);
        layerOneScroll.draw(batch);
        batch.end();

        //batch para os botoes
        batch.begin();
        //logo
        batch.draw(BSLogo, sW/4f,sH - (sH/2.4f), sW/2f, sH/2.5f);
        //field icons
        batch.draw(usernameTexture, sW/1.85f,sH/2.5f, sW/15f, sW/15f);
        batch.draw(passwordTexture, sW/1.85f,sH/7f, sW/15f, sW/15f);
        //buttons
        batch.draw(loginTexture, sW - (sW/3.1f),sH/2.7f, sW/3.8f, sH/5.4f);
        batch.draw(registerTexture, sW - (sW/3.1f),sH/9f , sW/3.8f, sH/5.4f);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {return false;}

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

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            Gdx.app.exit();
        }
        return true;
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
}
