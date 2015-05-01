package com.gamesys.aggregate.domain;

public interface OutputService<T> {
    void toOutput(T result);
}
