package com.javarush.task.task33.task3308;

import com.sun.xml.txw2.annotation.XmlElement;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType (name = "shop")
@XmlRootElement
public class Shop {

    public Goods goods;
    public int count;
    public double profit;
    public String[] secretData;

    @XmlElement
    public static class Goods {
        @XmlAnyElement
        public List<String> names;
    }

    public Shop () {

    }
}
