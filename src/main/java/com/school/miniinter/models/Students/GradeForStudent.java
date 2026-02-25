package com.school.miniinter.models.Students;

import java.text.DecimalFormat;

public class GradeForStudent {
    private String full_name;
    private Integer id_student;
    private Double n1;
    private Integer idN1;
    private Double n2;
    private Integer idN2;
    private Double average;

    DecimalFormat df = new DecimalFormat("#.00");

    public GradeForStudent(String full_name,Integer id_student, Double n1,
                           Integer idN1,
                           Double n2, Integer idN2) {
        this.full_name = full_name;
        this.n1 = n1;
        this.n2 = n2;
        this.id_student = id_student;
        this.idN1 = idN1;
        this.idN2 = idN2;
        if(n1 == -1.0 && n2 == -1.0){
            this.average = -1.0;
        } else if(n1 == -1.0){
            this.average = Double.parseDouble(df.format(n2/2).replace(',','.'));
        } else if(n2 == -1.0){
            this.average = Double.parseDouble(df.format(n2/1).replace(',','.'));
        }else{
            this.average =
                    Double.parseDouble(df.format((n1+n2)/2).replace(',','.'));
        }
    }
    public GradeForStudent() {
        this.full_name = null;
        this.n1 = null;
        this.n2 = null;
        this.id_student = null;
        this.idN1 = null;
        this.idN2 = null;
        this.average = null;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
        this.average = Double.parseDouble(df.format((n1+n2)/2).replace(',',
                '.'));
    }

    public Integer getId_student() {
        return id_student;
    }

    public void setId_student(Integer id_student) {
        this.id_student = id_student;
    }

    public Integer getIdN1() {
        return idN1;
    }

    public void setIdN1(Integer idN1) {
        this.idN1 = idN1;
    }

    public Integer getIdN2() {
        return idN2;
    }

    public void setIdN2(Integer idN2) {
        this.idN2 = idN2;
    }
}
