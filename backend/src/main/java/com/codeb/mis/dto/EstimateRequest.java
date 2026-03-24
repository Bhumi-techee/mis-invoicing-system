package com.codeb.mis.dto;

import lombok.Data;

@Data
public class EstimateRequest {
    private Integer chainId;
    private String groupName;
    private String brandName;
    private String zoneName;
    private String service;
    private Integer qty;
    private Float costPerUnit;
    private Float totalCost;
    private String deliveryDate;
    private String deliveryDetails;
}
