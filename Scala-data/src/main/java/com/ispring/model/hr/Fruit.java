package com.ispring.model.hr;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/8
 */
@XmlRootElement(name = "fruit")
public class Fruit {

    String name;
    int quality;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return quality;
    }

    @XmlElement
    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Fruit(String name, int quality) {
        this.name = name;
        this.quality = quality;
    }

    public Fruit() {
    }

}
