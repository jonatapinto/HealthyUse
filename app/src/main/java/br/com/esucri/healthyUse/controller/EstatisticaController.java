package br.com.esucri.healthyUse.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLOutput;

import br.com.esucri.healthyUse.model.Estatistica;
import br.com.esucri.healthyUse.model.Rotina;
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

        System.out.println("RESULTADO DO CREATE DO ESTATISTICA: "+resultado);
        return resultado;
    }

    public long update(final Estatistica estatistica){
        ContentValues dados = new ContentValues();
        long resutado;

        instanciaDB = db.getWritableDatabase();
        //dados.put("APLICATIVO", estatistica.getAplicativo());
        //dados.put("DATA_HORA_INICIO", estatistica.getDataHoraInicio().toString());
        dados.put("DATA_HORA_FIM", estatistica.getDataHoraFim().toString());

        String where = "_id = " + estatistica.getId();

        resutado = instanciaDB.update("ESTATISTICA", dados, where, null);
        instanciaDB.close();

        return resutado;
    }

    public int retrieveId(){
        instanciaDB = db.getReadableDatabase();
        String sql = "SELECT MAX(_id) AS MAIOR FROM ESTATISTICA";
        Cursor cursor = instanciaDB.rawQuery(sql,null);

        int maiorId = 0;
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            maiorId = cursor.getInt(cursor.getColumnIndex("MAIOR"));
            System.out.println("MAIOR ID: "+maiorId);
        }

        return maiorId;
    }
}
