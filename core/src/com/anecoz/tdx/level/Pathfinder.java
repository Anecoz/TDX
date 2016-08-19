package com.anecoz.tdx.level;


import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Stack;

public class Pathfinder {

    private Level _level;

    public Pathfinder(Level level) {
        _level = level;
    }

    public Path findPath() {
        // Assume a simple level where there is only one path and each node on path
        // is only connected to a single neighbour forward
        Vector2 goalPos = _level.getEndTile();
        Vector2 startPos = _level.getStartTile();

        ArrayList<Vector2> path = new ArrayList<Vector2>();
        path.add(startPos);

        int currX = (int)startPos.x;
        int currY = (int)startPos.y;
        int prevX = -1;
        int prevY = -1;
        boolean done = false;
        while (!done) {
            ArrayList<Vector2> neighboursPos = _level.getNeighboursPosAt(currX, currY);
            for (Vector2 pos : neighboursPos) {
                if (!((int)pos.x == prevX && (int)pos.y == prevY)) {
                    if ((int)pos.x == (int)goalPos.x && (int)pos.y == (int)goalPos.y) {
                        // Found goal
                        path.add(pos);
                        done = true;
                        break;
                    }
                    // Must be continuation on path
                    path.add(pos);
                    prevX = currX;
                    prevY = currY;
                    currX = (int)pos.x;
                    currY = (int)pos.y;
                    break;
                }
                // If we are here, means we found where we came from
            }
        }

        return new Path(path);
    }

    // Start of A* if it is ever needed

    /*private class Node {
        Node parent = null;
        int x, y;
        float f = 0;
        float g = 0;
        float h = 0;
    }*/

    /*public void findPath() {

        Stack<Node> open = new Stack<Node>();
        Stack<Node> closed = new Stack<Node>();

        Vector2 goalPos = _level.getEndTile();

        Vector2 startPos = _level.getStartTile();
        Node start = new Node();
        start.x = (int)startPos.x;
        start.y = (int)startPos.y;
        open.push(start);

        while (!open.empty()) {
            Node q = open.elementAt(findMinimumF(open));
            open.remove(q);

            Node[] successors = new Node[4];
            ArrayList<TiledMapTile> neighbours = _level.getNeighboursAt(q.x, q.y);
            int counter = 0;
            for (TiledMapTile tile : neighbours) {
                Node currNeigh = new Node();
                currNeigh.parent = q;
                currNeigh.

                successors[counter] = currNeigh;
                counter++;
            }
        }
    }

    private int findMinimumF(Stack<Node> open) {
        float currMin = 100000000;
        int currIndex = -1;

        for (Node node : open) {
            if (node.f < currMin) {
                currMin = node.f;
                currIndex = open.indexOf(node);
            }
        }

        return currIndex;
    }*/

}
