#!/bin/bash

# Variables
SRC_DIR="src"
OUT_DIR="out"
RESOURCES_DIR="src/resources"
MAIN_CLASS="main.Main"

echo "== Limpiando carpeta de salida =="
rm -rf $OUT_DIR
mkdir -p $OUT_DIR

echo "== Compilando archivos fuente =="
find $SRC_DIR -name "*.java" | xargs javac -d $OUT_DIR

if [ $? -ne 0 ]; then
    echo "Error al compilar. Por favor revisa tu código."
    exit 1
fi

echo "== Copiando recursos =="
if [ -d "$RESOURCES_DIR" ]; then
    cp -r $RESOURCES_DIR/* $OUT_DIR/
else
    echo "Error: El directorio de recursos $RESOURCES_DIR no existe."
    exit 1
fi

if [ ! -f "$OUT_DIR/config.properties" ]; then
    echo "Error: El archivo config.properties no se copió correctamente al directorio $OUT_DIR"
    exit 1
fi

echo "== Ejecutando aplicación =="

# Ajustando el classpath según el sistema operativo
if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    SEP=":"
elif [[ "$OSTYPE" == "msys"* ]]; then
    SEP=";"
else
    SEP=":"  # Asumimos Linux por defecto si el SO no es reconocido.
fi

java -cp "$OUT_DIR" $MAIN_CLASS

