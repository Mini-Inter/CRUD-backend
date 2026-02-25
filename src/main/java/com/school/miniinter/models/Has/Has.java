package com.school.miniinter.models.Has;

import com.school.miniinter.models.Class.Class;

public class Has {
    private int id;
    private Class classroom;

    // Métodos
    public Has(int id, com.school.miniinter.models.Class.Class classroom) {
        this.id = id;
        this.classroom = classroom;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public com.school.miniinter.models.Class.Class getClassroom() {
        return classroom;
    }
    public void setClassroom(Class classroom) {
        this.classroom = classroom;
    }
}
