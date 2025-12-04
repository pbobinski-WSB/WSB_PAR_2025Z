package clients;

import auto.isbn.ISBNService;
import auto.isbn.ISBNServiceSoapType;

import javax.xml.namespace.QName;

public class IsbnServiceClient {
    public static void main(String[] argv) {
        QName name = new QName("http://webservices.daehosting.com/ISBN","ISBNServiceSoap");
        ISBNServiceSoapType service = new ISBNService().getPort(name, ISBNServiceSoapType.class);
        //invoke business method
        Boolean b = service.isValidISBN13("978-8879070645");
        System.out.println(b);
    }
}
