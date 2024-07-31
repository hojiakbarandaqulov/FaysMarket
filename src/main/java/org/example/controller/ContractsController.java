package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.contract.ContractCreateDTO;
import org.example.dto.contract.ContractResponseDTO;
import org.example.service.ContractsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractsController {
    private final ContractsService contractsService;

    public ContractsController(ContractsService contractsService) {
        this.contractsService = contractsService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/contractCreate")
    public ResponseEntity<ApiResponse<?>> contractCreate(@RequestBody ContractCreateDTO createDTO) {
        ApiResponse<?> response = contractsService.create(createDTO);
        return ResponseEntity.ok().body(response);
    }
}
