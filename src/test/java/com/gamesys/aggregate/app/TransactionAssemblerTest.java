package com.gamesys.aggregate.app;

import com.gamesys.aggregate.app.assemblers.TransactionAssembler;
import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.Money;
import com.gamesys.aggregate.domain.model.Partner;
import com.gamesys.aggregate.domain.model.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionAssemblerTest {

    private TransactionAssembler testObj = new TransactionAssembler();

    @Test
    public void testApplyReturnsTransaction() {
        // setup
        String input = "Unlimited ltd.,GBP,200.5600";

        // act
        Transaction result = testObj.apply(input);

        // assert
        assertNotNull(result);
        assertEquals(Transaction.of(Partner.of("Unlimited ltd."), Money.of(new BigDecimal(200.5600, MathContext.DECIMAL32), Currency.of("GBP"))), result);
    }
}