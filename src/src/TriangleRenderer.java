package src;

public class TriangleRenderer extends Bitmap {

	public TriangleRenderer(int width, int height) {
		super(width, height);
	}
	public void drawModel(RigidBody rigidbody, Bitmap texture){
		for(int i = 0;i<rigidbody.indices.size();i+=3){
			int indexA = rigidbody.indices.get(i);
			int indexB = rigidbody.indices.get(i+1);
			int indexC = rigidbody.indices.get(i+2);
			fillTriangle(rigidbody.particles.get(indexA).getVertex(), rigidbody.particles.get(indexB).getVertex(), rigidbody.particles.get(indexC).getVertex(), texture);
		}
	}
	public void fillTriangle(Vertex a, Vertex b, Vertex c, Bitmap texture){
		Vertex min = a;
		Vertex mid = b;
		Vertex max = c;
		if(min.pos.y>mid.pos.y){
			Vertex buf = mid;
			mid = min;
			min = buf;
		}
		if(mid.pos.y>max.pos.y){
			Vertex buf = mid;
			mid = max;
			max = buf;
		}
		if(min.pos.y>mid.pos.y){
			Vertex buf = mid;
			mid = min;
			min = buf;
		}
		scanTriangle(min,mid,max,mid.pos.sub(min.pos).cross(max.pos.sub(min.pos))<0,texture);
	}
	private void scanTriangle(Vertex min, Vertex mid, Vertex max, boolean handednes, Bitmap texture){
		InterpolationData interpolationdata = new InterpolationData(min,mid,max);
		Edge topToBottom = new Edge(interpolationdata,min,max,0);
		Edge topToMiddle = new Edge(interpolationdata,min,mid,0);
		Edge middleToBottom = new Edge(interpolationdata,mid,max,1);
		int start = topToMiddle.yStart;
		int end = topToMiddle.yEnd;
		Edge left = topToBottom;
		Edge right = topToMiddle;
		if(handednes){
			Edge buf = left;
			left = right;
			right = buf;
		} 
		for(int y = start;y<end;y++){  
			drawScanLine(left,right,y,texture);
			left.step();
			right.step();
		}
		start = middleToBottom.yStart;
		end = middleToBottom.yEnd;
		left = topToBottom;
		right = middleToBottom;
		if(handednes){
			Edge buf = left;
			left = right;
			right = buf;
		} 
		for(int y = start;y<end;y++){
			drawScanLine(left,right,y,texture);
			left.step();
			right.step();
		}
		
	}

	private void drawScanLine(Edge left, Edge right, int y, Bitmap texture) {
		
		int xStart = (int) Math.ceil(left.x);
		int xEnd = (int) Math.ceil(right.x);
		float xPreStep = xStart - left.x;
		float xDist = right.x - left.x;
		
		float texCoordXXStep = (right.TexCoordX - left.TexCoordX) / xDist;
		float texCoordYXStep = (right.TexCoordY - left.TexCoordY) / xDist;
		
		float texCoordX = left.TexCoordX + xPreStep * texCoordXXStep;
		float texCoordY = left.TexCoordY + xPreStep * texCoordYXStep;

		for (int x = xStart; x < xEnd; x++) {
			int color = texture.getRGB((int)(texCoordX*texture.getWidth()),(int)(texCoordY*texture.getHeight()));
			if(ColorUtils.alpha(color)>0){
			setRGB(x,y,color);
			}
			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
		}
		
	}

}
