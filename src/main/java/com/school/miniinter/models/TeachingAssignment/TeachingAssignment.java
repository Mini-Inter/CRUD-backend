package com.school.miniinter.models.TeachingAssignment;

public class TeachingAssignment {
    private Integer id;
    private Integer idClass;
    private Integer idSubject;
    private Integer idTeacher;
    private Integer classNumber;

    public TeachingAssignment(Integer id, Integer idClass, Integer idSubject, Integer idTeacher, Integer classNumber) {
        this.id = id;
        this.idClass = idClass;
        this.idSubject = idSubject;
        this.idTeacher = idTeacher;
        this.classNumber = classNumber;
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
