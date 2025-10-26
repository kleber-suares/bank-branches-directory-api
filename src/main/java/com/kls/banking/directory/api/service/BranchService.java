package com.kls.banking.directory.api.service;

import com.kls.banking.directory.api.dto.BranchResponse;
import com.kls.banking.directory.api.entity.BranchEntity;
import com.kls.banking.directory.api.dao.BranchRepository;
import com.kls.banking.directory.api.mapper.BranchResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kls.banking.directory.api.config.CacheConfig.BRANCHES_DISTANCES_LIST_CACHE;

@Service
@Slf4j
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @CacheEvict(value = BRANCHES_DISTANCES_LIST_CACHE, allEntries = true)
    public Long saveBranch(BranchEntity branchEntity) {
        BranchEntity savedBranch =  branchRepository.save(branchEntity);
        Long branchId = savedBranch.getBranchId();

        log.info("Branch saved with id: {}", branchId);

        return branchId;
    }

    @Cacheable(BRANCHES_DISTANCES_LIST_CACHE)
    public List<BranchResponse> findBranchesWithDistances(Double posX, Double posY) {
        log.info("Fetching list of branches directly from database due to missing or expired cache.");

        List<BranchEntity> branchEntityList = branchRepository.findAll();

        List<BranchResponse> unsortedList = BranchResponseMapper.mapBranchesWithDistances(branchEntityList, posX, posY);

        return unsortedList.stream()
            .sorted(
                Comparator.comparingDouble(BranchResponse::getDistancia))
            .collect(Collectors.toList());
    }

}
