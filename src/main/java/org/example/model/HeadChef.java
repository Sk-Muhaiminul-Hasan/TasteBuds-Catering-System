package org.example.model;

import java.util.List;

public class HeadChef extends Chef {
    private List<Chef> subordinateChefs;

    public HeadChef(String chefId, String name) {
        super(chefId, name);
    }

    public void assignChef(Order order, List<Chef> availableChefs) {
        Chef assignedChef = availableChefs.stream()
                .filter(Chef::isAvailable)
                .findFirst()
                .orElse(null);

        if (assignedChef != null) {
            order.setAssignedChefId(assignedChef.getChefId());
            assignedChef.setAvailable(false);
            double prepTime = order.getReadyAt();

            System.out.println("Order " + order.getOrderId() + " assigned to Chef: " + assignedChef.getName());
            System.out.println("Estimated preparation time: " + prepTime + " minutes");
        }
    }


    public void determineOrderPriority(Order order, Customer customer) {
        if (customer != null && customer.getOrdersThisMonth() >= 5) {
            order.setPriority(true);
            System.out.println("Order " + order.getOrderId() + " marked as PRIORITY (loyal customer)");
        } else {
            order.setPriority(false);
            System.out.println("Order " + order.getOrderId() + " marked as NORMAL");
        }
    }

    public List<Chef> getSubordinateChefs() {
        return subordinateChefs;
    }

    public void setSubordinateChefs(List<Chef> subordinateChefs) {
        this.subordinateChefs = subordinateChefs;
    }
}