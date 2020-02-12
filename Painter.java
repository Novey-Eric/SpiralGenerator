import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;

public class Painter extends JComponent {

	private static final long serialVersionUID = 1L;
	private boolean epMode=false;
	private double spiral;
	private double range;
	private double zoom = 1;
	private double crazy = 1;
	private int xCenter;
	private int yCenter;
	private int xSize = 5;
	private int ySize = 5;
	private Random ra = new Random();
	private double increment;

	public void setSize() {
		xCenter = this.getWidth()/2;
		yCenter = this.getHeight()/2;
	}

	public void changeEp() {
		epMode = !epMode;
	}

	public void setXDotSize(int x) {
		xSize = x;
	}

	public void setYDotSize(int y) {
		ySize = y;
	}

	public boolean getEp() {
		return epMode;
	}

	public void setSpiral(double d) {
		spiral = d;
		increment= (2*Math.PI)*spiral;
	}

	public double getSpiral() {
		return spiral;
	}

	public void setRange(double d) {
		range = d;
	}

	public void setZoom(double d) {
		zoom = d;
	}

	public void setCrazy(double d) {
		crazy = d;
	}

	public void paintComponent(Graphics g) {
		double iZ;
		double iInc;
		for(double i = 0; i<range; i++) {

			if(epMode) {
				int r = (((ra.nextInt()%255)+255)%255);
				int gc = (((ra.nextInt()%255)+255)%255);
				int b = (((ra.nextInt()%255)+255)%255);
				g.setColor(new Color(r, gc, b));
			}

			iZ = i*zoom;
			iInc = i*increment;
			g.fillOval(xCenter+(int)((iZ)*Math.cos(iInc*crazy)), yCenter+(int)((iZ)*Math.sin(iInc/crazy)), xSize, ySize);
		}
		repaint();
	}
}