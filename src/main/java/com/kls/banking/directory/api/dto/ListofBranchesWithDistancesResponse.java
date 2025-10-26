package com.kls.banking.directory.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListofBranchesWithDistancesResponse {

    private List<BranchResponse> branchesList;

}
