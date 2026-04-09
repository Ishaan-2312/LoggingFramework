package com.IshaanBansal.LoggingFramework.GitHub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConnectedRepoRepository extends JpaRepository<ConnectedRepo,Integer> {
}
