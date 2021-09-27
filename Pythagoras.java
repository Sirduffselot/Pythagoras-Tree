
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.ArrayList;

class Pythagoras extends Frame {
   JMenu menu;
   JMenuItem pythagoras, exit;
   JFrame f = new JFrame("Pythagoras Tree");

   Pythagoras() {
      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      JMenuBar mb = new JMenuBar();
      menu = new JMenu("Menu");
      pythagoras = new JMenuItem("Pythagoras");
      exit = new JMenuItem("Exit");
      menu.add(pythagoras);
      menu.add(exit);
      mb.add(menu);
      f.setJMenuBar(mb);
      f.setPreferredSize(new Dimension(500, 500));

      //If pythagoras clicked, begins pythagoras tree
      pythagoras.addActionListener(e -> {
         f.add(new createTree());
         f.setVisible(true);
      });

      //If exit clicked, ends frame
      exit.addActionListener(e -> {
         f.dispose();
      });

      f.pack();
      f.setVisible(true);
   }
}



class createTree extends Canvas{
   int clickCounter;
   float x_pointA_initial = 0;
   float y_pointA_initial = 0;
   float x_pointB_initial = 0;
   float y_pointB_initial = 0;
   Point pointA, pointB;
   final int max_depth = 9;
   final int max_distance = 5;
   ArrayList<Integer> coordsX = new ArrayList<Integer>();
   ArrayList<Integer> coordsY = new ArrayList<Integer>();

   //Waits for two clicks
   createTree()
   {
      clickCounter=1;
      addMouseListener(new MouseAdapter()
      {
         public void mousePressed(MouseEvent click)
         {
            if (clickCounter == 1)
            {
               pointA = click.getPoint();
               x_pointA_initial = pointA.x;
               y_pointA_initial = pointA.y;
               clickCounter = 2;
            }
            else if (clickCounter == 2)
            {
               pointB = click.getPoint();
               x_pointB_initial = pointB.x;
               y_pointB_initial = pointB.y;
               clickCounter = 1;
               repaint();
            }
            else
            {
               System.out.println("Error");
            }
         }
      });
   }


   //Generates a random color
   public Color generateRandomColor(){
      Random rand = new Random();
      float r = rand.nextFloat();
      float g = rand.nextFloat();
      float b = rand.nextFloat();
      Color randomColor = new Color (r, g, b);

      return randomColor;
   }


   public void draw(Graphics g, float x_pointA, float y_pointA, float x_pointB, float y_pointB, int current_depth){
      float x_delta = x_pointB - x_pointA;
      float y_delta = y_pointA - y_pointB;

      float distance_between_points = (float)(Math.sqrt(Math.pow(x_delta, 2) + Math.pow(y_delta, 2)));


      if (distance_between_points < max_distance) {
         //If too small, will stop calls
         return;
      }
      else if (current_depth == max_depth) {
         //If reach max depth of 9, will stop calls
         return;
      }
      else{
         //Begins next recursion

         //Calculates square and triangle coordinates
         float x_pointC = x_pointB - y_delta;
         float y_pointC = y_pointB - x_delta;
         float x_pointD = x_pointA - y_delta;
         float y_pointD = y_pointA - x_delta;
         float x_pointE = x_pointD + 0.5F * (x_delta - y_delta);
         float y_pointE = y_pointD - 0.5F * (x_delta + y_delta);

         //Cast floats to ints
         int x_pointA_int = (int)x_pointA;
         int y_pointA_int = (int)y_pointA;
         int x_pointB_int = (int)x_pointB;
         int y_pointB_int = (int)y_pointB;
         int x_pointC_int = (int)x_pointC;
         int y_pointC_int = (int)y_pointC;
         int x_pointD_int = (int)x_pointD;
         int y_pointD_int = (int)y_pointD;
         int x_pointE_int = (int)x_pointE;
         int y_pointE_int = (int)y_pointE;

         //Convert points to int
         int[] x_coords_square = {x_pointA_int, x_pointB_int, x_pointC_int, x_pointD_int};
         int[] y_coords_square = {y_pointA_int, y_pointB_int, y_pointC_int, y_pointD_int};
         int[] x_coords_triangle = {x_pointC_int, x_pointD_int, x_pointE_int};
         int[] y_coords_triangle = {y_pointC_int, y_pointD_int, y_pointE_int};


         //Draw square
         g.setColor(Color.black);
         g.drawPolygon(x_coords_square, y_coords_square, 4);
         g.setColor(generateRandomColor());
         g.fillPolygon(x_coords_square, y_coords_square, 4);

         //Draw triangle
         g.setColor(Color.black);
         g.drawPolygon(x_coords_triangle, y_coords_triangle, 3);
         g.setColor(generateRandomColor());
         g.fillPolygon(x_coords_triangle, y_coords_triangle, 3);

         //Recursive calls
         draw(g, x_pointD, y_pointD, x_pointE, y_pointE, current_depth + 1);
         draw(g, x_pointE, y_pointE, x_pointC, y_pointC, current_depth + 1);
      }
   }

   public void paint(Graphics g) {
      draw((Graphics2D) g, x_pointA_initial, y_pointA_initial, x_pointB_initial, y_pointB_initial, 0);
   }
}
