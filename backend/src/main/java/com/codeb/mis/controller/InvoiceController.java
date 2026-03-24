package com.codeb.mis.controller;

import com.codeb.mis.dto.InvoiceRequest;
import com.codeb.mis.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // GET /api/invoices
    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    // GET /api/invoices/search?q=
    @GetMapping("/search")
    public ResponseEntity<?> searchInvoices(@RequestParam String q) {
        return invoiceService.searchInvoices(q);
    }

    // POST /api/invoices
    @PostMapping
    public ResponseEntity<?> generateInvoice(@RequestBody InvoiceRequest request) {
        return invoiceService.generateInvoice(request);
    }

    // PUT /api/invoices/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Integer id,
                                            @RequestBody InvoiceRequest request) {
        return invoiceService.updateInvoice(id, request);
    }

    // DELETE /api/invoices/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Integer id) {
        return invoiceService.deleteInvoice(id);
    }
}
