package br.com.esucri.healthyUse.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.healthyUse.model.Estatistica;
import br.com.esucri.healthyUse.utils.BancoDeDados;

public class EstatisticaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public EstatisticaController(Context context) {
        db = new BancoDeDados(context);
    }

    public long create(final Estatistica estatistica){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("APLICATIVO", estatistica.getAplicativo());
        dados.put("DATA_HORA_INICIO", estatistica.getDataHoraInicio());
        dados.put("DATA_HORA_FIM", estatistica.getDataHoraFim());

        resultado = instanciaDB.insert("ESTATISTICA", null, dados);
        instanciaDB.close();

        return resultado;
    }
}
