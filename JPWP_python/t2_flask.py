import json
from Flask import Flask, jsonify, request, Response

application = Flask(__name__)

## Write flask endpoints


if __name__ == "__main__":
    application.run(host="127.0.0.1", port=12715, debug=True)