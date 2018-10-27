package br.com.esucri.healthyUse.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.esucri.healthyUse.controller.EstatisticaController;
import br.com.esucri.healthyUse.model.Estatistica;

public class BancoDeDados extends SQLiteOpenHelper {
    private static final String DB_NOME = "HEALTHYUSE";
    private static final int DB_VERSAO = 1;
    private Context context;

    public BancoDeDados(Context context){
        super(context,DB_NOME, null, DB_VERSAO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableOficio =
                "CREATE TABLE ROTINA (" +
                        "  _id integer not null primary key autoincrement," +
                        "  NOME text not null," +
                        "  HORA_INICIO text not null,"+
                        "  HORA_FIM text not null,"+
                        "  DOM integer not null,"+
                        "  SEG integer not null,"+
                        "  TER integer not null,"+
                        "  QUA integer not null,"+
                        "  QUI integer not null,"+
                        "  SEX integer not null,"+
                        "  SAB integer not null,"+
                        "  INSTAGRAM integer not null,"+
                        "  FACEBOOK integer not null,"+
                        "  WHATSAPP integer not null,"+
                        "  STATUS integer not null)";
        db.execSQL(createTableOficio);

        String createTableEstatistica =
                "CREATE TABLE ESTATISTICA ("+
                        "_id integer not null primary key autoincrement,"+
                        "APLICATIVO text not null,"+
                        "DATA_HORA_INICIO text not null," +
                        "DATA_HORA_FIM text not null)";
        db.execSQL(createTableEstatistica);

        String createTableParametro =
                "CREATE TABLE PARAMETRO (" +
                        "  _id integer not null primary key autoincrement," +
                        "  ROTINA text not null," +
                        "  NOME text not null," +
                        "  TEMPO_MINIMO text not null," +
                        "  TEMPO_MAXIMO text not null)";
        db.execSQL(createTableParametro);
        System.out.println("Passou o create do banco");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        for (int i = oldVersion; oldVersion <= newVersion; i++)
            if (i==2){
                upgradeVersao2(db);
            }

        final String dropTableAplicativo = "DROP TABLE IF EXISTS ESTATISTICA";
        db.execSQL(dropTableAplicativo);

        final String dropTableOficio = "DROP TABLE IF EXISTS ROTINA";
        db.execSQL(dropTableOficio);

        final String dropTableParametro = "DROP TABLE IF EXISTS PARAMETRO";
        db.execSQL(dropTableParametro);

        onCreate(db);
    }

    public void upgradeVersao2(SQLiteDatabase db){
    }

}