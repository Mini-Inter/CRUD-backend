package com.school.miniinter.models.Receive;

public class Receive {
    private int id;
    private int fk_student;
    private int[] fk_students;
    private int fk_report;

    public Receive(int fk_student, int fk_report) {
        this.fk_student = fk_student;
        this.fk_report = fk_report;
    }

    public Receive(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_student() {
        return fk_student;
    }

    public void setFk_student(int fk_student) {
        this.fk_student = fk_student;
    }

    public int getFk_report() {
        return fk_report;
    }

    public void setFk_report(int fk_report) {
        this.fk_report = fk_report;
    }

    public int[] getFk_students() {
        return fk_students;
    }

    public void setFk_students(int[] fk_students) {
        this.fk_students = fk_students;
    }

    @Override
    public String toString() {
        return "Receive{" +
                "id=" + id +
                ", fk_student=" + fk_student +
                ", fk_report=" + fk_report +
                '}';
    }
}
