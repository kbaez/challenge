# API quasar
![technology Java](https://img.shields.io/badge/technology-java-blue.svg)

API quasar expone servicios para calcular la ubicación de una nave, según la posición de 3 satélites 
mediante la trilateración (https://es.wikipedia.org/wiki/Trilateraci%C3%B3n).

Proyecto que se usa de apoyo para el calculo de la trilateración:

https://github.com/lemmingapex/trilateration

## Pre-requisitos:

    Maven
    Java JDK 1.8

## Configure su proyecto
Clonar el repositorio desde Github, sitúese en el directorio root del proyecto y ejecute el siguiente comando 
desde la consola `mvn clean install` para poder buildear el proyecto y descargar todas sus dependencias.
A continuación, puede ejecutar la aplicación con el comando `mvn spring-boot:run`

La aplicación ejecutará en el puerto `8080` de `localhost`.

Una vez que la aplicación este corriendo podra visualizar los satelites almacenados 
(los pre-existentes y los que se creen) en una base de datos que ejecuta en memoria (Database **H2**).
Para consultar los elementos en la BD puede ingresar desde el navegador a `http://localhost:8080/h2-console` 
e ingresar con las siguientes credenciales y realizar las consultas necesarias:
```
User Name: sa 
Password: sa
```

Los satelites pre-cargados estan almacenados en la BD por el siguiente archivo sql:
[Inicialización Base Datos](https://github.com/kbaez/challenge/blob/alternative-class/src/main/resources/data.sql).

**IMPORTANT:** Una vez que se detiene y se vuelve a ejecutar la aplicación los elementos almacenados en la base de datos se pierden
(salvo los pre-existentes), ya que se encuentran almacenados en una BD que ejecuta en memoria (**H2**).

## Api Usage

Todos los endpoints y ejemplos de request estan en esta coleccion postman:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/15ce7eb025604c93d2e1)

## Uso de la API

### Obtener ubicación de la nave y el mensaje que emite.

`POST` http://<i></i>localhost:8080/topsecret

##### `BODY`
```json
{
	"satellites": [
		{
			"name":"kenobi",
			"distance": 100.0,
			"message": ["este","","","mensaje",""]
		},
		{
			"name":"skywalker",
			"distance": 115.5,
			"message": ["","es","","","secreto"]
		},
		{
			"name":"sato",
			"distance": 142.7,
			"message": ["este","","un","",""]
		}
	]
}
```

##### Response
**Status**: `200 OK`
```json
{
    "position": {
        "x": -58.31525,
        "y": -69.551414
    },
    "message": "este es un mensaje secreto"
}
```
#### Error Response Format
**Status**: `400 Bad request`
```json
{
    "error": "Quantity error exception",
    "message": "Error. Quantity of satellites is less than 3. It can't be done true-range multilateration for location.",
    "status": 400
}
```

### Guardar información del satelite
`POST` http://<i></i>localhost:8080/topsecret_split/{satellite_name}

##### `BODY`
```json
{
     "distance": 100.0,
     "message": ["este","","","mensaje",""]
}
```

#### Success Response
**Status**: `200 OK`
```json
{
    "position": {
        "x": -500.0,
        "y": -200.0
    },
    "message": "este, , , mensaje, "
}
```

### Calcula ubicación de la nave con los datos 

`GET` http://<i></i>localhost:8080/topsecret_split

#### Success Response
**Status**: `200 OK`
```json
{
    "position": {
        "x": -58.31525,
        "y": -69.551414
    },
    "message": "este es un mensaje secreto"
}
```

#### Error Response Format
**Status**: `400 Bad request`
```json
{
    "error": "Quantity error exception",
    "message": "Error. Quantity of satellites is less than 3. It can't be done true-range multilateration for location.",
    "status": 400
}
```
