package br.com.esucri.healthyUse.service;

import android.app.KeyguardManager;
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
    Integer cont2 = 0;

    String nome_aplicativo = "";
    String data_hora_inicial = "";

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

    public boolean gravar (String nome_aplicativo, String data_hora_inicial, String data_hora_fim){
        EstatisticaController controller = new EstatisticaController(getBaseContext());
        Estatistica estatistica = new Estatistica();
        estatistica.setAplicativo(nome_aplicativo);
        estatistica.setDataHoraInicio(data_hora_inicial);
        estatistica.setDataHoraFim(data_hora_fim);
        controller.create(estatistica);
        Log.w("AtividadeGravada",getForegroundApp()+ " " + data_hora_fim + " CONTADOR: "+cont);
        return true;
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }

    private class mainTask extends TimerTask {

        public void run() {
            Date currentTime = Calendar.getInstance().getTime();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

            String dateFormatted = formatter.format(currentTime);

            KeyguardManager myKM = (KeyguardManager) getBaseContext().getSystemService(getBaseContext().KEYGUARD_SERVICE);
            if( myKM.inKeyguardRestrictedInputMode()) {//tela bloqueada
                if ((getForegroundApp().equals("com.whatsapp")) ||
                        (getForegroundApp().equals("com.instagram.android")) ||
                        (getForegroundApp().equals("com.facebook.katana"))){
                    if (cont2 == 0){
                        gravar(nome_aplicativo,data_hora_inicial,dateFormatted);
                        cont = 0;
                    }
                    cont2 = cont2 + 1;
                }else {
                    cont2 = 0;
                    Log.w("AtividadeTelaBloqueada", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont + " CONTADOR2: "+cont2);
                }
            } else { //tela desbloqueada
                if ((getForegroundApp().equals("com.whatsapp")) ||
                    (getForegroundApp().equals("com.instagram.android")) ||
                    (getForegroundApp().equals("com.facebook.katana"))){
                    if (cont == 0) {
                        nome_aplicativo = getForegroundApp();
                        data_hora_inicial = dateFormatted;
                        Log.w("AtividadeGravandoInicio", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                    }
                    if (!getForegroundApp().equals(nome_aplicativo)){
                        if (cont != 0){
                            gravar(nome_aplicativo,data_hora_inicial,dateFormatted);
                        }
                    }
                    cont = cont + 1;
                    Log.w("Atividade: ", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                }else{
                    if (!getForegroundApp().equals(nome_aplicativo)) {
                        if (cont != 0) {
                            gravar(nome_aplicativo, data_hora_inicial, dateFormatted);
                        }
                        cont = 0;
                    }
                    Log.w("Atividade",  getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                }
            }
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

