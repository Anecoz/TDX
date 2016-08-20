package com.anecoz.tdx.entities.debuffs;

import com.anecoz.tdx.entities.Enemy;

public class SlowDebuff extends Debuff {

    private float _origSpeed;

    public SlowDebuff(float lifeTime) {
        super(lifeTime);
    }

    @Override
    public void apply(Enemy enemy) {
        _origSpeed = enemy.getBaseSpeed();
        enemy.setSpeed(_origSpeed*0.2f);
    }

    @Override
    public void unapply(Enemy enemy) {
        enemy.setSpeed(_origSpeed);
    }
}
