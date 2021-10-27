package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

public class AssetsLoader extends BaseAssetLoader {

    //texturas BG Parallax
    public Texture layerOne;
    public Texture layerTwo;
    public Texture layerThree;
    public Texture layerSky;

    //texturas botoes de controlo
    public Texture upArrow;
    public Texture leftArrow;
    public Texture rightArrow;
    public Texture attack;
    public Texture settings;

    //icones login/registo
    public Texture emailIcon;
    public Texture usernameIcon;
    public Texture passwordIcon;

    //botoes de menu
    public Texture loginButtonPressed;
    public Texture loginButtonShadow;
    public Texture registerButtonPressed;
    public Texture registerButtonShadow;
    public Texture optionButtonPressed;
    public Texture optionButtonShadow;
    public Texture playButtonPressed;
    public Texture playButtonShadow;
    public Texture aboutButtonPressed;
    public Texture aboutButtonShadow;

    //Logo
    public Texture BrightSoulsLogo;

    //Souls - Pontos
    Texture souls;
    public Animation<TextureRegion> soulsAnimation;

    //Flames - Vidas
    Texture flames;
    public Animation<TextureRegion> flamesAnimation;

    //Prida01 Font
    public BitmapFont pridaLight, pridaRegular, pridaBold, pridaBlack;

    //tiled map assets
    public TiledMap map;
    public TmxMapLoader mapLoader;

    //Hero animations
    Texture heroIdle;
    public Animation<TextureRegion> heroIdleAnim;

    Texture heroRun;
    public Animation<TextureRegion> heroRunAnim;



    public AssetsLoader() {
        //Logo
        addDisposable(BrightSoulsLogo = new Texture(Gdx.files.internal("Logos/brightSoulsBigLogo.png")));

        //BG
        addDisposable(layerOne = new Texture(Gdx.files.internal("Background/layerOne.png")));
        addDisposable(layerTwo = new Texture(Gdx.files.internal("Background/layerTwo.png")));
        addDisposable(layerThree = new Texture(Gdx.files.internal("Background/layerThree.png")));
        addDisposable(layerSky = new Texture(Gdx.files.internal("Background/layerSky.png")));

        //botoes HUD
        addDisposable(upArrow = new Texture(Gdx.files.internal("HUD/upHudButton.png")));
        addDisposable(leftArrow = new Texture(Gdx.files.internal("HUD/leftHudButton.png")));
        addDisposable(rightArrow = new Texture(Gdx.files.internal("HUD/rightHudButton.png")));
        addDisposable(attack = new Texture(Gdx.files.internal("HUD/attackHudButton.png")));
        addDisposable(settings = new Texture(Gdx.files.internal("HUD/settingsHudButton.png")));

        //icones login/registo
        addDisposable(emailIcon = new Texture(Gdx.files.internal("Icons/emailIcon.png")));
        addDisposable(usernameIcon = new Texture(Gdx.files.internal("Icons/usernameIcon.png")));
        addDisposable(passwordIcon = new Texture(Gdx.files.internal("Icons/passwordIcon.png")));

        //botoes menu
        addDisposable(loginButtonPressed = new Texture(Gdx.files.internal("Buttons/loginPressed.png")));
        addDisposable(loginButtonShadow = new Texture(Gdx.files.internal("Buttons/loginShadow.png")));
        addDisposable(registerButtonPressed = new Texture(Gdx.files.internal("Buttons/registerPressed.png")));
        addDisposable(registerButtonShadow = new Texture(Gdx.files.internal("Buttons/registerShadow.png")));
        addDisposable(playButtonPressed = new Texture(Gdx.files.internal("Buttons/playPressed.png")));
        addDisposable(playButtonShadow = new Texture(Gdx.files.internal("Buttons/playShadow.png")));
        addDisposable(optionButtonPressed = new Texture(Gdx.files.internal("Buttons/optionsPressed.png")));
        addDisposable(optionButtonShadow = new Texture(Gdx.files.internal("Buttons/optionsShadow.png")));
        addDisposable(aboutButtonPressed = new Texture(Gdx.files.internal("Buttons/aboutPressed.png")));
        addDisposable(aboutButtonShadow = new Texture(Gdx.files.internal("Buttons/aboutShadow.png")));

        //fonts
        addDisposable(pridaLight = new BitmapFont(Gdx.files.internal("Fonts/prida01_light.fnt")));
        pridaLight.getData().setScale(1);

        addDisposable(pridaRegular = new BitmapFont(Gdx.files.internal("Fonts/prida01_regular.fnt")));
        pridaRegular.getData().setScale(0.7f);

        addDisposable(pridaBold = new BitmapFont(Gdx.files.internal("Fonts/prida01_bold.fnt")));
        pridaBold.getData().setScale(1f);

        addDisposable(pridaBlack = new BitmapFont(Gdx.files.internal("Fonts/prida01_black.fnt")));
        pridaBlack.getData().setScale(1);

        //soul animation
        addDisposable(souls = new Texture(Gdx.files.internal("Sprites/souls.png")));
        soulsAnimation = buildAnimationFromTexture(souls, 64, true, false, Animation.PlayMode.LOOP_PINGPONG);

        //flame animation
        addDisposable(flames = new Texture(Gdx.files.internal("Sprites/flames.png")));
        flamesAnimation = buildAnimationFromTexture(flames, 16, true, false, Animation.PlayMode.LOOP);

//        //hero animations
//        addDisposable(heroIdle = new Texture(Gdx.files.internal("Characters/hero_idle.png")));
//        heroIdleAnim = buildAnimationFromTexture(heroIdle, 48, true, false, Animation.PlayMode.LOOP);
//
//        addDisposable(heroRun = new Texture(Gdx.files.internal("Characters/hero_run.png")));
//        heroRunAnim = buildAnimationFromTexture(heroRun, 48, true, false, Animation.PlayMode.LOOP);

        this.mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("TiledMaps/mapa.tmx");
    }




}

class BaseAssetLoader {
    private List<Disposable> disposableResources = new ArrayList<>();

    void addDisposable(Disposable disposable) {
        disposableResources.add(disposable);
    }

    Disposable getDisposable(int index) {
        return disposableResources.get(index);
    }


    static Animation<TextureRegion> buildAnimationFromTexture(Texture texture , int textRegSize , boolean flipHorizontally , boolean flipVertically ,
                                                              Animation.PlayMode playMode) {
        int numberInWidth = texture.getWidth() / textRegSize;
        int numberInHeight = texture.getHeight() / textRegSize;
        int numberOfTextureRegions = (numberInWidth * numberInHeight);
        TextureRegion[] texRegions = new TextureRegion[numberOfTextureRegions];
        for (int i = 0, x = 0, y = 0; i < texRegions.length; ++i) {
            texRegions[i] = new TextureRegion(texture , x * textRegSize , y * textRegSize , textRegSize , textRegSize);
            texRegions[i].flip(flipHorizontally , flipVertically);
            if (++x > numberInWidth) {
                x = 0;
                ++y;
            }
        }
        Animation <TextureRegion > animation = new Animation <>(0.5f, texRegions);
        animation.setPlayMode(playMode);
        return animation;
    }

    public void dispose(){
        for (Disposable disposableResource : disposableResources){
            disposableResource.dispose();
        }
    }

}//fim classe BaseAssets
