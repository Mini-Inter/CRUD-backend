package com.school.miniinter.models.Students;

public class BasicInfo {

    private int id_student;
    private String full_name;
    private String first_name;
    private String classroom;
    private String series;
    private int school_year;

    public BasicInfo(int id_student, String full_name, String first_name, String classroom, String series, int school_year) {
        this.id_student = id_student;
        this.full_name = full_name;
        this.first_name = first_name;
        this.classroom = classroom;
        this.series = series;
        this.school_year = school_year;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getSchool_year() {
        return school_year;
    }

    public void setSchool_year(int school_year) {
        this.school_year = school_year;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String showClass(){
        return this.series+"° ano "+this.classroom;
    }
}
