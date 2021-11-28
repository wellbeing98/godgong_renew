package com.example.godgong;

public class SubjectInform {
    private String subjectCode;
    private String professor;
    private String major;
    private String subject;
    public String getSubjectCode(){
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode){
        this.subjectCode = subjectCode;
    }
    public void setProfessor(String professor){
        this.professor = professor;
    }
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getsubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

//    public String getKorean() {
//        return Korean;
//    }
//
//    public void setKorean(String korean) {
//        Korean = korean;
//    }
    public SubjectInform(String subjectCode, String professor, String major,String subject) {
        this.subjectCode = subjectCode;
        this.professor = professor;
        this.major = major;
        this.subject = subject;
    }
}
