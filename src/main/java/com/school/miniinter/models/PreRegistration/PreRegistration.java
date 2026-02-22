package com.school.miniinter.models.PreRegistration;

public class PreRegistration {
    private int id;
    private String cpf;
    private int fk_student;

    // Métodos
    public PreRegistration(int fk_student, int id){
        this.fk_student = fk_student;
        this.id = id;
    }
    public PreRegistration(int id, String cpf) {
        this.id = id;
        this.cpf = cpf;
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
}
