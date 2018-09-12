package br.com.esucri.helthyuse.model;

import java.sql.Time;
import java.util.Date;

public class Estatistica {
    private Integer idEstatistica;
    private Integer idAplicativo;
    private Date dtInicio;
    private Time hrInicio;
    private Date dtFinal;
    private Time hrFinal;

    public Integer getIdEstatistica() {
        return idEstatistica;
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

    public void setIdEstatistica(Integer idEstatistica) {
        this.idEstatistica = idEstatistica;
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