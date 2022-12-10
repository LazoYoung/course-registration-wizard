package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AppPanel extends JPanel {
	
	private static final long serialVersionUID = 2199636082916978587L;
	
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