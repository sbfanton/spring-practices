from pymongo import MongoClient
import json
import argparse


def get_keys(doc):
    keys = {}
    for key, value in doc.items():
        full_key = key
        
        if isinstance(value, dict):  # 🔹 Subdocumento -> Recursión
            if full_key in keys:
                keys[full_key].update(get_keys(value))
            else:
                keys[full_key] = get_keys(value)
        
        elif isinstance(value, list):  # 🔹 Lista -> Detectar tipos dentro
            element_types = set()
            sub_keys = {}

            for item in value:
                if isinstance(item, dict): 
                    sub_keys.update(get_keys(item))
                else:
                    element_types.add(type(item).__name__)  # Guardar tipos de elementos simples

            if sub_keys:
                keys[full_key] = [{k: v for k, v in sub_keys.items()}]
            elif element_types:
                keys[full_key] = f"list[{', '.join(element_types)}]"  # Si solo hay tipos simples

        else:
            keys[full_key] = type(value).__name__

    return keys


def get_all_keys(uri, db, col):
    client = MongoClient(uri)
    db = client[db]
    collection = db[col]

    all_keys = {}
    for doc in collection.find().limit(1000): # solucion provisoria a gran cantidad de datos
        all_keys.update(get_keys(doc))

    client.close()  # Cerrar la conexión
    return all_keys


def main():
    parser = argparse.ArgumentParser(description="Ejemplo de uso de argparse en Python.")
    parser.add_argument("db", help="Nombre de la base de datos")  # Argumento obligatorio
    parser.add_argument("collection", help="Nombre de la coleccion")  # Argumento obligatorio

    args = parser.parse_args()

    uri = "mongodb://localhost:27017/"
    db_name = args.db
    collection_name = args.collection

    keys = get_all_keys(uri, db_name, collection_name)
    print("Campos únicos en la colección:", keys)

    file_name = collection_name + "_schema.json"

    with open(file_name, "w") as json_file:
        json.dump(keys, json_file, indent=4)


if __name__ == "__main__":
    main()