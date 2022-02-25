package homeworkPack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.*;
import java.awt.Shape.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Stack;
import java.util.*;
import java.awt.geom.*;
import java.awt.Component.*;

public class Homework3 {
	
	
	public static void main(String[] args) {
		JFrame frame = new HomeworkFrame("Homework 3");
	}

}

class HomeworkFrame extends JFrame{
	
	Info info;
	public HomeworkFrame(String title) {
		super(title);
		info = new Info();
		//add panels here
		this.add(new TopPanel(info));
		this.add(new BottomPanel(info));
		
		//frame information
		this.setLayout(new GridLayout(2,1));
		this.setSize(750,750);
		this.setLocation(500,50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}


class TopPanel extends JPanel{
	
	//buttons
	JButton undo;
	JButton triangle;
	JButton rectangle;
	JButton circle;
	//string for last selected button
	static String lastChoice;
	Panel canvas;
	public TopPanel(Info i) {
	
		super();
		
		canvas = new Canvas(i);
		this.setLayout(new BorderLayout());
		this.add(canvas,"Center");
		this.setBorder(BorderFactory.createTitledBorder("CANVAS"));
		
		JPanel panel = new JPanel();
		
		//buttons
		undo = new JButton("UNDO");
		triangle = new JButton("TRIANGLE");
		rectangle = new JButton("RECTANGLE");
		circle = new JButton("CIRCLE");
		panel.add(undo);
		panel.add(triangle);
		panel.add(rectangle);
		panel.add(circle);
		
		this.add(panel,"North");
		
		
		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//pop stack
				if(Canvas.shapes.isEmpty())return;
				Canvas.popShape();
				canvas.repaint();
			}
			
		});
		
		triangle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				lastChoice="Triangle";
				
			}
			
		});
		
		rectangle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lastChoice="Rectangle";
			}
			
		});
		
		circle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lastChoice="Circle";
			}
			
		});
	}
	
	public static String getSelected() {
		if(lastChoice==null)return "Circle";
		else return lastChoice;
	}


}


class BottomPanel extends JPanel{
	static JTextArea size;
	static JTextArea color;
	static JTextArea loc;
	static Info inf;
	public BottomPanel(Info i) {
		super();
		inf = i;
		this.setBorder(BorderFactory.createTitledBorder("Bottom Panel"));
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		
		size = new JTextArea(11,18);
		size.setText("20");
		size.setBorder(BorderFactory.createTitledBorder("SIZE"));
		color = new JTextArea(11,18);
		color.setText("45,56,10");
		color.setBorder(BorderFactory.createTitledBorder("COLOR"));
		loc = new JTextArea(11,18);
		loc.setBorder(BorderFactory.createTitledBorder("LOCATION"));
		p.add(size);
		p.add(color);
		p.add(loc);
		this.add(p,"South");
		
		
	}
	
	static void refresh() {
		loc.setText(inf.getLoc());
	}
	
	static Color getColor() {
		Color toreturn=new Color(100,100,100);
		try {
			String colorString = color.getText();
			String[] parts = colorString.split(",");
			int[] ints = new int[parts.length];
			for(int i = 0; i<parts.length;i++) {
				ints[i]=Integer.parseInt(parts[i]);
			}
			toreturn = new Color(ints[0],ints[1],ints[2]);
			return toreturn;
		}
		catch(Exception e) {
			return ColorFactory.getColor();
		}
		
		
		
	}
	
	static int getTxtSize() {
		int toreturn = Integer.parseInt(size.getText());
		return toreturn;
	}
	
	
}

class Info{	
	String loc;

	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
}



class Canvas extends Panel{
	Info inf;
	static Stack<Shape>shapes = new Stack<Shape>();
	public Canvas(Info i) {
		super();
		inf = i;
		this.addMouseListener(new MsListener());
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g.create();
		for(Shape s : shapes) {
			g2.draw(s);
			
		}
		
	}
	
	
	
	class MsListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			Graphics2D g2 = (Graphics2D) getGraphics().create();
			Shape shape = Canvas.getShape(e);
			g2.setColor(BottomPanel.getColor());
			g2.draw(shape);
			shapes.push(shape);
			inf.setLoc("("+e.getX()+","+e.getY()+")");
			BottomPanel.refresh();
		}		
	}
	
	static Shape getShape(MouseEvent e) {
		
		if(TopPanel.getSelected().equals("Triangle")) {
			return new Polygon(new int[] {e.getX(),(e.getX()+BottomPanel.getTxtSize()/2),e.getX()-BottomPanel.getTxtSize()/2},new int[] {e.getY(),(e.getY()+30),(e.getY()+30)},3);
		}
		if(TopPanel.getSelected().equals("Rectangle")) {
			return new Rectangle2D.Double(e.getX(),e.getY(),BottomPanel.getTxtSize(),BottomPanel.getTxtSize());
		}
		else {
			return new Ellipse2D.Double(e.getX(),e.getY(),BottomPanel.getTxtSize(),BottomPanel.getTxtSize());
		}
	}
	
	 static void popShape() {
		shapes.pop();
	}

}

class ColorFactory{
	public static Color getColor() {
		int r = (int) (Math.random() * 256);
		int g = (int) (Math.random() * 225);
		int b = (int) (Math.random() * 100);
		return new Color(r, g, b);		
	}
}
