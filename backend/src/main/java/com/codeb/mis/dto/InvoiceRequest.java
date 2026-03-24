package com.codeb.mis.dto;

import lombok.Data;

@Data
public class InvoiceRequest {
    private Integer estimatedId;
    private Integer chainId;
    private String serviceDetails;
    private Integer qty;
    private Float costPerQty;
    private Float amountPayable;
    private Float amountPaid;
    private Float balance;
    private String dateOfService;
    private String deliveryDetails;
    private String emailId;
}
