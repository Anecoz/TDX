package com.anecoz.tdx.entities;

import com.anecoz.tdx.graphics.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Turret extends Drawable {

    private float _range; //< Radius around turret (circle)
    private Enemy _currentTarget = null;
    private float _damage;
    private float _cooldown; //< in seconds

    private long _lastShootTime;

    public Turret(Texture texture, Vector2 position) {
        super(texture, position, 1.0f);

        _damage = 6f;
        _range = 1.5f;
        _cooldown = 0.5f;

        _lastShootTime = TimeUtils.nanoTime();
    }

    public void tick() {
        updateTracking();
        shoot();
    }

    private void shoot() {
        if (_currentTarget != null) {
            long currTime = TimeUtils.nanoTime();
            if (currTime - _lastShootTime >= (_cooldown * 1000000000)) {
                _currentTarget.takeDamage(_damage);
                _lastShootTime = currTime;
            }
        }
    }

    private void updateTracking() {
        if (_currentTarget != null) {
            if (_currentTarget.distanceTo(_position) >= _range || _currentTarget.isDead()) {
                _currentTarget = null;
                _rotation = 0.0f;
            }
            else
                _rotation = new Vector2(_currentTarget.getPosition()).sub(_position).angle();
        }
        else {
            Enemy enemy = findFirstCloseEnemy();
            if (enemy != null) {
                _currentTarget = enemy;
            }
        }
    }

    private Enemy findFirstCloseEnemy() {
        for (Enemy enemy : EntityHandler._enemies) {
            if (enemy.distanceTo(_position) <= _range)
                return enemy;
        }
        return null;
    }
}
