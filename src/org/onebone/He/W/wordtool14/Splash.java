package org.onebone.He.W.wordtool14;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class Splash {
	private static JWindow window;
	public static void splash(boolean isFX, String[] args) throws IOException{
		window = new JWindow();
		JLabel label = new JLabel(new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("resources/splash.png"))));
		window.getContentPane().add(label, SwingConstants.CENTER);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension lab = label.getPreferredSize();
		window.setBounds((int)(screen.getWidth() / 2 - lab.width/2), (int)(screen.getHeight() / 2 - lab.height/2), lab.width, lab.height);
		window.setVisible(true);
		try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		if(isFX){
			MainActivity.lc(args);
		}else{
			new SwingMainActivity();
		}
		window.setVisible(false);
	}
	public static void dispose(){
		window.dispose();
	}
}
