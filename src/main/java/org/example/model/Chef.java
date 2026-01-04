package org.example.model;


public class Chef {
    private String chefId;
    private String name;
    private boolean available;


    public Chef(String chefId, String name) {
        this.chefId = chefId;
        this.name = name;
        this.available = true;
    }

    // Getters and Setters
    public String getChefId() { return chefId; }
    public void setChefId(String chefId) { this.chefId = chefId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }



    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

}