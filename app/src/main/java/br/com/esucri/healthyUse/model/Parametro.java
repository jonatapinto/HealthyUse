package br.com.esucri.healthyUse.model;

import java.sql.Time;
import java.util.Date;

public class Parametro {
    private Integer id;
    private Integer idAplicativo;
    private String nome;
    private Time tempoMinimo;
    private Time tempoMaximo;

    public Integer getId() { return id; }

    public Integer getIdAplicativo() { return idAplicativo; }

    public String getNome() { return nome; }

    public Time getTempoMinimo() { return tempoMinimo; }

    public Time getTempoMaximo() { return tempoMaximo; }

    public void setId(Integer id) { this.id = id;  }

    public void setIdAplicativo(Integer idAplicativo) { this.idAplicativo = idAplicativo; }

    public void setNome(String nome) { this.nome = nome; }

    public void setTempoMinimo(Time tempoMinimo) { this.tempoMinimo = tempoMinimo; }

    public void setTempoMaximo(Time tempoMaximo) { this.tempoMaximo = tempoMaximo; }
}