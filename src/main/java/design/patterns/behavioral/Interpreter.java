package design.patterns.behavioral;

import java.util.ArrayList;

/**
 *
 */
public class Interpreter {

    /// <summary>
    /// The 'Context' class
    /// </summary>
    static class Context {
    }

    /// <summary>
    /// The 'AbstractExpression' abstract class
    /// </summary>
    interface AbstractExpression {
        void Interpret(Context context);
    }

    /// <summary>
    /// The 'TerminalExpression' class
    /// </summary>
    static class TerminalExpression implements AbstractExpression
    {
        public void Interpret(Context context) {
            System.out.println("Called Terminal.Interpret()");
        }
    }

    /// <summary>
    /// The 'NonterminalExpression' class
    /// </summary>
    static class NonterminalExpression implements AbstractExpression {
        public void Interpret(Context context) {
            System.out.println("Called Nonterminal.Interpret()");
        }
    }

    /// <summary>
    /// MainApp startup class for Structural
    /// Interpreter Design Pattern.
    /// </summary>
    static class MainApp
    {
        /// <summary>
        /// Entry point into console application.
        /// </summary>
        static void main()
        {
            Context context = new Context();

            // Usually a tree
            ArrayList<AbstractExpression> list = new ArrayList<>();

            // Populate 'abstract syntax tree'
            list.add(new TerminalExpression());
            list.add(new NonterminalExpression());
            list.add(new TerminalExpression());
            list.add(new TerminalExpression());

            // Interpret
            for (AbstractExpression exp : list) {
                exp.Interpret(context);
            }

        }
    }


}