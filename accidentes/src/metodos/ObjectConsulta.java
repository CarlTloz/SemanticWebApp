package metodos;

public class ObjectConsulta {
    private String date;
    private String injuryStatus;
    private String weatherCondition;
    private String typeAccident;
    private String expedient;
    private String district;
    private String steet;
    private String wikidataDistrict;
    private String uri;

    public ObjectConsulta(){
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getWikidataDistrict() {
        return wikidataDistrict;
    }

    public void setWikidataDistrict(String wikidataDistrict) {
        this.wikidataDistrict = wikidataDistrict;
    }

    public String getSteet() {
        return steet;
    }

    public void setSteet(String steet) {
        this.steet = steet;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getInjuryStatus() {
        return injuryStatus;
    }

    public void setInjuryStatus(String injuryStatus) {
        this.injuryStatus = injuryStatus;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getTypeAccident() {
        return typeAccident;
    }

    public void setTypeAccident(String typeAccident) {
        this.typeAccident = typeAccident;
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

}
