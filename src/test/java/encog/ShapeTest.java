package encog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class ShapeTest extends JFrame {
    List<Double[]> l;
    private Container cp;
    private JPanel pane;
    private JScrollPane jspane;
    public ShapeTest(List<Double[]> l){
        this.l = l;
        cp = getContentPane();

        pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintQ(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(2000, 1000);
            }

            @Override
            protected void processMouseEvent(MouseEvent e) {
                super.processMouseEvent(e);
            }

        };
        jspane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cp.add(jspane);

        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void paintQ(Graphics g){
        int[] xs = new int[l.size()];
        int[] yxs = new int[l.size()];
        int[] y1s = new int[l.size()];
        int[] y2s = new int[l.size()];
        int i = 0;
        for (Double[] e: l) {
            xs[i] = 20 + i;
            yxs[i] = 450;
            y1s[i] = 450 + (int )(e[1] * 200);
            y2s[i] = 450 + (int )(e[2] * 200);
            i++;
        }
        g.drawPolyline(xs, yxs, l.size());
        g.drawPolyline(xs, y2s, l.size());
        g.setColor(Color.BLUE);
        g.drawPolyline(xs, y1s, l.size());
        g.setColor(Color.BLACK);
    }
}
