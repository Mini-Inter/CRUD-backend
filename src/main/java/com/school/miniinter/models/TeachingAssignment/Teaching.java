package com.school.miniinter.models.TeachingAssignment;

public class Teaching {
    private Integer idTeaching;
    private String subject;
    private String teacher;
    private Integer classNum;
    private String class_hour;

    public Teaching() {
        this.idTeaching = null;
        this.subject = null;
        this.teacher = null;
        this.classNum = null;
    }

    public Teaching(Integer idTeaching, String subject, String teacher,
                    Integer classNum, String class_hour) {
        this.idTeaching = idTeaching;
        this.subject = subject;
        this.teacher = teacher;
        this.classNum = classNum;
        this.class_hour = class_hour;
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
    public String getClass_hour() {
        return class_hour;
    }
    public void setClass_hour(String class_hour) {
        this.class_hour = class_hour;
    }

    @Override
    public String toString() {
        return "Teaching{" +
                "idTeaching=" + idTeaching +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", classNum=" + classNum +
                "class_hour="+ class_hour+'}';
    }

    public boolean isEmpty(){
        return (idTeaching == null &&
        subject == null &&
        teacher == null);
    }
}
