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
import java.util.Map;

@Service
public class GithubService {

    @Autowired
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Autowired
    private ConnectedRepoRepository connectedRepoRepository;

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

    public List<String> getRepoBranches(String owner,String repo,OAuth2AuthenticationToken authenticationToken){
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                authenticationToken.getAuthorizedClientRegistrationId(),authenticationToken.getName());

        String accessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String url = String.format(
                "https://api.github.com/repos/%s/%s/branches",
                owner,
                repo
        );

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        try{
            List<Object> branches = objectMapper.readValue(response.getBody(),List.class);

            return branches.stream()
                    .map(branch -> ((java.util.Map<?, ?>) branch).get("name").toString())
                    .toList();
        }
        catch (Exception e){
            throw  new RuntimeException();
        }


    }


    public void saveRepoMapping(ConnectRepoRequest request, OAuth2AuthenticationToken token) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),token.getName());

        String accessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        ConnectedRepo entity = new ConnectedRepo();
        entity.setServiceName(request.getServiceName());
        entity.setRepoName(request.getRepoName());
        entity.setOwnerName(request.getOwnerName());
        entity.setBranch(request.getBranch());
        entity.setAccessToken(accessToken);

        connectedRepoRepository.save(entity);
    }

    public List<String> fetchRepoTree(String owner,String repo,String branch,String token){
        String url = String.format(
                "https://api.github.com/repos/%s/%s/git/trees/%s?recursive=1",
                owner, repo, branch
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<Map> response = new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                Map.class
        );

        List<Map<String,Object>> tree = (List<Map<String, Object>>) response.getBody().get("tree");

        return tree.stream()
                .filter(node -> "blob".equals(node.get("type")))
                .map(node -> node.get("path").toString())
                .toList();

    }


    public String fetchFileContent(String owner,String repo,String path,String token){
        String url = String.format(
                "https://api.github.com/repos/%s/%s/contents/%s",
                owner, repo, path
        );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<Map> response = new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                Map.class
        );

        String content = (String) response.getBody().get("content");

        return new String(java.util.Base64.getDecoder().decode(content));

    }
}
