package yaksok.dodream.com.yaksok_refactoring.Adapter.chat;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateTime {
    public String str;
    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;

    public CalculateTime() {
        long curTime = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        str = dayTime.format(new Date(curTime));



        Log.e("CalculateTime: ", str);
    }

    public static String formatTimeString(long regTime) {

        long curTime = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String str = dayTime.format(new Date(curTime));

        str.replaceAll("-","").replaceAll(" ","").replaceAll(":","");









        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < SEC) {
            msg = "방금 전";
        } else if ((diffTime /= SEC) < MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= MIN) < HOUR) {
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= HOUR) < DAY) {
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= DAY) < MONTH) {
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }
}
