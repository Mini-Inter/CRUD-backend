package com.school.miniinter.models.Teacher;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class CompleteInfo {

    private String full_name;
    private String subject;
    private String created_at;
    private Integer school_year;
    private String status;
    private String birth_date;
    private String login;
    private String phone;
    private String formated_address;
    private String urlImage;

    DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
            new Locale("pt","BR"));
    public CompleteInfo(Date created_at, String full_name,
                        Integer school_year, String status, Date birth_date,
                        String login, String phone,String formated_address,
                        String urlImage) {
        this.created_at = format.format(created_at);
        this.full_name = full_name;
        this.school_year = school_year;
        this.status = status;
        this.birth_date = format.format(birth_date);
        this.login = login+"@vidya.org.br";
        this.phone = phone;
        this.formated_address = formated_address;
        this.urlImage = urlImage;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getSchool_year() {
        return school_year;
    }

    public void setSchool_year(Integer school_year) {
        this.school_year = school_year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFormated_address() {
        return formated_address;
    }

    public void setFormated_address(String formated_address) {
        this.formated_address = formated_address;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "CompleteInfo{" +
                "full_name='" + full_name + '\'' +
                ", subject='" + subject + '\'' +
                ", created_at=" + created_at +
                ", school_year=" + school_year +
                ", status='" + status + '\'' +
                ", birth_date=" + birth_date +
                ", login='" + login + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
