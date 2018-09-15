package br.com.esucri.healthyUse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import br.com.esucri.healthyUse.controller.RotinaController;
import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.Parsers;

public class RotinaActivity extends AppCompatActivity {

    EditText editNome, editTipo, editHoraInicio, editHoraFinal, editDataFinal;
    RadioButton radioDomingo, radioSegunda, radioTerca, radioQuarta, radioQuinta, radioSexta,
            radioSabado, radioWhatsApp, radioInstagram, radioFacebook;
    Button botaoGravarRotina;
    TextView textViewAplicativos;
    Rotina editarRotina = new Rotina();
    Parsers parsers = new Parsers();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotina);

        Intent intent = getIntent();
        editarRotina = (Rotina) intent.getSerializableExtra("rotina_escolhida");

        //Buscar valores da activity por ID
        editNome = (EditText) findViewById(R.id.editNome);
        editTipo = (EditText) findViewById(R.id.editTipo);
        editHoraInicio = (EditText) findViewById(R.id.editHoraInicio);
        editHoraFinal = (EditText) findViewById(R.id.editHoraFinal);
        editDataFinal = (EditText) findViewById(R.id.editDataFinal);
        radioDomingo = (RadioButton) findViewById(R.id.radioDomingo);
        radioSegunda = (RadioButton) findViewById(R.id.radioSegunda);
        radioTerca = (RadioButton) findViewById(R.id.radioTerca);
        radioQuarta = (RadioButton) findViewById(R.id.radioQuarta);
        radioQuinta = (RadioButton) findViewById(R.id.radioQuinta);
        radioSexta = (RadioButton) findViewById(R.id.radioSexta);
        radioSabado = (RadioButton) findViewById(R.id.radioSabado);
        radioWhatsApp = (RadioButton) findViewById(R.id.radioWhatsApp);
        radioInstagram = (RadioButton) findViewById(R.id.radioInstagram);
        radioFacebook = (RadioButton) findViewById(R.id.radioFacebook);
        botaoGravarRotina = (Button) findViewById(R.id.botaoGravarRotina);
        textViewAplicativos = (TextView) findViewById(R.id.textViewAplicativos);

        //if(editarRotina !=null){
        //    botaoGravarRotina.setText("Modificar");
        //} else{
        //    botaoGravarRotina.setText("Gravar");
        //}

        botaoGravarRotina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar(v);
            }
        });
    }

    public void salvar(View view) {

        if (!validaCampos()) {
            return;
        }

        try {
            Rotina rotina = new Rotina();
            rotina.setNome(editNome.getText().toString());
            rotina.setTipo(editTipo.getText().toString());
            rotina.setHoraInicio(parsers.parserStringToTime(editHoraInicio.getText().toString()));
            rotina.setHoraFinal(parsers.parserStringToTime(editHoraFinal.getText().toString()));
            rotina.setDataFinal(parsers.parserStringToDate(editDataFinal.getText().toString()));
            rotina.setDom(radioDomingo.isChecked()?1:0);
            rotina.setSeg(radioSegunda.isChecked()?1:0);
            rotina.setTer(radioTerca.isChecked()?1:0);
            rotina.setQua(radioQuarta.isChecked()?1:0);
            rotina.setQui(radioQuinta.isChecked()?1:0);
            rotina.setSex(radioSexta.isChecked()?1:0);
            rotina.setSab(radioSabado.isChecked()?1:0);
            rotina.setWhatsapp(radioWhatsApp.isChecked()?1:0);
            rotina.setFacebook(radioFacebook.isChecked()?1:0);
            rotina.setInstagram(radioInstagram.isChecked()?1:0);

            RotinaController crud = new RotinaController(getBaseContext());

            long retorno;
            //if (TextUtils.isEmpty(idProduto)) {
                retorno = crud.create(rotina);
            //} else {
            //    produto.setId(Integer.parseInt(idProduto));
            //    retorno = crud.update(produto);
            //}

            if (retorno == -1) {
                Toast.makeText(RotinaActivity.this, "Erro ao salvar o registro!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RotinaActivity.this, "Registro salvo com sucesso!", Toast.LENGTH_LONG).show();
                //limpar();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean validaCampos() {
        Boolean result = true;
        String textoDescricao;
        Boolean marcacaoRadio;

        textoDescricao = editNome.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editNome.requestFocus();
            editNome.setError("Campo obrigatório!");
            return false;
        }

        textoDescricao = editHoraInicio.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editHoraInicio.requestFocus();
            editHoraInicio.setError("Campo obrigatório!");
            return false;
        }

        textoDescricao = editHoraFinal.getText().toString().trim();
        if (TextUtils.isEmpty(textoDescricao)) {
            editHoraFinal.requestFocus();
            editHoraFinal.setError("Campo obrigatório!");
            return false;
        }

        //Verifica se algum dos aplicativos foi selecionados
        marcacaoRadio = radioFacebook.isChecked();
        if (!marcacaoRadio) {
            marcacaoRadio = radioWhatsApp.isChecked();
            if (!marcacaoRadio){
                marcacaoRadio = radioInstagram.isChecked();
                if (!marcacaoRadio){
                    Toast.makeText(RotinaActivity.this, "Favor selecionar algum aplicativo!", Toast.LENGTH_LONG).show();
                    textViewAplicativos.setError("Opção obrigatória!");
                    return false;
                }
            }
        }

        return result;
    }
}
