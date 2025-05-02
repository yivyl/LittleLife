import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Utility class for consistent UI elements and styling
 */
public class UIComponents {
    // Color scheme
    public static final Color BACKGROUND_COLOR = new Color(252, 252, 206);
    public static final Color NIGHT_COLOR = new Color(44, 62, 80);
    public static final Color BUTTON_COLOR = new Color(255, 218, 185);
    public static final Color BUTTON_HOVER_COLOR = new Color(255, 182, 193);
    public static final Color BUTTON_PRESSED_COLOR = new Color(255, 160, 122);
    public static final Color TEXT_COLOR = new Color(139, 69, 19);
    public static final Color BORDER_COLOR = Color.WHITE;
    
    // Fonts
    public static final Font HEADER_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    public static final Font REGULAR_FONT = new Font("Comic Sans MS", Font.BOLD, 16);
    public static final Font SMALL_FONT = new Font("Comic Sans MS", Font.PLAIN, 14);
    
    /**
     * Creates a standardized JButton with consistent styling
     */
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2, true));
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
                button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 3, true));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
                button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2, true));
            }
            public void mousePressed(MouseEvent e) {
                button.setBackground(BUTTON_PRESSED_COLOR);
            }
            public void mouseReleased(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    /**
     * Creates a standardized JLabel with consistent styling
     */
    public static JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    /**
     * Creates a standardized JProgressBar for pet stats
     */
    public static JProgressBar createProgressBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(50);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        return bar;
    }
    
    /**
     * Creates a standardized text field
     */
    public static JTextField createTextField() {
        JTextField field = new JTextField(15);
        field.setFont(SMALL_FONT);
        field.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2, true));
        return field;
    }
    
    /**
     * Sets up a basic JFrame with consistent properties
     */
    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
    }
    
    /**
     * Creates an icon with a cartoon face for the pet
     * Now with more detail and personality types
     */
    public static Icon createPetIcon(int width, int height, String personality, boolean isNight) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Base color depends on personality
                Color petColor;
                switch(personality.toLowerCase()) {
                    case "playful":
                        petColor = new Color(255, 220, 177); // Peachy
                        break;
                    case "lazy":
                        petColor = new Color(204, 204, 255); // Lavender
                        break;
                    case "grumpy":
                        petColor = new Color(192, 192, 192); // Silver
                        break;
                    default:
                        petColor = new Color(255, 218, 185); // Default
                }
                
                // Draw body
                g2d.setColor(petColor);
                g2d.fillOval(x, y, width, height);
                g2d.setColor(TEXT_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawOval(x, y, width, height);
                
                // Eyes depend on personality and time
                if (isNight) {
                    // Sleeping eyes
                    g2d.drawLine(x + width/4 - 5, y + height/3, x + width/4 + 5, y + height/3);
                    g2d.drawLine(x + 3*width/4 - 5, y + height/3, x + 3*width/4 + 5, y + height/3);
                } else {
                    switch(personality.toLowerCase()) {
                        case "playful":
                            // Round eyes
                            g2d.fillOval(x + width/4 - 5, y + height/3 - 5, 10, 10);
                            g2d.fillOval(x + 3*width/4 - 5, y + height/3 - 5, 10, 10);
                            break;
                        case "lazy":
                            // Half-closed eyes
                            g2d.fillOval(x + width/4 - 5, y + height/3, 10, 5);
                            g2d.fillOval(x + 3*width/4 - 5, y + height/3, 10, 5);
                            break;
                        case "grumpy":
                            // Angled eyes
                            g2d.fillOval(x + width/4 - 5, y + height/3 - 2, 10, 8);
                            g2d.fillOval(x + 3*width/4 - 5, y + height/3 - 2, 10, 8);
                            // Angry eyebrows
                            g2d.drawLine(x + width/4 - 8, y + height/3 - 5, x + width/4 + 3, y + height/3 - 8);
                            g2d.drawLine(x + 3*width/4 - 3, y + height/3 - 8, x + 3*width/4 + 8, y + height/3 - 5);
                            break;
                        default:
                            // Default eyes
                            g2d.fillOval(x + width/4 - 5, y + height/3 - 5, 10, 10);
                            g2d.fillOval(x + 3*width/4 - 5, y + height/3 - 5, 10, 10);
                    }
                }
                
                // Mouth depends on personality and time
                if (isNight) {
                    // Sleeping - small z's
                    g2d.drawString("z", x + width/2 + 10, y + height/2 - 5);
                    g2d.drawString("z", x + width/2 + 20, y + height/2 - 15);
                } else {
                    switch(personality.toLowerCase()) {
                        case "playful":
                            // Big smile
                            g2d.drawArc(x + width/4, y + 2*height/3 - 10, width/2, height/4, 0, -180);
                            break;
                        case "lazy":
                            // Slight smile
                            g2d.drawArc(x + width/3, y + 2*height/3, width/3, height/6, 0, -180);
                            break;
                        case "grumpy":
                            // Frown
                            g2d.drawArc(x + width/3, y + 2*height/3 + 10, width/3, height/6, 0, 180);
                            break;
                        default:
                            // Default smile
                            g2d.drawArc(x + width/4, y + 2*height/3, width/2, height/6, 0, -180);
                    }
                }
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return width;
            }
            
            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }
}