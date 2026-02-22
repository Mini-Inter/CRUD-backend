package com.school.miniinter.models.Reports;

import java.util.Date;
import java.util.List;

public class CompleteReport {
    private int id;
    private String[] students;
    private String teacher;
    private String description;
    private String type;
    private Date send_at;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String[] getStudents() {
        return students;
    }
    public void setStudents(String[] students) {
        this.students = students;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
}
