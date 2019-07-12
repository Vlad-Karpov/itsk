package design.patterns.behavioral;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализует у класса механизм, который позволяет объекту этого класса получать оповещения об изменении состояния
 * других объектов и тем самым наблюдать за ними
 */
public class ObserverPattern {

    interface Observer {
        void update (float temperature, float humidity, int pressure);
    }

    interface Observable {
        void registerObserver(Observer o);
        void removeObserver(Observer o);
        void notifyObservers();
    }

    static class WeatherData implements Observable {
        private List<Observer> observers;
        private float temperature;
        private float humidity;
        private int pressure;

        public WeatherData() {
            observers = new LinkedList<>();
        }

        @Override
        public void registerObserver(Observer o) {
            observers.add(o);
        }

        @Override
        public void removeObserver(Observer o) {
            observers.remove(o);
        }

        @Override
        public void notifyObservers() {
            for (Observer observer : observers)
                observer.update(temperature, humidity, pressure);
        }

        public void setMeasurements(float temperature, float humidity, int pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            notifyObservers();
        }
    }

    static class CurrentConditionsDisplay implements Observer {
        private float temperature;
        private float humidity;
        private int pressure;
        private WeatherData weatherData;

        public CurrentConditionsDisplay(WeatherData weatherData) {
            this.weatherData = weatherData;
            weatherData.registerObserver(this);
        }

        @Override
        public void update(float temperature, float humidity, int pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            display();
        }

        public void display() {
            System.out.printf("Сейчас значения:%.1f градусов цельсия и %.1f %% влажности. Давление %d мм рт. ст.\n", temperature, humidity, pressure);
        }
    }

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(29f, 65f, 745);
        weatherData.setMeasurements(39f, 70f, 760);
        weatherData.setMeasurements(42f, 72f, 763);
    }


}
