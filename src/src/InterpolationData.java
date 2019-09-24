package src;

public class InterpolationData {
	float[] texCoordX = new float[3];
	float[] texCoordY = new float[3];
	float texCoordXXStep;
	float texCoordXYStep;
	float texCoordYXStep;
	float texCoordYYStep;
	public InterpolationData(Vertex min, Vertex mid, Vertex max) {
		float oneOverdX = 1.0f / (((mid.pos.x - max.pos.x) * (min.pos.y - max.pos.y))
				- ((min.pos.x - max.pos.x) * (mid.pos.y - max.pos.y)));
		float oneOverdY = -oneOverdX;
		texCoordX[0]=min.texCoords.x;
		texCoordX[1]=mid.texCoords.x;
		texCoordX[2]=max.texCoords.x;
		texCoordY[0]=min.texCoords.y;
		texCoordY[1]=mid.texCoords.y;
		texCoordY[2]=max.texCoords.y;
		texCoordXXStep = CalcXStep(texCoordX, min,mid,max,oneOverdX);
		texCoordXYStep = CalcYStep(texCoordX, min,mid,max,oneOverdY);
		texCoordYXStep = CalcXStep(texCoordY, min,mid,max,oneOverdX);
		texCoordYYStep = CalcYStep(texCoordY, min,mid,max,oneOverdY);
	}
	private float CalcXStep(float[] values, Vertex min, Vertex mid, Vertex max, float oneOverdX) {
		return (((values[1] - values[2]) * (min.pos.y - max.pos.y))
				- ((values[0] - values[2]) * (mid.pos.y - max.pos.y))) * oneOverdX;
	}

	private float CalcYStep(float[] values, Vertex min, Vertex mid, Vertex max, float oneOverdY) {
		return (((values[1] - values[2]) * (min.pos.x - max.pos.x))
				- ((values[0] - values[2]) * (mid.pos.x - max.pos.x))) * oneOverdY;
	}
}
