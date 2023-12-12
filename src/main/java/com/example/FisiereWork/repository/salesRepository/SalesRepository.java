package com.example.FisiereWork.repository.salesRepository;

import com.example.FisiereWork.entity.salesEntity.Sales;
import com.example.FisiereWork.entity.produsEntity.Produs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface SalesRepository  extends JpaRepository<Sales,Integer> {

    List<Sales> findByProdusAndDataSale( Produs produs, Date starData);
    List<Sales> findByProdusAndDataSaleAfter( Produs produs, LocalDate starData);
    List<Sales> findByProdus(Produs produs);

   // List<Sales> findByProdusAndStarDataSaleAndEndDataSale(Produs produs, Date startData, Date endData);
}
