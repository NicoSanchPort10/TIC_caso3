El sistema modela una cadena de procesamiento de eventos con multiples hilos:

- `Sensor`: genera eventos y los deposita en el buzon de entrada.
- `Broker`: revisa cada evento y lo envia al buzon de alertas o al buzon de clasificacion.
- `Administrador`: consume alertas, descarta o reenvia eventos, y luego notifica el fin a los clasificadores.
- `Clasificador`: enruta los eventos normales al servidor de consolidacion correspondiente.
- `ServidorDespliegue`: procesa eventos de su propio buzon hasta recibir un evento de fin.

## Requisitos

- Java 17 o superior instalado.
- Terminal con `javac` y `java` disponibles.

Verificacion rapida:

```sh
javac -version
java -version
```

## Estructura del proyecto

- `src/caso3/Main.java`: punto de entrada.
- `src/caso3/Config.java`: lectura de parametros desde archivo.
- `src/caso3/model/Evento.java`: representacion de eventos normales y de fin.
- `src/caso3/actors`: hilos del sistema.
- `src/caso3/sync`: buzones y coordinacion compartida.
- `config.txt`: archivo de configuracion de ejemplo listo para ejecutar.
- `config_rapido.txt`: configuracion corta para validar rapido.
- `config_estres.txt`: configuracion mas exigente para observar concurrencia.
- `out`: carpeta de compilacion.

## Parametros de configuracion

El programa recibe un archivo de texto con estos parametros:

- `numeroSensores`
- `eventosBase`
- `numeroClasificadores`
- `numeroServidores`
- `tamanoBuzonClasificacion`
- `tamanoBuzonConsolidacion`

Ejemplo actual en `config.txt`:

```properties
numeroSensores=3
eventosBase=10
numeroClasificadores=4
numeroServidores=3
tamanoBuzonClasificacion=20
tamanoBuzonConsolidacion=15
```

Configuraciones adicionales incluidas:

- `config_rapido.txt`: prueba corta con pocos eventos y finalizacion rapida.
- `config_estres.txt`: prueba mas pesada con mas competencia por buzones.

## Compilacion

Desde la raiz del proyecto:

```sh
javac -d out $(find src -name '*.java')
```

Si el comando termina sin salida, la compilacion fue exitosa.

## Ejecucion

Para correr la simulacion con la configuracion incluida:

```sh
java -cp out caso3.Main config.txt
```

Si quieres usar otro archivo:

```sh
java -cp out caso3.Main ruta/al/archivo.properties
```

Ejemplos utiles:

```sh
java -cp out caso3.Main config_rapido.txt
java -cp out caso3.Main config_estres.txt
```

## Como probarlo manualmente

Flujo recomendado:

1. Compila el proyecto.
2. Ejecuta `config_rapido.txt` para validar que todo cierre bien.
3. Ejecuta `config_estres.txt` para observar mas interaccion concurrente.
4. Revisa los mensajes de terminacion de sensores, broker, clasificadores y servidores.
5. Verifica que aparezca `Simulacion completada.`
6. Verifica que todos los buzones terminen en `0`.

Con el estado actual del proyecto, una ejecucion valida debe terminar con una salida parecida a esta:

```text
Simulacion completada.
Estado final de buzones (deben ser todos 0):
- entrada: 0
- alertas: 0
- clasificacion: 0
- consolidacion-1: 0
- consolidacion-2: 0
- consolidacion-3: 0
```

## Que valida esta prueba

La ejecucion manual te permite comprobar, de acuerdo con el enunciado:

- que los sensores producen todos los eventos esperados;
- que el broker procesa el total de eventos;
- que el administrador termina al recibir el evento de fin;
- que los clasificadores reparten eventos y el ultimo envia eventos de fin a servidores;
- que los servidores procesan eventos y terminan al recibir fin;
- que no quedan eventos atrapados en los buzones.

## Notas sobre sincronizacion

El proyecto usa exclusivamente primitivas basicas de Java para sincronizacion, como pide el enunciado:

- `synchronized`
- `wait`
- `notifyAll`
- `yield`
- `join`

Los tipos de espera usados son:

- espera pasiva en los buzones bloqueantes;
- espera semi-activa en `BuzonSemiActivo`.

## Estado actual verificado

Se verifico localmente que:

- el proyecto compila con `javac`;
- la simulacion ejecuta correctamente con `config.txt`;
- la simulacion ejecuta correctamente con `config_rapido.txt`;
- la simulacion ejecuta correctamente con `config_estres.txt`;
- la ejecucion finaliza;
- todos los buzones quedan vacios al terminar.
