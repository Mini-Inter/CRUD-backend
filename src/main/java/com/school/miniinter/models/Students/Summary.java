package com.school.miniinter.models.Students;

import java.sql.Date;

public class Summary {
    private Integer matricula;
    private String name;
    private String firstName;
    private String lastName;
    private String guardian;
    private Date birthDate;
    private Date createdAt;
    private String phone;
    private String email;
    private char classroom;
    private Integer series;
    private double average;
    private boolean situation;

    // Métodos getters e setters


    public String getFirstName() {
        return firstName;
    }

    private void setFirstName() {
        this.firstName = this.name.substring(0, this.name.indexOf(" "));
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName() {
        this.lastName = this.name.substring(this.name.lastIndexOf(" "));
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String login) {
        this.email = login+"@vidya.org.br";
    }

    public boolean isSituation() {
        return situation;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setLastName();
        setFirstName();
    }

    public char getClassroom() {
        return classroom;
    }

    public void setClassroom(char classroom) {
        this.classroom = classroom;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public boolean getSituation() {
        return situation;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }
}
