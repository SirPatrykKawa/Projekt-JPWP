import json
from flask import Flask, jsonify, request, Response
from flask_restful import Resource, Api, reqparse

application = Flask(__name__)
api = Api(application)


"""
Użyj:
 - klas, 
 - reqparse,(pamiętaj o opcjach parsera, aby dane były dobrze przechwycone)
aby odwzorować API z t2_flask.py
"""
###---DUUUUUUUUŻO MIEJSCA NA KODZIK---###
class Send(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('message', type=str)
        parser.add_argument('author', type=str)
        args = parser.parse_args()

        return jsonify(args)


class AddLocal(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('name')
            parser.add_argument('floors')
            parser.add_argument('built')
            parser.add_argument('town')
            parser.add_argument('country')
            args = parser.parse_args()

            with open('./local.json', 'r') as fin:
                data = json.load(fin)
                fin.close()
        
            lst = list(data.keys())
            lst = lst.sort()
            number = lst[-1] + 1
            data[number] = args

            with open('./local.json', 'w') as fout:
                json.dump(data, fout, indent=2)
                fout.close()
            
            return Response("DOC_ADD success. Number of new document is: {}".format(number), mimetype='text/html')
        except:
            return Response("DOC_ADD failure", mimetype='text/html')


class ManageLocal(Resource):
    def get(self, number):
        with open('./local.json', 'r') as fin:
            data = json.load(fin)
            fin.close()
    
        return jsonify(data[number])

    def put(self, number):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('field')
            parser.add_argument('new_value')
            args = parser.parse_args()

            with open('./local.json', 'r') as fin:
                data = json.load(fin)
                fin.close()

            data[number][args[field]] = args['new_value']

            with open('./local.json', 'w') as fout:
                json.dump(data, fout, indent=2)
                fout.close()

            return Response('DOC_UPDATE success', mimetype='text/html')
        except:
            return Response('DOC_UPDATE failure', mimetype='text/html')

    def delete(self, number):
        try:
            with open('./local.json', 'r') as fin:
                data = json.load(fin)
                fin.close()

            del data[number]

            with open('./local.json', 'w') as fout:
                json.dump(data, fout, indent=2)
                fout.close()

            return Response("DOC_DEL success", mimetype='text/html')
        except:
            return Response("DOC_DEL failure", mimetype='text/html')


api.add_resource(Send, '/send')
api.add_resource(AddLocal, '/local')
api.add_resource(ManageLocal, '/local/<string: number>')


if __name__ == "__main__":
    application.run(host="127.0.0.1", port=12715, debug=True)