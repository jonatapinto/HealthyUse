package br.com.esucri.healthyUse.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.esucri.healthyUse.utils.BancoDeDados;

public class ResultadoController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public ResultadoController(Context context) {
        db = new BancoDeDados(context);
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

    public Cursor retrieveTempos(Date dataInicio, Date dataFim) {
        String sql;
        instanciaDB = db.getReadableDatabase();
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");

        sql =   "SELECT 1 as _id, \n"+
                "       TD_WHATSAPP.TEMPO_EM_SEGUNDOS AS TEMPO_WHATSAPP, \n"+
                "       TD_INSTAGRAM.TEMPO_EM_SEGUNDOS AS TEMPO_INSTAGRAM, \n"+
                "       TD_FACEBOOK.TEMPO_EM_SEGUNDOS AS TEMPO_FACEBOOK \n"+
                "  FROM \n"+
                "(SELECT  TD_EST.APLICATIVO, \n"+
                "         DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "         SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "   FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "           FROM ESTATISTICA AS E) AS TD_EST \n"+
                "          WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "                DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "                TD_EST.APLICATIVO = 'FACEBOOK' \n"+
                "          GROUP BY TD_EST.APLICATIVO) AS TD_FACEBOOK, \n"+
                "(SELECT  TD_EST.APLICATIVO, \n"+
                "         DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "         SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "   FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "           FROM ESTATISTICA AS E) AS TD_EST \n"+
                "          WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "                DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "                TD_EST.APLICATIVO = 'WHATSAPP' \n"+
                "          GROUP BY TD_EST.APLICATIVO) AS TD_WHATSAPP, \n"+
                "(SELECT  TD_EST.APLICATIVO, \n"+
                "         DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "         SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "   FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "           FROM ESTATISTICA AS E) AS TD_EST \n"+
                "  WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "        DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "        TD_EST.APLICATIVO = 'INSTAGRAM' \n"+
                "  GROUP BY TD_EST.APLICATIVO) AS TD_INSTAGRAM \n";// acrescentar a data

        Cursor cursor = instanciaDB.rawQuery(sql,new String[] {in.format(dataInicio),in.format(dataInicio)});

        //List<String[]> resultadoTempos = new ArrayList<String[]>();
        //while (cursor.moveToNext()){
        //    resultadoTempos.add(new String[]{
        //            cursor.getColumnName(0),
        //            cursor.getColumnName(1),
        //            cursor.getColumnName(2)});
        //    System.out.println(cursor.getColumnName(0)+
        //            ", "+cursor.getColumnName(1)+
        //            ", "+cursor.getColumnName(2));
        //}
//
        //List<String[]> rotinas = new ArrayList<String[]>();
        //rotinas.add(new String[]{"teADSADASst"});
//
        //for(String[] a : resultadoTempos){
//
        //}

        if (cursor != null) {
            cursor.moveToFirst();
        }

        instanciaDB.close();
        return cursor;
    }
}
