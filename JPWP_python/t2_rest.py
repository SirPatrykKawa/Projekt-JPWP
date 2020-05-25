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



if __name__ == "__main__":
    application.run(host="127.0.0.1", port=12715, debug=True)