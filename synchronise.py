import localQueries as lq
import mongoQueries as mq
import datetime as dt
import base64
import json
from bson import ObjectId

class Date:
    def __init__(self, date:str):
        # date_format: "YYYY-MM-DD-hh:mm:ss"
        date_split = date.split('-')
        self.year = int(date_split[0])
        self.month = int(date_split[1])
        self.day = int(date_split[2])

        time = date_split[3].split(':')
        self.hour = int(time[0])
        self.minute = int(time[1])
        self.second = int(time[2])


def check_login(login) -> bool:
    with open("dbsettings.json", 'r') as fin:
        settings = json.load(fin)

    if settings['login'] == login:
        return True
    else:
        return False


def check_password(password) -> bool:
    with open("dbsettings.json", 'r') as fin:
        settings = json.load(fin)

    if settings['password'] == password:
        return True
    else:
        return False


def compare_dates(date1, date2) -> str:
    d1 = Date(date1); d2 = Date(date2)
    date_1 = dt.datetime(d1.year, d1.month, d1.day, d1.hour, d1.minute, d1.second)
    date_2 = dt.datetime(d2.year, d2.month, d2.day, d2.hour, d2.minute, d2.second)

    if date_1 > date_2:
        return 'later'
    elif date_1 < date_2:
        return 'sooner'
    else:
        return 'equal'


def synchronise() -> bool:

    try:
        local = lq.local_read()
        remote = mq.get_db()

        # TODO: How to manage removed documents??

        for coll_name, collection in local.items():
            for document in collection:
                if document['_id'] == "":
                    del document['_id']
                    id = mq.add_new_rec(coll_name, document)
                    document['_id'] = str(id)
                
                loc_date = document['last_modified']
                rem_date = mq.get_single_rec(coll_name, document['_id'])['last_modified']

                if compare_dates(loc_date, rem_date) == 'later':
                    mq.update_existing_rec(coll_name, document['_id'], document)

        for collection in remote.list_collections():
            for document in mq.get_multiple_rec(collection):
                if lq.get_document_by_id(local[collection.name], document['_id']) in None:
                    local.add_document(collection.name, document['_id'], document)
                    break

                rem_date = document['last_modified']
                loc_date = lq.get_document_by_id(collection.name, document['_id'])

                if compare_dates(rem_date, loc_date) == 'later':
                    lq.update_document_by_id(collection.name, document['_id'], document)
    
        return True
    except:
        return False