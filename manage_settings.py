import json
import base64
import pathlib as pl
import os

def collect_settings(path, login, password, serv_ip, serv_port, db_name, local_db_path, local_db_name) -> bool:
    try:
        settings = {"login": login, "password": password, "server_ip": serv_ip, "server_port": serv_port, "database": db_name,
                    "local_db_path": local_db_path, "local_db_name": local_db_name}

        path_settings = create_path(path, 'dbsettings.json')
        with open(path_settings, 'w') as fout:
            json.dump(settings, fout, indent=2)
        
        return True
    
    except:
        return False


def change_settings(path, setting_name, new_val):
    path_settings = create_path(path, 'dbsettings.json')
    with open(path_settings, 'r') as fin:
        settings = json.load(fin)

    settings[setting_name] = new_val

    with open(path_settings, 'w') as fout:
        json.dump(settings, fout, indent=2)


def get_settings(path):
    path_settings = create_path(path, 'dbsettings.json')
    with open(path_settings, 'r') as fin:
        settings = json.load(fin)

    return settings

def create_path(path, file) -> str:
    if os.name == 'nt':
        return pl.PureWindowsPath(path).joinpath(pl.PureWindowsPath(file))
    else:
        return pl.PurePosixPath(path).joinpath(pl.PurePosixPath(file))