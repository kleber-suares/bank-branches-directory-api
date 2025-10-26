package com.kls.banking.directory.api.controller;

import com.kls.banking.directory.api.dto.BranchResponse;
import com.kls.banking.directory.api.dto.ListofBranchesWithDistancesResponse;
import com.kls.banking.directory.api.dto.SaveBranchResponse;
import com.kls.banking.directory.api.dto.SaveBranchRequest;
import com.kls.banking.directory.api.entity.BranchEntity;
import com.kls.banking.directory.api.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/desafio")
@Slf4j
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping(value = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaveBranchResponse> getBranchCoordinates(@RequestBody SaveBranchRequest saveBranchRequest) {
        log.info("Save branch request received with body: {}", saveBranchRequest);

        var branchEntity = new BranchEntity();
        branchEntity.setBranchName(saveBranchRequest.getBranchName());
        branchEntity.setCoordinateX(saveBranchRequest.getPosX());
        branchEntity.setCoordinateY(saveBranchRequest.getPosX());

        branchService.saveBranch(branchEntity);

        return ResponseEntity.ok(SaveBranchResponse.success("Agência cadastrada com sucesso"));
    }

    @GetMapping("/distancia")
    public ResponseEntity<ListofBranchesWithDistancesResponse> getBranchesWithDistances(
        @RequestParam(required = false) Double posX,
        @RequestParam(required = false) Double posY
    ) {
        log.info("Received request to calculate distance for coordinates x={}, y={}", posX, posY);

        ListofBranchesWithDistancesResponse branchesWithDistancesResponse = new ListofBranchesWithDistancesResponse();

        List<BranchResponse> branchResponseList = branchService.findBranchesWithDistances(posX, posY);
        branchesWithDistancesResponse.setBranchesList(branchResponseList);

        return ResponseEntity.ok(branchesWithDistancesResponse);
    }

}
