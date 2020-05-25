import json
import os
from bson import ObjectId
import pathlib as Path


with open("dbsettings.json", 'r') as fin:
    settings = json.load(fin)
    fin.close()


path_local_db = ''
if os.name == 'posix':
    path_local_db = Path.PurePosixPath(settings['local_db_path']).joinpath(
        Path.PurePosixPath(settings['local_db_name']))
elif os.name == 'nt':
    path_local_db = Path.PureWindowsPath(settings['local_db_path']).joinpath(
        Path.PureWindowsPath(settings['local_db_name']))


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
    try:
        database = local_read()

        if coll not in list(database.keys()):
            database[coll] = []

        data['_id'] = doc_id
        database[coll].append(data)

        local_write(database)
        return True
    except:
        return False


def get_document_by_id(coll, doc_id):
    """
    Get document from local database
    :param coll:
    :param doc_id:
    :return:
    """
    database = local_read()
    document = {}
    
    idx = -1
    for i, doc in enumerate(database[coll]):
        if doc["_id"] == doc_id:
            idx = i
            if idx != -1:
                break
    if idx != -1:
        document = database[coll][idx]
    return document


def get_document_by_name(coll, doc_name):
    """
    Get document from local database
    :param coll:
    :param doc_name:
    :return:
    """
    database = local_read()
    document = {}
    
    idx = -1
    for i, doc in enumerate(database[coll]):
        if doc["name"] == doc_name:
            idx = i
            if idx != -1:
                break
    if idx != -1:
        document = database[coll][idx]
    return document


def get_note(coll, doc_number):
    """
    Get document from local database
    :param coll:
    :param doc_number:
    :return:
    """
    database = local_read()
    document = {}
    
    idx = -1
    for i, doc in enumerate(database[coll]):
        if doc["number"] == doc_number:
            idx = i
            if idx != -1:
                break
    if idx != -1:
        document = database[coll][idx]
    return document


def get_document_by_url(coll, doc_url):
    """
    Get document from local database
    :param coll:
    :param doc_url:
    :return:
    """
    database = local_read()
    document = {}

    idx = -1
    for i, doc in enumerate(database[coll]):
        if doc["url"] == doc_url:
            idx = i
            if idx != -1:
                break
    if idx != -1:
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


def update_document_by_id(coll, doc_id, data_to_change):
    """
    Update first document that meets the reqs' in local database
    :param coll:
    :param doc_id:
    :param data_to_change:
    """
    try:
        database = local_read()

        for doc in database[coll]:
            if doc["_id"] == doc_id:
                for key, value in data_to_change.items():
                    doc[key] = value
                break

        local_write(database)
        return True
    except:
        return False


def update_document_by_name(coll, doc_name, data_to_change):
    """
    Update first document that meets the reqs' in local database
    :param coll:
    :param doc_name:
    :param data_to_change:
    """
    try:
        database = local_read()

        for doc in database[coll]:
            if doc["name"] == doc_name:
                for key, value in data_to_change.items():
                    doc[key] = value
                    break

        local_write(database)
        return True
    except:
        return False


def update_note(coll, doc_number, data_to_change):
    """
    Update first note document that meets the reqs' in local database
    :param coll:
    :param doc_id:
    :param data_to_change:
    """
    try:
        database = local_read()

        for doc in database[coll]:
            if doc["number"] == doc_number:
                for key, value in data_to_change.items():
                    doc[key] = value
                break

        local_write(database)
        return True
    except:
        return False


def remove_document_by_id(coll, doc_id):
    """
    Remove document from local database
    :param coll:
    :param doc_id:
    """
    try:
        database = local_read()
        doc_ref = {}
        for doc in database[coll]:
            if doc['_id'] == doc_id:
                doc_ref = doc
                break
        if doc_ref['_id'] != '':
            if 'DELETED' not in database.keys():
                database['DELETED'] = []
            database['DELETED'].append({'ID': doc_ref['_id']})
        database[coll].remove(doc_ref)

        local_write(database)
        return True
    except:
        return False


def remove_document_by_name(coll, doc_name):
    """
    Remove document from local database
    :param coll:
    :param doc_name:
    """
    try:
        database = local_read()
        doc_ref = {}
        for doc in database[coll]:
            if doc['name'] == doc_name:
                doc_ref = doc
                break
        if doc_ref['_id'] != '':
            if 'DELETED' not in database.keys():
                database['DELETED'] = []
            database['DELETED'].append({'ID': doc_ref['_id']})
        database[coll].remove(doc_ref)

        local_write(database)
        return True
    except:
        return False


def remove_note(coll, doc_number):
    """
    Remove document from local database
    :param coll:
    :param doc_number:
    """
    try:
        database = local_read()
        doc_ref = {}
        for doc in database[coll]:
            if doc['number'] == doc_number:
                doc_ref = doc
                break
        if doc_ref['_id'] != '':
            if 'DELETED' not in database.keys():
                database['DELETED'] = []
            database['DELETED'].append({'ID_note': doc_ref['_id']})
        database[coll].remove(doc_ref)

        local_write(database)
        return True
    except:
        return False


def remove_collection(coll):
    """
    Remove collection from local database
    :param coll:
    """
    database = local_read()

    del database[coll]

    local_write(database)