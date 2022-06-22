package ru.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.error.CustomException;
import ru.edu.error.CustomException;
import ru.edu.error.Errors;
import ru.edu.service.CategoryInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.edu.service.ProductInfo;
import ru.edu.service.ProductService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductInfo> getAll() {
        List<ProductInfo> allProducts = productService.getAll();
        return allProducts;
    }


    @RequestMapping(value = "/allsim", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductInfo> getSimilarProducts(@RequestParam("itemId") String itemId, @RequestParam("priceDelta") double priceDelta) {
        List<ProductInfo> allProductsSim = productService.getSimilarProducts(itemId, priceDelta);
        return allProductsSim;
    }


    @GetMapping
    public ProductInfo getProduct(@RequestParam("itemId") String itemId) {
            ProductInfo product = productService.getProduct(itemId);
            return product;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductInfo create(@RequestBody ProductInfo info) {
        try {
            return productService.create(info);
        } catch (Exception ex){
            throw new CustomException("Ошибка в создание продукта " + info +  " возможно, такой продукт уже создан", Errors.DB_ERROR);
        }
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductInfo update(@RequestBody ProductInfo info) {
        try {
            return productService.update(info);
        }catch (Exception ex){
            throw new CustomException("Ошибка в обновление продукта " + info.getId() +  " такого id не существует", Errors.DB_ERROR);
        }
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@RequestParam("id") String itemId) {
        try {
            return new ResponseEntity(productService.delete(itemId), HttpStatus.OK);
        } catch (Exception ex) {

            return new ResponseEntity(ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  getCategories() {
        try {
            Map<String, CategoryInfo> listCategories = productService.getCategories();
            return new ResponseEntity(listCategories, HttpStatus.OK);
        } catch (Exception ex) {

            return new ResponseEntity(ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
