# position-service

Hantera positioner för fordon.

**15/3**

Ett hackigt ihopsatt API som fungerar.

HowTo 1:
1) Skapa underliggande databas med tabell (2 alternativ):
   1) Tabell via skript: *mysql -> create_car_table.sql*
   2) Egen tabell: Använd spatialt index på kolumn av typ Point med SRID 4326. https://dev.mysql.com/doc/refman/8.0/en/creating-spatial-indexes.html
2) Döp om instansvariablerna i DataAccessLayer.java till att passa med nyss skapad databas.
3) Kör "gradle apprun" i terminalfönster ståendes i projekt-rooten
4) Gå till http://localhost:8080/position-service

Fler länkar:<br>
- http://localhost:8080/position-service?polygon=56.64%2010.8820%2056.64%2012.12%2057.73%2012.12%2057.73%2010.8820%2056.64%2010.8820 <br>
- http://localhost:8080/position-service?polygon=57.64%2011.8820%2057.64%2012.12%2057.73%2012.12%2057.73%2011.8820%2057.64%2011.8820 <br>
- (Shall give error-msg:) http://localhost:8080/position-service?polygon=57.64%2011.8820%2057.64%2012.12%2057.73%2012.12%2057.73%2011.8820%2057.64 <br>

**12/3**

- DataAccessLayer.java
- TestDataAccessLayer.java

Kan hämta positioner från en tabell med latitud-/longitud-koordinater. Gör detta via jdbc, java.sql<br><br>
Script för att skapa underliggande databas: *mysql -> create_car_table.sql<br>*<br>
Ändra "url", "user" och "password" i DataAccessLayer.java för att stämma överens med egen databas.