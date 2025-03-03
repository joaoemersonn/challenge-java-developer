package br.com.neurotech.challenge.entity;

public enum CreditType {
    FIXED_INTEREST("Crédito com Juros Fixos"),   // Descrição personalizada
    VARIABLE_INTEREST("Crédito com Juros Variáveis"), // Descrição personalizada
    CONSIGNED("Crédito Consignado");         // Descrição personalizada

    private final String description; // Campo para armazenar a descrição

    // Construtor do enum
    CreditType(String description) {
        this.description = description;
    }

    // Método para obter a descrição
    public String getDescription() {
        return description;
    }
}