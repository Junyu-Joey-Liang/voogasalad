package testing.testingauthoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.authoring.model.BasicModel;
import voogasalad.authoring.model.Model;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthoringModelTest {

    private Model model;
    private Map<Integer, AuthoringFeature> idMap = new HashMap<>();
    private final String PROJ_NAME = "testProjectile";
    private final Projectile p  = new Projectile(PROJ_NAME,"",1,1,"",1,1);
    private final String type = "Projectile";

    @BeforeEach
    void setUp () {
        model = new BasicModel();
        idMap.put(10,p);
    }

    @Test
    void addAuthoringFeature() {
        int projectileId = model.addAuthoringFeature(type,p);
        assert(model.getAuthoringFeatures(type).contains(p));
        assertEquals(model.getAuthoringFeature(projectileId).getName(),PROJ_NAME);
    }

    @Test
    void loadMap() {
        model = new BasicModel();
        assertEquals(model.getIDMap().size(),0);
        model.load(idMap);
        assertEquals(model.getIDMap().size(),1);
        assert(model.getIDMap().keySet().iterator().next()==10);
    }
}