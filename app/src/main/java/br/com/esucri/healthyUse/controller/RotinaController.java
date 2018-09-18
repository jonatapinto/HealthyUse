package br.com.esucri.healthyUse.controller;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.BancoDeDados;
import br.com.esucri.healthyUse.utils.Parsers;

public class RotinaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public RotinaController(Context context) {
        db = new BancoDeDados(context);
    }

    public long create(final Rotina rotina){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", rotina.getNome());
        dados.put("TIPO", rotina.getTipo());
        dados.put("HORA_INICIO", rotina.getHoraInicio().toString());
        dados.put("HORA_FINAL", rotina.getHoraFinal().toString());
        dados.put("DOM", rotina.getDom());
        dados.put("SEG", rotina.getSeg());
        dados.put("TER", rotina.getTer());
        dados.put("QUA", rotina.getQua());
        dados.put("QUI", rotina.getQui());
        dados.put("SEX", rotina.getSex());
        dados.put("SAB", rotina.getSab());
        dados.put("INSTAGRAM", rotina.getInstagram());
        dados.put("FACEBOOK", rotina.getFacebook());
        dados.put("WHATSAPP", rotina.getWhatsapp());
        dados.put("DATA_FINAL", rotina.getDataFinal().toString());

        resultado = instanciaDB.insert("ROTINA", null, dados);
        instanciaDB.close();

        return resultado;
    }

    public ArrayList<Rotina> getListaRotinas() throws ParseException {
        String [] columns = {"_id","NOME","TIPO","HORA_INICIO","HORA_FINAL","DOM","SEG","TER","QUA","QUI","SEX","SAB","INSTAGRAM","FACEBOOK","WHATSAPP","DATA_FINAL"};
        instanciaDB = db.getWritableDatabase();
        Cursor cursor = instanciaDB.query("ROTINA",columns,null,null,null,null,null);
        ArrayList<Rotina> rotinas = new ArrayList<Rotina>();
        Parsers parsers = new Parsers();

        while(cursor.moveToNext()){
            Rotina rotina = new Rotina();
            rotina.setId(cursor.getInt(0));
            rotina.setNome(cursor.getString(1));
            rotina.setTipo(cursor.getString(2));
            rotina.setHoraInicio(parsers.parserStringToTime(cursor.getString(3)));
            rotina.setHoraFinal(parsers.parserStringToTime(cursor.getString(4)));
            rotina.setDom(cursor.getInt(5));
            rotina.setSeg(cursor.getInt(6));
            rotina.setTer(cursor.getInt(7));
            rotina.setQua(cursor.getInt(8));
            rotina.setQui(cursor.getInt(9));
            rotina.setSex(cursor.getInt(10));
            rotina.setSab(cursor.getInt(11));
            rotina.setInstagram(cursor.getInt(12));
            rotina.setFacebook(cursor.getInt(13));
            rotina.setWhatsapp(cursor.getInt(14));
            rotina.setDataFinal(parsers.parserStringToTime(cursor.getString(15)));

            rotinas.add(rotina);
        }
        return rotinas;
    }

    public long update(final Rotina rotina){
        ContentValues dados = new ContentValues();
        long resutado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", rotina.getNome());
        dados.put("TIPO", rotina.getTipo());
        dados.put("HORA_INICIO", rotina.getHoraInicio().toString());
        dados.put("HORA_FINAL", rotina.getHoraFinal().toString());
        dados.put("DOM", rotina.getDom());
        dados.put("SEG", rotina.getSeg());
        dados.put("TER", rotina.getTer());
        dados.put("QUA", rotina.getQua());
        dados.put("QUI", rotina.getQui());
        dados.put("SEX", rotina.getSex());
        dados.put("SAB", rotina.getSab());
        dados.put("INSTAGRAM", rotina.getInstagram());
        dados.put("FACEBOOK", rotina.getFacebook());
        dados.put("WHATSAPP", rotina.getWhatsapp());
        dados.put("DATA_FINAL", rotina.getDataFinal().toString());

        String where = "_id = " + rotina.getId();

        resutado = instanciaDB.update("ROTINA", dados, where, null);
        instanciaDB.close();

        return resutado;
    }

    public Cursor retrieve() {

        String[] campos = {"_id","NOME","TIPO","HORA_INICIO","HORA_FINAL"};
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("ROTINA", campos,
                null, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public Rotina getById(int id) {
        String[] campos = {"_id","NOME","TIPO","HORA_INICIO","HORA_FINAL","DOM","SEG","TER","QUA","QUI","SEX","SAB","INSTAGRAM","FACEBOOK","WHATSAPP","DATA_FINAL"};
        String where = "_id = " + id;
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("ROTINA", campos,
                null, null,null,null,null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        instanciaDB.close();

        Parsers parsers = new Parsers();
        Date dateAux;
        Time timeAux;
        Rotina rotina = new Rotina();

        rotina.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        rotina.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));
        rotina.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("TIPO")));

        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("HORA_INICIO")));
        rotina.setHoraInicio(timeAux);
        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("HORA_FINAL")));
        rotina.setHoraFinal(timeAux);
        if(!cursor.getString(cursor.getColumnIndexOrThrow("DATA_FINAL")).isEmpty()){
            dateAux = parsers.parserStringToDate(cursor.getString(cursor.getColumnIndexOrThrow("DATA_FINAL")));
            //dateAux = parsers.testing(cursor.getString(cursor.getColumnIndexOrThrow("DATA_FINAL")));
            rotina.setDataFinal(dateAux);
        }
        rotina.setDom(cursor.getInt(cursor.getColumnIndexOrThrow("DOM")));
        rotina.setSeg(cursor.getInt(cursor.getColumnIndexOrThrow("SEG")));
        rotina.setTer(cursor.getInt(cursor.getColumnIndexOrThrow("TER")));
        rotina.setQua(cursor.getInt(cursor.getColumnIndexOrThrow("QUA")));
        rotina.setQui(cursor.getInt(cursor.getColumnIndexOrThrow("QUI")));
        rotina.setSex(cursor.getInt(cursor.getColumnIndexOrThrow("SEX")));
        rotina.setSab(cursor.getInt(cursor.getColumnIndexOrThrow("SAB")));
        rotina.setInstagram(cursor.getInt(cursor.getColumnIndexOrThrow("INSTAGRAM")));
        rotina.setFacebook(cursor.getInt(cursor.getColumnIndexOrThrow("FACEBOOK")));
        rotina.setWhatsapp(cursor.getInt(cursor.getColumnIndexOrThrow("WHATSAPP")));

        return rotina;
    }

    public long delete(final Rotina rotina) {

        String where = "_id = " + rotina.getId();
        instanciaDB = db.getReadableDatabase();

        long resultado = instanciaDB.delete("ROTINA", where, null);
        return resultado;
    }
}
