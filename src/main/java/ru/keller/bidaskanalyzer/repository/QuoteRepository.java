package ru.keller.bidaskanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.keller.bidaskanalyzer.entity.QuoteEntity;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {

    Optional<QuoteEntity> findByIsin(String isin);
}
