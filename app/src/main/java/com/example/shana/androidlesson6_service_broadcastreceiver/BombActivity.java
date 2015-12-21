package com.example.shana.androidlesson6_service_broadcastreceiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TimePicker;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shana on 2015/12/21.
 */
public class BombActivity extends Activity {
    private TimePickerInterface timePickerInterface;
    private BombService bombService;
    private ServiceConnection serviceConnection;
    @OnClick(R.id.activity_bomb_button)
    void onSetTimerButtonClick() {
        bombService.schedule((60 * timePickerInterface.getHour() + timePickerInterface.getMinute()) * 60 * 1000L);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb);
        ButterKnife.bind(this);
        setupTimePickerInterface();
        createService();
    }

    private void createService() {
        Intent intent = new Intent(this, BombService.class);
        startService(intent);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                bombService = ((BombService.BombBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                bombService = null;
            }
        };
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void setupTimePickerInterface() {
        timePickerInterface = new TimePickerInterface((TimePicker) findViewById(R.id.activity_bomb_time_picker));
        timePickerInterface.timePicker.setIs24HourView(true);
        timePickerInterface.setHour(0);
        timePickerInterface.setMinute(0);
    }

    class TimePickerInterface {
        TimePicker timePicker;

        TimePickerInterface(TimePicker timePicker) {
            this.timePicker = timePicker;
        }

        public void setHour(int hour) {
            if (isNewVersion()) {
                timePicker.setHour(hour);
            } else {
                timePicker.setCurrentHour(hour);
            }
        }

        public void setMinute(int minute) {
            if (isNewVersion()) {
                timePicker.setMinute(minute);
            } else {
                timePicker.setCurrentMinute(minute);
            }
        }

        public int getHour() {
            if (isNewVersion()) {
                return timePicker.getHour();
            }
            return timePicker.getCurrentHour();
        }

        public int getMinute() {
            if (isNewVersion()) {
                return timePicker.getMinute();
            }
            return timePicker.getCurrentMinute();
        }

        private boolean isNewVersion() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        }
    }
}
