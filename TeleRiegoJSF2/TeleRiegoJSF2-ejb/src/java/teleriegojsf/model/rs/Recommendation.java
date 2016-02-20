/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teleriegojsf.model.rs;

import java.math.BigInteger;
import java.util.Date;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author macbookpro
 */
public class Recommendation {
    
    public boolean suggestIrrigation(BigInteger humidity, Date lastDateIrrigation, BigInteger wMAvailable, JsonArray weatherPredictions){     
        
        if(!thereIsWaterAvailable(wMAvailable)){
            return false;
        }
        else if(thereIsNoHumidity(humidity) && thereIsRain(weatherPredictions) < 5){
            return true;
        }        
        else if(thereIsABitOfHumidity(humidity)  && calculateDifOnMonths(lastDateIrrigation) > 1 && thereIsRain(weatherPredictions) < 3){
            return true;
        }      
        else if(thereIsEnoughtHumidity(humidity) && calculateDifOnMonths(lastDateIrrigation) > 3 && thereIsRain(weatherPredictions) < 1){
            return true;
        }     
        else if(thereIsFullHumidity(humidity) && calculateDifOnMonths(lastDateIrrigation) > 7 && thereIsRain(weatherPredictions) == 0){
            return true;
        }
        
        return false;
    }
    
    private boolean thereIsNoHumidity(BigInteger humidity){
        if(BigInteger.valueOf(20).compareTo(humidity) > 0)
            return true;
        return false;
    }
    
    private boolean thereIsABitOfHumidity(BigInteger humidity){
        if((BigInteger.valueOf(20).compareTo(humidity) < 0) && BigInteger.valueOf(50).compareTo(humidity) > 0)
            return true;
        return false;
    }
    
    private boolean thereIsEnoughtHumidity(BigInteger humidity){
        if((BigInteger.valueOf(50).compareTo(humidity) < 0) && BigInteger.valueOf(80).compareTo(humidity) > 0)
            return true;
        return false;
    }
    
    private boolean thereIsFullHumidity(BigInteger humidity){
        if((BigInteger.valueOf(80).compareTo(humidity) < 0) && BigInteger.valueOf(100).compareTo(humidity) > 0)
            return true;
        return false;
    }
    
    private int calculateDifOnMonths(Date lastDateIrrigation) {
        Date now = new Date();
        int months = 0;
        
        long dif = 0;
        dif =  lastDateIrrigation.getTime() - now.getTime();
        months = (int) (dif / (1000 * 60 * 60 * 24 * 30));
        
        return months;
    }
    
    public boolean thereIsWaterAvailable(BigInteger wMAvailable){
        if(BigInteger.valueOf(0).compareTo(wMAvailable) < 0)
            return true;
        return false;
    }
    
    private float thereIsRain(JsonArray weatherPredictions){
        float averageRain = 0, precipitations = 0, probability = 0;
        
        for (JsonObject wheaterPredictionPerDay : weatherPredictions.getValuesAs(JsonObject.class)) {
            precipitations = wheaterPredictionPerDay.getInt("precipitations");
            probability = (float) wheaterPredictionPerDay.getInt("probability");
            
            averageRain = averageRain + precipitations*(probability/100);
        }
        
        return averageRain;
    }
    
}
