package com.school.miniinter.models.TeachingAssignment;

public class TeachingAssignment {
    private Integer id;
    private Integer idClass;
    private Integer idSubject;
    private Integer idTeacher;
    private Integer classNumber;
    private String class_hour;

    public TeachingAssignment(Integer id, Integer idClass, Integer idSubject,
                              Integer idTeacher, Integer classNumber) {
        this.id = id;
        this.idClass = idClass;
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
        this.classNumber = classNumber;
        switch (this.classNumber) {
            case (1) -> {
                this.class_hour = "07:00 - 07:55";
            }
            case (2) -> {
                this.class_hour = "08:00 - 08:55";
            }
            case (3) -> {
                this.class_hour = "09:00 - 09:55";
            }
            case (4) -> {
                this.class_hour = "10:30 - 11:25";
            }
            case (5) -> {
                this.class_hour = "11:30 - 12:25";
            }
            case (6) -> {
                this.class_hour = "12:30 - 13:25";
            }
        }
    }

    public String getClass_hour() {
        return class_hour;
    }
    public void setClass_hour(String class_hour) {
        this.class_hour = class_hour;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getIdClass() {
        return idClass;
    }
    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }
    public Integer getIdSubject() {
        return idSubject;
    }
    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }
    public Integer getIdTeacher() {
        return idTeacher;
    }
    public void setIdTeacher(Integer idTeacher) {
        this.idTeacher = idTeacher;
    }
    public Integer getClassNumber() {
        return classNumber;
    }
    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }
    public boolean isNull() {
        return (this.id == null && this.idSubject == null && this.idTeacher == null);
    }
}
