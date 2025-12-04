const http2 = require('http2');
const fs = require('fs');
const path = require('path');

const options = {
  key: fs.readFileSync('localhost-privkey.pem'),
  cert: fs.readFileSync('localhost-cert.pem')
};

const server = http2.createSecureServer(options, (req, res) => {
  console.log(`Otrzymano żądanie dla: ${req.url}`);

  if (req.url === '/') {
    
    res.stream.additionalHeaders({
      ':status': 103,
      'link': '</style.css>; rel=preload; as=style'
    });
    console.log('--> Wysłano 103 Early Hints z sugestią pobrania /style.css');

    setTimeout(() => {
      console.log('   (Zakończono generowanie HTML, wysyłam główną odpowiedź)');
      
      // ZMIANA 1: Opakowujemy funkcję błędu w obiekt { onError: ... }
      res.stream.respondWithFile(
        path.join(__dirname, 'index.html'), 
        {
          'content-type': 'text/html; charset=utf-8',
          ':status': 200
        }, 
        {
          onError: (err) => {
            console.error('Błąd podczas wysyłania index.html:', err);
            // W przypadku błędu, zamknij strumień
            if (!res.stream.destroyed) {
              res.stream.respond({ ':status': 500 });
              res.stream.end();
            }
          }
        }
      );
    }, 1000);

  } 
  else if (req.url === '/style.css') {
    // ZMIANA 2: Opakowujemy funkcję błędu w obiekt { onError: ... }
    res.stream.respondWithFile(
      path.join(__dirname, 'style.css'),
      { 'content-type': 'text/css' },
      {
        onError: (err) => {
          console.error('Błąd podczas wysyłania style.css:', err);
          if (!res.stream.destroyed) {
            res.stream.respond({ ':status': 500 });
            res.stream.end();
          }
        }
      }
    );
  } 
  else {
    res.stream.respond({ ':status': 404 });
    res.stream.end();
  }
});

const PORT = 8443;
server.listen(PORT, () => {
  console.log(`Serwer HTTP/2 z Early Hints nasłuchuje na https://localhost:${PORT}`);
});