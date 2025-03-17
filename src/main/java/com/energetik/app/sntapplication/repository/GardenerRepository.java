package com.energetik.app.sntapplication.repository;


import com.energetik.app.sntapplication.entity.Gardener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Long> {

    @Query("""
            SELECT g FROM Gardener g JOIN FETCH g.roles WHERE g.username = :username
            """)
    Optional<Gardener> findGardenerByUsername(@Param("username") String username);

}
