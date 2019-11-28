
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

import java.util.*;

public class Mypanel extends JPanel
{
    ImageIcon img;
    public Mypanel( )
    {
    }
    public void setimg(ImageIcon img)
    {
        this.img = img;
    }
    public void paintComponent( Graphics g )
    {
        super.paintComponent(g);
        g.drawImage(img.getImage(),0,0,null);
    }
}