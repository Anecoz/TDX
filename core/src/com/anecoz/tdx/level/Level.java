package com.anecoz.tdx.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

// Abstraction for the level, i.e. tiles etc
public class Level {

    // How many tiles in x/y-direction
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;

    private final static String COLLISION_PROPERTY = "isCollision";
    private final static String START_TILE_PROPERTY = "isStartTile";
    private final static String END_TILE_PROPERTY = "isEndTile";
    private final static String WALKABLE_PROPERTY = "isWalkable";

    private OrthogonalTiledMapRenderer _mapRenderer;
    private TiledMapTileLayer _tileLayer;
    private Path _path;

    public Level(String mapName, SpriteBatch batch) {
        TiledMap map = new TmxMapLoader().load(mapName);
        _tileLayer = (TiledMapTileLayer)map.getLayers().get(0);
        MAP_WIDTH = _tileLayer.getWidth();
        MAP_HEIGHT = _tileLayer.getHeight();

        _mapRenderer = new OrthogonalTiledMapRenderer(map, 1/64f, batch);

        Pathfinder pathFinder = new Pathfinder(this);
        _path = pathFinder.findPath();
    }

    public Path getPath() {
        return _path;
    }

    public Vector2 getEndTile() {
        for (int x = 0; x < _tileLayer.getWidth(); x++)
            for (int y = 0; y < _tileLayer.getHeight(); y++) {
                if (isEndTile(x, y))
                    return new Vector2(x, y);
            }
        return null;
    }

    public Vector2 getStartTile() {
        for (int x = 0; x < _tileLayer.getWidth(); x++)
            for (int y = 0; y < _tileLayer.getHeight(); y++) {
                if (isStartTile(x, y))
                    return new Vector2(x, y);
            }
        return null;
    }

    public ArrayList<Vector2> getNeighboursPosAt(int x, int y) {
        ArrayList<Vector2> output = new ArrayList<Vector2>();

        if (isTileWalkableAt(x - 1, y)) output.add(new Vector2(x - 1, y));
        if (isTileWalkableAt(x, y + 1)) output.add(new Vector2(x, y + 1));
        if (isTileWalkableAt(x, y - 1)) output.add(new Vector2(x, y - 1));
        if (isTileWalkableAt(x + 1, y)) output.add(new Vector2(x + 1, y));

        return output;
    }

    public void render(OrthographicCamera camera) {
        _mapRenderer.setView(camera);
        _mapRenderer.renderTileLayer(_tileLayer);
    }

    private boolean isProperty(String prop, int x, int y) {
        if (_tileLayer.getCell(x, y) == null)
            return false;

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
