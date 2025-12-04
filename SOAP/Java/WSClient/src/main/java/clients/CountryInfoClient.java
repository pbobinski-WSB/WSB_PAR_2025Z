package clients;


import auto.countries.CountryInfoService;
import auto.countries.CountryInfoServiceSoapType;

import javax.xml.namespace.QName;

public class CountryInfoClient {
    public static void main(String[] argv) {
        QName name = new QName("http://www.oorsprong.org/websamples.countryinfo","CountryInfoServiceSoap");
        CountryInfoServiceSoapType service = new CountryInfoService().getPort(name, CountryInfoServiceSoapType.class);
        //invoke business method
        String s = service.capitalCity("PT");
        System.out.println(s);


    }
}
