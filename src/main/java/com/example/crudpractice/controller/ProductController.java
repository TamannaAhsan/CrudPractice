package com.example.crudpractice.controller;

import com.example.crudpractice.entity.Product;
import com.example.crudpractice.repository.ProductRepository;
import com.example.crudpractice.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/add/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product product1= productService.createProduct(product);
        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Integer productId ){
        Product product1 = productService.updateProduct(product, productId);
        return new ResponseEntity<>(product1, HttpStatus.OK);

    }
    @GetMapping("{productId}")
    public ResponseEntity<Product> getById (@PathVariable Integer productId){
        Product product = productService.getById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAll (){
        List<Product> allProducts = productService.showAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Map<String,String>> deleteProduct (@PathVariable Integer productId){
        productService.deleteProduct(productId);
        Map<String,String> message = Map.of("message", "Product deleted successfully");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @PostMapping("/showbyName/{productName}")
    public  ResponseEntity <List<Product>> productName (Product product, @PathVariable String productName){
        List<Product> productList= productService.showByName(product, productName);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/showFilter/{productName}/{id}/{localDate}")
    public  ResponseEntity <List<Product>> productName (Product product,
                                                        @PathVariable String productName,
                                                        @PathVariable Integer id,
                                                        @PathVariable String localDate){
        LocalDate localDate1 = LocalDate.parse(localDate);
        List<Product> productList= productService.showFilter(product,productName,id,localDate1);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<List<Product>> productNameWithQueryParam (@RequestParam Map<String,String> allValue){

        return new ResponseEntity<>(productService.showFilter2(allValue), HttpStatus.OK);
    }
    @GetMapping
    public void testFile (MultipartFile file){
        productService.showFileTest(file);
    }
}
