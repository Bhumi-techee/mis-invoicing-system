package com.codeb.mis.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer invoiceNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estimated_id", nullable = false)
    private Estimate estimate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chain_id", nullable = false)
    private Chain chain;

    @Column(nullable = false, length = 50)
    private String serviceDetails;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Float costPerQty;

    @Column(nullable = false)
    private Float amountPayable;

    @Column(nullable = false)
    private Float balance;

    @Column
    private LocalDateTime dateOfPayment;

    @Column
    private LocalDate dateOfService;

    @Column(length = 100)
    private String deliveryDetails;

    @Column(length = 50)
    private String emailId;
}
