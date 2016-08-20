package com.anecoz.tdx.logic;

import com.badlogic.gdx.Gdx;
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
        _font = new BitmapFont(Gdx.files.internal("calibri.fnt"), Gdx.files.internal("calibri.png"), false);
        _font.getData().setScale(0.25f);

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
        _font.draw(_batch, "Money: " + _cash, 210, 278);
        _batch.end();
    }
}
