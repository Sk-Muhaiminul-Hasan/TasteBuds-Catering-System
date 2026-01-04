package org.example.util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLStorageUtil {
    private static final String DATA_DIR = "src/main/resources/data/";

    static {
        new File(DATA_DIR).mkdirs();
    }

    public static Document createDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    public static Document loadDocument(String filename) throws Exception {
        File file = new File(DATA_DIR + filename);
        if (!file.exists()) {
            return null;
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(file);
    }

    public static void saveDocument(Document doc, String filename) throws Exception {
        // Remove all existing whitespace-only text nodes (cleanup)
        removeWhitespaceNodes(doc.getDocumentElement());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(DATA_DIR + filename));
        transformer.transform(source, result);
    }

    // Helper: Remove unnecessary whitespace text nodes
    private static void removeWhitespaceNodes(Element parent) {
        NodeList children = parent.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                String text = child.getTextContent();
                if (text != null && text.trim().isEmpty()) {
                    parent.removeChild(child);
                }
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeWhitespaceNodes((Element) child);
            }
        }
    }

    public static void appendElement(Document doc, Element parent, String name, String value) {
        Element elem = doc.createElement(name);
        elem.setTextContent(value != null ? value : "");
        parent.appendChild(elem);
    }

    public static String getElementText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}