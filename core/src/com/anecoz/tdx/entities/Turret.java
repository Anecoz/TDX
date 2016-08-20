package com.anecoz.tdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Turret {

    private Texture _texture;
    private Vector2 _position;

    public Turret(Texture texture, Vector2 position) {
        _texture = texture;
        _position = position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(_texture, _position.x, _position.y, 1.0f, 1.0f);
    }
}
