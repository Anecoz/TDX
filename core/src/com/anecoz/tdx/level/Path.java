package com.anecoz.tdx.level;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Path {

    // path data is from start to end, for instance
    // [(startX, startY) (node1x, node1y) ... (goalX, goalY)]
    private ArrayList<Vector2> _pathData;

    public Path(ArrayList<Vector2> pathData) {
        _pathData = pathData;
    }

    public boolean isLastPos(int x, int y) {
        Vector2 node = _pathData.get(_pathData.size() - 1);
        return (int)node.x == x && (int)node.y == y;
    }

    public Vector2 getNextPosFrom(int x, int y) {
        for (Vector2 node : _pathData) {
            if ((int)node.x == x && (int)node.y == y) {

                int index = _pathData.indexOf(node);
                if (index != (_pathData.size() - 1))
                    return new Vector2(_pathData.get(_pathData.indexOf(node) + 1));
                else
                    return node;
            }
        }
        // Shouldn't happen
        return null;
    }
}
