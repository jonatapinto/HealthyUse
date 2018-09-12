package br.com.esucri.helthyuse.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.helthyuse.model.Estatistica;
import br.com.esucri.helthyuse.utils.BancoDeDados;

public class EstatisticaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public long create(final Estatistica estatistica){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("ID_APLICATIVO", estatistica.getIdAplicativo());
        dados.put("DATA_INICIO", estatistica.getDataInicio().toString());
        dados.put("HORA_INICIO", estatistica.getHoraInicio().toString());
        dados.put("DATA_FINAL", estatistica.getDataFinal().toString());
        dados.put("HORA_FINAL", estatistica.getHoraFinal().toString());

        resultado = instanciaDB.insert("ESTATISTICA", null, dados);
        instanciaDB.close();

        return resultado;
    }
}
