package com.kls.references.bank.branches.directory.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListofBranchesWithDistancesResponse {

    private List<BranchResponse> branchesList;

}
