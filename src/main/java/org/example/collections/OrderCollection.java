package org.example.collections;

import org.example.model.Order;
import org.example.model.Item;
import org.example.util.XMLStorageUtil;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class OrderCollection {
    private static final String FILE = "orders.xml";

    public Order findById(String orderId) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return null;

            NodeList orders = doc.getElementsByTagName("order");
            for (int i = 0; i < orders.getLength(); i++) {
                Element elem = (Element) orders.item(i);
                if (XMLStorageUtil.getElementText(elem, "orderId").equals(orderId)) {
                    return parseOrderFromElement(elem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> findAll() {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return List.of();

            List<Order> orders = new ArrayList<>();
            NodeList list = doc.getElementsByTagName("order");
            for (int i = 0; i < list.getLength(); i++) {
                Element elem = (Element) list.item(i);
                orders.add(parseOrderFromElement(elem));
            }
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void save(Order order) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) {
                doc = XMLStorageUtil.createDocument();
                Element root = doc.createElement("orders");
                doc.appendChild(root);
            }
            Element root = doc.getDocumentElement();

            NodeList list = doc.getElementsByTagName("order");
            for (int i = 0; i < list.getLength(); i++) {
                Element elem = (Element) list.item(i);
                if (XMLStorageUtil.getElementText(elem, "orderId").equals(order.getOrderId())) {
                    root.removeChild(elem);
                    break;
                }
            }

            Element orderElem = doc.createElement("order");
            appendOrderFields(doc, orderElem, order);
            root.appendChild(orderElem);

            XMLStorageUtil.saveDocument(doc, FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Order parseOrderFromElement(Element elem) {
        String orderId = XMLStorageUtil.getElementText(elem, "orderId");
        String customerId = XMLStorageUtil.getElementText(elem, "customerId");
        int queueNumber = Integer.parseInt(XMLStorageUtil.getElementText(elem, "queueNumber"));
        boolean priority = Boolean.parseBoolean(XMLStorageUtil.getElementText(elem, "priority"));
        Order.OrderStatus status = Order.OrderStatus.valueOf(XMLStorageUtil.getElementText(elem, "status"));

        String readyAtStr = XMLStorageUtil.getElementText(elem, "readyAt");
        double readyAt = 0.0;
        if (!readyAtStr.isEmpty() && !readyAtStr.equals("null")) {
            try {
                readyAt = Double.parseDouble(readyAtStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid readyAt value: " + readyAtStr);
            }
        }

        List<Item> items = parseItemsFromElement(elem);

        Order order = new Order(orderId, customerId, items, queueNumber);
        order.setPriority(priority);
        order.setStatus(status);

        if (readyAt > 0.0) {
            order.setReadyAt(readyAt);
        }

        order.setAssignedChefId(XMLStorageUtil.getElementText(elem, "assignedChefId"));
        order.setAssignedDriverId(XMLStorageUtil.getElementText(elem, "assignedDriverId"));

        return order;
    }

    private List<Item> parseItemsFromElement(Element elem) {
        List<Item> items = new ArrayList<>();
        try {
            Element itemsElem = (Element) elem.getElementsByTagName("items").item(0);
            if (itemsElem != null) {
                NodeList itemNodes = itemsElem.getElementsByTagName("item");
                for (int i = 0; i < itemNodes.getLength(); i++) {
                    Element itemElem = (Element) itemNodes.item(i);
                    String name = XMLStorageUtil.getElementText(itemElem, "name");
                    double price = Double.parseDouble(XMLStorageUtil.getElementText(itemElem, "price"));
                    double timeToPrepare = Double.parseDouble(XMLStorageUtil.getElementText(itemElem, "timeToPrepare"));
                    items.add(new Item(name, price, timeToPrepare));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing items: " + e.getMessage());
        }
        return items;
    }

    private void appendOrderFields(Document doc, Element parent, Order order) {
        XMLStorageUtil.appendElement(doc, parent, "orderId", order.getOrderId());
        XMLStorageUtil.appendElement(doc, parent, "customerId", order.getCustomerId());
        XMLStorageUtil.appendElement(doc, parent, "queueNumber", String.valueOf(order.getQueueNumber()));
        XMLStorageUtil.appendElement(doc, parent, "priority", String.valueOf(order.isPriority()));
        XMLStorageUtil.appendElement(doc, parent, "status", order.getStatus().name());


        String readyAt = order.getReadyAt() != 0 ? String.valueOf(order.getReadyAt()) : "";
        XMLStorageUtil.appendElement(doc, parent, "readyAt", readyAt);


        XMLStorageUtil.appendElement(doc, parent, "assignedChefId", order.getAssignedChefId());
        XMLStorageUtil.appendElement(doc, parent, "assignedDriverId", order.getAssignedDriverId());

        appendItemsToElement(doc, parent, order.getItems());
    }

    private void appendItemsToElement(Document doc, Element parent, List<Item> items) {
        Element itemsElem = doc.createElement("items");
        parent.appendChild(itemsElem);

        for (Item item : items) {
            Element itemElem = doc.createElement("item");
            itemsElem.appendChild(itemElem);

            XMLStorageUtil.appendElement(doc, itemElem, "name", item.getName());
            XMLStorageUtil.appendElement(doc, itemElem, "price", String.valueOf(item.getPrice()));
            XMLStorageUtil.appendElement(doc, itemElem, "timeToPrepare", String.valueOf(item.getTimeTakenToPrepare()));
        }
    }
}