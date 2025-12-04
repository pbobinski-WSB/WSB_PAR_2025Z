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
    
    // Inicjujemy Server Push dla pliku /script.js
    res.stream.pushStream({ ':path': '/script.js' }, (err, pushStream) => {
      if (err) {
        throw err;
      }
      console.log('--> Wypycham /script.js');
      
      // pushStream zostanie automatycznie zamknięty po wysłaniu pliku
      pushStream.respondWithFile(path.join(__dirname, 'script.js'), {
        'content-type': 'application/javascript'
      });
    });

    // Serwujemy główny plik index.html
    // res.stream również zostanie automatycznie zamknięty po wysłaniu pliku
    res.stream.respondWithFile(path.join(__dirname, 'index.html'), {
      'content-type': 'text/html; charset=utf-8',
      ':status': 200
    });

  } else if (req.url === '/script.js') {
    // Serwujemy plik, jeśli klient poprosi o niego jawnie
    res.stream.respondWithFile(path.join(__dirname, 'script.js'), {
        'content-type': 'application/javascript'
    });
  } else {
    // Dla ścieżek 404, musimy jawnie zamknąć strumień
    res.stream.respond({ ':status': 404 });
    res.stream.end();
  }

  // NIE WOLNO TUTAJ WYWOŁYWAĆ res.stream.end()!
  // To by natychmiast zamknęło połączenie.
});

const PORT = 8443;
server.listen(PORT, () => {
  console.log(`Serwer HTTP/2 nasłuchuje na https://localhost:${PORT}`);
});