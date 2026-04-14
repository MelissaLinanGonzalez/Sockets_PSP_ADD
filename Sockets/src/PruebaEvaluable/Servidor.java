package PruebaEvaluable;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Servidor {
    public static boolean juegoIniciado = false;
    public static ArrayList<ClientHandler> clientes = new ArrayList<>();
    public static JTextArea textArea;
    public static ArrayList<PreguntaTrivia> listaPreguntas = new ArrayList<>();

    // ===== PALETA DE COLORES =====
    private static final Color BG_DARK = new Color(25, 28, 36);
    private static final Color BG_PANEL = new Color(32, 36, 46);
    private static final Color BG_CARD = new Color(42, 46, 58);
    private static final Color BG_INPUT = new Color(50, 54, 66);
    private static final Color BG_CONSOLE = new Color(18, 20, 26);
    private static final Color TEXT_PRIMARY = new Color(220, 225, 235);
    private static final Color TEXT_SECONDARY = new Color(120, 130, 150);
    private static final Color TEXT_CONSOLE = new Color(80, 250, 180);
    private static final Color ACCENT_GREEN = new Color(50, 215, 130);
    private static final Color ACCENT_GREEN_H = new Color(70, 240, 155);
    private static final Color ACCENT_RED = new Color(240, 80, 90);
    private static final Color ACCENT_RED_H = new Color(255, 110, 120);
    private static final Color ACCENT_CYAN = new Color(0, 200, 210);
    private static final Color ACCENT_PURPLE = new Color(140, 90, 255);
    private static final Color ACCENT_PURPLE_H = new Color(165, 120, 255);
    private static final Color BORDER_SUBTLE = new Color(60, 65, 78);
    private static final Color SELECTED_BG = new Color(55, 60, 80);
    private static final Color SCROLLBAR_THUMB = new Color(70, 75, 90);

    // ===== CLASE INTERNA PreguntaTrivia =====
    public static class PreguntaTrivia {
        String enunciado;
        String opciones;
        String respuesta;

        public PreguntaTrivia(String enunciado, String opciones, String respuesta) {
            this.enunciado = enunciado;
            this.opciones = opciones;
            this.respuesta = respuesta;
        }

        @Override
        public String toString() {
            return enunciado + "  [Resp: " + respuesta + "]";
        }
    }

    // ===== BOTÓN PERSONALIZADO GRAPHICS2D =====
    static class BotonModerno extends JButton {
        private boolean hover = false;
        private Color colorBase;
        private Color colorHover;

        public BotonModerno(String text, Color base, Color hoverColor) {
            super(text);
            this.colorBase = base;
            this.colorHover = hoverColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), arc = 16;

            // Glow en hover
            if (hover && isEnabled()) {
                g2.setColor(new Color(colorHover.getRed(), colorHover.getGreen(), colorHover.getBlue(), 45));
                g2.fill(new RoundRectangle2D.Float(-2, -2, w + 4, h + 4, arc + 4, arc + 4));
            }

            Color top = (hover && isEnabled()) ? colorHover : colorBase;
            Color bot = top.darker();
            if (!isEnabled()) {
                top = new Color(70, 75, 85);
                bot = top;
            }
            g2.setPaint(new GradientPaint(0, 0, top, 0, h, bot));
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

            g2.setFont(getFont());
            g2.setColor(isEnabled() ? new Color(10, 15, 20) : TEXT_SECONDARY);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(getText(), (w - fm.stringWidth(getText())) / 2,
                    (h + fm.getAscent() - fm.getDescent()) / 2);
            g2.dispose();
        }
    }

    // ===== CAMPO DE TEXTO PERSONALIZADO =====
    static class CampoEstilizado extends JTextField {
        private String label;

        public CampoEstilizado(String label) {
            this.label = label;
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setForeground(TEXT_PRIMARY);
            setCaretColor(ACCENT_CYAN);
            setBorder(BorderFactory.createEmptyBorder(22, 12, 8, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight(), arc = 12;

            g2.setColor(BG_INPUT);
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

            if (hasFocus()) {
                g2.setColor(ACCENT_CYAN);
                g2.setStroke(new BasicStroke(1.5f));
            } else {
                g2.setColor(BORDER_SUBTLE);
                g2.setStroke(new BasicStroke(1f));
            }
            g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, w - 1, h - 1, arc, arc));

            // Etiqueta flotante
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            g2.setColor(hasFocus() ? ACCENT_CYAN : TEXT_SECONDARY);
            g2.drawString(label, 12, 14);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== CELLRENDERER PERSONALIZADO (TARJETAS) =====
    static class TarjetaPreguntaRenderer extends JPanel implements ListCellRenderer<String> {
        private JLabel lblTexto = new JLabel();

        public TarjetaPreguntaRenderer() {
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(8, 12, 8, 12));
            lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            add(lblTexto, BorderLayout.CENTER);
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value,
                int index, boolean isSelected, boolean cellHasFocus) {
            lblTexto.setText((index + 1) + ".  " + value);
            if (isSelected) {
                setBackground(SELECTED_BG);
                lblTexto.setForeground(ACCENT_CYAN);
                setBorder(BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 3, 0, 0, ACCENT_CYAN),
                        new EmptyBorder(8, 10, 8, 12)));
            } else {
                setBackground(index % 2 == 0 ? BG_CARD : BG_PANEL);
                lblTexto.setForeground(TEXT_PRIMARY);
                setBorder(new EmptyBorder(8, 12, 8, 12));
            }
            return this;
        }
    }

    // ===== SCROLLPANE ESTILIZADO =====
    static class ScrollModerno extends JScrollPane {
        public ScrollModerno(Component view) {
            super(view);
            setBorder(BorderFactory.createEmptyBorder());
            getViewport().setBackground(BG_PANEL);
            getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
            getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    thumbColor = SCROLLBAR_THUMB;
                    trackColor = BG_PANEL;
                }

                @Override
                protected JButton createDecreaseButton(int o) {
                    return btnInv();
                }

                @Override
                protected JButton createIncreaseButton(int o) {
                    return btnInv();
                }

                private JButton btnInv() {
                    JButton b = new JButton();
                    b.setPreferredSize(new Dimension(0, 0));
                    return b;
                }

                @Override
                protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(SCROLLBAR_THUMB);
                    g2.fill(new RoundRectangle2D.Float(r.x + 1, r.y, r.width - 2, r.height, 8, 8));
                    g2.dispose();
                }

                @Override
                protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                    g.setColor(BG_PANEL);
                    g.fillRect(r.x, r.y, r.width, r.height);
                }
            });
        }
    }

    // ===== PERSISTENCIA =====
    private static final String ARCHIVO_PREGUNTAS = "preguntas.txt";
    private static final String DELIMITADOR = ";;;";

    public static void guardarPreguntas() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PREGUNTAS));
            for (PreguntaTrivia p : listaPreguntas) {
                bw.write(p.enunciado + DELIMITADOR + p.opciones + DELIMITADOR + p.respuesta);
                bw.newLine();
            }
            bw.close();
            System.out.println("Preguntas guardadas en " + ARCHIVO_PREGUNTAS);
            SwingUtilities.invokeLater(() -> textArea.append("Preguntas guardadas en " + ARCHIVO_PREGUNTAS + "\n"));
        } catch (IOException e) {
            System.out.println("Error al guardar preguntas: " + e.getMessage());
        }
    }

    public static void cargarPreguntas() {
        File archivo = new File(ARCHIVO_PREGUNTAS);
        if (!archivo.exists()) {
            System.out.println("No se encontró " + ARCHIVO_PREGUNTAS + ". Se inicia sin preguntas.");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(DELIMITADOR);
                if (partes.length == 3) {
                    listaPreguntas.add(new PreguntaTrivia(partes[0].trim(), partes[1].trim(), partes[2].trim()));
                }
            }
            br.close();
            System.out.println("Cargadas " + listaPreguntas.size() + " preguntas desde " + ARCHIVO_PREGUNTAS);
        } catch (IOException e) {
            System.out.println("Error al cargar preguntas: " + e.getMessage());
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        // Cargar preguntas desde archivo ANTES de construir la interfaz
        cargarPreguntas();

        try {
            ServerSocket server = new ServerSocket(5000);

            // ===== INTERFAZ GRÁFICA DEL SERVIDOR (REDISEÑO MODERNO) =====
            JFrame frame = new JFrame("⚡ Trivia Battle — Server Console");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1050, 680);
            frame.setMinimumSize(new Dimension(800, 550));
            frame.getContentPane().setBackground(BG_DARK);
            frame.setLayout(new BorderLayout());

            // --- Barra superior decorativa ---
            JPanel topBar = new JPanel(new BorderLayout());
            topBar.setBackground(BG_PANEL);
            topBar.setBorder(BorderFactory.createCompoundBorder(
                    new MatteBorder(0, 0, 2, 0, ACCENT_PURPLE),
                    new EmptyBorder(10, 20, 10, 20)));
            JLabel lblTitulo = new JLabel("TRIVIA BATTLE — SERVER");
            lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblTitulo.setForeground(ACCENT_PURPLE);
            JLabel lblEstado = new JLabel("● esperando jugadores");
            lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            lblEstado.setForeground(ACCENT_GREEN);
            topBar.add(lblTitulo, BorderLayout.WEST);
            topBar.add(lblEstado, BorderLayout.EAST);
            frame.add(topBar, BorderLayout.NORTH);

            // ============================
            // PANEL IZQUIERDO: CONSOLA
            // ============================
            JPanel panelConsola = new JPanel(new BorderLayout());
            panelConsola.setBackground(BG_DARK);
            panelConsola.setBorder(new EmptyBorder(10, 12, 10, 6));

            JLabel lblConsola = new JLabel("  SERVER LOG");
            lblConsola.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lblConsola.setForeground(TEXT_SECONDARY);
            lblConsola.setBorder(new EmptyBorder(0, 0, 6, 0));
            panelConsola.add(lblConsola, BorderLayout.NORTH);

            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            textArea.setForeground(TEXT_CONSOLE);
            textArea.setBackground(BG_CONSOLE);
            textArea.setCaretColor(BG_CONSOLE);
            textArea.setSelectionColor(new Color(80, 250, 180, 40));
            textArea.setSelectedTextColor(Color.WHITE);
            textArea.setMargin(new Insets(14, 16, 14, 16));

            ScrollModerno scrollLog = new ScrollModerno(textArea);
            scrollLog.getViewport().setBackground(BG_CONSOLE);
            scrollLog.setBorder(BorderFactory.createLineBorder(BORDER_SUBTLE, 1));
            panelConsola.add(scrollLog, BorderLayout.CENTER);

            // ============================
            // PANEL DERECHO: CRUD
            // ============================
            JPanel panelCRUD = new JPanel(new BorderLayout(0, 8));
            panelCRUD.setBackground(BG_DARK);
            panelCRUD.setBorder(new EmptyBorder(10, 6, 10, 12));
            panelCRUD.setPreferredSize(new Dimension(400, 0));

            JLabel lblCrud = new JLabel("  GESTIÓN DE PREGUNTAS");
            lblCrud.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lblCrud.setForeground(TEXT_SECONDARY);
            panelCRUD.add(lblCrud, BorderLayout.NORTH);

            // Lista de preguntas con renderer personalizado
            DefaultListModel<String> modeloLista = new DefaultListModel<>();
            for (PreguntaTrivia p : listaPreguntas) {
                modeloLista.addElement(p.toString());
            }
            JList<String> jListPreguntas = new JList<>(modeloLista);
            jListPreguntas.setBackground(BG_PANEL);
            jListPreguntas.setForeground(TEXT_PRIMARY);
            jListPreguntas.setSelectionBackground(SELECTED_BG);
            jListPreguntas.setSelectionForeground(ACCENT_CYAN);
            jListPreguntas.setCellRenderer(new TarjetaPreguntaRenderer());

            ScrollModerno scrollPreguntas = new ScrollModerno(jListPreguntas);
            scrollPreguntas.setBorder(BorderFactory.createLineBorder(BORDER_SUBTLE, 1));
            panelCRUD.add(scrollPreguntas, BorderLayout.CENTER);

            // --- Formulario de entrada estilizado ---
            JPanel panelForm = new JPanel();
            panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
            panelForm.setBackground(BG_DARK);
            panelForm.setBorder(new EmptyBorder(8, 0, 0, 0));

            CampoEstilizado txtEnunciado = new CampoEstilizado("Enunciado");
            txtEnunciado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

            // 4 campos independientes para las opciones (en grid 2x2)
            JPanel panelOpciones = new JPanel(new GridLayout(2, 2, 6, 6));
            panelOpciones.setBackground(BG_DARK);
            panelOpciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 106));
            CampoEstilizado txtOpcionA = new CampoEstilizado("Opción A");
            CampoEstilizado txtOpcionB = new CampoEstilizado("Opción B");
            CampoEstilizado txtOpcionC = new CampoEstilizado("Opción C");
            CampoEstilizado txtOpcionD = new CampoEstilizado("Opción D");
            panelOpciones.add(txtOpcionA);
            panelOpciones.add(txtOpcionB);
            panelOpciones.add(txtOpcionC);
            panelOpciones.add(txtOpcionD);

            CampoEstilizado txtRespuesta = new CampoEstilizado("Respuesta (a, b, c, d)");
            txtRespuesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

            JPanel panelBotonesCRUD = new JPanel(new GridLayout(1, 2, 8, 0));
            panelBotonesCRUD.setBackground(BG_DARK);
            panelBotonesCRUD.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            BotonModerno btnAnadir = new BotonModerno("＋ Añadir Pregunta", ACCENT_GREEN, ACCENT_GREEN_H);
            BotonModerno btnEliminar = new BotonModerno("✕ Eliminar Seleccionada", ACCENT_RED, ACCENT_RED_H);
            panelBotonesCRUD.add(btnAnadir);
            panelBotonesCRUD.add(btnEliminar);

            panelForm.add(txtEnunciado);
            panelForm.add(Box.createVerticalStrut(6));
            panelForm.add(panelOpciones);
            panelForm.add(Box.createVerticalStrut(6));
            panelForm.add(txtRespuesta);
            panelForm.add(Box.createVerticalStrut(8));
            panelForm.add(panelBotonesCRUD);
            panelCRUD.add(panelForm, BorderLayout.SOUTH);

            // ActionListener - Añadir Pregunta
            btnAnadir.addActionListener(e -> {
                String enunciado = txtEnunciado.getText().trim();
                String opA = txtOpcionA.getText().trim();
                String opB = txtOpcionB.getText().trim();
                String opC = txtOpcionC.getText().trim();
                String opD = txtOpcionD.getText().trim();
                String respuesta = txtRespuesta.getText().trim().toLowerCase();

                if (enunciado.isEmpty() || opA.isEmpty() || opB.isEmpty()
                        || opC.isEmpty() || opD.isEmpty() || respuesta.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Rellena todos los campos.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Concatenar las 4 opciones en un solo String (formato original)
                String opciones = "a) " + opA + "   b) " + opB + "   c) " + opC + "   d) " + opD;

                PreguntaTrivia nueva = new PreguntaTrivia(enunciado, opciones, respuesta);
                listaPreguntas.add(nueva);
                modeloLista.addElement(nueva.toString());

                txtEnunciado.setText("");
                txtOpcionA.setText("");
                txtOpcionB.setText("");
                txtOpcionC.setText("");
                txtOpcionD.setText("");
                txtRespuesta.setText("");

                guardarPreguntas();
            });

            // ActionListener - Eliminar Seleccionada
            btnEliminar.addActionListener(e -> {
                int indice = jListPreguntas.getSelectedIndex();
                if (indice >= 0) {
                    listaPreguntas.remove(indice);
                    modeloLista.remove(indice);
                    guardarPreguntas();
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecciona una pregunta de la lista.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            });

            // --- Composición del JFrame ---
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelConsola, panelCRUD);
            splitPane.setDividerLocation(560);
            splitPane.setBorder(null);
            splitPane.setBackground(BG_DARK);
            splitPane.setDividerSize(6);
            frame.add(splitPane, BorderLayout.CENTER);

            // --- Botón START estilizado (panel inferior) ---
            JPanel panelInferior = new JPanel(new BorderLayout());
            panelInferior.setBackground(BG_DARK);
            panelInferior.setBorder(new EmptyBorder(8, 14, 12, 14));
            BotonModerno btnStart = new BotonModerno("▶  INICIAR PARTIDA", ACCENT_PURPLE, ACCENT_PURPLE_H);
            btnStart.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btnStart.setPreferredSize(new Dimension(0, 48));
            btnStart.addActionListener(e -> {
                if (listaPreguntas.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Añade al menos una pregunta antes de iniciar.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // El botón START sustituye a HiloAdmin
                new Thread(() -> {
                    try {
                        Servidor.juegoIniciado = true;
                        server.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
                btnStart.setEnabled(false);
                btnStart.setText("⏳  Partida en curso...");
                SwingUtilities.invokeLater(() -> lblEstado.setText("● partida en curso"));
            });
            panelInferior.add(btnStart, BorderLayout.CENTER);
            frame.add(panelInferior, BorderLayout.SOUTH);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // ===== FIN INTERFAZ GRÁFICA =====

            System.out.println("Servidor iniciado. Esperando jugadores (Máx 10)...");
            SwingUtilities.invokeLater(() -> textArea.append("Servidor iniciado. Esperando jugadores (Máx 10)...\n"));

            // El servidor se queda bloqueado esperando clientes.
            // Este bucle se romperá cuando el botón START cierre el serverSocket.
            while (!juegoIniciado) {
                try {
                    Socket cliente = server.accept();
                    if (clientes.size() < 10) {
                        ClientHandler ch = new ClientHandler(cliente);
                        clientes.add(ch);
                        ch.start();
                    } else {
                        cliente.close();
                    }
                } catch (SocketException e) {
                    // Esta excepción salta a propósito cuando el botón START hace server.close()
                    // Significa que ya no aceptamos más clientes. Salimos del bucle.
                    break;
                }
            }

            // Una vez roto el bucle, arranca la partida
            iniciarTrivia();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void iniciarTrivia() {
        if (clientes.isEmpty()) {
            System.out.println("No hay jugadores conectados. Fin del programa.");
            SwingUtilities.invokeLater(() -> textArea.append("No hay jugadores conectados. Fin del programa.\n"));
            return;
        }

        System.out.println("¡Comienza la trivia para " + clientes.size() + " jugadores!");
        SwingUtilities
                .invokeLater(() -> textArea.append("¡Comienza la trivia para " + clientes.size() + " jugadores!\n"));

        int totalPreguntas = listaPreguntas.size();

        for (int i = 0; i < totalPreguntas; i++) {
            PreguntaTrivia p = listaPreguntas.get(i);

            System.out.println("\nEnviando pregunta " + (i + 1) + "...");
            final int idx = i;
            SwingUtilities.invokeLater(() -> textArea.append("\nEnviando pregunta " + (idx + 1) + "...\n"));

            for (ClientHandler ch : clientes) {
                ch.nuevaPregunta(p.enunciado, p.opciones);
            }

            // El servidor principal duerme 15 segundos mientras los ClientHandlers recogen
            // respuestas
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
            }

            System.out.println("Tiempo agotado para la pregunta " + (i + 1));
            SwingUtilities.invokeLater(() -> textArea.append("Tiempo agotado para la pregunta " + (idx + 1) + "\n"));

            for (ClientHandler ch : clientes) {
                ch.validarRespuesta(p.respuesta);
            }

            mostrarRanking(false);
        }

        mostrarRanking(true);
    }

    public static void mostrarRanking(boolean finalizado) {
        String cabecera;

        if (finalizado == true) {
            cabecera = "=== RANKING FINAL ===";
        } else {
            cabecera = "--- Ranking Actual ---";
        }

        System.out.println("\n" + cabecera);
        SwingUtilities.invokeLater(() -> textArea.append("\n" + cabecera + "\n"));

        ClientHandler ganador = null;
        for (ClientHandler ch : clientes) {
            System.out.println(ch.getNick() + ": " + ch.puntos + " puntos");
            String lineaRanking = ch.getNick() + ": " + ch.puntos + " puntos";
            SwingUtilities.invokeLater(() -> textArea.append(lineaRanking + "\n"));
            if (finalizado) {
                if (ganador == null || ch.puntos > ganador.puntos) {
                    ganador = ch;
                }
            }
        }

        if (finalizado && ganador != null) {
            System.out.println("¡El ganador es " + ganador.getNick() + "!");
            String lineaGanador = "¡El ganador es " + ganador.getNick() + "!";
            SwingUtilities.invokeLater(() -> textArea.append(lineaGanador + "\n"));
        }

        // Guardar ranking final en archivo histórico
        if (finalizado) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("historico_rankings.txt", true));
                bw.write("Fecha: " + new java.util.Date().toString());
                bw.newLine();
                for (ClientHandler ch : clientes) {
                    bw.write(ch.getNick() + ": " + ch.puntos + " puntos");
                    bw.newLine();
                }
                if (ganador != null) {
                    bw.write("Ganador: " + ganador.getNick());
                    bw.newLine();
                }
                bw.write("-----------------------------");
                bw.newLine();
                bw.close();
                System.out.println("Ranking exportado a historico_rankings.txt");
                SwingUtilities.invokeLater(() -> textArea.append("Ranking exportado a historico_rankings.txt\n"));
            } catch (IOException ex) {
                System.out.println("Error al guardar el ranking histórico: " + ex.getMessage());
            }
        }

        for (ClientHandler ch : clientes) {
            ch.enviarMensaje("RANKING|" + cabecera);
            for (ClientHandler ch2 : clientes) {
                ch.enviarMensaje("RANKING|" + ch2.getNick() + ": " + ch2.puntos + " puntos");
            }
            if (finalizado && ganador != null) {
                ch.enviarMensaje("RANKING|¡El ganador es " + ganador.getNick() + "!");
                ch.enviarMensaje("FIN|");
            }
        }
    }
}