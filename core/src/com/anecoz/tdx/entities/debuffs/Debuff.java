package com.anecoz.tdx.entities.debuffs;

import com.anecoz.tdx.entities.Enemy;
import com.badlogic.gdx.utils.TimeUtils;

public class Debuff {

    private float _lifeTime; //< in seconds
    private boolean _dead;
    private long _startTime;

    public Debuff(float lifeTime) {
        _lifeTime = lifeTime;
        _startTime = TimeUtils.nanoTime();
        _dead = false;
    }

    public boolean isDead() {
        return _dead;
    }

    public void tick() {
        long currTime = TimeUtils.nanoTime();
        if (currTime - _startTime >= (_lifeTime * 1000000000))
            _dead = true;
    }

    // Override this!
    public void apply(Enemy enemy) {

    }

    // And this!
    public void unapply(Enemy enemy) {

    }
}
