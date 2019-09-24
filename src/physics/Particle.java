package physics;

import src.Vertex;
import vectors.Point2D;

public class Particle {
public Point2D position;
public Point2D oldPosition;
public Point2D texCoords = new Point2D(0.5f,0.5f);
public Point2D force = new Point2D(0,0);
public float oldDT=1;
	public Particle(float x, float y) {
		position = new Point2D(x,y);
		oldPosition = new Point2D(x,y);
	}
	public void applyForce(Point2D force){
		this.force = this.force.add(force);
	}
	public void update(float dt){
		Point2D vel = position.sub(oldPosition);
		oldPosition = position;
		position = position.add(vel.mul(dt/oldDT)).add(force.mul(dt*dt));
		oldDT = dt;
		force = new Point2D(0,0);
	}
	public Point2D getVelocity(){
		return position.sub(oldPosition);
	}
	public Vertex getVertex(){
		return new Vertex(position, texCoords);
	}
}
