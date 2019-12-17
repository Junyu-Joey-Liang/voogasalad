package voogasalad.gameengine.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.controller.mapbuilder.MapElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPath {

    private GameMap map;
    private MapElementType[][] grid;
    @BeforeEach
    void setUp(){
        //map = new GameMap(grid, null);
    }

    @Test
    void simpleMap(){
        grid = new MapElementType[][]{
                {MapElementType.SPAWN, MapElementType.PATH_HORIZONTAL, MapElementType.END},
                {MapElementType.BUILDABLE, MapElementType.BUILDABLE, MapElementType.BUILDABLE}
        };
        map = new GameMap(grid, null);
        List<List<Point2D.Double>> paths = map.getMapPath().getPathsToEndPoint();
        List<Point2D.Double> truePathOne = new ArrayList<>();
        truePathOne.add(new Point2D.Double(0.0, 0.0));
        truePathOne.add(new Point2D.Double(0.0, 1.0));
        truePathOne.add(new Point2D.Double(0.0, 2.0));
        int trueSizeOfPath = 1;
        assertEquals(trueSizeOfPath, paths.size());
        assertEquals(truePathOne, paths.get(0));
    }

    @Test
    void mapWithJunction(){
        grid = new MapElementType[][]{
                {MapElementType.SPAWN, MapElementType.PATH_L_SW, MapElementType.BUILDABLE, MapElementType.BUILDABLE},
                {MapElementType.BUILDABLE, MapElementType.PATH_T_RIGHT, MapElementType.END, MapElementType.BUILDABLE},
                {MapElementType.BUILDABLE, MapElementType.PATH_L_NE, MapElementType.PATH_L_NW, MapElementType.BUILDABLE}
        };
        map = new GameMap(grid, null);
        List<List<Point2D.Double>> paths = map.getMapPath().getPathsToEndPoint();
        List<List<Point2D.Double>> truePath = new ArrayList<>();
        List<Point2D.Double> truePathOne = new ArrayList<>();
        truePathOne.add(new Point2D.Double(0.0, 0.0));
        truePathOne.add(new Point2D.Double(0.0, 1.0));
        truePathOne.add(new Point2D.Double(1.0, 1.0));
        truePathOne.add(new Point2D.Double(1.0, 2.0));
        truePath.add(truePathOne);

        List<Point2D.Double> truePathTwo = new ArrayList<>();
        truePathTwo.add(new Point2D.Double(0.0, 0.0));
        truePathTwo.add(new Point2D.Double(0.0, 1.0));
        truePathTwo.add(new Point2D.Double(1.0, 1.0));
        truePathTwo.add(new Point2D.Double(2.0, 1.0));
        truePathTwo.add(new Point2D.Double(2.0, 2.0));
        truePathTwo.add(new Point2D.Double(1.0, 2.0));
        truePath.add(truePathTwo);

        int trueSizeOfPath = 2;
        assertEquals(trueSizeOfPath, paths.size());
        assertTrue(paths.contains(truePathOne));
        assertTrue(paths.contains(truePathTwo));
    }

    @Test
    void mapWithJunctions(){
        grid = new MapElementType[][]{
                {MapElementType.SPAWN, MapElementType.PATH_T_BOTTOM, MapElementType.PATH_L_SW},
                {MapElementType.BUILDABLE, MapElementType.PATH_T_RIGHT, MapElementType.END},
                {MapElementType.BUILDABLE, MapElementType.PATH_L_NE, MapElementType.PATH_L_NW}
        };

        map = new GameMap(grid, null);
        List<List<Point2D.Double>> paths = map.getMapPath().getPathsToEndPoint();
        List<Point2D.Double> truePathOne = new ArrayList<>();
        List<List<Point2D.Double>> truePath = new ArrayList<>();
        truePathOne.add(new Point2D.Double(0.0, 0.0));
        truePathOne.add(new Point2D.Double(0.0, 1.0));
        truePathOne.add(new Point2D.Double(0.0, 2.0));
        truePathOne.add(new Point2D.Double(1.0, 2.0));

        List<Point2D.Double> truePathTwo = new ArrayList<>();
        truePathTwo.add(new Point2D.Double(0.0, 0.0));
        truePathTwo.add(new Point2D.Double(0.0, 1.0));
        truePathTwo.add(new Point2D.Double(1.0, 1.0));
        truePathTwo.add(new Point2D.Double(1.0, 2.0));

        List<Point2D.Double> truePathThree = new ArrayList<>();
        truePathThree.add(new Point2D.Double(0.0, 0.0));
        truePathThree.add(new Point2D.Double(0.0, 1.0));
        truePathThree.add(new Point2D.Double(1.0, 1.0));
        truePathThree.add(new Point2D.Double(2.0, 1.0));
        truePathThree.add(new Point2D.Double(2.0, 2.0));
        truePathThree.add(new Point2D.Double(1.0, 2.0));
        truePath.add(truePathOne);
        truePath.add(truePathThree);
        truePath.add(truePathTwo);

        int trueSizeOfPath = 3;

        assertEquals(trueSizeOfPath, paths.size());
        assertTrue(paths.contains(truePathOne));
        assertTrue(paths.contains(truePathTwo));
        assertTrue(paths.contains(truePathThree));
    }


}
