package com.komrz.trackxbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ContractBillInfoCreateDTO;
import com.komrz.trackxbackend.exception.BadRequestException;
import com.komrz.trackxbackend.model.ContractBillInfoPOJO;
import com.komrz.trackxbackend.model.CurrExchangeRate;
import com.komrz.trackxbackend.repository.ContractBillInfoRepository;
import com.komrz.trackxbackend.repository.CurrExchangeRateRepository;

@Service
public class ContractBillInfoService {

	@Autowired
	private ContractBillInfoRepository contractBillInfoRepository;
	
	@Autowired
	private CurrExchangeRateRepository currExchangeRateRepository;
	
	@Autowired
	private TenantDetailsService tenantDetailsService;
	
	private String createContractBillInfo(ContractBillInfoCreateDTO contractBillInfoCreateDTO, String contractId, String prefCurr) {
		ContractBillInfoPOJO newContractBillInfo = new ContractBillInfoPOJO();
		newContractBillInfo.setContractId(contractId);
		newContractBillInfo.setBillDate(contractBillInfoCreateDTO.getBillDate());
		newContractBillInfo.setCurrency(contractBillInfoCreateDTO.getCurrency());
		newContractBillInfo.setBillAmount(contractBillInfoCreateDTO.getBillAmount());
		newContractBillInfo.setPaymentType(contractBillInfoCreateDTO.getPaymentType());
		newContractBillInfo.setNote(contractBillInfoCreateDTO.getNote());
		newContractBillInfo.setBillAmntPrefCurr(getPrefCurr(newContractBillInfo.getBillAmount(), prefCurr, newContractBillInfo.getCurrency()));
		return contractBillInfoRepository.save(newContractBillInfo).getId();
	}
	
	public List<String> createContractBillList( List<ContractBillInfoCreateDTO> listContractBillInfo , String contractId, String tenantId){
		ListIterator<ContractBillInfoCreateDTO> contractBillInfoIterator = listContractBillInfo.listIterator();
		List<String> listOfBillInfoId = new ArrayList<String>();
		while(contractBillInfoIterator.hasNext()) {
			listOfBillInfoId.add( createContractBillInfo(contractBillInfoIterator.next(), contractId, tenantDetailsService.getPrefCurrency(tenantId)) );
		}
		return listOfBillInfoId;
	}
 	
	public List<ContractBillInfoPOJO> fetchContractBillInfoContractId(String contractId) {
		return contractBillInfoRepository.findByContractId(contractId);
	}
	
	private double getPrefCurr(int billAmount, String prefCurr, String billCurr) {
		System.out.println(prefCurr + " : " + billCurr);
		if(billCurr.equals(prefCurr)) {
			return billAmount;
		}
		CurrExchangeRate currExchangeRate = currExchangeRateRepository.findByFromCurrCodeAndToCurrCode(prefCurr, billCurr);
		if(currExchangeRate == null) {
			currExchangeRate = currExchangeRateRepository.findByFromCurrCodeAndToCurrCode(billCurr, prefCurr);
		}
		if(currExchangeRate == null) {
			throw new BadRequestException("Please select a proper currecny", "Selected Currenct: " + billCurr);
		}
		System.out.println(currExchangeRate);
		float conversionRate = 0;
		if(billCurr.equals(currExchangeRate.getFromCurrCode())) {
			conversionRate = currExchangeRate.getConversionValue();
		}else {
			conversionRate = currExchangeRate.getInverseConversionValue();
		}
		double convertedAmount = billAmount * conversionRate;
		convertedAmount = (double)Math.round(convertedAmount * 100) /100;
		return convertedAmount;
	}
}
