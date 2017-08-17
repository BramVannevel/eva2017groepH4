DE APPLICATIE RUNNEN:

Nodige software:
- NodeJS 4.6.0
- MongoDB: 3.4.6 2008R2Plus SSL (64 bit)
- Webbrowser

1. Start MongoDB
commando: mongod (indien een PATH variabele werd ingesteld)

2. Start de applicatie
- Open een terminal en navigeer naar de projectfolder
- Voer éénmaal onderstaand commando uit om de dependencies van het project te installeren:
  npm install --> er wordt een map node_modules aangemaakt
- Voer onderstaand commando uit om de applicatie uit te voeren
  npm run dev (Voor development, npm-start wordt gebruikt door de builder van heroku.com)

--> De webapplicatie is nu zichtbaar op http://localhost:8080

DE APPLICATIE DEPLOYEN
- Genereer de static resources van de applicatie
  commando: npm run build

- Push naar de gewenste server (Bv. Heroku)
  De server build met het commando npm start

=========== REST ROUTES ===========

ONDERSTAANDE ROUTES RETURNEN JSON EN ZIJN BESCHIKBAAR VOOR IN DE APP GEREGISTREERDE USERS:

!! Verplichte header bij alle HTTP calls (behalve registreren en inloggen) !!
Key: Authorization Value: JWT "HetVerkregenToken"

Het token kan je verkrijgen door een call te doen naar het endpoint om in te loggen

REGISTREREN [POST]
https://evabeheer.herokuapp.com/user/signup
Body: x-www-form-urlencoded
name: JeUsername
password: JePaswoord

INLOGGEN [POST]
https://evabeheer.herokuapp.com/user/authenticate
Body: x-www-form-urlencoded
name: JeUsername
password: JePaswoord

HAAL RESTAURANTS OP [GET]
https://evabeheer.herokuapp.com/restaurants/list

HAAL GERECHTEN OP [GET]
https://evabeheer.herokuapp.com/gerechten/list

HAAL CATEGORIEEN OP [GET]
https://evabeheer.herokuapp.com/categorieen/list

HAAL CHALLENGES OP [GET]
https://evabeheer.herokuapp.com/challenges/list

EEN VEGAGRAM POST DOOR EEN GEBRUIKER LATEN TOEVOEGEN [POST]
https://evabeheer.herokuapp.com/vegagram/
Body: Multipart form-data
fileToUpload -> Binair bestand (bv. .jpg)
isPublic -> true/false
likes -> geheel getal
posted -> date (format: yyyy/mm/dd)

ALLE VEGAGRAM POSTS OPHALEN VAN DE AUTHENTICATED GEBRUIKER [GET] **
https://evabeheer.herokuapp.com/vegagram/useruploads

ALLE PUBLIC VEGAGRAM POSTS OPHALEN VAN ALLE GEBRUIKERS [GET] **
https://evabeheer.herokuapp.com/vegagram/uploads

**een vegagram post bevat onder andere de image naam, deze kan gebruikt worden om het binaire bestand op te halen

AFBEELDING VAN EEN VEGAGRAM POST OPHALEN AAN DE HAND VAN DE VERKREGEN NAAM VAN DE IMAGE [GET]**
https://evabeheer.herokuapp.com/uploads/naamVanDeImage

EEN VEGAGRAM POST VERWIJDEREN [DEL] (bijhorende image wordt ook van server verwijderd)
https://evabeheer.herokuapp.com/idVanDePost


========================== EINDE ==========================
