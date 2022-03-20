package sg.edu.nus.iss.CurrencyConverterAPI.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.CurrencyConverterAPI.Models.Country;
import sg.edu.nus.iss.CurrencyConverterAPI.Services.CurrencyConverterInterface;

@Controller
@RequestMapping("/")
public class CurrencyConverterController {


    @Autowired
    CurrencyConverterInterface cService;

    @GetMapping("/")
    public String getCountryList(Model model){

        List<Country> countryList = cService.getAllCountries();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>County List: " + countryList.get(2));

        model.addAttribute("countryList", countryList);

        return "index";

    }

    @GetMapping("/rates")
    public String getRates(@RequestParam String from,
        @RequestParam String to,
        @RequestParam String amount, 
        Model model){

            System.out.println(from.toString());
            System.out.println(to.toString());

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + from + " " + to + " " + amount);

            String searchParam1 = String.join("_", from, to);
            String searchParam2 =  String.join("_", to,from);

            Double amountForConversion = Double.valueOf(amount);

            Double result = cService.getConvertionRates(searchParam1,searchParam2, amountForConversion);
            Country fromCountry = cService.findCountryByCurrencyId(from);
            Country tCountry = cService.findCountryByCurrencyId(to);

            model.addAttribute("fromCountry", fromCountry);
            model.addAttribute("toCountry", tCountry);
            model.addAttribute("result", result);
            model.addAttribute("amount", amount);

            System.out.println(fromCountry + "\n" + tCountry + "\n" + result);
            
            return "result";        
    
    
        }
    
}
