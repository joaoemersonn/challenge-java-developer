package br.com.neurotech.challenge.controller;

import br.com.neurotech.challenge.dto.ClientEligibleResponse;
import br.com.neurotech.challenge.dto.CreditCheckResponse;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client")
@Tag(name = "Client API", description = "API para gerenciamento de clientes")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;

    public ClientController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @Operation(
        summary = "Cria um novo cliente",
        description = "Cadastra um novo cliente no sistema e retorna a URL do recurso criado no header 'Location'."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados do cliente inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody NeurotechClient client) {
        String clientId = clientService.save(client);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(clientId).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
        summary = "Recupera um cliente pelo ID",
        description = "Retorna os dados de um cliente com base no ID fornecido."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NeurotechClient> getClient(
        @Parameter(description = "ID do cliente", example = "12345", required = true)
        @PathVariable String id) {
        NeurotechClient client = clientService.get(id);
        return ResponseEntity.ok(client);
    }

    @Operation(
        summary = "Verifica se um cliente está apto para crédito automotivo",
        description = "Verifica se o cliente está apto a receber crédito para um determinado modelo de veículo."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}/credit/{vehicleModel}")
    public ResponseEntity<CreditCheckResponse> checkCredit(
        @PathVariable String id,
        @PathVariable VehicleModel vehicleModel) {
        CreditCheckResponse response = creditService.checkCredit(id, vehicleModel);
        return ResponseEntity.ok(response);
    }
    @Operation(
        summary = "Lista clientes elegíveis para crédito fixo e Hatch",
        description = "Retorna uma lista de clientes entre 23 e 49 anos que possuem crédito com juros fixos e estão aptos a adquirirem crédito automotivo para veículos do tipo Hatch."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    @GetMapping("/eligible-hatch")
    public ResponseEntity<List<ClientEligibleResponse>> getEligibleFixedAndHatchClients() {
        List<NeurotechClient> eligibleClients = clientService.getEligibleHatchClients();

        List<ClientEligibleResponse> response = eligibleClients.stream()
                .map(client -> new ClientEligibleResponse(client.getName(), client.getIncome()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}