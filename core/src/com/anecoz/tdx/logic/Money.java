package com.anecoz.tdx.logic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Money {
    private int _cash;
    private BitmapFont _font;
    private OrthographicCamera _camera;
    private SpriteBatch _batch;

    public Money() {
        _cash = 10;
        _font = new BitmapFont(); //< uses default arial font

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, 300, 300);

        _batch = new SpriteBatch();
    }

    public boolean canAfford(int amount) {
        return _cash >= amount;
    }

    public void addCash(int amount) {
        _cash += amount;
    }

    public int getCash() {
        return _cash;
    }

    public void deductCash(int amount) {
        _cash -= amount;
    }

    public void render() {
        _camera.update();
        _batch.setProjectionMatrix(_camera.combined);
        _batch.begin();
        _font.draw(_batch, "Money: " + _cash, 220, 280);
        _batch.end();
    }
}
