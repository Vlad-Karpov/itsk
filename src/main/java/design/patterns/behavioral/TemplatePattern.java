package design.patterns.behavioral;

/**
 * Шаблонный метод (англ. Template method) — поведенческий шаблон проектирования, определяющий основу алгоритма и
 * позволяющий наследникам переопределять некоторые шаги алгоритма, не изменяя его структуру в целом.
 */
public class TemplatePattern {

    enum GameCode {
        CHESS,
        MONOPOLY
    }

    static abstract class Game {

        private int playersAmount;

        protected abstract void initializeGame();

        protected abstract void playGame();

        protected abstract void endGame();

        protected abstract void printWinner();

        public final void playOneGame(int playersAmount){
            setPlayersAmount(playersAmount);

            initializeGame();
            playGame();
            endGame();

            printWinner();
        }

        public void setPlayersAmount(int playersAmount){
            this.playersAmount = playersAmount;
        }

    }

    static class Chess extends Game {

        @Override
        protected void initializeGame() {
            // chess specific initialization actions
        }

        @Override
        protected void playGame() {
            // chess specific play actions
        }

        @Override
        protected void endGame() {
            // chess specific actions to end a game
        }

        @Override
        protected void printWinner() {
            // chess specific actions to print winner
        }

    }

    static class Monopoly extends Game{

        @Override
        protected void initializeGame() {
            // monopoly specific initialization actions
        }

        @Override
        protected void playGame() {
            // monopoly specific play actions
        }

        @Override
        protected void endGame() {
            // monopoly specific actions to end a game
        }

        @Override
        protected void printWinner() {
            // monopoly specific actions to print winner
        }

    }

    static class GamesManager {

        public static void main (String [] args){
            final GameCode gameCode = GameCode.CHESS;

            Game game;

            switch (gameCode){
                case CHESS :
                    game = new Chess();
                    break;
                case MONOPOLY :
                    game = new Monopoly();
                    break;
                default :
                    throw new IllegalStateException();
            }

            game.playOneGame(2);
        }

    }

}
