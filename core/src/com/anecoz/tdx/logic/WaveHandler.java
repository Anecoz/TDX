package com.anecoz.tdx.logic;


import com.anecoz.tdx.entities.Enemy;
import com.anecoz.tdx.entities.EntityHandler;
import com.anecoz.tdx.level.Level;
import com.anecoz.tdx.utils.ResourceHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class WaveHandler {

    private static final int ONGOING_WAVE_STATE = 0;
    private static final int COOLDOWN_STATE = 1;

    private float _cooldownTime;        //< cooldown between waves in seconds
    private float _timeLeft;            //< time left until wave starts
    private int _enemiesLeft;           //< enemies left in wave
    private float _enemySpawnCooldown;  //< cooldown between each spawn in the wave
    private int _currentWave;           //< wave number, use to spawn correct no of enemies
    private int _state;

    private long _coolDownStartTime;
    private long _lastEnemySpawnTime = -1;
    private ArrayList<Enemy> _waveEnemies;

    private Level _level;

    // For rendering text
    private BitmapFont _font;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;

    public WaveHandler(Level level) {
        _level = level;

        _cooldownTime = 5f;
        _enemySpawnCooldown = 1.f;
        _currentWave = 1;
        _state = COOLDOWN_STATE;
        _coolDownStartTime = TimeUtils.nanoTime();
        _timeLeft = _cooldownTime;

        _waveEnemies = new ArrayList<Enemy>();

        _font = new BitmapFont(Gdx.files.internal("calibri.fnt"), Gdx.files.internal("calibri.png"), false);
        _font.getData().setScale(0.25f);
        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, 300, 300);
        _batch = new SpriteBatch();
    }

    private void startNewWave() {
        for (int i = 0; i < _currentWave; i++) {
            _waveEnemies.add(new Enemy(new Vector2(_level.getStartTile()), ResourceHandler._enemyTexture, _level.getPath(), 0.3f));
        }
        _enemiesLeft = _currentWave;
    }

    public void tick() {
        long currentTime = TimeUtils.nanoTime();
        if (_state == COOLDOWN_STATE) {
            // Get cooldown left
            long waveCooldownDelta = currentTime - _coolDownStartTime;
            if (waveCooldownDelta >= (_cooldownTime * 1000000000)) {
                _state = ONGOING_WAVE_STATE;
                startNewWave();
            }
            else {
                _timeLeft = _cooldownTime - (float)waveCooldownDelta/1000000000f;
            }
        }
        else if (_state == ONGOING_WAVE_STATE) {
            _enemiesLeft = EntityHandler._enemies.size() + _waveEnemies.size();
            if (_enemiesLeft <= 0) {
                _coolDownStartTime = currentTime;
                _state = COOLDOWN_STATE;
                _currentWave++;
            }
            // Spawn new ones
            else if (_lastEnemySpawnTime == -1 || (currentTime - _lastEnemySpawnTime >= (_enemySpawnCooldown * 1000000000))) {
                if (_waveEnemies.size() > 0) {
                    EntityHandler._enemies.add(_waveEnemies.get(_waveEnemies.size() - 1));
                    _waveEnemies.remove(_waveEnemies.size() - 1);
                    _lastEnemySpawnTime = currentTime;
                }
            }
        }
    }

    public void render() {
        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        int x = 100;
        int y = 278;
        String text = null;
        _batch.begin();
        if (_state == COOLDOWN_STATE) {
            text = "Time left: " + (int)_timeLeft;
        }
        else if (_state == ONGOING_WAVE_STATE) {
            text = "Enemies: " + _enemiesLeft;
        }
        _font.draw(_batch, text, x, y);
        _batch.end();
    }
}
