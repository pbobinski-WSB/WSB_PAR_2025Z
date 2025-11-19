package wsb.par.rpc.json;


import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws Throwable {

        JsonRpcHttpClient client = new JsonRpcHttpClient(
                new URL("http://localhost:5000"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Map<String,String> params = new HashMap<>();
        params.put("name","Piotr");
        Map res = client.invoke("hello", params, Map.class);
        System.out.println(res);
        
        
    }

}