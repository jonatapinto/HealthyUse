package br.com.esucri.helthyuse.model;

import java.sql.Time;
import java.util.Date;

public class Oficio {
    private Integer id;
    private String nome;
    private String tipo;
    private Time horaInicio;
    private Time horaFim;
    private char dom;
    private char seg;
    private char ter;
    private char quar;
    private char quin;
    private char sex;
    private char sab;
    private Date dataTermino;

    public Integer getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getTipo() {
        return tipo;
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
        return dataTermino;
    }

    public Time getHora_fim() {
        return horaFim;
    }

    public Time getHora_inicio() {
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

    public void setHora_inicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHora_fim(Time horaFim) {
        this.horaFim = horaFim;
    }

    public void setData_termino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }
}