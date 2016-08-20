package com.anecoz.tdx.entities;

import com.anecoz.tdx.entities.debuffs.SlowDebuff;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class SlowTurret extends Turret {

    public SlowTurret(Texture texture, Vector2 position) {
        super(texture, position);
        _cooldown = 2.0f;
    }

    @Override
    public void tick() {
        super.tick();
        slow();
    }

    private void slow() {
        if (_currentTarget != null) {
            _currentTarget.addDebuff(new SlowDebuff(3.0f));
        }
    }
}
