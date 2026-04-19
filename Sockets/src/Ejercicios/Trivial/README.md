# Práctica 1.2: Trivia en Red (Cliente-Servidor con Sockets en Java)

Este proyecto es una aplicación de **Trivial Multijugador en tiempo real**, desarrollada en Java mediante una 
arquitectura Cliente-Servidor multihilo usando Sockets TCP. Permite a varios alumnos conectarse a un servidor 
central dentro de una misma red local para competir respondiendo preguntas tipo test.

## 🚀 Requisitos Previos

* **Java Development Kit (JDK):** Versión 8 o superior instalada en tu sistema.
* **Entorno:** Un terminal de comandos o un Entorno de Desarrollo Integrado (IDE) como IntelliJ IDEA, Eclipse o NetBeans.

---

## 🛠️ Instalación y Configuración

1. Clona este repositorio o descarga los archivos fuente en tu equipo local.
2. Asegúrate de mantener la estructura de paquetes correcta. Todos los archivos `.java` deben estar ubicados dentro de 
la carpeta/paquete `Ejercicios/Trivial/`.

---

## 📖 Instrucciones de Uso

### 1. Iniciar el Servidor
El servidor debe ser el primero en arrancar para poder escuchar las conexiones entrantes.
* Ejecuta la clase `Servidor.java`.
* Verás un mensaje indicando que el servidor está escuchando en el puerto **5000**.

### 2. Conectar a los Jugadores (Clientes)
Los jugadores deben ejecutar la aplicación cliente desde sus respectivos equipos en la red local.
* Ejecuta la clase `Cliente.java`.
* Al iniciar, el programa solicitará introducir un **nombre de usuario (nick)**.
* Una vez introducido, el cliente quedará a la espera de que el administrador inicie la partida.

### 3. Comenzar la Partida
El juego no arranca automáticamente. El servidor seguirá admitiendo jugadores (hasta un máximo de 10) de forma concurrente.
* Cuando todos los jugadores estén listos, el administrador del servidor debe escribir el comando **`START`** en la 
consola del servidor y pulsar Enter.
* En ese instante, la partida comenzará y la primera pregunta se enviará simultáneamente a las pantallas de todos los 
clientes conectados.

---

## 🎮 Reglas del Juego

* **Límite de Jugadores:** El servidor admite un máximo de **10 jugadores simultáneos**. Si se intenta conectar un 
jugador adicional cuando la sala está llena, recibirá un mensaje informándole del límite y su conexión será cerrada 
amistosamente.
* **Dinámica:** El juego consta de **5 preguntas** tipo test con 4 opciones (a, b, c, d).
* **Formato de Respuesta:** Los jugadores deben enviar su respuesta utilizando obligatoriamente el formato: `<letra>`
(por ejemplo: `b`).
* **Tiempo Límite:** Los jugadores tienen exactamente **15 segundos** para leer y responder cada pregunta. Las 
respuestas recibidas fuera de este tiempo no serán validadas.
* **Puntuación:**
    * Respuesta correcta: **+1 punto**.
    * Respuesta incorrecta, con formato inválido o fuera de tiempo: **0 puntos o -0.3, nunca obtendrá valores negativos**.
* **Transparencia:** Al finalizar cada pregunta, se muestra un ranking parcial actualizado tanto en la consola 
del servidor como en la de todos los clientes. Al acabar la última ronda, se muestra el ranking final.

---

## 🏗️ Arquitectura y Estructura del Código

El proyecto utiliza un enfoque multihilo para garantizar que la comunicación y los temporizadores del juego no 
bloqueen el programa principal:

* `Servidor.java`: Clase principal del servidor. Mantiene un bucle infinito escuchando el puerto 5000 a través 
de `server.accept()` hasta que la partida comienza.
* `EntradaServidor.java`: Un hilo independiente que monitorea el teclado del administrador esperando el comando 
`START` para romper el bloqueo del servidor y lanzar el juego.
* `ClienteHandler.java`: Un hilo "asistente" dedicado a cada jugador. Lee las respuestas de un cliente específico, 
valida el formato y actualiza sus puntos de forma concurrente.
* `GameManager.java`: El motor central del juego. Gestiona la lista de preguntas, los temporizadores de 15 segundos 
(`Thread.sleep()`), la corrección de respuestas y el envío masivo (broadcast) de los rankings.
* `Cliente.java`: Clase principal del jugador. Se conecta al socket del servidor, envía el nick y se queda en un 
bucle capturando las respuestas del usuario por teclado.
* `ReceptorMensajes.java`: Un hilo del lado del cliente que escucha continuamente el canal de entrada (`InputStream`) 
para imprimir inmediatamente las preguntas y rankings que envía el servidor.
* `Preguntas.java`: Modelo de datos (POJO) que estructura el enunciado, las 4 opciones de respuesta y la solución de 
cada pregunta.
