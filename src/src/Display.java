package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vectors.Point2D;

public class Display extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 996915286868931871L;

	int width;
	int height;
	private final JFrame frame = new JFrame();
	private BufferedImage frameBuffer;
	TriangleRenderer bitmap;
	boolean isPainted = true;
	long nanoTime = System.nanoTime();
	long dt = 0;
	Bitmap texture;
	RigidBody rigidbody;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		frameBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bitmap = new TriangleRenderer(width, height);
		setPreferredSize(new Dimension(width, height));
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
	}

	public void init() {
		rigidbody = RigidBody.Rectangle(new Point2D(width / 2-50-200, height / 2-50), new Point2D(100, 100));
		rigidbody.applyAngularForce(new Point2D(width / 2-200, height / 2), 100);
		rigidbody.applyForce(new Point2D(1000000,0));
		try {
			BufferedImage tex = ImageIO.read(new File(System.getProperty("user.dir") + "\\res\\star.png"));
			texture = new Bitmap(tex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {
		physicsUpdate();
		bitmap.drawModel(rigidbody, texture);
		bitmap.copyToBufferedImage(frameBuffer);
		bitmap.fill(0xff000000);
		isPainted = false;
		repaint();
		while (!isPainted) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}

	public void physicsUpdate() {
		dt = System.nanoTime() - nanoTime;
		nanoTime = System.nanoTime();
		rigidbody.update((float) (dt / 1000000000.0));
	}

	public void paint(Graphics g) {
		g.drawImage(frameBuffer, 0, 0, null);
		isPainted = true;
	}
}
