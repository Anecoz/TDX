package com.anecoz.tdx.entities;

import com.anecoz.tdx.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.ListIterator;

public class EntityHandler {

    public static ArrayList<Enemy> _enemies;
    private Level _level;
    public static Texture _enemyTexture;
    public static Texture _dmgTurretTexture;
    public static Texture _slowTurretTexture;

    public EntityHandler(Level level) {
        _enemies = new ArrayList<Enemy>();
        _level = level;
        _enemyTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        _dmgTurretTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        _slowTurretTexture = new Texture(Gdx.files.internal("droplet.png"));

        _enemies.add(new Enemy(new Vector2(_level.getStartTile()), _enemyTexture, _level.getPath(), 0.3f));
        _enemies.add(new Enemy(new Vector2(_level.getStartTile()), _enemyTexture, _level.getPath(), 0.3f));
        _enemies.add(new Enemy(new Vector2(_level.getStartTile()), _enemyTexture, _level.getPath(), 0.3f));
        _enemies.add(new Enemy(new Vector2(_level.getStartTile()), _enemyTexture, _level.getPath(), 0.3f));
    }

    static public Texture getTurretTexture(int turretType) {
        switch (turretType) {
            case Turret.DAMAGE_TURRET_TYPE:
                return _dmgTurretTexture;
            case Turret.SLOW_TURRET_TYPE:
                return _slowTurretTexture;
            default:
                break;
        }
        return null;
    }

    public void tick() {
        ListIterator<Enemy> it = _enemies.listIterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.tick();
            // Kill dead ones
            if (enemy.isDead())
                it.remove();
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : _enemies) {
            enemy.render(batch);
        }
    }
}
