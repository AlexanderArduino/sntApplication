package com.energetik.app.sntapplication.service.impl;

import com.energetik.app.sntapplication.entity.Debtors;
import com.energetik.app.sntapplication.repository.DebtorsRepository;
import com.energetik.app.sntapplication.repository.GardenerRepository;
import com.energetik.app.sntapplication.service.DebtorsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DebtorsServiceImpl implements DebtorsService {

    private final DebtorsRepository debtorsRepository;
    private final GardenerRepository gardenerRepository;

    public DebtorsServiceImpl(DebtorsRepository debtorsRepository,
                              GardenerRepository gardenerRepository) {
        this.debtorsRepository = debtorsRepository;
        this.gardenerRepository = gardenerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Debtors> findDebtorsById(Long id) {
        return debtorsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Debtors> findDebtorsByGardenerId(Long id) {
        return debtorsRepository.findByGardenerId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsDebtorsById(Long id) {
        return debtorsRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsDebtorsByGardenerId(Long id) {
        return debtorsRepository.existsDebtorsByGardenerId(id);
    }

    @Override
    public Debtors saveDebtors(Debtors debtors) {
        if (debtors == null) {
            throw new IllegalArgumentException(
                    String.format("Debtors must be not null")
            );
        }
        return debtorsRepository.save(debtors);
    }

    @Override
    public Debtors updateDebtors(Long debtorsId, Debtors newDebtors) {
        if (debtorsId == null || newDebtors == null) {
            throw new IllegalArgumentException(
                    String.format("DebtorsId or newDebtors must not be null")
            );
        }
        Optional<Debtors> optDebtors = findDebtorsById(debtorsId);
        if (optDebtors.isPresent()) {
            Debtors debtorsForSave = optDebtors.get();
            debtorsForSave.setOn_date(newDebtors.getOn_date());
            debtorsForSave.setBalance_member_pay(newDebtors.getBalance_member_pay());
            debtorsForSave.setBalance_electricity_pay(newDebtors.getBalance_electricity_pay());
            debtorsForSave.setGardener(newDebtors.getGardener());
            debtorsForSave.setTo_exclusion(newDebtors.getTo_exclusion());
            debtorsForSave.setExclude(newDebtors.getExclude());
            debtorsForSave.setNote(newDebtors.getNote());
            return debtorsRepository.save(debtorsForSave);
        }
        return null;
    }

    @Override
    public void deleteDebtors(Long id) {
        if (debtorsRepository.existsById(id)) {
            debtorsRepository.deleteById(id);
        }
    }
}
