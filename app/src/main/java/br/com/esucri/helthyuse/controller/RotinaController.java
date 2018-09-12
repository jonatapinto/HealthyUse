package br.com.esucri.helthyuse.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.helthyuse.model.Aplicativo;
import br.com.esucri.helthyuse.model.Oficio;
import br.com.esucri.helthyuse.utils.BancoDeDados;

public class RotinaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public long create(final Oficio oficio){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME",oficio.getNome());
        dados.put("TIPO",oficio.getTipo());
        dados.put("HORA_INI",oficio.getHoraInicio().toString());
        dados.put("HORA_FIM",oficio.getHoraFim().toString());
        dados.put("DOM",oficio.getDom());
        dados.put("SEG",oficio.getSeg());
        dados.put("TER",oficio.getTer());
        dados.put("QUA",oficio.getQua());
        dados.put("QUI",oficio.getQui());
        dados.put("SEX",oficio.getSex());
        dados.put("SAB",oficio.getSab());
        dados.put("DATA_FIM",oficio.getDataFim().toString());

        resultado = instanciaDB.insert("OFICIO", null, dados);
        instanciaDB.close();

        return resultado;
    }

    //public Cursor retrieve(){
    //    return ;
    //}

    //public long update(final Oficio oficio){
    //    return 1;
    //}

    //public long delete(final Oficio oficio) {
    //    return 1;
    //}
}
