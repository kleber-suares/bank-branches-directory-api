package com.kls.references.bank.branches.directory.api.controller;

import com.kls.references.bank.branches.directory.api.dto.*;
import com.kls.references.bank.branches.directory.api.entity.BranchEntity;
import com.kls.references.bank.branches.directory.api.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
@Slf4j
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveBranchResponse> saveBranch(@RequestBody SaveBranchRequest saveBranchRequest) {
        log.info("Save branch request received with body: {}", saveBranchRequest);

        var branchEntity = new BranchEntity();
        branchEntity.setBranchName(saveBranchRequest.getBranchName());
        branchEntity.setXCoordinate(saveBranchRequest.getXCoord());
        branchEntity.setYCoordinate(saveBranchRequest.getYCoord());

        branchService.saveBranch(branchEntity);

        return ResponseEntity.ok(SaveBranchResponse.success("Branch succefully saved"));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/distance")
    public ResponseEntity<ListofBranchesWithDistancesResponse> getBranchesWithDistances(
        @RequestParam(required = false) @NotNull Double xCoord,
        @RequestParam(required = false) @NotNull Double yCoord
    ) {
        log.info("Received request to calculate distance for coordinates x={}, y={}", xCoord, yCoord);

        Coordinates coordinates =
            Coordinates.builder()
                .xCoord(xCoord)
                .yCoord(yCoord)
                .build();

        ListofBranchesWithDistancesResponse branchesWithDistancesResponse = new ListofBranchesWithDistancesResponse();

        List<BranchResponse> branchResponseList = branchService.findBranchesWithDistances(coordinates);
        branchesWithDistancesResponse.setBranchesList(branchResponseList);

        return ResponseEntity.ok(branchesWithDistancesResponse);
    }

    @GetMapping("/health")
    public String getAppHealth() {
        return "Healthy! All up and running!";
    }

}
