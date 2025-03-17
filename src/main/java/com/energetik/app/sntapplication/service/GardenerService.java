package com.energetik.app.sntapplication.service;




import com.energetik.app.sntapplication.entity.Gardener;

import java.util.Optional;

public interface GardenerService {

    Optional<Gardener> findGardenerById(Long id);

    Optional<Gardener> findGardenerByUsername(String username);

    boolean existGardenerById(Long id);

    void saveGardener(Gardener gardener);

    void updateGardener(Gardener gardener);

    void deleteGardenerById(Long id);
}
