package com.ruoyi.web.Mapper;


import com.ruoyi.web.entity.Node;
import com.ruoyi.web.entity.Relationship;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface RelationRepository extends Neo4jRepository<Relationship ,Long> {
    @Query("MATCH (start:my_entity)-[relation]->(end:my_entity)RETURN TYPE(relation) AS text, ID(end) AS to, ID(start) AS from;")
    List<Relationship> selectAll();
    @Query("MATCH p=()-[r:`属于`]->() RETURN p LIMIT 25")
    List<Relationship> selectBelong();
    @Query("MATCH (start:my_entity) WHERE start.name CONTAINS $name MATCH (start)-[r]->(end) RETURN TYPE(r) AS text, ID(end) AS to, ID(start) AS from;")
    List<Relationship> selectRelatedNode(@Param("name") String name);
    @Query("MATCH (end:my_entity) WHERE end.name CONTAINS $name MATCH (start)-[r]->(end) RETURN TYPE(r) AS text, ID(end) AS to, ID(start) AS from;")
    List<Relationship> selectRelatedRootNode(@Param("name") String name);
    @Query("MATCH (start:my_entity) WHERE start.name CONTAINS $startName MATCH (start)-[r]-(end:my_entity) WHERE end.name CONTAINS $endName RETURN TYPE(r) AS text, ID(end) AS to, ID(start) AS from;")
    List<Relationship> selectRelatedNode(@Param("startName") String startName, @Param("endName") String endName);

}
