import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SnakeGame extends PApplet {

Snake s = new Snake();
Wall w = new Wall();
int risoluzione = 20;
int righe;
int colonne;
int xFood;
int yFood;
int rate = 10;
boolean alive = true;


public void setup() {
  
  righe = height / risoluzione;
  colonne = width / risoluzione;
  s.setSpace(righe,colonne);
  xFood = floor(random(0,colonne));
  yFood = floor(random(0,righe));
}

public void draw(){
  frameRate(rate/2);
  background(0);
  
  w.show(risoluzione);
  if(alive)s.show(risoluzione);
  else if( !alive ) {
    s.c = color(255,0,0);
    s.show(risoluzione);
    noLoop();
  }
  
  for(int i = 0; i < righe; i++){
    for(int j = 0; j < colonne; j++){
      stroke(255,100);
      strokeWeight(0.5f);
      noFill();
      rect(j * risoluzione, i * risoluzione, risoluzione, risoluzione);
      /*if( j == s.x && i == s.y){
        fill(0,200,0);
        noStroke();
        rect(j * risoluzione + 3, i * risoluzione + 3, risoluzione - 6, risoluzione - 6);
      }*/
      fill(255,250,0);
      noStroke();
      rect(xFood * risoluzione + 3, yFood * risoluzione + 3, risoluzione - 6, risoluzione - 6);
      
      if(s.x == xFood && s.y == yFood) {
          xFood = floor(random(0,colonne));
          yFood = floor(random(0,righe));
          s.mangia();
          rate ++;
      }
      
    }
  }
  if(!s.moove() || w.intersect(s.x*risoluzione,s.y*risoluzione)) {
    alive = false;
  }

}

public void mousePressed(){
  w.add(mouseX - mouseX%risoluzione,mouseY - mouseY%risoluzione);
}


/*
void moove() {
  switch(dir){
    case 'w':  s.x = (s.x - 1 + righe) % righe; break;
    case 's':  s.x = (s.x + 1 + righe) % righe; break;
    case 'a':  s.y = (s.y - 1 + colonne) % colonne; break;
    case 'd':  s.y = (s.y + 1 + colonne) % colonne; break;
  }
}
*/


public void keyPressed(){
  if(keyCode == UP) {
    s.dir(0,-1);
  } else if(keyCode == DOWN) {
    s.dir(0,1);
  } else if(keyCode == RIGHT) {
    s.dir(1,0);
  } else if(keyCode == LEFT) {
    s.dir(-1,0);
  }
}

/*
void keyPressed(){
  switch(key){
    case 'w':  x = (x - 1 + righe) % righe; break;
    case 's':  x = (x + 1 + righe) % righe; break;
    case 'a':  y = (y - 1 + colonne) % colonne; break;
    case 'd':  y = (y + 1 + colonne) % colonne; break;
  }
}
*/
class Queue{
  private ArrayList l = new ArrayList();
  Queue(){ super(); }
  
  public boolean isEmpty(){
    if( this.l.size() == 0 ) return true;
    else return false;
  }
  
  public int size() { return this.l.size(); }
  
  public void enqueue(Object e) { this.l.add(e); }
  
  public void enqueue(Object...Elements) {
      for(Object e : Elements) this.l.add(e);
  }
  
  public Object dequeue() { return this.l.remove(0); }
  
  public Object first() { return this.l.get(0); }
  
  public void show(){ print(this.l); }
  
}
class Snake{
  int c = color(0,200,0);
  int x = 0;
  int y = 0;
  int dirX = 1;
  int dirY = 0;
  int righe = 1;
  int colonne = 1;
  Queue coda = new Queue();
  
  Snake(){
    super();
    this.coda.enqueue( new PVector(this.x,this.y) );
  }
  
  public void setSpace(int righe, int colonne){
    this.righe = righe;
    this.colonne = colonne;
  }
  
  public boolean moove(){
    this.coda.dequeue();
    
    this.x = (x + dirX + colonne) % colonne;
    this.y = (y + dirY + righe) % righe;
    
    for(int i = 1; i < this.coda.size(); i++) {
      PVector p = (PVector)this.coda.l.get(i);
      if(this.x == p.x && this.y == p.y) return false;
    }
    
    
    this.coda.enqueue( new PVector(this.x,this.y) );
    
    return true;
  }
  
  public void dir(int x, int y){
    if( this.dirX != -x ) this.dirX = x;
    if( this.dirY != -y ) this.dirY = y;
  }
  
  public void mangia(){
    this.coda.enqueue( new PVector(this.x,this.y) );
  }
  
  public void show(int risoluzione) {
    for(Object e : this.coda.l){
      PVector p = (PVector)e;
      fill(this.c);
      noStroke();
      rect(p.x * risoluzione + 3, p.y * risoluzione + 3, risoluzione - 6, risoluzione - 6);
    }
  }
  
}

class Wall{
  LinkedList<PVector> wall = new LinkedList<PVector>();
  
  Wall() { super(); }
  
  public void add(int x, int y) { this.wall.add( (new PVector(x,y)) ); }
  
  public void show(int risoluzione) { 
    for(PVector p  : this.wall) {
      fill(255);
      noStroke();
      rect(p.x + 3, p.y + 3, risoluzione - 6, risoluzione - 6);  
     }
  }
  
  public boolean intersect(int x, int y) {
    for(PVector p : this.wall) {
      if( p.x == x && p.y == y ) return true;
    }
    return false;
  }

}
  public void settings() {  size(1000,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SnakeGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
