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
        if(n1 == -1.0 && n2 == -1.0){
            this.average = -1.0;
        }
        else if(n1 == -1.0){
            this.average = n2/2;
        }else if(n2 == -1.0){
            this.average = n1/2;
        }else{
            this.average = (n1+n2)/2;
        }
        if(this.average == -1.0){
            this.situation = "-";
        }
        else if (this.average >= 7){
            this.situation = "Aprovado";
        }else{
            this.situation = "Reprovado";
        }
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
        if(n1 == -1.0){
            this.average = n2/2;
        }else if(n2 == -1.0){
            this.average = n1/2;
        }else{
            this.average = (n1+n2)/2;
        }
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
