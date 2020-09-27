package lt.myserver.backend.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConverterReturnDataTest {
    
    @Test
    public void createErrorTest() {
        ConverterReturnData crd = new ConverterReturnData();
        crd.createError("Test Error");
        assertTrue(crd.getIsError());
    }
  
    @Test
    public void checkForErrorTest() {
        ConverterReturnData crd = new ConverterReturnData();
        crd.checkForError();
        assertTrue(crd.getIsError());
    }
}
