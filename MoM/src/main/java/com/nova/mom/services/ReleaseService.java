package com.nova.mom.services;

import com.nova.mom.dtos.ReleaseDTO;

import java.util.List;

public interface ReleaseService {

    ReleaseDTO createRelease(ReleaseDTO releaseDTO);

    ReleaseDTO updateRelease(ReleaseDTO releaseDTO);

    List<ReleaseDTO> getReleaseDetails();

    ReleaseDTO getReleaseDetailsById(Long releaseId);

}
