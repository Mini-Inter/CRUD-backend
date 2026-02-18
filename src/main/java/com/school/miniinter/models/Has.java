package com.school.miniinter.models;

public class Has {
    private int id;
    private Class classroom;
    private Teach professoria;

    // Métodos
    public Has(int id, Class classroom, Teach professoria) {
        this.id = id;
        this.classroom = classroom;
        this.professoria = professoria;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Class getClassroom() {
        return classroom;
    }
    public void setClassroom(Class classroom) {
        this.classroom = classroom;
    }
    public Teach getProfessoria() {
        return professoria;
    }
    public void setProfessoria(Teach professoria) {
        this.professoria = professoria;
    }
}
