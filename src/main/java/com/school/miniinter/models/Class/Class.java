package com.school.miniinter.models.Class;

public class Class {
    private int id;
    private int series;
    private int classroom;

    public Class(int series, int classroom) {
        this.series = series;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", series=" + series +
                ", classroom=" + classroom +
                '}';
    }
}
