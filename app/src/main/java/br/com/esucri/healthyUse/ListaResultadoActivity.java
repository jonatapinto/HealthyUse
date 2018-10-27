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

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.esucri.healthyUse.controller.ResultadoController;
import br.com.esucri.healthyUse.utils.Validations;

public class ListaResultadoActivity extends AppCompatActivity{
    EditText editDataInicio, editDataFinal;
    Button botaoResult;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_resultado);

        editDataInicio = (EditText) findViewById(R.id.editDataInicio);
        editDataFinal = (EditText) findViewById(R.id.editDataFinal);
        botaoResult = (Button) findViewById(R.id.botaoResult);

        botaoResult.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    resultado(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        SimpleMaskFormatter smfData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwDataInicio = new SimpleMaskTextWatcher(editDataInicio, smfData);
        editDataInicio.addTextChangedListener(mtwDataInicio);
        MaskTextWatcher mtwDataFinal = new SimpleMaskTextWatcher(editDataFinal, smfData);
        editDataFinal.addTextChangedListener(mtwDataFinal);
    }

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
    public void resultado(View view)  throws ParseException{
        SimpleDateFormat in = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");

        if (!validaCampos()) {
            return;
        }

        ResultadoController crud = new ResultadoController(getBaseContext());
        //passa a data de inicio e fim da tela
        System.out.println("ENTROU NO TRY");
        Date dataInicioFormatada = in.parse(editDataInicio.getText().toString());
        Date dataFimFormatada = in.parse(editDataFinal.getText().toString());

        final Cursor cursor = crud.retrieveTempos(out.parse(out.format(dataInicioFormatada)),
                out.parse(out.format(dataFimFormatada)));

        System.out.println("NUMERO DE LINHAS DO CURSOR "+cursor.getCount());

        String[] campos = {"_id","CODIGO","NOME","TEMPO_WHATSAPP","TEMPO_INSTAGRAM","TEMPO_FACEBOOK","RESULTADO"}; //,"TOTAL_ROTINA"
        int[] componentes = {R.id.textIDWhatsApp, R.id.textView_id, R.id.nomeRotina, R.id.tempoWhatsApp, R.id.tempoInstagram, R.id.tempoFacebook, R.id.textViewResultado};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lista_resultado, cursor, campos, componentes, 0);
        ListView lista = (ListView) findViewById(R.id.listViewResultados);
        lista.setAdapter(adapter);
        System.out.println("PASSOU O ADAPTER");
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}
