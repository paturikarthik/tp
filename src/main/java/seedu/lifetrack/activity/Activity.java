package seedu.lifetrack.activity;

public class Activity {

    private String date;
    private String time;
    private String description;

    public Activity(String date, String time,String description){
        this.date = date;
        this.time = time;
        this.description = description;
    }
}