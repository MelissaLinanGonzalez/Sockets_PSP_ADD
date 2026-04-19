## Gestor de Reservas de Vuelo (Multihilo)
Desarrolla una aplicación cliente-servidor en Java usando Sockets TCP. El servidor gestionará las reservas de un 
único vuelo que tiene una capacidad máxima de 50 asientos.<br>
Varios clientes (agencia de viaje) podrán conectarse simultáneamente. El servidor mostrará un menú con las siguientes
opciones:
1. CONSULTAR: Muestra cuántos asientos quedan libres.
2. RESERVAR: Pide al cliente cuántos asientos quiere reservar. Si hay suficientes, se restan y se confirman. Si no, 
avisa del error.
3. DEVOLVER: Pide al cliente cuántos asientos quiere devolver al sistema y los suma.
4. Salir: Descontecta al cliente.

El sistema debe ser concurrente y eviar que dos clientes reserven el mismo asiento al mismo tiempo