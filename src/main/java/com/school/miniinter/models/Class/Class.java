package com.school.miniinter.models.Class;

public class Class {
    private int id;
    private Character series;
    private Character classroom;
    private Integer academic_year;

    public Class(int id, Character series, Character classroom, Integer academic_year) {
        this.id = id;
        this.series = series;
        this.classroom = classroom;
        this.academic_year = academic_year;
    }

    public Class(Character series, Character classroom, Integer academic_year){
        this.series = series;
        this.classroom = classroom;
        this.academic_year = academic_year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Character getSeries() {
        return series;
    }

    public void setSeries(Character series) {
        this.series = series;
    }

    public Character getClassroom() {
        return classroom;
    }

    public void setClassroom(Character classroom) {
        this.classroom = classroom;
    }

    public Integer getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(Integer academic_year) {
        this.academic_year = academic_year;
    }

    @Override
    public String toString() {
        return this.series+"°"+this.classroom;
    }
}
