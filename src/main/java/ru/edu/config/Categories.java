package ru.edu.config;

public enum Categories {

    pastry("кондитерские изделия"),
    milk("молоко"),
    chai("чай"),
    coffee("кофе"),
    meatCanned("мясные консервы"),
    pasta("макаронные изделия"),
    rice("рис"),
    sunflowerOil("масло подсолнечное");


    private String name;
    private Categories(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
