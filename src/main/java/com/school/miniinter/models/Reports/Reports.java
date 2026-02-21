package com.school.miniinter.models.Reports;

import java.sql.Date;

public class Reports {
    private int id;
    private int fk_teachers;
    private String type;
    private String description;
    private Date send_at;

    public Reports(int fk_teachers, String type, String description, Date send_at) {
        this.type = type;
        this.fk_teachers = fk_teachers;
        this.description = description;
        this.send_at = send_at;
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
    public Date getSend_at() {
        return send_at;
    }
    public void setSend_at(Date send_at) {
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
