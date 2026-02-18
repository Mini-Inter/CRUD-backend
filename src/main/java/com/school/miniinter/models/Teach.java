package com.school.miniinter.models;
import java.util.List;

public class Teach {
    Teacher teacher;
    Subject subject;
    List<Class> classes;

    // Métodos
    public Teach(Teacher teacher, Subject subject, List<Class> classes) {
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
    public List<Class> getClasses() {
        return classes;
    }
    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
