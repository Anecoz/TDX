package com.anecoz.tdx.logic;

import com.anecoz.tdx.entities.EntityHandler;
import com.anecoz.tdx.entities.Player;
import com.anecoz.tdx.entities.Turret;
import com.anecoz.tdx.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TurretPicker {

    HashMap<Vector2, Integer> _turretMap;
    private float _size;
    private boolean _hasSelection;
    private int _selectedTurretType;
    private Player _player;

    public TurretPicker(Player player) {
        _turretMap = new HashMap<Vector2, Integer>();
        _player = player;

        _turretMap.put(new Vector2(0, Level.MAP_HEIGHT), Turret.DAMAGE_TURRET_TYPE);
        _turretMap.put(new Vector2(1, Level.MAP_HEIGHT), Turret.SLOW_TURRET_TYPE);
        _size = 0.7f;
        _hasSelection = false;
        _selectedTurretType = -1;
    }

    public boolean hasSelection() {
        return _hasSelection;
    }

    public void clearSelection() {
        _hasSelection = false;
        _selectedTurretType = -1;
    }

    public int getSelectedTurretType() {
        return _selectedTurretType;
    }

    private int getSelectedTurretType(Vector2 pos) {
        Iterator it = _turretMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Vector2 currPos = (Vector2)pair.getKey();
            if ((int)currPos.x == (int)pos.x && (int)currPos.y == (int)pos.y)
                return (Integer)pair.getValue();
        }
        return -1;
    }

    public void tick(OrthographicCamera camera) {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            int selectedTurretType = getSelectedTurretType(new Vector2(touchPos.x, touchPos.y));
            if (!_hasSelection) {
                if (selectedTurretType != -1 && _player.getMoney().canAfford(Turret.getCostFromType(selectedTurretType))) {
                    _hasSelection = true;
                    _selectedTurretType = selectedTurretType;
                }
            }
            else {
                // Trying to deselect?
                if (selectedTurretType == _selectedTurretType)
                    clearSelection();
            }
        }
    }

    public void render(SpriteBatch batch) {
        Iterator it = _turretMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int currTurrType = (Integer)pair.getValue();
            Vector2 currPos = (Vector2)pair.getKey();
            if (_hasSelection && currTurrType == _selectedTurretType) {
                float tmpSize = _size*1.3f;
                batch.draw(EntityHandler.getTurretTexture(currTurrType),
                        currPos.x + 0.5f - tmpSize/2.0f,
                        currPos.y + 0.5f - tmpSize/2.0f,
                        tmpSize, tmpSize);
            }
            else {
                batch.draw(EntityHandler.getTurretTexture(currTurrType),
                        currPos.x + 0.5f - _size/2.0f,
                        currPos.y + 0.5f - _size/2.0f,
                        _size, _size);

                if (!_player.getMoney().canAfford(Turret.getCostFromType(currTurrType))) {
                    // Draw cross over
                    batch.draw(EntityHandler._crossTexture,
                            currPos.x, currPos.y,
                            1.0f, 1.0f);
                }
            }
        }
    }
}
