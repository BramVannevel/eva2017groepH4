DE APPLICATIE RUNNEN:

- Start MongoDB
commando: mongod

- Start de applicatie
commando: npm run dev


DE APPLICATIE DEPLOYEN
- Genereer de static resources van de applicatie
commando: npm run build

- push naar de gewenste server


=========== REST ROUTES VOOR EXTERNE APPLICATIES ===========

ONDERSTAANDE ROUTES RETURNEN JSON:

HAAL CATEGORIEEN OP:
https://restobeheerapp.herokuapp.com/categorieen/categorieList

--------------------------------------------------------------

HAAL ALLERGENEN OP:
https://restobeheerapp.herokuapp.com/allergenen/allergeenList

--------------------------------------------------------------

HAAL GERECHTEN OP:
https://restobeheerapp.herokuapp.com/gerechten/gerechtList

--------------------------------------------------------------

HAAL ALLE GERECHTEN DIE OP HET MENU STAAN OP:
https://restobeheerapp.herokuapp.com/menu/menuList

--------------------------------------------------------------
BESTELLINGEN:

VOEG BESTELLING TOE (POST)
https://restobeheerapp.herokuapp.com/bestellingen/

JSON structuur voor post:
{
  datum: Date,
  gebruiker: String,
	gerechten:
  	[
      		{
			      naam: String,
        		prijs: Number
      		}
  	]
}

Bijvoorbeeld:
{
  "datum": "2016-12-13T02:15:12.356Z",
  "gebruiker": "Dennis",
  "gerechten":
  	[
      	{
		      "naam": "Cola",
        	"prijs": 0.60
      	},
      	{
      		"naam": "Broodje Kaas",
      		"prijs": 1.20
      	}
  	]
}

HAAL BESTELLINGEN OP VAN EEN GEBRUIKER VAN DE EXTERNE APPLICATIE (GET)
Bijvoorbeeld:
https://restobeheerapp.herokuapp.com/bestellingen/bestellingList/dennis.noens@telenet.be
--------------------------------------------------------------


========================== EINDE ==========================
