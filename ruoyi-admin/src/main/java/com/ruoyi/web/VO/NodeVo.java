package com.ruoyi.web.VO;


import lombok.Data;


@Data
public class NodeVo {

    private String id;

    private String entity;

    private String text;

    private String color = "rgba(30, 144, 255, 1)";

    public NodeVo(String id, String text, String entity, String color) {

        this.id = id;

        this.text = text;

        this.entity = entity;

        this.color = color;
    }
}
