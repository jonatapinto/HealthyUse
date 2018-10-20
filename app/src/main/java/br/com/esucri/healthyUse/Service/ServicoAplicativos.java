package br.com.esucri.healthyUse.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.esucri.healthyUse.MainActivity;

public class ServicoAplicativos extends Service {
    public List<Worker> threads = new ArrayList<Worker>();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Script", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("Script", "onStartCommand()");

        Worker w = new Worker(startId);
        w.start();
        threads.add(w);

        return(super.onStartCommand(intent, flags, startId));
        // START_NOT_STICKY
        // START_STICKY
        // START_REDELIVER_INTENT
    }

    public String getRunningAppPackageName() {
        //ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        //List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
        //return activityManager.getRunningAppProcesses().get(0).processName;
        MonitorService monitorService = new MonitorService();

        ActivityManager activityManager = (ActivityManager)monitorService.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        return taskInfo.get(0).topActivity.getPackageName();
    }

    class Worker extends Thread{
        public int count = 0;
        public int startId;
        public boolean ativo = true;

        public Worker(int startId){
            this.startId = startId;
        }

        public void run(){
            Boolean mudouApp = true;
            String packageAppCorrente;
            int contTeste = 0;

            do{
                packageAppCorrente = getRunningAppPackageName();
                System.out.println("Caminho do package: "+packageAppCorrente);

                //  switch (packageAppCorrente){
                //      case "teste":{
                //      }
                // }

                //contTeste = contTeste + 1;
                System.out.println("teste ");
                //teste
                //if (contTeste == 10){
                //    System.out.println("entrou no if");
                //    mudouApp = false;
                //}

            } while(mudouApp);
            System.out.println("testando treads "+ new Date());

            //while(ativo && count < 1000){
            //    try {
            //        Thread.sleep(1000);
            //    } catch (InterruptedException e) {
            //        // TODO Auto-generated catch block
            //        e.printStackTrace();
            //    }
//
            //    count++;
            //    Log.i("Script", "COUNT: "+count);
            //}
            stopSelf(startId);
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        for(int i = 0, tam = threads.size(); i < tam; i++){
            threads.get(i).ativo = false;
        }
    }

}
