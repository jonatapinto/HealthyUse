package br.com.esucri.healthyUse.model;

import java.sql.Time;
import java.util.Date;

public class Estatistica {
    private Integer id;
    private String aplicativo;
    private String dataHoraInicio;
    private String dataHoraFim;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAplicativo() {
        return aplicativo;
    }

    public void setAplicativo(String aplicativo) {
        this.aplicativo = aplicativo;
    }

    public String getDataHoraInicio() { return dataHoraInicio; }

    public void setDataHoraInicio(String dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public String getDataHoraFim() { return dataHoraFim; }

    public void setDataHoraFim(String dataHoraFim) { this.dataHoraFim = dataHoraFim; }
}