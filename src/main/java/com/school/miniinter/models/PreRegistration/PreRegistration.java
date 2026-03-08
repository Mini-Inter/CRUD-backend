package com.school.miniinter.models.PreRegistration;

public class PreRegistration {
    private int id;
    private String cpf;
    private int fk_student;
    private String name_student;

    // Métodos
    public PreRegistration(String cpf){
        this.cpf = cpf;
    }
    public PreRegistration(int fk_student, int id){
        this.fk_student = fk_student;
        this.id = id;
    }
    public PreRegistration(int id, String cpf) {
        this.id = id;
        this.cpf = cpf;
    }
    public PreRegistration(int id, int fk_student, String cpf,
                           String name_student){
        this.id = id;
        this.fk_student = fk_student;
        this.cpf = cpf;
        this.name_student = name_student;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public int getFk_student() {
        return fk_student;
    }
    public void setFk_student(int fk_student) {
        this.fk_student = fk_student;
    }
    public String getName_student() {
        return name_student;
    }
    public void setName_student(String name_student) {
        this.name_student = name_student;
    }
}
