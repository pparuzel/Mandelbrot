package mandelbrot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mandelbrot extends JFrame implements ActionListener, RegionChooser
{
    private GridBagLayout   layout;
    private ImagePanel      imagePanel;
    private JButton         button;
    private JButton         resetButton;
    private JButton         animButton;
    private JButton         resizeButton;
    private JLabel          zoomLabel;
    private JTextField      zoomInput;
    private JLabel          moveLabel;
    private JTextField      moveXInput;
    private JTextField      moveYInput;
    private JLabel          maxitLabel;
    private JTextField      maxitInput;
    private Timer           timer = new Timer(5, this);
    private Point           p;
    private JLabel          widheiLabel;
    private JTextField      widInput;
    private JTextField      heiInput;
    private JLabel          authorLabel;
    private boolean         isAnimated = false;
    public int dimX = 512;
    public int dimY = 512;

    public Mandelbrot()
    {
        super("Zbior Mandelbrota");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridBagConstraints c
                        =       new GridBagConstraints();
        layout          =       new GridBagLayout();
        imagePanel      =       new ImagePanel(this, timer, dimX, dimY);
        JPanel panel    =       new JPanel();
        button          =       new JButton("Draw Mandelbrot set");
        resetButton     =       new JButton("RESET");
        animButton      =       new JButton("Animation");
        resizeButton    =       new JButton("RESIZE");
        zoomLabel       =       new JLabel(" Zoom: ");
        moveLabel       =       new JLabel(" Move (x,y): ");
        maxitLabel      =       new JLabel(" Max iterations: ");
        widheiLabel     =       new JLabel(" (Width, Height)");
        zoomInput       =       new JTextField("190.0");
        moveXInput      =       new JTextField("400.00000000");
        moveYInput      =       new JTextField("256.00000000");
        maxitInput      =       new JTextField("100");
        widInput        =       new JTextField("512");
        heiInput        =       new JTextField("512");
        p               =       new Point(260, 293);
        authorLabel     =       new JLabel("Autor: Paweł Paruzel");
        imagePanel.setPreferredSize(new Dimension(dimX, dimY));
        resetButton.addActionListener(this);
        animButton.addActionListener(this);
        button.addActionListener(this);
        resizeButton.addActionListener(this);

        setContentPane(panel);
        setLayout(layout);

        c.fill = GridBagConstraints.HORIZONTAL;
        // IMAGE PANEL
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        panel.add(imagePanel, c);
        // ZOOM LABEL
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panel.add(zoomLabel, c);
        // MOVE LABEL
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(moveLabel, c);
        // MAXIMUM ITERATIONS LABEL
        c.gridx = 0;
        c.gridy = 3;
        panel.add(maxitLabel, c);
        // WIDTH/HEIGHT LABEL
        c.gridx = 0;
        c.gridy = 4;
        panel.add(widheiLabel, c);
        // AUTHOR LABEL
        c.gridx = 1;
        c.gridy = 5;
        panel.add(authorLabel, c);
        // ZOOM INPUT
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        panel.add(zoomInput, c);
        // MOVE X INPUT
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        panel.add(moveXInput, c);
        // MOVE Y INPUT
        c.gridx = 2;
        c.gridy = 2;
        panel.add(moveYInput, c);
        // MAXIMUM ITERATIONS INPUT
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;
        panel.add(maxitInput, c);
        // WIDTH INPUT
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        panel.add(widInput, c);
        // HEIGHT INPUT
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        panel.add(heiInput, c);
        // DRAW BUTTON
        c.gridx = 3;
        c.gridy = 1;
        panel.add(button, c);
        // ANIM BUTTON
        c.gridx = 3;
        c.gridy = 2;
        panel.add(animButton, c);
        // RESET BUTTON
        c.gridx = 3;
        c.gridy = 3;
        panel.add(resetButton, c);
        // RESIZE BUTTON
        c.gridx = 3;
        c.gridy = 4;
        panel.add(resizeButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == button)
        {
            isAnimated = false;
            String iZoom = zoomInput.getText();
            String iMoveX = moveXInput.getText();
            String iMoveY = moveYInput.getText();
            String iMaxit = maxitInput.getText();
            try
            {
                ImagePanel.ZOOM = Double.parseDouble(iZoom);
                ImagePanel.MOVEX = Double.parseDouble(iMoveX);
                ImagePanel.MOVEY = Double.parseDouble(iMoveY);
                ImagePanel.MAX = Integer.parseInt(iMaxit);
            }
            catch(Exception ex)
            {
                System.out.println(ex + ": Try passing a number.");
            }
            imagePanel.repaint();
            imagePanel.timer.stop();
        }
        else if(e.getSource() == animButton)
        {
            if(!isAnimated)
            {
                timer.start();
                isAnimated = true;
            }
            else
            {
                timer.stop();
                isAnimated = false;
                update();
            }
        }
        else if(e.getSource() == resetButton)
        {
            isAnimated = false;
            ImagePanel.ZOOM = 190.0;
            ImagePanel.MOVEX = 400.0;
            ImagePanel.MOVEY = 256.0;
            ImagePanel.MAX = 100;
            p = new Point(260, 293);
            imagePanel.update();
        }
        else if(e.getSource() == resizeButton)
        {
            String dx = widInput.getText();
            String dy = heiInput.getText();
            try
            {
                dimX = Integer.parseInt(dx);
                dimY = Integer.parseInt(dy);
                imagePanel.setGray();
                imagePanel.setPreferredSize(new Dimension(Integer.parseInt(dx), Integer.parseInt(dy)) );

            }
            catch(Exception ex)
            {
                System.out.println(ex + ": Try passing a number.");
            }
            pack();
        }
        if(isAnimated)
        {
            double n = 1.1;
            if(ImagePanel.MOVEX == 400 && ImagePanel.MOVEY == 256 && ImagePanel.ZOOM == 190)
            {
                ImagePanel.MOVEX += 256 - p.getX();
                ImagePanel.MOVEY += -256 + p.getY();
            }
            ImagePanel.ZOOM *= n;
            ImagePanel.MOVEX = n * (ImagePanel.MOVEX-256) + 256;
            ImagePanel.MOVEY = n * (ImagePanel.MOVEY-256) + 256;
            imagePanel.repaint();
            p.setLocation(256, 256);
        }
    }

    public static void main(String[] args)
    {
        Mandelbrot mb = new Mandelbrot();
        mb.pack();
        mb.setLocationRelativeTo(null);
        mb.setResizable(true);
        mb.setVisible(true);
    }

    @Override
    public void regionChosen(Point from, Point to)
    {
        double distX = imagePanel.endDrag.getX()-imagePanel.startDrag.getX();
        distX = distX < 0 ? -distX : distX;
        int n = dimX/(int) distX;
        double hdist = distX*0.5;

        ImagePanel.MOVEX += dimX/2-(imagePanel.startDrag.getX()+hdist);
        ImagePanel.MOVEY += -dimY/2+(imagePanel.startDrag.getY()+hdist);
        ImagePanel.ZOOM *= n;
        ImagePanel.MOVEX = n*ImagePanel.MOVEX - (n-1)*dimX/2;
        ImagePanel.MOVEY = n*ImagePanel.MOVEY - (n-1)*dimY/2;
    }

    public void update()
    {
        zoomInput.setText (String.valueOf(ImagePanel.ZOOM));
        moveXInput.setText(String.valueOf(ImagePanel.MOVEX));
        moveYInput.setText(String.valueOf(ImagePanel.MOVEY));
        maxitInput.setText(String.valueOf(ImagePanel.MAX));
    }
}