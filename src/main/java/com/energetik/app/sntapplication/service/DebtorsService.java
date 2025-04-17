package com.energetik.app.sntapplication.service;


import com.energetik.app.sntapplication.entity.Debtors;

import java.util.Optional;

public interface DebtorsService {

    Optional<Debtors> findDebtorsById(Long id);

    Optional<Debtors> findDebtorsByGardenerId(Long id);

    boolean existsDebtorsById(Long id);

    boolean existsDebtorsByGardenerId(Long id);

    Debtors saveDebtors(Debtors debtors);

    Debtors updateDebtors(Long debtorsId, Debtors newDebtors);

    void deleteDebtors(Long id);
}
