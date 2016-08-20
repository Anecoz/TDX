package com.anecoz.tdx.entities;


import com.anecoz.tdx.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player {

    private Level _level;
    private ArrayList<Turret> turrets;

    public Player(Level level) {
        _level = level;

        turrets = new ArrayList<Turret>();
    }

    public void render(SpriteBatch batch) {
        for (Turret turret : turrets) {
            turret.render(batch);
        }
    }

    public void tick(OrthographicCamera camera) {
        // Check input TODO ADD TIMER SO WE CAN'T SPAM
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (_level.isTileBuildableAt((int)touchPos.x, (int)touchPos.y)) {
                turrets.add(new Turret(
                        EntityHandler._turretTexture,
                        new Vector2((float)Math.floor(touchPos.x), (float)Math.floor(touchPos.y))));
            }
        }
    }
}
