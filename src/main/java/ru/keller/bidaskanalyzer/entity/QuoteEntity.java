package ru.keller.bidaskanalyzer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@NoArgsConstructor
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 12)
    private String isin;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal bid;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal ask;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "elvl_id", referencedColumnName = "id")
    private ElvlEntity elvl;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
