package com.ruoyi.web.controller.graph;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.web.Mapper.NodeRepository;
import com.ruoyi.web.Mapper.RelationRepository;
import com.ruoyi.web.Service.GraphSearchService;
import com.ruoyi.web.VO.NodeVo;
import com.ruoyi.web.VO.RelationVo;
import com.ruoyi.web.VO.SopVo;
import com.ruoyi.web.dao.Sop;
import com.ruoyi.web.entity.GraphResult;
import com.ruoyi.web.entity.Node;
import com.ruoyi.web.entity.Relationship;
import com.ruoyi.web.utils.MainPart;
import com.ruoyi.web.utils.MainPartExtractor;
import com.ruoyi.web.utils.NodeUtils;
import edu.stanford.nlp.ling.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Set<String> nodeIds = nodes.stream()
            .map(node -> node.getId().toString())
            .collect(Collectors.toSet());

        List<Relationship> relationships = relationRepository.selectAll();
        System.out.println(relationships.size() + " " + nodes.size());
        List<RelationVo> relationVos = relationships.stream()
            .map(relationship -> new RelationVo(
                relationship.getFrom().toString(),
                relationship.getText(),
                relationship.getTo().toString()
            ))
            .collect(Collectors.toList());

        System.out.println(relationVos.size() + " " + nodes.size());
        List<NodeVo> nodeVos = nodes.stream()
            .map(node -> new NodeVo(node.getId().toString(), node.getText(), node.getEntity(), "rgba(30, 144, 255, 1)"))
            .collect(Collectors.toList());
        //过滤掉没有关系的节点
        System.out.println(nodeVos.size() + " " + relationVos.size());
        // 手动添加的节点，考虑是否需要根据业务逻辑动态添加
        //过滤relationVos 当from和to都在nodeVos里的时候才保留
        System.out.println(relationVos.size() + " " + nodeVos.size());
        // 添加节点代码...
        nodeVos.add(new NodeVo("2008","高标准基本农田建设","e1916","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("503","土地整治与规划技术","e503","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1367","土地权属调查","e1821","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("777","GBT 19231-2003 土地基本术语","e1231","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("789","城乡规划","e1243","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1254","土地利用现状分类-三调","e1243","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("360","地籍测量","e360","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("503","土地整治与规划技术","e503","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("847","遥感基础知识","e1301","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1462","高标准基本农田建设","e1916","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1135","土地利用现状分类-二调","e1589","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1441","生态规划","e1895","rgba(238, 178, 94, 1)"));
        nodeVos.add(new NodeVo("1170","建设用地","e1624","rgba(238, 178, 94, 1)"));
      /*  nodeVos.add(new NodeVo("1696","20180205 第三次全国土地调查技术规程","e1708","rgba(238, 178, 94, 1)"));*/
        /*nodeVos.add(new NodeVo("467","耕地质量","e467","rgba(238, 178, 94, 1)"));*/
        //给nodeVos排序
        nodeVos.sort((o1, o2) -> {
            if (nodeIds.contains(o1.getId()) && nodeIds.contains(o2.getId())) {
                return 0;
            }
            if (nodeIds.contains(o1.getId())) {
                return -1;
            }
            if (nodeIds.contains(o2.getId())) {
                return 1;
            }
            return 0;
        });
        GraphResult graphResult = new GraphResult();
        //拿到relationVos里的to出现次数最多的前100个relationVos 然后有这100个to的全保留
        Map<String, Long> toFrequency = relationVos.stream()
            .collect(Collectors.groupingBy(RelationVo::getTo, Collectors.counting()));

        // 找到出现次数最多的前100个 'to'
        List<String> top100Tos = toFrequency.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(30)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
         top100Tos.forEach(System.out::println);
        // 过滤 relationVos，仅保留 'to' 在 top100 中的对象
        List<RelationVo> filteredRelationVos = relationVos.stream()
            .filter(relationVo -> top100Tos.contains(relationVo.getTo()))
            .collect(Collectors.toList());
        filteredRelationVos.forEach(vo -> {
            System.out.println(vo.toString());
        });
        System.out.println(filteredRelationVos.size() + " " + nodeVos.size());
        graphResult.setNodes(nodeVos);
        graphResult.setLines(filteredRelationVos);
        graphResult.setRootId("777");
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
    @PostMapping("/api/graph/search/sop")
    public R<SopVo> sop(@RequestBody Sop sop) {
        MainPart mp = MainPartExtractor.getMainPart(sop.getName());
        System.out.println(mp.toString());
        System.out.println(mp.subject);
        System.out.println(mp.object);
        System.out.println(mp.predicate);
        //创建sopvo对象参数要判断是否为空为空的话就传null字符串
           SopVo sopvo = new SopVo();
        if(mp.subject==null){
            sopvo.setSubject("null");
        }else{
            sopvo.setSubject(mp.subject.toString("value"));
        }
        if(mp.object==null){
            sopvo.setObject("null");
        }else{
            sopvo.setObject(mp.object.toString("value"));
        }
        if(mp.predicate==null){
            sopvo.setPredicate("null");
        }else{
            sopvo.setPredicate(mp.predicate.toString("value"));
        }
        List<Word> seg = MainPartExtractor.seg(sop.getName());
        List<String> result = new ArrayList<>();
        seg.forEach(word -> {
            result.add(word.toString());
        });
        sopvo.setWordList(result);
        return R.ok(sopvo);
    }
    @PostMapping("/api/graph/search/sop/finish")
    public R<GraphResult> sopFinish(@RequestBody SopVo sopVo) {
        System.out.println(sopVo.toString());

        if (isNullOrEmpty(sopVo.getSubject()) && isNullOrEmpty(sopVo.getObject()) && isNullOrEmpty(sopVo.getPredicate())) {
            return R.ok();
        }

        if (isValidSopQuery(sopVo)) {
            List<NodeVo> nodes = queryNodes(sopVo.getSubject(), sopVo.getObject());
            List<Relationship> relationships = queryRelationships(sopVo.getSubject(), sopVo.getObject());

            nodes = filterNodesByRelationships(nodes, relationships);
            relationships = new ArrayList<>(new HashSet<>(relationships)); // 去重关系

            GraphResult graphResult = createGraphResult(nodes, relationships);
            System.out.println(graphResult.toString());
            return R.ok(graphResult);
        }

        return R.ok();
    }

    private GraphResult createGraphResult(List<NodeVo> nodes, List<Relationship> relationships) {
        GraphResult graphResult = new GraphResult();
        graphResult.setNodes(nodes);

        List<RelationVo> relationVos = relationships.stream()
            .map(r -> new RelationVo(r.getFrom().toString(), r.getText(), r.getTo().toString()))
            .collect(Collectors.toList());
        graphResult.setLines(relationVos);

        String rootId = NodeUtils.findRootId(relationships.stream()
            .map(Relationship::getTo)
            .map(Object::toString)
            .collect(Collectors.toList()));
        graphResult.setRootId(rootId);

        return graphResult;
    }
    // 将字符串 "null" 判断改为更合适的空值检查
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty() || "null".equals(value.trim());
    }

    // 检查 SOP 条件是否符合查询要求
    private boolean isValidSopQuery(SopVo sopVo) {
        return !isNullOrEmpty(sopVo.getSubject()) && !isNullOrEmpty(sopVo.getObject());
    }
    // 查询节点和关系
    private List<NodeVo> queryNodes(String subject, String object) {
        List<NodeVo> nodeVos = graphSearchService.selectRelatedNode(subject);
        if (nodeVos.isEmpty()) {
            nodeVos = graphSearchService.selectRelatedNode(object);
        }
        return nodeVos;
    }

    private List<Relationship> queryRelationships(String subject, String object) {
        List<Relationship> relationships = relationRepository.selectRelatedNode(subject, object);
        List<Relationship> relationships1 = new ArrayList<>();
        if(relationships.isEmpty() || relationships.size() < 2) {
            relationships1.addAll(relationRepository.selectRelatedNode(subject));
            relationships1.addAll(relationRepository.selectRelatedRootNode(object));
        }
        if (relationships1.isEmpty() || relationships1.size() < 2) {
            relationships.addAll(relationRepository.selectRelatedRootNode(subject));
            if(relationships.size()>100 && relationships1.size()<2) {
                relationships = relationships.subList(0, 100);
            }
        }
        relationships.addAll(relationships1);
        return relationships;
    }

    // 过滤节点基于关系ID
    private List<NodeVo> filterNodesByRelationships(List<NodeVo> nodes, List<Relationship> relationships) {
        Set<String> relevantIds = relationships.stream()
            .flatMap(r -> Stream.of(r.getFrom().toString(), r.getTo().toString()))
            .collect(Collectors.toSet());
        return nodes.stream()
            .filter(node -> relevantIds.contains(node.getId()))
            .collect(Collectors.toList());
    }


}
