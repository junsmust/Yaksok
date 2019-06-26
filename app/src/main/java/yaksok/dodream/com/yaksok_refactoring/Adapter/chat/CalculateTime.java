package yaksok.dodream.com.yaksok_refactoring.Adapter.chat;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalculateTime {
    public String str;
    int year_f,month_f,day_f,hour_f,min_f,sec_f;



    public String formatTimeString(String regTime) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        Date date = new Date();
        String getTime = formatter.format(date);



        String lastTime = null;


        boolean isChanged = true;

        String year = getTime.substring(0,4);
        String month = getTime.substring(4,6);
        String day = getTime.substring(6,8);
        String hour = getTime.substring(8,10);
        String min = getTime.substring(10,12);
        String milsec = getTime.substring(12);



        String year2 = regTime.substring(0,4);
        String month2 = regTime.substring(4,6);
        String day2 = regTime.substring(6,8);
        String hour2 = regTime.substring(8,10);
        String min2 = regTime.substring(10,12);
        String milsec2 = regTime.substring(12);



        System.out.println("year "+year);
        System.out.println("month "+month);
        System.out.println("day "+day);
        System.out.println("hour "+hour);
        System.out.println("min "+min);
        System.out.println("milsec "+milsec);

        System.out.println("year2 "+year2);
        System.out.println("month2 "+month2);
        System.out.println("day2 "+day2);
        System.out.println("hour2 "+hour2);
        System.out.println("min2 "+min2);
        System.out.println("milsec2 "+milsec2);

        while(true){
            if(!year.equals(year2)){
                year_f = Integer.parseInt(year)-Integer.parseInt(year2);
                System.out.println("year"+year_f);
                lastTime = year_f+"년 전";
                return lastTime;
            }if(!month.equals(month2)){
                month_f = Integer.parseInt(month)-Integer.parseInt(month2);
                System.out.println("month"+month_f);
                return month_f+"달 전";
            }if(!day.equals(day2)){
                day_f = Integer.parseInt(day)-Integer.parseInt(day2);
                System.out.println("day"+day_f);
                lastTime =  day_f+"일 전";
                return lastTime;

            }if(!hour.equals(hour2)){
                hour_f = Integer.parseInt(hour)-Integer.parseInt(hour2);
                System.out.println("hour"+hour_f);
                lastTime = hour_f+"시간 전";
                return lastTime;

            }if(!min.equals(min2)){
                min_f = Integer.parseInt(min)-Integer.parseInt(min2);
                System.out.println("min"+min_f);
                lastTime = min_f+"분 전";
                return lastTime;

            }if(!milsec.equals(milsec2)){
                sec_f = Integer.parseInt(milsec)-Integer.parseInt(milsec2);
                System.out.println("sec"+sec_f);
                lastTime = "방금 전";
                return lastTime;
            }
        }


    }
}
