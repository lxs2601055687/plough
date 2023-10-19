package com.ruoyi.web.Mapper;

import com.ruoyi.web.entity.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface NodeRepository extends Neo4jRepository<Node,Long> {
    @Query("MATCH p=(n:my_entity) RETURN p limit 350")
    List<Node> selectAll();
    @Query("MATCH p=(n:my_entity) RETURN p")
    List<Node> selectAllIndex();

    @Query("MATCH(p:Person{name:{my_entity}}) return p")
    Node findByName(String name);


}
