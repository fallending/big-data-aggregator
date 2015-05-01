package com.gamesys.aggregate.domain.model;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public final class Currency implements Serializable {
    private final String code;
}

