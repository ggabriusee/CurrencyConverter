package lt.myserver.backend.models;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConverterReturnData {
    
    private BigDecimal convertedAmount = BigDecimal.ZERO;

    private String errMsg = "";

    private Boolean isError = false;

    public void createError(String msg){
        setErrMsg(msg);
        setIsError(true);
    }

    public void checkForError(){
        if (!isError && convertedAmount.equals(BigDecimal.ZERO)){
            createError("Šiuo metu nėra pasirinktų valiutų kursų.");
        }
    }
}
