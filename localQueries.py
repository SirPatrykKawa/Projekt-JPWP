import json
import os
from bson import ObjectId
import pathlib as Path


with open("dbsettings.json", 'r') as fin:
    settings = json.load(fin)
    fin.close()


path_local_db = ''
if os.name == 'posix':
    path_local_db = Path.PurePosixPath(settings['local_db_path']).joinpath(Path.PurePosixPath(settings['local_db_name']))
elif os.name == 'nt':
    path_local_db = Path.PureWindowsPath(settings['local_db_path']).joinpath(Path.PureWindowsPath(settings['local_db_name']))


def local_read():
    """
    Fetch local database from a json file
    :return:
    """
    with open(path_local_db, 'r') as fin:
        database = json.load(fin)
        fin.close()
    return database


def local_write(database):
    """
    Write local database to a json file
    :param database:
    """
    with open(path_local_db, 'w') as fout:
        json.dump(database, fout)
        fout.close()


def add_document(coll, doc_id, data):
    """
    Insert new document into collection
    :param coll:
    :param doc_id:
    :param data:
    """
    database = local_read()

    if coll not in list(database.keys()):
        add_collection(coll)

    data['_id'] = doc_id
    database[coll].append(data)

    local_write(database)


def add_collection(coll):
    """
    Get collection from local database
    :param coll:
    """
    database = local_read()

    database[coll] = []

    local_write(database)


def get_document_by_id(coll, doc_id):
    """
    Get document from local database
    :param coll:
    :param doc_id:
    :return:
    """
    database = local_read()

    idx = 0
    for i, doc in enumerate(database[coll]):
        if doc["_id"] == ObjectId(doc_id):
            idx = i
    document = database[coll][idx]
    return document


def get_document_by_name(coll, doc_name):
    """
    Get document from local database
    :param coll:
    :param doc_id:
    :return:
    """
    database = local_read()

    idx = 0
    for i, doc in enumerate(database[coll]):
        if doc["name"] == doc_name:
            idx = i
    document = database[coll][idx]
    return document


def get_document_by_url(coll, doc_url):
    """
    Get document from local database
    :param coll:
    :param doc_id:
    :return:
    """
    database = local_read()

    idx = 0
    for i, doc in enumerate(database[coll]):
        if doc["url"] == doc_url:
            idx = i
    document = database[coll][idx]
    return document


def get_collection(coll):
    """
    Get collection from local database
    :param coll:
    :return:
    """
    database = local_read()

    if coll in list(database.keys()):
        return database[coll]
    else:
        return None


def update_document(coll, doc_id, data_to_change):
    """
    Update document in local database
    :param coll:
    :param doc_id:
    :param data_to_change:
    """
    database = local_read()

    for doc in database[coll]:
        if doc["_id"] == ObjectId(doc_id):
            for key, value in data_to_change.items():
                doc[key] = value

    local_write(database)


def remove_document(coll, doc_id):
    """
    Remove document from local database
    :param coll:
    :param doc_id:
    """
    database = local_read()

    for i, doc in enumerate(database[coll]):
        if doc["_id"] == ObjectId(doc_id):
            database[i].remove(doc)
            break

    local_write(database)


def remove_collection(coll):
    """
    Remove collection from local database
    :param coll:
    """
    database = local_read()

    del database[coll]

    local_write(database)