package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ProfileDTO;
import org.example.dto.auth.AuthorizationResponseDTO;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.entity.ProfileEntity;
import org.example.entity.ProfileEntity;
import org.example.enums.LanguageEnum;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.exp.AppBadException;
import org.example.repository.ProfileRepository;
import org.example.service.history.SmsHistoryService;
import org.example.service.history.SmsService;
import org.example.utils.JwtUtil;
import org.example.utils.MD5Util;
import org.example.utils.RandomUtil;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Service
public class AuthorizationService {

    private final ProfileRepository profileRepository;
    private final ResourceBundleMessageSource resourceBundleMessageSource;
    private final SmsHistoryService smsHistoryService;
    private final SmsService smsService;

    public AuthorizationService(ProfileRepository profileRepository, ResourceBundleMessageSource resourceBundleMessageSource, SmsHistoryService smsHistoryService, SmsService smsService) {
        this.profileRepository = profileRepository;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
        this.smsHistoryService = smsHistoryService;
        this.smsService = smsService;
    }

    public String registration(RegistrationDTO dto, LanguageEnum language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            log.warn("Phone already exists email => {}", dto.getPhone());
            String message = resourceBundleMessageSource.getMessage("phone.exists", null, new Locale(language.name()));
            throw new AppBadException(message);
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(entity);
        // send email
        sendRegistrationPhone(entity.getId(), dto.getPhone());
        smsService.sendSms(dto.getPhone());
        return resourceBundleMessageSource.getMessage("email.registration.verify", null, new Locale(language.name()));
    }

    public String authorizationVerification(Integer userId, LanguageEnum language) {
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            log.warn("User not found => {}", userId);
            String message = resourceBundleMessageSource.getMessage("user.not.found", null, new Locale(language.name()));
            throw new AppBadException(message);
        }

        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            String message = resourceBundleMessageSource.getMessage("registration.not.completed", null, new Locale(language.name()));
            throw new AppBadException(message);
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return resourceBundleMessageSource.getMessage("Success", null, new Locale(language.name()));
    }

    public AuthorizationResponseDTO login(LoginDTO dto, LanguageEnum language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPasswordAndVisibleIsTrue(
                dto.getPhone(),
                MD5Util.getMD5(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("Wrong status");
        }

        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(responseDTO.getId(), entity.getPhone(), responseDTO.getRole()));
        return responseDTO;
    }

    public String registrationResendPhone(String phone, LanguageEnum language) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            throw new AppBadException("Phone not exists");
        }
        ProfileEntity entity = optional.get();
        smsHistoryService.isNotExpiredPhone(entity.getPhone());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        smsHistoryService.checkPhoneLimit(phone);
        sendRegistrationPhone(entity.getId(), phone);
        return "To complete your registration please verify your phone.";
    }

    public void sendRegistrationPhone(Integer profileId, String phone) {
        String url = "http://localhost:8080/auth/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
        smsService.sendSms(phone);// create history
    }
}
