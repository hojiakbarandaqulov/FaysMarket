package org.example.service;

import org.example.dto.contract.ContractCreateDTO;
import org.example.dto.contract.ContractResponseDTO;
import org.example.entity.ContractsEntity;
import org.example.repository.ContractsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContractsService {
    private final ContractsRepository  contractsRepository;

    public ContractsService(ContractsRepository contractsRepository) {
        this.contractsRepository = contractsRepository;
    }

    public ContractResponseDTO create(ContractCreateDTO createDTO) {
        ContractsEntity contractsEntity=new ContractsEntity();
        contractsEntity.setName(createDTO.getName());
        contractsEntity.setSurname(createDTO.getSurname());
        contractsEntity.setScarf(createDTO.getScarf());
        contractsEntity.setProductName(createDTO.getProductName());
        contractsEntity.setContractLifeTime(createDTO.getContractLifeTime());
        contractsEntity.setProductPrice(createDTO.getProductPrice());
        contractsEntity.setMonthlyPayment(createDTO.getMonthlyPayment());
        contractsEntity.setPhone(createDTO.getPhone());
        contractsEntity.setContractSignedTime(LocalDateTime.now());
        contractsRepository.save(contractsEntity);
        return ToDTO(contractsEntity);
    }

    public ContractResponseDTO ToDTO(ContractsEntity entity) {
        ContractResponseDTO createDTO=new ContractResponseDTO();
        createDTO.setProductName(entity.getProductName());
        createDTO.setProductPrice(entity.getProductPrice());
        createDTO.setMonthlyPayment(entity.getMonthlyPayment());
        createDTO.setPhone(entity.getPhone());
        return createDTO;
    }
}
