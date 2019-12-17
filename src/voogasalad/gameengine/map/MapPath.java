package voogasalad.gameengine.map;


import javafx.util.Pair;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * holds the path that the attackers can travel
 */
public class MapPath implements Serializable {
    private static final String JUNCTIONS_RESOURCE_FILEPATH = "voogasalad.gameengine.map.mapResources/junctions";
    private static final String GO_LEFT_FILEPATH = "voogasalad.gameengine.map.mapResources/goLeft";
    private static final String GO_RIGHT_FILEPATH = "voogasalad.gameengine.map.mapResources/goRight";
    private static final String GO_UP_FILEPATH = "voogasalad.gameengine.map.mapResources/goUp";
    private static final String GO_DOWN_FILEPATH = "voogasalad.gameengine.map.mapResources/goDown";
    private static final String START_END = "voogasalad.gameengine.map.mapResources/startEnd";
    private FindValues junctionsBundle;
    private FindValues upBundle;
    private FindValues downBundle;
    private FindValues rightBundle;
    private FindValues leftBundle;
    private FindValues startEnd;
    private List<Point2D.Double> spawnPoint;
    private List<Point2D.Double> endPoint;
    private static final long serialVersionUID = 0L;
    private Map<Pair<Point2D.Double, Point2D.Double>, List<List<Point2D.Double>>> junctionRelations;
    private List<List<Point2D.Double>> pathsToEndPoint;


    /**
     * constructor for MapPath
     * @param grid of enums that hold what type each cell is
     */
    public MapPath(MapElementType[][] grid){
        junctionsBundle = new FindValues(JUNCTIONS_RESOURCE_FILEPATH);
        upBundle = new FindValues(GO_UP_FILEPATH);
        downBundle = new FindValues(GO_DOWN_FILEPATH);
        rightBundle = new FindValues(GO_RIGHT_FILEPATH);
        leftBundle = new FindValues(GO_LEFT_FILEPATH);
        startEnd = new FindValues(START_END);
        junctionRelations = new HashMap<>();
        pathsToEndPoint = new ArrayList<>();
        makePath(grid);
    }

    /**
     *
     * @return all possible paths from spawn point(s) to end point(s)
     */
    public List<List<Point2D.Double>> getPathsToEndPoint(){
        return new ArrayList<>(pathsToEndPoint);
    }


    /**
     *
     * @return the spawn point(s) of the map
     */
    public List<Point2D.Double> getSpawnPoint(){
        return new ArrayList<>(spawnPoint);
    }

    /**
     *
     * @return list of end points of the map
     */
    public List<Point2D.Double> getEndPoint(){
        return new ArrayList<>(endPoint);
    }


    /**
     * calls the private methods in the correct order to make the paths
     * @param grid holds the enum types
     */
    private void makePath(MapElementType[][] grid){
        findSpawnPointAndEndPoint(grid);
        Set<Point2D.Double> intersections = new HashSet<>();
        findJunctions(grid, intersections);
        makeJunctionGraph(intersections, grid);
        makeAllPossiblePaths();

    }


    /**
     * maps each junction to its connected neighbors(that are also junctions)
     * a junction is the following MapElementTypes: T, Cross, End point, Spawn point because these are the enum types that cause a change in direction
     * @param junctions the coordinates that are junctions in the map
     * @param grid represents the map that the author makes
     */
    private void makeJunctionGraph(Set<Point2D.Double> junctions, MapElementType[][] grid){
       for (Point2D.Double junction : junctions
             ) {
            findNeighbors(junction, grid);
        }
    }

    /**
     * creates paths that connect the connected junctions
     * @param point current point that is being checked
     * @param grid represents the map that the author makes
     */
    private void findNeighbors(Point2D.Double point, MapElementType[][] grid){
        Point2D.Double currPoint = point;
        Boolean[][] visited = new Boolean[grid.length][grid[0].length];
        fillArray(visited);
        visited[(int)currPoint.getX()][(int)currPoint.getY()] = true;
            int currentX = (int)currPoint.getX();
            int currentY = (int)currPoint.getY();
            if(currentX > 0){
                fillArray(visited);
                visited[(int)currPoint.getX()][(int)currPoint.getY()] = true;
                MapElementType abovePathType = grid[currentX-1][currentY];
                findNeighborInDirection(grid, currPoint, visited, currentY, abovePathType, downBundle, currentX - 1);
            }
            if(currentX < grid.length-1){
                fillArray(visited);
                visited[(int)currPoint.getX()][(int)currPoint.getY()] = true;
                MapElementType downPathType = grid[currentX+1][currentY];
                findNeighborInDirection(grid, currPoint, visited, currentY, downPathType, upBundle, currentX+1);

            }
            if(currentY>0){
                fillArray(visited);
                visited[(int)currPoint.getX()][(int)currPoint.getY()] = true;
                MapElementType leftPathType = grid[currentX][currentY-1];
                findNeighborInDirection(grid, currPoint, visited, currentY-1, leftPathType, rightBundle, currentX);

            }
            if(currentY<grid[currentX].length-1){
                fillArray(visited);
                visited[(int)currPoint.getX()][(int)currPoint.getY()] = true;
                MapElementType rightPathType = grid[currentX][currentY+1];
                findNeighborInDirection(grid, currPoint, visited, currentY + 1, rightPathType, leftBundle, currentX);

            }

    }

    /**
     * helper method for findNeighbors
     * @param grid represents the map that the author makes
     * @param currPoint current point that we are checking the neighbors of
     * @param visited grid that holds all the coordinates that have already been visited
     * @param y the new y coordinate
     * @param pathType the direction that we are traveling in
     * @param directionBundle the collection of MapElementTypes that will allow the attacker to move into it from the direction of travel
     * @param x new x coordinate
     */
    private void findNeighborInDirection(MapElementType[][] grid, Point2D.Double currPoint, Boolean[][] visited, int y, MapElementType pathType, FindValues directionBundle, int x) {
        if (directionBundle.contains(pathType.getDisplayName()) || startEnd.contains(pathType.getDisplayName())) {
            List<Point2D.Double> trail = new ArrayList<>();
            trail.add(currPoint);
            makeJunctionRelations(grid, visited, new Point2D.Double(x, y), trail, currPoint);
        }
    }

    /**
     * initializes the array to all false
     * @param visited the array to be filled
     */
    private void fillArray(Boolean[][] visited) {
        for (Boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    /**
     * makes a map of all the connected junctions
     * @param grid represents the authoring environment map
     * @param visited grid of all visited coordinates
     * @param startingPoint the starting junction coorindate
     * @param trail the list of coordinates that connect each junction
     * @param initialPoint the starting junction coordinate
     */
    private void makeJunctionRelations(MapElementType[][] grid, Boolean[][] visited, Point2D.Double startingPoint, List<Point2D.Double> trail, Point2D.Double initialPoint){
        MapElementType val = grid[(int)startingPoint.getX()][(int)startingPoint.getY()];
        visited[(int)startingPoint.getX()][(int)startingPoint.getY()] = true;
        if(val.equals(MapElementType.END)){
            trail.add(startingPoint);
        }
        if(junctionsBundle.contains(val.getDisplayName())){
            Pair<Point2D.Double, Point2D.Double> pair = new Pair<>(initialPoint, startingPoint);
            if(junctionRelations.containsKey(pair)){
                junctionRelations.get(pair).add(new ArrayList<>(trail));
            }
            else{
                junctionRelations.put(pair, new ArrayList<>());
                junctionRelations.get(pair).add(new ArrayList<>(trail));
            }
            return;
        }
        trail.add(startingPoint);
        int x = (int)startingPoint.getX();
        int y = (int)startingPoint.getY();

        if(!val.equals(MapElementType.NOT_BUILDABLE)){
            if(x < grid.length - 1 && !visited[x + 1][y] && downBundle.contains(val.getDisplayName())){
                makeJunctionRelations(grid, visited, new Point2D.Double(x+1, y), trail, initialPoint);
            }
            else if(x > 0 && !visited[x - 1][y] && upBundle.contains(val.getDisplayName())){
                makeJunctionRelations(grid, visited, new Point2D.Double(x - 1, y), trail, initialPoint);
            }
            else if(y > 0 && !visited[x][y - 1] && leftBundle.contains(val.getDisplayName())){
                makeJunctionRelations(grid, visited, new Point2D.Double(x, y - 1), trail, initialPoint);
            }
            else if(y < grid[x].length - 1 && !visited[x][y+1] && rightBundle.contains(val.getDisplayName())){
                makeJunctionRelations(grid, visited, new Point2D.Double(x, y+1), trail, initialPoint);
            }
        }


    }


    /**
     * creates all the possible routes
     */
    private void makeAllPossiblePaths(){
        List<List<Pair<Point2D.Double, Point2D.Double>>> paths = new ArrayList<>();
        for(Map.Entry<Pair<Point2D.Double, Point2D.Double>, List<List<Point2D.Double>>> entry : junctionRelations.entrySet()){
            if(spawnPoint.contains(entry.getKey().getKey()) && !spawnPoint.contains(entry.getKey().getValue())){
                List<Pair<Point2D.Double, Point2D.Double>> route = new ArrayList<>();
                List<Point2D.Double> seen = new ArrayList<>();
                route.add(entry.getKey());
                seen.add(entry.getKey().getKey());
                findPaths(route, entry.getKey().getValue(), paths, seen);
            }
            }

        for(List<Pair<Point2D.Double, Point2D.Double>> path : paths){
            List<Point2D.Double> spawnToEnd = new ArrayList<>();
            makeAllPathsFromSpawnToEnd(path, spawnToEnd);
        }


    }


    /**
     * creates all the possible routes from spawn to end point
     * @param path the path being made
     * @param spawnToEnd list that holds all the paths from spawn to end point
     */
    private void makeAllPathsFromSpawnToEnd(List<Pair<Point2D.Double, Point2D.Double>> path, List<Point2D.Double> spawnToEnd){
        if(path.isEmpty()){
            pathsToEndPoint.add(spawnToEnd);
            return;
        }
        List<List<Point2D.Double>> possiblePaths = junctionRelations.get(path.get(0));
        List<Pair<Point2D.Double, Point2D.Double>> tempPath = new ArrayList<>(path);
        tempPath.remove(0);
        for(List<Point2D.Double> route : possiblePaths){
            List<Point2D.Double> temp = new ArrayList<>(spawnToEnd);
            temp.addAll((route));
            makeAllPathsFromSpawnToEnd(tempPath, temp);
        }

    }


    /**
     * helper method for make all possible paths
     * @param route the current route being made
     * @param findPoint the current point we are looking at
     * @param paths all the paths that connect the two junctions
     * @param seen paths that have already been visited
     */
    private void findPaths(List<Pair<Point2D.Double, Point2D.Double>> route, Point2D.Double findPoint, List<List<Pair<Point2D.Double, Point2D.Double>>> paths, List<Point2D.Double> seen){
        for(Map.Entry<Pair<Point2D.Double, Point2D.Double>, List<List<Point2D.Double>>> entry : junctionRelations.entrySet()){
            List<Pair<Point2D.Double, Point2D.Double>> tempRoute = new ArrayList<>(route);
            if(!spawnPoint.contains(entry.getKey().getKey()) && !spawnPoint.contains(entry.getKey().getValue()) && entry.getKey().getKey().equals(findPoint)
                    && !seen.contains(entry.getKey().getValue()) && !seen.contains(findPoint)){
                List<Point2D.Double> tempSeen = new ArrayList<>(seen);
                tempSeen.add(findPoint);
                if(endPoint.contains(entry.getKey().getKey())){
                    return;
                }
                else{
                    tempRoute.add(entry.getKey());
                    findPaths(tempRoute, entry.getKey().getValue(), paths, tempSeen);
                }
                if(endPoint.contains(tempRoute.get(tempRoute.size()-1).getValue())){
                    paths.add(new ArrayList<>(tempRoute));
                }
            }
            else if(endPoint.contains(findPoint) && route.size() == 1){
                paths.add(new ArrayList<>(tempRoute));
                return;
            }

        }
    }


    /**
     * finds all the junctions in the grid
     * @param grid represents authoring environment map
     * @param intersections holds all the junctions
     */
    private void findJunctions(MapElementType[][] grid, Set<Point2D.Double> intersections){
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[row].length; col++){
                MapElementType currentCell = grid[row][col];
                if(junctionsBundle.contains(currentCell.getDisplayName())){
                    intersections.add(new Point2D.Double(row, col));
                }
            }
        }
    }


    /**
     * finds all the spawn and end points in myGrid
     * @param myGrid represents authoring environment map
     */
    private void findSpawnPointAndEndPoint(MapElementType[][] myGrid){
        spawnPoint = new ArrayList<>();
       endPoint = new ArrayList<>();
        for(int row = 0; row < myGrid.length; row++){
            for(int col = 0; col < myGrid[row].length; col++){
                MapElementType cellType = myGrid[row][col];
                if(cellType.equals(MapElementType.SPAWN)){
                    spawnPoint.add(new Point2D.Double(row, col));
                }
                else if(cellType.equals(MapElementType.END)){
                    endPoint.add(new Point2D.Double(row, col));
                }
            }
        }
    }



}

