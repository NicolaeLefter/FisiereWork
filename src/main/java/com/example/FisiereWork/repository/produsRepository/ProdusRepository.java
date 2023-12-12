package com.example.FisiereWork.repository.produsRepository;

import com.example.FisiereWork.entity.produsEntity.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdusRepository extends JpaRepository<Produs, Integer> {


    List<Produs> findAllByMagazinId(Integer idMagazin);

    List<Produs> findAllByNameProdus(String nameProdus);

    Produs findByNameProdus(String nameProdus);
}
