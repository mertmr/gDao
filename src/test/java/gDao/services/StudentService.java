package gDao.services;

import gDao.genericDao.GDao;
import gDao.genericDao.SimpleDao;
import gDao.models.Mobile;
import gDao.models.Student;
import gDao.util.Finder;
import gDao.util.FinderFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/15/13
 * Time: 10:12 AM
 */
@Service
public class StudentService {

    @SimpleDao(Student.class)
    private GDao<Student> studentDao;

    @SimpleDao(Mobile.class)
    private GDao<Mobile> mobileDao;

    public StudentService() {
    }

    public List<Student> getAllStudents() throws Exception {
        return studentDao.findAll();
    }

    public List<Mobile> getMobileList() throws Exception {
        return mobileDao.findAll();
    }

    public List<Student> findStudentByAddress(String adress) {
        Finder finder = FinderFactory.getInstance();
        finder.addFilterEqual("mobile.address.address", adress);
        return studentDao.findWithFinder(finder);
    }
}
