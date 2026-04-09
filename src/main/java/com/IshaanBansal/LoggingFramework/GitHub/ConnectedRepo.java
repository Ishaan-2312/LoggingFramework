package com.IshaanBansal.LoggingFramework.GitHub;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "connected_repos")
@Data
public class ConnectedRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "repo_name")
    private String repoName;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "branch")
    private String branch;
    @Column(name = "access_token")
    private String accessToken;
}
