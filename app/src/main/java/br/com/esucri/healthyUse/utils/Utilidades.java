package br.com.esucri.healthyUse.utils;

public class Utilidades {

    public String formataTempo(int elapsed){
        int ss = elapsed % 60;
        elapsed /= 60;
        int min = elapsed % 60;
        elapsed /= 60;
        int hh = elapsed % 24;
        return strzero(hh) + ":" + strzero(min) + ":" + strzero(ss);
    }
    private String strzero(int n){
        if(n < 10)
            return "0" + String.valueOf(n);
        return String.valueOf(n);
    }
}
