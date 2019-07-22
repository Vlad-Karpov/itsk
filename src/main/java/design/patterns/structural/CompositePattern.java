package design.patterns.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * Компоновщик (англ. Composite pattern) — структурный шаблон проектирования, объединяющий объекты в древовидную
 * структуру для представления иерархии от частного к целому. Компоновщик позволяет клиентам обращаться к отдельным
 * объектам и к группам объектов одинаково.
 */
public class CompositePattern {

    /** "Component" */
    interface Graphic {

        //Prints the graphic.
        public void print();

    }

    /** "Composite" */
    static class CompositeGraphic implements Graphic {

        //Collection of child graphics.
        private List<Graphic> mChildGraphics = new ArrayList<Graphic>();

        //Prints the graphic.
        public void print() {
            for (Graphic graphic : mChildGraphics) {
                graphic.print();
            }
        }

        //Adds the graphic to the composition.
        public void add(Graphic graphic) {
            mChildGraphics.add(graphic);
        }

        //Removes the graphic from the composition.
        public void remove(Graphic graphic) {
            mChildGraphics.remove(graphic);
        }

    }


    /** "Leaf" */
    static class Ellipse implements Graphic {

        //Prints the graphic.
        public void print() {
            System.out.println("Ellipse");
        }

    }


        public static void main(String[] args) {
            //Initialize four ellipses
            Ellipse ellipse1 = new Ellipse();
            Ellipse ellipse2 = new Ellipse();
            Ellipse ellipse3 = new Ellipse();
            Ellipse ellipse4 = new Ellipse();

            //Initialize three composite graphics
            CompositeGraphic graphic = new CompositeGraphic();
            CompositeGraphic graphic1 = new CompositeGraphic();
            CompositeGraphic graphic2 = new CompositeGraphic();

            //Composes the graphics
            graphic1.add(ellipse1);
            graphic1.add(ellipse2);
            graphic1.add(ellipse3);

            graphic2.add(ellipse4);

            graphic.add(graphic1);
            graphic.add(graphic2);

            //Prints the complete graphic (four times the string "Ellipse").
            graphic.print();
        }


}
