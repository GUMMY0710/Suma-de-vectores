import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SumaVectores extends JFrame {

    private JPanel panel;
    private JTextField txtMagnitude1, txtMagnitude2;
    private JComboBox<String> directionVector1, directionVector2;
    private JButton btnSumar;

    public SumaVectores() {
        setTitle("Suma de vectores - Método del triángulo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponentes();
    }

    private void initComponentes() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel para dibujar los vectores
        JPanel dibujoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarVectores(g);
            }
        };
        panel.add(dibujoPanel, BorderLayout.CENTER);

        // Panel para los campos de texto y botón
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        JLabel lblMagnitude1 = new JLabel("Magnitud del vector 1:");
        inputPanel.add(lblMagnitude1);

        txtMagnitude1 = new JTextField();
        inputPanel.add(txtMagnitude1);

        JLabel lblDirection1 = new JLabel("Dirección del vector 1:");
        inputPanel.add(lblDirection1);

        directionVector1 = new JComboBox<>(new String[]{"Norte", "Sur", "Este", "Oeste"});
        inputPanel.add(directionVector1);

        JLabel lblMagnitude2 = new JLabel("Magnitud del vector 2:");
        inputPanel.add(lblMagnitude2);

        txtMagnitude2 = new JTextField();
        inputPanel.add(txtMagnitude2);

        JLabel lblDirection2 = new JLabel("Dirección del vector 2:");
        inputPanel.add(lblDirection2);

        directionVector2 = new JComboBox<>(new String[]{"Norte", "Sur", "Este", "Oeste"});
        inputPanel.add(directionVector2);

        btnSumar = new JButton("Sumar");
        btnSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint(); // Actualizar el dibujo al hacer clic en el botón
            }
        });
        inputPanel.add(btnSumar);

        panel.add(inputPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void dibujarVectores(Graphics g) {
        // Dibujar el plano cartesiano
        g.setColor(Color.BLACK);
        g.drawLine(0, 200, 400, 200); // Eje X
        g.drawLine(200, 0, 200, 400); // Eje Y

        // Obtener magnitud y dirección del vector 1
        int magnitude1;
        try {
            magnitude1 = Integer.parseInt(txtMagnitude1.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce una magnitud válida para el vector 1.");
            return;
        }
        String direction1 = (String) directionVector1.getSelectedItem();

        // Calcular componentes del vector 1
        int x1 = 0, y1 = 0;
        switch (direction1.toLowerCase()) {
            case "norte":
                y1 = -magnitude1;
                break;
            case "sur":
                y1 = magnitude1;
                break;
            case "este":
                x1 = magnitude1;
                break;
            case "oeste":
                x1 = -magnitude1;
                break;
            default:
                JOptionPane.showMessageDialog(null, "La dirección del vector 1 debe ser Norte, Sur, Este u Oeste.");
                return;
        }

        // Obtener magnitud y dirección del vector 2
        int magnitude2;
        try {
            magnitude2 = Integer.parseInt(txtMagnitude2.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, introduce una magnitud válida para el vector 2.");
            return;
        }
        String direction2 = (String) directionVector2.getSelectedItem();

        // Calcular componentes del vector 2
        int x2 = 0, y2 = 0;
        switch (direction2.toLowerCase()) {
            case "norte":
                y2 = -magnitude2;
                break;
            case "sur":
                y2 = magnitude2;
                break;
            case "este":
                x2 = magnitude2;
                break;
            case "oeste":
                x2 = -magnitude2;
                break;
            default:
                JOptionPane.showMessageDialog(null, "La dirección del vector 2 debe ser Norte, Sur, Este u Oeste.");
                return;
        }

        // Calcular componentes del vector resultante
        int xR = x1 + x2;
        int yR = y1 + y2;

        // Dibujar vector 1
        g.setColor(Color.BLACK);
        g.drawLine(200, 200, 200 + x1, 200 - y1);
        drawArrow(g, 200, 200, 200 + x1, 200 - y1);

        // Dibujar vector 2
        g.setColor(Color.BLUE);
        g.drawLine(200, 200, 200 + x2, 200 - y2);
        drawArrow(g, 200, 200, 200 + x2, 200 - y2);

        // Dibujar vector resultante
        g.setColor(Color.ORANGE);
        g.drawLine(200, 200, 200 + xR, 200 - yR);
        drawArrow(g, 200, 200, 200 + xR, 200 - yR);

        // Dibujar la unión de los vectores
        g.setColor(Color.RED);
        g.drawLine(200, 200, 200 + x1, 200 - y1);
        g.drawLine(200 + x1, 200 - y1, 200 + xR, 200 - yR);
        g.drawLine(200, 200, 200 + x2, 200 - y2);
        g.drawLine(200 + x2, 200 - y2, 200 + xR, 200 - yR);
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        double angle = Math.atan2(deltaY, deltaX);
        int len = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x1, y1, x2, y2);
        g2d.drawLine(x2, y2, (int) (x2 - 10 * Math.cos(angle - Math.PI / 6)),
                (int) (y2 - 10 * Math.sin(angle - Math.PI / 6)));
        g2d.drawLine(x2, y2, (int) (x2 - 10 * Math.cos(angle + Math.PI / 6)),
                (int) (y2 - 10 * Math.sin(angle + Math.PI / 6)));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SumaVectores().setVisible(true));
    }
}