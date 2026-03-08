package com.school.miniinter.models.Reports;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

public class Reports {
    private int id;
    private int fk_teachers;
    private String type;
    private String description;
    private String send_at;
    private String[] fk_students;

    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("pt","BR"));

    public Reports(Integer fk_teacher, String description, String type) {
        this.fk_teachers = fk_teacher;
        this.description = description;
        this.type = type.substring(0,1).toUpperCase() + type.substring(1);
    }
    public Reports(int fk_teachers, String type, String description, Date send_at) {
        this.type = type;
        this.fk_teachers = fk_teachers;
        this.description = description;
        this.send_at = format.format(send_at);
    }
    public Reports(int id, int fk_teachers, String type, String description, Date send_at) {
        this.id = id;
        this.fk_teachers = fk_teachers;
        this.type = type;
        this.description = description;
        this.send_at = format.format(send_at);
    }

    public String[] getFk_students() {
        return fk_students;
    }
    public void setFk_students(String[] fk_students) {
        this.fk_students = fk_students;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getFk_teachers() {
        return fk_teachers;
    }
    public void setFk_teachers(int fk_teachers) {
        this.fk_teachers = fk_teachers;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSend_at() {
        return send_at;
    }
    public void setSend_at(String send_at) {
        this.send_at = send_at;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "id=" + id +
                ", fk_teachers=" + fk_teachers +
                ", description='" + description + '\'' +
                ", send_at='" + send_at + '\'' +
                '}';
    }
}
