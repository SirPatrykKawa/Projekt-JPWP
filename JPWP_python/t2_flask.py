import json
from flask import Flask, jsonify, request, Response

app = Flask(__name__)


@app.route('/send', methods=['POST'])
def send():
    args = {}
    args['message'] = request.values.get('message')
    args['author'] = request.values.get('author')
    
    return jsonify(args)


@app.route('/local/<string:number>', methods=['GET'])
def local_get(number):
    with open('./local.json', 'r') as fin:
        data = json.load(fin)
        fin.close()
    
    return jsonify(data[number])


@app.route('/local/<string:number>', methods=['PUT'])
def local_update(number):
    try:
        args['field'] = request.values.get('field')
        args['new_value'] = request.values.get('new_value')

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


@app.route('/local', methods=['POST'])
def local_add():
    try:
        args = {}
        args['name'] = request.values.get('name')
        args['floors'] = request.values.get('floors')
        args['built'] = request.values.get('built')
        args['town'] = args.values.get('town')
        args['country'] = args.values.get('country')

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


@app.route('/local/<string:number>', methods=['DELETE'])
def local_del(number):
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


if __name__ == "__main__":
    app.run(host="127.0.0.1", port=9999, debug=True)