package com.school.miniinter.models.TeachingAssignment;

public class Teaching {
    private Integer idTeaching;
    private String subject;
    private String teacher;
    private Integer classNum;

    public Teaching() {
        this.idTeaching = null;
        this.subject = null;
        this.teacher = null;
        this.classNum = null;
    }

    public Teaching(Integer idTeaching, String subject, String teacher, Integer classNum) {
        this.idTeaching = idTeaching;
        this.subject = subject;
        this.teacher = teacher;
        this.classNum = classNum;
    }

    public Integer getIdTeaching() {
        return idTeaching;
    }
    public void setIdTeaching(Integer idTeaching) {
        this.idTeaching = idTeaching;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public Integer getClassNum() {
        return classNum;
    }
    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}
