/**
 * Exemplare dieser Klasse zeichnen eine einfache Zeichnung.
 * Um die Zeichnung auf dem Bildschirm anzuzeigen, muss die
 * zeichneSimpel-Methode an einem Exemplar aufgerufen werden.
 *
 * Diese Klasse ist als fruehes Java-Lehrbeispiel mit BlueJ gedacht.
 * 
 * @author Petra Becker-Pechau
 */
class Zeichner
{
    /**
     * Zeichnet das Haus mit einer gelben Wand.
     */
    public void zeichneSimpel()
    {
        Kreis sonne;
        Quadrat wand;
        Quadrat fenster;
        Dreieck dach;

        sonne = new Kreis();
        sonne.sichtbarMachen();
        sonne.farbeAendern("rot");
        sonne.horizontalBewegen(180);
        sonne.vertikalBewegen(-10);
        sonne.groesseAendern(60);

        wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern("gelb");
        wand.vertikalBewegen(80);
        wand.groesseAendern(100);
        
        fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("blau");
        fenster.horizontalBewegen(20);
        fenster.vertikalBewegen(100);

        dach = new Dreieck();  
        dach.sichtbarMachen();
        dach.groesseAendern(50, 140);
        dach.horizontalBewegen(60);
        dach.vertikalBewegen(70);
    }

    
    
    
    
    
    
    
    
    
    
    
    /**
     * Zeichnet das Haus mit einer Wandfarbe, die der Anwender bestimmt.
     * 
     * @param wandfarbe die Farbe der Wand, z.B. "gelb" oder "gruen"
     */
    public void zeichneStrukturiert(String wandfarbe)
    {
        // Fuer das Zeichnen von Sonne und Wand gibt es bereits Hilfsmethoden :-)
        zeichneSonne();
        zeichneWand(wandfarbe);
        
        // Fuer das Zeichnen von Fenster und Dach bisher noch nicht :-(
        Quadrat fenster = new Quadrat();
        fenster.sichtbarMachen();
        fenster.farbeAendern("blau");
        fenster.horizontalBewegen(20);
        fenster.vertikalBewegen(100);

        Dreieck dach = new Dreieck();  
        dach.sichtbarMachen();
        dach.groesseAendern(50, 140);
        dach.horizontalBewegen(60);
        dach.vertikalBewegen(70);
    }

    private void zeichneSonne()
    {
        Kreis sonne = new Kreis();
        sonne.sichtbarMachen();
        sonne.farbeAendern("rot");
        sonne.horizontalBewegen(180);
        sonne.vertikalBewegen(-10);
        sonne.groesseAendern(60);
    }
    
    private void zeichneWand(String farbe)
    {
        Quadrat wand = new Quadrat();
        wand.sichtbarMachen();
        wand.farbeAendern(farbe);
        wand.vertikalBewegen(80);
        wand.groesseAendern(100);
    }
}
