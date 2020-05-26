#!/bin/sh

set -e

# Perform all actions as $POSTGRES_USER
export PGUSER="$POSTGRES_USER"

# DB container configuration
psql -c "ALTER SYSTEM SET max_connections = '150';"
psql -c "ALTER SYSTEM SET shared_buffers = '1GB';"
psql -c "ALTER SYSTEM SET effective_cache_size = '3GB';"
psql -c "ALTER SYSTEM SET maintenance_work_mem = '256MB';"
psql -c "ALTER SYSTEM SET checkpoint_completion_target = '0.7';"
psql -c "ALTER SYSTEM SET wal_buffers = '16MB';"
psql -c "ALTER SYSTEM SET default_statistics_target = '100';"
psql -c "ALTER SYSTEM SET random_page_cost = '1.1';"
psql -c "ALTER SYSTEM SET effective_io_concurrency = '200';"
psql -c "ALTER SYSTEM SET work_mem = '6990kB';"
psql -c "ALTER SYSTEM SET min_wal_size = '1GB';"
psql -c "ALTER SYSTEM SET max_wal_size = '2GB';"
psql -c "ALTER SYSTEM SET max_worker_processes = '2';"
psql -c "ALTER SYSTEM SET max_parallel_workers_per_gather = '1';"
psql -c "ALTER SYSTEM SET max_parallel_workers = '2';"

# add postgrereader user
psql -c "CREATE USER mbus_user WITH PASSWORD 'mbus123';"

# create databases
psql -c "CREATE DATABASE gis;"

# add extensions to databases
psql gis -c "CREATE EXTENSION IF NOT EXISTS postgis;"

# add shapefiles to database
shp2pgsql -I -s 4326 /src/metrobus/data/estaciones-metrobus.shp estaciones-metrobus | psql -U metro_user -d gis
shp2pgsql -I -s 4326 /src/metrobus/data/limite-de-las-alcaldias.shp limite-de-las-alcaldias | psql -U metro_user -d gis
shp2pgsql -I -s 4326 /src/metrobus/data/lineas-metrobus.shp lineas-metrobus | psql -U metro_user -d gis
shp2pgsql -I -s 4326 /src/metrobus/data/prueba_fetchdata_metrobus.shp prueba_fetchdata_metrobus | psql -U metro_user -d gis
