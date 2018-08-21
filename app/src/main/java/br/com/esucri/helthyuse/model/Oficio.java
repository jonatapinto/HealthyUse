package br.com.esucri.helthyuse.model;

import java.sql.Time;
import java.util.Date;

public class Oficio {
    private Integer id_oficio;
    private String nome;
    private Time hora_inicio;
    private Time hora_fim;
    private char dom;
    private char seg;
    private char ter;
    private char quar;
    private char quin;
    private char sex;
    private char sab;
    private Date data_termino;

    public Integer getId_oficio(){
        return id_oficio;
    }

    public String getNome(){
        return nome;
    }

    public char getDom() {
        return dom;
    }

    public char getSeg() {
        return seg;
    }

    public char getTer() {
        return ter;
    }

    public char getQuar() {
        return quar;
    }

    public char getQuin() {
        return quin;
    }

    public char getSex() {
        return sex;
    }

    public char getSab() {
        return sab;
    }

    public Date getData_termino() {
        return data_termino;
    }

    public Time getHora_fim() {
        return hora_fim;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setId_oficio(Integer id_oficio) {
        this.id_oficio = id_oficio;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDom(char dom) {
        this.dom = dom;
    }

    public void setSeg(char seg) {
        this.seg = seg;
    }

    public void setTer(char ter) {
        this.ter = ter;
    }

    public void setQuar(char quar) {
        this.quar = quar;
    }

    public void setQuin(char quin) {
        this.quin = quin;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setSab(char sab) {
        this.sab = sab;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public void setHora_fim(Time hora_fim) {
        this.hora_fim = hora_fim;
    }

    public void setData_termino(Date data_termino) {
        this.data_termino = data_termino;
    }
}