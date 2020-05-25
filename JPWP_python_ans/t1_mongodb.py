import json
from pprint import *
import pymongo

# Zmień frazy w nawiasach trójkątnych na podane przez autorów ćwiczeń <database>-<username>-<password>
client = pymongo.MongoClient("mongodb+srv://<user>:<password>@mrpass-ssbep.mongodb.net/<database>?retryWrites=true&w=majority")
db = client.<database>

def get_collection():
    data = {}
    coll1 = db.coll1
    data['coll1'] = list(coll1.find())
    coll2 = db.coll2
    data['coll2'] = list(coll2.find())
    pprint(data)

def add_record():
    with open('./doc.json', 'r') as fin:
        document = json.load(fin)
        fin.close()

    collection = db.coll1
    id = collection.insert_one(document).inserted_id
    print('ID of the added document: {}'.format(str(id)))

def list_sorted_collection():
    coll2 = db.coll2
    data = coll2.find()
    data_sorted = sorted(data, key=lambda x: x['author'])
    pprint(list(data_sorted))

def remove_document_by_criteria():
    coll2 = db.coll2
    query = {'published': { '$lt': '2017-01-01' }}
    ack = coll2.delete_many(query)
    print('{} deleted documents.'.format(ack.deleted_count))

def main():
    get_collection()
    add_record()
    list_sorted_collection()
    remove_document_by_criteria()
    

if __name__ == "__main__":
    main()