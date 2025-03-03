package br.com.neurotech.challenge.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NeurotechClient {

    @JsonIgnore 
    private String id;

    @JsonAlias({"Name", "name"}) // Aceita "Name" ou "name" no JSON
    @JsonProperty("name") // Nome padrão no JSON
    private String name;

    @JsonAlias({"Age", "age"}) // Aceita "Age" ou "age" no JSON
    @JsonProperty("age") // Nome padrão no JSON
    private Integer age;

    @JsonAlias({"Income", "income"}) // Aceita "Income" ou "income" no JSON
    @JsonProperty("income") // Nome padrão no JSON
    private Double income;
}