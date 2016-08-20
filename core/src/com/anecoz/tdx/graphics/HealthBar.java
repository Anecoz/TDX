package com.anecoz.tdx.graphics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class HealthBar {
    private ShapeRenderer _shapeRenderer;
    private Vector2 _ownerPosition; //< pointer to owners pos!
    private float _barWidth;
    private float _barHeight;
    private float _ownerWidth;
    private float _ownerHeight;

    public HealthBar(Vector2 positionPointer, float ownerWidth, float ownerHeight) {
        _shapeRenderer = new ShapeRenderer();
        _ownerPosition = positionPointer;
        _barWidth = 0.7f;
        _barHeight = 0.1f;
        _ownerWidth = ownerWidth;
        _ownerHeight = ownerHeight;
    }

    public void render(SpriteBatch batch, float health) {
        batch.end();
        _shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // BACKGROUND RECT (RED)
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.RED);
        _shapeRenderer.rect(
                _ownerPosition.x + _ownerWidth/2.0f - _barWidth/2.0f,
                _ownerPosition.y + _ownerHeight/2.0f - _barHeight/2.0f + 0.2f,
                _barWidth, _barHeight);
        _shapeRenderer.end();

        // FOREGROUND RECT (GREEN)
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.GREEN);
        _shapeRenderer.rect(
                _ownerPosition.x + _ownerWidth/2.0f - _barWidth/2.0f,
                _ownerPosition.y + _ownerHeight/2.0f - _barHeight/2.0f + 0.2f,
                _barWidth*(health/100f), _barHeight);
        _shapeRenderer.end();
        batch.begin();
    }
}
