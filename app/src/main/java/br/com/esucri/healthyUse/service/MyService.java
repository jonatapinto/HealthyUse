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

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 1000);
    }

    private class mainTask extends TimerTask
    {
        EstatisticaController controller = new EstatisticaController(getBaseContext());
        public void run()
        {   Estatistica estatistica = new Estatistica();

            Date currentTime = Calendar.getInstance().getTime();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

            String dateFormatted = formatter.format(currentTime);

            KeyguardManager myKM = (KeyguardManager) getBaseContext().getSystemService(getBaseContext().KEYGUARD_SERVICE);
            if( myKM.inKeyguardRestrictedInputMode()) {
                estatistica.setAplicativo(nome_aplicativo);
                estatistica.setDataHoraInicio(data_hora_inicial);
                estatistica.setDataHoraFim(dateFormatted);
                controller.create(estatistica);
                nome_aplicativo = getForegroundApp();
                Log.w("AtividadeGravada",getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                Log.w("AtividadeTelaBloqueada", getForegroundApp());
            } else {
                switch (getForegroundApp()){
                    case "com.whatsapp": {
                        if (cont == 0) {
                            nome_aplicativo = "com.whatsapp";
                            data_hora_inicial = dateFormatted;
                            Log.w("AtividadeGravandoIni", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        if (!getForegroundApp().equals(nome_aplicativo)){ //mudou o app
                            //grava registro
                            estatistica.setAplicativo(nome_aplicativo);
                            estatistica.setDataHoraInicio(data_hora_inicial);
                            estatistica.setDataHoraFim(dateFormatted);
                            controller.create(estatistica);
                            nome_aplicativo = getForegroundApp();
                            Log.w("AtividadeGravada",getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        cont = cont + 1;
                        Log.w("AtividadeEWhats", getForegroundApp());
                        break;
                    }
                    case "com.instagram.android": {
                        if (cont == 0) {
                            nome_aplicativo = "com.instagram.android";
                            data_hora_inicial = dateFormatted;
                            Log.w("AtividadeGravandoIni", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        if (!getForegroundApp().equals(nome_aplicativo)){ //mudou o app
                            //grava registro
                            estatistica.setAplicativo(nome_aplicativo);
                            estatistica.setDataHoraInicio(data_hora_inicial);
                            estatistica.setDataHoraFim(dateFormatted);
                            controller.create(estatistica);
                            nome_aplicativo = getForegroundApp();
                            Log.w("AtividadeGravada",getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        cont = cont + 1;
                        Log.w("AtividadeEInsta", getForegroundApp());
                        break;
                    }
                    case "com.facebook.katana": {
                        if (cont == 0) {
                            nome_aplicativo = "com.facebook.katana";
                            data_hora_inicial = dateFormatted;
                            Log.w("AtividadeGravandoIni", getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        if (!getForegroundApp().equals(nome_aplicativo)){ //mudou o app
                            //grava registro
                            estatistica.setAplicativo(nome_aplicativo);
                            estatistica.setDataHoraInicio(data_hora_inicial);
                            estatistica.setDataHoraFim(dateFormatted);
                            controller.create(estatistica);
                            nome_aplicativo = getForegroundApp();
                            Log.w("AtividadeGravada",getForegroundApp()+ " " + dateFormatted + " CONTADOR: "+cont);
                        }
                        cont = cont + 1;
                        Log.w("AtividadeEFace", getForegroundApp());
                        break;
                    }
                    default:
                        if (!getForegroundApp().equals(nome_aplicativo)){
                            if (cont != 0) { //grava registro
                                estatistica.setAplicativo(nome_aplicativo);
                                estatistica.setDataHoraInicio(data_hora_inicial);
                                estatistica.setDataHoraFim(dateFormatted);
                                controller.create(estatistica);
                                Log.w("AtividadeGravada",getForegroundApp() + dateFormatted + " CONTADOR: "+cont);
                            }
                            //nome_aplicativo = getForegroundApp();
                            cont = 0;
                            break;
                        }
                }
                Log.w("Atividade", getForegroundApp() + " " + dateFormatted);
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

