package com.school.miniinter.models.Reports;

import java.util.Date;

public class CompleteInformationReport {

    private String subject;
    private String teacher_name;
    private String description;
    private String type;
    private Date send_at;

    public CompleteInformationReport(String subject, String teacher_name, String description, String type, Date send_at) {
        this.subject = subject;
        this.teacher_name = teacher_name;
        this.description = description;
        this.type = type;
        this.send_at = send_at;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
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
