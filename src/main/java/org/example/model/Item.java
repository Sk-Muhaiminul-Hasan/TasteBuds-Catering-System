package org.example.model;

public class Item {
    private String name;
    private double price;
    private double timeTakenToPrepare;

    public Item(String name, double price, double timeTakenToPrepare) {
        this.name = name;
        this.price = price;
        this.timeTakenToPrepare = timeTakenToPrepare;
    }


    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getTimeTakenToPrepare() {return timeTakenToPrepare;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTimeTakenToPrepare(double timeTakenToPrepare) {
        this.timeTakenToPrepare = timeTakenToPrepare;
    }
}
