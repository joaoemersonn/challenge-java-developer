package br.com.neurotech.challenge.dto;

import lombok.Data;

@Data
public class ClientEligibleResponse {
    private String name;
    private Double income;

    public ClientEligibleResponse(String name, Double income) {
        this.name = name;
        this.income = income;
    }
}