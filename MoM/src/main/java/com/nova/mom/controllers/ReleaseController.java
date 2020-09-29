package com.nova.mom.controllers;
import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.services.ReleaseService;
import com.nova.mom.utils.MoMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = MoMConstants.CONFIG_PATH_VERSION)
public class ReleaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseController.class);

    @Autowired
    ReleaseService releaseService;

    @PostMapping(MoMConstants.RELEASE_DETAILS)
    public ResponseEntity<ReleaseDTO> createRelease(@RequestBody ReleaseDTO releaseDTO) {
        LOGGER.info("create release method start");
        ReleaseDTO responseDto = releaseService.createRelease(releaseDTO);
        LOGGER.info("create release method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping(MoMConstants.RELEASE_DETAILS)
    public ResponseEntity<ReleaseDTO> updateRelease(@RequestBody ReleaseDTO releaseDTO) {
        LOGGER.info("update release method start");
        ReleaseDTO responseDto = releaseService.updateRelease(releaseDTO);
        LOGGER.info("update release method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.RELEASE_DETAILS)
    public ResponseEntity<List<ReleaseDTO>> getReleaseDetails() {
        LOGGER.info("get release method start");
        List<ReleaseDTO> responseDto = releaseService.getReleaseDetails();
        LOGGER.info("get release method end");
        return ResponseEntity.ok().body(responseDto);
    }

//    @GetMapping(value = MoMConstants.RELEASE_DETAILS + MoMConstants.RELEASE_ID)
//    public ResponseEntity<ReleaseDTO> getReleaseDetailsById(@PathVariable Long releaseId) {
//        LOGGER.info("get by id release method start");
//        ReleaseDTO responseDto = releaseService.getReleaseDetailsById(releaseId);
//        LOGGER.info("get by id release method end");
//        return ResponseEntity.ok().body(responseDto);
//    }

    @GetMapping(MoMConstants.RELEASE_DETAILS + MoMConstants.RELEASE_ID)
    public ResponseEntity<ReleaseDTO> getReleaseDetailsById(@PathVariable Long releaseId) {
        LOGGER.info("get by id release method start");
        ReleaseDTO responseDto = releaseService.getReleaseDetailsById(releaseId);
        LOGGER.info("get by id release method end");
        return ResponseEntity.ok().body(responseDto);
    }

}
