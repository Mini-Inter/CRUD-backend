package com.school.miniinter.models.Grades;

public class SimpleGrade {
    private Integer id_student;
    private Integer id_subject;
    private Double value;
    private String grade_type;

    public SimpleGrade(Integer id_student, Integer id_subject, Double value, String grade_type) {
        this.id_student = id_student;
        this.id_subject = id_subject;
        this.value = value;
        this.grade_type = grade_type;
    }

    public Integer getId_student() {
        return id_student;
    }

    public void setId_student(Integer id_student) {
        this.id_student = id_student;
    }

    public Integer getId_subject() {
        return id_subject;
    }

    public void setId_subject(Integer id_subject) {
        this.id_subject = id_subject;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getGrade_type() {
        return grade_type;
    }

    public void setGrade_type(String grade_type) {
        this.grade_type = grade_type;
    }

    @Override
    public String toString() {
        return "SimpleGrade{" +
                "id_student=" + id_student +
                ", id_subject=" + id_subject +
                ", value=" + value +
                ", grade_type='" + grade_type + '\'' +
                '}';
    }
}
