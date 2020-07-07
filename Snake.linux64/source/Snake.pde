class Snake{
  color c = color(0,200,0);
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
  
  void setSpace(int righe, int colonne){
    this.righe = righe;
    this.colonne = colonne;
  }
  
  boolean moove(){
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
  
  void dir(int x, int y){
    if( this.dirX != -x ) this.dirX = x;
    if( this.dirY != -y ) this.dirY = y;
  }
  
  void mangia(){
    this.coda.enqueue( new PVector(this.x,this.y) );
  }
  
  void show(int risoluzione) {
    for(Object e : this.coda.l){
      PVector p = (PVector)e;
      fill(this.c);
      noStroke();
      rect(p.x * risoluzione + 3, p.y * risoluzione + 3, risoluzione - 6, risoluzione - 6);
    }
  }
  
}
