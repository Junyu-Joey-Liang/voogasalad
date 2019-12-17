//
///**
// * This class is a mock of a data handler that specilizes in creating backend attackers from the provided frontend
// * data list and adding them to the current game engine.
// */
//public class Attackers implements DataHandler {
//
//    private engine gameEngine;
//
//    public Attackers(engine gameEngine) {
//        this.gameEngine = gameEngine;
//    }
//
//    /**
//     * @see DataHandler#handle(dataList)
//     */
//    public void handle(List<Object> dataList) throws UnknownDataException {
//        for (Object o : dataList) {
//            Attacker authoringAttacker = (Attacker) o;
//            try {
//                EngineAttacker attacker = new EngineAttacker(authoringAttacker.getProperties());
//            } catch (Exception e) {
//                throw new UnknownDataException(e);
//            }
//            gameEngine.addAttacker(attacker);
//        }
//    }
//}