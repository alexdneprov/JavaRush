package com.javarush.task.task33.task3309;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment)  {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = db.newDocument();

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(obj.getClass());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Marshaller marshaller = null;
        try {
            marshaller = context.createMarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            e.printStackTrace();
        }
        try {
            marshaller.marshal(obj,document);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        document.normalizeDocument();

        NodeList nodeList = document.getElementsByTagName(tagName);

        for(int i=0; i<nodeList.getLength(); i++) {
            //Node currentNode = nodeList.item(i);
            nodeList.item(i).getParentNode().insertBefore(document.createComment(comment),nodeList.item(i));
        }

        DOMSource source = new DOMSource(document);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        try {
            transformer.transform(source,result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static void main(String[] args) {
        System.out.println(Solution.toXmlWithComment(new TestClass(), "numbers", "it's a comment"));
    }




    @XmlRootElement
    public static class TestClass {

        @XmlElement (name = "name")
        public String name = "Some text without forbidden symbols";

        @XmlElement (name = "numbers")
        public int[] numbers = new int[]{1,2,3,4,5,6,7,8,9,10};

        @XmlElement
        public String[] strings = new String[]{"string1" , "string2", "string3"};

        @XmlElement (name = "numbers")
        public int data = 5435;

        @XmlElement (name = "numbers")
        public String[] b = new String[]{"</b>"};

        public TestClass () {

        }

    }


    @XmlRootElement(name = "first")
    public static class First {
        @XmlElement(name = "second")
        public String item1 = "some string";
        @XmlElement(name = "second")
        public String item2 = "need CDATA because of <second>";
        @XmlElement(name = "second")
        public String item3 = "";
        @XmlElement(name = "third")
        public String item4;
        @XmlElement(name = "fifth")
        public String item5 = "need CDATA because of \"";
    }


}
