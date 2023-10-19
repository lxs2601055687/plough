package com.ruoyi.web.controller.graph;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.web.Mapper.NodeRepository;
import com.ruoyi.web.Mapper.RelationRepository;
import com.ruoyi.web.Service.GraphSearchService;
import com.ruoyi.web.VO.NodeVo;
import com.ruoyi.web.VO.RelationVo;
import com.ruoyi.web.entity.GraphResult;
import com.ruoyi.web.entity.Node;
import com.ruoyi.web.entity.Relationship;
import com.ruoyi.web.utils.NodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PloughGraphController {
    @Resource
    private GraphSearchService graphSearchService;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private RelationRepository relationRepository;
    @GetMapping("/api/graph")
    public R<GraphResult> graph() {
        List<Node> nodes = nodeRepository.selectAll();
        List<Relationship> relationships = relationRepository.selectAll();
        List<RelationVo> relationVos = new ArrayList<>();
        List<NodeVo> nodeVos = new ArrayList<>();
        relationships.forEach(relationship -> {
            relationVos.add(new RelationVo(relationship.getFrom().toString(),relationship.getText(),relationship.getTo().toString()));
        });
        nodes.forEach(node -> {
            nodeVos.add(new NodeVo(node.getId().toString(),node.getText(),node.getEntity(),"rgba(30, 144, 255, 1)"));
        });
        nodeVos.add(new NodeVo("2008","高标准基本农田建设","e1916","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("503","土地整治与规划技术","e503","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("777","GBT 19231-2003 土地基本术语","e1231","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1913","土地权属调查","e1821","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("777","GBT 19231-2003 土地基本术语","e1231","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("789","城乡规划","e1243","rgba(238, 178, 94, 1)"));
        /*nodeVos.add(new NodeVo("467","耕地质量","e467","rgba(238, 178, 94, 1)"));*/

        //对relationVos进行去重
        GraphResult graphResult = new GraphResult();
        graphResult.setNodes(nodeVos);
        graphResult.setLines(relationVos);
        graphResult.setRootId("1913");
        return R.ok(graphResult);
    }
    @GetMapping("/api/graph/search/{name}")
    public R<GraphResult> search(@PathVariable("name") String name) {
        List<NodeVo> nodeVos = graphSearchService.selectRelatedNode(name);
        List<Relationship> relationships = relationRepository.selectRelatedNode(name);
        List<Relationship> relationships1 = relationRepository.selectRelatedRootNode(name);
        relationships.addAll(relationships1);
        GraphResult graphResult = new GraphResult();
        graphResult.setNodes(nodeVos);
        List<RelationVo> relationVos = new ArrayList<>();
        relationships.forEach(relationship -> {
            relationVos.add(new RelationVo(relationship.getFrom().toString(),relationship.getText(),relationship.getTo().toString()));
        });
        graphResult.setLines(relationVos);
        //rootId应该是to数组里出现次数最多的内个

        List<String> nodesId = new ArrayList<>();
        relationships.forEach(relationship -> {
            nodesId.add(relationship.getTo().toString());
        });
        String rootId = NodeUtils.findRootId(nodesId);
        graphResult.setRootId(rootId);
        return R.ok(graphResult);
    }
}
