package br.com.esucri.healthyUse.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;

public class Validations {

    public Validations(){

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isDateValid(String strDate) {
        String dateFormat = "dd/MM/yyyy";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
                                              //.withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate date = LocalDate.parse(strDate, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isTimeValid(String hora){
        SimpleDateFormat sdf  = new SimpleDateFormat( "HH:mm" );
        //Não soma a data para o dia seguinte quando informado 25:00 por exemplo.
        sdf.setLenient(false);
        try {
            Date d = sdf.parse(hora);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
