package br.com.esucri.healthyUse.service;

import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Intent;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service
{
    private static Timer timer = new Timer();

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        startService();
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            //switch (getForegroundApp()){
            //    case "com.whatsapp":
            //        //--Salva WhatsApp
            //    case "com.facebook":
            //        //--Salva Facebook
            //    case "com.instagram":
            //        //--Salva Instagram
            //}

            //Date currentTime = Calendar.getInstance().getTime();

            //DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            //String dateFormatted = formatter.format(currentTime);

            Log.w("Saymon", getForegroundApp());
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
    }

    private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        }
    };

    public String getForegroundApp() {

        String foregroundApp = null;

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Service.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();

        UsageEvents usageEvents = mUsageStatsManager.queryEvents(time - 1000 * 3600, time);
        UsageEvents.Event event = new UsageEvents.Event();
        while (usageEvents.hasNextEvent()) {
            usageEvents.getNextEvent(event);
            if(event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                foregroundApp = event.getPackageName();
            }
        }

        return foregroundApp ;
    }
}

