package ru.keller.bidaskanalyzer.repository;

import ru.keller.bidaskanalyzer.entity.ElvlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElvlRepository extends JpaRepository<ElvlEntity, Long> {
    Optional<ElvlEntity> findByIsin(String isin);

}