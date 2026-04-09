package com.IshaanBansal.LoggingFramework.GitHub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/github/getGithubRepos")
    public ResponseEntity<?> getGithubRepos(OAuth2AuthenticationToken authenticationToken) throws  Exception{
        return ResponseEntity.ok(githubService.getGithubRepos(authenticationToken));

    }


    @GetMapping("/repos/{owner}/{repo}/branches")
    public List<String> getBranches(@RequestParam String owner, @RequestParam String repo, OAuth2AuthenticationToken authToken) {
        return githubService.getRepoBranches(owner, repo, authToken);
    }

    @PostMapping("/connect-repo")
    public void connectRepo(@RequestBody ConnectRepoRequest request, OAuth2AuthenticationToken token) {
        githubService.saveRepoMapping(request, token);
    }
}
