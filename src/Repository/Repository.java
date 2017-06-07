package Repository;
import Domain.*;
import Domain.Validators.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Repository<ID, T extends BaseEntity<ID>> implements IRepository<ID, T> {
    private HashMap<ID, T> entities;
    private Validator<T> validator;

    public Repository(Validator<T> validator){
        this.validator = validator;
        this.entities = new HashMap<>();

    }

    public int sizeOfRepo(){return entities.size();}

    public Set<ID> keys(){
        return entities.keySet();
    }

    @Override
    public T get(ID id) {
        if (id == null){
            throw new IllegalArgumentException("Null id");
        }
        return this.entities.get(id);
    }

    @Override
    public List<T> getAll() {
        return entities.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public void delete(ID id) {
        if (id == null){
            throw new IllegalArgumentException("Null id");
        }
        entities.remove(id);

    }

    @Override
    public void add(T entity) {
        if (entity == null){
            throw new IllegalArgumentException("Null entity");
        }
        entities.putIfAbsent(entity.getId(),entity);
    }

    @Override
    public void update(ID id, T entity) {
        if (id == null){
            throw new IllegalArgumentException("Null id");
        }
        entities.put(id,entity);

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
