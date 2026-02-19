package com.school.miniinter.models.Grades;

public class Grades {
    private int id;
    private int fk_student;
    private int fk_subject;
    private int type;
    private float value;
    private String send_at;

    public Grades(int fk_student, int fk_subject, int type, float value, String send_at) {
        this.fk_student = fk_student;
        this.fk_subject = fk_subject;
        this.type = type;
        this.value = value;
        this.send_at = send_at;
    }

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

    public int getFk_subject() {
        return fk_subject;
    }

    public void setFk_subject(int fk_subject) {
        this.fk_subject = fk_subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getSend_at() {
        return send_at;
    }

    public void setSend_at(String send_at) {
        this.send_at = send_at;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "id=" + id +
                ", fk_student=" + fk_student +
                ", fk_subject=" + fk_subject +
                ", type=" + type +
                ", value=" + value +
                ", send_at='" + send_at + '\'' +
                '}';
    }
}
