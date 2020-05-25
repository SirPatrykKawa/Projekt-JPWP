# Zadania Python
#### *by Kawa Patryk & Twardosz Adam*

## __Setup__
Aby zainstalować potrzebne moduły użyj polecenia poniżej:

```
pip install -r requirements.txt
```
Python version: <span style="color: limegreen">__3.6__</span> or newer
***


## Zadanie no. 1 -> __MongoDB__

Potrzebne dane do uzyskania dostępu do bazy dostaniesz od autorów zadań podczas laboratoriów.

Uzupełnij funkcje `t1_mongodb.py` w Pythonie:

* wyświetla zawartość obu kolekcji z bazy danych
* dodaje nowy dokument do bazy danych do kolekcji `coll1` z pliku `doc.json`
* wyświetla dokumenty po autorze rosnąco z kolekcji `coll2`
* usuwa dokumenty o *dacie sprzed 2017 roku* z bazy danych z kolekcji `coll2`

## Zadanie no. 2 -> __Flask_i_REST_API__

Przepisz na bazie dostępnych w pliku `t2_rest.py` przykładów skrypt w Pythonie robiący dokładnie to samo, ale przy użyciu Flask-RESTful.

> Do sprawdzenia poprawności zapytań możesz użyć aplikacji `curl` w środowisku UNIX albo aplikacji Postman. 

## Zadanie no. 3 -> __Szyfrowanie__

Uzupełnij funkcje w pliku `t3_encryption.py`, tak aby spełniały swoje zadanie.

***
### Przydatne linki:
* https://pymongo.readthedocs.io/en/stable/
* https://flask-restful.readthedocs.io/en/latest/
* https://pycryptodome.readthedocs.io/en/latest/
