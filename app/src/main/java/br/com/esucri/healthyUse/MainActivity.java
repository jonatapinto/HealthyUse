package br.com.esucri.healthyUse;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
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
    BancoDeDados db;

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
                Intent intent = new Intent(getBaseContext(), ListaResultadoActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.listViewProcess);
        //createAdapter();

        //this.bindService(new Intent(this, MonitorService.class),serviceConnection,Context.BIND_AUTO_CREATE);
        //db.insereDadosSimulados();
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

}
