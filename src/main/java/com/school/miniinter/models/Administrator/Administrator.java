package com.school.miniinter.models.Administrator;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Locale;

public class Administrator {
        private int id;
        private String name;
        private String birthDate;
        private String type;
        private String login;
        private String password;

        // Métodos

    public Administrator(int id, String name, Date birthDate, String type, String login, String password) {
        this.id = id;
        this.name = name;
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
                new Locale("pt","BR"));
        this.birthDate = format.format(birthDate);
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
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
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
