package clients;

import auto.numberconversion.NumberConversion;
import auto.numberconversion.NumberConversionSoapType;

import javax.xml.namespace.QName;
import java.math.BigInteger;

public class NumberConversionClient {
  public static void main(String[] argv) {
    QName name = new QName("http://www.dataaccess.com/webservicesserver/","NumberConversionSoap");
    NumberConversionSoapType service = new NumberConversion().getPort(name, NumberConversionSoapType.class);
    //invoke business method
    String s = service.numberToWords(new BigInteger("1234563457445"));
    System.out.println(s);


  }
}
