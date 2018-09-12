package br.com.esucri.helthyuse.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.helthyuse.model.Aplicativo;
import br.com.esucri.helthyuse.utils.BancoDeDados;

public class AplicativoController {

    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public long create(final Aplicativo aplicativo){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME",aplicativo.getNome());

        resultado = instanciaDB.insert("APLICATIVO", null, dados);
        instanciaDB.close();

        return resultado;
    }

    //public Cursor retrieve(){
    //    return ;
    //}

    //public long update(final Aplicativo aplicativo){
    //    return 1;
    //}

    //public long delete(final Aplicativo aplicativo) {
    //    return 1;
    //}

}

