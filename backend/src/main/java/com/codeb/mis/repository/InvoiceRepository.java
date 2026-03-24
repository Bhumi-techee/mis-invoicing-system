package com.codeb.mis.repository;

import com.codeb.mis.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Optional<Invoice> findByInvoiceNo(Integer invoiceNo);
    boolean existsByEstimate_EstimatedId(Integer estimatedId);

    @Query("SELECT i FROM Invoice i WHERE " +
           "CAST(i.invoiceNo AS string) LIKE %:q% OR " +
           "CAST(i.estimate.estimatedId AS string) LIKE %:q% OR " +
           "CAST(i.chain.chainId AS string) LIKE %:q% OR " +
           "LOWER(i.chain.companyName) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Invoice> search(@Param("q") String query);
}
