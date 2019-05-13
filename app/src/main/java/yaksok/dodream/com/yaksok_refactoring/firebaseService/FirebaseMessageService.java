package yaksok.dodream.com.yaksok_refactoring.firebaseService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


import yaksok.dodream.com.yaksok_refactoring.R;
import yaksok.dodream.com.yaksok_refactoring.model.chat.User_info;
import yaksok.dodream.com.yaksok_refactoring.model.user.User_Id;
import yaksok.dodream.com.yaksok_refactoring.view.chat.Chat_Room;
import yaksok.dodream.com.yaksok_refactoring.view.login.Login_activity;

import static com.google.firebase.messaging.RemoteMessage.*;

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String NOTIFICATION_CHANNEL_ID = "7788";
    public static Boolean fireStatus = false;
    public static String userss_name;
    String decodeName, decodeMessage, decodeId, decodeRegiDate;
    String channelId;
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("message??", "메시지 왔어요");

        // 이거 추가 하면
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakeLock.acquire(3000);


        try {
            decodeName = URLDecoder.decode(remoteMessage.getData().get("name"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            decodeMessage = URLDecoder.decode(remoteMessage.getData().get("message"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            decodeId = URLDecoder.decode(remoteMessage.getData().get("id"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            decodeRegiDate = URLDecoder.decode(remoteMessage.getData().get("messageRegiDate"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // sendNotification(decodeName, decodeMessage);
    }

    private void sendNotification(String title, String message) { //FCM서버에서 메세지를 앱으로 보내줄시 백그라운드에서 받는 알림내용

        int NOTIFICATION_ID = 234;

        if (User_Id.getUser_Id().equals("")) {
           /* fireStatus = true;
            userss_name = decodeName;
            Log.d("test_k", "id == null");
            intent = new Intent(this, Login_activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0Request code, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            channelId = "my_channel_01";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title) //알림제목
                    .setContentText(message)    //알림내용
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setChannelId("my_channel_01");

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String CHANNEL_ID = "my_channel_01";
                CharSequence name = "my_channel";
                String Description = "This is my channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(234ID of notification, notificationBuilder.build());
*/
        } else {
//            intent = new Intent(this, Chat_Room.class);
//
//
//            intent.putExtra("send_user", decodeName);
//            intent.putExtra("recived_message", decodeMessage);
//            intent.putExtra("goBack", "123");
//            intent.putExtra("user_id", User_Id.getUser_Id());
//            intent.putExtra("your_id", decodeId);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0  Request code , intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//
//
//            if (!Chat_Room.iInTheChattingRoom) {
//                channelId = getString(R.string.default_notification_channel_id);
//                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                notificationBuilder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(title) //알림제목
//                        .setContentText(message)    //알림내용
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setPriority(Notification.PRIORITY_MAX)
//                        .setContentIntent(pendingIntent)
//                        .setChannelId("my_channel_01");
//                //채널 아이디 값을 정확히 지정해줘야 함
//
//                ChattingMenu.user_me = decodeId;
//                ChattingMenu.user = LoginActivity.userVO.getId();
//                ChattingRoom.msgStatus = false;
//
//
//                Log.d("dddddd", "실행됨");
//
//
//                notificationManager =
//                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                // Since android Oreo notification channel is needed.
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel channel = new NotificationChannel(channelId,
//                            "channel",
//                            NotificationManager.IMPORTANCE_DEFAULT);
//                    notificationManager.createNotificationChannel(channel);
//                    notificationManager.notify(0  ID of notification , notificationBuilder.build());
//
//                    String CHANNEL_ID = "my_channel_01";
//                    CharSequence name = "my_channel";
//                    String Description = "This is my channel";
//                    int importance = NotificationManager.IMPORTANCE_HIGH;
//                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//                    mChannel.setDescription(Description);
//                    mChannel.enableLights(true);
//                    mChannel.setLightColor(Color.RED);
//                    mChannel.enableVibration(true);
//                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                    mChannel.setShowBadge(false);
//                    notificationManager.createNotificationChannel(mChannel);
//                }
//
//                notificationManager.notify(234  ID of notification , notificationBuilder.build());
//                //notify(Id of notification,
//
//
//            } else if (ChattingRoom.iInTheChattingRoom) {
//
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {  // Runnable 의 Run() 메소드에서 UI 접근
//                        SendMessageVO sendVO = new SendMessageVO();
//                        sendVO.setGivingUser(decodeId);
//                        sendVO.setContent(decodeMessage);
//                        sendVO.setReceivingUser(LoginActivity.userVO.getId());
//                        sendVO.setRegidate(decodeRegiDate.substring(11,16));
//                        if(ChattingRoom.your_id.equals(sendVO.getGivingUser())) {
//                            ChattingRoom.albumList.add(sendVO);
//                            //linearLayoutManager.setReverseLayout(true);
//
//                            ChattingRoom.linearLayoutManager.setStackFromEnd(true);
//                            ChattingRoom.mRecyclerView.setAdapter(ChattingRoom.myRecyclerAdapter);
//                            ChattingRoom.mRecyclerView.setLayoutManager(ChattingRoom.linearLayoutManager);
//                        }
//
//                    }
//                });
//
//
//
//
//            }
//
//        }


        }


    } // end class
}



