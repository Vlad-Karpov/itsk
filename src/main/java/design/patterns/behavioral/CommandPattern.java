package design.patterns.behavioral;

/**
 * В объектно-ориентированном программировании шаблон проектирования Команда является поведенческим шаблоном, в котором
 * объект используется для инкапсуляции всей информации, необходимой для выполнения действия или вызова события в более
 * позднее время. Эта информация включает в себя имя метода, объект, который является владельцем метода и значения
 * параметров метода.
 *
 * Четыре термина всегда связаны с шаблоном Команда: команды (command), приёмник команд (receiver), вызывающий команды
 * (invoker) и клиент (client). Объект Command знает о приёмнике и вызывает метод приемника. Значения параметров
 * приёмника сохраняются в команде. Вызывающий объект (invoker) знает, как выполнить команду и, возможно, делает учёт и
 * запись выполненных команд. Вызывающий объект (invoker) ничего не знает о конкретной команде, он знает только об
 * интерфейсе. Оба объекта (вызывающий объект и несколько объектов команд) принадлежат объекту клиента (client).
 * Клиент решает, какие команды выполнить и когда. Чтобы выполнить команду он передает объект команды вызывающему
 * объекту (invoker).
 *
 * Использование командных объектов упрощает построение общих компонентов, которые необходимо делегировать или
 * выполнять вызовы методов в любое время без необходимости знать методы класса или параметров метода. Использование
 * вызывающего объекта (invoker) позволяет ввести учёт выполненных команд без необходимости знать клиенту об этой
 * модели учёта (такой учёт может пригодиться, например, для реализации отмены и повтора команд).
 */
public class CommandPattern {

    /*the Invoker class*/

    static class Switch {
        private Command flipUpCommand;
        private Command flipDownCommand;

        public Switch(Command flipUpCommand,Command flipDownCommand){
            this.flipUpCommand=flipUpCommand;
            this.flipDownCommand=flipDownCommand;
        }

        public void flipUp(){
            flipUpCommand.execute();
        }

        public void flipDown(){
            flipDownCommand.execute();
        }
    }

    /*Receiver class*/

    static class Light{
        public Light(){  }

        public void turnOn(){
            System.out.println("The light is on");
        }

        public void turnOff(){
            System.out.println("The light is off");
        }
    }


    /*the Command interface*/

    interface Command{
        void execute();
    }


    /*the Command for turning on the light*/

    static class TurnOnLightCommand implements Command{
        private Light theLight;

        public TurnOnLightCommand(Light light){
            this.theLight=light;
        }

        public void execute(){
            theLight.turnOn();
        }
    }

    /*the Command for turning off the light*/

    static class TurnOffLightCommand implements Command{
        private Light theLight;

        public TurnOffLightCommand(Light light){
            this.theLight=light;
        }

        public void execute(){
            theLight.turnOff();
        }
    }

    /*The test class*/
    static class TestCommand{
        public static void main(String[] args){
            Light l=new Light();
            Command switchUp=new TurnOnLightCommand(l);
            Command switchDown=new TurnOffLightCommand(l);

            Switch s=new Switch(switchUp,switchDown);

            s.flipUp();
            s.flipDown();
        }
    }

}
