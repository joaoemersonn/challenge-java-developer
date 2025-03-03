package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    private final Map<String, NeurotechClient> clients = new HashMap<>();

    @Override
    public String save(NeurotechClient client) {
        String clientId = String.valueOf(System.currentTimeMillis()); // Simples geração de ID
        client.setId(clientId);
        clients.put(clientId, client);
        return clientId;
    }

    @Override
    public NeurotechClient get(String id) {
        return clients.get(id);
    }

    @Override
    public List<NeurotechClient> getEligibleHatchClients() {
        List<NeurotechClient> eligibleClients = new ArrayList<>();
        for (NeurotechClient client : clients.values()) {
            if (client.getAge() >= 23 && client.getAge() <= 49 && client.getIncome() >= 5000 && client.getIncome() <= 15000) {
                eligibleClients.add(client);
            }
        }
        return eligibleClients;
    }
}