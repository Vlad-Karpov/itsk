package design.patterns.creational.prototype;

/*
Задаёт виды создаваемых объектов с помощью экземпляра-прототипа и создаёт новые объекты путём копирования этого
прототипа. Он позволяет уйти от реализации и позволяет следовать принципу «программирование через интерфейсы».
В качестве возвращающего типа указывается интерфейс/абстрактный класс на вершине иерархии, а классы-наследники могут
подставить туда наследника, реализующего этот тип.

Проще говоря, это паттерн создания объекта через клонирование другого объекта вместо создания через конструктор.
*/