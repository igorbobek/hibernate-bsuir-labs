import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainFrame extends JFrame {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 400;

    private static StudentService studentService;

    private final JButton jExit = new JButton("Exit");
    private final JButton jConnect = new JButton("Connect");
    private final JTextField jId = new JTextField("id");
    private final JTextField jAge = new JTextField("age");
    private final JTextField jName = new JTextField("name");
    private final JTextField jStatus =  new JTextField("Empty");
    private final JButton jFind = new JButton("Find");
    private final JButton jDelete = new JButton("Delete");
    private final JButton jAdd = new JButton("Add");
    private final JTextArea jResult = new JTextArea();

    public MainFrame(){
        super("Hibernate");
        this.setLayout(null);
        this.setSize(WIDTH,HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        initElements();

        this.setVisible(true);
    }

    private void initElements(){
        jExit.setSize(100,50);
        jExit.setLocation((WIDTH - jExit.getWidth())/2,HEIGHT - jExit.getHeight()*2);
        jExit.addActionListener((e)->{
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
        this.add(jExit);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jId.setSize(120,20);
        jId.setLocation((WIDTH - jId.getWidth())/2, 0);
        this.add(jId);

        jName.setSize(120,20);
        jName.setLocation((WIDTH - jName.getWidth())/2, 20);
        this.add(jName);

        jAge.setSize(120,20);
        jAge.setLocation((WIDTH - jAge.getWidth())/2, 40);
        this.add(jAge);

        jConnect.setSize(100,50);
        jConnect.setLocation((WIDTH - jConnect.getWidth())/2, HEIGHT - jConnect.getHeight()*3);
        jConnect.addActionListener((e)->{
            connect();
        });
        this.add(jConnect);

        jStatus.setSize(70,20);
        jStatus.setLocation(WIDTH - jConnect.getWidth()+20, HEIGHT - jConnect.getHeight());
        this.add(jStatus);

        jFind.setSize(100,20);
        jFind.setLocation(0,    (HEIGHT - jFind.getHeight())/2 - 40);
        jFind.addActionListener((e)->{
            find();
        });
        this.add(jFind);


        jDelete.setSize(100,20);
        jDelete.setLocation(0,    (HEIGHT - jDelete.getHeight())/2 - 20);
        jDelete.addActionListener((e)->{
            delete();
        });
        this.add(jDelete);

        jAdd.setSize(100,20);
        jAdd.setLocation(0,    (HEIGHT - jAdd.getHeight())/2);
        jAdd.addActionListener((e)->{
            add();
        });
        this.add(jAdd);

        jResult.setSize(150,170);
        jResult.setLocation((WIDTH - jResult.getWidth())/2+50, (HEIGHT - jResult.getHeight())/2-40);
        jResult.setAutoscrolls(true);
        this.add(jResult);


    }

    private void add() {
        Student student = null;
        try {
            int age = Integer.parseInt(jAge.getText());
            String name = jName.getText();
            for (Student element: studentService.findAll()
                 ) {
                if(element.getName().equals(name)) student = element;
            }
            if(student != null){
                student.setName(name);
                student.setAge(age);
                studentService.update(student);
            }else{
                studentService.save(new Student(name,age));
            }
        }catch (NumberFormatException e){
            System.err.println(e);
        }
    }

    private void delete() {
        if(jId.getText().equals("")){
            studentService.deleteAll();
            return;
        }
        try {
            Long id = Long.parseLong(jId.getText());
            studentService.delete(studentService.findById(id));
        }catch (NumberFormatException e){
            System.err.println(e);
        }
    }

    private void find() {
        jResult.setText("");
        if(jId.getText().equals("")){
            studentService.findAll().forEach((e)->{
                jResult.append(e.toString()+"\n");
            });
            return;
        }
        try {
            Long id = Long.parseLong(jId.getText());
            Student student = studentService.findById(id);
            if(student != null){
                jId.setText(student.getId().toString());
                jAge.setText(student.getAge().toString());
                jName.setText(student.getName());
            }
        }catch (NumberFormatException e){
            System.err.println(e);
        }
    }

    private void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/lab7?useSSL=false&user=root&password=pass");
            this.getContentPane().setBackground(new Color(0, 125, 0));
            jStatus.setText("SUCCESS");
            conn.close();
            if (studentService == null) {
                studentService = new StudentServiceImpl();
            }
        }catch (Exception e){
            System.out.println(e);
            jStatus.setText("ERROR");
            this.getContentPane().setBackground(new Color(155, 0, 0));
        }

    }
}
