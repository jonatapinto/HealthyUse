package br.com.esucri.healthyUse;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Runnable{

    Button botaoRotinas;
    Button botaoParametros;
    Button botaoRelatorios;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRotinas = (Button) findViewById(R.id.botaoRotinas);
        botaoRotinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListRotinaActivity.class);
                startActivity(intent);
            }
        });

        botaoParametros = (Button) findViewById(R.id.botaoParametros);
        botaoParametros.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ParametroActivity.class);
                startActivity(intent);
            }
        });

        botaoRelatorios = (Button) findViewById(R.id.botaoResultado);
        botaoRelatorios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ResultadoActivity.class);
                startActivity(intent);
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                String app = app = getRunningAppPackageName();
               Toast.makeText(MainActivity.this, app, Toast.LENGTH_SHORT).show();
            }
        });

        //Execução de Thread

        //run();
        //System.out.println("Iniciando execução");
        //ThreadRotina t1 = new ThreadRotina();

        //super.runOnUiThread(t1);
        //ExecutorService threadExecutor = Executors.newCachedThreadPool();
        //threadExecutor.execute(t1);
        //threadExecutor.shutdown();
        //System.out.println("Finalizado");
    }

    public String getRunningAppPackageName() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
       return activityManager.getRunningAppProcesses().get(0).processName;
    }

    @Override
    public void run() {
        Boolean mudouApp = true;
        String packageAppCorrente;
        MainActivity tela = new MainActivity();
        int contTeste = 0;
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        //for (ActivityManager.RunningTaskInfo runningTaskInfo : activityManager.getRunningTasks(5)) {
        //    System.out.println("TESTE"+runningTaskInfo.topActivity.toString());
        //};
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
    }
}
