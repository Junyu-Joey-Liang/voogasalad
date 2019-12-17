///**
// * Interface for the model which stores all created authoring features and allows other classes to retrieve stored features
// *
// * @author Goh Khian Wei
// * @author Junyu Liang
// */
//public interface Model {
//
//    /**
//     * Add a newly created feature to the model for storage
//     *
//     * @param type   String representing type of feature created
//     * @param object Actual feature created
//     * @return Unique ID representing the object for ease of future access
//     */
//    int addAuthoringFeature(String type, Object object);
//
//    /**
//     * Returns all stored features of a specific type
//     *
//     * @param type Type to retrieve objects of
//     * @return Map containing each object of the type and their assigned unique ID
//     */
//    Map<Integer, Object> getAuthoringFeatures(String type);
//
//    /**
//     * Returns specific object based on unique ID
//     *
//     * @param ID Unique ID of object
//     * @return Object with specified unique ID
//     */
//    Object getAuthoringFeature(int ID);
//
//    /**
//     * Deletes specific object based on unique ID
//     *
//     * @param ID Unique ID of object
//     */
//    void deleteAuthoringFeature(int ID);
//
//}