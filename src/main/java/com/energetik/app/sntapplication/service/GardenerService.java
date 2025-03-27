package com.energetik.app.sntapplication.service;




import com.energetik.app.sntapplication.entity.Gardener;

import java.util.Optional;

public interface GardenerService {

    Optional<Gardener> findGardenerById(Long id);

    Optional<Gardener> findGardenerByUsername(String username);

    boolean existGardenerById(Long id);

    Gardener saveGardener(Gardener gardener);

    Gardener updateGardener(String username, Gardener gardener);

    void deleteGardenerById(Long id);
}
