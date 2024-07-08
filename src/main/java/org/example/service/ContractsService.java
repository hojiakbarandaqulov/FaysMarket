package org.example.service;

import org.example.dto.ContractCreateDTO;
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

    public ContractCreateDTO create(ContractCreateDTO createDTO) {
        ContractsEntity contractsEntity=new ContractsEntity();
        contractsEntity.setProductName(createDTO.getProductName());
        contractsEntity.setProductPrice(createDTO.getProductPrice());
        contractsEntity.setMonthlyPayment(createDTO.getMonthlyPayment());
        contractsEntity.setPhone(createDTO.getPhone());
        contractsRepository.save(contractsEntity);
        return ToDTO(contractsEntity);
    }

    public ContractCreateDTO ToDTO(ContractsEntity entity) {
        ContractCreateDTO createDTO=new ContractCreateDTO();
        createDTO.setProductName(entity.getProductName());
        createDTO.setProductPrice(entity.getProductPrice());
        createDTO.setMonthlyPayment(entity.getMonthlyPayment());
        createDTO.setPhone(entity.getPhone());
        return createDTO;
    }
}
