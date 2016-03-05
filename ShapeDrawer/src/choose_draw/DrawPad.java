package choose_draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class DrawPad extends JPanel{
	private JButton clear;
	private JButton undo;
	private static JLabel statusBar;
	private static JComboBox colors;
	private JComboBox shape_Box;
	private JCheckBox fill_Box;
	private static Color color_Selected;
	private static JPanel center;
	private static String draw_Shape;
	private static Graphics2D shape;
	private static JPanel controls;
	private String draw_What;
	private static boolean fill_In, fill_Rect,fill_Oval;
	private static final Color[] fill_Colors = {Color.red, Color.green,Color.blue, Color.black};
	private static final String[] color_Names = {"Red","Green","Blue","Black"};
	private static final String[] shape_Names = {"Rectangle","Oval","Edge"};
	
	 public DrawPad() {  
		 	
		 	DrawingArea canvas = new DrawingArea();
		 	setControls(new JPanel());
			colors = new JComboBox(color_Names);
			shape_Box= new JComboBox(shape_Names);
			fill_Box = new JCheckBox("Filled");
			shape_Box.setMaximumRowCount(3);
			colors.setMaximumRowCount(4);
			
			// adding a control JPanel
			
			getControls().setSize(5,5);
			undo = new JButton("Undo");
			undo.setSize(5, 5);
			clear = new JButton("Clear");
			clear.setSize(5, 5);
			getControls().add(undo);
			getControls().add(clear);
			getControls().add(colors);
			getControls().add(shape_Box);
			getControls().add(fill_Box);
			setStatusBar(new JLabel("Status Bar"));
			clear.addActionListener(canvas);
			undo.addActionListener(canvas);
			fill_Box.addActionListener(canvas);
			colors.addActionListener(canvas);
			shape_Box.addActionListener(canvas);
			//adding control JPanel to the North
			
			
			
			setLayout(new BorderLayout(3,3));
			add(getControls(),BorderLayout.NORTH);
			//Center of window. a JPanel called draw_Pad
			add(canvas, BorderLayout.CENTER);
			//South of window
			
			add(getStatusBar(),BorderLayout.SOUTH);
			
	 } // end constructor

	public static JLabel getStatusBar() {
		return statusBar;
	}

	public static void setStatusBar(JLabel statusBar) {
		DrawPad.statusBar = statusBar;
	}
	
	public static JPanel getControls() {
		return controls;
	}

	public static void setControls(JPanel controls) {
		DrawPad.controls = controls;
	}

	class DrawingArea extends JPanel implements ActionListener{
		
		Shape[] shapes_Nums = new Shape[10]; // holds a list of up to 10 shape
		Boolean undoDraw = false;
		Boolean clearDraw = false;
		int shapeCount = 0;  // the actual number of shapes
		Color currentColor = Color.black ;  // current color; when a shape is created, this is its color
		int fill_Count = 0;


		DrawingArea() {
			addMouseListener(new MouseClickHandler());
	        addMouseMotionListener(new MouseClickHandler());
		}   
		 public void paintComponent(Graphics g) {
             // In the paint method, all the shapes in ArrayList are
             // copied onto the canvas.
          g.setColor(Color.WHITE);
          g.fillRect(0,0,getSize().width,getSize().height);
          for (int i = 0; i < shapeCount; i++) {
        	  if(undoDraw == true){
        		  undoFunction();
        	  }else{
              Shape s = shapes_Nums[i];
              s.draw(g);
              
        	  }
          }
         // System.out.println("Clear "+ clearDraw);
          if(clearDraw == true){
        	 clearFunction(g);
        	
          }
          g.setColor(Color.BLACK);  // draw a black border around the edge of the drawing area
          g.drawRect(0,0,getWidth()-1,getHeight()-1);
      }   

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == colors) {
				switch ( colors.getSelectedIndex() ) {
				case 0: currentColor = Color.RED;   
				break;
				case 1: currentColor = Color.GREEN;   
				break;
				case 2: currentColor = Color.BLUE;    
				break;
				case 3: currentColor = Color.BLACK;   
				break;

				}
			}if(event.getSource() == fill_Box){
				if(fill_Count == 0){
					fill_In = true;
					fill_Count ++;
				}else if( fill_Count > 0){
					fill_In = false;
					fill_Count = 0;
				}
				 
			}
			
			if(event.getSource() == shape_Box){
				if (event.getSource() == shape_Box ) {
	                switch (shape_Box.getSelectedIndex() ) {
	                case 0: 
	                	addShape(new Rectangle());
	                	draw_What = "Rect";
	                	
	                break;
	                case 1:
	                	addShape(new Ellipse());
	                	draw_What = "Oval";
	                	
	                break;
	                case 2: 
	                	addShape(new Edge());  
	                	draw_What = "Line";
	                	 break;
	                }
	          }
        }
			if(event.getSource() == undo ){
				undoDraw = true;
				}
			if(event.getSource() == clear ){
				clearDraw = true;
				}
		}//End of Action Performed
		
		void undoFunction(){
			for(int j = shapes_Nums.length-1;j>=0;j--){
				if(shapes_Nums[j] != null) {
					shapes_Nums[j] = null;
					repaint();
					}
				}
		}
		void clearFunction(Graphics g){
			 g.clearRect(0,0,getSize().width,getSize().height);
		}
		void addShape(Shape shape) {	
            // Add the shape to the canvas, and set its size/position and color.
            // The shape is added at the top-left corner, with size 80-by-50.
            // Then redraw the canvas to show the newly added shape.
			shape.setColor(currentColor);
			if(draw_What == "Oval"){
				shape.reshape(5,80,175,80);
				if(fill_In == true){
            		fill_Oval = true;
            	}
				shapes_Nums[shapeCount] = shape;
				shapeCount++;
				repaint();
			}else if(draw_What == "Rect"){
				shape.reshape(30,5,80,150);
				if(fill_In == true){
            		fill_Rect = true;
            	}
				shapes_Nums[shapeCount] = shape;
				shapeCount++;
				repaint();
			}else if(draw_What == "Line"){
				shape.reshape(5,80,175,80);
				shapes_Nums[shapeCount] = shape;
				shapeCount++;
				repaint();
			}
		}// End of addShape Method
		 Shape Dragged = null;  // This is null unless a shape is being dragged.
		    // A non-null value is used as a signal that dragging
		    // is in progress, as well as indicating which shape
		    // is being dragged.

			int prevDragX;  // During dragging, these record the x and y coordinates of the
			int prevDragY;  //    previous position of the mouse.

			private class MouseClickHandler extends MouseAdapter{
			public void mousePressed(MouseEvent event) {
			// User has pressed the mouse.  Find the shape that the user has clicked on, if
			// any.  If there is a shape at the position when the mouse was clicked, then
			// start dragging it.  
			int x = event.getX();  // x-coordinate of point where mouse was clicked
			int y = event.getY();  // y-coordinate of point 
			  for ( int i = shapeCount-1; i >= 0; i-- ) {  // check shapes from front to back
	                Shape s = shapes_Nums[i];
	                if (s.containsPoint(x,y)) {
	                    Dragged = s;
	                    prevDragX = x;
	                    prevDragY = y;
	                   
	                        for (int j = i; j < shapeCount-1; j++) {
	                                // move the shapes following s down in the list
	                            shapes_Nums[j] = shapes_Nums[j+1];
	                        }
	                        shapes_Nums[shapeCount-1] = s;  // put s at the end of the list
	                        repaint();  // repaint canvas to show s in front of other shapes
	                    }
			  	}
	        }
			public void mouseExited(MouseEvent event){
				statusBar.setText("Mouse outside Panel");
			}
			
			public void mouseMoved(MouseEvent event){
				statusBar.setText(String.format("[%d,%d]", event.getX(),event.getY()));
			}
			public void mouseDragged(MouseEvent event) {
			// User has moved the mouse.  Move the dragged shape by the same amount.
			int x = event.getX();
			int y = event.getY();
			if (Dragged != null) {
			Dragged.moveBy(x - prevDragX, y - prevDragY);
			prevDragX = x;
			prevDragY = y;
			repaint();      // redraw canvas to show shape in new position
			}
		}

			public void mouseReleased(MouseEvent event) {
			// User has released the mouse.  Move the dragged shape, then set
			// shapeBeingDragged to null to indicate that dragging is over.
			int x = event.getX();
			int y = event.getY();
			if (Dragged != null) {
				Dragged.moveBy(x - prevDragX, y - prevDragY);
				Dragged = null;
				repaint();
				}
			}
		}
	}// End of DrawArea Class
	
	static abstract class Shape {
    	
        // When a shape is first constructed, it has height and width zero
        // and a default color of white.

		int left, top;      // Position of top left corner of shape that bounds this shape.
		int width, height;  // Size of the bounded shape.
		Color color = color_Selected;  // Color of this shape.

		void reshape(int left, int top, int width, int height) {
            // Set the position and size of this shape.
		
			this.left = left;
			this.top = top;
			this.width = width;
			this.height = height;
		}
		void setColor(Color color) {
            // Set the color of this shape
			this.color = color;
		}
		void moveBy(int dx, int dy) {
            // Move the shape by dx pixels horizontally and dy pixels vertically
            // (by changing the position of the top-left corner of the shape).
        left += dx;
        top += dy;
    }
		
		 boolean containsPoint(int x, int y) {
             // Check whether the shape contains the point (x,y).
         if (x >= left && x < left+width && y >= top && y < top+height)
             return true;
         else
             return false;
     }

		abstract void draw(Graphics g);  
		
 }  // End of Shape class
	
	//-------------------------------------------Classes for different shapes---------------------------------------------------------------//

	static class Rectangle extends Shape {
        // This class represents rectangle shapes.
		void draw(Graphics g) {
			Graphics2D DShape = (Graphics2D)g;
			DShape.setColor(color);
			if(fill_Rect == true){
				DShape.fillRect(left,top,width,height);
			}
			DShape.draw(new Rectangle2D.Float(left,top,width,height));
		}
	}

	static class Ellipse extends Shape {
       // This class represents Ellipse shapes.
		void draw(Graphics g) {
			System.out.println(fill_In);
			Graphics2D DShape = (Graphics2D)g;
			DShape.setColor(color);
			if(fill_Oval == true){
				DShape.fillOval(left,top,width,height);
			}
			DShape.draw(new Ellipse2D.Float(left,top,width,height));
		}
     
	}
	
	static class Edge extends Shape {
	// This class represents line shapes.
		void draw(Graphics g) {
			Graphics2D Dshape = (Graphics2D)g;
			Dshape.setColor(color);
			Dshape.draw(new Line2D.Float(left,top,width,height));
		}
	}	
}// End of DrawPad Class
