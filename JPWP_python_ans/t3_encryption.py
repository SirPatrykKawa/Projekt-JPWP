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
pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * chr(BLOCK_SIZE - len(s) % BLOCK_SIZE)
unpad = lambda s: s[:-ord(s[len(s) - 1:])]


# TODO: Function below.
# The harder way is to use PBDKF2 function, but also the more secure way. If you will use it, you need to have salt.
# As salt use phrase: salt = b"Salty_MrPass_So_Salty"
def get_priv_key(password):
    """
    Function to generate AES encryption key
    :param password:
    :return:
    """
    func = 'harder'
    if func == 'harder':
        salt = b"application for securing json"
        kdf = PBKDF2(password, salt, 64, 1000)
        key = kdf[:32]
        return key
    elif func == 'easier':
        return hashlib.sha256(password)


# TODO: Function below. Remember to fill the padding, so that blocks are full
def encrypt(raw_data, password):
    """
    Function used to encrypt data
    :param raw_data:
    :param password:
    :return: Encrypted data 
    """
    private_key = get_priv_key(password)
    raw = pad(raw_data)
    iv = Random.new().read(AES.block_size)
    cipher = AES.new(private_key, AES.MODE_CBC, iv)
    return base64.b64encode(iv + cipher.encrypt(raw.encode('utf-8')))


# TODO: Function below. Remember to unpad decryprted data
def decrypt(encrypted_data, password):
    """
    Function used to decrypt data
    :param encrypted_data:
    :param password:
    :return:
    """
    private_key = get_priv_key(password)
    enc = base64.b64decode(encrypted_data)
    iv = enc[:16]
    cipher = AES.new(private_key, AES.MODE_CBC, iv)
    return unpad(cipher.decrypt(enc[16:]))


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