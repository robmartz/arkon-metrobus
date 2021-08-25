# arkon-metrobus
 Pipeline de análisis de datos utilizando los datos abiertos de la Ciudad de México del Metrobus

## Introducción

Este repositorio contiene el pipeline para analizar datos abiertos del Metrobus de la Ciudad de México. En particular, dichos datos han sido puestos en un contenedor docker en una base de datos georeferenciada PotsgreSQL+PostGIS. El código scala para hacer las consultas a dicha base de datos se crea de manera independiante aunque enlazados a través de la misma `network`. 


![image](https://github.com/robmartz/arkon-metrobus/blob/master/image/metrobus2.png?raw=true)


## Base de datos

Para correr el análisis, basta ejecutar el siguiente comando en la carpeta donde se ha clonado el repositorio:

```console
foo@bar:~$ docker-compose up --build -d
```
Una vez finalizada la instalación de la base de datos, veremos un mensaje como este

```console
Creating mbus_db_container ... done
```

Verificamos que el contenedor este activo, para ello ejecutamos:

```console
foo@bar:~$ docker container ls 
```
donde veremos una salida como la siguiente:

```console
ONTAINER ID        IMAGE                COMMAND                  CREATED             STATUS              PORTS                    NAMES
304b266fa014        metrobus             "docker-entrypoint.s…"   4 minutes ago       Up 3 minutes        0.0.0.0:9595->5432/tcp   mbus_db_container
```

## Contenedor Scala

Con esto estará corriendo el contenedor de la base de datos. A continuación ejecutamos el siguiente comando para levantar el contendor que tiene el código que hace consultas a la base de datos Postgres.

```console
foo@bar:~$ docker run -it --rm --network=arkon-metrobus_postgres_conn --name=scala_container -v $(pwd)/src:/src/code hseeberger/scala-sbt:8u222_1.3.5_2.13.1 bash
```
Podemos verificar en otra consola que ambos contenedores están funcionando y se encuentran en la misma `network`. Si ejecutamos `$ docker container ls` veremos una salida en consola como la siguiente:

```console
CONTAINER ID        IMAGE                                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
1fa5dc76ccb2        hseeberger/scala-sbt:8u222_1.3.5_2.13.1   "bash"                   9 seconds ago       Up 4 seconds                                 scala_container
ab07f96dafaa        metrobus                                  "docker-entrypoint.s…"   20 seconds ago      Up 15 seconds       0.0.0.0:9595->5432/tcp   mbus_db_container
```

Una vez ejecutado el comando que levanta el contenedor, estaremos dentro del contenedor que tiene el código scala para hacer consultas a la base de datos, veremos algo como esto:

```console
root@50254eb0118f:~#
```
A continuación ejecutamos la siguiente línea: 

```console
root@50254eb0118f:~# cd /src/code/ && sbt run > resultado.txt
```

## Resultados

Al iniciar el contenedor con las dependencias necesarias se creó un volumen de datos, en este caso la carpeta `src/` del repositorio, de este modo, fuera del contenedor podemos consultar el archivo de texto creado con los resultados de la consulta a la base de datos. Una fracción de dicho archivo es:

```**********************************************
           Alcaldías disponibles:  
**********************************************
> Álvaro Obregón
> Azcapotzalco
> Benito Juárez
> Coyoacán
> Cuauhtémoc
> Gustavo A. Madero
...

**********************************************
     Unidades disponibles por alcaldía:       
**********************************************
> Álvaro Obregón | 1255
> Azcapotzalco 	 | 1075
> Azcapotzalco 	 | 1141
> Azcapotzalco 	 | 1112
> Azcapotzalco 	 | 1110
> Azcapotzalco 	 | 1101
...

**********************************************
          Rutas por alcaldía:            
**********************************************
> MB01-B (Indios Verdes - Caminero) | Álvaro Obregón
> MB01-C (Galvez - Indios Verdes)   | Álvaro Obregón
> MB01-C (Indios Verdes - Galvez)   | Álvaro Obregón
> MB01-D (Buenavista - Caminero)    | Álvaro Obregón
...

**********************************************
          Estaciones por alcaldía:            
**********************************************
> Álvaro Obregón 	| José María Velasco
> Álvaro Obregón 	| Altavista
> Álvaro Obregón 	| Olivo
> Álvaro Obregón 	| Dr. Gálvez
...
```




