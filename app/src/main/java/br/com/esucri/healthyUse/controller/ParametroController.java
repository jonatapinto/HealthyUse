package br.com.esucri.healthyUse.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;

import br.com.esucri.healthyUse.model.Parametro;
import br.com.esucri.healthyUse.utils.BancoDeDados;
import br.com.esucri.healthyUse.utils.Parsers;

public class ParametroController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public ParametroController(Context context) {
        db = new BancoDeDados(context);
    }

    public long create(final Parametro parametro){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("ROTINA", parametro.getRotina().toString());
        dados.put("NOME", parametro.getNome().toString());
        dados.put("TEMPO_MINIMO", parametro.getTempoMinimo().toString());
        dados.put("TEMPO_MAXIMO", parametro.getTempoMaximo().toString());

        resultado = instanciaDB.insert("PARAMETRO", null, dados);
        instanciaDB.close();

        return resultado;
    }

    public ArrayList<Parametro> getListaParametros() throws ParseException {
        String [] columns = {"_id","ROTINA","NOME","TEMPO_MINIMO","TEMPO_MAXIMO"};
        instanciaDB = db.getWritableDatabase();
        Cursor cursor = instanciaDB.query("PARAMETRO",columns,null,null,null,null,null);
        ArrayList<Parametro> parametros = new ArrayList<Parametro>();
        Parsers parsers = new Parsers();

        while(cursor.moveToNext()){
            Parametro parametro = new Parametro();
            parametro.setId(cursor.getInt(0));
            parametro.setRotina(cursor.getString(1));
            parametro.setNome(cursor.getString(2));
            parametro.setTempoMinimo(parsers.parserStringToTime(cursor.getString(3)));
            parametro.setTempoMaximo(parsers.parserStringToTime(cursor.getString(4)));

            parametros.add(parametro);
        }
        return parametros;
    }

    public long update(final Parametro parametro){
        ContentValues dados = new ContentValues();
        long resutado;

        instanciaDB = db.getWritableDatabase();
        dados.put("ROTINA", parametro.getRotina());
        dados.put("NOME", parametro.getNome());
        dados.put("TEMPO_MINIMO", parametro.getTempoMinimo().toString());
        dados.put("TEMPO_MAXIMO", parametro.getTempoMaximo().toString());

        String where = "_id = " + parametro.getId();

        resutado = instanciaDB.update("PARAMETRO", dados, where, null);
        instanciaDB.close();

        return resutado;
    }

    public Cursor retrieve() {
        String[] campos = {"_id","ROTINA","NOME","TEMPO_MINIMO","TEMPO_MAXIMO"};
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("PARAMETRO", campos, null, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public Parametro getById(int id) {
        String[] campos = {"_id","ROTINA","NOME","TEMPO_MINIMO","TEMPO_MAXIMO"};
        String where = "_id = " + id;
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("PARAMETRO", campos,
                where, null,null,null,null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        instanciaDB.close();

        Parsers parsers = new Parsers();
        Time timeAux;
        Parametro parametro = new Parametro();

        parametro.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        parametro.setRotina(cursor.getString(cursor.getColumnIndexOrThrow("ROTINA")));
        parametro.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));

        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("TEMPO_MINIMO")));
        parametro.setTempoMinimo(timeAux);
        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("TEMPO_MAXIMO")));
        parametro.setTempoMaximo(timeAux);

        return parametro;
    }

    public long delete(final Parametro parametro) {

        String where = "_id = " + parametro.getId();
        instanciaDB = db.getReadableDatabase();

        long resultado = instanciaDB.delete("PARAMETRO", where, null);
        return resultado;
    }
}
