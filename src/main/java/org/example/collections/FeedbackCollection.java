package org.example.collections;

import org.example.util.XMLStorageUtil;
import org.w3c.dom.*;

public class FeedbackCollection {
    private static final String FILE = "feedback.xml";

    public void save(String orderId, int rating, String comment) {
        try {
            Document doc = XMLStorageUtil.loadDocument(FILE);
            if (doc == null) {
                doc = XMLStorageUtil.createDocument();
                Element root = doc.createElement("feedbacks");
                doc.appendChild(root);
            }

            Element root = doc.getDocumentElement();
            NodeList list = doc.getElementsByTagName("feedback");
            for (int i = 0; i < list.getLength(); i++) {
                Element fb = (Element) list.item(i);
                String existingOrderId =
                        XMLStorageUtil.getElementText(fb, "orderId");

                if (orderId.equals(existingOrderId)) {
                    root.removeChild(fb);
                    break;
                }
            }

            Element fb = doc.createElement("feedback");
            XMLStorageUtil.appendElement(doc, fb, "orderId", orderId);
            XMLStorageUtil.appendElement(doc, fb, "rating", String.valueOf(rating));
            XMLStorageUtil.appendElement(doc, fb, "comment", comment);

            root.appendChild(fb);
            XMLStorageUtil.saveDocument(doc, FILE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
