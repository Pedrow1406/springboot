package com.pedrowsite.springboot.controllers;

import com.pedrowsite.springboot.dtos.ProductPatchDto;
import com.pedrowsite.springboot.dtos.ProductPostDto;
import com.pedrowsite.springboot.models.ProductModel;
import com.pedrowsite.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") Long id){
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(product.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
    }

    @PostMapping
    public ResponseEntity<ProductModel> newProduct(@RequestBody @Valid ProductPostDto postDto){
        ProductModel product = new ProductModel();
        BeanUtils.copyProperties(postDto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePartialProduct(@PathVariable(value="id") Long id,
                                                       @RequestBody(required = false) ProductPatchDto patchDto){
        System.out.println(patchDto);
        ProductModel product = new ProductModel();
        Optional<ProductModel> productFoundByID = productRepository.findById(id);
        if (productFoundByID.isPresent()){
            BeanUtils.copyProperties(productFoundByID.get(), product);
            if (patchDto != null){
                if (patchDto.name() != null) {
                    product.setName(patchDto.name());
                }
                if (patchDto.price() != null){
                    product.setPrice(patchDto.price());
                }
            }
            System.out.println(product);
            return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not Found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("id") Long id){
        Optional<ProductModel> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()){
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not Found");
    }
}
