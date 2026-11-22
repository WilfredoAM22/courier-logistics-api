package com.wilfredo.courier_logistics_api.service;
import com.wilfredo.courier_logistics_api.domain.enums.ProductCategory;
import com.wilfredo.courier_logistics_api.dto.QuoteRequest;
import com.wilfredo.courier_logistics_api.dto.QuoteResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;

public class TariffEngineServiceTest {
    private final TariffEngineService tariffEngineService = new TariffEngineService();
    private void assertBigDecimalEquals(String expected, BigDecimal actual){
        assertNotNull(actual, "El valor BigDecimal no debe ser nulo");
        assertEquals(0, new BigDecimal(expected).compareTo(actual),
                () -> "Se esperaba" + expected + "pero se obtuvo" + actual);
    }


    @Test
    void shouldCalculateTaxExemptQuoteWhenFobIsUnderOrEqual200(){
        QuoteRequest request = new QuoteRequest(
                new BigDecimal("2.3"),
                new BigDecimal("199.99"),
                ProductCategory.ELECTRONICS
        );
        QuoteResponse response = tariffEngineService.calculateQuote(request);
        assertTrue(response.taxExempt());
        assertBigDecimalEquals("3", response.billableWeight());
        assertBigDecimalEquals("13.50", response.freightCost());
        assertBigDecimalEquals("0.00", response.customsDuty());
        assertBigDecimalEquals("0.00", response.selectiveTax());
        assertBigDecimalEquals("0.00", response.itbis());

    }
    @Test
    void shouldCalculateCascadingTaxesWhenFobExceeds200() {
        QuoteRequest request = new QuoteRequest(
                new BigDecimal("2.3"),
                new BigDecimal("200.01"),
                ProductCategory.ELECTRONICS
        );
        QuoteResponse response = tariffEngineService.calculateQuote(request);

        assertFalse(response.taxExempt());
        assertBigDecimalEquals("40.00", response.customsDuty());
        assertBigDecimalEquals("20.00", response.selectiveTax());
        assertBigDecimalEquals("46.80", response.itbis());
        assertBigDecimalEquals("120.30", response.totalEstimatedCost());
    }
    @Test
    void shouldApplyZeroDutyForBooksEvenAbove200() {
        QuoteRequest request = new QuoteRequest(
                new BigDecimal("1.0"),
                new BigDecimal("350.00"),
                ProductCategory.BOOKS
        );
        QuoteResponse response = tariffEngineService.calculateQuote(request);
        assertFalse(response.taxExempt());
        assertBigDecimalEquals("0.00", response.customsDuty());
        assertBigDecimalEquals("0.00", response.selectiveTax());
        assertBigDecimalEquals("4.50", response.freightCost());
        assertBigDecimalEquals("63.00", response.itbis());
        assertBigDecimalEquals("67.50", response.totalEstimatedCost());
    }


    }
