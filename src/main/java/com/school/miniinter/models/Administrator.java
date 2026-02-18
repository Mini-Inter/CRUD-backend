package com.school.miniinter.models;

import java.sql.Date;

public class Administrator {
        private int id;
        private String name;
        private Date birthDate;
        private String type;
        private String login;
        private String password;

        // Métodos

    public Administrator(int id, String name, Date birthDate, String type, String login, String password) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.login = login;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getType() {
        return type;
    }
    public void setPosition(String position) {
        this.type = position;
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
}
