package br.com.esucri.healthyUse;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;

import br.com.esucri.healthyUse.utils.Validations;

public class ResultadoActivity extends AppCompatActivity {
    EditText editDataInicio, editDataFinal;
    Button botaoResultados;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_resultado);

        editDataInicio = (EditText) findViewById(R.id.editDataInicio);
        editDataFinal = (EditText) findViewById(R.id.editDataFinal);
        botaoResultados = (Button) findViewById(R.id.botaoResultados);

        botaoResultados.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                resultado(v);
            }
        });

        SimpleMaskFormatter smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwDataInicio = new SimpleMaskTextWatcher(editDataInicio, smfData);
        editDataInicio.addTextChangedListener(mtwDataInicio);
        MaskTextWatcher mtwDataFinal = new SimpleMaskTextWatcher(editDataFinal, smfData);
        editDataFinal.addTextChangedListener(mtwDataFinal);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validaCampos() {
        Boolean result = true;
        String textoDescricao;
        Validations validacao = new Validations();

        textoDescricao = editDataInicio.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editDataInicio.requestFocus();
            editDataInicio.setError("Campo obrigatório!");
            return false;
        } else {
            if(!validacao.isDateValid(editDataInicio.getText().toString())){
                editDataInicio.requestFocus();
                editDataInicio.setError("Data inicial inválida!");
                return false;
            }
        }

        textoDescricao = editDataFinal.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editDataFinal.requestFocus();
            editDataFinal.setError("Campo obrigatório!");
            return false;
        } else {
            if(!validacao.isDateValid(editDataFinal.getText().toString())){
                editDataFinal.requestFocus();
                editDataFinal.setError("Data final inválida!");
                return false;
            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void resultado(View view) {
        if (!validaCampos()) {
            return;
        }

        //ParametroController crud = new ParametroController(getBaseContext());
        //final Cursor cursor = crud.retrieve();

        //String[] campos = {"_id", "ROTINA", "NOME", "TEMPO_MINIMO", "TEMPO_MAXIMO"};
        //int[] componentes = {R.id.textView_id, R.id.textViewRotina, R.id.textViewNome, R.id.textViewTempoMinimo, R.id.textViewTempoMaximo};

        //SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
        //        R.layout.lista_parametro, cursor, campos, componentes, 0);
        //ListView lista = (ListView) findViewById(R.id.listViewParametros);
        //lista.setAdapter(adapter);

        //lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //   @Override
        //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //       cursor.moveToPosition(position);
        //       String idParametro = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

        //        Intent intent = new Intent(ListaParametroActivity.this, ParametroActivity.class);
        //        intent.putExtra("id", idParametro);

        //        startActivity(intent);
        //        finish();
        //    }
        //});

    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
