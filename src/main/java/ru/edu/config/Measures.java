package ru.edu.config;

public enum Measures {

    Unit("шт"),
    Weight("кг"),
    Volume("л"),
    Package("упак.");

    private String name;
    private Measures(String name) {
        this.name = name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
