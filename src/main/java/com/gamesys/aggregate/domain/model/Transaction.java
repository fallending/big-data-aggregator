package com.gamesys.aggregate.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@RequiredArgsConstructor(staticName = "of")
public final class Transaction implements Serializable {
    @NonNull
    private Partner partner;
    @NonNull
    private Money money;
}