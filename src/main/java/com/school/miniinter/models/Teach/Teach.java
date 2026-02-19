package com.school.miniinter.models.Teach;
import com.school.miniinter.models.Class.Class;
import com.school.miniinter.models.Subject.Subject;
import com.school.miniinter.models.Teacher.Teacher;

import java.util.List;

public class Teach {
    Teacher teacher;
    Subject subject;
    List<com.school.miniinter.models.Class.Class> classes;

    // Métodos
    public Teach(Teacher teacher, Subject subject, List<com.school.miniinter.models.Class.Class> classes) {
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
    public List<com.school.miniinter.models.Class.Class> getClasses() {
        return classes;
    }
    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
