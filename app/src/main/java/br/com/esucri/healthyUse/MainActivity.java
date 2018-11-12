package br.com.esucri.healthyUse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import br.com.esucri.healthyUse.controller.EstatisticaController;
import br.com.esucri.healthyUse.model.Estatistica;
import br.com.esucri.healthyUse.service.MyService;
import br.com.esucri.healthyUse.utils.BancoDeDados;

public class MainActivity extends Activity {

    Button botaoRotinas;
    Button botaoParametros;
    Button botaoRelatorios;
    Button botaoPermissao;
    BancoDeDados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // primeira chamada
        //startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        EstatisticaController controller = new EstatisticaController(getBaseContext());

        Estatistica est1 = new Estatistica();
        est1.setAplicativo("WHATSAPP");
        est1.setDataHoraInicio("2018-11-12 08:00:00");
        est1.setDataHoraFim("2018-11-12 10:02:37");
        controller.create(est1);
        System.out.println("est1");

        Estatistica est2 = new Estatistica();
        est2.setAplicativo("INSTAGRAM");
        est2.setDataHoraInicio("2018-11-12 14:00:00");
        est2.setDataHoraFim("2018-11-12 15:37:42");
        controller.create(est2);
        System.out.println("est2");

        Estatistica est3 = new Estatistica();
        est3.setAplicativo("FACEBOOK");
        est3.setDataHoraInicio("2018-11-12 10:30:00");
        est3.setDataHoraFim("2018-11-12 11:07:23");
        controller.create(est3);
        System.out.println("est3");

        // chamar sempre menos na primeira vez
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);

        botaoPermissao = (Button) findViewById(R.id.botaoPermissao);
        botaoPermissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            }
        });

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
    }
}
