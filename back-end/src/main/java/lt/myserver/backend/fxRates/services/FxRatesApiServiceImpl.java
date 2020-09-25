package lt.myserver.backend.fxRates.services;

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
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;
import lt.myserver.backend.fxRates.entities.EuroExcRate;
import lt.myserver.backend.fxRates.repositories.EuroExcRateRepository;
import lt.myserver.backend.userAction.UserActionServiceImpl;


@Service("fxRatesServiceImpl")
public class FxRatesApiServiceImpl implements FxRatesService{
    
    @Autowired
    private WebClient fxRatesApiClient;

    private FxRatesHandling fxRatesHandling;

    
    
    public int checkgetCurrentFxRates(){
        fxRatesHandling = fxRatesApiClient
                .get()
                .uri("/getCurrentFxRates?tp=EU")
                .retrieve()
                .bodyToMono(FxRatesHandling.class)
                .block();
        return fxRatesHandling != null ? fxRatesHandling.getFxRate().size() : 0; 
    }

    public FxRatesHandling getCurrentFxRates(){
        return fxRatesApiClient
                .get()
                .uri("/getCurrentFxRates?tp=EU")
                .retrieve()
                .bodyToMono(FxRatesHandling.class)
                .block(); 
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