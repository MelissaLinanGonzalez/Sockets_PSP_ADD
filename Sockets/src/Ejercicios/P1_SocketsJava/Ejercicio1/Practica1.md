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
- Contador: El servidor pedirá un texto y devolverá cuántas vocales tiene.
- Invertir: El servidor pedirá un texto y devolverá la cadena invertida.
- EsPrimo: El servidor pedirá un número y responderá si es primo o no.
- Cerrar: El servidor enviará un mensaje de despedida y cerrará la conexión.

### <u>Ejemplo de ejecución</u>
SERVIDOR -> Usuario:
CLIENTE -> admin
SERVIDOR -> Contraseña:
CLIENTE -> 1234
SERVIDOR -> LOGIN_OK

SERVIDOR -> Menú:
1. Sumar.
2. Contar vocales.
3. Invertir cadena.
4. Primo.
5. Salir.

CLIENTE -> 1

SERVIDOR -> Introduce el primer número:
CLIENTE -> 8
SERVIDOR -> Introduce el segundo número:
CLIENTE -> 5
SERVIDOR -> Resultado: 13