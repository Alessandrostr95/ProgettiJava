import java.util.*;
class Wall{
  LinkedList<PVector> wall = new LinkedList<PVector>();
  
  Wall() { super(); }
  
  void add(int x, int y) { this.wall.add( (new PVector(x,y)) ); }
  
  void show(int risoluzione) { 
    for(PVector p  : this.wall) {
      fill(255);
      noStroke();
      rect(p.x + 3, p.y + 3, risoluzione - 6, risoluzione - 6);  
     }
  }
  
  boolean intersect(int x, int y) {
    for(PVector p : this.wall) {
      if( p.x == x && p.y == y ) return true;
    }
    return false;
  }

}
