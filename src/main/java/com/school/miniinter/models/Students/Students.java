package com.school.miniinter.models.Students;
import java.util.Date;

public class Students {
    private int id_student;
    private int fk_class;
    private String full_name;
    private String first_name;
    private String last_name;
    private Date birth_date;
    private String login;
    private String password;
    private String created_at;

    public Students(int id_student, int fk_class,
    String full_name, String first_name, String last_name, Date birth_date, String login, String password, String created_at) {
        this.id_student = id_student;
        this.fk_class = fk_class;
        this.full_name = full_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.login = login;
        this.password = password;
        this.created_at = created_at;
    }

    public Students(){}

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getFk_class() {
        return fk_class;
    }

    public void setFk_class(int fk_class) {
        this.fk_class = fk_class;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Students{" +
                "id_student=" + id_student +
                ", fk_class=" + fk_class +
                ", full_name='" + full_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birth_date=" + birth_date +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
