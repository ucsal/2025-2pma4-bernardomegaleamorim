package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 60;
    private final List<Shape> shapes = new ArrayList<>();
    private Point startDrag = null;
    private Shape tempShape = null;

    DrawingPanel() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrag = e.getPoint();
                tempShape = new Ellipse2D.Double(startDrag.x, startDrag.y, 0, 0);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (startDrag != null) {
                    int width = e.getX() - startDrag.x;
                    int height = e.getY() - startDrag.y;
                    tempShape = new Ellipse2D.Double(startDrag.x, startDrag.y, width, height);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (tempShape != null) {
                    shapes.add(tempShape);
                    tempShape = null;
                }
            }
        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenhar todas as figuras
        for (Shape s : shapes) {
            g2.setColor(new Color(30, 144, 255));
            g2.fill(s);
            g2.setColor(new Color(0, 0, 0, 70));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(s);
        }

        // Desenhar a figura de pré-visualização
        if (tempShape != null) {
            g2.setColor(new Color(255, 0, 0, 100)); // Cor da pré-visualização
            g2.fill(tempShape);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(tempShape);
        }

        g2.dispose();
    }
}
