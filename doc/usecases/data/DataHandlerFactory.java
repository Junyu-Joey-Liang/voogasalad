//
///**
// * This class is a mock of a data handler factory that uses
// * reflection to create a data handler from one of its subclasses.
// */
//public class DataHandlerFactory {
//
//    public DataHandler makeHandler(String s, engine gameEngine) throws UnknownDataException {
//        try {
//            Class<?> clazz = Class.forName(DataHandlerFactory.class.getPackageName() + "." + s);
//            return (DataHandler) clazz.getDeclaredConstructor(engine.class).newInstance(gameEngine);
//        } catch (Exception e) {
//            throw new UnknownDataException(e);
//        }
//    }
//}