/*
 * Copyright 2013 Mert Meral
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gDao.genericDao;

import java.lang.reflect.Field;

/**
 * @author Mert Meral
 * @since 0.1.0
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
