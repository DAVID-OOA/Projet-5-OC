package com.oconte.david.mynews.OptionMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.oconte.david.mynews.Utils.AlarmWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        getWorkManager();
    }

    private void getWorkManager(){
        Data data = new Data.Builder()
                .putString(AlarmWorker.CHANNEL_ID, "There are number of page for the result searchNotification.")
                .build();

        //This is the subclass of periodicWorkRequest
        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(AlarmWorker.class,1, TimeUnit.HOURS)
                .setInputData(data)
                .build();

        WorkManager.getInstance().enqueue(periodicWorkRequest);
    }
}