package com.example.crudpractice.service;

import com.example.crudpractice.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProductService {

    Product createProduct (Product product);

    Product updateProduct (Product product, Integer productId);

    void deleteProduct (Integer productId);

    Product getById (Integer productId);

    List<Product> showAllProducts ();

    List<Product> showByName (Product product, String productName);

    List<Product> showFilter (Product product, String productName, Integer id, LocalDate date);

    List<Product> showFilter2 (Map<String, String> allParam);

    void showFileTest (MultipartFile file);
}
