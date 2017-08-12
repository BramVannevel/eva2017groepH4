DE APPLICATIE RUNNEN:

Nodige software:
- NodeJS 4.6.0
- MongoDB: 3.4.6 2008R2Plus SSL (64 bit)
- Webbrowser

1. Start MongoDB
commando: mongod (indien een PATH variabele werd ingesteld)

2. Start de applicatie
- Open een terminal en navigeer naar de projectfolder
- Voer onderstaand commando uit
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
name: JeUserName
password: JePaswoord

INLOGGEN [POST]
https://evabeheer.herokuapp.com/user/authenticate
Body: x-www-form-urlencoded
name: JeUserName
password: JePaswoord

HAAL RESTAURANTS OP [GET]
https://evabeheer.herokuapp.com/restaurants/list

HAAL GERECHTEN OP [GET]
https://evabeheer.herokuapp.com/gerechten/list

HAAL CATEGORIEEN OP [GET]
https://evabeheer.herokuapp.com/categorieen/list

HAAL CHALLENGES OP [GET]
https://evabeheer.herokuapp.com/challenges/list


========================== EINDE ==========================
