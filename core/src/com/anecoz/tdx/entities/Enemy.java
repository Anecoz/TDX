package com.anecoz.tdx.entities;

import com.anecoz.tdx.entities.debuffs.Debuff;
import com.anecoz.tdx.graphics.Drawable;
import com.anecoz.tdx.graphics.HealthBar;
import com.anecoz.tdx.level.Path;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Enemy extends Drawable {

    private Vector2 _velocity;
    private float _baseSpeed = 1.0f;
    private float _speed;
    private Vector2 _offset;
    private Vector2 _currentGoalPos;

    private float _health;
    private HealthBar _healthBar;

    private Path _path;

    private boolean _dead = false;

    private ArrayList<Debuff> _debuffs;

    public Enemy(Vector2 startPos, Texture texture, Path path, float size) {
        super(texture, startPos, size);
        _velocity = new Vector2(0f, 0f);
        _path = path;
        _speed = _baseSpeed;
        _debuffs = new ArrayList<Debuff>();

        _currentGoalPos = _path.getNextPosFrom((int)Math.floor(_position.x), (int)Math.floor(_position.y));

        _health = 100f;
        _healthBar = new HealthBar(_position, _size, _size);

        // Calculate an offset that this particular enemy has in relation to the path
        Random rand = new Random();
        _offset = new Vector2();
        _offset.x = (1 - _size) * rand.nextFloat(); //< Random float between 0 and 1 - size
        _offset.y = (1 - _size) * rand.nextFloat();
        _position.x += _offset.x;
        _position.y += _offset.y;
        _currentGoalPos.x += _offset.x;
        _currentGoalPos.y += _offset.y;

        Vector2 dir = new Vector2(_currentGoalPos.x - _position.x, _currentGoalPos.y - _position.y).nor();
        _velocity.x = _speed * dir.x;
        _velocity.y = _speed * dir.y;
    }

    public void addDebuff(Debuff debuff) {
        debuff.apply(this);
        _debuffs.add(debuff);
    }

    public float getBaseSpeed() {return _baseSpeed;}

    public void setSpeed(float speed) {
        _speed = speed;
    }

    public boolean isDead() {
        return _dead;
    }

    public void takeDamage(float damage) {
        _health -= damage;
        if (_health <= 0)
            _dead = true;
    }

    private void updateVelocity() {
        // Have we reached close enough to goal pos?
        if (Math.abs(_position.x - _currentGoalPos.x) <= 0.1 && Math.abs(_position.y - _currentGoalPos.y) <= 0.1) {
            if (_path.isLastPos((int)Math.floor(_position.x), (int)Math.floor(_position.y))) {
                _dead = true;
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

    private void updateDebuffs() {
        Iterator it = _debuffs.iterator();
        while (it.hasNext()) {
            Debuff debuff = (Debuff)it.next();
            debuff.tick();
            if (debuff.isDead()) {
                debuff.unapply(this);
                it.remove();
            }
        }
    }

    public void tick() {
        if (!_dead) {
            updateDebuffs();
            updateVelocity();
            _position.x += _velocity.x * Gdx.graphics.getDeltaTime();
            _position.y += _velocity.y * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!_dead) {
            super.render(batch);
            _healthBar.render(batch, _health);
        }
    }
}
