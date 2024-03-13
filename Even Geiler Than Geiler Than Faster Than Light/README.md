Wenn man das Projekt neu uebersetzt mit 'mvn clean package', muss man den Ordner 'db' kopieren und dann
in den Ordner 'target' einfuegen. Denn bei erneuten Uebersetzung geht die Datenbank verloren und das
Programm erzeugt keine neue Datenbank.

Auf MacOS laesst sich das Programm nicht durch Doppelklick starten. Man muss stattdessen in den Kommandozeile
wie folgt eintippen:

java -jar -XstartOnFirstThread [Name die .jar Datei]