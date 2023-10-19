package com.ruoyi.web.entity;

import com.ruoyi.web.VO.NodeVo;
import com.ruoyi.web.VO.RelationVo;
import lombok.Data;

import java.util.List;

@Data
public class GraphResult {
    private String rootId;
    private List<NodeVo> nodes;
    private List<RelationVo> lines;
}
