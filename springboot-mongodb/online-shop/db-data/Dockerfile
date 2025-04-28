FROM mongo:4.4

# Copiar los archivos JSON al contenedor
COPY ./data/init/customers.json /data/init/customers.json
COPY ./data/init/facturers.json /data/init/facturers.json
COPY ./data/init/products.json /data/init/products.json
COPY ./data/init/orders.json /data/init/orders.json

# Copiar el script de inicialización
COPY ./data/init/init_mongo.js /docker-entrypoint-initdb.d/init.js