package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AppPanel extends JPanel {
	
	//protected enum Rule { ABOVE, BELOW, RIGHT, LEFT }
	
	private static final long serialVersionUID = 2199636082916978587L;
	
	//private Set<Rule> activeRules = new HashSet<Rule>();

	/*
	protected AppPanel() {
		//this(0, 0, AppFrame.d.width, AppFrame.d.height);
		//setLayout(null);
		//setLocation(0, 0);
	}
	
	protected AppPanel(int x, int y, int width, int height) {
		//setLayout(null);
		//setBounds(x, y, width, height);
	}

	protected void addPanelBounds(AppPanel app, Rule rule) {
		Dimension d = AppFrame.d;
		Rectangle rect = app.getBounds();
		int x = getX();
		int y = getY();
		int width = getWidth();
		int height = getHeight();
		boolean hasAbove = activeRules.contains(Rule.ABOVE);
		boolean hasBelow = activeRules.contains(Rule.BELOW);
		boolean hasRight = activeRules.contains(Rule.RIGHT);
		boolean hasLeft = activeRules.contains(Rule.LEFT);
		
		switch (rule) {
		case ABOVE:
			y = hasBelow ? y : 0;
			height = d.height - rect.height - y;
			break;
		case BELOW:
			y = (int) rect.getMaxY();
			height = hasAbove ? height - y : d.height - y;
			break;
		case RIGHT:
			x = (int) rect.getMaxX();
			width = hasLeft ? width - x : d.width - x;
			break;
		case LEFT:
			x = hasRight ? x : 0;
			width = d.width - rect.width - x;
			break;
		}
		
		activeRules.add(rule);
		setBounds(x, y, width, height);
	}
	*/
	
	protected void addAll(Component... comp) {
		for (Component c : comp) {
			add(c);
		}
	}
	
	protected ImageIcon getIcon(String fileName, int size) {
		URL iconURL = getClass().getClassLoader().getResource(fileName);
		Image origin = new ImageIcon(iconURL).getImage();
		Image scaled = origin.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
	
}