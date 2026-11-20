#!/bin/bash
# Cópia de segurança da base de dados do Portal ECL.
# Uso: ./backup.sh            (guarda em /var/backups/portal-ecl)
#      ./backup.sh /outra/pasta

set -euo pipefail

DESTINO="${1:-/var/backups/portal-ecl}"
DATA=$(date +%Y-%m-%d_%H%M)
FICHEIRO="$DESTINO/ecl_$DATA.sql.gz"

sudo mkdir -p "$DESTINO"
sudo chown "$(id -un)" "$DESTINO"

sudo mysqldump \
    --single-transaction --routines --triggers --databases ecl \
    | gzip > "$FICHEIRO"

find "$DESTINO" -name "ecl_*.sql.gz" -mtime +14 -delete

echo "Cópia criada: $FICHEIRO ($(du -h "$FICHEIRO" | cut -f1))"
