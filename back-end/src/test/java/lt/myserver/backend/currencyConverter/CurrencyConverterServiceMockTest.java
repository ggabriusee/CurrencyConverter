package lt.myserver.backend.currencyConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import lt.myserver.backend.currencyConverter.entities.ExcRate;
import lt.myserver.backend.currencyConverter.repositories.ExcRateRepository;
import lt.myserver.backend.currencyConverter.services.ConverterUserActionServiceImpl;
import lt.myserver.backend.currencyConverter.services.CurrencyConverterServiceImpl;
import lt.myserver.backend.currencyConverter.services.FxRatesApiServiceImpl;
import lt.myserver.backend.models.ConverterReturnData;
import lt.myserver.backend.models.ConverterUserData;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConverterServiceMockTest {
    
    @Mock
    private ExcRateRepository repositoryMock;

    @InjectMocks
    private FxRatesApiServiceImpl fxRatesApiMock;

    @Mock
    private ConverterUserActionServiceImpl userActionServiceMock;

    @InjectMocks
    private CurrencyConverterServiceImpl ccServiceImplMock;

    @Test
    public void completeRequestTest() {
        ConverterUserData cud = new ConverterUserData();
        ConverterReturnData crd = new ConverterReturnData();
        
        ccServiceImplMock.completeRequest(cud, crd);
        verify(userActionServiceMock, times(1)).registerUserAction(cud, crd.getConvertedAmount());
    }

    @Test
    public void convertByDateFoundInDbNoBankReqTest() {
        ConverterUserData cud = new ConverterUserData();
        cud.setAmount(BigDecimal.ONE);
        ConverterReturnData crd = new ConverterReturnData();
        ExcRate er = new ExcRate();
        er.setExcRate(BigDecimal.TEN);
        
        when(repositoryMock.searchByExcRateDate(cud.getFrom(), cud.getTo(), cud.getType(), cud.getDate()))
        .thenReturn(er);
        
        ccServiceImplMock.convertByDate(cud, crd);
        assertEquals(BigDecimal.TEN, crd.getConvertedAmount());
    }

    @Test
    public void convertToCurrentFoundInDbNoBankReqTest() {
        /*
        ConverterUserData cud = new ConverterUserData();
        cud.setAmount(BigDecimal.ONE);
        ConverterReturnData crd = new ConverterReturnData();
        Date today = new Date(System.currentTimeMillis());
        ExcRate er1 = new ExcRate();
        er1.setExcRate(BigDecimal.ZERO);
        er1.setExcRateDate(today);
        ExcRate er2 = new ExcRate();
        er1.setExcRateDate(today);
        List<ExcRate> erList = Stream.of(er1, er2).collect(Collectors.toList());

        when(repositoryMock.searchByDateAdded(cud.getFrom(), cud.getTo(), cud.getType(), today))
        .thenReturn(erList);

        when(ccServiceImplMock.findNewestExcRate(erList)).thenReturn(er1);
        
        ccServiceImplMock.convertToCurrent(cud, crd, today);
        assertEquals(BigDecimal.ZERO, crd.getConvertedAmount());*/
    }
}
