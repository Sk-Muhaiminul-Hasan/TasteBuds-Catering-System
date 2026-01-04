package org.example.collections;

import org.example.model.Customer;
import org.example.util.XMLStorageUtil;
import org.w3c.dom.*;

public class CustomerCollection {
    private static final String FILE = "customers.xml";

    public Customer findById(String id) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return null;

            NodeList customers = doc.getElementsByTagName("customer");
            for (int i = 0; i < customers.getLength(); i++) {
                Element custElem = (Element) customers.item(i);
                if (XMLStorageUtil.getElementText(custElem, "id").equals(id)) {
                    String name = XMLStorageUtil.getElementText(custElem, "name");
                    String email = XMLStorageUtil.getElementText(custElem, "email");
                    boolean registered = Boolean.parseBoolean(XMLStorageUtil.getElementText(custElem, "registered"));
                    int orders = Integer.parseInt(XMLStorageUtil.getElementText(custElem, "ordersThisMonth"));
                    Customer c = new Customer(id, name, email, registered);
                    c.setOrdersThisMonth(orders);
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Customer customer) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) {
                doc = XMLStorageUtil.createDocument();
                Element root = doc.createElement("customers");
                doc.appendChild(root);
            }
            Element root = doc.getDocumentElement();

            NodeList list = doc.getElementsByTagName("customer");
            for (int i = 0; i < list.getLength(); i++) {
                Element elem = (Element) list.item(i);
                if (XMLStorageUtil.getElementText(elem, "id").equals(customer.getId())) {
                    root.removeChild(elem);
                    break;
                }
            }

            Element custElem = doc.createElement("customer");
            XMLStorageUtil.appendElement(doc, custElem, "id", customer.getId());
            XMLStorageUtil.appendElement(doc, custElem, "name", customer.getName());
            XMLStorageUtil.appendElement(doc, custElem, "email", customer.getEmail());
            XMLStorageUtil.appendElement(doc, custElem, "registered", String.valueOf(customer.isRegistered()));
            XMLStorageUtil.appendElement(doc, custElem, "ordersThisMonth", String.valueOf(customer.getOrdersThisMonth()));
            root.appendChild(custElem);

            XMLStorageUtil.saveDocument(doc, FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}