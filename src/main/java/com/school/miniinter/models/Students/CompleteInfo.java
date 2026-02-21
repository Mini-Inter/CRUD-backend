package com.school.miniinter.models.Students;

import com.school.miniinter.models.Class.Class;

import java.util.Date;

public class CompleteInfo {
    private String full_name;
    private Integer id_student;
    private String firstName_guardian;
    private Class studentClass;
    private Date created_at;
    private Integer school_year;
    private String status;
    private Date birth_date;
    private String login;
    private String phone;
    private String full_address;

    public CompleteInfo(String full_name, Integer id_student,
                        String firstName_guardian, Class studentClass,
                        Date created_at,Integer school_year, Date birth_date,
                        String login,
                        String phone, String full_address) {
        this.full_name = full_name;
        this.id_student = id_student;
        this.firstName_guardian = firstName_guardian;
        this.studentClass = studentClass;
        this.created_at = created_at;
        this.birth_date = birth_date;
        this.login = login+"@vidya.org.br";
        this.phone = phone;
        this.full_address = full_address;
        this.school_year = school_year;
        this.status = "Ativo";
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Integer getId_student() {
        return id_student;
    }

    public void setId_student(Integer id_student) {
        this.id_student = id_student;
    }

    public String getFirstName_guardian() {
        return firstName_guardian;
    }

    public void setFirstName_guardian(String firstName_guardian) {
        this.firstName_guardian = firstName_guardian;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
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

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    @Override
    public String toString() {
        return "CompleteInfo{" +
                "full_name='" + full_name + '\'' +
                ", id_student=" + id_student +
                ", firstName_guardian='" + firstName_guardian + '\'' +
                ", studentClass=" + studentClass +
                ", created_at=" + created_at +
                ", school_year=" + school_year +
                ", status='" + status + '\'' +
                ", birth_date=" + birth_date +
                ", login='" + login + '\'' +
                ", phone='" + phone + '\'' +
                ", full_address='" + full_address + '\'' +
                '}';
    }
}
