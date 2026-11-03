import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Leinwand ist eine Klasse, die einfache Zeichenoperationen auf einer
 * LEINWANDartigen Zeichenflaeche ermoeglicht. Sie ist eine vereinfachte Version
 * der Klasse Canvas (englisch fuer Leinwand) des JDK und wurde speziell fuer
 * das Projekt "Figuren" geschrieben.
 * 
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 * @author: Axel Schmolitzky
 * @author: Fredrik Winkler
 * 
 * @version: 2.2
 */
class Leinwand extends JPanel
{
    private static final Leinwand LEINWAND;
    private static final Map<String, Color> FARBEN;

    static
    {
        LEINWAND = new Leinwand("Leinwand", 400, 300, Color.white);

        FARBEN = new HashMap<String, Color>();
        FARBEN.put("rot", Color.red);
        FARBEN.put("blau", Color.blue);
        FARBEN.put("gelb", Color.yellow);
        FARBEN.put("gruen", Color.green);
        FARBEN.put("lila", Color.magenta);
        FARBEN.put("weiss", Color.white);
        FARBEN.put("schwarz", Color.black);
    }

    /**
     * Liefert eine Referenz auf das einzige Exemplar dieser Klasse.
     * 
     * @return Leinwand
     */
    public static Leinwand gibLeinwand()
    {
        return LEINWAND;
    }

    private final Map<Object, ShapeMitFarbe> _figuren;

    private final JFrame _frame;
    private final Color _hintergrundfarbe;
    private Image _image;
    private Graphics2D _graphics;

    /**
     * Erzeuge eine Leinwand.
     * 
     * @param titel
     *            Titel, der im Rahmen der Leinwand angezeigt wird
     * @param breite
     *            die gewuenschte Breite der Leinwand
     * @param hoehe
     *            die gewuenschte Hoehe der Leinwand
     * @param grundfarbe
     *            die Hintergrundfarbe der Leinwand
     */
    private Leinwand(String titel, int breite, int hoehe, Color grundfarbe)
    {
        _figuren = new LinkedHashMap<Object, ShapeMitFarbe>();

        _frame = new JFrame();
        _frame.setContentPane(this);
        _frame.setTitle(titel);
        setPreferredSize(new Dimension(breite, hoehe));
        _frame.pack();
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        _hintergrundfarbe = grundfarbe;
        calculateGraphics();
        setzeSichtbarkeit(true);

        addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt)
            {
                neuZeichnen();
            }
        });
    }

    private void calculateGraphics()
    {
        Dimension size = getSize();
        _image = createImage(size.width, size.height);

        _graphics = (Graphics2D) _image.getGraphics();
        _graphics.setColor(_hintergrundfarbe);
        _graphics.fillRect(0, 0, size.width, size.height);
    }

    private void neuZeichnen()
    {
        calculateGraphics();

        synchronized (_figuren)
        {
            for (ShapeMitFarbe shape: _figuren.values())
            {
                shape.draw(_graphics);
            }
        }

        repaint();
    }

    public void paint(Graphics g)
    {
        g.drawImage(_image, 0, 0, null);
    }

    /**
     * Setze, ob diese Leinwand sichtbar sein soll oder nicht. Wenn die Leinwand
     * sichtbar gemacht wird, wird ihr Fenster in den Vordergrund geholt. Diese
     * Operation kann auch benutzt werden, um ein bereits sichtbares
     * Leinwandfenster in den Vordergrund (vor andere Fenster) zu holen.
     * 
     * @param sichtbar
     *            boolean fuer die gewuenschte Sichtbarkeit: true fuer sichtbar,
     *            false fuer nicht sichtbar.
     */
    public void setzeSichtbarkeit(boolean sichtbar)
    {
        _frame.setVisible(sichtbar);
    }

    /**
     * Zeichne fuer das gegebene Figur-Objekt eine Java-Figur (einen Shape) auf
     * die Leinwand.
     * 
     * @param figur
     *            das Figur-Objekt, fuer das ein Shape gezeichnet werden soll
     * @param farbe
     *            die Farbe der Figur
     * @param shape
     *            ein Objekt der Klasse Shape, das tatsaechlich gezeichnet wird
     */
    public void zeichne(Object figur, String farbe, Shape shape)
    {
        ShapeMitFarbe shapeMitFarbe = new ShapeMitFarbe(shape, farbe);
        
        boolean geloescht;
        synchronized (_figuren)
        {
            if (geloescht = _figuren.containsKey(figur))
            {
                _figuren.remove(figur);
            }
            _figuren.put(figur, shapeMitFarbe);
        }
        
        if (geloescht)
        {
            neuZeichnen();
        }
        else
        {
            shapeMitFarbe.draw(_graphics);
            repaint(shape.getBounds());
        }
    }

    /**
     * Entferne die gegebene Figur von der Leinwand.
     * 
     * @param figur
     *            die Figur, deren Shape entfernt werden soll
     */
    public void entferne(Object figur)
    {
        synchronized (_figuren)
        {
            _figuren.remove(figur);
        }
        neuZeichnen();
    }

    /**
     * Setze die Zeichenfarbe der Leinwand.
     * 
     * @param farbname
     *            der Name der neuen Zeichenfarbe.
     */
    public void setzeZeichenfarbe(String farbname)
    {
        Color farbe = FARBEN.get(farbname);
        _graphics.setColor(farbe != null ? farbe : Color.black);
    }

    /**
     * Warte fuer die angegebenen Millisekunden. Mit dieser Operation wird eine
     * Verzoegerung definiert, die fuer animierte Zeichnungen benutzt werden
     * kann.
     * 
     * @param millisekunden
     *            die zu wartenden Millisekunden
     */
    public void warte(int millisekunden)
    {
        try
        {
            Thread.sleep(millisekunden);
        }
        catch (Exception e)
        {
            // Exception ignorieren
        }
    }

    /***************************************************************************
     * Interne Klasse ShapeMitFarbe - Da die Klasse Shape des JDK nicht auch
     * eine Farbe mitverwalten kann, muss mit dieser Klasse die Verknuepfung
     * modelliert werden.
     */
    private class ShapeMitFarbe
    {
        private Shape shape;
        private String farbe;

        public ShapeMitFarbe(Shape shape, String farbe)
        {
            this.shape = shape;
            this.farbe = farbe;
        }

        public void draw(Graphics2D graphic)
        {
            setzeZeichenfarbe(farbe);
            graphic.fill(shape);
        }
    }
}
