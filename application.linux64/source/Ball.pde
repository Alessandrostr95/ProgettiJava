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
  
  void moove() {
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
  
  void friction() {
    if(height - (this.pos.y + r/2) < 1){
      PVector attr = this.vel.copy();
      attr.normalize();
      attr.mult(-1);
      
      float mu = 0.03;
      attr.mult(mu*this.mass);
      
      this.applyForce(attr);
    
    }
  }
  
  void drag(float c){
    PVector f = this.vel.copy();
    f.normalize();
    f.mult(-1);
    
    float modulo = this.vel.magSq();
    f.setMag(c * modulo);
    
    this.applyForce(f);

  }
  
  void applyForce(PVector F) {
    this.acc.add(PVector.div(F,this.mass));
  }
  
  void draw(){
    fill(0,100);
    noStroke();
    circle(this.pos.x,this.pos.y,this.r);
  }
  
  
  
}
