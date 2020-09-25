package com.nova.mom.services.impl;

import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.entities.ReleaseMaster;
import com.nova.mom.mappers.ReleaseMasterMapper;
import com.nova.mom.repositories.ReleaseMasterRepository;
import com.nova.mom.services.ReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReleaseServiceImpl implements ReleaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseServiceImpl.class);

    @Autowired
    private ReleaseMasterRepository releaseMasterRepository;

    @Override
    public ReleaseDTO createRelease(ReleaseDTO releaseDTO) {
        LOGGER.info("create release service method start");
        if(releaseDTO !=null){
            if(releaseNameUniqueCheck(releaseDTO)){
                ReleaseMaster releaseMaster = new ReleaseMaster();
                releaseMaster.setReleaseName(releaseDTO.getReleaseName());
                releaseMaster.setServerBaseUrl(releaseDTO.getServerBaseUrl());
                releaseMaster.setActive("Y");
                releaseMaster.setCreatedAt(new Date());
                releaseMaster.setCreatedBy("Admin");
                releaseMaster.setUpdatedAt(new Date());
                releaseMaster.setUpdatedBy("Admin");
                releaseMaster.setVersion("V1.0");
                releaseMaster.setRecVerNo(1);
                ReleaseMaster releaseMaster1 = releaseMasterRepository.save(releaseMaster);
                releaseDTO = ReleaseMasterMapper.INSTANCE.toDto(releaseMaster1);
            }else{
                LOGGER.info("release name duplicate in create");
                releaseDTO.setErrorStatus(true);
                releaseDTO.setErrorMessage("Release Name is already available");
            }
        }
        LOGGER.info("create release service method end");
        return releaseDTO;
    }

    private boolean releaseNameUniqueCheck(ReleaseDTO releaseDTO){
        LOGGER.info("release name unique check method start");
        boolean uniqueCheck = false;
       if(releaseMasterRepository.findByReleaseName(releaseDTO.getReleaseName()).isPresent()){
           uniqueCheck = false;
       } else {
           uniqueCheck = true;
       }
        LOGGER.info("release name unique check method end");
       return uniqueCheck;
    }

    private boolean releaseNameUniqueCheckWithId(ReleaseDTO releaseDTO){
        LOGGER.info("release name unique check with id method start");
        boolean uniqueCheck = false;
        List<Long> ids = new ArrayList<>();
        ids.add(releaseDTO.getReleaseId());
        if(releaseMasterRepository.findByReleaseNameAndReleaseIdNotIn(releaseDTO.getReleaseName(),ids).isPresent()){
            uniqueCheck = false;
        } else {
            uniqueCheck = true;
        }
        LOGGER.info("release name unique check with id method end");
        return uniqueCheck;
    }

    @Override
    public ReleaseDTO updateRelease(ReleaseDTO releaseDTO) {
        LOGGER.info("update release service method start");
        ReleaseDTO releaseDTORes = new ReleaseDTO();
        if(releaseNameUniqueCheckWithId(releaseDTO)) {
            if(releaseDTO.getReleaseId() !=null){
            Optional<ReleaseMaster> releaseMasterOptional = releaseMasterRepository.findById(releaseDTO.getReleaseId());
            ReleaseMaster releaseMaster = null;
            if (releaseMasterOptional.isPresent()) {
                releaseMaster = releaseMasterOptional.get();
            } else {
                LOGGER.info("release data not available in update");
                releaseDTORes.setReleaseId(releaseDTO.getReleaseId());
                releaseDTORes.setErrorStatus(true);
                releaseDTORes.setErrorMessage("Data Not Available");
                return releaseDTORes;
            }
            releaseMaster = ReleaseMasterMapper.INSTANCE.toEntity(releaseDTO);
            if (releaseMasterRepository.save(releaseMaster) != null) {
                releaseDTORes = getReleaseDetailsById(releaseDTO.getReleaseId());
            }
        }
        }else{
            LOGGER.info("release name duplicate in update");
            releaseDTORes.setErrorStatus(true);
            releaseDTORes.setErrorMessage("Release Name is already available");
        }
        LOGGER.info("update release service method end");
        return releaseDTORes;
    }

    @Override
    public List<ReleaseDTO> getReleaseDetails() {
        return Optional.ofNullable(releaseMasterRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(ReleaseMasterMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReleaseDTO getReleaseDetailsById(Long releaseId) {
        LOGGER.info("get release details by id service method start");
        ReleaseDTO releaseDTO = new ReleaseDTO();
        if(releaseId !=null){
            Optional<ReleaseMaster> releaseMaster = releaseMasterRepository.findByReleaseId(releaseId);
            if(releaseMaster.isPresent()){
                releaseDTO = ReleaseMasterMapper.INSTANCE.toDto(releaseMaster.get());
            }
         }else{
            LOGGER.info("invalid id for getReleaseDetailsById");
            releaseDTO.setErrorMessage("Give Valid Id");
            releaseDTO.setErrorStatus(true);
        }
        LOGGER.info("get release details by id service method end");
        return releaseDTO;
    }
}
