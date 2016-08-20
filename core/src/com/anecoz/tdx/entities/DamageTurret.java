package com.anecoz.tdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class DamageTurret extends Turret {

    private long _lastShootTime;

    public DamageTurret(Texture texture, Vector2 position) {
        super(texture, position);

        _lastShootTime = TimeUtils.nanoTime();
    }

    @Override
    public void tick() {
        super.tick();
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
}
