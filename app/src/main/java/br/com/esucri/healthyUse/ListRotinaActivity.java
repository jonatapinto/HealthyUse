package br.com.esucri.healthyUse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.esucri.healthyUse.controller.RotinaController;
import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.BancoDeDados;

public class ListRotinaActivity extends AppCompatActivity {

    ListView lista;
    RotinaController rotinas;
    ArrayList<Rotina> listview_Rotina;
    Rotina rotina;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rotina);

        //lista = (ListView) findViewById(R.id.listview_Rotina);

        Button botaoNovo = (Button) findViewById(R.id.botaoNovo);
        botaoNovo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RotinaActivity.class);
                startActivity(intent);
            }
        });
    }

    //public void carregarRotina() {
    //    rotinas = new RotinaController(ListRotinaActivity.this);
    //    listview_Rotina = rotinas.getListaRotinas();
    //}

}
