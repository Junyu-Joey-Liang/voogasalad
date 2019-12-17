package voogasalad.gameengine.map;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.utilities.Element;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import voogasalad.utilities.frontEndCommands.initialization.AddMapTileCommand;
import voogasalad.utilities.gameElements.Attacker;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Holds the game map for each level
 */
public class GameMap {
    public static final int FRAMES_PER_SECOND = 60;
    private static final int DEFAULT_SPEED = 5;
    public static final String DEFENDER = "defender";
    private static final String OBSTACLE = "Obstacle";
    private MapPath mapPath;
    private List<Point2D.Double> spawnPoint;
    private MapElementType[][] grid;
    private Map<MapElementType,String> mapImage;
    private FindValues obstacleBundle;
    private static final String OBSTACLE_FILE_PATH= "voogasalad.gameengine.map.mapResources/obstaclePlace";


    /**
     * constructor for GameMap
     * @param grid holds the enum types for each cell in the map
     * @param mapImage maps the enum type with the filepath to find the image that is associated with the enum type
     */
    public GameMap(MapElementType[][] grid, Map<MapElementType,String> mapImage){
        obstacleBundle = new FindValues(OBSTACLE_FILE_PATH);
        this.mapImage = mapImage;
        mapPath = new MapPath(grid);
        spawnPoint = mapPath.getSpawnPoint();
        makeGrid(grid);
    }

    /**
     *
     * @return the MapPath object that holds all the possible routes in the map
     */
    public MapPath getMapPath(){
        return this.mapPath;
    }

    /**
     *
     * @return the width of the grid
     */
    public int gridWidth(){
        return grid.length;
    }

    /**
     *
     * @return the height of the grid
     */
    public int gridHeight(){
        return grid[0].length;
    }

    /**
     * initializes the backend
     * @return list of associated front end commands that initializes the front end
     */
    public List<FrontEndCommand> initialize(){
        List<FrontEndCommand> list = new ArrayList<>();
        for(int i =0; i<grid.length;i++){
            for(int j = 0; j< grid[0].length;j++){
                list.add(new AddMapTileCommand(mapImage.get(grid[i][j]),i,j));
            }
        }
        return list;
    }

    /**
     * tests is a game object of elementType can be placed at a certain location
     * @param elementType type of game element
     * @param location location to be placed
     * @return true if can place, false if not compatible
     */
    public boolean canPlace(String elementType, Point2D.Double location){
        int x = convertToAuthoringNum(location.getX());
        int y = convertToAuthoringNum(location.getY());
        String mapElementType = grid[x][y].getDisplayName();
        if(elementType.equals(DEFENDER) && mapElementType.equals(MapElementType.BUILDABLE.getDisplayName())){
            return true;
        }
        else if(elementType.equals(OBSTACLE) && obstacleBundle.contains(mapElementType)){
            return true;
        }
        return false;
    }

    /**
     * finds the oldest element in the game
     * @param attackers list of elements to check which one is oldest
     * @return the attackerID that represents the oldest element
     */
    public int findOldestElement(List<Element> attackers){
        int min = Integer.MAX_VALUE;
        Element oldest = null;
        for(Element attacker: attackers){
            int temp = ((Attacker) attacker).getPath().size()/((Attacker) attacker).getSpeed();
            if(((Attacker) attacker).getPath().size() > 0 && temp < min){
                min = temp;
                oldest = attacker;
            }
        }
        return oldest == null ? -1 : oldest.getId();
    }

    /**
     * @return list of spawn points
     */
    public List<Point2D.Double> getSpawnPoint(){
        return new ArrayList<>(spawnPoint);
    }

    /**
     * @return list of end points
     */
    public List<Point2D.Double> getEndPoint(){return mapPath.getEndPoint();}

    /**
     * assigns each attacker a path for it to travel on
     * @param speed of the attacker
     * @return the queue of points for the attacker to travel
     */
    public Queue<Point2D.Double> makeElementPath(double speed){
        if(speed < 0){
            speed = DEFAULT_SPEED;

        }
        double multiplier = FRAMES_PER_SECOND / speed;
        Queue<Point2D.Double> linkedList = new LinkedList<>();
        Random rand = new Random();
        int path = rand.nextInt(mapPath.getPathsToEndPoint().size());
        for(int j = 0; j < mapPath.getPathsToEndPoint().get(path).size()-1;j++){
            int deltaY = (int) (mapPath.getPathsToEndPoint().get(path).get(j+1).getY() - mapPath.getPathsToEndPoint().get(path).get(j).getY());
            int deltax = (int) (mapPath.getPathsToEndPoint().get(path).get(j+1).getX() - mapPath.getPathsToEndPoint().get(path).get(j).getX());
            for(int i=0;i<multiplier;i++){
                linkedList.add(new Point2D.Double(
                        mapPath.getPathsToEndPoint().get(path).get(j).getX() + (i*deltax/multiplier),
                        mapPath.getPathsToEndPoint().get(path).get(j).getY() + (i*deltaY/multiplier)
                ));
            }
        }
        return linkedList;
    }


    /**
     * converts the backend coordinates to front end coordinates
     * @param i
     * @return
     */
    private int convertToAuthoringNum(double i){
        return (int)i;
    }

    /**
     * creates a copy of the grid passed in from the constructor
     * @param copy grid to be copied
     */
    private void makeGrid(MapElementType[][] copy){
        this.grid = new MapElementType[copy.length][copy[copy.length - 1].length];
        for(int row = 0; row < copy.length; row++){
            for(int col = 0; col < copy[row].length; col++){
                this.grid[row][col] = copy[row][col];
            }
        }
    }


}
