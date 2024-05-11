package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.repository.CredentialRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;

    private final   EncryptionService encryptionService;

    private final Logger logger = Logger.getLogger(CredentialService.class.getName());

    public CredentialService(CredentialRepository credentialRepository, EncryptionService encryptionService) {
        this.credentialRepository = credentialRepository;
        this.encryptionService = encryptionService;
    }


    public boolean addCredential(String url, String username, String password, Integer userid) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
            Credential credential = new Credential(null, url, username, encodedKey, encryptedPassword, userid);
            credentialRepository.insert(credential);
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    public boolean updateCredential(String url, String username, String password, Integer credentialId) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
            credentialRepository.update(url, username, encodedKey, encryptedPassword, credentialId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCredentialById(Integer credentialId) {
        try {
            credentialRepository.delete(credentialId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Credential> getCredentials(Integer userid) {
        List<Credential> credentials = credentialRepository.getCredentialsByUserId(userid);
        for (Credential credential : credentials) {
            credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }
}
