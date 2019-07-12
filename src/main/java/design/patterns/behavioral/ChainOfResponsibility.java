package design.patterns.behavioral;

/**
 * Шаблон рекомендован для использования в условиях:
 *
 *  в разрабатываемой системе имеется группа объектов, которые могут обрабатывать сообщения определенного типа;
 *
 *  все сообщения должны быть обработаны хотя бы одним объектом системы;
 *
 *  сообщения в системе обрабатываются по схеме «обработай сам либо перешли другому», то есть одни сообщения
 *  обрабатываются на том уровне, где они получены, а другие пересылаются объектам иного уровня.
 *
 */
public class ChainOfResponsibility {

    abstract static class Logger {
        public static int ERR = 3;
        public static int NOTICE = 5;
        public static int DEBUG = 7;
        protected int mask;

        // The next element in the chain of responsibility
        protected Logger next;

        public Logger setNext(Logger log) {
            next = log;
            return log;
        }

        public void message(String msg, int priority) {
            if (priority <= mask) {
                writeMessage(msg);
            }
            if (next != null) {
                next.message(msg, priority);
            }
        }

        abstract protected void writeMessage(String msg);
    }

    static class StdoutLogger extends Logger {
        public StdoutLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.out.println("Writing to stdout: " + msg);
        }
    }

    static class EmailLogger extends Logger {
        public EmailLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.out.println("Sending via email: " + msg);
        }
    }

    static class StderrLogger extends Logger {
        public StderrLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.out.println("Sending to stderr: " + msg);
        }
    }

    static class ChainOfResponsibilityExample {
        public static void main(String[] args) {
            // Build the chain of responsibility
            Logger logger, logger1,logger2;
            logger = new StdoutLogger(Logger.DEBUG);
            logger1 = logger.setNext(new EmailLogger(Logger.NOTICE));
            logger2 = logger1.setNext(new StderrLogger(Logger.ERR));

            // Handled by StdoutLogger
            logger.message("Entering function y.", Logger.DEBUG);

            // Handled by StdoutLogger and EmailLogger
            logger.message("Step1 completed.", Logger.NOTICE);

            // Handled by all three loggers
            logger.message("An error has occurred.", Logger.ERR);
        }
    }
}
