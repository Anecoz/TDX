package com.anecoz.tdx;

import com.anecoz.tdx.entities.EntityHandler;
import com.anecoz.tdx.entities.Player;
import com.anecoz.tdx.level.Level;
import com.anecoz.tdx.logic.WaveHandler;
import com.anecoz.tdx.utils.ResourceHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    final TDXGame game;
    private Level level;
    private EntityHandler entityHandler;
    private Player player;
    private WaveHandler waveHandler;

    OrthographicCamera camera;

    public GameScreen(final TDXGame gam) {
        ResourceHandler.init();
        game = gam;

        level = new Level("map_02.tmx", game.batch); //< gotta do level first to init static dims

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Level.MAP_WIDTH, Level.MAP_HEIGHT+1); //< for space for bar

        player = new Player(level);
        entityHandler = new EntityHandler(level, player);
        waveHandler = new WaveHandler(level);
    }

    private void tick() {
        player.tick(camera);
        entityHandler.tick();
        waveHandler.tick();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tick();

        // tell the camera to update its matrices.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        level.render(camera);
        entityHandler.render(game.batch);
        player.render(game.batch);
        game.batch.end();

        player.getMoney().render();
        waveHandler.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
