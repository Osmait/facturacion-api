package com.example.facturationproject.infrastructure.controller;

import com.example.facturationproject.application.commant.Sale.SaleCreator;
import com.example.facturationproject.application.commant.Sale.SaleDeleted;
import com.example.facturationproject.application.query.sale.SaleFind;
import com.example.facturationproject.infrastructure.Dto.sale.SaleRequest;
import com.example.facturationproject.infrastructure.Dto.sale.SaleResponse;
import com.example.facturationproject.infrastructure.controller.exceptionControler.exceptions.BadRequestException;
import com.example.facturationproject.infrastructure.utils.ValidateErrors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleFind saleFind;
    private final SaleCreator  saleCreator;
    private final SaleDeleted saleDeleted;
    private final ValidateErrors validateErrors;


    @PostMapping
    public ResponseEntity<String> createSAle(@Validated @RequestBody SaleRequest saleRequest, BindingResult result){
        if(result.hasErrors()) {

            throw new BadRequestException(validateErrors.ValidFields(result));
        }
        saleCreator.create(saleRequest);
        return ResponseEntity.ok("sale created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SaleResponse>> getAllSales(@PathVariable Long id) {
        List<SaleResponse> saleResponses = saleFind.findAll(id);
        return new ResponseEntity<>( saleResponses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id){
        saleDeleted.deleted(id);
        return ResponseEntity.ok("sale deleted");
    }





}
