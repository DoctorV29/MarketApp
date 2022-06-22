package ru.edu.service;

public class ProductInfo {

    private String id;
    private String name;
    private String composition;
    private double price;
    private String categoryId;
    private double weight;
    private String EI;
    private int kol;


    public ProductInfo(){
    }
    public ProductInfo(String id, String name, String composition, double price, String categoryId, double weight, String EI, int kol) {
        this.id = id;
        this.name = name;
        this.composition = composition;
        this.price = price;
        this.categoryId = categoryId;
        this.weight = weight;
        this.EI = EI;
        this.kol = kol;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getEI() {
        return EI;
    }

    public void setEI(String EI) {
        this.EI = EI;
    }

    public int getKol() {
        return kol;
    }

    public void setKol(int kol) {
        this.kol = kol;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", composition='" + composition + '\'' +
                ", price=" + price +
                ", categoryId='" + categoryId + '\'' +
                ", weight=" + weight +
                ", EI='" + EI + '\'' +
                ", kol='" + kol + '\'' +
                '}';
    }
}
