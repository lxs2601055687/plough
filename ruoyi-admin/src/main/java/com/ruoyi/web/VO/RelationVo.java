package com.ruoyi.web.VO;

import lombok.Data;

@Data
public class RelationVo {
    private String from;
    private String text;
    private String to;

    public RelationVo(String from, String text, String to) {
        this.from = from;
        this.text = text;
        this.to = to;
    }
}
