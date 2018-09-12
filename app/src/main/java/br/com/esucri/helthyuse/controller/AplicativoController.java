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
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", aplicativo.getNome());

        resultado = instanciaDB.insert("APLICATIVO",
                null, dados);
        instanciaDB.close();
        return resultado;
    }

    public long update(final Aplicativo aplicativo){

        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME",aplicativo.getNome());

        resultado = instanciaDB.insert("APLICATIVO", null, dados);
        dados.put("NOME",aplicativo.getNome());

        String where = "_ID = " + aplicativo.getId();

        resultado = instanciaDB.update("APLICATIVO", dados, where, null);
        instanciaDB.close();

        return resultado;
    }

    public Aplicativo getById(int id){

        String[] campos = {"_ID", "NOME"};
        String where = "_ID = " + id;
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("APLICATIVO", campos,
                null, null, null,
                null, null);

        if (cursor == null){
            return null;
        }

        cursor.moveToFirst();
        instanciaDB.close();

        Aplicativo aplicativo = new Aplicativo();
        aplicativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
        aplicativo.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));

        return aplicativo;
    }

    public Cursor retrieve(){

        String[] campos = {"_ID", "NOME"};
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("APLICATIVO", campos,
                null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public long delete(final Aplicativo aplicativo) {

        String where = "_ID = " + aplicativo.getId();
        instanciaDB = db.getReadableDatabase();

        long resultado = instanciaDB.delete("APLICATIVO", where,null);
        return resultado;
    }
}
