package br.com.esucri.helthyuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botaoOficios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoOficios = (Button) findViewById(R.id.botaoOficios);
        botaoOficios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*chamar outra tela*/
            }
        });
    }
}
