package com.anecoz.tdx.entities;

import com.anecoz.tdx.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EntityHandler {

    private ArrayList<Enemy> _enemies;
    private Level _level;
    private Texture _enemyTexture;

    public EntityHandler(Level level) {
        _enemies = new ArrayList<Enemy>();
        _level = level;
        _enemyTexture = new Texture(Gdx.files.internal("badlogic.jpg"));

        _enemies.add(new Enemy(_level.getStartTile(), _enemyTexture, _level.getPath(), 0.7f));
    }

    public void tick() {
        for (Enemy enemy : _enemies) {
            enemy.tick();
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : _enemies) {
            enemy.render(batch);
        }
    }
}
