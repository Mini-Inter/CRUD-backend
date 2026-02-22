package com.school.miniinter.models.Guardian;

import java.sql.Date;

public class Guardian {
    private int id;
    private String name;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public Guardian(int id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        setFirstName();
        setLastName();
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
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName() {
        this.firstName = this.name.substring(0, this.name.indexOf(" "));
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName() {
        this.lastName = this.name.substring(this.name.lastIndexOf(" "));
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}