package mandelbrot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseMotionListener
{
    private BufferedImage image;
    private Complex c;
    private Rectangle selection;
    static double ZOOM = 190.0;
    static int MAX = 100;
    private boolean isDragging = false;
    static double MOVEX = 400.0;
    static double MOVEY = 256.0;
    private Mandelbrot m;
    int width;
    int height;
    Point startDrag;
    Point endDrag;
    Timer timer;

    public ImagePanel(Mandelbrot mb, Timer tm, int dimX, int dimY)
    {
        super();
        m           = mb;
        timer       = tm;
        width       = dimX;
        height      = dimY;
        startDrag   = new Point(0, 0);
        endDrag     = new Point(getWidth(), getHeight());
        addMouseMotionListener(this);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                double n;
                if(!e.isShiftDown())
                {
                    n = 2.0;
                }
                else
                {
                    n = 0.5;
                }
                startDrag = e.getPoint();
                MOVEX += dimX/2-startDrag.getX();
                MOVEY += -dimY/2+startDrag.getY();
                ZOOM *= n;
                MOVEX = n*(MOVEX-dimX/2) + dimX/2;
                MOVEY = n*(MOVEY-dimY/2) + dimY/2;
                update();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                startDrag = e.getPoint();
                selection = new Rectangle(startDrag);
                update();
                //System.out.println("MOUSE PRESSED AT "+ e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                selection.setSize(0,0);
                if(isDragging && e.getY() < height && e.getX() < width)
                {
                    endDrag = e.getPoint();
                    m.regionChosen(startDrag, endDrag);
                    isDragging = false;
                    update();
                    //System.out.println("MOUSE RELEASED AT "+ e.getPoint());
                }
            }
        });
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    private int iterationbrot(Complex c)
    {
        Complex z = new Complex(0, 0);
        int iteration = 0;
        while(z.sqrAbs() < 4.0 && iteration < MAX)
        {
            z.mul(new Complex(z)).add(c);
            //z = Complex.add( Complex.mul(z, z), c );
            //Complex muled = Complex.mul(z, z);
            //System.out.println( Complex.add(muled, c) );
            //System.out.println( new Complex(z).mul(new Complex(z)).add(new Complex(c)) );
            iteration++;
        }
        return iteration;
    }

    public void calculateMandel(double zoom, double movex, double movey)
    {
        for(int y=height-1; y>=0; y--)
        {
            for(int x=0; x<width; x++)
            {
                c = new Complex( (x-movex)/zoom, (y-movey)/zoom);
                int it = iterationbrot(c);
                // zamiast 100 mozna dac MAX i uzaleznimy wtedy kolorowanie od maksymalnej ilosci iteracji
                float color = (100f - it % 100) / (100f);
                //float grayscale = (float) (MAX - it) / MAX;
                // stary sposob kolorowania: image.setRGB(x, 511-y, (it % 100)*(it % 100)/10 << 8);
                if(it == MAX)
                    image.setRGB(x, height-1-y, Color.black.getRGB());
                else
                    image.setRGB(x, height-1-y, Color.HSBtoRGB(color, 1, 1));
                    //image.setRGB(x, 511-y, new Color(grayscale, grayscale, grayscale).getRGB());
            }
        }
    }

    public void setSize(int _x, int _y)
    {
        width = _x;
        height = _y;
        repaint();
    }

    public void setGray()
    {
        for(int y=0; y<height; y++)
        {
            for(int x=0; x<width; x++)
            {
                image.setRGB(x, y, 13158600);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        int mwidth = Math.max(selection.x - e.getX(), e.getX() - selection.x);
        selection.setSize(mwidth, mwidth);
        if(!isDragging) isDragging = true;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        calculateMandel(ZOOM, MOVEX, MOVEY);
        //g.setXORMode(Color.white); // Niby widac wyrazniej
        g.drawImage(image, 0, 0, Color.white, this);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(image, WIDTH, 0, this);
        if (selection != null)
        {
            g2d.setColor(new Color(225, 225, 255, 128));
            g2d.fill(selection);
            g2d.setColor(Color.GRAY);
            g2d.draw(selection);
        }
        g2d.dispose();
    }

    public void update()
    {
        m.update();
        repaint();
    }
}
