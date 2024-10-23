#!/bin/sh

# Ejecuta el cliente mysql dentro del contenedor `tareas-db`
docker exec -it PontiSabe \
  mysql -p