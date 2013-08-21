package gDao;

import gDao.genericDao.GDao;
import gDao.genericDao.SimpleDao;
import gDao.models.Student;
import gDao.services.StudentService;
import gDao.util.Finder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Mert Meral
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

    @Test
    public void testMert() throws Exception {
        List<Student> studentsList = studentGDao.findAll();
    }

    @Test
    @Rollback(false)
    public void testInsertStudent() throws Exception {
        Student student = new Student();
        student.setAddress("kozyatagi");
        studentGDao.save(student);
    }

    @Test
    public void testFinder() throws Exception{
        Finder finder = new Finder();
        finder.addFilterEqual("ADDRESS", "kozyatagi");
        finder.addSortAsc("ADDRESS");
        List<Student> studentList = studentGDao.findWithFinder(finder);
    }

    @Test
    public void testFindByColumn() throws Exception {
        List<Student> studentList = studentGDao.findByColumn("ADDRESS", "kozyatagi");
    }

    @Test
    public void testFindByColumn2() throws Exception {
        List<Student> studentList = studentGDao.findByColumn("NUMBER", 123);
    }
}
