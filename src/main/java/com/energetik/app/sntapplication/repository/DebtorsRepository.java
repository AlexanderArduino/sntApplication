package com.energetik.app.sntapplication.repository;

import com.energetik.app.sntapplication.entity.Debtors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebtorsRepository extends JpaRepository<Debtors, Long> {
    Optional<Debtors> findByGardenerId(Long id);

    boolean existsDebtorsByGardenerId(Long id);
}
