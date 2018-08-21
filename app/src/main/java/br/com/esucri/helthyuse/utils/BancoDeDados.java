package br.com.esucri.helthyuse.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {
    private static final String DB_NOME = "HelthyUse";
    private static final int DB_VERSAO = 1;

    public BancoDeDados(Context context){
        super(context,DB_NOME, null, DB_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String create_table_Aplicativo =
                "CREATE TABLE Aplicativo (" +
                    "  _id integer primary key autoincrement," +
                    "  nome text not null)";
        db.execSQL(create_table_Aplicativo);

        String create_table_Oficio =
                "CREATE TABLE Oficio (" +
                    "  _id integer primary key autoincrement," +
                    "  nome text not null,"+
                    "  hora_ini numeric not null,"+ 
                    "  hora_fim numeric not null,"+
                    "  dom text not null,"+
                    "  seg text not null,"+
                    "  ter text not null,"+
                    "  qua text not null,"+
                    "  qui text not null,"+
                    "  sex text not null,"+
                    "  sab text not null,"+
                    "  data_termino numeric)";
        db.execSQL(create_table_Oficio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        for (int i = oldVersion; oldVersion <= newVersion; i++)
            if (i==2){
                upgradeVersao2(db);
            }
        final String drop_table_Aplicativo = "DROP TABLE IF EXISTS Aplicativo";
        db.execSQL(drop_table_Aplicativo);

        final String drop_table_Oficio = "DROP TABLE IF EXISTS Oficio";
        db.execSQL(drop_table_Oficio);

        onCreate(db);
    }
    public void upgradeVersao2(SQLiteDatabase db){

    }
}