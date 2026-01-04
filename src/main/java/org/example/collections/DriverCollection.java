package org.example.collections;

import org.example.model.Driver;
import org.example.model.License;
import org.example.util.XMLStorageUtil;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class DriverCollection {
    private static final String FILE = "drivers.xml";

    public Driver findById(String id) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return null;

            NodeList drivers = doc.getElementsByTagName("driver");
            for (int i = 0; i < drivers.getLength(); i++) {
                Element d = (Element) drivers.item(i);
                if (XMLStorageUtil.getElementText(d, "id").equals(id)) {
                    String name = XMLStorageUtil.getElementText(d, "name");
                    String licenseNum = XMLStorageUtil.getElementText(d, "licenseNumber");
                    String expiry = XMLStorageUtil.getElementText(d, "licenseExpiry");
                    boolean available = Boolean.parseBoolean(XMLStorageUtil.getElementText(d, "available"));

                    License license = new License(licenseNum, expiry);
                    Driver driver = new Driver(id, name, license);
                    driver.setAvailable(available);
                    return driver;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Driver findByLicenseNumber(String licenseNumber) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return null;

            NodeList drivers = doc.getElementsByTagName("driver");
            for (int i = 0; i < drivers.getLength(); i++) {
                Element d = (Element) drivers.item(i);
                String id = XMLStorageUtil.getElementText(d, "id");
                String name = XMLStorageUtil.getElementText(d, "name");
                String licenseNum = XMLStorageUtil.getElementText(d, "licenseNumber");
                String expiry = XMLStorageUtil.getElementText(d, "licenseExpiry");
                boolean available = Boolean.parseBoolean(XMLStorageUtil.getElementText(d, "available"));

                if (licenseNum.equals(licenseNumber)) {
                    License license = new License(licenseNum, expiry);
                    Driver driver = new Driver(id, name, license);
                    driver.setAvailable(available);
                    return driver;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Driver> findAll() {
        List<Driver> driverList = new ArrayList<>();
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) return driverList;

            NodeList drivers = doc.getElementsByTagName("driver");
            for (int i = 0; i < drivers.getLength(); i++) {
                Element d = (Element) drivers.item(i);
                String id = XMLStorageUtil.getElementText(d, "id");
                String name = XMLStorageUtil.getElementText(d, "name");
                String licenseNum = XMLStorageUtil.getElementText(d, "licenseNumber");
                String expiry = XMLStorageUtil.getElementText(d, "licenseExpiry");
                boolean available = Boolean.parseBoolean(XMLStorageUtil.getElementText(d, "available"));

                License license = new License(licenseNum, expiry);
                Driver driver = new Driver(id, name, license);
                driver.setAvailable(available);
                driverList.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driverList;
    }

    public void save(Driver driver) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) {
                doc = XMLStorageUtil.createDocument();
                Element root = doc.createElement("drivers");
                doc.appendChild(root);
            }
            Element root = doc.getDocumentElement();

            NodeList list = doc.getElementsByTagName("driver");
            for (int i = 0; i < list.getLength(); i++) {
                Element elem = (Element) list.item(i);
                if (XMLStorageUtil.getElementText(elem, "id").equals(driver.getId())) {
                    root.removeChild(elem);
                    break;
                }
            }

            Element driverElem = doc.createElement("driver");
            appendDriverFields(doc, driverElem, driver);
            root.appendChild(driverElem);

            XMLStorageUtil.saveDocument(doc, FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void appendDriverFields(Document doc, Element parent, Driver driver) {
        XMLStorageUtil.appendElement(doc, parent, "id", driver.getId());
        XMLStorageUtil.appendElement(doc, parent, "name", driver.getName());
        XMLStorageUtil.appendElement(doc, parent, "available", String.valueOf(driver.isAvailable()));

        License license = driver.getLicense();
        if (license != null) {
            XMLStorageUtil.appendElement(doc, parent, "licenseNumber", license.getNumber());
            XMLStorageUtil.appendElement(doc, parent, "licenseExpiry", license.getExpiryDate());
        } else {
            XMLStorageUtil.appendElement(doc, parent, "licenseNumber", "");
            XMLStorageUtil.appendElement(doc, parent, "licenseExpiry", "");
        }
    }
}