///**
// * Represents a user-created attacker object that can be used in the various game levels.
// */
//public class Attacker implements AuthoredFeature {
//
//    private String name;
//    private String imagePath;
//    private int size;
//
//    public Attacker(String name, String imagePath, int size) {
//        this.name = name;
//        this.imagePath = imagePath;
//        this.size = size;
//    }
//
//    /**
//     * @return Returns a properties map of all the necessary properties required to create a custom attacker in the game engine.
//     */
//    @Override
//    public Map<String, Object> getProperties() {
//        Map<String, Object> propertiesMap = new HashMap<>();
//        propertiesMap.put("NAME", name);
//        propertiesMap.put("IMAGE PATH", imagePath);
//        propertiesMap.put("SIZE", size);
//
//        return propertiesMap;
//    }
//
//}