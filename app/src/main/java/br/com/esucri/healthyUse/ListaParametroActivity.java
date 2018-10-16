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

import java.util.ArrayList;

import br.com.esucri.healthyUse.controller.ParametroController;
import br.com.esucri.healthyUse.model.Parametro;

public class ListaParametroActivity extends AppCompatActivity {

    ListView lista;
    ParametroController parametros;
    ArrayList<Parametro> listview_Parametros;
    Parametro parametro;
    ArrayAdapter adapter;
    Button botaoNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_parametro);

        botaoNovo = (Button) findViewById(R.id.botaoNovo);

        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ParametroActivity.class);
                startActivity(intent);
            }
        });

        ParametroController crud = new ParametroController(getBaseContext());
        final Cursor cursor = crud.retrieve();

        String[] campos = {"_id", "ROTINA", "NOME", "TEMPO_MINIMO", "TEMPO_MAXIMO"};
        int[] componentes = {R.id.textView_id, R.id.textViewRotina, R.id.textViewNome, R.id.textViewTempoMinimo, R.id.textViewTempoMaximo};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_parametro, cursor, campos, componentes, 0);
        ListView lista = (ListView) findViewById(R.id.listViewParametros);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String idParametro = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                Intent intent = new Intent(ListaParametroActivity.this, ParametroActivity.class);
                intent.putExtra("id", idParametro);

                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed () {
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
