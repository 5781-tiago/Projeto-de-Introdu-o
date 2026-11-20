#!/bin/bash
# Repõe a base de dados do Portal ECL a partir de uma cópia.
# Uso: ./repor.sh /var/backups/portal-ecl/ecl_2026-11-27_0200.sql.gz

set -euo pipefail

if [ $# -ne 1 ] || [ ! -f "$1" ]; then
    echo "Uso: $0 <ficheiro .sql.gz>"
    exit 1
fi

read -r -p "Isto substitui a base de dados 'ecl' atual. Continuar? (s/N) " resposta
if [ "$resposta" != "s" ]; then
    echo "Cancelado."
    exit 0
fi

sudo systemctl stop tomcat10
gunzip -c "$1" | sudo mysql
sudo systemctl start tomcat10

echo "Base de dados reposta a partir de $1"
