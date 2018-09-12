package br.com.esucri.helthyuse.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import br.com.esucri.helthyuse.model.Rotina;
import br.com.esucri.helthyuse.utils.BancoDeDados;

public class RotinaController {
    private SQLiteDatabase instanciaDB;
    private BancoDeDados db;

    public long create(final Rotina rotina){
        ContentValues dados = new ContentValues();
        long resultado;

        instanciaDB = db.getWritableDatabase();
        dados.put("NOME", rotina.getNome());
        dados.put("TIPO", rotina.getTipo());
        dados.put("HORA_INICIO", rotina.getHoraInicio().toString());
        dados.put("HORA_FINAL", rotina.getHoraFinal().toString());
        dados.put("DOM", rotina.getDom());
        dados.put("SEG", rotina.getSeg());
        dados.put("TER", rotina.getTer());
        dados.put("QUA", rotina.getQua());
        dados.put("QUI", rotina.getQui());
        dados.put("SEX", rotina.getSex());
        dados.put("SAB", rotina.getSab());
        dados.put("INSTAGRAM", rotina.getInstagram());
        dados.put("FACEBOOK", rotina.getFacebook());
        dados.put("WHATSAPP", rotina.getWhatsapp());
        dados.put("DATA_FINAL", rotina.getDataFinal().toString());

        resultado = instanciaDB.insert("ROTINA", null, dados);
        instanciaDB.close();

        return resultado;
    }

    //public Cursor retrieve(){
    //    return ;
    //}

    //public long update(final Rotina rotina){
    //    return 1;
    //}

    //public long delete(final Rotina rotina) {
    //    return 1;
    //}
}
