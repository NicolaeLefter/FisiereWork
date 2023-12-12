package com.example.FisiereWork.entity.produsEntity;

import com.example.FisiereWork.entity.salesEntity.Sales;
import com.example.FisiereWork.entity.magazinEntity.Magazin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "produs1")
public class Produs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produs")
    private Integer idProdus;
    @Column(name = "name_produs")
    private String nameProdus;
    @ManyToOne
    @JoinColumn(name = "id_magazin")
    private Magazin magazin;
    //@ManyToOne
    //@JoinColumn(name = "id_produs")
    @JsonIgnore
    @OneToMany(mappedBy = "produs")
    private List<Sales> salesList;
    @Column(name = "price")
    private Double price;

    public Produs() {

    }

    public Produs(Integer idProdus, String name, Magazin magazin) {
        this.idProdus = idProdus;
        this.nameProdus = name;
        this.magazin = magazin;
    }


    private static final long serialVersionUID = -120000004565758657L;


    public List<Sales> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList) {
        this.salesList = salesList;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Magazin getMagazin() {
        return magazin;
    }

    public void setMagazin(Magazin magazin) {
        this.magazin = magazin;
    }

    public Integer getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(Integer idProdus) {
        this.idProdus = idProdus;
    }

    public String getNameProdus() {
        return nameProdus;
    }

    public void setNameProdus(String nameProdus) {
        this.nameProdus = nameProdus;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "idProdus=" + idProdus +
                ", nameProdus='" + nameProdus + '\'' +
                ", magazin=" + magazin.getId() +
                '}';
    }
}
