# Caso 3 - Sistema IoT concurrente

Base del proyecto para implementar el simulador concurrente del caso.

El proyecto esta armado como Java simple, sin Maven ni Gradle, para que puedas
compilarlo directamente con `javac`.

## Estructura

- `src/caso3/Main.java`: punto de entrada y armado de actores.
- `src/caso3/Config.java`: lectura del archivo de parametros.
- `src/caso3/model/Evento.java`: modelo base de evento normal o evento de fin.
- `src/caso3/sync`: buzones y coordinacion compartida.
- `src/caso3/actors`: sensores, broker, administrador, clasificadores y servidores.
- `config/ejemplo.properties`: archivo de configuracion de ejemplo.
- `docs/TODO_IMPLEMENTACION.md`: lista de TODOs principales del caso.

## Compilar

```sh
make build
```

## Ejecutar

```sh
make run
```

Tambien puedes indicar otro archivo de configuracion:

```sh
make run CONFIG=config/mi_config.properties
```

## Estado actual

La base compila y crea todos los objetos principales, pero los actores todavia no
implementan el flujo del caso. Cada `run()` tiene TODOs concretos para completar.
