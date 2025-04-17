package com.energetik.app.sntapplication.service.impl;


import com.energetik.app.sntapplication.entity.Gardener;
import com.energetik.app.sntapplication.repository.GardenerRepository;
import com.energetik.app.sntapplication.service.GardenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GardenerServiceImpl implements GardenerService {

    private final GardenerRepository gardenerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GardenerServiceImpl(GardenerRepository gardenerRepository, PasswordEncoder passwordEncoder) {
        this.gardenerRepository = gardenerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gardener> findGardenerById(Long id) {
        return gardenerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Gardener> findGardenerByUsername(String username) {
        return gardenerRepository.findGardenerByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existGardenerById(Long id) {
        return gardenerRepository.existsById(id);
    }

    @Override
    public Gardener saveGardener(Gardener gardener) {
        if (gardener == null || gardener.getUsername() == null) {
            throw new IllegalArgumentException(String.format("User or user.username must be not null"));
        }
        Optional<Gardener> optUser = gardenerRepository.findGardenerByUsername(gardener.getUsername());
        if (optUser.isEmpty()) {
            gardener.setPassword(passwordEncoder.encode(gardener.getPassword()));
            gardenerRepository.save(gardener);
            return gardener;
        } else {
            throw new IllegalArgumentException(String.format("User with username: %s was exist", gardener.getUsername()));
        }
    }

    @Override
    public Gardener updateGardener(String username, Gardener gardener) {
        if (gardener == null || username == null) {
            throw new IllegalArgumentException(String.format("Gardener or username must be not null"));
        }
        Optional<Gardener> optUser = gardenerRepository.findGardenerByUsername(username);
        if (optUser.isPresent()) {
            Gardener nGardener = optUser.get();
            nGardener.setUsername(gardener.getUsername());
            nGardener.setPassword(passwordEncoder.encode(gardener.getPassword()));
            nGardener.setRoles(gardener.getRoles());
            nGardener.setFirstName(gardener.getFirstName());
            nGardener.setMiddleName(gardener.getMiddleName());
            nGardener.setLastName(gardener.getLastName());
            nGardener.setPhone(gardener.getPhone());
            nGardener.setEmail(gardener.getEmail());
            nGardener.setMember(gardener.isMember());
            nGardener.setDateEnter(gardener.getDateEnter());
            nGardener.setDocumentEnter(gardener.getDocumentEnter());
            nGardener.setDateOut(gardener.getDateOut());
            nGardener.setReasonOut(gardener.getReasonOut());
            nGardener.setPassport(gardener.getPassport());
            nGardener.setAddressRegistration(gardener.getAddressRegistration());
            nGardener.setAddressResidential(gardener.getAddressResidential());
            nGardener.setNote(gardener.getNote());
            nGardener.setArchive(gardener.isArchive());
            nGardener.setConversations(gardener.getConversations());
            nGardener.setPayments(gardener.getPayments());
            return gardenerRepository.save(nGardener);
        } else {
            throw new IllegalArgumentException(String.format("Gardener with username: %s was not exist", username));
        }
    }

    @Override
    public void deleteGardenerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("User id must be not null"));
        }
        Optional<Gardener> optUser = gardenerRepository.findById(id);
        if (optUser.isPresent()) {
            gardenerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("User with id:%d was not exist", id));
        }
    }
}
