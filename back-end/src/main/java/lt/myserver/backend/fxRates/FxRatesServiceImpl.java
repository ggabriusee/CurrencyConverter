package lt.myserver.backend.fxRates;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import lt.lb.webservices.fxrates.CcyISO4217;
import lt.lb.webservices.fxrates.FxRateHandling;
import lt.lb.webservices.fxrates.FxRatesHandling;
import lt.myserver.backend.models.ConverterData;
import lt.myserver.backend.models.UserActions;
import lt.myserver.backend.repositories.UserActionsRepository;


@Service("fxRatesServiceImpl")
public class FxRatesServiceImpl implements FxRatesService{
    
    @Autowired
    private WebClient fxRatesApiClient;

    @Autowired
    private UserActionsRepository userActionsRepository;
    
    public FxRatesHandling getCurrentFxRates(){
        return fxRatesApiClient
                .get()
                .uri("/getCurrentFxRates?tp=LT")
                .retrieve()
                .bodyToMono(FxRatesHandling.class)
                .block();
    }

    public CcyISO4217[] getCurrencyList(){
        return CcyISO4217.values();
    }

    public BigDecimal convertCurrency(ConverterData cd){
        BigDecimal convertedAmount = BigDecimal.ZERO;
        List<FxRateHandling> rates = getCurrentFxRates().getFxRate();
        for (FxRateHandling rate: rates){
            String currentCurrencyCode = rate.getCcyAmt().get(1).getCcy().value();
            if(cd.getTo().equalsIgnoreCase(currentCurrencyCode)){
                convertedAmount = rate.getCcyAmt().get(1).getAmt().multiply(cd.getAmount());
            }
        }
        UserActions ua = createUserAction(cd, convertedAmount);       
        userActionsRepository.save(ua);
        return convertedAmount;
    }


    private UserActions createUserAction(ConverterData cd, BigDecimal convertedAmount){
        UserActions ua = new UserActions();
        ua.setAmount(cd.getAmount());
        ua.setConvertFrom(cd.getFrom());
        ua.setConvertTo(cd.getTo());
        ua.setResult(convertedAmount);
        ua.setActionDate(new Timestamp(System.currentTimeMillis()));
        return ua;
    }
    /*
    @Autowired
	private UserDataRepository repository;

    @Override
    public List<UserData> getAllUserData(){
        return repository.getAllData();
    }

    @Override
    public List<UserData> createUserDataList(UserData data[]){
        List<UserData> dataWithResults = new ArrayList<>();
		for (UserData d : data){
			d.genResult();
			dataWithResults.add(d);
			repository.saveUserData(d);
        }
        return dataWithResults;
    }

    @Override
    public List<UserData> createUserDataFromFiles(MultipartFile files[]){
        List<UserData> data = new ArrayList<>();
		for (MultipartFile file : files){
			UserData ud = this.createUserDataFromFile(file);
			data.add(ud);
			repository.saveUserData(ud);
        }
        return data;
    }

    public UserData createUserDataFromFile(MultipartFile file){
        String inputText = "";
        try{ 
            Scanner sc = new Scanner(file.getInputStream());
            while (sc.hasNextLine()) {
                inputText += sc.nextLine(); 
            }
            sc.close();
        }catch(IOException e) {  
            e.printStackTrace();  
        }
         
        return new UserData(inputText);
    }*/
    
}