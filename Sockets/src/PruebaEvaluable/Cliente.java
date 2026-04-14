package PruebaEvaluable;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Cliente {
    public static JTextArea textArea;

    // ===== PALETA DE COLORES MODERNA =====
    private static final Color BG_DARK = new Color(30, 33, 40);
    private static final Color BG_PANEL = new Color(40, 43, 52);
    private static final Color BG_INPUT = new Color(55, 58, 68);
    private static final Color BG_TEXTAREA = new Color(35, 38, 47);
    private static final Color TEXT_PRIMARY = new Color(220, 225, 235);
    private static final Color TEXT_SECONDARY = new Color(140, 148, 165);
    private static final Color ACCENT = new Color(0, 210, 210);
    private static final Color ACCENT_HOVER = new Color(50, 240, 240);
    private static final Color ACCENT_GLOW = new Color(0, 210, 210, 60);
    private static final Color BORDER_SUBTLE = new Color(65, 70, 82);
    private static final Color SCROLLBAR_THUMB = new Color(70, 75, 88);

    // ===== BOTÓN PERSONALIZADO CON GRAPHICS2D =====
    static class BotonNeon extends JButton {
        private boolean hover = false;
        private float glowAlpha = 0f;

        public BotonNeon(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 15));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(110, 42));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    glowAlpha = 1.0f;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    glowAlpha = 0f;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = 22;
            RoundRectangle2D shape = new RoundRectangle2D.Float(0, 0, w, h, arc, arc);

            // Glow exterior en hover
            if (hover && glowAlpha > 0) {
                g2.setColor(ACCENT_GLOW);
                g2.fill(new RoundRectangle2D.Float(-3, -3, w + 6, h + 6, arc + 6, arc + 6));
            }

            // Fondo del botón con gradiente
            Color topColor = hover ? ACCENT_HOVER : ACCENT;
            Color bottomColor = hover ? ACCENT : new Color(0, 170, 170);
            GradientPaint gp = new GradientPaint(0, 0, topColor, 0, h, bottomColor);
            g2.setPaint(gp);
            g2.fill(shape);

            // Texto centrado
            g2.setFont(getFont());
            g2.setColor(new Color(10, 20, 30));
            FontMetrics fm = g2.getFontMetrics();
            int textX = (w - fm.stringWidth(getText())) / 2;
            int textY = (h + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(getText(), textX, textY);

            g2.dispose();
        }
    }

    // ===== CAMPO DE TEXTO PERSONALIZADO =====
    static class CampoModerno extends JTextField {
        public CampoModerno(String placeholder) {
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setForeground(TEXT_PRIMARY);
            setCaretColor(ACCENT);
            setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            setToolTipText(placeholder);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = 18;

            // Fondo redondeado
            g2.setColor(BG_INPUT);
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

            // Borde sutil (cian si tiene foco)
            if (hasFocus()) {
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(1.5f));
            } else {
                g2.setColor(BORDER_SUBTLE);
                g2.setStroke(new BasicStroke(1f));
            }
            g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, w - 1, h - 1, arc, arc));

            g2.dispose();
            super.paintComponent(g);

            // Placeholder cuando está vacío
            if (getText().isEmpty() && !hasFocus()) {
                Graphics2D g3 = (Graphics2D) getGraphics();
                if (g3 != null) {
                    g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g3.setFont(getFont().deriveFont(Font.ITALIC));
                    g3.setColor(TEXT_SECONDARY);
                    Insets ins = getInsets();
                    g3.drawString(getToolTipText(), ins.left,
                            getHeight() / 2 + g3.getFontMetrics().getAscent() / 2 - 2);
                    g3.dispose();
                }
            }
        }
    }

    // ===== SCROLLBAR PERSONALIZADA =====
    static class ScrollBarModerna extends JScrollPane {
        public ScrollBarModerna(Component view) {
            super(view);
            setBorder(BorderFactory.createEmptyBorder());
            getViewport().setBackground(BG_TEXTAREA);

            // Scrollbar vertical personalizada
            getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = SCROLLBAR_THUMB;
                    this.trackColor = BG_TEXTAREA;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    return crearBotonInvisible();
                }

                @Override
                protected JButton createIncreaseButton(int orientation) {
                    return crearBotonInvisible();
                }

                private JButton crearBotonInvisible() {
                    JButton btn = new JButton();
                    btn.setPreferredSize(new Dimension(0, 0));
                    return btn;
                }

                @Override
                protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(SCROLLBAR_THUMB);
                    g2.fill(new RoundRectangle2D.Float(r.x + 2, r.y, r.width - 4, r.height, 10, 10));
                    g2.dispose();
                }

                @Override
                protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                    g.setColor(BG_TEXTAREA);
                    g.fillRect(r.x, r.y, r.width, r.height);
                }
            });
            getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // ===== INTERFAZ GRÁFICA DEL CLIENTE (REDISEÑO MODERNO) =====
            JFrame frame = new JFrame("⚡ Trivia Battle");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 520);
            frame.setMinimumSize(new Dimension(500, 380));
            frame.getContentPane().setBackground(BG_DARK);
            frame.setLayout(new BorderLayout());

            // --- Barra superior decorativa ---
            JPanel topBar = new JPanel(new BorderLayout());
            topBar.setBackground(BG_PANEL);
            topBar.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 2, 0, ACCENT),
                    new EmptyBorder(10, 18, 10, 18)));
            JLabel titulo = new JLabel("TRIVIA BATTLE");
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
            titulo.setForeground(ACCENT);
            JLabel subtitulo = new JLabel("conectado al servidor");
            subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            subtitulo.setForeground(TEXT_SECONDARY);
            topBar.add(titulo, BorderLayout.WEST);
            topBar.add(subtitulo, BorderLayout.EAST);
            frame.add(topBar, BorderLayout.NORTH);

            // --- Área de lectura (preguntas y mensajes del servidor) ---
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            textArea.setForeground(TEXT_PRIMARY);
            textArea.setBackground(BG_TEXTAREA);
            textArea.setCaretColor(BG_TEXTAREA);
            textArea.setSelectionColor(new Color(0, 210, 210, 50));
            textArea.setSelectedTextColor(Color.WHITE);
            textArea.setMargin(new Insets(18, 20, 18, 20));

            ScrollBarModerna scroll = new ScrollBarModerna(textArea);
            frame.add(scroll, BorderLayout.CENTER);

            // --- Panel inferior: input + botón ---
            JPanel panelInferior = new JPanel(new BorderLayout(12, 0));
            panelInferior.setBackground(BG_DARK);
            panelInferior.setBorder(new EmptyBorder(14, 18, 16, 18));

            CampoModerno campoTexto = new CampoModerno("Escribe tu respuesta...");
            BotonNeon btnEnviar = new BotonNeon("ENVIAR");

            panelInferior.add(campoTexto, BorderLayout.CENTER);
            panelInferior.add(btnEnviar, BorderLayout.EAST);
            frame.add(panelInferior, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // ===== FIN INTERFAZ GRÁFICA =====

            // Arrancamos el hilo que se encargará de leer todo lo que envíe el servidor
            HiloReceptorCliente receptor = new HiloReceptorCliente(socket);
            receptor.start();

            // Control de si el nick ya fue enviado
            final boolean[] nickEnviado = { false };

            // ActionListener compartido por el botón y la tecla Enter
            Runnable accionEnviar = () -> {
                String texto = campoTexto.getText().trim();
                if (texto.isEmpty())
                    return;

                if (!nickEnviado[0]) {
                    salida.println(texto);
                    nickEnviado[0] = true;
                } else {
                    salida.println("RESPUESTA|" + texto);
                    System.out.println("-> Respuesta registrada. Esperando a que termine el tiempo...");
                    textArea.append("-> Respuesta registrada. Esperando a que termine el tiempo...\n");
                }
                campoTexto.setText("");
            };

            btnEnviar.addActionListener(e -> accionEnviar.run());
            campoTexto.addActionListener(e -> accionEnviar.run());

        } catch (Exception e) {
            System.out.println("No se pudo conectar con el servidor.");
        }
    }
}