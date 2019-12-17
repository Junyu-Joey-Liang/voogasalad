///**
// * Skeleton of controller for attacker builder functionality in the game authoring environment.
// */
//public class AttackerBuilder implements Controller {
//
//    private Set<AttackerObserver> observers;
//    private Node myNode;
//    private Model myModel;
//
//    public AttackerBuilder(Model model) {
//        this.myModel = model;
//        this.myNode = new Node();
//        this.observers = new HashSet<>();
//    }
//
//    @Override
//    public Node getNode() {
//        return myNode;
//    }
//
//    @Override
//    public void addObservers(List<Object> observers) {
//        observers.forEach(observer -> this.observers.put((AttackerObserver) observer));
//    }
//
//    @Override
//    public void load() {
//        // Necessary methods after a file load
//    }
//
//    /**
//     * Handler for when user clicks button to store new attacker.
//     * Creates the new Attacker object, stores it in the model and retrieves its unique ID,
//     * and updates all observers that a new attacker has been created with the specified unique ID.
//     *
//     * @param name      Name of new attacker
//     * @param imagePath Path to image representing new attacker
//     * @param size      Size of new attacker
//     */
//    protected buildAttacker(String name, String imagePath, int size) {
//        Attacker newAttacker = new Attacker(name, imagePath, size);
//        int newID = model.addAuthoringFeature(AuthoringConstants.TYPE_ATTACKER, newAttacker);
//        attackerBuilderObservers.forEach(observer -> observer.update(newID));
//    }
//}