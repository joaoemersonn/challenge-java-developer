package br.com.neurotech.challenge.dto;

import br.com.neurotech.challenge.entity.CreditType;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreditCheckResponse {
    private boolean eligibleForVehicle;
    private List<String> eligibleCreditTypes; // Lista de descrições de modalidades de crédito

    public CreditCheckResponse(boolean eligibleForVehicle, List<CreditType> eligibleCreditTypes) {
        this.eligibleForVehicle = eligibleForVehicle;
        // Converte os enums para suas descrições
        this.eligibleCreditTypes = eligibleCreditTypes.stream()
                .map(CreditType::getDescription)
                .collect(Collectors.toList());
    }
}