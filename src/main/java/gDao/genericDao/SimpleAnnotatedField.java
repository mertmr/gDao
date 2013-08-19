package gDao.genericDao;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: mmeral
 * Date: 5/20/13
 * Time: 1:51 PM
 */
public class SimpleAnnotatedField {
    private Field annotatedField;

    private Class persistentClass;

    public SimpleAnnotatedField(Field field, Class value) {
        this.annotatedField = field;
        this.persistentClass = value;
    }

    public Field getAnnotatedField() {
        return annotatedField;
    }

    public void setAnnotatedField(Field annotatedField) {
        this.annotatedField = annotatedField;
    }

    public Class getPersistentClass() {
        return persistentClass;
    }

    public void setPersistentClass(Class persistentClass) {
        this.persistentClass = persistentClass;
    }
}
