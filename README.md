KinoServer
==========

API
---------
**GET:** _http://kinoserver-cybern223.rhcloud.com/kinoserver/mobile/update_ to get all available updates

**GET:** _http://kinoserver-cybern223.rhcloud.com/kinoserver/mobile/update/2014-02-24_ to get all update after Feb. 24th, 2014

* [Wiki](https://github.com/cybern223/kinoserver/wiki)

@TODO
---------
* Оптимизировать парсер parsers.stcollect (с помощью потоков)
* Оптимизировать парсер parsers.kinopoisk (с помощью потоков)
* Оптимизировать парсер whatsong (с помощью потоков)
* Добавить логгер и перенаправить вывод из System.out в logger
* Разработать контроллер для управления парсерами (запуск, получение данных)
* ~~Реализовать загрузку данных в СУБД PostgreSQL~~
* ~~Реализовать получение клиентских данных из СУБД PostgreSQL~~
* ~~Написать обработку запроса на обновление~~
