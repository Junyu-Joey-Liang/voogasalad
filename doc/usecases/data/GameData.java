//package xstreamspike;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//
///**
// * This class is an example implementation of the Data Interface.
// */
//public class GameData implements Data {
//
//    private engine gameEngine;
//    private Map<String,List<Object>> dataMap;
//    private String filepath;
//    private XStream mySerializer = new XStream(new DomDriver());
//
//    public GameData(String filepath) {
//        this.filepath = filepath;
//        // attempt to read from saved file
//        try {
//            getEngine();
//        } catch (Exception e) {
//            // no such filepath exists so create a new game engine
//            gameEngine = new GameEngine();
//        }
//        dataMap = null;
//    }
//
//    /**
//     * @see Data#saveData(dataMap)
//     */
//    public void saveData(Map<String,List<Object>> dataMap) throws UnknownDataException {
//        this.dataMap = dataMap;
//        for (String s : dataMap.keySet()) {
//            DataHandler handler = new DataHandlerFactory().makeHandler(s, gameEngine);
//            handler.handle(dataMap.get(s));
//        }
//        // save game engine after iterating through the map
//        mySerializer.toXML(gameEngine);
//    }
//
//    /**
//     * @see Data#retrieveEngine()
//     */
//    public engine retrieveEngine() throws UnknownDataException {
//        try {
//            getEngine();
//        } catch (Exception e) {
//            throw new UnknownDataException(e);
//        }
//        return gameEngine;
//    }
//
//    /**
//     * @see Data#retrieveDataMap()
//     */
//    public Map<String,List<Object>> retrieveDataMap() {
//        return dataMap;
//    }
//
//    /**
//     * @see Data#setFilePath(filePath)
//     */
//    public void setFilePath(String filePath) {
//        if (filePath != null) {
//            this.filepath = filePath;
//        }
//    }
//
//    private void getEngine() throws Exception {
//        String mySavedEngine = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
//        gameEngine = (engine)mySerializer.fromXML(mySavedEngine);
//    }
//}