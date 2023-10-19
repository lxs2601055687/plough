package com.ruoyi.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@org.springframework.data.neo4j.core.schema.Node("my_entity")
public class Node {
    @Id
    @GeneratedValue
    private Long id;
    @Property("entity")
    private String entity;
    @Property("name")
    @JsonProperty("name")
    private String text;
}
