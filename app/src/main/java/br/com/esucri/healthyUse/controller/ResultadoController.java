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

    public Cursor retrieveTempos(Date dataInicio, Date dataFim) {
        String sql;
        instanciaDB = db.getReadableDatabase();
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");

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
        System.out.println("CURSOR LINHAS: "+cursor.getCount());
        System.out.println("TESTE PASSOU PELO CURSOR");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        List<String[]> tempos = new ArrayList<String[]>();
        //AJUSTAR OS SEGUNDOS DE WHATS, INSTA E FACEBOOK PARA HH:MM:SS USANDO FUNCAO formataTempo ABAIXO
        //int cont = 0;
        //do{
        //    cont = cont + 1;
        //    tempos.add(new String[]{cursor.getString(2),cursor.getString(3),cursor.getString(4)});
        //    tempos.get(cont).
        //}while(cursor.moveToNext());

        instanciaDB.close();
        return cursor;
    }

    public String formataTempo(String xis){
        int elapsed = 0;

        try {
            elapsed = Integer.parseInt(xis);
        } catch (NumberFormatException e) {
            System.out.println("Numero com formato errado!");
        }

        int ss = elapsed % 60;
        elapsed /= 60;
        int min = elapsed % 60;
        elapsed /= 60;
        int hh = elapsed % 24;
        return strzero(hh) + ":" + strzero(min) + ":" + strzero(ss);
    }

    private String strzero(int n){
        if(n < 10)
            return "0" + String.valueOf(n);
        return String.valueOf(n);
    }
}
