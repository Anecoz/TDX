package com.anecoz.tdx.entities;

import com.anecoz.tdx.level.Level;
import com.anecoz.tdx.utils.ResourceHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.ListIterator;

public class EntityHandler {

    public static ArrayList<Enemy> _enemies;
    private Level _level;
    private Player _player;

    public EntityHandler(Level level, Player player) {
        _enemies = new ArrayList<Enemy>();
        _level = level;
        _player = player;

        //_enemies.add(new Enemy(new Vector2(_level.getStartTile()), ResourceHandler._enemyTexture, _level.getPath(), 0.3f));
        //_enemies.add(new Enemy(new Vector2(_level.getStartTile()), ResourceHandler._enemyTexture, _level.getPath(), 0.3f));
        //_enemies.add(new Enemy(new Vector2(_level.getStartTile()), ResourceHandler._enemyTexture, _level.getPath(), 0.3f));
        //_enemies.add(new Enemy(new Vector2(_level.getStartTile()), ResourceHandler._enemyTexture, _level.getPath(), 0.3f));
    }

    static Turret getTurretFromType(int turretType, Vector2 spawnPos) {
        switch (turretType) {
            case Turret.DAMAGE_TURRET_TYPE:
                return new DamageTurret(ResourceHandler._dmgTurretTexture, spawnPos);
            case Turret.SLOW_TURRET_TYPE:
                return new SlowTurret(ResourceHandler._slowTurretTexture, spawnPos);
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
            // Kill dead ones and give player money for it
            if (enemy.isDead()) {
                _player.addMoney(enemy.getWorth());
                it.remove();
            }
            else if (enemy.isFinished()) {
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Enemy enemy : _enemies) {
            enemy.render(batch);
        }
    }
}
