# Refaktoriseringsplan

## Nya Klasser

* Main  
* CarFrame  
* SimulationPanel  
* ControlPanel  
* PanelManager

## Nya Interfaces

* TurboCar  
* FlatbedCar  
* RampCar

## Generell struktur

Generella sturkturen har omformeras för att stöta en MVC modell, där Model är bil systemet vilket kommunicerar genom gränsnittet CarController, View hanteras i SimulationPanel och Controller hanteras med ControlPanel. Istället för att programmet initialiseras med main inom CarController så finns en ny Main klass vilket kontrollerar programmets tickrate och andra möjliga gränssnitts funktioner utanför kontrollering av bilar.

För varje frame så skickas en signal från Main till CarController att uppdatera bilarna.  
ControlPanel skickar en avsändare till CarController om vilken metod de ska anropa i bil systemet efter ett knapptryck. Efteråt frågar SimulationPanel för en uppdatering från CarController och den svarar med vilka ändringar som behövs på vyn.

## CarController

CarController har gjorts om så att den hanterar indata från ControlPanel och skickar information till SimulationPanel istället för att DrawPanel använder BufferedImage, Map och Car som innan. 

Innan behövde CarController använda alla bilvarianter som används, nu använder den istället interfaces med de unika funktionerna som biltyperna har tillgång till. CarController behöver inte använda andra workshoptyper än den vanliga, eftersom de inte lägger till nya funktioner. Om det behöver utvecklas används interfaces liksom kommunikationen mellan biltyperna och CarController.

CarController hanterar interfaces inom bil systemet som specificerar vilka egenskaper av bilen som finns, som en flak eller turbomotor.

## CarView

CarView har bytts ut med klasserna CarFrame och PanelManager, istället för att hantera alla knappar på en JFrame så har ansvaret skickats till ControlPanel. Nu hanterar CarFrame ansvaret för JFrame som ska visas till användaren och PanelManager samling och synkning av panelerna.

## DrawPanel

DrawPanel har delats upp i klasserna SimulationPanel och ControlPanel. SimulationPanel visar bilden som simuleras och uppdaterar den när det görs ändringar. ControlPanel visar kontrollerna och skickar vidare instruktioner till CarController.

SimulationPanel och ControlPanel har ingen kommunikationskanal förutom genom CarController. För framtiden så hanteras nya paneler av PanelManager som kommer hantera det översiktliga ansvaret för klassen bland andra paneler.