import java.util.Set;

public interface StudentService {
    void update(Student entity);
    void delete(Student entity);
    void save(Student entity);
    Student findById(Long id);
    Set<Student> findAll();
    void deleteAll();
}
