package com.udacity.jwdnd.course1.cloudstorage.cotrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    @Autowired
    CredentialService credentialService;

    @Autowired
    private UserService userService;

    @PostMapping("/addCredential")
    public String addCredential(
            @RequestParam String url,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Integer credentialId,
            Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        if (credentialId != null) {
            Boolean bool = credentialService.updateCredential(url, username, password, credentialId);
            return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
        }
        Boolean bool = credentialService.addCredential(url, username, password, user.getUserId());
        return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId) {
        Boolean bool = credentialService.deleteCredentialById(credentialId);
        return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false";
    }

    @PostMapping("/editCredential")
    public String editCredential(@RequestParam Integer credentialId) {
        return "redirect:/home?credentialId=" + credentialId;
    }

    @PostMapping("/viewCredential")
    public String viewCredential(@RequestParam Integer credentialId) {
        return "redirect:/home?credentialId=" + credentialId;
    }
}
