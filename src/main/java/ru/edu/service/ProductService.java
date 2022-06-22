package ru.edu.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.edu.config.Categories;
import ru.edu.config.Measures;
import ru.edu.dao.ProductRepository;
import ru.edu.error.CustomException;
import ru.edu.error.Errors;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductService {

    private final Logger logger = LogManager.getLogger(ProductInfo.class);
    private ProductRepository repository;


    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductInfo> getAll() {
        logger.info("getAll started");
        List<ProductInfo> list = this.repository.getAll();
        logger.info("getAll completed");
        return list;
    }


    /**
     * Get products of current category with similar prices.
     *
     * @throws ru.edu.error.CustomException with code PRODUCT_NOT_FOUND if product doesn't exist
     * @throws ru.edu.error.CustomException with code ARGUMENT_ERROR if priceDelta is negative
     */
    public List<ProductInfo> getSimilarProducts(String itemId, double priceDelta) {
        logger.info("getSimilarProduct started");
        try {
            if (priceDelta < 0) {
                logger.error("Failed to getSimilarProduct - " + "ARGUMENT_ERROR FOR " + itemId );
                throw new CustomException("Failed to getSimilarProduct - " + "ARGUMENT_ERROR FOR "+ itemId , Errors.ARGUMENT_ERROR);

            }
        } catch (CustomException ex) {
            return null;
        }
        try {
            if(getProduct(itemId).equals(null)){
                logger.error("Failed to getSimilarProduct - " + "PRODUCT_NOT_FOUND FOR " + itemId );
                throw new CustomException("Failed to getSimilarProduct - " + "PRODUCT_NOT_FOUND FOR " + itemId, Errors.PRODUCT_NOT_FOUND);
            }
        } catch (CustomException ex) {
            return null;
        }
        double Pricemin = getProduct(itemId).getPrice() - priceDelta;
        Pricemin = Pricemin > 0 ? Pricemin : 0;
        logger.info("min = " + Pricemin );
        double Pricemax = getProduct(itemId).getPrice() + priceDelta;
        logger.info("max = " + Pricemax );
        List<ProductInfo> similarProducts = getAll().stream()
                .filter(ProductInfo -> (Math.abs(ProductInfo.getPrice()-getProduct(itemId).getPrice())) < priceDelta & ProductInfo.getCategoryId().equals(getProduct(itemId).getCategoryId()) & (!ProductInfo.getId().equals(itemId)))
                .limit(3)
                .collect(Collectors.toList());
        logger.info(".getSimilarProduct completed");
        return similarProducts;
    }

    /**
     * Get product by id. Returns null if not found.
     *
     * @param itemId - item id
     * @throws ru.edu.error.CustomException with code PRODUCT_NOT_FOUND if product doesn't exist
     */
    public ProductInfo getProduct(String itemId) {
         try {
             logger.info(".getProduct started with id =" + itemId);
            ProductInfo item = repository.getProduct(itemId);
            if (item == null) {
                logger.error("Error to .getProduct with id =" + itemId + "." + Errors.PRODUCT_NOT_FOUND);
                throw new CustomException(" Failed to .getProduct. Item id=" + itemId + " not found!", Errors.PRODUCT_NOT_FOUND);
            }
             logger.info(".getProduct completed with id =" + itemId);
            return item;
        } catch (CustomException ex) {
//            logger.error("Failed to .getProduct error={}", ex.toString(), ex);
            throw ex;
        }
    }

    /**
     * Create new product.
     *
     * @throws ru.edu.error.CustomException with code PRODUCT_ALREADY_EXISTS if product with current id already exists
     * @throws ru.edu.error.CustomException with code ARGUMENT_ERROR if info is incorrect
     */
    public ProductInfo create(ProductInfo info) {
        logger.info("ProductService.create it's start");
        if (repository.getProduct(info.getId()) != null) {
            logger.info("ProductService.getProduct(info.getId()) Error = " + info.getId() + " уже существует");
            throw new CustomException(info.getId() + "exist", Errors.PRODUCT_NOT_FOUND);
        }
        if (!preobrazId(info.getId())) {
            logger.info("ProductService.preobrazId(info.getId()) Error = " + info.getId() + " неверный формат");
            throw new CustomException("Неверный формат " + info.getId(), Errors.SERVICE_ERROR);
        }
        if (info.getName().isEmpty()) {
            logger.info("ProductService.info.getName() Error = " + info.getName() + " пустое поле");
            throw new CustomException("Пустое поле " + info.getName(), Errors.SERVICE_ERROR);
        }
        if (info.getComposition().isEmpty()) {
            logger.info("ProductService.info.getComposition() Error = " + info.getComposition() + " пустое поле");
            throw new CustomException("Пустое поле " + info.getComposition(), Errors.SERVICE_ERROR);
        }
        if (!preobrazPrice(info)){
            logger.info("ProductService.preobrazPrice(info) Error = " + info.getPrice() + " неверный формат цены");
            throw new CustomException("Неверный формат цены " + info.getPrice(), Errors.SERVICE_ERROR);
        }
        if (!preobrazWeight(info)){
            logger.info("ProductService.preobrazWeight(info) Error = " + info.getWeight() + " неверный формат веса");
            throw new CustomException("Неверный формат веса " + info.getWeight(), Errors.SERVICE_ERROR);
        }
        logger.info("ProductService.create completed successfully");
        return repository.create(info);
    }

    /**
     * Update existing product. Don't change id
     *
     * @throws ru.edu.error.CustomException with code PRODUCT_NOT_FOUND if product doesn't exist
     * @throws ru.edu.error.CustomException with code ARGUMENT_ERROR if info is incorrect
     */
    public ProductInfo update(ProductInfo info) {
        logger.info("ProductService.update it's start");
        if (repository.getProduct(info.getId()) == null) {
            logger.info("ProductService.getProduct(info.getId()) Error = " + info.getId() + " не существует");
            throw new CustomException(info.getId() + "exist", Errors.PRODUCT_NOT_FOUND);
        }
        if (!preobrazId(info.getId())) {
            logger.info("ProductService.preobrazId(info.getId()) Error = " + info.getId() + " неверный формат");
            throw new CustomException("Неверный формат " + info.getId(), Errors.SERVICE_ERROR);
        }
        if (info.getName().isEmpty()) {
            logger.info("ProductService.info.getName() Error = " + info.getName() + " пустое поле");
            throw new CustomException("Пустое поле " + info.getName(), Errors.SERVICE_ERROR);
        }
        if (info.getComposition().isEmpty()) {
            logger.info("ProductService.info.getComposition() Error = " + info.getComposition() + " пустое поле");
            throw new CustomException("Пустое поле " + info.getComposition(), Errors.SERVICE_ERROR);
        }
        if (!preobrazPrice(info)){
            logger.info("ProductService.preobrazPrice(info) Error = " + info.getPrice() + " неверный формат цены");
            throw new CustomException("Неверный формат цены " + info.getPrice(), Errors.SERVICE_ERROR);
        }
        if (!preobrazWeight(info)){
            logger.info("ProductService.preobrazWeight(info) Error = " + info.getWeight() + " неверный формат веса");
            throw new CustomException("Неверный формат веса " + info.getWeight(), Errors.SERVICE_ERROR);
        }
        logger.info("ProductService.update completed successfully");
        return repository.update(info);
    }

    /**
     * Delete product by id
     *
     * @throws ru.edu.error.CustomException with code PRODUCT_NOT_FOUND if product doesn't exist
     */
    public ProductInfo delete(String itemId) {

        try {
            logger.info(".delete started");
            ProductInfo productInfo;
            productInfo = repository.delete(itemId);
            if (productInfo == null) {
                logger.error(".delete Error! Product with id=" + itemId + " not found. " + Errors.PRODUCT_NOT_FOUND);
                throw new CustomException("Error in service.ProductServer.delete itemId="
                        + itemId,Errors.PRODUCT_NOT_FOUND);
            }
            logger.info(".delete completed");
            return productInfo;
        } catch (Exception ex) {
            logger.error("Failed to .delete ", ex.toString(), ex);
            throw ex;
        }
    }

    /**
     * Categories. Return constants.
     *
     */
    public Map<String, CategoryInfo> getCategories() {

        try {
            Map<String, CategoryInfo> categories = new HashMap<>();

            for (Categories item : Categories.values()) {
                categories.put(item.toString(), new CategoryInfo(item.toString(), item.getName()));
            }
            return categories;
        } catch (Exception ex) {
            throw new CustomException("Error in service.ProductServer.getCategories " +
                    "Failed to create a map", ex, Errors.SERVICE_ERROR);
        }
    }

    public List<ProductInfo> getProductsByCategory(String categoryId) {

        try {
            List<ProductInfo> allProductList = getAll();
            List<ProductInfo> filteredProductList = allProductList.stream()
                    .filter(p -> p.getCategoryId().equals(categoryId))
                    .collect(Collectors.toList());
            return filteredProductList;
        } catch (Exception ex) {
            throw new CustomException("Error in service.ProductServer.getProductsByCategory " +
                    "Failed to create list with ProductInfo ", ex, Errors.SERVICE_ERROR);
        }
    }

    @Bean
    public List<String> getMeasures(){
        try {
            List<String> measuresList = new ArrayList<>();
            for (Measures measure : Measures.values()) {
                measuresList.add(measure.getName());
            }
            return measuresList;
        } catch (Exception ex) {
            throw new CustomException("Error in service.ProductServer.getMeasures " +
                    "Failed to create list with Measures ", ex, Errors.SERVICE_ERROR);
        }
    }

    //подумать о добавлении:
    @PostConstruct
    public void init() {

    }
    //метод для проверки латинице
    private boolean preobrazId(String string) {
        String output = string.replaceAll("[^\\da-zA-Z]", "");
        if (string.equalsIgnoreCase(output)) {
            return true;
        }
        return false;
    }
    //метод для проверки цены на наличие недопустимого формата
    private boolean preobrazPrice(ProductInfo info){
        String strPrice = String.valueOf(info.getPrice());
        String output = strPrice.replaceAll("[^\\d.]", "");
        if (output.equalsIgnoreCase(output)) {
            return true;
        }
        return false;
    }
    //метод для проверки веса товара на наличие недопустимого формата
    private boolean preobrazWeight(ProductInfo info){
        String strPrice = String.valueOf(info.getWeight());
        String output = strPrice.replaceAll("[^\\d.]", "");
        if (output.equalsIgnoreCase(output)) {
            return true;
        }
        return false;
    }
}
