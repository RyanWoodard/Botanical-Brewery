

public class Seed {
  
  private int seedNum, cost, time;
  private String name;
  
  public Seed(String name, int seedNum,int cost, int time){
    this.seedNum = seedNum;
    this.cost = cost;
    this.time = time;
    this.name = name;
  }
  public int getTime(){
    return time;
  }
  public int getNum(){
    return seedNum;
  }
  public int getCost(){
    return cost;
  }
  public String getName(){
   return name;
  }
}
