package com.kls.references.bank.branches.directory.api.repository;

import com.kls.references.bank.branches.directory.api.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {

}
