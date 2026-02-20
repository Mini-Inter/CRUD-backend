package com.school.miniinter.models.Grades;

public class GradeForSubject {

    private String subject;
    private Double n1;
    private Double n2;
    private Double average;
    private String situation;


    public GradeForSubject(String subject, Double n1, Double n2){
        this.subject = subject;
        this.n1 = n1;
        this.n2 = n2;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getN1() {
        return n1;
    }

    public void setN1(Double n1) {
        this.n1 = n1;
    }

    public Double getN2() {
        return n2;
    }

    public void setN2(Double n2) {
        this.n2 = n2;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage() {
        this.average = (this.n1 + this.n2) / 2;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation() {
        if (this.average >= 7){
            this.situation = "Aprovado";
        }else{
            this.situation = "Reprovado";
        }
    }
}
