package com.example.pmprogect.fragment.home.notice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.example.pmprogect.R;
import com.example.pmprogect.base.BaseNoBarActivity;
import com.example.pmprogect.fragment.home.test.customview.CustomToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class NoticeActivity extends BaseNoBarActivity {

    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomtoolbar;

    @Override
    protected int setView() {
        return R.layout.activity_notifaction;

    }

    @Override
    protected void afterBinder() {
        mCustomtoolbar.setMainTitle("8.0通知适配");
        setHeadView(mCustomtoolbar);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createnotificationChannel(channelId,channelName,importance);
            channelId ="subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createnotificationChannel(channelId,channelName,importance);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createnotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId,channelName,importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    @OnClick({R.id.btn_send, R.id.btn_subscribe})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendCharMessage();
                break;
            case R.id.btn_subscribe:
                sendSubscribeMessage();
                break;
        }
    }

    /**
     * 发送聊天消息
     */
    private void sendCharMessage() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //8.0打开通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("chat");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }
        Notification notification = new NotificationCompat.Builder(this,"chat")
                .setContentTitle("收到一条聊天信息")
                .setContentText("今天中午吃什么?")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(1)
                .build();
        manager.notify(1,notification);
    }

    /**
     * 发送订阅消息
     */
    private void sendSubscribeMessage() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //8.0打开通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("subscribe");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }
        Notification notification = new NotificationCompat.Builder(this,"subscribe")
                .setContentTitle("收到一条订阅信息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(2,notification);
    }
}
