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
    
    /**
     * Creates an animated pet icon based on animation type and frame
     */
    public static Icon createAnimatedPetIcon(int width, int height, String personality, 
                                             boolean isNight, String animationType, int frame) {
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
                
                // For sick animation, adjust color
                if (animationType.equals("sick")) {
                    petColor = new Color(
                        Math.min(255, petColor.getRed() + 20),
                        Math.max(0, petColor.getGreen() - 20),
                        Math.max(0, petColor.getBlue() - 20)
                    );
                }
                
                // Draw body with animation
                int offsetY = 0;
                if (animationType.equals("happy") && frame % 2 == 0) {
                    offsetY = -3; // Bounce up and down
                }
                
                if (animationType.equals("sick") && frame % 2 == 0) {
                    offsetY = 2; // Slight shaking
                }
                
                g2d.setColor(petColor);
                g2d.fillOval(x, y + offsetY, width, height);
                g2d.setColor(TEXT_COLOR);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawOval(x, y + offsetY, width, height);
                
                // Eyes depend on animation type and frame
                if (animationType.equals("happy")) {
                    // Happy eyes (change based on frame)
                    if (frame % 2 == 0) {
                        // Wider eyes
                        g2d.fillOval(x + width/4 - 6, y + offsetY + height/3 - 6, 12, 12);
                        g2d.fillOval(x + 3*width/4 - 6, y + offsetY + height/3 - 6, 12, 12);
                    } else {
                        // More normal eyes
                        g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                        g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                    }
                } else if (animationType.equals("sick")) {
                    // Sick eyes (droopy)
                    g2d.drawLine(x + width/4 - 5, y + offsetY + height/3 + 2, x + width/4 + 5, y + offsetY + height/3);
                    g2d.drawLine(x + 3*width/4 - 5, y + offsetY + height/3 + 2, x + 3*width/4 + 5, y + offsetY + height/3);
                } else if (animationType.equals("idle")) {
                    // Idle animation - blinking
                    if (frame % 4 == 0) {
                        // Eyes closed
                        g2d.drawLine(x + width/4 - 5, y + offsetY + height/3, x + width/4 + 5, y + offsetY + height/3);
                        g2d.drawLine(x + 3*width/4 - 5, y + offsetY + height/3, x + 3*width/4 + 5, y + offsetY + height/3);
                    } else {
                        // Regular eyes based on personality
                        switch(personality.toLowerCase()) {
                            case "playful":
                                g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                                g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                                break;
                            case "lazy":
                                g2d.fillOval(x + width/4 - 5, y + offsetY + height/3, 10, 5);
                                g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3, 10, 5);
                                break;
                            case "grumpy":
                                g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 2, 10, 8);
                                g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 2, 10, 8);
                                g2d.drawLine(x + width/4 - 8, y + offsetY + height/3 - 5, x + width/4 + 3, y + offsetY + height/3 - 8);
                                g2d.drawLine(x + 3*width/4 - 3, y + offsetY + height/3 - 8, x + 3*width/4 + 8, y + offsetY + height/3 - 5);
                                break;
                            default:
                                g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                                g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                        }
                    }
                } else {
                    // Default to regular eyes based on personality
                    switch(personality.toLowerCase()) {
                        case "playful":
                            g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                            g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                            break;
                        case "lazy":
                            g2d.fillOval(x + width/4 - 5, y + offsetY + height/3, 10, 5);
                            g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3, 10, 5);
                            break;
                        case "grumpy":
                            g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 2, 10, 8);
                            g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 2, 10, 8);
                            g2d.drawLine(x + width/4 - 8, y + offsetY + height/3 - 5, x + width/4 + 3, y + offsetY + height/3 - 8);
                            g2d.drawLine(x + 3*width/4 - 3, y + offsetY + height/3 - 8, x + 3*width/4 + 8, y + offsetY + height/3 - 5);
                            break;
                        default:
                            g2d.fillOval(x + width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                            g2d.fillOval(x + 3*width/4 - 5, y + offsetY + height/3 - 5, 10, 10);
                    }
                }
                
                // Mouth depends on animation type and frame
                if (animationType.equals("happy")) {
                    // Big smile that changes with frame
                    int smileSize = (frame % 2 == 0) ? height/3 : height/4;
                    g2d.drawArc(x + width/4, y + offsetY + 2*height/3 - 10, width/2, smileSize, 0, -180);
                } else if (animationType.equals("sick")) {
                    // Sick frown
                    g2d.drawArc(x + width/3, y + offsetY + 2*height/3 + 10, width/3, height/6, 0, 180);
                } else if (animationType.equals("idle")) {
                    // Normal mouth for personality
                    switch(personality.toLowerCase()) {
                        case "playful":
                            g2d.drawArc(x + width/4, y + offsetY + 2*height/3 - 10, width/2, height/4, 0, -180);
                            break;
                        case "lazy":
                            g2d.drawArc(x + width/3, y + offsetY + 2*height/3, width/3, height/6, 0, -180);
                            break;
                        case "grumpy":
                            g2d.drawArc(x + width/3, y + offsetY + 2*height/3 + 10, width/3, height/6, 0, 180);
                            break;
                        default:
                            g2d.drawArc(x + width/4, y + offsetY + 2*height/3, width/2, height/6, 0, -180);
                    }
                } else {
                    // Default to regular mouth
                    switch(personality.toLowerCase()) {
                        case "playful":
                            g2d.drawArc(x + width/4, y + offsetY + 2*height/3 - 10, width/2, height/4, 0, -180);
                            break;
                        case "lazy":
                            g2d.drawArc(x + width/3, y + offsetY + 2*height/3, width/3, height/6, 0, -180);
                            break;
                        case "grumpy":
                            g2d.drawArc(x + width/3, y + offsetY + 2*height/3 + 10, width/3, height/6, 0, 180);
                            break;
                        default:
                            g2d.drawArc(x + width/4, y + offsetY + 2*height/3, width/2, height/6, 0, -180);
                    }
                }
                
                // Special effects for animations
                if (animationType.equals("happy")) {
                    // Hearts or stars around pet
                    if (frame % 2 == 0) {
                        drawHeart(g2d, x + width - 20, y + height/4, 12);
                        drawHeart(g2d, x + 10, y + height/4 - 10, 12);
                    } else {
                        drawStar(g2d, x + width - 25, y + height/4 - 5, 12);
                        drawStar(g2d, x + 15, y + height/4 - 15, 12);
                    }
                } else if (animationType.equals("sick")) {
                    // Sweat drops
                    g2d.setColor(new Color(100, 180, 255));
                    g2d.fillOval(x + width/8, y + offsetY + height/4, 8, 12);
                    g2d.fillOval(x + 7*width/8 - 8, y + offsetY + height/4, 8, 12);
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
            
            private void drawHeart(Graphics2D g, int x, int y, int size) {
                g.setColor(new Color(255, 105, 180)); // Pink
                
                int[] xPoints = new int[]{
                    x, x + size/2, x + size, x + size/2
                };
                int[] yPoints = new int[]{
                    y + size/4, y, y + size/4, y + size
                };
                
                g.fillOval(x - size/4, y, size/2, size/2);
                g.fillOval(x + size - size/4, y, size/2, size/2);
                g.fillPolygon(xPoints, yPoints, 4);
            }
            
            private void drawStar(Graphics2D g, int x, int y, int size) {
                g.setColor(new Color(255, 215, 0)); // Gold
                
                int[] xPoints = new int[10];
                int[] yPoints = new int[10];
                
                double angle = Math.PI / 2;
                double angleIncrement = Math.PI / 5;
                
                for (int i = 0; i < 10; i++) {
                    int radius = (i % 2 == 0) ? size/2 : size/4;
                    xPoints[i] = x + (int)(radius * Math.cos(angle));
                    yPoints[i] = y + (int)(radius * Math.sin(angle));
                    angle += angleIncrement;
                }
                
                g.fillPolygon(xPoints, yPoints, 10);
            }
        };
    }
    
    /**
     * Creates a sun icon for the day/night indicator
     */
    public static Icon createSunIcon() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sun color
                g2d.setColor(new Color(255, 200, 0));
                
                // Draw sun circle
                g2d.fillOval(x, y, 16, 16);
                
                // Draw rays
                g2d.setStroke(new BasicStroke(2f));
                for (int i = 0; i < 8; i++) {
                    double angle = i * Math.PI / 4;
                    int x1 = x + 8 + (int)(10 * Math.cos(angle));
                    int y1 = y + 8 + (int)(10 * Math.sin(angle));
                    int x2 = x + 8 + (int)(16 * Math.cos(angle));
                    int y2 = y + 8 + (int)(16 * Math.sin(angle));
                    g2d.drawLine(x1, y1, x2, y2);
                }
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return 32;
            }
            
            @Override
            public int getIconHeight() {
                return 32;
            }
        };
    }
    
    /**
     * Creates a moon icon for the day/night indicator
     */
    public static Icon createMoonIcon() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Moon colors
                g2d.setColor(new Color(200, 200, 255));
                
                // Draw crescent moon
                g2d.fillOval(x, y, 16, 16);
                g2d.setColor(new Color(44, 62, 80)); // Night color
                g2d.fillOval(x - 4, y, 16, 16);
                
                // Draw stars
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x + 20, y + 2, 2, 2);
                g2d.fillRect(x + 24, y + 12, 2, 2);
                g2d.fillRect(x + 18, y + 18, 2, 2);
                
                g2d.dispose();
            }
            
            @Override
            public int getIconWidth() {
                return 32;
            }
            
            @Override
            public int getIconHeight() {
                return 32;
            }
        };
    }
}