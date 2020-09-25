package com.nova.mom.repositories;

import com.nova.mom.entities.ReleaseMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReleaseMasterRepository extends JpaRepository<ReleaseMaster,Long> {

    Optional<ReleaseMaster> findByReleaseName(String releaseName);

    Optional<ReleaseMaster> findByReleaseId(Long releaseId);

    Optional<ReleaseMaster> findByReleaseNameAndReleaseIdNotIn(String releaseName, List<Long> ids);
}
