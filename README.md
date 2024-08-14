## Projekt - Schach 2022
121 WINF - Erik Baerwald, Sören Raube

Wo wird die Datei gelauncht?

--> gestartet wird das Spiel in der der Java-Datei run.java (mainPrograms)
    Server und Client müssen NICHT seperat gestartet werden (server_client)

Grafics - Board:
Zuerst öffnet sich nun der Start-Screen, in dem ein neues Spiel gestartet werden kann. Mit einem Klick auf den "Neus Spiel"-Button öffnet sich der Spieleinstellungen-Frame, indem die Einstellungen nach belieben ausgewählt werden können. Wenn man bei den Spieleinstellungen alles eingegeben hat kann das Spiel gestartet werden und der Game-Frame öffnet sich.
    --> WICHTIG!: Es muss bei jeder Einstellung etwas ausgewählt sein, da das Spiel sonst nicht starten kann. 
Dafür dass das Spielfeld richtig ist, sorgt die Klasse Spring-Utilities.

MainPrograms:
Im Hintergrund des Spiel werden alle Züge durch die Move.java-Dateien überprüft. Wählt man mit einer Figur ein Feld, welches diese nicht erreichen kann, so muss man erneut eine Figur wählen und ihr "Ziel" anklicken. Außerdem wird in der json.java die JSON-Datei erzeugt, die für die Speicherung und Bereitstellung der Daten zuständig ist. So können auch im Online-Spiel Daten übertragen werden.

Alle benötigten Bilder sind im Projekt eingebunden und sollten ohne Probleme angezeigt werden können.

Viel Spaß beim spielen ;)
