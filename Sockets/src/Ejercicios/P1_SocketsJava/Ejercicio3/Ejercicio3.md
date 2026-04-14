## Ejercicio 3
Desarrolla una aplicación cliente-servidor en Java usando sockets TCP donde varios clientes puedan conectarse 
simultáneamente a un servidor de subastas. Cada cliente podrá identificarse con un nombre y enviar comandos para 
consultar el artículo actual, realizar pujas y ver la puja más alta.

El servidor deberá gestionar correctamente el acceso concurrente al estado compartido de la subasta, validando que
una nueva puja solo sea aceptada si supera a la actual, e informando a todos los clientes cuando cambie el mejor
postor o cuando la subasta finalice.