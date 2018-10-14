package br.com.esucri.healthyUse.model;

import java.io.Serializable;
import java.sql.Time;

public class Parametro implements Serializable{
    private Integer id;
    private String rotina;
    private String nome;
    private Time tempoMinimo;
    private Time tempoMaximo;

    public Integer getId() { return id; }

    public String getRotina() { return rotina; }

    public String getNome() { return nome; }

    public Time getTempoMinimo() { return tempoMinimo; }

    public Time getTempoMaximo() { return tempoMaximo; }

    public void setId(Integer id) { this.id = id;  }

    public void setRotina(String rotina) { this.rotina = rotina; }

    public void setNome(String nome) { this.nome = nome; }

    public void setTempoMinimo(Time tempoMinimo) { this.tempoMinimo = tempoMinimo; }

    public void setTempoMaximo(Time tempoMaximo) { this.tempoMaximo = tempoMaximo; }

    @Override
    public String toString() { return nome; }
}