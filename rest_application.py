from pathlib import Path
import json

from flask import Flask, jsonify, request, Response
from flask_restful import Resource, Api, reqparse

import localQueries as lQ
import manage_settings as ms
import synchronise as sync


path = Path('.')


application = Flask(__name__)
api = Api(application)


###################################################################################################
###                                                                                             ###
###-------------------------------------------CODE----------------------------------------------###
###                                                                                             ###
###################################################################################################
class LogIn(Resource):
    def get(self):
        """
        Check users credentials
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('login', type=str, case_sensitive=True)
        parser.add_argument('password', type=str, case_sensitive=True)
        args = parser.parse_args()

        if sync.check_login(args['login']) and sync.check_password(args['password']):
            return Response('LOGIN success', mimetype='text/html')
        else:
            return Response('LOGIN failure', mimetype='text/html')


class Synchronise(Resource):
    def get(self):
        """
        Synchronise local and remote database
        :return:
        """
        if sync.synchronise():
            return Response('SYNC success', mimetype='text/html')
        else:
            return Response('SYNC failure', mimetype='text/html')


class Setup(Resource):
    def get(self):
        """
        Fetch setup from dbsettings.json file
        :return:
        """
        settings = ms.get_settings(path)
        if settings is not None:
            return jsonify(settings)
        else:
            return Response('SETUP_FETCH failure', mimetype='text/html')

    def post(self):
        """
        Set setup
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('login', type=str, case_sensitive=True)
        parser.add_argument('password', type=str, case_sensitive=True)
        parser.add_argument('server_ip', type=str)
        parser.add_argument('server_port', type=int)
        parser.add_argument('database', type=str, case_sensitive=True)
        parser.add_argument('local_db_path', type=str, case_sensitive=True)
        parser.add_argument('local_db_name', type=str, case_sensitive=True)
        data = parser.parse_args()

        if ms.collect_settings(path, data['login'], data['password'], data['server_ip'], data['server_port'], data['database'], data['local_db_path'], data['local_db_name']):
            return Response('SETUP success', mimetype='text/html')
        else:
            return Response('SETUP failure', mimetype='text/html')

    def put(self):
        """
        Update setup
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('position_name', type=str, case_sensitive=True)
        parser.add_argument('new_value', type=str, case_sensitive=True)
        change = parser.parse_args()

        if ms.change_settings(path, change['position_name'], change['new_value']):
            return Response('SETUP_CHANGE success', mimetype='text/html')
        else:
            return Response('SETUP_CHANGE failure', mimetype='text/html')


class GetRecord(Resource):
    def get(self, coll, criteria, value):
        """
        Fetch record from database
        :return:
        """
        if criteria == 'id':
            return jsonify(lQ.get_document_by_id(coll, value))
        elif criteria == 'name':
            return jsonify(lQ.get_document_by_name(coll, value))
        elif criteria == 'url':
            return jsonify(lQ.get_document_by_url(coll, value))
        else:
            return Response('REC_FETCH failure', mimetype='text/html')


class AddRecord(Resource):
    def post(self, coll):
        """
        Add record to local database
        :param coll:
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('name', type=str, case_sensitive=True)
        parser.add_argument('login', type=str, case_sensitive=True)
        parser.add_argument('passord', type=str, case_sensitive=True)
        parser.add_argument('url', type=str, case_sensitive=True)
        parser.add_argument('expires', type=str)
        parser.add_argument('expires_when', type=str)
        parser.add_argument('description', type=str, case_sensitive=True)
        parser.add_argument('created_when', type=str)
        parser.add_argument('last_modified', type=str)

        data = parser.parse_args()

        if lQ.add_document(coll, "", data):
            return Response('REC_ADD success', mimetype='text/html')
        else:
            return Response('REC_ADD failure', mimetype='text/html')


class ManageRecord(Resource):
    def put(self, coll, id):
        """
        Uodate record in a local database
        :param coll:
        :param id:
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('last_modified', type=str)
        parser.add_argument('position_to_change', type=str, case_sensitive=True)
        parser.add_argument('new_value', type=str, case_sensitive=True)
        args = parser.parse_args()

        data = {'last_modified': args['last_modified'], args['position_to_change']: args['new_value'] }

        if lQ.update_document(coll, id, data):
            return Response('REC_UPDATE success', mimetype='text/html')
        else:
            return Response('REC_UPDATE failure', mimetype='text/html')

    def delete(self, coll, id):
        """
        Delete a record from a local database
        :param coll:
        :param id:
        :return:
        """
        if lQ.remove_document(coll, id):
            return Response('REC_DEL success', mimetype='text/html')
        else: 
            return Response('REC_DEL failure', mimetype='text/html')


class ManageCollections(Resource):
    def get(self, coll):
        """
        Fetch a full collection from a local database
        :param coll:
        :return:
        """
        return jsonify(lQ.get_collection(coll))

    def delete(self, coll):
        """
        Delete a full collection from local database
        :param coll:
        :return:
        """
        if lQ.remove_collection(coll):
            return Response('COLL_DEL success', mimetype='text/html')
        else:
            return Response('COLL_DEL failure', mimetype='text/html')


class ManageNotes(Resource):
    def get(self, number):
        """
        Add a nate to a local database
        :param number:
        :return:
        """
        return lQ.get_note(number)
    
    def post(self, number):
        """
        Add a nate to a local database
        :param number:
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('description', type=str, case_sensitive=True)
        parser.add_argument('created_when', type=str, case_sensitive=True)
        parser.add_argument('last_modified', type=str, case_sensitive=True)
        args = parser.parse_args()

        data = {'number': number, 'description': args['description'], 'created_when': args['created_when'], 'last_modified': args['last_modified']}

        if lQ.add_document('notes', "", data):
            return Response('NOTE_ADD success', mimetype='text/html')
        else:
            return Response('NOTE_ADD failure', mimetype='text/html')

    def put(self, number):
        """
        Update a note in a local database
        :param number:
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('last_modified', type=str)
        parser.add_argument('position_to_change', type=str, case_sensitive=True)
        parser.add_argument('new_value', type=str, case_sensitive=True)
        args = parser.parse_args()

        data = {'last_modified': args['last_modified'], args['position_to_change']: args['new_value'] }

        if lQ.update_document('notes', number, data):
            return Response('NOTE_UPDATE success', mimetype='text/html')
        else:
            return Response('NOTE_UPDATE failure', mimetype='text/html')

    def delete(self, number):
        """
        Delete a note from a database
        :param number:
        :return:
        """
        if lQ.remove_document('notes', number):
            return Response('NOTE_DEL success', mimetype='text/html')
        else: 
            return Response('NOTE_DEL failure', mimetype='text/html')


###################################################################################################
###                                                                                             ###
###-------------------------------------------TEST----------------------------------------------###
###                                                                                             ###
###################################################################################################
class Test(Resource):
    def get(self):
        """
        Simple test for an API
        :return:
        """
        return Response('TEST success', mimetype='text/html')


class TestJson(Resource):
    def get(self):
        """
        Json test for an API with a GET method 
        :return:
        """
        test_json = {"name": "CrazyBoi", "date": "2020-07-19-13:40:37"}
        return jsonify(test_json)

    def post(self):
        """
        Json test for an API with a POST method
        :return:
        """
        parser = reqparse.RequestParser()
        parser.add_argument('test', type=str)
        parser.add_argument('test_line_2', type=str)
        args = parser.parse_args()

        if args['test'] == "line_1" and args['test_line_2'] == "works!":
            return Response('TEST_JSON success', mimetype='text/html')
        else:
            return Response('TEST_JSON failure', mimetype='text/html')


class TestJsonList(Resource):
    def get(self):
        """
        Json list test for an API with a GET method 
        :return:
        """
        test_json = [{"name": "CrazyBoi", "date": "2020-07-19-13:40:37"}, {"pss": "1249jiosndf9jr(*&DiBI*DT#*@HRIJNF CWS*(AE&RG&#()$&@)#$(!$&)#!&", "test_goes": "now"}]
        return jsonify(test_json)


###################################################################################################
###                                                                                             ###
###-----------------------------------REST_ADDRESS_ASOCIATION-----------------------------------###
###                                                                                             ###
###################################################################################################
api.add_resource(LogIn, '/login')
api.add_resource(Synchronise, '/sync')
api.add_resource(Setup, '/setup')

api.add_resource(GetRecord, '/db/<string:coll>/<string:criteria>/<string:value>')
api.add_resource(AddRecord, '/db/<string:coll>/add')
api.add_resource(ManageRecord, '/db/<string:coll>/<id>')

api.add_resource(ManageCollections, '/db/<string:coll>')
api.add_resource(ManageNotes, '/db/notes/<int:number>')

api.add_resource(Test, '/test')
api.add_resource(TestJson, '/test/json')
api.add_resource(TestJsonList, '/test/json_coll')


###################################################################################################
###                                                                                             ###
###-------------------------------------------MAIN----------------------------------------------###
###                                                                                             ###
###################################################################################################
if __name__ == "__main__":
    application.run(host="127.0.0.1", port=12715, debug=True)


