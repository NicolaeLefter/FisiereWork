package com.example.FisiereWork.entity.salesEntity;

import com.example.FisiereWork.entity.produsEntity.Produs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Sales implements Serializable {

    /*
    id_vanzare INT NOT NULL AUTO_INCREMENT,
id_produs INT NOT NULL,
name_produs VARCHAR(50) NOT NULL,
data_sale DATE NOT NULL,
cantitatea_vanduta INT NOT NULL,
total_price DECIMAL(15,2) NOT NULL,*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vanzare")
    private Integer idVanzare;

    @ManyToOne()
    @JoinColumn(name = "id_produs")
    private Produs produs;
    @Column(name = "data_sale")
    private LocalDate dataSale;
    @Column(name = "cantitatea_vanduta")
    private Double cantitateaVanduta;
    @Column(name = "total_price")
    private Double totalPrice;

    public Integer getIdVanzare() {
        return idVanzare;
    }

    public void setIdVanzare(Integer idVanzare) {
        this.idVanzare = idVanzare;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public LocalDate getDataSale() {
        return dataSale;
    }

    public void setDataSale(LocalDate dataSale) {
        this.dataSale = dataSale;
    }

    public Double getCantitateaVanduta() {
        return cantitateaVanduta;
    }

    public void setCantitateaVanduta(Double cantitateaVanduta) {
        this.cantitateaVanduta = cantitateaVanduta;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
