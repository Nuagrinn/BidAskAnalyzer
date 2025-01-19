package ru.keller.bidaskanalyzer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "elvls")
@Getter
@Setter
@NoArgsConstructor
public class ElvlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 12, unique = true)
    private String isin;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal elvl;

    @OneToOne(mappedBy = "elvl", cascade = CascadeType.ALL)
    private QuoteEntity quote;
}
