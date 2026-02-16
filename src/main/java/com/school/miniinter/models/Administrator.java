package com.school.miniinter.models;

import java.time.LocalDate;

public class Administrator {
        private int id;
        private String name;
        private LocalDate birthDate;
        private String position;
        private String login;
        private String password;

        // Métodos

    public Administrator(int id, String name, LocalDate birthDate, String position, String login, String password) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
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
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
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
