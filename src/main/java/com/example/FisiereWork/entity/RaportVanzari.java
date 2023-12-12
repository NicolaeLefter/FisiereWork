package com.example.FisiereWork.entity;

public class RaportVanzari {


    private String numeProdus;
    private Double cantitateTotala;
    private Double sumaTotala;

    public RaportVanzari(String numeProdus, Double cantitateTotala, Double sumaTotala) {
        this.numeProdus = numeProdus;
        this.cantitateTotala = cantitateTotala;
        this.sumaTotala = sumaTotala;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public Double getCantitateTotala() {
        return cantitateTotala;
    }

    public void setCantitateTotala(Double cantitateTotala) {
        this.cantitateTotala = cantitateTotala;
    }

    public Double getSumaTotala() {
        return sumaTotala;
    }

    public void setSumaTotala(Double sumaTotala) {
        this.sumaTotala = sumaTotala;
    }
}
