package com.example.FisiereWork.controller.produsController;

import com.example.FisiereWork.repository.magazinRepository.MagazinRespository;
import com.example.FisiereWork.repository.produsRepository.ProdusRepository;
import com.example.FisiereWork.entity.magazinEntity.Magazin;
import com.example.FisiereWork.entity.produsEntity.Produs;
import com.example.FisiereWork.service.produsSerivce.ProdusService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/file")
public class FileController {
    @Autowired
    MagazinRespository magazinRespository;
    @Autowired
    ProdusRepository produsRepository;
    @Autowired
    ProdusService produsService;


    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException, ClassNotFoundException {
        String fileStatus;
        Produs produs;

        ObjectMapper mapper = new ObjectMapper();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("fisier.json");
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            produs = mapper.readValue(Paths.get("fisier.json").toFile(), Produs.class);
            fileStatus = "Fisierul a fost incarcat cu succes!";
        } catch (Exception e) {
            fileStatus = "Fisierul nu a fost incarcat!";
            throw new RuntimeException(e);
        }
        if (produs.getMagazin().getId() == null) {
            Magazin magazin1 = magazinRespository.save(produs.getMagazin());
            produs.setMagazin(magazin1);
        }
        produsRepository.save(produs);

        System.out.println(produs);
        return fileStatus;

    }

    @PostMapping("/uploadAll")
    public String uploadAll(@RequestParam MultipartFile file) throws IOException, ClassNotFoundException {
        String fileStatus;
        List<Produs> produsList;

        ObjectMapper mapper = new ObjectMapper();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("fisier.json");
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            produsList = Arrays.asList(mapper.readValue(Paths.get("fisier.json").toFile(), Produs[].class));
            fileStatus = "Fisierul a fost incarcat cu succes!";
        } catch (Exception e) {
            fileStatus = "Fisierul nu a fost incarcat!";
            throw new RuntimeException(e);
        }
        for (Produs produs : produsList) {
            if (produs.getMagazin().getId() == null) {
                Magazin magazin1 = magazinRespository.save(produs.getMagazin());
                produs.setMagazin(magazin1);
            }
        }

        produsRepository.saveAll(produsList);

        return fileStatus;

    }

    @GetMapping("/dowlandAll")
    public ResponseEntity<InputStreamResource> downlandAll() throws IOException {
        File file1 = new File("fisier2.json");
        file1.createNewFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Paths.get("fisier2.json").toFile(), produsRepository.findAll());
        InputStreamResource input = new InputStreamResource((new FileInputStream(file1)));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/octet-stream")).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + " fisier.json  " + "\"").
                body(input);
    }

    @GetMapping("/downland/{fileName}")
    public ResponseEntity<InputStreamResource> downland(@PathVariable String fileName) throws FileNotFoundException {

        File file = new File(fileName);
        InputStreamResource input = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").
                body(input);

    }
  /*  @GetMapping("/sumaVanzari/{idProdus}")
    public ResponseEntity<Object> sumaVanzariProdus(@PathVariable Integer idProdus) {
          return ResponseEntity.status(HttpStatus.OK).body(produsService.raportVanzariProdus(idProdus));
    }
    @GetMapping("/suma/{name}")
    public ResponseEntity<Object> sumaVanzarilorByDenumire(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(produsService.raportVanzariByDenProdus(name));
    } */

    @PostMapping("/saveMagazin")
    public void saveMagazin(@RequestBody Magazin magazin) {
        magazinRespository.save(magazin);
    }

    @GetMapping("/getById/{idMagazin}")
    public ResponseEntity<Object> getMagazinById(@PathVariable Integer idMagazin) {

        return ResponseEntity.status(HttpStatus.OK).body(magazinRespository.findById(idMagazin));

    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllMagazin() {
        return ResponseEntity.status(HttpStatus.OK).body(magazinRespository.findAll());
    }

    @PostMapping("/saveProdus/{idMagazin}")
    public void saveProdus(@RequestBody Produs produs, @PathVariable Integer idMagazin) {

        Magazin magazin = magazinRespository.findById(idMagazin).get();
        produs.setMagazin(magazin);
        produsRepository.save(produs);
    }

    @GetMapping("findProdus/{idProdus}")
    public ResponseEntity<Object> getProdusbyId(@PathVariable Integer idProdus) {

        return ResponseEntity.status(HttpStatus.OK).body(produsRepository.findById(idProdus));

    }

    @GetMapping("/magazin/{idMagazin}")
    public ResponseEntity<Object> findAllByIdMagazin(@PathVariable Integer idMagazin) {
        return ResponseEntity.status(HttpStatus.OK).body(produsRepository.findAllByMagazinId(idMagazin));
    }

    @GetMapping("/downland2/{idMagazin}")
    public ResponseEntity<InputStreamResource> downland(@PathVariable Integer idMagazin) throws IOException {

        List<Produs> produsList = produsRepository.findAllByMagazinId(idMagazin);
        File file = new File("fisier.txt");
        FileWriter writer = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.print(produsList);
        printWriter.close();

        InputStreamResource input = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + " fisier.txt  " + "\"").
                body(input);
    }





}
