package com.school.miniinter.models.Teach;
import com.school.miniinter.models.Teacher.Teacher;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.models.Class.Class;
import java.util.List;

public class Teach {
    private int id;
    private Teacher teacher;
    private Subject subject;
    private List<Class> classes;

    // Métodos
    public Teach(Teacher teacher, Subject subject, List<Class> classes) {
        this.teacher = teacher;
        this.subject = subject;
        this.classes = classes;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
