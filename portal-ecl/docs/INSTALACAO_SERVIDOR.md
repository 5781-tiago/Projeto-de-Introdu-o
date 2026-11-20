# Instalação do servidor (máquina virtual)

Guia para pôr o Portal ECL a funcionar numa máquina virtual **Ubuntu Server 24.04 LTS** no VirtualBox, acessível a partir de outro computador da rede do laboratório.

**Porquê Ubuntu Server:** é gratuito, leve (corre sem interface gráfica), tem o Tomcat 10.1, o Java e o MariaDB nos repositórios oficiais e é muito usado em servidores reais. As atualizações de segurança são garantidas até 2029.

---

## 1. Criar a máquina virtual

1. No VirtualBox: **Nova** → nome `portal-ecl`, tipo Linux, Ubuntu (64-bit).
2. 2 GB de RAM, 2 CPU, disco de 20 GB.
3. **Definições → Rede → Adaptador 1 → Ligada a: Placa em modo Bridge** (*Bridged Adapter*). Assim a VM recebe um IP da rede do laboratório e os outros PCs conseguem chegar a ela.
4. Instalar o Ubuntu Server a partir da ISO. Durante a instalação, marcar **Install OpenSSH server**.

## 2. IP fixo

Ver o nome da placa de rede e o IP atual:

```bash
ip a
```

Editar a configuração (o nome do ficheiro pode variar):

```bash
sudo nano /etc/netplan/50-cloud-init.yaml
```

Exemplo (ajustar a placa, o IP, o gateway e o DNS à rede do laboratório):

```yaml
network:
  version: 2
  ethernets:
    enp0s3:
      dhcp4: false
      addresses: [192.168.1.50/24]
      routes:
        - to: default
          via: 192.168.1.1
      nameservers:
        addresses: [192.168.1.1, 8.8.8.8]
```

```bash
sudo netplan apply
ip a
```

## 3. Instalar Java, Tomcat e MariaDB

```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y tomcat10 mariadb-server
java -version
systemctl status tomcat10 mariadb
```

O pacote `tomcat10` instala o Tomcat 10.1 (compatível com Jakarta Servlet 6) e o Java. Os dois serviços ficam configurados para **arrancar sozinhos quando a VM reinicia** (teste T09).

Proteger o MariaDB:

```bash
sudo mariadb-secure-installation
```

## 4. Criar a base de dados

Copiar `database/ecl.sql` para a VM (por exemplo com `scp`) e, **antes de o executar**, trocar `MUDAR_ESTA_PASSWORD` por uma password forte. Depois:

```bash
sudo mariadb < ecl.sql
sudo mariadb -e "SHOW TABLES FROM ecl;"
```

## 5. Configuração da ligação (fora do repositório)

As credenciais da base de dados **não estão no GitHub**. Criar o ficheiro de configuração no Tomcat:

```bash
sudo nano /var/lib/tomcat10/conf/ecl-db.properties
```

```properties
db.url=jdbc:mysql://localhost:3306/ecl?useSSL=false&serverTimezone=Europe/Lisbon&characterEncoding=UTF-8
db.user=ecl_app
db.password=A_PASSWORD_ESCOLHIDA_NO_PASSO_4
```

```bash
sudo chown root:tomcat /var/lib/tomcat10/conf/ecl-db.properties
sudo chmod 640 /var/lib/tomcat10/conf/ecl-db.properties
```

## 6. Instalar a aplicação

No computador de desenvolvimento:

```bash
mvn clean package
scp target/ecl.war utilizador@192.168.1.50:/tmp/
```

Na VM:

```bash
sudo cp /tmp/ecl.war /var/lib/tomcat10/webapps/
sudo systemctl restart tomcat10
```

Testar na própria VM: `curl -I http://localhost:8080/ecl/inicio`

## 7. Firewall

```bash
sudo ufw allow OpenSSH
sudo ufw allow 8080/tcp
sudo ufw enable
sudo ufw status
```

A porta do MariaDB (3306) **não** é aberta: só a aplicação, na própria VM, acede à base de dados.

## 8. Acesso a partir de outro computador

Noutro PC da mesma rede, abrir no navegador:

```
http://192.168.1.50:8080/ecl/
```

(teste T01)

## 9. Passwords da área reservada

O script SQL cria os utilizadores `gestor` e `tecnico` com passwords de demonstração (guardadas em hash). Para definir uma password nova, gerar o hash no computador de desenvolvimento:

```bash
java -cp target/classes pt.ecl.util.Senhas "NovaPassword"
```

E atualizar na VM:

```bash
sudo mariadb ecl -e "UPDATE utilizadores SET password_hash='HASH_GERADO' WHERE identificador='gestor';"
```

## 10. Cópias de segurança

```bash
sudo mkdir -p /opt/portal-ecl
sudo cp scripts/backup.sh scripts/repor.sh /opt/portal-ecl/
sudo chmod +x /opt/portal-ecl/*.sh
/opt/portal-ecl/backup.sh
ls -lh /var/backups/portal-ecl/
```

Cópia automática todos os dias às 02:00:

```bash
sudo crontab -e
```

```
0 2 * * * /opt/portal-ecl/backup.sh >> /var/log/portal-ecl-backup.log 2>&1
```

**Reposição** (teste T10):

```bash
/opt/portal-ecl/repor.sh /var/backups/portal-ecl/ecl_AAAA-MM-DD_HHMM.sql.gz
```

O script para o Tomcat, repõe a base de dados e volta a arrancar o Tomcat.

## 11. Comandos úteis para a demonstração

```bash
ip a                                   # mostrar o IP
systemctl status tomcat10 mariadb      # serviços a correr
sudo ufw status                        # firewall
sudo mariadb ecl -e "SELECT codigo, estado FROM ocorrencias;"
sudo tail -f /var/log/tomcat10/catalina.out   # registos do Tomcat
```
