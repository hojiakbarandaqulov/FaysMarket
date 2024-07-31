package org.example.service;

import org.example.dto.ApiResponse;
import org.example.dto.contract.ContractCreateDTO;
import org.example.dto.contract.ContractResponseDTO;
import org.example.entity.ContractsEntity;
import org.example.repository.ContractsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ContractsService {
    private final ContractsRepository  contractsRepository;

    public ContractsService(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    public ApiResponse<?> create(ContractCreateDTO createDTO) {
        ContractsEntity contractsEntity=new ContractsEntity();
        contractsEntity.setName(createDTO.getName());
        contractsEntity.setSurname(createDTO.getSurname());
        contractsEntity.setScarf(createDTO.getScarf());
        contractsEntity.setProductName(createDTO.getProductName());
        contractsEntity.setContractLifeTime(LocalDate.now());
        contractsEntity.setProductPrice(createDTO.getProductPrice());
        contractsEntity.setMonthlyPayment(createDTO.getMonthlyPayment());
        contractsEntity.setPhone(createDTO.getPhone());
        contractsEntity.setContractSignedTime(LocalDateTime.now());
        contractsRepository.save(contractsEntity);
        return ApiResponse.ok(toDTO(contractsEntity));
    }

    public ContractResponseDTO toDTO(ContractsEntity entity) {
        ContractResponseDTO createDTO=new ContractResponseDTO();
        createDTO.setProductName(entity.getProductName());
        createDTO.setName(entity.getName());
        createDTO.setSurname(entity.getSurname());
        createDTO.setScarf(entity.getScarf());
        createDTO.setContractLifeTime(entity.getContractLifeTime());
        createDTO.setProductPrice(entity.getProductPrice());
        createDTO.setMonthlyPayment(entity.getMonthlyPayment());
        createDTO.setPhone(entity.getPhone());
        createDTO.setContractSignedTime(entity.getContractSignedTime());

        return createDTO;
    }
}
