package br.com.esucri.healthyUse;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLOutput;
import java.util.ArrayList;

import br.com.esucri.healthyUse.controller.RotinaController;
import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.BancoDeDados;

public class ListaRotinaActivity extends AppCompatActivity {

    ListView lista;
    RotinaController rotinas;
    ArrayList<Rotina> listview_Rotina;
    Rotina rotina;
    ArrayAdapter adapter;
    Button botaoNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rotina);

        botaoNovo = (Button) findViewById(R.id.botaoNovo);

        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RotinaActivity.class);
                startActivity(intent);
            }
        });

        RotinaController crud = new RotinaController(getBaseContext());
        final Cursor cursor = crud.retrieve();


        String[] campos = {"_id","NOME","HORA_INICIO","HORA_FIM","STATUS"};
        int[] componentes = {R.id.textView_id, R.id.textViewNome, R.id.textViewHoraInicio, R.id.textViewHoraFinal, R.id.textViewStatus};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_rotina, cursor, campos, componentes, 0);
        ListView lista = (ListView) findViewById(R.id.listViewRotinas);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String idRotina = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                Intent intent = new Intent(ListaRotinaActivity.this, RotinaActivity.class);
                intent.putExtra("id", idRotina);

                startActivity(intent);
                finish();
            }
        });

        //Listar rotinas já gravadas para poder alterar
        //lista = (ListView) findViewById(R.id.listViewRotinas);
        //lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        //        Rotina rotinaEscolhida = (Rotina) adapter.getItemAtPosition(position);

        //        Intent i = new Intent(ListaRotinaActivity.this,RotinaActivity.class);
        //        i.putExtra("rotina-escolhida",rotinaEscolhida);
        //    }
        //});
    }

    //public void carregarRotina() {
    //    rotinas = new RotinaController(ListaRotinaActivity.this);
    //    listview_Rotina = rotinas.getListaRotinas();
    //}

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

}
