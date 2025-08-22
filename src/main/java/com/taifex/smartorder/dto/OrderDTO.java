package com.taifex.smartorder.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String productName;
    private Integer amount;
    private Double price;
    private Double total; //額外計算欄位
}
