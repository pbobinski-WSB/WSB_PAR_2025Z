from unittest import result

import requests

# response = requests.get("http://taco-randomizer.herokuapp.com/random")
# print(response)
# print(response.json())

response = requests.get("http://universities.hipolabs.com/search?name=nova");
print(response)
print(response.json())

# response = requests.get("https://openlibrary.org/isbn/9780140328721.json");
print(response)
print(response.json())

#response = requests.get("http://localhost:8080/demoWS03-1.0-SNAPSHOT/api/hello-world");
print(response)
print(response.text)

from zeep import Client

client = Client('http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL')
result = client.service.CapitalCity("PT")
print(result)

client = Client('http://webservices.daehosting.com/services/isbnservice.wso?WSDL')
result = client.service.IsValidISBN13("978-8879070645")
print(result)

client = Client('http://localhost:8000/?wsdl')
print(client.service.add(8,9))


# client = Client('http://localhost:8080/WebService_war_exploded/services/Test?wsdl')
# result = client.service.sayHelloWorldFrom('PBo')
# print(result)


#client = Client('http://localhost:8080/demoWS03-1.0-SNAPSHOT/services/TestService?wsdl')
#result = client.service.sayHelloWorldFrom("PBo")
print(result)

# client = Client('http://localhost:8080/demoWS03-1.0-SNAPSHOT/services/TestService?wsdl')
#result = client.service.add(3, 5)
print(result)
