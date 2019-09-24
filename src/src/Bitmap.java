package src;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Bitmap {
	private int width;
	private int height;
	private int[] raster;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		raster = new int[width * height];
	}

	public Bitmap(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		raster = new int[width * height];
		image.getRGB(0, 0, width, height, raster, 0, width);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		raster = new int[width * height];
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		raster = new int[width * height];
	}

	public void setRGB(int x, int y, int color) {
		raster[x + y * width] = color;
	}

	public int getRGB(int x, int y) {
		try{
			return raster[x + y * width];
		}catch(Exception e){
			return 0;
		}
	}

	public void fill(int color) {
		Arrays.fill(raster, color);
	}

	public void copyToBufferedImage(BufferedImage image) {
		image.setRGB(0, 0, width, height, raster, 0, width);
	}
	static class ColorUtils{
		public static float blue(int in){
			return (in&0xff)/256.0f;
		}
		public static float green(int in){
			return ((in>>8)&0xff)/256.0f;
		}
		public static float red(int in){
			return ((in>>16)&0xff)/256.0f;
		}
		public static float alpha(int in){
			return ((in>>24)&0xff)/256.0f;
		}
		public static int compose(float a, float r, float g, float b){
			return ((int)(a*256)<<24)+((int)(r*256)<<16)+((int)(g*256)<<8)+((int)(b*256));
		}
		public static int over(int a,int b){
			//float alpha = alpha(a)+alpha(b)*(1-alpha(a));
			return 0;
		}
	}
}
