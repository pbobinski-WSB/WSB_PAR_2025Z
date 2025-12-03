#from suds.client import Client
from zeep import Client

c = Client('http://localhost:8000/?wsdl')
print(c.service.slow_request('req test'))

print(c.service.add(8,9))
