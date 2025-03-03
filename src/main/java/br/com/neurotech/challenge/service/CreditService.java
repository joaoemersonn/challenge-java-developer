package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.dto.CreditCheckResponse;
import br.com.neurotech.challenge.entity.CreditType;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CreditService {

    private final ClientService clientService;

    public CreditService(ClientService clientService) {
        this.clientService = clientService;
    }

    public CreditCheckResponse checkCredit(String clientId, VehicleModel vehicleModel) {
    NeurotechClient client = clientService.get(clientId);
    if (client == null) {
        return new CreditCheckResponse(false, List.of()); // Cliente não encontrado
    }

    boolean eligibleForVehicle = false;
    List<CreditType> eligibleCreditTypes = new ArrayList<>();

    // Verifica se o cliente está elegível para o veículo solicitado
    switch (vehicleModel) {
        case HATCH:
            eligibleForVehicle = client.getIncome() >= 5000 && client.getIncome() <= 15000;
            break;
        case SUV:
            eligibleForVehicle = client.getIncome() > 8000 && client.getAge() > 20;
            break;
    }

    // Verifica as modalidades de crédito elegíveis
    if (client.getAge() >= 18 && client.getAge() <= 25) {
        eligibleCreditTypes.add(CreditType.FIXED_INTEREST); // Juros Fixos
    }
    if (client.getAge() >= 21 && client.getAge() <= 65 && client.getIncome() >= 5000 && client.getIncome() <= 15000) {
        eligibleCreditTypes.add(CreditType.VARIABLE_INTEREST); // Juros Variáveis
    }
    if (client.getAge() > 65) {
        eligibleCreditTypes.add(CreditType.CONSIGNED); // Consignado
    }

    // Retorna a resposta
    return new CreditCheckResponse(eligibleForVehicle, eligibleCreditTypes);
}}