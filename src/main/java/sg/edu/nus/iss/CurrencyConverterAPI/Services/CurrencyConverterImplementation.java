package sg.edu.nus.iss.CurrencyConverterAPI.Services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.CurrencyConverterAPI.Models.Country;

@Service
public class CurrencyConverterImplementation implements CurrencyConverterInterface{

    private String apiKey;
    private String apiUrl = "https://free.currconv.com";

    @PostConstruct
    public void init(){
        apiKey = System.getenv("OPEN_CURRENCY_MAP");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + apiKey);
    }

    @Override
    public List<Country> getAllCountries() {
        
        String url = createUrl();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> URL :" + url);

        RequestEntity<Void> req = RequestEntity
        .get(url).build();

        RestTemplate rTemplate = new RestTemplate();

        ResponseEntity<String> resp = rTemplate.exchange(req, String.class);

        List<JsonObject> jsonObjList = createJsonObj(resp);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>JSON COUNTRY: " + jsonObjList.get(1));

        List<Country> countryList = jsonObjList.stream().map(o->Country.createModel(o)).toList();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Country Model: " + countryList.get(1));

        return countryList;
    }

    @Override
    public String createUrl() {

        String newUrl = UriComponentsBuilder.fromUriString(apiUrl)
                        .path("/api/v7/countries")
                        .queryParam("apiKey", apiKey)
                        .toUriString();

        return newUrl;
    }

    @Override
    public String createUrl(String s1, String s2) {

        String newUrl = UriComponentsBuilder.fromUriString(apiUrl)
                            .path("/api/v7/convert")
                            .queryParam("q", s1 + "," +s2)
                            .queryParam("compact", "ultra")
                            .queryParam("apiKey", apiKey)
                            .toUriString();

        return newUrl;
    }

    @Override
    public List<JsonObject> createJsonObj(ResponseEntity<String> resp) {
        
        JsonObject result = null;

        try(InputStream file = new ByteArrayInputStream(resp.getBody().getBytes())){
            JsonReader reader = Json.createReader(file);
            result = reader.readObject();
        } catch (IOException ex){
            //handle ex
        }

        JsonObject countries = result.getJsonObject("results");
        Set<String> keySet =  countries.keySet();

        List<JsonObject> oList = new ArrayList<>();

        for(String k : keySet){
            oList.add(countries.getJsonObject(k));
        }


        return oList;
    }

    @Override
    public Double getConvertionRates(String s1, String s2, Double amount) {

        String url = createUrl(s1, s2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>URL2: " + url);

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate rTemplate = new RestTemplate();

        ResponseEntity<String> resp = rTemplate.exchange(req, String.class);
        
        Map<String,Double> result = getResult(resp, s1, s2);

        Double convertedAmount = convert(amount, result.get(s1));


        return convertedAmount;
    }

    @Override
    public Map<String, Double> getResult(ResponseEntity<String> resp, String s1, String s2) {
        
        JsonObject resultObj = null;

        try(InputStream file = new ByteArrayInputStream(resp.getBody().getBytes())){
            JsonReader reader = Json.createReader(file);
            resultObj = reader.readObject();
        } catch (IOException ex){
            //handle error ex
        }

        Map<String, Double> result = new HashMap<>();
        result.put(s1, resultObj.getJsonNumber(s1).doubleValue());
        result.put(s2, resultObj.getJsonNumber(s2).doubleValue());

        return result;
    }

    @Override
    public Double convert(Double d1, Double d2) {
        
        Double result = d1 * d2;

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>RESULTTTTT " + result);

        return result;


    }

    @Override
    public Country findCountryByCurrencyId(String currencyId) {
        
        List<Country> countryList = getAllCountries();

        Optional<Country> country = countryList.stream().filter(c->c.getCurrencyId().equals(currencyId)).findFirst();

        Country result = country.get();

        return result;
    }

    
}
