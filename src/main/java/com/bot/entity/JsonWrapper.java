package com.bot.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonWrapper {
    List<JSonData> items ;

    public List<JSonData> getInputJsons() {
        return items;
    }

    public void setInputJsons(List<JSonData> inputJsons) {
        this.items = inputJsons;
    }
    
    
}
