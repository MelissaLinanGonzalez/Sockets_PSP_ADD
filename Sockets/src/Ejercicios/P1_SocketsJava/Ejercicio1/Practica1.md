## Práctica 1 - Sockets en JAVA

### Ejercicio 1
Desarrolla una aplicación cliente-servidor en Java utilizando sockets TPC.
La aplicación permitirá que un cliente se conecte al servidor, se autentique
mediante usuario y contraseña y, una vez validado, pueda ejecutar varias
operaciones remotas desde un menú.

El flujo es el siguiente:

- El cliente se conecta al servidor.
- El servidor solicita usuario y contraseña.
- El cliente envía ambos datos al servidor.
- El servidor validará las credenciales contra una pequeña colección interna definida en memoria.
  - Si el login es incorrecto, el servidor informará del error y dará 2 oportunidades más, si el tercero no es correcto, se cerrará la sesión.
  - Si el login es correcto, el servidor abrirá una sesión y mostrará un menú de operaciones.

### <u>Operaciones disponibles</u>
- Sumar: El servidor pedirá dos números y devolverá el resultado.