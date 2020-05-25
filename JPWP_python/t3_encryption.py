import base64
import hashlib
from Crypto.Cipher import AES
from Crypto import Random
from Crypto.Protocol.KDF import PBKDF2
import json

############################################################
###                                                      ###
###---------------_USE !__CBC__! AES MODE_---------------###
###                                                      ###
############################################################

# TODO: Use !__lambda expressions__! to make pad() and unpad() functions
BLOCK_SIZE = 16
pad = ...
unpad = ...


# TODO: Function below.
# The harder way is to use PBDKF2 function, but also the more secure way. If you will use it, you need to have salt.
# As salt use phrase: salt = b"Salty_MrPass_So_Salty"
def get_priv_key(password):
    """
    Function to generate AES encryption key
    :param password:
    :return:
    """
    pass


# TODO: Function below. Remember to fill the padding, so that blocks are full
def encrypt(raw_data, password):
    """
    Function used to encrypt data
    :param raw_data:
    :param password:
    :return: Encrypted data 
    """
    pass


# TODO: Function below. Remember to unpad decryprted data
def decrypt(encrypted_data, password):
    """
    Function used to decrypt data
    :param encrypted_data:
    :param password:
    :return:
    """
    pass


############################################################
###                                                      ###
###--------------DO NOT TOUCH MAIN FUNCTION--------------###
###                                                      ###
############################################################
if __name__ == "__main__":
    passwd = hashlib.sha256("JpWp_4W50#3_3nCrYpT10n_T45k")
    with open('./database.json', 'r') as fin:
        data = json.load(fin)
        fin.close()

    enc = encrypt(data, passwd)
    with open('./encrypted.bin', 'wb') as fout1:
        fout1.writelines(enc)
        fout1.close()

    dec = decrypt(enc, passwd)
    with open('./decrypted.json', 'w') as fout2:
        json.dump(dec, fout2, indent=2)
        fout2.close()