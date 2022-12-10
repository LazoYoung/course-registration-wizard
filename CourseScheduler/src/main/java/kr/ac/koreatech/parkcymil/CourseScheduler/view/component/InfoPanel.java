package kr.ac.koreatech.parkcymil.CourseScheduler.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class InfoPanel extends AppPanel {
	
	private static final long serialVersionUID = 1805488919917305748L;
	private Dimension size = new Dimension(1000, 100);
	private JLabel updateText;

	protected InfoPanel() {
		// Icon by Ayub Irawan
		var icon = getIcon("icon.png", 50);
		var titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		var updFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
		var creditFont = new Font(Font.SERIF, Font.BOLD, 14);
		var imgLabel = new JLabel(icon);
		var titleText = new JLabel("시간표 앱");
		var authorText = new JLabel("개발자: 박찬영");
		var snoText = new JLabel("학번: 2019136063");
		var emailText = new JLabel("parkcymil@koreatech.ac.kr");
		updateText = new JLabel();
		
		setLayout(null);
		setMinimumSize(size);
		setPreferredSize(size);
		setBackground(Color.GRAY);
		addAll(imgLabel, titleText, authorText, snoText, emailText, updateText);
		
		titleText.setFont(titleFont);
		updateText.setFont(updFont);
		authorText.setFont(creditFont);
		snoText.setFont(creditFont);
		emailText.setFont(creditFont);
		imgLabel.setBounds(0, 0, 100, 100);
		titleText.setBounds(100, 30, 500, 20);
		updateText.setBounds(100, 55, 500, 20);
		
		updateTime();
		this.addComponentListener(new ComponentAdapter() {
			final int margin = 200;
			
			@Override
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				authorText.setBounds(width - margin, 20, margin, 20);
				snoText.setBounds(width - margin, 40, margin, 20);
				emailText.setBounds(width - margin, 60, margin, 20);
			}
		});
	}
	
	private void updateTime() {
		var sdf = new SimpleDateFormat("y-MM-dd / HH:mm");
		var format = sdf.format(new Date());
		updateText.setText("데이터 갱신: ".concat(format));
	}
	
}