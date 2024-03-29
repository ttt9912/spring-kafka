- https://www.baeldung.com/spring-boot-kafka-ssl
- https://stackoverflow.com/questions/47434877/how-to-generate-keystore-and-truststore

# Create Server Certificate

### Create Server Keystore

`$ keytool -genkey -alias bmc -keyalg RSA -keystore kafka.server.keystore.jks -keysize 2048`

- Keystore password: password
- What is your first and last name?:  localhost

### Create Certificate and Private Key

`$ openssl req -new -x509 -keyout ca-key-server -out ca-cert-server`

- PEM: password
- Common Name (eg, fully qualified host name) []: localhost

### Create cert sign request

`$ keytool -keystore kafka.server.keystore.jks -alias bmc -certreq -file cert-file-server`

### Sign the certificate, create cert-signed-server

`$ openssl x509 -req -CA ca-cert-server -CAkey ca-key-server -in cert-file-server -out cert-signed-server -days 365 -CAcreateserial -passin pass:password`

### Import ca-cert-server to keystore

`$ keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert-server`

### Import cert-signed-server to keystore

`$ keytool -keystore kafka.server.keystore.jks -alias bmc -import -file cert-signed-server`

---

# Create Client Certificate

### Create Client Keystore

`$ keytool -genkey -alias bmc -keyalg RSA -keystore kafka.client.keystore.jks -keysize 2048`

- Keystore password: password
- What is your first and last name?:  localhost

### Create Certificate and Private Key

`$ openssl req -new -x509 -keyout ca-key-client -out ca-cert-client`

- PEM: password
- Common Name (eg, fully qualified host name) []: localhost

### Create cert sign request

`$ keytool -keystore kafka.client.keystore.jks -alias bmc -certreq -file cert-file-client`

### Sign the certificate, create cert-signed-client

`$ openssl x509 -req -CA ca-cert-client -CAkey ca-key-client -in cert-file-client -out cert-signed-client -days 365 -CAcreateserial -passin pass:password`

### Import ca-cert-client to keystore

`$ keytool -keystore kafka.client.keystore.jks -alias CARoot -import -file ca-cert-client`

### Import cert-signed-client to keystore

`$ keytool -keystore kafka.client.keystore.jks -alias bmc -import -file cert-signed-client`

---

# Create Client Truststore and Import Server Certificate

`$ keytool -keystore kafka.client.truststore.jks -alias bmc -import -file ca-cert-server`

---

# Create Server Truststore and Import Client Certificate

`$ keytool -keystore kafka.server.truststore.jks -alias bmc -import -file ca-cert-client`

---

# Manually Create Credentials Files for Kafka Docker

`$ echo password >> kafka_keystore_credentials`

`$ echo password >> kafka_sslkey_credentials`

`$ echo password >> kafka_truststore_credentials`
