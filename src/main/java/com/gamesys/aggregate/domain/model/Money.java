package com.gamesys.aggregate.domain.model;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;


@Data
@RequiredArgsConstructor(staticName = "of")
public final class Money implements Serializable {

    @NonNull
    private final BigDecimal amount;

    @NonNull
    private final Currency currency;

    public Money add(Money money) {
        Preconditions.checkArgument(this.currency.equals(money.currency), "You cannot add different currencies. This currency: " + currency + ", toCurrency: " + money.getCurrency());
        return new Money(this.getAmount().add(money.getAmount(), MathContext.DECIMAL32), this.currency);
    }

    public Money exchange(Currency toCurrency, BigDecimal exchangeRate) {
        Money result = this;
        if (!currency.equals(toCurrency)) {
            result = new Money(amount.multiply(exchangeRate, MathContext.DECIMAL32), toCurrency);
        }
        return result;
    }
}