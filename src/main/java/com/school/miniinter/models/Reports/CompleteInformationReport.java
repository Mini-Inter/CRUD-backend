package com.school.miniinter.models.Reports;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class CompleteInformationReport {

    private String teacher_name;
    private String description;
    private String type;
    private String send_at;

    public CompleteInformationReport(String teacher_name, String description, String type, Date send_at) {
        this.teacher_name = teacher_name;
        this.description = description;
        this.type = type;
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
                new Locale("pt","BR"));
        this.send_at = format.format(send_at);
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
    public String getSend_at() {
        return send_at;
    }

    public void setSend_at(String send_at) {
        this.send_at = send_at;
    }
}
