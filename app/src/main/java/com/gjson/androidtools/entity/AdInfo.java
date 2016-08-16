package com.gjson.androidtools.entity;

import java.io.Serializable;

/**
 * Created by gjson on 16/7/20.
 * Name AdInfo
 * Version 1.0
 */
public class AdInfo implements Serializable {
    public String name;
    public String url;

    public AdInfo(String na, String ul) {
        this.name = na;
        this.url=ul;

    }
}
