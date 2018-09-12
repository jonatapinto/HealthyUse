package br.com.esucri.helthyuse.model;

import java.sql.Time;
import java.util.Date;

public class Oficio {
    private Integer id;
    private String nome;
    private String tipo;
    private Time horaInicio;
    private Time horaFim;
    private Date dataFim;
    private Integer dom;
    private Integer seg;
    private Integer ter;
    private Integer qua;
    private Integer qui;
    private Integer sex;
    private Integer sab;

    public Integer getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

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

    public Date getDataFim() {
        return dataFim;
    }

    public Time getHoraFim() {
        return horaFim;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(Time horaFim) {
        this.horaFim = horaFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}