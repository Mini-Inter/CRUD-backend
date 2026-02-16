package com.school.miniinter.models;
import java.util.List;

public class Teach {
    Teacher teacher;
    Subject subject;
    List<Classroom> classes;

    // Métodos
    public Teach(Teacher teacher, Subject subject, List<Classroom> classes) {
        this.teacher = teacher;
        this.subject = subject;
        this.classes = classes;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public Subject getSubject() {
        return subject;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public List<Classroom> getClasses() {
        return classes;
    }
    public void setClasses(List<Classroom> classes) {
        this.classes = classes;
    }
}
