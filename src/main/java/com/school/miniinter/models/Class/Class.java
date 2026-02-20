package com.school.miniinter.models.Class;

public class Class {
    private int id;
    private Character series;
    private Character classroom;

    public Class(int id, Character series, Character classroom) {
        this.id = id;
        this.series = series;
        this.classroom = classroom;
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

    @Override
    public String toString() {
        return this.series+"°"+this.classroom;
    }
}
