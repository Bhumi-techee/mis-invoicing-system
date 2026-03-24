package com.codeb.mis.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estimates")
@Data
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer estimatedId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chain_id", nullable = false)
    private Chain chain;

    @Column(nullable = false, length = 50)
    private String groupName;

    @Column(nullable = false, length = 50)
    private String brandName;

    @Column(nullable = false, length = 50)
    private String zoneName;

    @Column(nullable = false, length = 100)
    private String service;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Float costPerUnit;

    @Column(nullable = false)
    private Float totalCost;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(length = 100)
    private String deliveryDetails;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
