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
    private SQLiteDatabase instanciaDB;
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
                        "  HORA_FINAL text not null,"+
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
                        "_id              INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                        "APLICATIVO       TEXT    NOT NULL,"+
                        "DATA_HORA_INICIO TEXT    NOT NULL,"+
                        "DATA_HORA_FIM    TEXT    NOT NULL)";
        db.execSQL(createTableEstatistica);
        System.out.println("TABELA ESTATISTICA "+createTableEstatistica);
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

    public void insereDadosSimulados(){
        String inserts;
        instanciaDB = getWritableDatabase();
        Cursor cursor = instanciaDB.rawQuery("SELECT COUNT() FROM ESTATISTICA",null);

        //if(cursor.getCount() != 0){
        //    return;
        //}

        Estatistica est1 = new Estatistica();
        Estatistica est2 = new Estatistica();

        est1.setAplicativo("FACEBOOK");
        est1.setDataHoraInicio("2018-10-10 10:00:00");
        est1.setDataHoraFim("2018-10-10 11:00:00");

        est2.setAplicativo("INSTAGRAM");
        est2.setDataHoraInicio("2018-10-10 11:01:00");
        est2.setDataHoraFim("2018-10-10 11:30:00");

        EstatisticaController e = new EstatisticaController(this.context);

        e.create(est1);
        e.create(est2);

        System.out.println("PASSANDO PELOS INSERTS");

        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 07:40:00','2018-09-10 07:43:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 07:43:00','2018-09-10 07:50:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 09:30:00','2018-09-10 09:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 09:40:00','2018-09-10 09:45:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 11:50:00','2018-09-10 11:55:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 11:56:00','2018-09-10 11:59:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:10:00','2018-09-10 12:11:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 12:11:00','2018-09-10 12:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:13:00','2018-09-10 12:15:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 12:20:00','2018-09-10 12:25:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 12:50:00','2018-09-10 12:51:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 12:55:00','2018-09-10 13:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 15:40:00','2018-09-10 15:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 15:47:00','2018-09-10 15:48:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 15:48:00','2018-09-10 15:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 15:52:00','2018-09-10 15:54:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 18:02:00','2018-09-10 18:03:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 18:03:00','2018-09-10 18:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 18:32:00','2018-09-10 18:33:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 18:42:00','2018-09-10 18:58:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 19:22:00','2018-09-10 19:42:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 19:46:00','2018-09-10 19:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 20:03:00','2018-09-10 20:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 20:05:00','2018-09-10 20:32:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 20:32:00','2018-09-10 20:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 20:36:00','2018-09-10 21:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-10 22:02:00','2018-09-10 22:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-10 23:30:00','2018-09-10 23:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 23:47:00','2018-09-10 23:53:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-10 23:59:00','2018-09-11 00:08:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 07:12:00','2018-09-11 07:43:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 07:43:00','2018-09-11 07:50:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 09:30:00','2018-09-11 09:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 09:40:00','2018-09-11 09:45:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:00:00','2018-09-11 12:01:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:01:00','2018-09-11 12:05:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:10:00','2018-09-11 12:11:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:11:00','2018-09-11 12:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:13:00','2018-09-11 12:15:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 12:20:00','2018-09-11 12:25:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 12:50:00','2018-09-11 12:51:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 12:55:00','2018-09-11 13:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 15:40:00','2018-09-11 15:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 15:47:00','2018-09-11 15:48:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 15:48:00','2018-09-11 15:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 15:52:00','2018-09-11 15:54:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 18:02:00','2018-09-11 18:03:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 18:03:00','2018-09-11 18:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 18:32:00','2018-09-11 18:33:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 18:42:00','2018-09-11 18:58:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 19:22:00','2018-09-11 19:42:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 19:46:00','2018-09-11 19:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 20:03:00','2018-09-11 20:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 20:05:00','2018-09-11 20:32:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 20:32:00','2018-09-11 20:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 20:36:00','2018-09-11 21:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-11 22:02:00','2018-09-11 22:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 23:30:00','2018-09-11 23:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-11 23:47:00','2018-09-11 23:53:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-11 23:59:00','2018-09-12 00:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 07:40:00','2018-09-12 07:43:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 07:43:00','2018-09-12 07:50:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 09:30:00','2018-09-12 09:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 09:40:00','2018-09-12 09:45:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 11:50:00','2018-09-12 11:55:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 11:56:00','2018-09-12 11:59:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:10:00','2018-09-12 12:11:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 12:11:00','2018-09-12 12:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:13:00','2018-09-12 12:15:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 12:20:00','2018-09-12 12:25:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 12:50:00','2018-09-12 12:51:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 12:55:00','2018-09-12 13:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 15:40:00','2018-09-12 15:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 15:47:00','2018-09-12 15:48:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 15:48:00','2018-09-12 15:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 15:52:00','2018-09-12 15:54:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 18:02:00','2018-09-12 18:03:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 18:03:00','2018-09-12 18:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 18:32:00','2018-09-12 18:33:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 18:42:00','2018-09-12 18:58:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 19:22:00','2018-09-12 19:42:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 19:46:00','2018-09-12 19:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 20:03:00','2018-09-12 20:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 20:05:00','2018-09-12 20:32:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 20:32:00','2018-09-12 20:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 20:36:00','2018-09-12 21:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-12 22:02:00','2018-09-12 22:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 23:30:00','2018-09-12 23:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-12 23:47:00','2018-09-12 23:53:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-12 23:59:00','2018-09-13 00:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 00:33:00','2018-09-13 00:49:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 00:50:00','2018-09-13 00:55:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 00:56:00','2018-09-13 00:59:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 07:23:00','2018-09-13 07:43:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 07:43:00','2018-09-13 07:50:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 09:30:00','2018-09-13 09:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 09:40:00','2018-09-13 09:45:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:00:00','2018-09-13 12:01:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:01:00','2018-09-13 12:05:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:10:00','2018-09-13 12:11:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:11:00','2018-09-13 12:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 12:13:00','2018-09-13 12:15:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 12:20:00','2018-09-13 12:25:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 12:50:00','2018-09-13 12:51:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 12:55:00','2018-09-13 13:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 15:40:00','2018-09-13 15:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 15:47:00','2018-09-13 15:48:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 15:48:00','2018-09-13 15:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 15:52:00','2018-09-13 15:54:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 18:02:00','2018-09-13 18:03:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 18:03:00','2018-09-13 18:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 18:32:00','2018-09-13 18:33:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 18:42:00','2018-09-13 18:58:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 19:22:00','2018-09-13 19:42:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 19:46:00','2018-09-13 19:52:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 20:03:00','2018-09-13 20:04:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 20:05:00','2018-09-13 20:32:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 20:32:00','2018-09-13 20:35:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 20:36:00','2018-09-13 21:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-13 22:02:00','2018-09-13 22:12:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 23:30:00','2018-09-13 23:46:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'WHATSAPP','2018-09-13 23:47:00','2018-09-13 23:53:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'INSTAGRAM','2018-09-13 23:59:00','2018-09-14 00:02:00');";
        //instanciaDB.execSQL(inserts);
        //inserts = "INSERT INTO ESTATISTICA (_id,APLICATIVO,DATA_HORA_INICIO,DATA_HORA_FIM) VALUES ((SELECT MAX(_id)+1 FROM ESTATISTICA),'FACEBOOK','2018-09-14 00:28:00','2018-09-14 00:37:00');";
        //instanciaDB.execSQL(inserts);

        instanciaDB.close();

        System.out.println("CLOSE DO INSTANCIADB");
    }

}