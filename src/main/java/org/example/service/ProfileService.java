package org.example.service;

import org.example.dto.profile.ProfileCreateDTO;
import org.example.dto.profile.ProfileUpdateDTO;
import org.example.entity.ProfileEntity;
import org.example.exp.AppBadException;
import org.example.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProfileDTO;

import org.example.utils.MD5Util;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;



    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileDTO create(ProfileCreateDTO profileDTO) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(MD5Util.getMD5(profileDTO.getPassword()));
        entity.setStatus(profileDTO.getStatus());
        entity.setRole(profileDTO.getRole());
        profileRepository.save(entity);
        return profileToDTO(entity);
    }

    public Boolean update(Integer id,ProfileCreateDTO profile) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getSurname());
        profileEntity.setPhone(profile.getPhone());
        profileEntity.setPassword(MD5Util.getMD5(profile.getPassword()));
        profileEntity.setStatus(profile.getStatus());
        profileEntity.setRole(profile.getRole());
        profileRepository.save(profileEntity);
        return true;
    }


    public Boolean updateUser(Integer id, ProfileUpdateDTO profileUser) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profileUser.getName());
        profileEntity.setSurname(profileUser.getSurname());
        profileRepository.save(profileEntity);
        return true;
    }

    public ProfileDTO profileToDTO(ProfileEntity entity) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(entity.getId());
        profileDTO.setName(entity.getName());
        profileDTO.setSurname(entity.getSurname());
        profileDTO.setPhone(entity.getPhone());
        profileDTO.setPassword(entity.getPassword());
        profileDTO.setStatus(entity.getStatus());
        profileDTO.setRole(entity.getRole());
        return profileDTO;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            log.error("Profile not found id = {}", id);
            throw new AppBadException("Profile not found");
        });
    }

    public PageImpl<ProfileDTO> getAllPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfileEntity> pageObj = profileRepository.findAll(pageable);

        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : pageObj.getContent()) {
            dtoList.add(profileToDTO(entity));
        }

        Long totalCount = pageObj.getTotalElements();
        return new PageImpl<ProfileDTO>(dtoList, pageable, totalCount);
    }

    public Boolean deleteId(Integer id) {
        profileRepository.deleteById(id);
        return true;
    }
}

