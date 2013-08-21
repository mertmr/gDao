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
 * @author Mert Meral
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
