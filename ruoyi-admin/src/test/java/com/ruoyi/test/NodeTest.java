package com.ruoyi.test;

import com.ruoyi.web.Mapper.NodeRepository;
import com.ruoyi.web.Mapper.RelationRepository;
import com.ruoyi.web.Service.GraphSearchService;
import com.ruoyi.web.VO.NodeVo;
import com.ruoyi.web.entity.Node;
import com.ruoyi.web.entity.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
@SpringBootTest
public class NodeTest {
    @Autowired
    private GraphSearchService graphSearchService;
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    RelationRepository relationRepository;
    @Test
    public void test() {
        List<Node> nodes = nodeRepository.selectAllIndex();
        nodes.forEach(System.out::println);
    }
    @Test
    public void test1() {
        List<Relationship> all = relationRepository.selectAll();
        all.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<Relationship> all = relationRepository.selectRelatedRootNode("城乡规划");

        all.forEach(System.out::println);
        System.out.println(all.size());
    }
    @Test
    public void test3() {
        List<NodeVo> all = graphSearchService.selectRelatedNode("园");
        all.forEach(System.out::println);
    }
}
