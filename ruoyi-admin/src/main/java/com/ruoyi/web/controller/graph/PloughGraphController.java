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
        if(sopVo.getSubject().equals("null")&&sopVo.getObject().equals("null")&&sopVo.getPredicate().equals("null")){
            return R.ok();
        }
        if(!sopVo.getSubject().equals("null")&&!sopVo.getObject().equals("null")){
            List<NodeVo> nodeVos = new ArrayList<>();
            List<Relationship> relationships = new ArrayList<>();
            List<Relationship> relationships1 = new ArrayList<>();
                nodeVos = graphSearchService.selectRelatedNode(sopVo.getSubject());
                if(nodeVos.size()==0){
                    nodeVos = graphSearchService.selectRelatedNode(sopVo.getObject());
                }
                relationships = relationRepository.selectRelatedNode(sopVo.getSubject(),sopVo.getObject());
                if(relationships.size()==0){
                    relationships = relationRepository.selectRelatedNode(sopVo.getSubject());
                    relationships1 = relationRepository.selectRelatedRootNode(sopVo.getObject());
                }
                if(relationships.size()==1){
                    Relationship relationship = relationships.get(0);
                    //过滤nodeVos只保留relationship的to和from
                    List<NodeVo> nodeVos1 = new ArrayList<>();
                    nodeVos.forEach(nodeVo -> {
                        if(nodeVo.getId().equals(relationship.getFrom().toString())||nodeVo.getId().equals(relationship.getTo().toString())){
                            nodeVos1.add(nodeVo);
                        }
                    });
                    nodeVos = nodeVos1;
                }
                if(relationships.size()!= 0 || relationships1.size()!=0){
                    //把关系的to和from加入存在一个数组里 然后过滤nodeVos只保留这个数组里的
                    List<String> nodeIds = new ArrayList<>();
                    relationships.forEach(relationship -> {
                        nodeIds.add(relationship.getFrom().toString());
                        nodeIds.add(relationship.getTo().toString());
                    });
                    relationships1.forEach(relationship -> {
                        nodeIds.add(relationship.getFrom().toString());
                        nodeIds.add(relationship.getTo().toString());
                    });
                    List<NodeVo> nodeVos1 = new ArrayList<>();
                    nodeVos.forEach(nodeVo -> {
                        if(nodeIds.contains(nodeVo.getId())){
                            nodeVos1.add(nodeVo);
                        }
                    });
                    nodeVos = nodeVos1;

                }

        /*if(!sopVo.getObject().equals("null")){
            nodeVos.addAll(graphSearchService.selectRelatedNode(sopVo.getObject()));
            relationships.addAll(relationRepository.selectRelatedNode(sopVo.getObject()));
            relationships1.addAll(relationRepository.selectRelatedRootNode(sopVo.getObject()));
        }*/
       /* if(!sopVo.getPredicate().equals("null")){
            nodeVos.addAll(graphSearchService.selectRelatedNode(sopVo.getObject()));
            relationships.addAll(relationRepository.selectRelatedNode(sopVo.getObject()));
            relationships1.addAll(relationRepository.selectRelatedRootNode(sopVo.getObject()));
        }*/
            //给nodeVos去重
            List<NodeVo> nodeVos1 = new ArrayList<>();
            for (NodeVo nodeVo : nodeVos) {
                if (!nodeVos1.contains(nodeVo)) {
                    nodeVos1.add(nodeVo);
                }
            }
            nodeVos = nodeVos1;
            //给relationships去重
            relationships.addAll(relationships1);
            List<Relationship> relationships2 = new ArrayList<>();
            for (Relationship relationship : relationships) {
                if (!relationships2.contains(relationship)) {
                    relationships2.add(relationship);
                }
            }
            relationships = relationships2;
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
            System.out.println(graphResult.toString());
            return R.ok(graphResult);
        }
        if(!sopVo.getSubject().equals("null")){
            List<NodeVo> nodeVos = new ArrayList<>();
            List<Relationship> relationships = new ArrayList<>();
            List<Relationship> relationships1 = new ArrayList<>();
            if(!sopVo.getSubject().equals("null")){
                nodeVos = graphSearchService.selectRelatedNode(sopVo.getSubject());
                relationships = relationRepository.selectRelatedNode(sopVo.getSubject());
                relationships1 = relationRepository.selectRelatedRootNode(sopVo.getSubject());
            }
        /*if(!sopVo.getObject().equals("null")){
            nodeVos.addAll(graphSearchService.selectRelatedNode(sopVo.getObject()));
            relationships.addAll(relationRepository.selectRelatedNode(sopVo.getObject()));
            relationships1.addAll(relationRepository.selectRelatedRootNode(sopVo.getObject()));
        }*/
       /* if(!sopVo.getPredicate().equals("null")){
            nodeVos.addAll(graphSearchService.selectRelatedNode(sopVo.getObject()));
            relationships.addAll(relationRepository.selectRelatedNode(sopVo.getObject()));
            relationships1.addAll(relationRepository.selectRelatedRootNode(sopVo.getObject()));
        }*/
            //给nodeVos去重
            List<NodeVo> nodeVos1 = new ArrayList<>();
            for (NodeVo nodeVo : nodeVos) {
                if (!nodeVos1.contains(nodeVo)) {
                    nodeVos1.add(nodeVo);
                }
            }
            nodeVos = nodeVos1;
            //给relationships去重
            relationships.addAll(relationships1);
            List<Relationship> relationships2 = new ArrayList<>();
            for (Relationship relationship : relationships) {
                if (!relationships2.contains(relationship)) {
                    relationships2.add(relationship);
                }
            }
            relationships = relationships2;
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
            System.out.println(graphResult.toString());
            return R.ok(graphResult);
        }

        return R.ok();
    }

}
