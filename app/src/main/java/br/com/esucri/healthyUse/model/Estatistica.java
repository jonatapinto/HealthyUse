package br.com.esucri.healthyUse.model;

import java.sql.Time;
import java.util.Date;

public class Estatistica {
    private Integer id;
    private Integer idAplicativo;
    private Date dataInicio;
    private Time horaInicio;
    private Date dataFinal;
    private Time horaFinal;

    public Integer getId() {
        return id;
    }

    public Integer getIdAplicativo() {
        return idAplicativo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdAplicativo(Integer idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }
}