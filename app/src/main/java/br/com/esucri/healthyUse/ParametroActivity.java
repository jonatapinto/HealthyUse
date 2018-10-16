package br.com.esucri.healthyUse;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.com.esucri.healthyUse.controller.ParametroController;
import br.com.esucri.healthyUse.controller.RotinaController;
import br.com.esucri.healthyUse.model.Parametro;
import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.Parsers;
import br.com.esucri.healthyUse.utils.Validations;

public class ParametroActivity extends AppCompatActivity {
    EditText editNomeParametro, editTempoMinimo, editTempoMaximo;
    TextView textViewAplicativo;
    Spinner spinnerRotinas;
    Button botaoGravarParametro, botaoExcluirParametro;
    Parametro editarParametro = new Parametro();
    Parsers parsers = new Parsers();
    String idParametro;
    Parametro parametro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametro);

        //Buscar valores da activity por ID
        editNomeParametro = (EditText) findViewById(R.id.editNomeParametro);
        editTempoMinimo = (EditText) findViewById(R.id.editTempoMinimo);
        editTempoMaximo = (EditText) findViewById(R.id.editTempoMaximo);
        textViewAplicativo = (TextView) findViewById(R.id.textViewAplicativo);
        botaoGravarParametro = (Button) findViewById(R.id.botaoGravarParametro);
        botaoExcluirParametro = (Button) findViewById(R.id.botaoExcluirParametro);

        try {
            ArrayList<Rotina> arrayList = new RotinaController(this).getListaRotinas();
            RotinaAdapter rotinaAdapter = new RotinaAdapter(this);
            spinnerRotinas = (Spinner) findViewById(R.id.spinnerRotinas);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
            spinnerRotinas.setAdapter(arrayAdapter);
        } catch (Exception ex){
        }

        //Criando mascara para campos
        SimpleMaskFormatter smfTempo = new SimpleMaskFormatter("NN:NN");
        MaskTextWatcher mtwTempoMinimo = new MaskTextWatcher(editTempoMinimo, smfTempo);
        editTempoMinimo.addTextChangedListener(mtwTempoMinimo);
        MaskTextWatcher mtwTempoMaximo = new MaskTextWatcher(editTempoMaximo, smfTempo);
        editTempoMaximo.addTextChangedListener(mtwTempoMaximo);

        //Se existir valor ao id em Intent, preenche os campos da tela tal com o objeto passado
        idParametro = this.getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(idParametro)) {
            botaoGravarParametro.setText("Modificar");
            ParametroController crud = new ParametroController(getBaseContext());
            parametro = crud.getById(Integer.parseInt(idParametro));

            editNomeParametro.setText(parametro.getNome());
            editTempoMinimo.setText(parametro.getTempoMinimo().toString());
            editTempoMaximo.setText(parametro.getTempoMaximo().toString());
            spinnerRotinas.setAdapter(spinnerRotinas.getAdapter());

        } else{
            botaoGravarParametro.setText("Gravar");
        }

        botaoGravarParametro.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                salvar(v);
            }
        });

        botaoExcluirParametro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir(v);
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void salvar(View view) {
        if (!validaCampos()) {
            return;
        }

        Parametro parametro = new Parametro();
        parametro.setNome(editNomeParametro.getText().toString());
        parametro.setTempoMinimo(parsers.parserStringToTime(editTempoMinimo.getText().toString()));
        parametro.setTempoMaximo(parsers.parserStringToTime(editTempoMaximo.getText().toString()));
        parametro.setRotina(spinnerRotinas.getAdapter().toString());

        ParametroController crud = new ParametroController(getBaseContext());

        long retorno;
        if (TextUtils.isEmpty(idParametro)) {
            retorno = crud.create(parametro);
        } else {
            parametro.setId(Integer.parseInt(idParametro));
            retorno = crud.update(parametro);
        }

        if (retorno == -1) {
            Toast.makeText(ParametroActivity.this, "Erro ao salvar o registro!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ParametroActivity.this, "Registro salvo com sucesso!", Toast.LENGTH_LONG).show();
            limpar();
        }
        //Volta para a tela de listagem de rotinas
        Intent intent = new Intent(getBaseContext(), ListRotinaActivity.class);
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validaCampos() {
        Boolean result = true;
        String textoDescricao;
        Validations validacao = new Validations();

        textoDescricao = editNomeParametro.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editNomeParametro.requestFocus();
            editNomeParametro.setError("Campo obrigatório!");
            return false;
        }

        textoDescricao = editTempoMinimo.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editTempoMinimo.requestFocus();
            editTempoMinimo.setError("Campo obrigatório!");
            return false;
        } else {
            if(!validacao.isTimeValid(editTempoMinimo.getText().toString())){
                //Toast.makeText(RotinaActivity.this, "Favor informar um horario válido!", Toast.LENGTH_LONG).show();
                editTempoMinimo.requestFocus();
                editTempoMinimo.setError("Tempo mínimo inválido!");
                return false;
            }
        }

        textoDescricao = editTempoMaximo.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editTempoMaximo.requestFocus();
            editTempoMaximo.setError("Campo obrigatório!");
            return false;
        } else {
            if(!validacao.isTimeValid(editTempoMaximo.getText().toString())){
                //Toast.makeText(RotinaActivity.this, "Favor informar um horario válido!", Toast.LENGTH_LONG).show();
                editTempoMaximo.requestFocus();
                editTempoMaximo.setError("Tempo máximo inválido!");
                return false;
            }
        }

        return result;
    }

    public void excluir(View view) {

        if (TextUtils.isEmpty(idParametro)) {
            Toast.makeText(getBaseContext(),
                    "Este parâmetro ainda não está no banco de dados!", Toast.LENGTH_LONG).show();
            return;
        }

        ParametroController crud = new ParametroController(getBaseContext());
        long resultado = crud.delete(parametro);

        limpar();

        if (resultado == -1) {
            Toast.makeText(getBaseContext(),"Erro ao excluir parâmetro!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(),"Parâmetro excluído com sucesso!", Toast.LENGTH_LONG).show();
        }
        //Volta para a tela de listagem de rotinas
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    private void limpar() {
        editNomeParametro.setText("");
        editTempoMinimo.setText("");
        editTempoMaximo.setText("");
    }
}
