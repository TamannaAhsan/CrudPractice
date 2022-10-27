package com.example.crudpractice.service.impl;

import com.example.crudpractice.entity.Attachment;
import com.example.crudpractice.entity.Product;
import com.example.crudpractice.repository.AttachRepository;
import com.example.crudpractice.repository.ProductRepository;
import com.example.crudpractice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final AttachRepository attachRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Integer productId) {
        Product product1 = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));

        product1.setName(product.getName());
        product1.setPurchase_date(product.getPurchase_date());
        product1.setDetails(product.getDetails());

        return productRepository.save(product1);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product1 = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        productRepository.delete(product1);

    }

    @Override
    public Product getById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        return product;
    }

    @Override
    public List<Product> showAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.isEmpty())
            throw new RuntimeException("Product list is empty");
        return allProducts;
    }

    @Override
    public List<Product> showByName(Product product, String productName) {

        List<Product> products = productRepository.findAllByName(productName);
        if (products.isEmpty())
            throw new RuntimeException("Product list is empty");

        if(product.getId()!=null) {
            products = products.stream()
                    .filter(element -> element.getName().equals(product.getName()))
                    .collect(Collectors.toList());
        }
            return products;
    }

    @Override
    public List<Product> showFilter(Product product, String productName, Integer id, LocalDate date) {

        List<Product> allProducts = productRepository.findAllByName(productName);
        if (allProducts.isEmpty())
            throw new RuntimeException("Product list is empty");

        if (product != null) {
            if (product.getName() != null) {
                allProducts = allProducts.stream()
                        .filter(element -> element.getName().equals(product.getName()))
                        .collect(Collectors.toList());
            }
            if (product.getId() != null) {
                allProducts = allProducts.stream()
                        .filter(element -> element.getId().equals(product.getId()))
                        .collect(Collectors.toList());
            }
            if (product.getPurchase_date() != null) {
                allProducts = allProducts.stream()
                        .filter(element -> element.getPurchase_date().equals(product.getPurchase_date()))
                        .collect(Collectors.toList());
            }
        }
            return allProducts;
        }

    @Override
    public List<Product> showFilter2(Map<String, String> allParam) {
        List<Product> allProduct = productRepository.findAll();
        if(allParam.containsKey("user_id"))
            allProduct = allProduct.stream()
                    .filter(element -> element.getId().equals(Integer.parseInt(allParam.get("user_id"))))
                    .collect(Collectors.toList());

        if(allParam.containsKey("date"))
            allProduct = allProduct.stream()
                    .filter(element -> element.getPurchase_date().equals(LocalDate.parse(allParam.get("date"))))
                    .collect(Collectors.toList());

        if(allParam.containsKey("name"))
            allProduct = allProduct.stream()
                    .filter(element -> element.getName().equals((allParam.get("name"))))
                    .collect(Collectors.toList());
        return allProduct;
    }

    @Override
    public void showFileTest(MultipartFile inputFile) {

        String directoryName = "C:\\Project\\Work\\resource\\";
        String fileName = LocalDate.now()+"_"+
                new Random().nextInt(1000000000) +"_"+
                inputFile.getOriginalFilename();

        String totalPath = directoryName+fileName;

        File path = new File(totalPath);
        try(FileOutputStream outputStream = new FileOutputStream(path)) {

            outputStream.write(inputFile.getBytes());
            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setDirectoryName(directoryName);
            attachRepository.save(attachment);

        }catch (IOException ex){
            log.info(ex.getMessage());
        }
       /* FileInputStream inputStream = new FileInputStream(path);
        System.out.println(new String(inputStream.readAllBytes()));
        inputStream.close();*/
    }
}
