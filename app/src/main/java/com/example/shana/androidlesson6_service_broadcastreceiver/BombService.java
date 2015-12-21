package com.example.shana.androidlesson6_service_broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shana on 2015/12/21.
 */
public class BombService extends Service {
    public static final String ACTION = "BOMB_SERVICE_ACTION";
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        showToast("onCreate");
        System.out.println("Service Create: " + android.os.Process.myPid() + " ,Task: " + android.os.Process.myTid());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showToast("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showToast("onDestroy");
        cancelTimer();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }

    class BombBinder extends Binder {
        BombService getService() {
            return BombService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showToast("onBind");
        return new BombBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        showToast("onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showToast("onUnbind");
        return super.onUnbind(intent);
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void schedule(long delay) {
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendBroadcast(new Intent(ACTION));
                BombService.this.stopSelf();
            }
        }, delay);
    }
}
