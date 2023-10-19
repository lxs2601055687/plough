package com.ruoyi.web.Service.impl;

import com.ruoyi.web.Mapper.NodeRepository;
import com.ruoyi.web.Mapper.RelationRepository;
import com.ruoyi.web.Service.GraphSearchService;
import com.ruoyi.web.VO.NodeVo;
import com.ruoyi.web.entity.Node;
import com.ruoyi.web.entity.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GraphSearchServiceImpl implements GraphSearchService {
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private NodeRepository nodeRepository;

    @Override
    public List<NodeVo> selectRelatedNode(String name) {
        //拿到所有的Node
        List<Node> nodeVos = nodeRepository.selectAllIndex();
        List<Relationship> relationships = relationRepository.selectRelatedNode(name);
        List<Relationship> relationships1 = relationRepository.selectRelatedRootNode(name);
        relationships.addAll(relationships1);
        //拿到一个数组数组值是Relationship的from和to
        int size = nodeVos.size();
        //定义一个数组，长度是nodeVos的长度
        List<String> nodeIdsList = new ArrayList<>();
        relationships.forEach(relationship -> {
            nodeIdsList.add(relationship.getFrom().toString());
            nodeIdsList.add(relationship.getTo().toString());

        });
        //去重
        List<String> nodeIdSet = new ArrayList<>();
        for (String s : nodeIdsList) {
            if (!nodeIdSet.contains(s)) {
                nodeIdSet.add(s);
            }
        }
        //构造一个新的nodeList只要nodeIdSet里面的存在的Id
        List<Node> nodesResult = new ArrayList<>();
        for (String s : nodeIdSet) {
            for (Node nodeVo : nodeVos) {
                if (s.equals(nodeVo.getId().toString())) {
                    nodesResult.add(nodeVo);
                }
            }
        }
        //构造Vo对象
        List<NodeVo> nodeVoList = new ArrayList<>();
        nodesResult.forEach(node -> {
            nodeVoList.add(new NodeVo(node.getId().toString(), node.getText(), node.getEntity(), "rgba(30, 144, 255, 1)"));
        });

        return nodeVoList;
    }
}
