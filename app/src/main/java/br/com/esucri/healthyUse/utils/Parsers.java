package br.com.esucri.healthyUse.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Parsers {

    public Time parserStringToTime(String time) {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
        Date dataAux = null;
        try {
            dataAux = formatador.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time timeAux = new Time(dataAux.getTime());
        return timeAux;
    }

    public java.util.Date parserStringToDate(String date) {
        System.out.println("teste: "+date);
        SimpleDateFormat formatador = new SimpleDateFormat( "dd/MM/yyyy");
        Date dataAux = null;
        try {
            dataAux = formatador.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataAux;
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    //public Date testing(String dados){
    //    Date date = null;
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //    LocalDate localDate = LocalDate.parse(dados, formatter);
    //    return date.valueOf(String.valueOf(localDate));
    //}
}
