package com.ruoyi.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@RelationshipEntity("relation")
public class Relationship  {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode

    private Long from;

    @EndNode
    private Long to;

    @Property
    private String text;

}
