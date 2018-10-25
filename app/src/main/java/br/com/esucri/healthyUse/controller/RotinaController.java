package br.com.esucri.healthyUse.controller;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.esucri.healthyUse.model.Rotina;
import br.com.esucri.healthyUse.utils.BancoDeDados;
import br.com.esucri.healthyUse.utils.Parsers;

public class RotinaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public RotinaController(Context context) {
        db = new BancoDeDados(context);
        //db.insereDadosSimulados();
    }

    public long create(final Rotina rotina){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", rotina.getNome());
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
        dados.put("STATUS", 1);

        resultado = instanciaDB.insert("ROTINA", null, dados);
        instanciaDB.close();

        return resultado;
    }

    public ArrayList<Rotina> getListaRotinas() throws ParseException {
        String [] columns = {"_id","NOME","HORA_INICIO","HORA_FINAL","DOM","SEG","TER","QUA","QUI","SEX","SAB","INSTAGRAM","FACEBOOK","WHATSAPP"};
        instanciaDB = db.getWritableDatabase();
        Cursor cursor = instanciaDB.query("ROTINA",columns,null,null,null,null,null);
        ArrayList<Rotina> rotinas = new ArrayList<Rotina>();
        Parsers parsers = new Parsers();

        while(cursor.moveToNext()){
            Rotina rotina = new Rotina();
            rotina.setId(cursor.getInt(0));
            rotina.setNome(cursor.getString(1));
            rotina.setHoraInicio(parsers.parserStringToTime(cursor.getString(2)));
            rotina.setHoraFinal(parsers.parserStringToTime(cursor.getString(3)));
            rotina.setDom(cursor.getInt(4));
            rotina.setSeg(cursor.getInt(5));
            rotina.setTer(cursor.getInt(6));
            rotina.setQua(cursor.getInt(7));
            rotina.setQui(cursor.getInt(8));
            rotina.setSex(cursor.getInt(9));
            rotina.setSab(cursor.getInt(10));
            rotina.setInstagram(cursor.getInt(11));
            rotina.setFacebook(cursor.getInt(12));
            rotina.setWhatsapp(cursor.getInt(13));

            rotinas.add(rotina);
        }
        return rotinas;
    }

    public long update(final Rotina rotina){
        ContentValues dados = new ContentValues();
        long resutado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", rotina.getNome());
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

        String where = "_id = " + rotina.getId();

        resutado = instanciaDB.update("ROTINA", dados, where, null);
        instanciaDB.close();

        return resutado;
    }

    public long changeStatus(final Rotina rotina,Integer status){
        ContentValues dados = new ContentValues();
        long resutado;

        instanciaDB = db.getWritableDatabase();
        dados.put("STATUS", status);
        String where = "_id = " + rotina.getId();

        resutado = instanciaDB.update("ROTINA", dados, where, null);
        instanciaDB.close();

        return resutado;
    }

    public Cursor retrieve() {

        String[] campos = {"_id","NOME","HORA_INICIO","HORA_FINAL","STATUS"};
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("ROTINA", campos,
                null, null,null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public Cursor retrieveTemposApp(Date dataInicio, Date dataFim) {
        String sql;
        String[] campos = {"APLICATIVO","DATA","TEMPO_EM_SEGUNDOS"};
        instanciaDB = db.getReadableDatabase();
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");

        sql = "SELECT TD_EST.APLICATIVO AS APLICATIVO, \n" +
              "       DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n" +
              "       SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n" +
              "  FROM (SELECT E.APLICATIVO AS APLICATIVO, \n" +
              "               E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n" +
              "               (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n" +
              "  FROM ESTATISTICA AS E) TD_EST \n" +
              " WHERE DATE(TD_EST.DATA_HORA_INICIO) BETWEEN ? AND ? \n" +
              " GROUP BY TD_EST.APLICATIVO, DATA";

        Cursor cursor = instanciaDB.rawQuery(sql,new String[] {in.format(dataInicio),in.format(dataInicio)});

        List<String[]> resultadoTempos = new ArrayList<String[]>();
        while (cursor.moveToNext()){
            resultadoTempos.add(new String[]{
                    cursor.getColumnName(0),
                    cursor.getColumnName(1),
                    cursor.getColumnName(2)});
            System.out.println(cursor.getColumnName(0)+
                    ", "+cursor.getColumnName(1)+
                    ", "+cursor.getColumnName(2));
        }

        List<String[]> rotinas = new ArrayList<String[]>();
        rotinas.add(new String[]{"teADSADASst"});

        for(String[] a : resultadoTempos){

        }
            

        cursor.close();


        if (cursor != null) {
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }

    public Rotina getById(int id) {
        String[] campos = {"_id","NOME","HORA_INICIO","HORA_FINAL","DOM","SEG","TER","QUA","QUI","SEX","SAB","INSTAGRAM","FACEBOOK","WHATSAPP","STATUS"};
        String where = "_id = " + id;
        instanciaDB = db.getReadableDatabase();

        Cursor cursor = instanciaDB.query("ROTINA", campos,
                where, null,null,null,null);

        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        instanciaDB.close();

        Parsers parsers = new Parsers();
        Time timeAux;
        Rotina rotina = new Rotina();

        rotina.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        rotina.setNome(cursor.getString(cursor.getColumnIndexOrThrow("NOME")));

        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("HORA_INICIO")));
        rotina.setHoraInicio(timeAux);
        timeAux = parsers.parserStringToTime(cursor.getString(cursor.getColumnIndexOrThrow("HORA_FINAL")));
        rotina.setHoraFinal(timeAux);

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
        rotina.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("STATUS")));

        return rotina;
    }

    public long delete(final Rotina rotina) {

        String where = "_id = " + rotina.getId();
        instanciaDB = db.getReadableDatabase();

        long resultado = instanciaDB.delete("ROTINA", where, null);
        return resultado;
    }
}