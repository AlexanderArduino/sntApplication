package com.energetik.app.sntapplication.service.impl;


import com.energetik.app.sntapplication.repository.GardenerRepository;
import com.energetik.app.sntapplication.entity.Gardener;
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
    public void saveGardener(Gardener gardener) {
        if (gardener == null || gardener.getUsername() == null) {
            throw new IllegalArgumentException(String.format("User or user.username must be not null"));
        }
        Optional<Gardener> optUser = gardenerRepository.findGardenerByUsername(gardener.getUsername());
        if (optUser.isEmpty()) {
            Gardener nGardener = new Gardener();
            nGardener.setId(gardener.getId());
            nGardener.setUsername(gardener.getUsername());
            nGardener.setPassword(passwordEncoder.encode(gardener.getPassword()));
            nGardener.setRoles(gardener.getRoles());
            gardenerRepository.saveAndFlush(gardener);
        } else {
            throw new IllegalArgumentException(String.format("User with username: %s was exist", gardener.getUsername()));
        }
    }

    @Override
    public void updateGardener(Gardener gardener) {
        if (gardener == null || gardener.getUsername() == null) {
            throw new IllegalArgumentException(String.format("User or user.username must be not null"));
        }
        Optional<Gardener> optUser = gardenerRepository.findGardenerByUsername(gardener.getUsername());
        if (optUser.isPresent()) {
            Gardener nGardener = new Gardener();
            nGardener.setId(gardener.getId());
            nGardener.setUsername(gardener.getUsername());
            nGardener.setPassword(passwordEncoder.encode(gardener.getPassword()));
            nGardener.setRoles(gardener.getRoles());
            gardenerRepository.saveAndFlush(gardener);
        } else {
            throw new IllegalArgumentException(String.format("User with username: %s was not exist", gardener.getUsername()));
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
