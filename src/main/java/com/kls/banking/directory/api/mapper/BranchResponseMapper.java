package com.kls.banking.directory.api.mapper;

import com.kls.banking.directory.api.dto.BranchResponse;
import com.kls.banking.directory.api.entity.BranchEntity;
import com.kls.banking.directory.api.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BranchResponseMapper {

    private BranchResponseMapper() {}

    public static List<BranchResponse> mapBranchesWithDistances(
        List<BranchEntity> branchEntityList,
        Double posX,
        Double posY
    ) {
        List<BranchResponse> branchResponseList = new ArrayList<>();

        if (!branchEntityList.isEmpty()) {
            return branchEntityList.stream()
                .map(branchEntity -> {
                    var branchResponse = new BranchResponse();
                    branchResponse.setAgencia(branchEntity.getBranchName());

                    Double distance = DistanceCalculator.getDistanceBetween(
                        posX,
                        posY,
                        branchEntity.getCoordinateX(),
                        branchEntity.getCoordinateY()
                    );

                    branchResponse.setDistancia(distance);

                    return branchResponse;
                })
                .collect(Collectors.toList());
        }

        return branchResponseList;
    }

}
