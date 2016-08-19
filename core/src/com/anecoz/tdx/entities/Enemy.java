package com.anecoz.tdx.entities;

import com.anecoz.tdx.level.Path;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

    private Vector2 _position;
    private Texture _texture;
    private Vector2 _velocity;
    private float _size;
    private Rectangle _boundingBox;
    private Path _path;
    private float _speed = 1.5f;

    private Vector2 _currentGoalPos;

    public Enemy(Vector2 startPos, Texture texture, Path path, float size) {
        _position = startPos;
        _texture = texture;
        _velocity = new Vector2(0f, -0.05f);
        _size = size;
        _path = path;

        _currentGoalPos = _path.getNextPosFrom((int)Math.floor(_position.x), (int)Math.floor(_position.y));
        Vector2 dir = new Vector2(_currentGoalPos.x - _position.x, _currentGoalPos.y - _position.y).nor();
        _velocity.x = _speed * dir.x;
        _velocity.y = _speed * dir.y;
    }

    private void updateVelocity() {
        // Have we reached close enough to goal pos?
        if (Math.abs(_position.x - _currentGoalPos.x) <= 0.1 && Math.abs(_position.y - _currentGoalPos.y) <= 0.1) {
            _currentGoalPos = _path.getNextPosFrom((int)Math.floor(_position.x + _size/2.0f), (int)Math.floor(_position.y + _size/2.0f));
            Vector2 dir = new Vector2(_currentGoalPos.x - _position.x, _currentGoalPos.y - _position.y).nor();
            _velocity.x = _speed * dir.x;
            _velocity.y = _speed * dir.y;
        }

        // Have we reached the middle of the tile we're on yet?
        /*float middleTileX = (float)Math.floor(_position.x) + 0.5f;
        float middleTileY = (float)Math.floor(_position.y) + 0.5f;
        float ourMiddleX = _position.x + _size/2.0f;
        float ourMiddleY = _position.y + _size/2.0f;

        Vector2 direction;
        if (Math.abs(middleTileX - ourMiddleX) <= 0.3 && Math.abs(middleTileY - ourMiddleY) <= 0.3) {
            direction = _path.getNextPosFrom((int)Math.floor(_position.x), (int)Math.floor(_position.y));
            direction = direction.sub(_position.x, _position.y).nor();
            _velocity.x = _speed * direction.x;
            _velocity.y = _speed * direction.y;
        }
        else {
            // Steer towards middle of tile
            //direction = new Vector2(middleTileX - _position.x, middleTileY - _position.y).nor();
        }*/
    }

    public void tick() {
        updateVelocity();
        _position.x += _velocity.x * Gdx.graphics.getDeltaTime();
        _position.y += _velocity.y * Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch batch) {
        batch.draw(_texture, _position.x, _position.y, _size, _size);
    }

    public void setSize(float size) {
        _size = size;
    }

    public float getSize() {
        return _size;
    }

}
