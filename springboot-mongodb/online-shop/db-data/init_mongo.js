// Conectar o crear la base de datos
const dbName = "online-shop"
db = db.getSiblingDB(dbName);

// Crear un nuevo usuario con contraseña
const username = "user1";
const password = "1234";

// Crear el usuario
db.createUser ({
    user: username,
    pwd: password,
    roles: [
        { role: "readWrite", db: dbName } // Asigna el rol de lectura y escritura en la nueva base de datos
    ]
});

// Definir las colecciones
const collections = [
    ["customers", "./customers.json"], 
    ["products", "./products.json"], 
    ["orders", "./orders.json"], 
    ["facturers", "./facturers.json"]
];

// Función para cargar datos desde archivos JSON
// Función para cargar datos desde archivos JSON
function loadJSON(collectionName, filePath) {
    try {
        const data = cat(filePath);
        let jsonData = JSON.parse(data);

        // Convertir _id a ObjectId si existe
        if (Array.isArray(jsonData)) {
            jsonData = jsonData.map(doc => {
                if (doc._id) {
                    doc._id = ObjectId(doc._id);  // Convertir a ObjectId
                }
                return doc;
            });
            db[collectionName].insertMany(jsonData);
        } else {
            if (jsonData._id) {
                jsonData._id = ObjectId(jsonData._id); // Convertir a ObjectId
            }
            db[collectionName].insertOne(jsonData);
        }
        print(`✅ Datos insertados en ${collectionName} desde ${filePath}`);
    } catch (e) {
        print(`❌ Error cargando ${filePath}: ${e}`);
    }
}


// Crear colecciones si no existen
collections.forEach(coll => {
    if (!db.getCollectionNames().includes(coll[0])) {
        db.createCollection(coll[0]);
        print(`Colección creada: ${coll[0]}`);
        loadJSON(coll[0], coll[1])
    }
});