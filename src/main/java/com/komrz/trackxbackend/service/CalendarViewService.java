package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.LatestEventCalendarViewDTO;
import com.komrz.trackxbackend.dto.NewContractCalendarViewDTO;
import com.komrz.trackxbackend.dto.RenewalContractCalendarViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.repository.CalendarViewRepository;

@Service
public class CalendarViewService {

	@Autowired
	private CalendarViewRepository calendarViewRepository;
	
	public List<LatestEventCalendarViewDTO> latestEventCalendarView(String tenantId){
		return calendarViewRepository.latestEvent(tenantId);
	}
	
	public List<NewContractCalendarViewDTO> newContractCalendarView(String tenantId, BuySellEn buySell) {
		return calendarViewRepository.newContract(tenantId, buySell);
	}
	
	public List<RenewalContractCalendarViewDTO> renewalContractCalendarView(String tenantId, BuySellEn buySell) {
		return calendarViewRepository.renewalContract(tenantId, buySell);
	}
}
