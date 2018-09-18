package br.com.esucri.healthyUse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botaoRotinas;
    Button botaoRelatorios;

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

        botaoRelatorios = (Button) findViewById(R.id.botaoRelatorios);
        botaoRelatorios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RelatoriosActivity.class);
                startActivity(intent);
            }
        });
    }
}
