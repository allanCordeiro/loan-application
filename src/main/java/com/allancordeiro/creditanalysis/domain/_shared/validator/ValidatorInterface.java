package com.allancordeiro.creditanalysis.domain._shared.validator;

public interface ValidatorInterface<T> {
    void Validate(T entity) throws Exception;
}



