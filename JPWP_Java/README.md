# Zadania Java
#### *by Kawa Patryk & Twardosz Adam*

##### Zadanie do wykonania w klasie Zadanie1

## Zadanie no. 1

Zadanie 1: Dodaj do centralnej części siatki panel do wyboru daty (klasa DatePicker), natomiast do dolnej części
siatki przycisk submit. Przycisk ma być nieaktywny (metoda setDisable), dopóki nie wybierzemy daty dzisiejszej (26.05.2020).
Aktywny przycisk powinien zamykać okno aplikacji.

# UWAGA: Data wybierana z panelu jest zmienną typu LocalDate!

##### Zadania do wykonania w klasie Zadanie2

## Zadanie no. 2

Nałóż opóźnienie wynoszące trzy sekundy, które uruchomi się po kliknięciu dolnego przycisku.
Po upływie trzech sekund dolny przycisk zniknie, a pojawi sie górny.
Aby nałożyć opóźnienie należy stworzyć obiekt klasy PauseTransitionm który w konstruktorze jako argument
Przyjmie czas trzy sekund (obiekt klasy Duration) 

## Zadanie no. 3

W centralnym punkcie siatki znajduje sie koło progresu.
Spraw, aby po czterech kliknięciach górnego przycisku pasek był w pełni naładowany.
Po naładowaniu należy sprawić, że w centralnej części siatki pojawi sie przycisk z napisem "GG WP"
A górny przycisk zniknie
# UWAGA: Po każdym kliknięciu w górny przycisk powinno być widać rosnący progres!

## Zadanie no. 4

Spraw, aby po kliknięcie w przycisk GG WP scena zmieniła sie na taką, którą zwraca metoda
Finish z klasy WellDone
