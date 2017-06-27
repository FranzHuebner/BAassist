package edu.ba.baassist;


/**
 * Class for the objects hich will be displayed in the timetable
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

    String getSubject(){return subject;}

    public void setSubject(String subject) {this.subject = subject;}

    String getTeacher(){return teacher;}

    public void setTeacher(String teacher) {this.teacher = teacher;}

    String getTime(){return time;}

    public void setTime(String time){this.time = time;}
}
