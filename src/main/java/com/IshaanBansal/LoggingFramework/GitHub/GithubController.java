package com.IshaanBansal.LoggingFramework.GitHub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/github/getGithubRepos")
    public ResponseEntity<?> getGithubRepos(OAuth2AuthenticationToken authenticationToken) throws  Exception{
        return ResponseEntity.ok(githubService.getGithubRepos(authenticationToken));

    }
}
