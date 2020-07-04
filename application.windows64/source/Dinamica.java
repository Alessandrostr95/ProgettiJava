import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Dinamica extends PApplet {

ArrayList<Ball> punti = new ArrayList<Ball>();
PVector g = new PVector(0,98.1f);

boolean lock = false;
float x1,y1,x2,y2;

public void setup(){
  
  
  for(int i = 0; i < 4; i++) {
    Ball p = new Ball(random(width),300,random(60,400));
    punti.add(p);
  }
  
}

public void draw(){
  background(255);
  for(Ball p : punti) {
    p.moove();
    p.draw();
    p.drag(0.5f);
    p.applyForce(g);
    p.friction();
  }
  
  if(lock){
    stroke(255,0,0);
    strokeWeight(3);
    line(x1,y1,x2,y2);
    
    //in teoria un triangolo arientato
    pushMatrix();
    translate(x2, y2);
    float a = atan2(x1-x2, y2-y1);
    rotate(a);
    noStroke();
    fill(255,0,0);
    triangle(0,0,-10,-20,10,-20);
    popMatrix();
    
  }

}

public void mousePressed() {
  x1 = mouseX;
  y1 = mouseY;
}
public void mouseDragged(){
  x2 = mouseX;
  y2 = mouseY;
  lock = true;
}
public void mouseReleased() {
  PVector r1 = new PVector(x1,y1);
  PVector r2 = new PVector(x2,y2);
  for(Ball p : punti) {
    p.applyForce(PVector.sub(r2,r1).mult(10));
  }

  x1 = 0;
  y1 = 0;
  x2 = 0;
  y2 = 0;
  lock = false;
}

public void mouseClicked(){
  punti.add(new Ball(mouseX,mouseY,random(60,400)));
}
class Ball {
  PVector pos = new PVector(0, 0);
  PVector vel = new PVector(0, 0);
  PVector acc = new PVector(0, 0);
  float mass;
  float r;

  
  Ball(float x, float y){
    pos = new PVector(x, y);
    mass = 120;
    r = this.mass/5;
  }
  
  Ball(float x, float y, float mass){
    pos = new PVector(x, y);
    this.mass = mass;
    this.r = this.mass/5;
  }
  
  Ball(){
    pos = new PVector(width/2,height/2);
    vel = PVector.random2D().mult(random(1,10));
    mass = 120;
    r = this.mass/5;
  }
  
  public void moove() {
    this.vel.add(this.acc);
    this.pos.add(this.vel);
    this.acc.set(0, 0);
    
    if( this.pos.y + r/2 > height ) {
      this.pos.y = height-r/2;
      this.vel.y *= -1;
    }
    if( this.pos.y - r/2 < 0 ) {
      this.pos.y = r/2;
      this.vel.y *= -1;
    }
    if( this.pos.x - r/2 < 0 ) {
      this.pos.x = r/2;
      this.vel.x *= -1;
    }
    if( this.pos.x + r/2 > width ) {
      this.pos.x = width-r/2;
      this.vel.x *= -1;
    } 
  }
  
  public void friction() {
    if(height - (this.pos.y + r/2) < 1){
      PVector attr = this.vel.copy();
      attr.normalize();
      attr.mult(-1);
      
      float mu = 0.03f;
      attr.mult(mu*this.mass);
      
      this.applyForce(attr);
    
    }
  }
  
  public void drag(float c){
    PVector f = this.vel.copy();
    f.normalize();
    f.mult(-1);
    
    float modulo = this.vel.magSq();
    f.setMag(c * modulo);
    
    this.applyForce(f);

  }
  
  public void applyForce(PVector F) {
    this.acc.add(PVector.div(F,this.mass));
  }
  
  public void draw(){
    fill(0,100);
    noStroke();
    circle(this.pos.x,this.pos.y,this.r);
  }
  
  
  
}
  public void settings() {  size(800,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Dinamica" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
