package br.com.esucri.healthyUse.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Rotina implements Serializable{
    private Integer id;
    private String nome;
    private Time horaInicio;
    private Time horaFinal;
    private Integer dom;
    private Integer seg;
    private Integer ter;
    private Integer qua;
    private Integer qui;
    private Integer sex;
    private Integer sab;
    private Integer instagram;
    private Integer facebook;
    private Integer whatsapp;
    private Integer status;

    public Integer getId(){
        return id;
    }

    public String getNome() { return nome; }

    public Integer getDom() {
        return dom;
    }

    public Integer getSeg() {
        return seg;
    }

    public Integer getTer() {
        return ter;
    }

    public Integer getQua() {
        return qua;
    }

    public Integer getQui() {
        return qui;
    }

    public Integer getSex() {
        return sex;
    }

    public Integer getSab() {
        return sab;
    }

    public Integer getInstagram() { return instagram; }

    public Integer getFacebook() { return facebook; }

    public Integer getWhatsapp() { return whatsapp; }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDom(Integer dom) {
        this.dom = dom;
    }

    public void setSeg(Integer seg) {
        this.seg = seg;
    }

    public void setTer(Integer ter) {
        this.ter = ter;
    }

    public void setQua(Integer qua) {
        this.qua = qua;
    }

    public void setQui(Integer qui) {
        this.qui = qui;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setSab(Integer sab) {
        this.sab = sab;
    }

    public void setInstagram(Integer instagram) { this.instagram = instagram; }

    public void setFacebook(Integer facebook) { this.facebook = facebook; }

    public void setWhatsapp(Integer whatsapp) { this.whatsapp = whatsapp; }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() { return nome; }
}