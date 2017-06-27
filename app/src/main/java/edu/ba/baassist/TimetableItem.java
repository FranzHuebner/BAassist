package edu.ba.baassist;


/**
 * Class for the objects which will be displayed in the timetable.
 */

public class TimetableItem {
    private String subject;
    private String teacher;
    private String time_room;

    public TimetableItem(String subject, String teacher, String time_room){
        this.subject = subject;
        this.teacher = teacher;
        this.time_room = time_room;
    }

    //Getter and Setter.
    String getSubject(){return subject;}

    public void setSubject(String subject) {this.subject = subject;}

    String getTeacher(){return teacher;}

    public void setTeacher(String teacher) {this.teacher = teacher;}

    String getTime_room(){return time_room;}

    public void setTime_room(String time_room){this.time_room = time_room;}
}
