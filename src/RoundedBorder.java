import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedBorder extends JButton {
    private int radius;

    public RoundedBorder(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        g2.setColor(getForeground());
//        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
//
//        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Button Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        RoundedBorder button = new RoundedBorder("Rounded Button", 20);

        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); 

        frame.add(button);

        frame.setLayout(new FlowLayout());

        frame.setVisible(true);
    }
}
