package yaksok.dodream.com.yaksok_refactoring.Adapter.MyPill;

import java.util.List;

public class MyPillItem {

    private String name;
    private int dosagi, time_of_day;
    private String family;

    public MyPillItem(){}
    public MyPillItem(String name, int dosagi, int time_of_day, String family){
        this.name = name;
        this.dosagi = dosagi;
        this.time_of_day = time_of_day;
        this.family = family;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getDosagi() { return dosagi; }

    public void setDosagi(int dosagi) { this.dosagi = dosagi; }

    public int getTime_of_day() { return time_of_day; }

    public void setTime_of_day(int time_of_day) { this.time_of_day = time_of_day; }

    public String getFamily() { return family; }

    public void setFamily(String family) { this.family = family; }
}
