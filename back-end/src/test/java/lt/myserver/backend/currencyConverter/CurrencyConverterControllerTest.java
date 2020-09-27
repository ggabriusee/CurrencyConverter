package lt.myserver.backend.currencyConverter;

import static org.mockito.Mockito.when;

//import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import lt.lb.webservices.fxrates.CcyISO4217;
import lt.myserver.backend.currencyConverter.controllers.CurrencyConverterController;
import lt.myserver.backend.currencyConverter.services.CurrencyConverterServiceImpl;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;

import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyConverterController.class)
public class CurrencyConverterControllerTest {

    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private CurrencyConverterServiceImpl serviceMock;

    @Test
    public void getRequestReturnsCcyArray() throws Exception {
    
        CcyISO4217[] currencyList = {CcyISO4217.AUD};
    
        when(serviceMock.getCurrencyList()).thenReturn(currencyList);
    
        mvc.perform(get("/getCurrencyList")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0]", is(CcyISO4217.AUD.name())));
    }

    @Test
    public void postRequestReturnsCorrectResponse() throws Exception {
    
        ConverterReturnData crd = new ConverterReturnData();
        //String jsonCrd = new ObjectMapper().writeValueAsString(crd);

        when(serviceMock.convert(any(ConverterUserData.class))).thenReturn(crd);

        String request = "{\"amount\": \"1\",\"from\": \"EUR\",\"to\": \"AUD\",\"type\": \"EU\",\"date\": \"2020-09-25\"}";
        mvc.perform(post("/convertCurrency")
        .content(request)
        .contentType(MediaType.APPLICATION_JSON))   
        .andExpect(status().isOk());
    }
    
}