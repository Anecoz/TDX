package com.anecoz.tdx.entities;

import com.anecoz.tdx.graphics.Drawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class Turret extends Drawable {

    protected float _range; //< Radius around turret (circle)
    protected Enemy _currentTarget = null;
    protected float _damage;
    protected float _cooldown; //< in seconds
    private long _lastActionTime;

    public final static int DAMAGE_TURRET_TYPE = 0;
    public final static int SLOW_TURRET_TYPE = 1;

    private final static int DAMAGE_TURRET_COST = 5;
    private final static int SLOW_TURRET_COST = 10;

    public Turret(Texture texture, Vector2 position) {
        super(texture, position, 1.0f);

        _damage = 15f;
        _range = 1.5f;
        _cooldown = 0.5f;
        _lastActionTime = -1;
    }

    public static int getCostFromType(int type) {
        switch (type) {
            case DAMAGE_TURRET_TYPE:
                return DAMAGE_TURRET_COST;
            case SLOW_TURRET_TYPE:
                return SLOW_TURRET_COST;
            default:
                return -1;
        }
    }

    protected void startCooldown() {
        _lastActionTime = TimeUtils.nanoTime();
    }

    protected boolean isOnCooldown() {
        if (_lastActionTime == -1)
            return false;

        long currTime = TimeUtils.nanoTime();
        return !(currTime - _lastActionTime >= (_cooldown * 1000000000));
    }

    public void tick() {
        updateTracking();
    }

    private void updateTracking() {
        if (_currentTarget != null) {
            if (_currentTarget.distanceTo(getMiddlePos()) > _range || _currentTarget.isDead()) {
                _currentTarget = null;
                _rotation = 0.0f;
            }
            else
                _rotation = _currentTarget.getMiddlePos().sub(getMiddlePos()).angle();
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
            if (enemy.distanceTo(getMiddlePos()) <= _range)
                return enemy;
        }
        return null;
    }
}
