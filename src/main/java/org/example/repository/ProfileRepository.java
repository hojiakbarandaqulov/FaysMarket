package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.ProfileEntity;
import org.example.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

   @Transactional
   @Modifying
   @Query("update ProfileEntity set status =?2 where id =?1")
   void updateStatus(Integer profileId, ProfileStatus status);

   Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);

   Optional<ProfileEntity> findByPhoneAndPasswordAndVisibleIsTrue(String phone, String md5);
}
