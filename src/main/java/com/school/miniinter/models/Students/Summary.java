package com.school.miniinter.models.Students;

import com.school.miniinter.models.Class.Class;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Summary {
    private Integer matricula;
    private String name;
    private String firstName;
    private String lastName;
    private String guardian;
    private String birthDate;
    private String createdAt;
    private String phone;
    private String email;
    private char classroom;
    private char series;
    private Class classStudent;
    private double average;
    private String situation;
    private Integer school_year;

    public Summary(int id_student, char classroom, char series,
                   String full_name){
        this.matricula = id_student;
        school_year = LocalDate.now().getYear();
        this.classStudent = new Class(series,classroom,school_year);
        this.name = full_name;
    }

    public Summary(Integer matricula, char classroom
            , char series, String name, double average,String email,
                   String guardian, String phone, Date createdAt, Date birthDate) {
        this.matricula = matricula;
        setName(name);
        this.guardian = guardian;
        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT,
                new Locale("pt","BR"));
        this.createdAt = format.format(createdAt);
        this.birthDate = format.format(birthDate);
        this.phone = phone;
        this.email = email;
        this.classroom = classroom;
        this.series = series;
        school_year = LocalDate.now().getYear();
        this.classStudent = new Class(series,classroom,school_year);
        this.average = average;
        if(this.average >=7){
            this.situation = "Aprovado";
        }else{
            this.situation = "Reprovado";
        }
    }

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

    public String isSituation() {
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

    public char getSeries() {
        return series;
    }

    public void setSeries(char series) {
        this.series = series;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Class getClassStudent() {
        return classStudent;
    }

    public void setClassStudent(Class classStudent) {
        this.classStudent = classStudent;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
