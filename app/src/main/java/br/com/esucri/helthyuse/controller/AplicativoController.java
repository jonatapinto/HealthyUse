package br.com.esucri.helthyuse.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.helthyuse.model.Aplicativo;
import br.com.esucri.helthyuse.utils.BancoDeDados;

public class AplicativoController {

    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public AplicativoController(Context context){
        db = new BancoDeDados(context);
    }

    public long create(final Aplicativo aplicativo){
        ContentValues dados = new ContentValues();
        long resultadoCre;

        instanciaDB = db.getWritableDatabase();
        dados.put("nome", aplicativo.getNome());

        resultadoCre = instanciaDB.insert("aplicativo",
                null, dados);
        instanciaDB.close();
        return resultadoCre;
    }

    public long update(final Aplicativo aplicativo){

        ContentValues dados = new ContentValues();
        long resultadoUp;

        instanciaDB = db.getWritableDatabase();
        dados.put("nome",aplicativo.getNome());

        String where = "_id = " + aplicativo.getId();

        resultadoUp = instanciaDB.update("aplicativo", dados, where, null);
        instanciaDB.close();

        return resultadoUp;
    }

    public Aplicativo getById(int id){

        String[] campos = {"_id", "nome"};
        String where = "_id = " + id;
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("aplicativo", campos,
                null, null, null, null, null);

        if (cursor == null){
            return null;
        }

        cursor.moveToFirst();
        instanciaDB.close();

        Aplicativo aplicativo = new Aplicativo();
        aplicativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        aplicativo.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));

        return aplicativo;
    }

    public Cursor retrieve(){

        String[] campos = {"_id", "nome"};
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("aplicativo", campos,
                null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public long delete(final Aplicativo aplicativo) {

        String where = "_id = " + aplicativo.getId();
        instanciaDB = db.getReadableDatabase();

        long resultado = instanciaDB.delete("aplicativo", where,null);
        return resultado;
    }
}