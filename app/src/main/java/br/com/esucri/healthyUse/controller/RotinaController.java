package br.com.esucri.healthyUse.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.BancoDeDados;
import br.com.esucri.healthyUse.utils.Parsers;

public class RotinaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

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
        String [] columns = {"ID","NOME","TIPO","HORA_INICIO","HORA_FINAL","DOM","SEG","TER","QUA","QUI","SEX","SAB","INSTAGRAM","FACEBOOK","WHATSAPP","DATA_FINAL"};
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

    //public long update(final Rotina rotina){
    //    return 1;
    //}

    //public long delete(final Rotina rotina) {
    //    return 1;
    //}
}
