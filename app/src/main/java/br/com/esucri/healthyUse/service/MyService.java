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

import br.com.esucri.healthyUse.controller.EstatisticaController;
import br.com.esucri.healthyUse.model.Estatistica;


public class MyService extends Service {

    Integer cont = 0;
    int _idCorrente; //Utilizado para pegar sempre o maior _id da estatistica

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
        EstatisticaController controller = new EstatisticaController(getBaseContext());
        public void run()
        {
            Date currentTime = Calendar.getInstance().getTime();


            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            String dateFormatted = formatter.format(currentTime);

            switch (getForegroundApp()){
                case "com.whatsapp": {
                    Estatistica estatistica = new Estatistica();
                    estatistica.setAplicativo("WHATSAPP");
                    if (cont == 0) {
                        estatistica.setDataHoraInicio(dateFormatted);
                        estatistica.setDataHoraFim(dateFormatted);
                        controller.create(estatistica);
                        //chamar uma função que deve ser criado no controller que busque o MAX(_id)
                        _idCorrente = controller.retrieveId();
                        Log.w("AtividadeGravandoIni", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont + " _id " + _idCorrente);
                    }
                    if (cont > 0){
                        estatistica.setDataHoraFim(dateFormatted);
                        estatistica.setId(_idCorrente);
                        controller.update(estatistica);
                        Log.w("AtividadeGravandoFim", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont + " _id " + _idCorrente);
                    }
                    Log.w("AtividadeEWhats", getForegroundApp());
                    cont = cont + 1;
                    break;
                }
                case "com.instagram.android": {
                    Log.w("AtividadeEInsta", getForegroundApp()+ " CONTADOR: "+cont + " _id " + _idCorrente);
                    break;
                }
                case "com.facebook.katana": {
                    Log.w("AtividadeEFace", getForegroundApp()+ " CONTADOR: "+cont + " _id " + _idCorrente);
                    break;
                }
            }

            Log.w("Atividade", getForegroundApp() + " " + dateFormatted + " CONTADOR: "+cont + " _id " + _idCorrente);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Serviço Terminado", Toast.LENGTH_SHORT).show();
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

