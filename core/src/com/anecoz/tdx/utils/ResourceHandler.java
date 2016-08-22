package com.anecoz.tdx.utils;

import com.anecoz.tdx.entities.Turret;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ResourceHandler {
    public static Texture _enemyTexture;
    public static Texture _dmgTurretTexture;
    public static Texture _slowTurretTexture;
    public static Texture _crossTexture;

    public static void init() {
        _enemyTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        _dmgTurretTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        _slowTurretTexture = new Texture(Gdx.files.internal("droplet.png"));
        _crossTexture = new Texture(Gdx.files.internal("cross.png"));
    }

    static public Texture getTurretTexture(int turretType) {
        switch (turretType) {
            case Turret.DAMAGE_TURRET_TYPE:
                return _dmgTurretTexture;
            case Turret.SLOW_TURRET_TYPE:
                return _slowTurretTexture;
            default:
                break;
        }
        return null;
    }
}
