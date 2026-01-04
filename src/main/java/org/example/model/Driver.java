package org.example.model;

public class Driver {
    private String id;
    private String name;
    private License license;
    private boolean available;

    public Driver(String id, String name, License license) {
        this.id = id;
        this.name = name;
        this.license = license;
        this.available = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public License getLicense() { return license; }
    public boolean isAvailable() { return available; }
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setLicense(License license) {this.license = license;}
    public void setAvailable(boolean available) { this.available = available; }
}