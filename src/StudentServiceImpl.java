import java.util.Set;

public class StudentServiceImpl implements StudentService {

    private static StudentDao studentDao = null;

    public StudentServiceImpl(){
        studentDao = new StudentDao();
    }


    @Override
    public void update(Student entity) {
        studentDao.openCurrentSessionwithTransaction();
        studentDao.update(entity);
        studentDao.closeCurrentSessionwithTransaction();
    }

    @Override
    public void delete(Student entity) {
        studentDao.openCurrentSessionwithTransaction();
        studentDao.delete(entity);
        studentDao.closeCurrentSessionwithTransaction();
    }

    @Override
    public void save(Student entity) {
        studentDao.openCurrentSessionwithTransaction();
        studentDao.save(entity);
        studentDao.closeCurrentSessionwithTransaction();
    }

    @Override
    public Student findById(Long id) {
        try {
            studentDao.openCurrentSession();
            return studentDao.findById(id);
        }finally {
            studentDao.closeCurrentSession();
        }
    }

    @Override
    public Set<Student> findAll() {
        try {
            studentDao.openCurrentSession();
            return studentDao.findAll();
        }finally {
            studentDao.closeCurrentSession();
        }

    }

    @Override
    public void deleteAll() {
        studentDao.openCurrentSessionwithTransaction();
        studentDao.deleteAll();
        studentDao.closeCurrentSessionwithTransaction();
    }
}
