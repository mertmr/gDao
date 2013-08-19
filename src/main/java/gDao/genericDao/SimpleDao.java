package gDao.genericDao;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/15/13
 * Time: 2:41 PM
 */
@Component
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleDao {
    Class value();    // Annotation member
}
