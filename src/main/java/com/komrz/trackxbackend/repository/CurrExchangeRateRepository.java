package com.komrz.trackxbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.CurrExchangeRate;

@Repository
public interface CurrExchangeRateRepository extends JpaRepository<CurrExchangeRate, String> {

	CurrExchangeRate findByFromCurrCodeAndToCurrCode(String prefCurr, String billCurr);

}
