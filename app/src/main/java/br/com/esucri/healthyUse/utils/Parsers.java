package br.com.esucri.healthyUse.utils;

import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parsers {

    public Time parserStringToTime(String time) throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        Date dataAux = formatador.parse(time);
        Time timeAux = new Time(dataAux.getTime());
        return timeAux;
    }

    public Date parserStringToDate(String date) throws ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat( "dd/MM/yyyy");
        Date dataAux = formatador.parse(date);
        return dataAux;
    }
}
