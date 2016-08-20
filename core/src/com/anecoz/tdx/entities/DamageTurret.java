package com.anecoz.tdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DamageTurret extends Turret {

    public DamageTurret(Texture texture, Vector2 position) {
        super(texture, position);
    }

    @Override
    public void tick() {
        super.tick();
        shoot();
    }

    private void shoot() {
        if (_currentTarget != null) {
            if (!isOnCooldown()) {
                _currentTarget.takeDamage(_damage);
                resetCooldown();
            }
        }
    }
}
