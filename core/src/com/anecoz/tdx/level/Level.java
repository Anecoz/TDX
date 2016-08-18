package com.anecoz.tdx.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

// Abstraction for the level, i.e. tiles etc
public class Level {

    // How many tiles in x/y-direction
    public final static int MAP_WIDTH = 10;
    public final static int MAP_HEIGHT = 5;

    private final static String COLLISION_PROPERTY = "isCollision";
    private final static String START_TILE_PROPERTY = "isStartTile";
    private final static String END_TILE_PROPERTY = "isEndTile";
    private final static String WALKABLE_PROPERTY = "isWalkable";

    private OrthogonalTiledMapRenderer _mapRenderer;
    private TiledMap _map;
    private TiledMapTileLayer _tileLayer;

    public Level(String mapName) {
        _map = new TmxMapLoader().load(mapName);
        _tileLayer = (TiledMapTileLayer)_map.getLayers().get(0);

        _mapRenderer = new OrthogonalTiledMapRenderer(_map, 1/64f);
    }

    public void render(OrthographicCamera camera) {
        _mapRenderer.setView(camera);
        _mapRenderer.render();
    }

    private boolean isProperty(String prop, int x, int y) {
        return _tileLayer.getCell(x, y)
                .getTile()
                .getProperties()
                .get(prop, String.class) != null;
    }

    public boolean isStartTile(int x, int y) {
        return isProperty(START_TILE_PROPERTY, x, y);
    }

    public boolean isEndTile(int x, int y) {
        return isProperty(END_TILE_PROPERTY, x, y);
    }

    public boolean isTileBuildableAt(int x, int y) {
        return isProperty(COLLISION_PROPERTY, x, y);
    }

    public boolean isTileWalkableAt(int x, int y) {
        return isProperty(WALKABLE_PROPERTY, x, y);
    }
}
