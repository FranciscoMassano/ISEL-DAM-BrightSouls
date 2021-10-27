package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main.BrightSouls;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Hero;
import com.mygdx.game.Sprites.SimpleSkeleton;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.Scrollable;
import com.mygdx.game.Tools.AssetsLoader;


public class PlayScreen implements InputProcessor, Screen {

    //game references and others
    public BrightSouls main;
    public AssetsLoader assetsLoader;
    public final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    //playscreen variables
    public Viewport viewport;
    private OrthographicCamera camera;
    private Hud hud;
    public int score, lives;

    //tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d variables
    public World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //hero
    public Hero hero;

    //background fields
    private final Texture layerOneTex, layerTwoTex, layerThreeTex, layerSkyTex;
    private final Scrollable layerOneScroll, layerTwoScroll, layerThreeScroll, layerSkyScroll;
    private float L1ScrollTime, L2ScrollTime, L3ScrollTime;

    //controls
    public Circle up, left, right, attack, settings;
    public float radius;

    private  WorldContactListener worldContactListener;


    public float elapsedTime, jumpAgain, timeSinceJump, hVel, vVel;
    private int sW, sH;

    public float heroRecoverTime, timeSinceStruck;
    public boolean invencible;


    public PlayScreen(BrightSouls main) {
        //score e vidas
        this.score = 10;
        this.lives = 3;


        heroRecoverTime = 5;
        timeSinceStruck = 0;
        invencible = false;

        sH = Gdx.graphics.getHeight();
        sW = sH * main.screenW / main.screenH;


        //main app, and its dependencies
        this.main = main;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.assetsLoader = main.assetsLoader;

        //camera to follow player nad viewport to keep aspect ratio
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(BrightSouls.V_WIDTH/BrightSouls.PPM ,BrightSouls.V_HEIGHT/BrightSouls.PPM, camera);

        //displaying the HUD
        this.hud = new Hud(batch, this);

        //load the map and setup renderer
        this.mapLoader = assetsLoader.mapLoader;
        this.map = assetsLoader.map;
        this.renderer = new OrthogonalTiledMapRenderer(map);

        //start camera at center
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        hVel = 0;
        vVel = -100;

        //create the world with -10 gravity, enable debug lines
        this.world = new World(new Vector2(0, vVel), true);
        this.b2dr = new Box2DDebugRenderer();
        this.creator = new B2WorldCreator(this);

        //creating the hero
        this.hero = new Hero(this);

        //creating the contact listner
        worldContactListener = new WorldContactListener(this);
        world.setContactListener(worldContactListener);


        //temporizacoes
        L1ScrollTime = 0f;
        L2ScrollTime = 0f;
        L3ScrollTime = 0f;
        elapsedTime = 0f;

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

        //definir inputs
        defineButtons();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setInputProcessor(this);
    }

    public void update(float delta){
        handleInput(delta);
        timeSinceStruck += delta;
        if (timeSinceStruck > heroRecoverTime)
            invencible = false;

        if (camera.position.x <= 960)
            camera.position.x = 960;
        if (camera.position.x >= 2876)
            camera.position.x = 2876;

        //60 steps per seconds
        world.step(1/60f,6,2);

        //update the player
        hero.update(delta);

        for (Enemy enemy: creator.getSkeletons()) {
            enemy.update(delta);
        }

        //update camera
        camera.update();

        //render whats in bounds of the camera
        renderer.setView(camera);
    }


    private void handleInput(float delta){
        if (Gdx.input.isTouched(0) || Gdx.input.isTouched(1) ){
            int screenX = Gdx.input.getX();
            int screenY = sH - Gdx.input.getY();
            //main.log("cam pos: " + camera.position);
            //main.log("body pos: " + hero.b2body.getPosition());

            if (left.contains(screenX, screenY)) {
                //main.log("left");
                vVel = -50;
                hVel = -100;

                //camera.position.x = hero.b2body.getPosition().x;
                L1ScrollTime = 20f;
                L2ScrollTime = 45f;
                L3ScrollTime = 60f;

                //parallax speed relativa ao jogador
                layerOneScroll.setSpeed(L1ScrollTime);
                layerTwoScroll.setSpeed(L2ScrollTime);
                layerThreeScroll.setSpeed(L3ScrollTime);

                hero.b2body.applyLinearImpulse(new Vector2(hVel, vVel), hero.b2body.getWorldCenter(), true);
            }

            if (right.contains(screenX, screenY)) {
                //main.log("right");
                vVel = -50;
                hVel = 100;

                //camera.position.x = hero.b2body.getPosition().x;
                L1ScrollTime = -20f;
                L2ScrollTime = -45f;
                L3ScrollTime = -60f;

                //parallax speed relativa ao jogador
                layerOneScroll.setSpeed(L1ScrollTime);
                layerTwoScroll.setSpeed(L2ScrollTime);
                layerThreeScroll.setSpeed(L3ScrollTime);

                hero.b2body.applyLinearImpulse(new Vector2(hVel, vVel), hero.b2body.getWorldCenter(), true);
            }

            if (up.contains(screenX, screenY) && hero.b2body.getLinearVelocity().x == 0 && worldContactListener.isPlayerOnGround() ) {
                vVel = 1000;
                hero.b2body.applyLinearImpulse(new Vector2(0, vVel), hero.b2body.getWorldCenter(), true);
            }
            if (up.contains(screenX, screenY ) && hero.b2body.getLinearVelocity().x != 0 && worldContactListener.isPlayerOnGround()) {
                vVel = 1000;
                hero.b2body.applyLinearImpulse(new Vector2(hVel * 10, vVel), hero.b2body.getWorldCenter(), true);
            }

            if (attack.contains(screenX, screenY)){
                worldContactListener.isAttacking(true);
                hero.b2body.applyLinearImpulse(new Vector2(0, 0), hero.b2body.getWorldCenter(), true);
                hero.isAttacking = true;
            }
        }
    }

    public TiledMap getMap() {return this.map;}

    public World getWorld() {return  this.world;}

    public void defineButtons(){
        float size = assetsLoader.upArrow.getWidth()/1.5f;

        //left
        left = new Circle();
        left.setRadius(size/2.5f);
        left.setPosition(sW/10f, sH/6f);

        //right
        right = new Circle();
        right.setRadius(size/2.5f);
        right.setPosition(sW/4.5f, sH/6f);

        //up
        up = new Circle();
        up.setRadius(size/2.5f);
        up.setPosition(sW/1.1f, sH/3.1f);

        //attack
        attack = new Circle();
        attack.setRadius(size/2.5f);
        attack.setPosition(sW/1.25f, sH/6f);

        //settings
        settings = new Circle();
        settings.setRadius(size/2.5f);
        settings.setPosition(sW/1.08f, sH - (sH/10f));

        //radius definition for HUD
        this.radius = settings.radius;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        //update method before rendering
        update(delta);

//        //camera adjustments
        if (camera.position.x >= 960.0)
            camera.position.x = hero.b2body.getPosition().x;
            camera.position.y = hero.b2body.getPosition().y;

        if (camera.position.x <= 2876)
            camera.position.x = hero.b2body.getPosition().x;
            camera.position.y = hero.b2body.getPosition().y;


        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update BG positions
        layerOneScroll.update(delta);
        layerTwoScroll.update(delta);
        layerThreeScroll.update(delta) ;
        layerSkyScroll.update(delta);

        //draw BG
        batch.begin();
        layerSkyScroll.draw(batch);
        layerThreeScroll.draw(batch);
        layerTwoScroll.draw(batch);
        layerOneScroll.draw(batch);
        batch.end();

        //render the map
        renderer.render();

        //render debug lines
        b2dr.render(world, camera.combined);


        main.batch.setProjectionMatrix(camera.combined);
        main.batch.begin();
        hero.draw(main.batch);
        for (Enemy enemy: creator.getSkeletons()) {
            enemy.draw(main.batch);
        }
        main.batch.end();


        //para desenhar os circulos dos botoes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(right.x, right.y, right.radius);
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.circle(left.x, left.y, left.radius);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(up.x, up.y, up.radius);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(attack.x, attack.y, attack.radius);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(settings.x, settings.y, settings.radius);
        shapeRenderer.end();

        //renderizar o HUD no fim
        hud.render(elapsedTime);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        screenY = sH - screenY;
        if (settings.contains(screenX, screenY)){

        }

        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = sH - screenY;

        if (left.contains(screenX, screenY)) {
            //parallax speed relativa ao jogador
            layerOneScroll.setSpeed(0f);
            layerTwoScroll.setSpeed(0f);
            layerThreeScroll.setSpeed(0f);
            hero.b2body.applyLinearImpulse(new Vector2(-hVel, vVel), hero.b2body.getWorldCenter(), true);

        }
        if (right.contains(screenX, screenY)) {
            //parallax speed relativa ao jogador
            layerOneScroll.setSpeed(0f);
            layerTwoScroll.setSpeed(0f);
            layerThreeScroll.setSpeed(0f);
            hero.b2body.applyLinearImpulse(new Vector2(-hVel, vVel), hero.b2body.getWorldCenter(), true);
        }
        if (up.contains(screenX, screenY)) {

        }
        if (attack.contains(screenX,screenY)){
            hero.isAttacking = false;
            worldContactListener.isAttacking(true);

        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            main.setScreen(new MenuScreen(main));
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
        camera.setToOrtho(false, sH * width / (float)height, sH);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

}
