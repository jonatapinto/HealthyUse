package br.com.esucri.healthyUse;

import android.app.ActivityManager;
import android.content.Context;
import java.util.Date;
import java.util.List;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ThreadRotina extends AppCompatActivity implements Runnable {
    public ThreadRotina(){
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
        //activityManager.getRunningAppProcesses().get(0).processName;
        this.run();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void run() {
        Boolean mudouApp = true;
        String packageAppCorrente;
        MainActivity tela = new MainActivity();
        int contTeste = 0;

        try {
            do{
                packageAppCorrente = getRunningAppPackageName(this.getBaseContext());
                System.out.println("Caminho do package: "+packageAppCorrente);

              //  switch (packageAppCorrente){
              //      case "AGDGASGDHAS":{
//
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
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fim thread "+ new Date());
    }

    public String getRunningAppPackageName(Context context) {
        ActivityManager activityManager = (ActivityManager)getSystemService(context.ACTIVITY_SERVICE);
        //List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
        return activityManager.getRunningAppProcesses().get(0).processName;
    }
}
