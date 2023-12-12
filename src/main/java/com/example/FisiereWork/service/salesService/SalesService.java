package com.example.FisiereWork.service.salesService;

import com.example.FisiereWork.entity.RaportVanzari;
import com.example.FisiereWork.entity.salesEntity.Sales;
import com.example.FisiereWork.entity.produsEntity.Produs;
import com.example.FisiereWork.repository.salesRepository.SalesRepository;
import com.example.FisiereWork.repository.produsRepository.ProdusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService {

    @Autowired
    SalesRepository salesRepository;
    @Autowired
    ProdusRepository produsRepository;


   @Transactional
    public ResponseEntity<InputStreamResource> raportSalesByNameProdus(Produs produs, Date startData) {
        try {
            String statusRaport;
            /*List<Sales> salesList = salesRepository.findByProdusAndDataSale(produs, startData);
            Double totalVanzari = 0.0;
            Double sumaTotal = 0.0;
            Double sumaTemporar = 0.0; //suma  de produse temporara din lista de produse
            for (Sales sales : salesList) {
                totalVanzari += sales.getCantitateaVanduta();
                List<Produs> produsList = produsRepository.findAllByNameProdus(produs.getNameProdus());
                for (Produs produs1 : produsList) {
                    if (produs1.getNameProdus().toUpperCase().equals(produs.getNameProdus().toUpperCase())) {
                        sumaTemporar += produs1.getPrice() * sales.getCantitateaVanduta();
                    }
                    sumaTotal = sumaTemporar;
                }
            } */

           List<Sales> salesList = salesRepository.findByProdusAndDataSale(produs, startData);
            Double totalVanzari = 0.0;
            Double sumaTotal = 0.0;

            for (Sales sales : salesList) {
                totalVanzari += sales.getCantitateaVanduta();
                List<Produs> produsList = produsRepository.findAllByNameProdus(produs.getNameProdus());
                for (Produs produs1 : produsList) {
                    if (produs1.getNameProdus().equalsIgnoreCase(produs.getNameProdus())) {
                        sumaTotal += produs1.getPrice() * sales.getCantitateaVanduta();
                    }
                }
            }

            statusRaport = "Produsul: " + produs.getNameProdus() + " cu id-ul: " + produs.getIdProdus() +
                    "de la data de: " + startData + " pana la data curenta, au fost inregistrate vanzari totale de: " +
                    totalVanzari + " in valoare de: " + sumaTotal + " lei " + " informatia data este salvata in file!";

            System.out.println("Status raport este " + statusRaport);


            try {
                File file = new File("file.json");
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Is created " + file.getName());
                    FileWriter writer = new FileWriter("file.json");
                    writer.write(statusRaport);
                    writer.close();
                } else if (file.exists()) {
                    System.out.println("File exist!");
                    FileWriter writer = new FileWriter("file.json");
                    writer.write(statusRaport);
                    writer.close();

                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Nu au fost gasite produse cu denumirea : " + produs.getNameProdus(), e);
        }
    }

    @Transactional
    public void generateSalesReport(String productName, Date startDate) {
        try {
            Produs produs = produsRepository.findByNameProdus(productName);

            if (produs == null) {
                throw new RuntimeException("Produsul nu a fost găsit.");
            }

            List<Sales> salesList = salesRepository.findByProdusAndDataSale(produs, startDate);

            Double totalCantitate = 0.0;
            Double totalSuma = 0.0;

            for (Sales sales : salesList) {
                totalCantitate += sales.getCantitateaVanduta();
                totalSuma += sales.getTotalPrice();
            }

            RaportVanzari raport = new RaportVanzari(productName, totalCantitate, totalSuma);

            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("raport.json");
            objectMapper.writeValue(file, raport);

        } catch (IOException e) {
            throw new RuntimeException("Eroare la generarea raportului: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void generateSalesReportForAllProducts(LocalDate startData) {
        try {
            List<Produs> allProducts = produsRepository.findAll();

            List<RaportVanzari> raportVanzari = new ArrayList<>();

            for (Produs produs : allProducts) {
               // List<Sales> salesList = salesRepository.findByProdusAndDataSale(produs, startDate);
                List<Sales> salesList = produs.getSalesList().stream()
                        .filter((a)->a.getDataSale().isAfter(startData))
                        .collect(Collectors.toList());


                Double totalCantitate = 0.0;
                Double totalSuma = 0.0;

                for (Sales sales : salesList) {
                    totalCantitate += sales.getCantitateaVanduta();
                    totalSuma += sales.getTotalPrice();
                }
                raportVanzari.add(new RaportVanzari(produs.getNameProdus(),totalCantitate, totalSuma));


            }
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("raportAll.json");
            objectMapper.writeValue(file, raportVanzari);

        } catch (IOException e) {
            throw new RuntimeException("Eroare la generarea raportului: " + e.getMessage(), e);
        }
    }

}


