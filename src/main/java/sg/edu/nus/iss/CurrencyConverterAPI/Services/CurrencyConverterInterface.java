package sg.edu.nus.iss.CurrencyConverterAPI.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.CurrencyConverterAPI.Models.Country;

public interface CurrencyConverterInterface {

    public List<Country> getAllCountries();

    public String createUrl();
    public String createUrl(String s1, String s2);

    public List<JsonObject> createJsonObj(ResponseEntity<String> resp);

    public Double getConvertionRates(String s1, String s2, Double amount); 

    public Map<String,Double> getResult(ResponseEntity<String> resp, String s1, String s2);

    public Double convert(Double d1, Double d2);

    public Country findCountryByCurrencyId(String currencyId); 
    
}
