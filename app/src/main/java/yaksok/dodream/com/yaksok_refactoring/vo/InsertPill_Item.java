package yaksok.dodream.com.yaksok_refactoring.vo;

import java.util.List;

public class InsertPill_Item {
    private String userId,name,dosagi;
    private List<String> timeList;
    private List<Integer> elementList;
    private List<String> alarmList;

    public List<String> getAlarmList() { return alarmList; }

    public void setAlarmList(List<String> alarmList) { this.alarmList = alarmList; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosagi() {
        return dosagi;
    }

    public void setDosagi(String dosagi) {
        this.dosagi = dosagi;
    }

    public List<String> getTimeList() { return timeList; }

    public void setTimeList(List<String> timeList) { this.timeList = timeList; }

    public List<Integer> getElementList() { return elementList; }

    public void setElementList(List<Integer> elementList) { this.elementList = elementList; }
}
