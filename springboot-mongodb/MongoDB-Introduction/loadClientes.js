// Selecciona la base de datos (se crea automáticamente si no existe)
db = db.getSiblingDB("tiendaOnline")

db.clientes.drop()

// Inserta datos en la colección "clientes"
db.clientes.insertMany([
    { "nombre": "Juan Pérez", "edad": 32, "email": "juan.perez@example.com", "telefono": "555-1234", "direccion": { "calle": "Av. Siempre Viva", "numero": 742, "ciudad": "Springfield" } },
    { "nombre": "María López", "edad": 28, "email": "maria.lopez@example.com", "telefono": "555-5678", "direccion": { "calle": "Calle Falsa", "numero": 123, "ciudad": "Shelbyville" } },
    { "nombre": "Carlos Rodríguez", "edad": 45, "email": "carlos.rod@example.com", "telefono": "555-8765", "direccion": { "calle": "Boulevard Central", "numero": 456, "ciudad": "Capital City" } },
    { "nombre": "Ana Martínez", "edad": 36, "email": "ana.martinez@example.com", "telefono": "555-2345", "direccion": { "calle": "Av. Libertador", "numero": 789, "ciudad": "Metropolis" } },
    { "nombre": "Pedro Gómez", "edad": 50, "email": "pedro.gomez@example.com", "telefono": "555-4321", "direccion": { "calle": "Calle Mayor", "numero": 321, "ciudad": "Gotham" } }
]);

// Verifica que los datos se insertaron correctamente
print("Base de datos 'tiendaOnline' creada con éxito.");
print("Total de documentos en 'clientes':", db.clientes.count());