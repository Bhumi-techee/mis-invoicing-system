package com.codeb.mis.service;

import com.codeb.mis.dto.InvoiceRequest;
import com.codeb.mis.model.Chain;
import com.codeb.mis.model.Estimate;
import com.codeb.mis.model.Invoice;
import com.codeb.mis.repository.ChainRepository;
import com.codeb.mis.repository.EstimateRepository;
import com.codeb.mis.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InvoiceService {

    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired private EstimateRepository estimateRepository;
    @Autowired private ChainRepository chainRepository;

    public ResponseEntity<?> getAllInvoices() {
        return ResponseEntity.ok(invoiceRepository.findAll());
    }

    public ResponseEntity<?> searchInvoices(String query) {
        return ResponseEntity.ok(invoiceRepository.search(query));
    }

    public ResponseEntity<?> generateInvoice(InvoiceRequest request) {
        if (request.getEstimatedId() == null)
            return ResponseEntity.badRequest().body("Estimate ID is required.");
        if (request.getEmailId() == null || request.getEmailId().trim().isEmpty())
            return ResponseEntity.badRequest().body("Email ID cannot be empty.");

        Optional<Estimate> estimateOpt = estimateRepository.findById(request.getEstimatedId());
        if (estimateOpt.isEmpty())
            return ResponseEntity.badRequest().body("Estimate not found.");

        Optional<Chain> chainOpt = chainRepository.findById(request.getChainId());
        if (chainOpt.isEmpty())
            return ResponseEntity.badRequest().body("Chain not found.");

        // Generate unique 4-digit invoice number
        int invoiceNo;
        do {
            invoiceNo = 1000 + new Random().nextInt(9000);
        } while (invoiceRepository.findByInvoiceNo(invoiceNo).isPresent());

        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(invoiceNo);
        invoice.setEstimate(estimateOpt.get());
        invoice.setChain(chainOpt.get());
        invoice.setServiceDetails(request.getServiceDetails());
        invoice.setQty(request.getQty());
        invoice.setCostPerQty(request.getCostPerQty());
        invoice.setAmountPayable(request.getAmountPayable());
        invoice.setBalance(request.getBalance() != null ? request.getBalance() : 0f);
        invoice.setDateOfPayment(LocalDateTime.now());
        invoice.setDateOfService(request.getDateOfService() != null ?
            LocalDate.parse(request.getDateOfService()) : null);
        invoice.setDeliveryDetails(request.getDeliveryDetails());
        invoice.setEmailId(request.getEmailId().trim());
        invoiceRepository.save(invoice);
        return ResponseEntity.ok(invoice);
    }

    public ResponseEntity<?> updateInvoice(Integer id, InvoiceRequest request) {
        Optional<Invoice> existing = invoiceRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        if (request.getEmailId() == null || request.getEmailId().trim().isEmpty())
            return ResponseEntity.badRequest().body("Email ID cannot be empty.");
        Invoice invoice = existing.get();
        invoice.setEmailId(request.getEmailId().trim());
        invoiceRepository.save(invoice);
        return ResponseEntity.ok("Invoice updated successfully.");
    }

    public ResponseEntity<?> deleteInvoice(Integer id) {
        Optional<Invoice> existing = invoiceRepository.findById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();
        invoiceRepository.deleteById(id);
        return ResponseEntity.ok("Invoice deleted successfully.");
    }
}
