package com.IshaanBansal.LoggingFramework.GitHub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectRepoRequest {


        private String serviceName;
        private String repoName;
        private String ownerName;
        private String branch;



}
