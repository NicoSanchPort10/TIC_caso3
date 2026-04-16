# TODOs de implementacion

Esta base deja marcada la logica que debes completar. La idea es que puedas
implementar cada parte de forma incremental y probar el sistema completo al final.

## 1. Sensores

- Generar `eventosBase * idSensor` eventos por sensor.
- Construir identificadores unicos con `idSensor` y secuencia local.
- Asignar a cada evento un numero pseudoaleatorio entre `1` y `ns`.
- Depositar cada evento en el buzon de entrada ilimitado.
- Terminar cuando se hayan generado todos los eventos asignados.

## 2. Broker

- Retirar exactamente `totalEventosEsperados` eventos del buzon de entrada.
- Por cada evento, generar un numero pseudoaleatorio entre `0` y `200`.
- Si el numero es multiplo de `8`, enviar el evento al buzon de alertas.
- Si no es multiplo de `8`, enviar el evento al buzon de clasificacion.
- Al terminar, enviar un evento de fin al administrador por el buzon de alertas.

## 3. Administrador

- Retirar eventos del buzon de alertas.
- Terminar cuando reciba un evento de fin.
- Para cada evento sospechoso, generar un numero pseudoaleatorio entre `0` y `20`.
- Si el numero es multiplo de `4`, reenviar el evento al buzon de clasificacion.
- Si no es multiplo de `4`, descartar el evento.
- Antes de terminar, enviar `nc` eventos de fin al buzon de clasificacion.

## 4. Clasificadores

- Retirar eventos del buzon de clasificacion.
- Terminar cuando reciban un evento de fin.
- Para eventos normales, usar `tipoServidor` para decidir el buzon de consolidacion.
- Depositar cada evento en el buzon del servidor correspondiente.
- Registrar la terminacion de cada clasificador.
- El ultimo clasificador en terminar debe enviar `ns` eventos de fin, uno por servidor.

## 5. Servidores

- Retirar eventos de su buzon de consolidacion.
- Terminar cuando reciban un evento de fin.
- Para cada evento normal, simular procesamiento entre `100 ms` y `1000 ms`.
- Consolidar metricas utiles para validar el sistema.

## 6. Sincronizacion

- Usar solo primitivas basicas de Java: `synchronized`, `wait`, `notifyAll`,
  `yield` y `join`.
- Usar espera pasiva en los buzones cuando esten vacios o llenos.
- Usar espera semi-activa solo si decides agregar alguna seccion que revise una
  condicion y ceda CPU con `Thread.yield()`.
- Documentar en el informe cada pareja de objetos que interactua y como se
  sincroniza.

## 7. Validacion

- Verificar que el numero total esperado sea:
  `eventosBase * (1 + 2 + ... + numeroSensores)`.
- Verificar que todos los hilos terminen.
- Verificar que todos los buzones queden vacios al final.
- Probar con configuraciones pequenas antes de subir el numero de eventos.
- Registrar cuantos eventos procesa cada etapa para detectar perdidas o duplicados.
