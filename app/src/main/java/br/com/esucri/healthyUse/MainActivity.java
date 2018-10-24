package br.com.esucri.healthyUse;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.esucri.healthyUse.service.ProcessList.COLUMN_PROCESS_NAME;
import static br.com.esucri.healthyUse.service.ProcessList.COLUMN_PROCESS_PROP;
import static br.com.esucri.healthyUse.service.ProcessList.COLUMN_PROCESS_COUNT;
import static br.com.esucri.healthyUse.service.ProcessList.COLUMN_PROCESS_TIME;
import br.com.esucri.healthyUse.service.MonitorApp;
import br.com.esucri.healthyUse.service.MonitorService;
import br.com.esucri.healthyUse.utils.BancoDeDados;

public class MainActivity extends Activity implements MonitorService.ServiceCallback{

    Button botaoRotinas;
    Button botaoParametros;
    Button botaoRelatorios;
    //Button button;
    private ArrayList<HashMap<String,Object>> processList;
    private MonitorService backgroundService;
    private MyCustomAdapter adapter = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRotinas = (Button) findViewById(R.id.botaoRotinas);
        botaoRotinas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListaRotinaActivity.class);
                startActivity(intent);
            }
        });

        botaoParametros = (Button) findViewById(R.id.botaoParametros);
        botaoParametros.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListaParametroActivity.class);
                startActivity(intent);
            }
        });

        botaoRelatorios = (Button) findViewById(R.id.botaoResultado);
        botaoRelatorios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), ResultadoActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.listViewProcess);
        //createAdapter();

        //this.bindService(new Intent(this, MonitorService.class),serviceConnection,Context.BIND_AUTO_CREATE);
    }

    private void createAdapter(){
        processList = ((MonitorApp)getApplication()).getProcessList();
        adapter = new MyCustomAdapter(this, processList, R.layout.activity_main,
                new String[]{
                                COLUMN_PROCESS_NAME,
                                COLUMN_PROCESS_PROP, // TODO: you may calculate and pre-fill this field
                                // from COLUMN_PROCESS_COUNT and COLUMN_PROCESS_TIME
                                // so eliminating the need to use the custom adapter
                        },
                new int[]{
                                android.R.id.text1,
                                android.R.id.text2
                        });

        listView.setAdapter(adapter);
    }

    // callback method invoked by the service when foreground process changed
    @Override
    public void sendResults(int resultCode, Bundle b){
        adapter.notifyDataSetChanged();
    }

    private class MyCustomAdapter extends SimpleAdapter{
        MyCustomAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
            super(context, data, resource, from, to);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent){
            View result = super.getView(position, convertView, parent);

            // TODO: customize process statistics display
            int count = (Integer)(processList.get(position).get(COLUMN_PROCESS_COUNT));
            int seconds = (Integer)(processList.get(position).get(COLUMN_PROCESS_TIME));

            return result;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName className, IBinder service){
            MonitorService.LocalBinder binder = (MonitorService.LocalBinder)service;
            backgroundService = binder.getService();
            backgroundService.setCallback(MainActivity.this);
            backgroundService.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName className){
            backgroundService = null;
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if(backgroundService != null){
            backgroundService.setCallback(this);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(backgroundService != null){
            backgroundService.setCallback(null);
        }
    }

    //public String getRunningAppPackageName() {
    //    ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
    //    List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
    //   return activityManager.getRunningAppProcesses().get(0).processName;
    //}

    //@Override
    //public void run() {
    //    Boolean mudouApp = true;
    //    String packageAppCorrente;
    //    MainActivity tela = new MainActivity();
   //    int contTeste = 0;
   //    ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

   //    //for (ActivityManager.RunningTaskInfo runningTaskInfo : activityManager.getRunningTasks(5)) {
   //    //    System.out.println("TESTE"+runningTaskInfo.topActivity.toString());
   //    //};
   //    do{
   //        packageAppCorrente = getRunningAppPackageName();
   //        System.out.println("Caminho do package: "+packageAppCorrente);

   //        //  switch (packageAppCorrente){
   //        //      case "teste":{
   //        //      }
   //        // }

   //        //contTeste = contTeste + 1;
   //        System.out.println("teste ");
   //        //teste
   //        //if (contTeste == 10){
   //        //    System.out.println("entrou no if");
   //        //    mudouApp = false;
   //        //}

   //    } while(mudouApp);
   //    System.out.println("testando treads "+ new Date());
   //}

    public void startService(View view){
        Intent it = new Intent("SERVICO_INICIAR");
        it.setPackage(this.getPackageName());
        startService(it);
    }

    public void stopService(View view){
        Intent it = new Intent("SERVICO_INICIAR");
        stopService(it);
    }

    public void insereDadosSimulados(){
        String inserts;
        SQLiteDatabase instanciaDB;
        BancoDeDados db = null;

        instanciaDB = db.getWritableDatabase();

        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 07:40:00','2018-09-10 07:43:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 07:43:00','2018-09-10 07:50:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 09:30:00','2018-09-10 09:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 09:40:00','2018-09-10 09:45:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 11:50:00','2018-09-10 11:55:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 11:56:00','2018-09-10 11:59:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:10:00','2018-09-10 12:11:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 12:11:00','2018-09-10 12:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:13:00','2018-09-10 12:15:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 12:20:00','2018-09-10 12:25:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 12:50:00','2018-09-10 12:51:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:55:00','2018-09-10 13:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 15:40:00','2018-09-10 15:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 15:47:00','2018-09-10 15:48:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 15:48:00','2018-09-10 15:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 15:52:00','2018-09-10 15:54:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 18:02:00','2018-09-10 18:03:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 18:03:00','2018-09-10 18:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 18:32:00','2018-09-10 18:33:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 18:42:00','2018-09-10 18:58:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 19:22:00','2018-09-10 19:42:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 19:46:00','2018-09-10 19:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 20:03:00','2018-09-10 20:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 20:05:00','2018-09-10 20:32:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 20:32:00','2018-09-10 20:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 20:36:00','2018-09-10 21:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 22:02:00','2018-09-10 22:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 23:30:00','2018-09-10 23:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 23:47:00','2018-09-10 23:53:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 23:59:00','2018-09-11 00:08:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 07:12:00','2018-09-11 07:43:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 07:43:00','2018-09-11 07:50:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 09:30:00','2018-09-11 09:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 09:40:00','2018-09-11 09:45:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:00:00','2018-09-11 12:01:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:01:00','2018-09-11 12:05:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:10:00','2018-09-11 12:11:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:11:00','2018-09-11 12:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:13:00','2018-09-11 12:15:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 12:20:00','2018-09-11 12:25:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:50:00','2018-09-11 12:51:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:55:00','2018-09-11 13:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 15:40:00','2018-09-11 15:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 15:47:00','2018-09-11 15:48:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 15:48:00','2018-09-11 15:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 15:52:00','2018-09-11 15:54:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 18:02:00','2018-09-11 18:03:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 18:03:00','2018-09-11 18:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 18:32:00','2018-09-11 18:33:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 18:42:00','2018-09-11 18:58:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 19:22:00','2018-09-11 19:42:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 19:46:00','2018-09-11 19:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 20:03:00','2018-09-11 20:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 20:05:00','2018-09-11 20:32:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 20:32:00','2018-09-11 20:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 20:36:00','2018-09-11 21:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 22:02:00','2018-09-11 22:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 23:30:00','2018-09-11 23:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 23:47:00','2018-09-11 23:53:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 23:59:00','2018-09-12 00:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 07:40:00','2018-09-12 07:43:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 07:43:00','2018-09-12 07:50:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 09:30:00','2018-09-12 09:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 09:40:00','2018-09-12 09:45:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 11:50:00','2018-09-12 11:55:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 11:56:00','2018-09-12 11:59:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:10:00','2018-09-12 12:11:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 12:11:00','2018-09-12 12:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:13:00','2018-09-12 12:15:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 12:20:00','2018-09-12 12:25:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 12:50:00','2018-09-12 12:51:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:55:00','2018-09-12 13:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 15:40:00','2018-09-12 15:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 15:47:00','2018-09-12 15:48:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 15:48:00','2018-09-12 15:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 15:52:00','2018-09-12 15:54:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 18:02:00','2018-09-12 18:03:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 18:03:00','2018-09-12 18:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 18:32:00','2018-09-12 18:33:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 18:42:00','2018-09-12 18:58:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 19:22:00','2018-09-12 19:42:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 19:46:00','2018-09-12 19:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 20:03:00','2018-09-12 20:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 20:05:00','2018-09-12 20:32:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 20:32:00','2018-09-12 20:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 20:36:00','2018-09-12 21:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 22:02:00','2018-09-12 22:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 23:30:00','2018-09-12 23:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 23:47:00','2018-09-12 23:53:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 23:59:00','2018-09-13 00:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 00:33:00','2018-09-13 00:49:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 00:50:00','2018-09-13 00:55:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 00:56:00','2018-09-13 00:59:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 07:23:00','2018-09-13 07:43:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 07:43:00','2018-09-13 07:50:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 09:30:00','2018-09-13 09:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 09:40:00','2018-09-13 09:45:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:00:00','2018-09-13 12:01:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:01:00','2018-09-13 12:05:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:10:00','2018-09-13 12:11:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:11:00','2018-09-13 12:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 12:13:00','2018-09-13 12:15:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 12:20:00','2018-09-13 12:25:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:50:00','2018-09-13 12:51:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:55:00','2018-09-13 13:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 15:40:00','2018-09-13 15:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 15:47:00','2018-09-13 15:48:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 15:48:00','2018-09-13 15:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 15:52:00','2018-09-13 15:54:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 18:02:00','2018-09-13 18:03:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 18:03:00','2018-09-13 18:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 18:32:00','2018-09-13 18:33:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 18:42:00','2018-09-13 18:58:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 19:22:00','2018-09-13 19:42:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 19:46:00','2018-09-13 19:52:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 20:03:00','2018-09-13 20:04:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 20:05:00','2018-09-13 20:32:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 20:32:00','2018-09-13 20:35:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 20:36:00','2018-09-13 21:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 22:02:00','2018-09-13 22:12:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 23:30:00','2018-09-13 23:46:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 23:47:00','2018-09-13 23:53:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 23:59:00','2018-09-14 00:02:00');";
        instanciaDB.execSQL(inserts);
        inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FINAL) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-14 00:28:00','2018-09-14 00:37:00');";
        instanciaDB.execSQL(inserts);

    }


}
