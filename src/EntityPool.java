import java.util.ArrayList;
import java.util.List;

public class EntityPool {    
    private static EntityPool instance;

    /**
     * Contains every created object of this class. It is necessary for controlling collisions.
     */
    private final List<Entity> entities;

    private EntityPool() {
        entities = new ArrayList<>();
    }

    public boolean checkVictoryCondition(){
        final EntityPool pool = EntityPool.getInstance();
        final int entitiesSize = pool.getEntities().size();
        for (int i = 0; i < entitiesSize + 1; i++){
            if (i == entitiesSize){
                break;
            }
            if(pool.getEntities().get(i).getTypeOfObject() != pool.getEntities().get(++i).getTypeOfObject()){
                return false;
            }
        }
        return true;
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
