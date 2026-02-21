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
    public GradeForSubject(){
        this.subject = null;
        this.n1 = null;
        this.n2 = null;
        this.average = null;
        this.situation = null;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public double getN1() {
        return n1;
    }
    public void setN1(double n1) {
        this.n1 = n1;
    }
    public void setN1() {
        this.n1 = -1.0;
    }
    public double getN2() {
        return n2;
    }
    public void setN2(double n2) {
        this.n2 = n2;
    }
    public void setN2() {
        this.n2 = -1.0;
    }
    public double getAverage() {
        return average;
    }
    public void setAverage() {
        if (this.n1 != -1 && this.n2!=-1)
            this.average = (this.n1 + this.n2)/2;
        else
            this.average = -1.0;
    }
    public String getSituation() {
        return situation;
    }
    public void setSituation() {
        if (average != -1) {
            if (average >= 7)
                this.situation = "APROVADO";
            else
                this.situation = "REPROVADO";
        } else {
            this.situation = "INDEFINIDO";
        }
    }
}
