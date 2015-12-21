package com.example.shana.androidlesson6_service_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by shana on 2015/12/21.
 */
public class BombReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Time over", Toast.LENGTH_SHORT).show();
    }
}
