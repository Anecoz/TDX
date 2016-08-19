package com.anecoz.tdx.entities;

import com.anecoz.tdx.level.Path;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Enemy {

    private Vector2 _position;
    private Vector2 _velocity;
    private float _speed = 1.6f;
    private Vector2 _offset;
    private Vector2 _currentGoalPos;

    private Texture _texture;
    private float _size;

    private Rectangle _boundingBox;
    private Path _path;

    private boolean dead = false;

    public Enemy(Vector2 startPos, Texture texture, Path path, float size) {
        _position = new Vector2(startPos);
        _texture = texture;
        _velocity = new Vector2(0f, 0f);
        _size = size;
        _path = path;

        _currentGoalPos = _path.getNextPosFrom((int)Math.floor(_position.x), (int)Math.floor(_position.y));

        // Calculate an offset that this particular enemy has in relation to the path
        Random rand = new Random();
        _offset = new Vector2();
        _offset.x = (1 - _size) * rand.nextFloat(); // Random float between 0 and 1 - size
        _offset.y = (1 - _size) * rand.nextFloat();
        _position.x += _offset.x;
        _position.y += _offset.y;
        _currentGoalPos.x += _offset.x;
        _currentGoalPos.y += _offset.y;

        Vector2 dir = new Vector2(_currentGoalPos.x - _position.x, _currentGoalPos.y - _position.y).nor();
        _velocity.x = _speed * dir.x;
        _velocity.y = _speed * dir.y;
    }

    public boolean isDead() {
        return dead;
    }

    private void updateVelocity() {
        // Have we reached close enough to goal pos?
        if (Math.abs(_position.x - _currentGoalPos.x) <= 0.1 && Math.abs(_position.y - _currentGoalPos.y) <= 0.1) {
            if (_path.isLastPos((int)Math.floor(_position.x), (int)Math.floor(_position.y))) {
                dead = true;
                return;
            }
            _currentGoalPos = _path.getNextPosFrom((int)Math.floor(_position.x), (int)Math.floor(_position.y));
            _currentGoalPos.x += _offset.x;
            _currentGoalPos.y += _offset.y;
            Vector2 dir = new Vector2(_currentGoalPos.x - _position.x, _currentGoalPos.y - _position.y).nor();
            _velocity.x = _speed * dir.x;
            _velocity.y = _speed * dir.y;
        }
    }

    public void tick() {
        if (!dead) {
            updateVelocity();
            _position.x += _velocity.x * Gdx.graphics.getDeltaTime();
            _position.y += _velocity.y * Gdx.graphics.getDeltaTime();
        }
    }

    public void render(SpriteBatch batch) {
        if (!dead)
            batch.draw(_texture, _position.x, _position.y, _size, _size);
    }

    public void setSize(float size) {
        _size = size;
    }

    public float getSize() {
        return _size;
    }

}
