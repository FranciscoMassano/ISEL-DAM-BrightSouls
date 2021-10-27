package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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

public class RegisterScreen extends Actor implements Screen, InputProcessor {

    BrightSouls main;
    OrthographicCamera camera;
    Stage stage;
    SpriteBatch batch;
    Skin skin;
    AssetsLoader assetsLoader;
    ShapeRenderer shapeRenderer;
    //background fields
    Texture layerOneTex, layerTwoTex, layerThreeTex, layerSkyTex;
    Texture BSLogo;
    Texture emailIcon, usernameIcon, passwordIcon;
    Texture loginTexture, loginPressedTexture, loginShadowTexture;
    Texture registerTexture, registerPressedTexture, registerShadowTexture;
    Scrollable layerOneScroll, layerTwoScroll, layerThreeScroll, layerSkyScroll;
    I18NBundle myBundle;
    TextField usernameTF, passwordTF, emailTF;
    TextButton loginTextButton, registerTextButton;
    float L1ScrollTime, L2ScrollTime, L3ScrollTime;
    int sW, sH;


    public RegisterScreen(final BrightSouls main) {
        this.main = main;
        this.stage = new Stage();
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.assetsLoader = main.assetsLoader;
        //FileHandle baseFileHandle = Gdx.files.internal("Strings//strings");
        //myBundle = I18NBundle.createBundle(baseFileHandle);

        sW = main.screenW;
        sH = main.screenH;
        L1ScrollTime = -20f;
        L2ScrollTime = -45f;
        L3ScrollTime = -60f;


        //Definir logo
        BSLogo = assetsLoader.BrightSoulsLogo;

        //Definir icons
        emailIcon = assetsLoader.emailIcon;
        usernameIcon = assetsLoader.usernameIcon;
        passwordIcon = assetsLoader.passwordIcon;

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
        layerTwoScroll = new Scrollable(layerTwoTex , 0, 0, sW,  sH-(sH/12), L2ScrollTime);
        layerThreeScroll = new Scrollable(layerThreeTex , 0, 0, sW, sH, L3ScrollTime);
        layerSkyScroll = new Scrollable(layerSkyTex , 0, 0, sW, sH, 0f);

        //skin for the register
        skin = new Skin(Gdx.files.internal("Skins//uiskin.json"));

        emailTF = new TextField("", skin);
        emailTF.setMessageText("Email");
        emailTF.setPosition(sW/10f, sH/2f);
        emailTF.setSize(sW/2.4f,sH/6.5f);
        emailTF.setAlignment(Align.center);

        usernameTF = new TextField("", skin);
        usernameTF.setMessageText("Username");
        usernameTF.setPosition(sW/10f, sH/3.108f);
        usernameTF.setSize(sW/2.4f,sH/6.5f);
        usernameTF.setAlignment(Align.center);

        passwordTF = new TextField("", skin);
        passwordTF.setMessageText("Password");
        passwordTF.setPosition(sW/10f, sH/7f);
        passwordTF.setSize(sW/2.4f,sH/6.5f);
        passwordTF.setAlignment(Align.center);

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
                            //main.log("Ecra Registo -> Botao Login");
                            main.setScreen(new LoginScreen(main));
                        }
                    }, 0.1f);
                }else {
                    loginTexture = loginShadowTexture;
                }
                return false;
            }
        });
        loginTextButton.setColor(0,0,0,0);


        registerTextButton = new TextButton("", skin);
        registerTextButton.setPosition(sW - sW / 3.12f, sH/4f);
        registerTextButton.setSize(assetsLoader.registerButtonPressed.getWidth()/3.4f, assetsLoader.registerButtonPressed.getHeight()/3f);
        registerTextButton.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (registerTextButton.isPressed()){
                    //TODO -> Fazer registo na Firebase, setScreen(Menu)
                    registerTexture = registerPressedTexture;
                    Timer.schedule(new Timer.Task(){
                        @Override
                        public void run() {
                            //main.log("Ecra Registo -> Botao Registar");
                        }
                    }, 0.1f);
                }
                else {
                    registerTexture = registerShadowTexture;
                }
                return false;
            }
        });
        registerTextButton.setColor(0,0,0,0);

        stage.addActor(emailTF);
        stage.addActor(usernameTF);
        stage.addActor(passwordTF);
        stage.addActor(loginTextButton);
        stage.addActor(registerTextButton);
        Gdx.input.setInputProcessor(stage);
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
        //field icons
        batch.draw(emailIcon, 1050,570, sW/15f, sW/15f);
        batch.draw(usernameIcon, 1050,370, sW/15f, sW/15f);
        batch.draw(passwordIcon, 1050,170, sW/15f, sW/15f);
        //buttons
        batch.draw(loginTexture, sW - (sW/3.1f),sH/2.1f, sW/3.8f, sH/5.4f);
        batch.draw(registerTexture, sW - (sW/3.1f),sH/4f , sW/3.8f, sH/5.4f);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { screenY = sH - screenY;
        return true;
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
