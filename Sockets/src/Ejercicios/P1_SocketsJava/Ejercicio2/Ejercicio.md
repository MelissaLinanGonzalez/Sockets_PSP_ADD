## Ejercicio 2
Desarrolla un servidor multihilo en Java que permita a varios clientes conectarse simultáneamente.
Cada cliente podrá enviar comandos para incremetnar, consultar o resetear un contador global compartido
(*INC, GET, RESET*).

El servidor deberá gestionar correctamente la comunicación para evitar incosistencias en el contador cuando
varios clientes accedan al mismo tiempo.

**Usar synchronized**