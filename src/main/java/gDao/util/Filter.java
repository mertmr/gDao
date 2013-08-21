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
package gDao.util;

/**
 * @author Mert Meral
 * @since 0.0.2
 */
public class Filter {
    private String column;
    private Object value;
    private String relation;
    private String[] joinedColumns;

    public Filter(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public Filter(String column, String[] joinedColumns){
        this.column = column;
        this.joinedColumns = joinedColumns;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String[] getJoinedColumns() {
        return joinedColumns;
    }

    public void setJoinedColumns(String[] joinedColumns) {
        this.joinedColumns = joinedColumns;
    }

    public Object getValue() {

        return value;
    }

    public void setValue(Object value) {
        this.value = this.value;
    }
}
