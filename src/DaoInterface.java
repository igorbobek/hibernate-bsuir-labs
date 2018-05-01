import java.io.Serializable;
import java.util.Set;

public interface DaoInterface<T, Id extends Serializable>{

    void update(T entity);
    void delete(T entity);
    void save(T entity);
    T findById(Id id);
    Set<T> findAll();
    void deleteAll();


}
