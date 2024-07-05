package org.example.repository;

import org.example.entity.ContractsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractsRepository extends JpaRepository<ContractsEntity, Integer> {

}
