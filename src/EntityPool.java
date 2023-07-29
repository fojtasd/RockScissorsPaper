import java.util.ArrayList;
import java.util.List;

public class EntityPool {    
    private static EntityPool instance;

    /**
     * Contains every created object of this class. It is necessary for controlling collisions.
     */
    private final List<Entity> entities;

    private EntityPool() {
        entities = new ArrayList<Entity>();
    }

    public static EntityPool getInstance() {
        if (instance == null) 
            instance = new EntityPool();

        return instance;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
