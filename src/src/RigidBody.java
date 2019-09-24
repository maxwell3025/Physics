package src;

import java.util.ArrayList;
import java.util.List;

import physics.Beam;
import physics.Particle;
import vectors.Point2D;

public class RigidBody {
	List<Particle> particles = new ArrayList<Particle>();
	List<Beam> beams = new ArrayList<Beam>();
	List<Integer> indices = new ArrayList<Integer>();
	public RigidBody(){
		
	}
	public static RigidBody Rectangle(Point2D pos, Point2D size){
		RigidBody out = new RigidBody();
		out.particles.add(new Particle(pos.x,pos.y));
		out.particles.add(new Particle(pos.x+size.x,pos.y));
		out.particles.add(new Particle(pos.x,pos.y+size.y));
		out.particles.add(new Particle(pos.x+size.x,pos.y+size.y));
		out.particles.get(0).texCoords = new Point2D(0,0);
		out.particles.get(1).texCoords = new Point2D(1,0);
		out.particles.get(2).texCoords = new Point2D(0,1);
		out.particles.get(3).texCoords = new Point2D(1,1);
		out.beams.add(new Beam(0,1,size.x));
		out.beams.add(new Beam(2,3,size.x));
		out.beams.add(new Beam(0,2,size.y));
		out.beams.add(new Beam(1,3,size.y));
		out.beams.add(new Beam(1,2,size.dist()));
		out.indices.add(0);
		out.indices.add(1);
		out.indices.add(2);
		out.indices.add(1);
		out.indices.add(2);
		out.indices.add(3);
		return out;
	}
	public void update(float dt){
		for(int i = 0;i<16;i++){
		beamConstraints();
		}
		for(Particle particle:particles){
			particle.update(dt);
		}
	}
	private void beamConstraints() {
		for (int i = 0; i < beams.size(); i++) {
			Beam beam = beams.get(i);
			Point2D positionA = particles.get(beam.a).position;
			Point2D positionB = particles.get(beam.b).position;
			Point2D center = positionA.add(positionB).mul(0.5f);
			Point2D difference = positionA.sub(positionB).mul(0.5f);
			difference = difference.norm().mul(beam.dist / 2);
			particles.get(beam.a).position = center.add(difference);
			particles.get(beam.b).position = center.sub(difference);
		}
	}
	public void applyForce(Point2D force){
		for(Particle particle:particles){
			particle.applyForce(force);
		}
	}
	public void applyAngularForce(Point2D anchor, float force){
		for(Particle particle:particles){
			particle.applyForce(particle.position.sub(anchor).perp().mul(force));
		}
	}
}
