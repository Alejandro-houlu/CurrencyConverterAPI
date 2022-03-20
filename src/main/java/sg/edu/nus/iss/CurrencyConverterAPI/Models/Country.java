package sg.edu.nus.iss.CurrencyConverterAPI.Models;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Country {
    
    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String alpha3;
    private String currencyId;
    private String currencyName;
    private String currencySymbol;
    private String id;
    private String name;

    @Override
    public String toString() {
        return "Country [currencyName=" + currencyName + ", currencySymbol=" + currencySymbol + ", name=" + name + "]";
    }

    public void print(){
        System.out.println(1);
    }

    public static Country createModel(JsonObject jsonObj){

        Country country = new Country();

        country.setAlpha3(jsonObj.getString("alpha3"));
        country.setCurrencyId(jsonObj.getString("currencyId"));
        country.setCurrencyName(jsonObj.getString("currencyName"));
        country.setCurrencySymbol(jsonObj.getString("currencySymbol"));
        country.setId(jsonObj.getString("id"));
        country.setName(jsonObj.getString("name"));

        return country;

    }

    

    

}
