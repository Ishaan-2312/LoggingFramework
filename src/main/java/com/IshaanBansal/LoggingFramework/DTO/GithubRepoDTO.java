package com.IshaanBansal.LoggingFramework.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubRepoDTO {

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("private")
    private boolean isPrivate;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("default_branch")
    private String defaultBranch;

}
