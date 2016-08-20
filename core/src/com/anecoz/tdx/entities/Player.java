package com.anecoz.tdx.entities;


import com.anecoz.tdx.level.Level;
import com.anecoz.tdx.logic.TurretPicker;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player {

    private Level _level;
    private ArrayList<Turret> _turrets;
    private TurretPicker _turretPicker;

    public Player(Level level) {
        _level = level;
        _turrets = new ArrayList<Turret>();
        _turretPicker = new TurretPicker();
    }

    public void render(SpriteBatch batch) {
        _turretPicker.render(batch);
        for (Turret turret : _turrets) {
            turret.render(batch);
        }
    }

    public void tick(OrthographicCamera camera) {
        _turretPicker.tick(camera);

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (_turretPicker.hasSelection()) {
                if (_level.isTileBuildableAt((int)touchPos.x, (int)touchPos.y)
                        && isTileFree((int)touchPos.x, (int)touchPos.y)) {
                    _turrets.add(getTurretFromType(_turretPicker.getSelectedTurretType(),
                            new Vector2((float)Math.floor(touchPos.x), (float)Math.floor(touchPos.y))));
                    _turretPicker.clearSelection();
                }
            }
        }
        for (Turret turret : _turrets) {
            turret.tick();
        }
    }

    private Turret getTurretFromType(int turretType, Vector2 spawnPos) {
        switch (turretType) {
            case Turret.DAMAGE_TURRET_TYPE:
                return new DamageTurret(EntityHandler._dmgTurretTexture, spawnPos);
            case Turret.SLOW_TURRET_TYPE:
                return new SlowTurret(EntityHandler._slowTurretTexture, spawnPos);
            default:
                break;
        }
        return null;
    }

    private boolean isTileFree(int x, int y) {
        for (Turret turret : _turrets) {
            if ((int)Math.floor(turret.getPosition().x) == x
                    && (int)Math.floor(turret.getPosition().y) == y)
                return false;
        }
        return true;
    }
}
