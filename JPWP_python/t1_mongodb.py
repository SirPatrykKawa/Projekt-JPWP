import json
import pymongo

# Zmień frazy w nawiasach trójkątnych na podane przez autorów ćwiczeń <database>-<username>-<password>
client = pymongo.MongoClient("mongodb+srv://<username>:<password>@mrpass-ssbep.mongodb.net/<database>?retryWrites=true&w=majority")
db = client.<database>

def get_collection():
    pass

def add_record():
    pass

def remove_document_by_criteria():
    pass

def list_collection():
    pass

def main():
    get_collection()
    add_record()
    remove_document_by_criteria()
    list_collection()


if __name__ == "__main__":
    main()