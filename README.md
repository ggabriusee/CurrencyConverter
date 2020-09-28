# CurrencyConverter

Deployed at: https://mano-currency-converter.herokuapp.com/

Galima pasiekti duombazę adresu https://mano-currency-converter.herokuapp.com/h2-console prisijungiant prie jdbc:h2:./mydatabase

Deployed produkcinis app yra ta pati versija kaip ir šioje repositorijoje. Galima rasti https://git.heroku.com/mano-currency-converter.git




Toliau Lokali/dev pasileidimo instrukcija. 

Ištestuota ir pagaminta Linux Ubuntu aplinkoje.

Paleisti back-end:

1. Nueiti į back-end direktoriją ( cd back-end ).

2. Galima paleisti unit testus ( mvn test ).

3. Startuoti serverį ( linux: ./mvnw spring-boot:run ) ( windows: mvnw spring-boot:run ).

Paleidimo Dependencies:
Maven,
Java 11


Paleisti front-end:

1. Nueiti į front-end direktoriją ( cd front-end ).

2. Atsisiųsti projekto dependencies ( npm install )

3. Startuoti front-end ( ng serve )


Paleidimo Dependencies:
Node Package Manager (npm),
Angular CLI

Padarius šiuos veiksmus, aplikacija naršyklėje pasiekiama adresu: http://localhost:4200

Duomenų bazė naršyklėje pasiekiama adresu: http://localhost:8080/h2-console
