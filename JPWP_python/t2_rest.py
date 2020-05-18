import json
from Flask import Flask, jsonify, request, Response
from Flask_RESTful import Resource, Api, reqparse

application = Flask(__name__)
api = Api(application)

###---DUUUUUUUUŻO MIEJSCA NA KODZIK---###




if __name__ == "__main__":
    application.run(host="127.0.0.1", port=12715, debug=True)