package com.anecoz.tdx;

import com.anecoz.tdx.entities.EntityHandler;
import com.anecoz.tdx.level.Level;
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

    OrthographicCamera camera;

    public GameScreen(final TDXGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Level.MAP_WIDTH, Level.MAP_HEIGHT);

        level = new Level("map_02.tmx", game.batch);
        entityHandler = new EntityHandler(level);
    }

    private void tick() {
        entityHandler.tick();
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
        game.batch.end();

        // process user input
        /*if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        raindrop.y -= 200 * Gdx.graphics.getDeltaTime();*/
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
