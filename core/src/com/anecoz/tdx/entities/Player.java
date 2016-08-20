package com.anecoz.tdx.entities;


import com.anecoz.tdx.level.Level;
import com.anecoz.tdx.logic.Money;
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
    private Money _money;

    public Player(Level level) {
        _level = level;
        _turrets = new ArrayList<Turret>();
        _turretPicker = new TurretPicker(this);
        _money = new Money();
    }

    public void render(SpriteBatch batch) {
        _turretPicker.render(batch);
        for (Turret turret : _turrets) {
            turret.render(batch);
        }
    }

    public Money getMoney() {
        return _money;
    }

    public void addMoney(int amount) {
        _money.addCash(amount);
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

                    // Can we afford it?
                    int cost = Turret.getCostFromType(_turretPicker.getSelectedTurretType());
                    if (_money.canAfford(cost)) {
                        _turrets.add(EntityHandler.getTurretFromType(_turretPicker.getSelectedTurretType(),
                                new Vector2((float)Math.floor(touchPos.x), (float)Math.floor(touchPos.y))));
                        _turretPicker.clearSelection();

                        _money.deductCash(cost);
                    }
                }
            }
        }
        for (Turret turret : _turrets) {
            turret.tick();
        }
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
