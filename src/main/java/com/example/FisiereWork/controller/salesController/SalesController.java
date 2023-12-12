package com.example.FisiereWork.controller.salesController;

import com.example.FisiereWork.entity.salesEntity.Sales;
import com.example.FisiereWork.entity.magazinEntity.Magazin;
import com.example.FisiereWork.entity.produsEntity.Produs;
import com.example.FisiereWork.repository.salesRepository.SalesRepository;
import com.example.FisiereWork.service.salesService.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/sales")
public class SalesController {

    @Autowired
    SalesRepository salesRepository;
    @Autowired
    SalesService salesService;

    @PostMapping("/save")
    public ResponseEntity<Object> addSales(@RequestBody Sales sales) {
        return ResponseEntity.status(HttpStatus.OK).body(salesRepository.save(sales));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(salesRepository.findAll());
    }
  //  @RequestBody Produs produs, @RequestParam Data startData
    @GetMapping("/raport")
    public ResponseEntity<Object> raportSalesByProdus( ){

      //@RequestBody Produs produs, @RequestParam Date startData
        Magazin magazin = new Magazin(1, "Linella");
        Produs produs = new Produs(3, "Lapte", magazin);
        Date startData = new Date("11/12/2023");
        System.out.println("am ajuns" + produs.toString() + " start data " + startData);
       return ResponseEntity.ok(salesService.raportSalesByNameProdus(produs, startData));

    }

    @PostMapping("/raport2")
    public ResponseEntity<Object> reportSales(@RequestBody Produs produs,
                                              @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date startData){
        return ResponseEntity.status(HttpStatus.OK).body(salesService.raportSalesByNameProdus(produs, startData));
    }
    @PostMapping("/raport1")
    public ResponseEntity<Object> raportSalesByProdus(@RequestParam String numeProdus,
                                                      @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date startData) {
        try {
            Produs produs = new Produs();
            produs.setNameProdus(numeProdus);

            return ResponseEntity.ok(salesService.raportSalesByNameProdus(produs, startData));
        } catch (Exception e) {
            // Tratați excepțiile aici sau returnați o altă ResponseEntity în caz de eroare
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Eroare la generarea raportului: " + e.getMessage());
        }
    }
    @GetMapping("/generate")
    public ResponseEntity<Object> generateSalesReport(@RequestParam String produsName,
                                                      @RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date data){
        try {
            salesService.generateSalesReport(produsName, data);
            return ResponseEntity.ok("Raportul a fost generat cu succes și salvat în raport.json.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Eroare la generarea raportului: " + e.getMessage());
        }

    }
    @GetMapping("/raportTotal")
    public ResponseEntity<String> generateRaportAll(@RequestParam
                                                    @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDate startData){
        try {
            salesService.generateSalesReportForAllProducts(startData);
            return ResponseEntity.ok("Raportul a fost geenrat cu succes si salvat in fisierul raportAll.json.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroare la generarea raportului: " + e.getMessage());
        }
    }


}
