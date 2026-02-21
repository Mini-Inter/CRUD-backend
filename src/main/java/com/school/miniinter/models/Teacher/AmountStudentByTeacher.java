package com.school.miniinter.models.Teacher;

import com.school.miniinter.models.Class.Class;
public class AmountStudentByTeacher {

    private Class teacherClass;
    private Integer amountStudent;

    public AmountStudentByTeacher(Class teacherClass, Integer amountStudent) {
        this.teacherClass = teacherClass;
        this.amountStudent = amountStudent;
    }

    public Class getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(Class teacherClass) {
        this.teacherClass = teacherClass;
    }

    public Integer getAmountStudent() {
        return amountStudent;
    }

    public void setAmountStudent(Integer amountStudent) {
        this.amountStudent = amountStudent;
    }

    @Override
    public String toString() {
        return "AmountStudentByTeacher{" +
                "teacherClass=" + teacherClass +
                ", amountStudent=" + amountStudent +
                '}';
    }
}
