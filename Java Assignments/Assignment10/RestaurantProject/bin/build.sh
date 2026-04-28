#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  build.sh  —  compile & run the Restaurant JavaFX App
#
#  Prerequisites:
#    1. JDK 17+ installed
#    2. JavaFX SDK downloaded (https://gluonhq.com/products/javafx/)
#       e.g. extracted to ~/javafx-sdk-21
#    3. MySQL Connector/J jar (mysql-connector-j-8.x.x.jar)
#       placed in the lib/ folder
#
#  Usage:
#    chmod +x build.sh
#    ./build.sh
# ─────────────────────────────────────────────────────────────

# ── CONFIGURE THESE ──────────────────────────────────────────
JAVAFX_LIB="$HOME/javafx-sdk-21/lib"          # path to JavaFX lib folder
MYSQL_JAR="lib/mysql-connector-j-8.3.0.jar"  # path to MySQL connector jar
# ─────────────────────────────────────────────────────────────

SRC="src"
OUT="out"

mkdir -p "$OUT" lib

echo "Compiling..."
javac \
  --module-path "$JAVAFX_LIB" \
  --add-modules javafx.controls,javafx.fxml \
  -cp "$MYSQL_JAR" \
  -d "$OUT" \
  "$SRC"/*.java

if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi

echo "Running..."
java \
  --module-path "$JAVAFX_LIB" \
  --add-modules javafx.controls,javafx.fxml \
  -cp "$OUT:$MYSQL_JAR" \
  MainApp
