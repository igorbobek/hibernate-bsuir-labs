import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    public Student(){}

    public Student(String name, Integer age){
        this.name = name;
        this.age = age;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;

        if(obj instanceof Student){
            Student student = (Student)obj;
            if(student.name.equals(this.name)) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Name:%s, Age: %d", this.id, this.name,this.age);
    }
}
