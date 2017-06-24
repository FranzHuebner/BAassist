package edu.ba.baassist;


/**
 * Created by Richard on 21.06.2017.
 */

public class TimetableItem {
    private String subject;
    private String teacher;
    private String time;

    public TimetableItem(String subject, String teacher, String time){
        this.subject = subject;
        this.teacher = teacher;
        this.time = time;
    }

    public String getSubject(){return subject;}

    public void setSubject(String subject) {this.subject = subject;}

    public String getTeacher(){return teacher;}

    public void setTeacher(String teacher) {this.teacher = teacher;}

    public String getTime(){return time;}

    public void setTime(String time){this.time = time;}
}
