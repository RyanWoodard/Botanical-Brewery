public class Potion {
  
  private String name;
  private int costToMake;
  private int[] required;
  public Potion(String name, int costToMake,int[] required){
    this.name = name;
    this.costToMake = costToMake;
    this.required = required;
  }
  
  public int[] getReq(){
    return required;
  }
  public String getName(){
    return name;
  }
  public int getCost(){
  return costToMake;
  }
}
