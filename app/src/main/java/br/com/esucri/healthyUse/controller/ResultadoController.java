package br.com.esucri.healthyUse.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.esucri.healthyUse.utils.BancoDeDados;

public class ResultadoController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public ResultadoController(Context context) {
        db = new BancoDeDados(context);
    }

    //public Cursor retrieve() {
    //    String[] campos = {"_id","ROTINA","NOME","TEMPO_MINIMO","TEMPO_MAXIMO"};
    //    instanciaDB = db.getReadableDatabase();

    //    Cursor cursor = instanciaDB.query("PARAMETRO", campos, null, null,null,null,null);

    //    if (cursor != null) {
    //        cursor.moveToFirst();
    //    }

    //    instanciaDB.close();
    //    return cursor;
    //}

    public Cursor retrieveTempos(Date dataInicio, Date dataFim) {
        String sql;
        instanciaDB = db.getReadableDatabase();
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");

        sql =   "SELECT 1 as _id, \n"+
                "       TD_WHATSAPP.TEMPO_EM_SEGUNDOS AS TEMPO_WHATSAPP, \n"+
                "       TD_INSTAGRAM.TEMPO_EM_SEGUNDOS AS TEMPO_INSTAGRAM, \n"+
                "       TD_FACEBOOK.TEMPO_EM_SEGUNDOS AS TEMPO_FACEBOOK \n"+
                "  FROM (SELECT  TD_EST.APLICATIVO, \n"+
                "                DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "                SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                       E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                       (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "               TD_EST.APLICATIVO = 'FACEBOOK' \n"+
                "          GROUP BY TD_EST.APLICATIVO) AS TD_FACEBOOK, \n"+

                "       (SELECT  TD_EST.APLICATIVO, \n"+
                "                DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "                SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                       E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                       (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "               TD_EST.APLICATIVO = 'WHATSAPP' \n"+
                "         GROUP BY TD_EST.APLICATIVO) AS TD_WHATSAPP, \n"+

                "       (SELECT  TD_EST.APLICATIVO, \n"+
                "                DATE(TD_EST.DATA_HORA_INICIO) AS DATA, \n"+
                "                SUM(TD_EST.DIF_SEG) AS TEMPO_EM_SEGUNDOS \n"+
                "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                "                       E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                "                       (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                "               TD_EST.APLICATIVO = 'INSTAGRAM' \n"+
                "         GROUP BY TD_EST.APLICATIVO) AS TD_INSTAGRAM;";// acrescentar a data

        String sql2 = "SELECT _id, APLICATIVO, DATA_HORA_INICIO, DATA_HORA_FIM FROM ESTATISTICA";

        String sql3 =
                        "SELECT 1 as _id, \n"+
                        "       TD_WHATSAPP.TEMPO_WHATSAPP AS TEMPO_WHATSAPP, \n"+
                        "       TD_INSTAGRAM.TEMPO_INSTAGRAM AS TEMPO_INSTAGRAM, \n"+
                        "       TD_FACEBOOK.TEMPO_FACEBOOK AS TEMPO_FACEBOOK \n"+
                        "  FROM (SELECT SUM(TD_EST.DIF_SEG) AS TEMPO_INSTAGRAM \n"+
                        "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                        "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                        "                       E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                        "                       (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                        "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                        "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                        "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                        "               TD_EST.APLICATIVO = 'com.instagram.android') AS TD_INSTAGRAM, \n"+

                        "       (SELECT SUM(TD_EST.DIF_SEG) AS TEMPO_FACEBOOK \n"+
                        "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                        "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                        "                      E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                        "                      (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                        "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                        "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                        "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                        "               TD_EST.APLICATIVO = 'com.facebook.katana') AS TD_FACEBOOK, \n"+

                        "       (SELECT SUM(TD_EST.DIF_SEG) AS TEMPO_WHATSAPP \n"+
                        "          FROM (SELECT E.APLICATIVO AS APLICATIVO, \n"+
                        "                       E.DATA_HORA_INICIO AS DATA_HORA_INICIO, \n"+
                        "                      E.DATA_HORA_FIM AS DATA_HORA_FIM, \n"+
                        "                      (strftime('%s',E.DATA_HORA_FIM) - strftime('%s',E.DATA_HORA_INICIO)) AS DIF_SEG \n"+
                        "                  FROM ESTATISTICA AS E) AS TD_EST \n"+
                        "         WHERE DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n"+
                        "               DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n"+
                        "               TD_EST.APLICATIVO = 'com.whatsapp') AS TD_WHATSAPP;";


        String sql4 =
                " SELECT 1 AS _id, SB.CODIGO, \n "+
                        "        SB.NOME, \n "+
                        "        SB.TEMPO_WHATSAPP, \n "+
                        "        SB.TEMPO_INSTAGRAM, \n "+
                        "        SB.TEMPO_FACEBOOK, \n "+
                        "        P.NOME AS RESULTADO, \n "+
                        "        (SELECT   \n "+
                        "             sum(((CASE   WHEN (TIME(TD_EST.DATA_HORA_FIM) > R.HORA_FIM) THEN (SUBSTR(R.HORA_FIM,1,2)*3600 + SUBSTR(R.HORA_FIM,4,2) *60) \n "+
                        "                                ELSE (SUBSTR(TIME(TD_EST.DATA_HORA_FIM),1,2) * 3600 + SUBSTR(TIME(TD_EST.DATA_HORA_FIM),4,2)*60+SUBSTR(TIME(TD_EST.DATA_HORA_FIM),7,2)) \n "+
                        "                                END) - \n "+
                        "             (CASE   WHEN (TIME(TD_EST.DATA_HORA_INICIO) < R.HORA_INICIO) THEN (SUBSTR(R.HORA_INICIO,1,2)*3600 + SUBSTR(R.HORA_INICIO,4,2) *60) \n "+
                        "                                ELSE  (SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),1,2) * 3600 + SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),4,2)*60+SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),7,2)) \n "+
                        "                                END))) AS TEMPO_INDIVIDUAL                                      \n "+
                        "         FROM ROTINA AS R,  \n "+
                        "              ESTATISTICA AS TD_EST  \n "+
                        "        WHERE  \n "+
                        "              R.NOME = P.ROTINA AND \n "+
                        "              DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n "+
                        "              DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n "+
                        "              TD_EST.APLICATIVO in ('com.instagram.android','com.whatsapp','com.facebook.katana') AND \n "+
                        "              ((TIME(R.HORA_INICIO) <= TIME(TD_EST.DATA_HORA_INICIO) AND TIME(R.HORA_FIM) >= TIME(TD_EST.DATA_HORA_INICIO)) OR \n "+
                        "              (TIME(R.HORA_FIM) >= TIME(TD_EST.DATA_HORA_FIM) AND TIME(R.HORA_INICIO) <= TIME(TD_EST.DATA_HORA_FIM) ) ) AND \n "+
                        "              (  \n "+
                        "                (R.DOM = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '0') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '0'))) OR \n "+
                        "                (R.SEG = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '1') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '1'))) OR \n "+
                        "                (R.TER = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '2') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '2'))) OR \n "+
                        "                (R.QUA = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '3') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '3'))) OR \n "+
                        "                (R.QUI = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '4') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '4'))) OR \n "+
                        "                (R.SEX = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '5') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '5'))) OR \n "+
                        "                (R.SAB = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '6') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '6')))          \n "+
                        "              )) AS TOTAL_ROTINA \n "+
                        " FROM PARAMETRO AS P, \n "+
                        "  \n "+
                        "       (SELECT R._ID AS CODIGO,  \n "+
                        "              R.NOME AS NOME, \n "+
                        "              R.STATUS AS STATUS,  \n "+
                        "             sum((CASE TD_EST.APLICATIVO WHEN 'com.whatsapp' THEN \n "+
                        "                     ((CASE   WHEN (TIME(TD_EST.DATA_HORA_FIM) > R.HORA_FIM) THEN (SUBSTR(R.HORA_FIM,1,2)*3600 + SUBSTR(R.HORA_FIM,4,2) *60) \n "+
                        "                                        ELSE (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),7,2),0)) \n "+
                        "                                     END) - \n "+
                        "                          (CASE   WHEN (TIME(TD_EST.DATA_HORA_INICIO) < R.HORA_INICIO) THEN (SUBSTR(R.HORA_INICIO,1,2)*3600 + SUBSTR(R.HORA_INICIO,4,2) *60) \n "+
                        "                                        ELSE  (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),7,2),0)) \n "+
                        "                           END)) \n "+
                        "              ELSE 0 \n "+
                        "              END)) AS TEMPO_WHATSAPP, \n "+
                        "             sum((CASE TD_EST.APLICATIVO WHEN 'com.instagram.android' THEN \n "+
                        "                         ((CASE   WHEN (TIME(TD_EST.DATA_HORA_FIM) > R.HORA_FIM) THEN (SUBSTR(R.HORA_FIM,1,2)*3600 + SUBSTR(R.HORA_FIM,4,2) *60) \n "+
                        "                                        ELSE (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),7,2),0)) \n "+
                        "                                     END) - \n "+
                        "                          (CASE   WHEN (TIME(TD_EST.DATA_HORA_INICIO) < R.HORA_INICIO) THEN (SUBSTR(R.HORA_INICIO,1,2)*3600 + SUBSTR(R.HORA_INICIO,4,2) *60) \n "+
                        "                                        ELSE  (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),7,2),0)) \n "+
                        "                           END)) \n "+
                        "              ELSE 0 \n "+
                        "              END)) AS TEMPO_INSTAGRAM, \n "+
                        "             sum((CASE TD_EST.APLICATIVO WHEN 'com.facebook.katana' THEN \n "+
                        "                            ((CASE   WHEN (TIME(TD_EST.DATA_HORA_FIM) > R.HORA_FIM) THEN (SUBSTR(R.HORA_FIM,1,2)*3600 + SUBSTR(R.HORA_FIM,4,2) *60) \n "+
                        "                                        ELSE (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_FIM),7,2),0)) \n "+
                        "                                     END) - \n "+
                        "                          (CASE   WHEN (TIME(TD_EST.DATA_HORA_INICIO) < R.HORA_INICIO) THEN (SUBSTR(R.HORA_INICIO,1,2)*3600 + SUBSTR(R.HORA_INICIO,4,2) *60) \n "+
                        "                                        ELSE  (COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),1,2),0)*3600 + COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),4,2),0)*60+COALESCE(SUBSTR(TIME(TD_EST.DATA_HORA_INICIO),7,2),0)) \n "+
                        "                           END)) \n "+
                        "              ELSE 0 \n "+
                        "              END)) AS TEMPO_FACEBOOK                                     \n "+
                        "         FROM ROTINA AS R,  \n "+
                        "              ESTATISTICA AS TD_EST  \n "+
                        "        WHERE  \n "+
                        "  \n "+
                        "              DATE(TD_EST.DATA_HORA_INICIO) >= ? AND \n "+
                        "              DATE(TD_EST.DATA_HORA_FIM) <= ? AND \n "+
                        "              TD_EST.APLICATIVO in ('com.instagram.android','com.whatsapp','com.facebook.katana') AND \n "+
                        "              ((TIME(R.HORA_INICIO) <= TIME(TD_EST.DATA_HORA_INICIO) AND TIME(R.HORA_FIM) >= TIME(TD_EST.DATA_HORA_INICIO)) OR \n "+
                        "              (TIME(R.HORA_FIM) >= TIME(TD_EST.DATA_HORA_FIM) AND TIME(R.HORA_INICIO) <= TIME(TD_EST.DATA_HORA_FIM) ) ) AND \n "+
                        "              (  \n "+
                        "                (R.DOM = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '0') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '0'))) OR \n "+
                        "                (R.SEG = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '1') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '1'))) OR \n "+
                        "                (R.TER = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '2') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '2'))) OR \n "+
                        "                (R.QUA = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '3') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '3'))) OR \n "+
                        "                (R.QUI = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '4') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '4'))) OR \n "+
                        "                (R.SEX = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '5') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '5'))) OR \n "+
                        "                (R.SAB = 1 AND ((strftime('%w',TD_EST.DATA_HORA_INICIO) = '6') OR (strftime('%w',TD_EST.DATA_HORA_FIM) = '6')))          \n "+
                        "              ) \n "+
                        "       GROUP BY 1,2,3) AS SB \n "+
                        " WHERE SB.STATUS = 1 AND \n "+
                        "       P.ROTINA = SB.NOME AND  \n "+
                        "       ( \n "+
                        "       ((SUBSTR(P.TEMPO_MINIMO,1,2)*3600 + SUBSTR(P.TEMPO_MINIMO,4,2) *60)<= TOTAL_ROTINA AND  \n "+
                        "       (SUBSTR(P.TEMPO_MAXIMO,1,2)*3600 + SUBSTR(P.TEMPO_MAXIMO,4,2) *60) >= TOTAL_ROTINA ) OR \n "+
                        "        ( \n "+
                        "        (CASE WHEN (SELECT MAX((SUBSTR(P2.TEMPO_MAXIMO,1,2)*3600 + SUBSTR(P2.TEMPO_MAXIMO,4,2) *60)) FROM PARAMETRO P2  WHERE P2.ROTINA = P.ROTINA) < TOTAL_ROTINA \n "+
                        "              THEN (SELECT _ID FROM PARAMETRO P2  WHERE P2.ROTINA = P.ROTINA AND P2.TEMPO_MAXIMO = ( SELECT MAX(P3.TEMPO_MAXIMO)  \n "+
                        "                                                                                                      FROM PARAMETRO P3  WHERE P3.ROTINA = P.ROTINA)) \n "+
                        "              ELSE 0 \n "+
                        "              END) = P._ID \n "+
                        "        ) OR \n "+
                        "        ( \n "+
                        "        (CASE WHEN (SELECT MIN((SUBSTR(P2.TEMPO_MINIMO,1,2)*3600 + SUBSTR(P2.TEMPO_MINIMO,4,2) *60)) FROM PARAMETRO P2  WHERE P2.ROTINA = P.ROTINA) > TOTAL_ROTINA \n "+
                        "              THEN (SELECT _ID FROM PARAMETRO P2  WHERE P2.ROTINA = P.ROTINA AND P2.TEMPO_MINIMO = ( SELECT MIN(P3.TEMPO_MINIMO)  \n "+
                        "                                                                                                      FROM PARAMETRO P3  WHERE P3.ROTINA = P.ROTINA)) \n "+
                        "              ELSE 0 \n "+
                        "              END) = P._ID \n "+
                        "        ) \n "+
                        " ) \n "+
                        " ORDER BY CODIGO; ";

        System.out.println("Data início: "+in.format(dataInicio)+"Data fim: "+ in.format(dataFim));
        Cursor cursor = instanciaDB.rawQuery(sql4,new String[] {in.format(dataInicio),in.format(dataFim),in.format(dataInicio),in.format(dataFim)});
        //Cursor cursor = instanciaDB.rawQuery(sql2,null);
        System.out.println("CURSOR LINHAS: "+cursor.getCount());
        System.out.println("TESTE PASSOU PELO CURSOR");

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
