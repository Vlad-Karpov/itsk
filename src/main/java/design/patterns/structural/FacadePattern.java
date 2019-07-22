package design.patterns.structural;

/**
 * Шаблон фасад (англ. Facade) — структурный шаблон проектирования, позволяющий скрыть сложность системы путём сведения
 * всех возможных внешних вызовов к одному объекту, делегирующему их соответствующим объектам системы.
 *
 * пример - Единая Фронтальная система банка
 */
public class FacadePattern {

    /* Complex parts */

    static class CPU {
        void freeze() { System.out.println("freese"); }
        void jump(long position) { System.out.println("jump"); }
        void execute() { System.out.println("execute"); }
    }

    static class Memory {
        void load(long position, byte[] data) { System.out.println("load"); }
    }

    static class HardDrive {
        byte[] read(long lba, int size) { System.out.println("read"); return new byte[]{0, 1, 2};}
    }

    /*  Complex parts  */

    /* Facade */
    static class Computer {
        private static final long BOOT_ADDRESS = 0L;
        private static final long BOOT_SECTOR = 0L;
        private static final int SECTOR_SIZE = 0;
        private CPU cpu;
        private Memory memory;
        private HardDrive hardDrive;

        Computer() {
            this.cpu = new CPU();
            this.memory = new Memory();
            this.hardDrive = new HardDrive();
        }

        void startComputer() {
            cpu.freeze();
            memory.load(BOOT_ADDRESS, hardDrive.read(BOOT_SECTOR, SECTOR_SIZE));
            cpu.jump(BOOT_ADDRESS);
            cpu.execute();
        }
    }

    /* Client */

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.startComputer();
    }

}
