package com.example.FisiereWork.entity.magazinEntity;

import com.example.FisiereWork.entity.produsEntity.Produs;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Magazin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "denumire")
    private String denumire;
    @JsonIgnore
    @OneToMany(mappedBy = "magazin")
    private List<Produs> produsList;
    private static final long serialVersionUID = -210000004565758657L;

    public Magazin(Integer id, String denumire, List<Produs> produsList) {
        this.id = id;
        this.denumire = denumire;
        this.produsList = produsList;
    }

    public Magazin(Integer id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public Magazin() {

    }

    public List<Produs> getProdusList() {
        return produsList;
    }

    public void setProdusList(List<Produs> produsList) {
        this.produsList = produsList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Magazin{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", produsList=" + produsList +
                '}';
    }

    //de citit si de salvat in baza de date, de creat un api care se poate de incarcat un fisier, datele la fisier sa fie citite
    // si salvate in baza de date
}
