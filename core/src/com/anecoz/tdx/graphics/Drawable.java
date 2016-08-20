package com.anecoz.tdx.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Drawable {

    protected float _rotation = 0.0f;
    protected Vector2 _position;
    protected Texture _texture;
    protected float _size;

    public Drawable(Texture texture, Vector2 position, float size) {
        _texture = texture;
        _position = position;
        _size = size;
    }

    public Vector2 getPosition() {
        return _position;
    }

    public void setPosition(Vector2 pos) {
        _position = pos;
    }

    public Texture getTexture() {
        return _texture;
    }

    public float distanceTo(Vector2 compare) {
        return _position.dst(compare);
    }

    public void render(SpriteBatch batch) {
        batch.draw(_texture,
                _position.x, _position.y,
                _size/2.0f, _size/2.0f,
                _size, _size,
                1.0f, 1.0f,
                _rotation,
                0, 0,
                _texture.getWidth(), _texture.getHeight(),
                false, false);
    }
}
