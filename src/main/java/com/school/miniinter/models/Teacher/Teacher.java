package com.school.miniinter.models.Teacher;

import com.school.miniinter.models.Subject.Subject;

import java.sql.Date;
import java.util.List;

public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private String name;
    private String login;
    private String phone;
    private String password;
    private Date birthDate;
    private Date createdAt;

    // Métodos
    public Teacher(int id, String name, String login,
                   String phone, String password, Date birthDate,
                   Date createdAt) {
        this.id = id;
        this.name = name;
        this.firstName = name.substring(0, name.indexOf(" "));
        this.lastName = name.substring(name.lastIndexOf(" "));
        this.login = login;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
    }

    public Teacher(String name, String login,String phone, String password,
                   Date birthDate){
        this.name = name;
        this.firstName = name.substring(0, name.indexOf(" "));
        this.lastName = name.substring(name.lastIndexOf(" "));
        this.login = login;
        this.phone = phone;
        this.password = password;
        this.birthDate = birthDate;
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
        setFirstName();
        setLastName();
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName() {
        this.firstName = this.name.substring(0, name.indexOf(" "));
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName() {
        this.lastName = name.substring(name.lastIndexOf(" "));
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
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
