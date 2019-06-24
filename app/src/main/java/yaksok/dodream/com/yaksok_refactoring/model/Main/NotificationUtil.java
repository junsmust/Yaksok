package yaksok.dodream.com.yaksok_refactoring.model.Main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.view.Alarm_On;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;

public class NotificationUtil extends BroadcastReceiver{
    public static String pillNo;
    public static String userId;
    public static String userName;
    public static String pillName;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("알람여부", "리시브 들어옴");

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        pillNo = intent.getStringExtra("PillNo");
        Intent intentActivity = new Intent(context, Login_activity.class); //그메세지를 클릭했을때 불러올엑티비티를 설정함
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);//플레그부분은 옵션인데 나도 자세하게 몰르겠음
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentActivity, 0);
        String ticker = "ticker";//여긴 알림바에 등록될 글이랑 타이틀 적는곳.
        String title = "알림";
        String text = "약 복용시간 입니다";



         userId = intent.getStringExtra("takingUser");
         pillNo = intent.getStringExtra("PillNo");
         userName = intent.getStringExtra("a_name");
         pillName = intent.getStringExtra("pill_Names");

        Log.d("약 번호",pillNo+userId+userName);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.yaksokloggo)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setTicker(ticker);
        Notification notification = builder.build();
        nm.notify(1, notification);

        Intent intent_ = new Intent(context, Alarm_On.class);
        intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);// 이거 안해주면 안됨
        context.startActivity(intent_);
    }
}
