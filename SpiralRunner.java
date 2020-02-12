import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SpiralRunner implements ActionListener {

	private JFrame frame;
	private JButton startTime;

	private JButton epMode;
	private JButton calc;
	private JLabel label;
	private SpringLayout layout;
	private Painter paint;
	private JPanel bottom;
	private JTextField range;
	private JLabel rangeLabel;
	private JLabel spiralDisp = new JLabel();
	private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = Toolkit.getDefaultToolkit().getScreenSize().height - 30;
	private JTextField spiralFact = new JTextField(7);
	private JButton upInc;
	private JButton downInc;
	private Timer timer = new Timer(1, this);

	private JTextField zoom = new JTextField(5);
	private JTextField crazy = new JTextField(5);

	private JLabel crazyLabel = new JLabel("Set crazy factor");
	private JLabel zoomLabel = new JLabel("Set zoom factor");
	private JLabel speedLabel = new JLabel("Set Speed");
	private JTextField speedField = new JTextField(5);

	private Icon frog = new ImageIcon("images//dancingfrog.gif");
	private JLabel frogL = new JLabel(frog);

	private JTextField xSize = new JTextField(5);
	private JTextField ySize = new JTextField(5);

	private JLabel xLabel = new JLabel("Set X Size");
	private JLabel yLabel = new JLabel("Set Y Size");

	SpiralRunner() {

		timer.setActionCommand("timer");
		upInc = new JButton("increment slowly");
		downInc = new JButton("decrement slowly");
		epMode = new JButton("toggle color mode");
		epMode.addActionListener(this);
		epMode.setActionCommand("epMode");
		BorderLayout l = new BorderLayout();
		startTime = new JButton("toggle movie");
		startTime.addActionListener(this);
		startTime.setActionCommand("timeButton");
		rangeLabel = new JLabel("Enter the number of dots");
		range = new JTextField(5);
		frame = new JFrame("Spiral");
		frame.setLayout(l);
		bottom = new JPanel();
		paint = new Painter();

		layout = new SpringLayout();
		bottom.setPreferredSize(new Dimension(100, 100));
		frame.add(paint, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.PAGE_END);
		layout = new SpringLayout();
		bottom.setLayout(layout);

		calc = new JButton("draw");
		label = new JLabel("Enter a spiral factor");
		bottom.add(label);
		bottom.add(spiralFact);

		bottom.add(epMode);
		bottom.add(crazy);
		bottom.add(zoom);
		bottom.add(zoomLabel);
		bottom.add(crazyLabel);
		crazy.addActionListener(this);
		crazy.setActionCommand("crazy");
		zoom.addActionListener(this);
		zoom.setActionCommand("zoom");

		bottom.add(startTime);
		bottom.add(rangeLabel);
		bottom.add(calc);
		bottom.add(range);
		bottom.add(spiralDisp);
		bottom.add(downInc);
		bottom.add(upInc);
		bottom.add(speedLabel);
		bottom.add(speedField);
		bottom.setOpaque(false);
		downInc.addActionListener(this);
		upInc.addActionListener(this);
		downInc.setActionCommand("down");
		upInc.setActionCommand("up");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		paint.setSize();
		bottom.add(xSize);
		bottom.add(ySize);
		bottom.add(xLabel);
		bottom.add(yLabel);

		layout.putConstraint(SpringLayout.WEST, crazyLabel, 5, SpringLayout.EAST, startTime);
		layout.putConstraint(SpringLayout.NORTH, crazyLabel, 5, SpringLayout.NORTH, calc);

		layout.putConstraint(SpringLayout.WEST, crazy, 5, SpringLayout.EAST, crazyLabel);
		layout.putConstraint(SpringLayout.NORTH, crazy, -5, SpringLayout.NORTH, crazyLabel);

		layout.putConstraint(SpringLayout.WEST, zoomLabel, 5, SpringLayout.EAST, crazy);
		layout.putConstraint(SpringLayout.NORTH, zoomLabel, 5, SpringLayout.NORTH, crazy);

		layout.putConstraint(SpringLayout.WEST, zoom, 5, SpringLayout.EAST, zoomLabel);
		layout.putConstraint(SpringLayout.NORTH, zoom, -5, SpringLayout.NORTH, zoomLabel);

		layout.putConstraint(SpringLayout.WEST, startTime, 5, SpringLayout.EAST, calc);
		layout.putConstraint(SpringLayout.NORTH, startTime, 0, SpringLayout.NORTH, calc);

		layout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, bottom);
		layout.putConstraint(SpringLayout.WEST, spiralFact, 10, SpringLayout.EAST, label);

		layout.putConstraint(SpringLayout.NORTH, rangeLabel, 10, SpringLayout.SOUTH, label);
		layout.putConstraint(SpringLayout.WEST, range, 5, SpringLayout.EAST, rangeLabel);
		layout.putConstraint(SpringLayout.NORTH, range, 0, SpringLayout.NORTH, rangeLabel);
		layout.putConstraint(SpringLayout.NORTH, calc, 5, SpringLayout.SOUTH, range);
		layout.putConstraint(SpringLayout.WEST, upInc, 80, SpringLayout.EAST, spiralFact);
		layout.putConstraint(SpringLayout.WEST, downInc, 5, SpringLayout.EAST, upInc);

		layout.putConstraint(SpringLayout.WEST, epMode, 5, SpringLayout.EAST, zoom);
		layout.putConstraint(SpringLayout.NORTH, epMode, 0, SpringLayout.NORTH, zoom);

		layout.putConstraint(SpringLayout.WEST, spiralDisp, 10, SpringLayout.EAST, spiralFact);
		layout.putConstraint(SpringLayout.WEST, speedLabel, 10, SpringLayout.EAST, epMode);
		layout.putConstraint(SpringLayout.NORTH, speedLabel, 0, SpringLayout.NORTH, epMode);
		layout.putConstraint(SpringLayout.WEST, speedField, 10, SpringLayout.EAST, speedLabel);
		layout.putConstraint(SpringLayout.NORTH, speedField, 0, SpringLayout.NORTH, epMode);

		layout.putConstraint(SpringLayout.WEST, xLabel, 5, BorderLayout.EAST, downInc);
		layout.putConstraint(SpringLayout.WEST, xSize, 5, BorderLayout.EAST, xLabel);

		layout.putConstraint(SpringLayout.WEST, yLabel, 5, BorderLayout.EAST, xSize);
		layout.putConstraint(SpringLayout.WEST, ySize, 5, BorderLayout.EAST, yLabel);

		ySize.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					paint.setYDotSize(Integer.parseInt(ySize.getText()));
				}catch(Exception e1) {
					paint.setYDotSize(5);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					paint.setYDotSize(Integer.parseInt(ySize.getText()));
				}catch(Exception e1) {
					paint.setYDotSize(5);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					paint.setYDotSize(Integer.parseInt(ySize.getText()));
				}catch(Exception e1) {
					paint.setYDotSize(5);
				}
			}
		});

		xSize.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try {
					paint.setXDotSize(Integer.parseInt(xSize.getText()));
				}catch(Exception e) {
					paint.setXDotSize(5);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					paint.setXDotSize(Integer.parseInt(xSize.getText()));
				}catch(Exception e1) {
					paint.setXDotSize(5);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					paint.setXDotSize(Integer.parseInt(xSize.getText()));
				}catch(Exception e1) {
					paint.setXDotSize(5);
				}
			}
		});

		calc.addActionListener(this);
		calc.setActionCommand("calc");
		spiralFact.addActionListener(this);

		speedField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				try {
					timer.setDelay(Integer.parseInt(speedField.getText()));
				} catch (Exception e) {
					timer.setDelay(1);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					timer.setDelay(Integer.parseInt(speedField.getText()));
				} catch (Exception e) {
					timer.setDelay(1);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try {
					timer.setDelay(Integer.parseInt(speedField.getText()));
				} catch (Exception e) {
					timer.setDelay(1);
				}
			}
		});

		range.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					paint.setRange(Double.parseDouble(range.getText()));
				} catch (Exception er) {
					paint.setRange(500);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					paint.setRange(Double.parseDouble(range.getText()));
				} catch (Exception er) {
					paint.setRange(500);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					paint.setRange(Double.parseDouble(range.getText()));
				} catch (Exception er) {
					paint.setRange(500);
				}
			}
		});

		zoom.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					paint.setZoom(Double.parseDouble(zoom.getText()));
				} catch (Exception er) {
					paint.setZoom(1);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					paint.setZoom(Double.parseDouble(zoom.getText()));
				} catch (Exception er) {
					paint.setZoom(1);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					paint.setZoom(Double.parseDouble(zoom.getText()));
				} catch (Exception er) {
					paint.setZoom(1);
				}
			}
		});

		crazy.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					paint.setCrazy(Double.parseDouble(crazy.getText()));
				} catch (Exception er) {
					paint.setCrazy(1);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					paint.setCrazy(Double.parseDouble(crazy.getText()));
				} catch (Exception er) {
					paint.setCrazy(1);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					paint.setCrazy(Double.parseDouble(crazy.getText()));
				} catch (Exception er) {
					paint.setCrazy(1);
				}
			}
		});
		bottom.revalidate();
	}

	public static void main(String[] args) {
		new SpiralRunner();
	}

	//Updates all the variables regardless of whether or not they have been changed
	private void updateVar() {
		try {
			paint.setSpiral(Double.parseDouble(spiralFact.getText()));
		} catch (Exception e) {
			paint.setSpiral(1);
		}
		try {
			paint.setZoom(Double.parseDouble(zoom.getText()));
		} catch (Exception e) {
			paint.setZoom(1);
		}
		try {
			paint.setRange(Double.parseDouble(range.getText()));
		} catch (Exception e) {
			paint.setRange(1);
		}
		try {
			paint.setCrazy(Double.parseDouble(crazy.getText()));
		} catch (Exception e) {
			paint.setCrazy(1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// up and down buttons good
		double spir = paint.getSpiral();

		switch (e.getActionCommand()) {
		case "up":
			paint.setSpiral(spir + .0001);
			spir += .0001;
			break;
		case "down":
			paint.setSpiral(spir - .0001);
			spir -= .0001;
			break;
		case "timeButton":
			if (timer.isRunning()) {
				timer.stop();
			} else {
				timer.start();
			}
			break;
		case "timer":
			paint.setSpiral(spir + .00003);
			break;
		case "epMode":

			if(!paint.getEp()) {
				frame.add(frogL, BorderLayout.EAST);
				frame.repaint();
			}else {
				frame.remove(frogL);
				frame.repaint();
			}

			paint.changeEp();
			break;
		default:
			updateVar();
			break;
		}
		spiralDisp.setText(((int)(spir*100000.0))/100000.0+"");
	}
}