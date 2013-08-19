package gDao;

import gDao.genericDao.GDao;
import gDao.genericDao.SimpleDao;
import gDao.models.Student;
import gDao.services.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit test for simple App.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StudentTest {

    @SimpleDao(Student.class)
    private GDao<Student> studentGDao;

    public void testFindAllStudents() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:applicationContext.xml"});
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.getAllStudents();
    }

    public void testFindAllMobiles() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:applicationContext.xml"});
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.getMobileList();
    }

    public void testFindAddress() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:applicationContext.xml"});
        StudentService studentService = (StudentService) context.getBean("studentService");
        studentService.findStudentByAddress("organize sanayi");
    }

    public void testAnnotation() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:applicationContext.xml"});
        //I have the application context via method "setApplicationContext" and need to get test class object to get the
        // annotated field "TestAnnotation" because I also need to set this field with my business logic, using reflection.
        // Problem is; test class object is not included in the "applicationContext" as a bean and I don't know how to access my
        //test class which is already starting the spring context initialization. I need to get that test class object.
    }

    @Test
    public void testMert() throws Exception {
        studentGDao.findAll();
    }

    @Test
    @Rollback(false)
    public void testInsertStudent() throws Exception {
        Student student = new Student();
        student.setAddress("kozyatagi");
        studentGDao.save(student);
    }
}
