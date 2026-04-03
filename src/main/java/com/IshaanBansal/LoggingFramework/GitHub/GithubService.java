package com.IshaanBansal.LoggingFramework.GitHub;

import com.IshaanBansal.LoggingFramework.DTO.GithubRepoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);


    public Object getGithubRepos(OAuth2AuthenticationToken authenticationToken) throws  Exception {

        OAuth2AuthorizedClient client  = oAuth2AuthorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),
                authenticationToken.getName()
                );

        String accesToken = client.getAccessToken().getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accesToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://api.github.com/user/repos";

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        List<GithubRepoDTO> repos = new ArrayList<>();
        repos = objectMapper.readValue(response.getBody(), new TypeReference<List<GithubRepoDTO>>() {
            @Override
            public int compareTo(TypeReference<List<GithubRepoDTO>> o) {
                return super.compareTo(o);
            }
        });
        return repos;

    }
}
