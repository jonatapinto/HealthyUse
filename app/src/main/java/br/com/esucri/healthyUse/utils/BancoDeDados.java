package br.com.esucri.healthyUse.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {
    private static final String DB_NOME = "HEALTHYUSE";
    private static final int DB_VERSAO = 1;

    public BancoDeDados(Context context){
        super(context,DB_NOME, null, DB_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String createTableOficio =
                "CREATE TABLE ROTINA (" +
                        "  _id integer not null primary key autoincrement," +
                        "  NOME text not null," +
                        "  HORA_INICIO text not null,"+
                        "  HORA_FINAL text not null,"+
                        "  DOM integer not null,"+
                        "  SEG integer not null,"+
                        "  TER integer not null,"+
                        "  QUA integer not null,"+
                        "  QUI integer not null,"+
                        "  SEX integer not null,"+
                        "  SAB integer not null," +
                        "  INSTAGRAM integer not null," +
                        "  FACEBOOK integer not null," +
                        "  WHATSAPP integer not null,"+
                        "  STATUS integer not null)";
        db.execSQL(createTableOficio);

        String createTableEstatistica =
                "CREATE TABLE ESTATISTICA (" +
                        " _id integer not null primary key autoincrement," +
                        " ID_APLICATIVO integer not null," +
                        " DATA_INICIO text not null," +
                        " HORA_INICIO text not null," +
                        " DATA_FINAL text not null," +
                        " HORA_FINAL time not null)";
        db.execSQL(createTableEstatistica);

        String createTableParametro =
                "CREATE TABLE PARAMETRO (" +
                        "  _id integer not null primary key autoincrement," +
                        "  ROTINA text not null," +
                        "  NOME text not null," +
                        "  TEMPO_MINIMO text not null," +
                        "  TEMPO_MAXIMO text not null)";
        db.execSQL(createTableParametro);
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