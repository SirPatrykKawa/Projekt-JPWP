import pymongo
from bson import ObjectId
import json


with open("dbsettings.json", 'r') as fin:
    settings = json.load(fin)
    fin.close()



# CONNECT TO DATABASE
client = pymongo.MongoClient(settings["server_ip"], int(settings["server_port"]))

# CREATE DATABASE
db = client[settings["database"]]
print("Database connected")

def get_db():
    return db


def add_new_rec(coll_name, doc_record):
    """
    Insert new password_record into collection
    :param coll_name:
    :param doc_record:
    :return:
    """
    coll = db[coll_name]

    document_id = coll.insert_one(doc_record).inserted_id
    return str(document_id)


def update_or_create_rec(coll_name, doc_id, doc_param):
    """
    This will create new document in collection
    IF same document ID exist then update the data
    :param coll_name:
    :param doc_id:
    :param doc_param:
    :return:
    """
    coll = db[coll_name]
    # TO AVOID DUPLICATES - THIS WILL CREATE NEW DOCUMENT IF SAME ID NOT EXIST
    document_ack = coll.update_one({'_id': ObjectId(doc_id)}, {"$set": doc_param}, upsert=True).acknowledged
    return document_ack


def get_single_rec(coll_name, doc_id):
    """
    get document data by document ID
    :param coll_name:
    :param doc_id:
    :return:
    """
    coll = db[coll_name]

    element = coll.find_one({'_id': ObjectId(doc_id)})
    return element


def get_multiple_rec(coll_name):
    """
    get document data by document ID
    :param coll_name:
    :return:
    """
    coll = db[coll_name]

    elements = coll.find()
    return list(elements)


def update_existing_rec(coll_name, doc_id, data):
    """
    Update existing document data by document ID
    :param coll_name:
    :param doc_id:
    :param data:
    :return:
    """
    coll = db[coll_name]

    document_ack = coll.update_one({'_id': ObjectId(doc_id)}, {"$set": data}).acknowledged
    return document_ack


def remove_rec(coll_name, doc_id):
    coll = db[coll_name]

    document_ack = coll.delete_one({'_id': ObjectId(doc_id)}).acknowledged
    return document_ack


# CLOSE DATABASE
client.close()