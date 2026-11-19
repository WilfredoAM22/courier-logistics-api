package com.wilfredo.courier_logistics_api.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.wilfredo.courier_logistics_api.dto.QuoteResponse;
import com.wilfredo.courier_logistics_api.dto.QuoteRequest;
@Service
public class TariffEngineService {

    private static final BigDecimal RATE_PER_POUND = new BigDecimal("4.50");
    private static final BigDecimal TAX_FREE_THRESHOLD = new BigDecimal("200.00");
    private static final BigDecimal ITBIS_RATE = new BigDecimal("0.18");

    public QuoteResponse calculateQuote(QuoteRequest request) {
        BigDecimal billableWeight = request.weightInLbs().compareTo(BigDecimal.ONE)< 0
                ? BigDecimal.ONE
                : request.weightInLbs().setScale(0, RoundingMode.CEILING);

        BigDecimal freightCost = billableWeight.multiply(RATE_PER_POUND);

        BigDecimal fobValue = request.fobValue();
        boolean taxExempt = fobValue.compareTo(TAX_FREE_THRESHOLD) <= 0;

        BigDecimal customsDuty;
        BigDecimal selectiveTax;
        BigDecimal itbis;

        if (taxExempt){
            customsDuty = BigDecimal.ZERO;
            selectiveTax = BigDecimal.ZERO;
            itbis = BigDecimal.ZERO;
        } else {
            customsDuty = fobValue.multiply(request.category().getDutyRate());
            selectiveTax = fobValue.multiply(request.category().getSelectiveTaxRate());

            BigDecimal itbisTaxableBase = fobValue.add(customsDuty).add(selectiveTax);
            itbis = itbisTaxableBase.multiply(ITBIS_RATE);
        }

        freightCost = freightCost.setScale(2, RoundingMode.HALF_UP);
        customsDuty = customsDuty.setScale(2, RoundingMode.HALF_UP);
        selectiveTax = selectiveTax.setScale(2, RoundingMode.HALF_UP);
        itbis = itbis.setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalEstimatedCost = freightCost
                .add(customsDuty)
                .add(selectiveTax)
                .add(itbis)
                .setScale(2,RoundingMode.HALF_DOWN);
        return new QuoteResponse(
                billableWeight,
                fobValue,
                freightCost,
                customsDuty,
                selectiveTax,
                itbis,
                totalEstimatedCost,
                taxExempt
                );
}
}