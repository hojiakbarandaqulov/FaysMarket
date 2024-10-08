package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ApiResponse;
import org.example.dto.auth.AuthorizationResponseDTO;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.entity.ProfileEntity;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.exp.AppBadException;
import org.example.repository.ProfileRepository;
import org.example.service.history.SmsHistoryService;
import org.example.service.history.SmsService;
import org.example.utils.JwtUtil;
import org.example.utils.MD5Util;
import org.example.utils.RandomUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthorizationService {
    private final ProfileRepository profileRepository;
    private final SmsHistoryService smsHistoryService;
    private final SmsService smsService;

    public AuthorizationService(ProfileRepository profileRepository, SmsHistoryService smsHistoryService, SmsService smsService) {
        this.profileRepository = profileRepository;
        this.smsHistoryService = smsHistoryService;
        this.smsService = smsService;
    }

    public ApiResponse<String> registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.error("phone exists");
            throw new AppBadException("Phone exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);

        sendRegistrationPhone(entity.getId(), dto.getPhone());
        smsService.sendSms(dto.getPhone());
        return ApiResponse.ok("To complete your registration please verify your phone");
    }

    public ApiResponse authorizationVerification(Integer userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            log.error("profile not found");
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("Registration not completed");
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return ApiResponse.ok();
    }

    public ApiResponse<AuthorizationResponseDTO> login(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPasswordAndVisibleIsTrue(
                dto.getPhone(),
                MD5Util.getMD5(dto.getPassword()));
        if (optional.isEmpty()) {
            log.error("user not found");
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.error("wrong status");
            throw new AppBadException("Wrong status");
        }

        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(responseDTO.getId(), entity.getPhone(), responseDTO.getRole()));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<?> registrationResendPhone(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            log.error("phone not found");
            throw new AppBadException("Phone not exists");
        }
        ProfileEntity entity = optional.get();
        smsHistoryService.isNotExpiredPhone(entity.getPhone());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("registration not completed");
            throw new AppBadException("Registration not completed");
        }
        smsHistoryService.checkPhoneLimit(phone);
        sendRegistrationPhone(entity.getId(), phone);
        return ApiResponse.ok("To complete your registration please verify your phone.");
    }

    public void sendRegistrationPhone(Integer profileId, String phone) {
        String url = "http://localhost:8080/api/v1/authorization/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
        smsService.sendSms(phone);
    }
}

