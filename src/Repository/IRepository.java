package Repository;
import Domain.BaseEntity;

import java.util.List;
import java.util.Set;
/**
 * Created by prunescu on 22/03/2017.
 */



public interface IRepository<ID, T extends BaseEntity<ID>> {
    /**
     * Get the entity with the given id
     * @param id must not be null
     * @return an object of type T
     * @throws
     */
    T get(ID id);

    /**
     * Get all the entities
     * @param -
     * @return a list of objects of type T
     * @throws
     */
    List<T> getAll();

    /**
     * Deletes the entity with the given id
     * @param id not null
     * @return -
     * @throws -an exception when object with that id is not found
     */
    void delete(ID id);

    /**
     * Adds an entity in the repository
     * @param entity of type T
     * @throws IllegalArgumentException if entity is null
     */
    void add(T entity);

    /**
     * Updates the entity with a given id
     * @param id,
     * @param entity of type T
     * @throws - an exception if entity not found
     */
    void update(ID id, T entity);

    Set<ID> keys();
}
