package com.wilfredo.courier_logistics_api.domain.enums;

import java.math.BigDecimal;
public enum ProductCategory {
    COMPUTERS_PHONES(BigDecimal.ZERO, BigDecimal.ZERO),
    ELECTRONICS(new BigDecimal("0.20"), new BigDecimal("0.10")),
    CLOTHING(new BigDecimal("0.20"), BigDecimal.ZERO),
    BOOKS(BigDecimal.ZERO, BigDecimal.ZERO),
    SUPPLEMENTS(new BigDecimal("0.10"), BigDecimal.ZERO),
    TOYS(new BigDecimal("0.20"), BigDecimal.ZERO),
    DOCUMENTS(BigDecimal.ZERO, BigDecimal.ZERO),
    ACCESSORIES(new BigDecimal("0.20"), BigDecimal.ZERO),
    GENERAL(new BigDecimal("0.20"), BigDecimal.ZERO);


    private final BigDecimal dutyRate;
    private final BigDecimal selectiveTaxRate;
    ProductCategory(BigDecimal dutyRate, BigDecimal selectiveTaxRate){
   this.dutyRate = dutyRate;
   this.selectiveTaxRate = selectiveTaxRate;
   }

   public BigDecimal getDutyRate(){
       return this.getDutyRate();
   }


   public BigDecimal getSelectiveTaxRate(){
       return  this.getSelectiveTaxRate();
   }
}