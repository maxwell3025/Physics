package src;

public class Edge {
	int yStart;
	int yEnd;
	float x;
	float xStep;
	float TexCoordX;
	float TexCoordXStep;
	float TexCoordY;
	float TexCoordYStep;
	
	public Edge(InterpolationData interpolation, Vertex min, Vertex max, int minVertIndex) {
		yStart = (int) Math.ceil(min.pos.y);
		yEnd = (int) Math.ceil(max.pos.y);
		float yDist = max.pos.y - min.pos.y;
		float xDist = max.pos.x - min.pos.x;
		xStep = xDist / yDist;
		float yPreStep = yStart - min.pos.y;
		x = min.pos.x + xStep * yPreStep;
		TexCoordXStep = interpolation.texCoordXYStep + interpolation.texCoordXXStep * xStep;
		TexCoordYStep = interpolation.texCoordYYStep + interpolation.texCoordYXStep * xStep;
		
		TexCoordX = interpolation.texCoordX[minVertIndex] + TexCoordXStep * yPreStep;
		TexCoordY = interpolation.texCoordY[minVertIndex] + TexCoordYStep * yPreStep;
	}

	public void step() {
		x += xStep;
		TexCoordX += TexCoordXStep;
		TexCoordY += TexCoordYStep;
	}
}
