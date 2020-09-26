package lt.myserver.backend.fxRates.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.lb.webservices.fxrates.CcyISO4217;
import lt.lb.webservices.fxrates.FxRateHandling;
import lt.lb.webservices.fxrates.FxRateTypeHandling;
import lt.myserver.backend.fxRates.services.CurrencyConverterServiceImpl;
import lt.myserver.backend.fxRates.services.FxRatesApiServiceImpl;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class FxRatesController {

	@Autowired
	private CurrencyConverterServiceImpl converterService;

    @GetMapping(path = "/getCurrencyList", produces = "application/json")
	public CcyISO4217[] getCurrencyList() {
		return converterService.getCurrencyList();
	}

	@GetMapping(path = "/getFxRateTypeList", produces = "application/json")
	public FxRateTypeHandling[] getFxRateTypeList() {
		return converterService.getExcRateTypes();
	}
    
    @PostMapping(path = "/convertCurrency", consumes = "application/json", produces = "application/json")
	public ConverterReturnData convertCurrency(@RequestBody ConverterUserData converterUserData) {
		return converterService.convert(converterUserData);
	}

}