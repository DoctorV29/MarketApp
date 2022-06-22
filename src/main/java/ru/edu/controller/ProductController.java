package ru.edu.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import ru.edu.error.CustomException;
import ru.edu.service.ProductInfo;
import ru.edu.service.ProductService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/market", consumes = MediaType.ALL_VALUE)
public class ProductController {

    private double priceDelta;

    @Autowired
    public void setPriceDelta(@Value("${priceDelta}") double priceDelta) {
        this.priceDelta = priceDelta;
    }

    private final Logger logger = LogManager.getLogger(ProductController.class);
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {

        this.productService = productService;
    }

    private List<String> eiList;
    @Autowired
    public void setMeasures(List<String> eiList){
        this.eiList = eiList;
    }


    @GetMapping
    public ModelAndView getAllProductsView() {
        List<ProductInfo> allProducts = productService.getAll();
        ModelAndView view = new ModelAndView();
        view.setViewName("/allProducts.jsp");
        view.addObject("allProducts", allProducts);
        if(productService.getAll().size() == 0){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/marketEmpty.jsp");
            return modelAndView;
        }
        return view;
    }

    @GetMapping("/service")
    public ModelAndView getAllProductsService() {
        List<ProductInfo> allProducts = productService.getAll();
        ModelAndView view = new ModelAndView();
        view.setViewName("/service.jsp");
        view.addObject("allProducts", allProducts);
        if(productService.getAll().size() == 0){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/marketEmpty.jsp");
            return modelAndView;
        }
        return view;
    }

    @PostMapping("/service")
    public ModelAndView deleteProduct(@RequestParam("id") String itemId) {
        productService.delete(itemId);
        return getAllProductsService();
    }

    @GetMapping("/item")
    public ModelAndView getProduct(@RequestParam("itemId") String itemId) {
        logger.info("Start getProduct with itemId =" + itemId);
        ProductInfo product = productService.getProduct(itemId);
        ModelAndView view = new ModelAndView("/iteminfo.jsp");
        view.addObject("name", product.getName());
        view.addObject("composition", product.getComposition());
        view.addObject("categoryId", productService.getCategories().get(product.getCategoryId()).getName());
        view.addObject("weight", String.format("%.3f",product.getWeight()));
        view.addObject("price", String.format("%.2f", product.getPrice()));
        view.addObject("priceDelta", (int) priceDelta);
        view.addObject("similarProducts", productService.getSimilarProducts(itemId, priceDelta));
        view.addObject("EI",product.getEI());
        logger.info("Completed getProductView with itemId =" + itemId);
        return view;
    }

    @GetMapping("/item/create")
    public ModelAndView getCreateProductView() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/itemCreate.jsp");
        modelAndView.addObject("eiList", eiList);
        modelAndView.addObject("listCategories", productService.getCategories().values());
        return modelAndView;
    }


    @PostMapping(value = "/item/create", consumes = MediaType.ALL_VALUE)
    public ModelAndView createProduct(@ModelAttribute ProductInfo info, BindingResult bindingResult) {

        ProductInfo productInfo = null;
        ModelAndView modelAndView = new ModelAndView();
        List<String> resultChecking = checkFormData(info, bindingResult);
        Element elementImg = null;


        if (resultChecking.size() == 0) {

            try {
                Document doc = Jsoup.connect("https://www.google.com/search?q="+info.getName()+"&sxsrf=APq-WBvOd0DE0jQO_c7ELK-P9jn4WhRyHA:1650106322313&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjjiLuJtZj3AhWTyYsKHZxDBBUQ_AUoAXoECAIQAw&biw=1366&bih=588&dpr=1").get();
                elementImg = doc.select("img.yWs4tf").first();
                String src = elementImg.attr("src");
                Connection.Response response = Jsoup.connect(src).ignoreContentType(true).execute();
                FileOutputStream out = new FileOutputStream("./src/main/webapp/img/" + info.getId() + ".jpg");
                out.write(response.bodyAsBytes());
                out.close();
            } catch (Exception ex) {
                throw new RuntimeException();
            }

            try {
                productInfo = productService.getProduct(info.getId());
            } catch (CustomException ex) {
            }

            if (productInfo == null) {
                productInfo = productService.create(info);
                modelAndView.addObject("result", "Продукт создан");
            } else {
                productInfo = productService.update(info);
                modelAndView.addObject("result", "Продукт обновлен");
            }
        }

        modelAndView.setViewName("/itemCreate.jsp");
        modelAndView.addObject("id", info.getId());
        modelAndView.addObject("name", info.getName());
        modelAndView.addObject("kol", info.getKol());
        modelAndView.addObject("eiList", eiList);
        modelAndView.addObject("ei", info.getEI());
        modelAndView.addObject("composition", info.getComposition());
        modelAndView.addObject("categoryId", info.getCategoryId());
        modelAndView.addObject("weight", info.getWeight());
        modelAndView.addObject("price", info.getPrice());
        modelAndView.addObject("listCategories", productService.getCategories().values());
        modelAndView.addObject("resultChecking", resultChecking);
        modelAndView.addObject("elementImg", elementImg);
        return modelAndView;
    }

    @GetMapping("/item/edit")
    public ModelAndView getEditProductView(@RequestParam("id") String itemId) {

        String filePath = "src/main/webapp/img/" + itemId + ".jpg";

        String imageBase = null;
        try {
            File file = new File(filePath);
            byte[] fileArray = Files.readAllBytes(file.toPath());
            imageBase = new String(Base64.getEncoder().encode(fileArray));
        } catch (Exception ex) {
            throw new RuntimeException();
        }

        ProductInfo product = productService.getProduct(itemId);
        ModelAndView view = new ModelAndView("/itemEdit.jsp");
        view.addObject("listCategories", productService.getCategories().values());
        view.addObject("name", product.getName());
        view.addObject("composition", product.getComposition());
        view.addObject("categoryId", product.getCategoryId());
        view.addObject("weight", product.getWeight());
        view.addObject("price", product.getPrice());
        view.addObject("kol", product.getKol());
        view.addObject("eiList", eiList);
        view.addObject("ei", product.getEI());
        view.addObject("id", product.getId());
        view.addObject("imageBase",imageBase);
        return view;
    }

    @PostMapping(value = "/item/edit", consumes = MediaType.ALL_VALUE)
    public ModelAndView editProduct(@RequestParam("uploadFile") MultipartFile uploadFile, @ModelAttribute ProductInfo info, BindingResult bindingResult) {

        ProductInfo productInfo = null;
        ModelAndView modelAndView = new ModelAndView();
        List<String> resultChecking = checkFormData(info, bindingResult);
        String result = null;
        String imageBase = null;
        byte[] fileArray;

        try {
            if (!uploadFile.isEmpty()) {

                uploadFile.transferTo(Paths.get("src/main/webapp/img/" + info.getId() + ".jpg"));
            }

            String filePath = "src/main/webapp/img/" + info.getId() + ".jpg";
            File file = new File(filePath);
            fileArray = Files.readAllBytes(file.toPath());
            imageBase = new String(Base64.getEncoder().encode(fileArray));

        } catch (Exception ex) {
            throw new RuntimeException();
        }

        if (resultChecking.size() == 0) {

            productInfo = productService.update(info);
            result = "Продукт обновлен";
        }

        modelAndView.addObject("result", result);
        modelAndView.setViewName("/itemEdit.jsp");
        modelAndView.addObject("name", info.getName());
        modelAndView.addObject("composition", info.getComposition());
        modelAndView.addObject("categoryId", info.getCategoryId());
        modelAndView.addObject("weight", info.getWeight());
        modelAndView.addObject("price", info.getPrice());
        modelAndView.addObject("listCategories", productService.getCategories().values());
        modelAndView.addObject("resultChecking", resultChecking);
        modelAndView.addObject("kol", info.getKol());
        modelAndView.addObject("eiList", eiList);
        modelAndView.addObject("ei", info.getEI());
        modelAndView.addObject("resultChecking", resultChecking);
        modelAndView.addObject("imageBase", imageBase);

        CacheControl.noStore();

        return modelAndView;
    }

    @GetMapping("/categories")
    public ModelAndView getCategories() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/allCategories.jsp");
        return modelAndView;
    }

    @GetMapping("/productsByCategory")
    public ModelAndView getProductByCategory(@RequestParam("categoryId") String categoryId) {

        List<ProductInfo> allProducts = productService.getProductsByCategory(categoryId);
        ModelAndView view = new ModelAndView();
        view.setViewName("/allProductsByCategories.jsp");
        view.addObject("allProducts", allProducts);
        if (allProducts.size() == 0) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/marketEmpty.jsp");
            return modelAndView;
        }
        return view;
    }

    private List<String> checkFormData(ProductInfo info, BindingResult bindingResult) {

        List<String> resultChecking = new ArrayList<>();

        final int idLength = 30;
        final int nameLength = 100;
        final int compositionLength = 500;
        final int categoryIdLength = 50;
        final int EILength = 50;

        if (info.getId().length() == 0 || info.getId().length() > idLength) {
            resultChecking.add("ID товара обязательно к заполнению а длина не должна превышать " + idLength + " символов");
        }
        if (info.getName().length() == 0 || info.getName().length() > nameLength) {
            resultChecking.add("Название товара обязательно к заполнению а длина не должна превышать " + nameLength + " символов");
        }
        if (info.getComposition().length() == 0 || info.getComposition().length() > compositionLength) {
            resultChecking.add("Состав товара обязателен к заполнению а длина не должна превышать " + compositionLength + " символов");
        }
        if (info.getCategoryId().length() > categoryIdLength) {
            resultChecking.add("Длина категории товара не должна превышать " + categoryIdLength + " символов");
        }
        if (info.getCategoryId().equals("0")) {
            resultChecking.add("Необходимо выбрать категорию товара");
        }
        if (info.getEI().length() > EILength) {
            resultChecking.add("Длина поля для едениц измерения не должна превышать " + EILength + " символов");
        }
        if (info.getEI().equals("0")) {
            resultChecking.add("Необходимо выбрать еденицу измерения товара");
        }
        if (info.getPrice() < 0) {
            resultChecking.add("Цена должна быть положительным числом");
        }
        if (info.getWeight() < 0) {
            resultChecking.add("Вес должен быть положительным числом");
        }
        if (info.getKol() <= 0) {
            resultChecking.add("Количество должно быть положительным числом");
        }
        if (bindingResult.hasErrors()) {

            FieldError error = bindingResult.getFieldError();
            if (error.getField().equals("price")) {
                resultChecking.add("Цена должна быть числом формата *.*");
            }
            if (error.getField().equals("weight")) {
                resultChecking.add("Вес должен быть числом формата *.*");
            }
            if (error.getField().equals("kol")) {
                resultChecking.add("Количество должно быть целым формата *.*");
            }
        }
        return resultChecking;
    }
}
