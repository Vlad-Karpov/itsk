package design.patterns.behavioral;

/**
 * Некий объект обладает состояниями (переменная с типом State).
 * Объект может производить разные действия (interface State), но... :
 * В разных состояниях объект может делать только либо ограниченный набор действий, либо как-то ограничены
 * сами дейсвтия (для каждого состояния своя имплементация State). В конце каждого действия состояние объекта меняется
 * (изменяется переменная с типом State на новое состояние)
 */
public class StatePattern {

    interface CarState {
        void Start(Car c) throws Exception;
        void Stop(Car c) throws Exception;
        void Speed(Car c, int speed) throws Exception;
    }

    static class MotorWorksState implements CarState {
        private static MotorWorksState instance = new MotorWorksState();
        public static CarState getInstance()  {
            return instance;
        }

        public void Start(Car c) throws Exception {
            //Запрещаем запуск двигателя до того как он будет остановлен
            throw new Exception("Двигатель уже запущен!");
        }

        public void Stop(Car c) throws Exception {
            //Остановка двигателя
            //Запрещаем остановку двигателя, если скорость не равно 0 (автомобиль ещё движется)
            if (c.speed == 0) {
                c.motorWorks = true;
                c.ChangeState(MotorStoppedState.getInstance());
            }
            else {
                throw new Exception("Прежде чем остановить двигатель уменьшите скорость до 0!!!");
            }
        }

        public void Speed(Car c, int speed) throws Exception {
            //Устанавливаем скорость
            if (speed >= 0) {
                c.speed = speed;
            }
            else {
                //Запрет ввода некорректных значений скорости
                throw new Exception("Значение скорости не может быть отрицательным!");
            }
        }
    }

    static class MotorStoppedState implements CarState {

        private static final MotorStoppedState instance = new MotorStoppedState();

        private MotorStoppedState() {
        }

        public static CarState getInstance() {
            return instance;
        }

        public void Start(Car c) {
            //Запуск двигателя
            c.motorWorks = true;
            c.ChangeState(MotorWorksState.getInstance());
        }

        public void Stop(Car c) throws Exception {
            //Запрет остановки двигателя до того, как он будет снова запущен
            throw new Exception("Двигатель уже остановлен!");
        }

        public void Speed(Car c, int speed) throws Exception {
            //Запрет изменения скорости при остановленном двигателе
            throw new Exception("Нельзя изменить скорость, когда двигатель остановлен!");
        }

    }

    static class Car {

        private CarState state;
        int speed;
        boolean motorWorks;

        public Car() {
            //При создании объекта устанвливаем скорость равную 0
            speed = 0;
            //При создании объекта "двигатель остановлен"
            motorWorks = false;
            ChangeState(MotorStoppedState.getInstance());
        }

        public int getSpeed() {
            //Отображение текущей скорости (только чтение)
            return speed;
        }

        public void StartMotor() throws Exception {
            //Запуск двигателя
            state.Start(this);
        }

        public void StopMotor() throws Exception {
            //Остановка двигателя
            state.Stop(this);
        }

        public void SetSpeed(int speed) throws Exception {
            //Изменение скорости
            state.Speed(this, speed);
        }

        void ChangeState(CarState s) {
            //Изменение состояния автомобиля
            state = s;
        }

    }

    public static void main(String[] args) throws Exception {
        Car car = new Car();
        car.StartMotor();
        car.SetSpeed(10);
        car.SetSpeed(0);
        car.StopMotor();
    }

}
