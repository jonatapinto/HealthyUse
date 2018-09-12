package br.com.esucri.helthyuse.model;

import java.sql.Time;
import java.util.Date;

public class Estatistica {
    private Integer id;
    private Integer idAplicativo;
    private Date dtInicio;
    private Time hrInicio;
    private Date dtFinal;
    private Time hrFinal;

    public Integer getId() {
        return id;
    }

    public Integer getIdAplicativo() {
        return idAplicativo;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public Time getHrInicio() {
        return hrInicio;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public Time getHrFinal() {
        return hrFinal;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdAplicativo(Integer idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public void setHrInicio(Time hrInicio) {
        this.hrInicio = hrInicio;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public void setHrFinal(Time hrFinal) {
        this.hrFinal = hrFinal;
    }
}