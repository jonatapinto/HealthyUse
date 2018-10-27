package br.com.esucri.healthyUse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

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
        // startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

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
