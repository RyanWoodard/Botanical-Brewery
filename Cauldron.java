
import java.awt.Graphics;
import java.awt.* ; 

public class Cauldron{
 private int potion = -1;
 private int ing1 = -1;
 private int ing2 = -1;
 private int ing3 = -1;
 private int x,y,size;
 private static int level = 1;
 private static int cost = 100;//temp
 private boolean brewing,badBrew,close,win;
 private Potion[] potions;
 private StartBrewery main;

 public Cauldron(Potion[] potions,StartBrewery main){
  this.main = main;
  this.potions = potions;
  x = 360;
  y = 490;
  size = 80;
  close = false;
  badBrew = false;
  win = false;
 }


 public void brew(int ing1,int ing2){
  boolean test1 = false;
  boolean test2 = false;
  if(ing1 > ing2){
   int temp = ing1;
   ing1 = ing2;
   ing2 = temp;


  }
  if(!brewing){
   for(int i = 0; i<potions.length;i++){
    if(potions[i].getReq().length ==2){
     if(potions[i].getReq()[0] ==ing1)
      test1 = true;
     if(potions[i].getReq()[1] ==ing2)
      test2 = true;
    }
    if(test1 && test2){
     main.getPlayer().addPotion(i);
     potion = i;
     break;
    }
    test1 = false;
    test2 = false;
   }


  }

 }
 public void brew(int xM, int yM,int nothing){
  if(yM >= y-50 && yM <= y-10  && xM >= 520 && xM <= (520 + 160)){

   potion = -1;
   if(ing3 == -1 && ing2 != -1 && ing3 == -1)
    brew(ing1,ing2);
   if(ing1 != -1 && ing2 != -1 && ing3 != -1  && level >=2){
    if(ing3 < ing2){
     int temp = ing2;
     ing2 = ing3;
     ing3 = temp;    
    }
    if(ing1 > ing2){
     int temp = ing1;
     ing1 = ing2;
     ing2 = temp;    
    }
    if(ing3 < ing2){
     int temp = ing2;
     ing2 = ing3;
     ing3 = temp;    
    }


    if(ing1 == 0 || level >= 3){

     boolean test1 = false;
     boolean test2 = false;
     boolean test3 = false;
     if(!brewing){
      for(int i = 0; i<potions.length;i++){

       if(potions[i].getReq().length ==3){

        if(potions[i].getReq()[0] ==ing1)
         test1 = true;

        if(potions[i].getReq()[1] ==ing2)
         test2 = true;

        if(potions[i].getReq()[2] ==ing3)
         test3 = true;
       }
       if(test1 && test2 && test3){
        main.getPlayer().addPotion(i);
        potion = i;
        if(potion == 9 && !win){
          win = true;
         main.setWin();
        }
        break;
       }

       test1 = false;
       test2 = false;
       test3 = false;
      }


     }
    }
   }

   if(potion ==-1){
    badBrew = true;
    if(ing1  != -1)
     main.getPlayer().addIng(ing1);
    if(ing2  != -1)
     main.getPlayer().addIng(ing2);
    if(ing3  != -1)
     main.getPlayer().addIng(ing3);
   }
   else
    badBrew = false;
   ing1 = -1; ing2 = -1; ing3 = -1;
  }


 }
 public void setClose(boolean a){
  close = a;
 }
 public void paint2(Graphics g){
  //paint circle around cauldron
  if(close){
   g.setColor(Color.ORANGE);  
   g.drawOval(499,333,199,199);

  }
 }
 public void paint(Graphics g){
  //WHO NEEEDS COMMENTS?

  if(main.getPlayer().getBrew()){
   g.setColor(Color.WHITE);  
   g.fillRect(348,98,size*6+20+4,504);

 g.drawImage(Images.getRock(), 350,100,size*6+20,500, null);
  // g.fillRect(350,100,size*6+20,500);

   for(int i =  0; i < 6; i++){

    g.setColor(Color.GRAY);  
    g.fillRect(x + size*i,y,size,size+20);

    g.setColor(Color.BLACK);
    g.drawRect(x + size*i,y,size,size+20);
    g.drawRect(x + size*i,y,size,size+2);

    g.setColor(Color.GREEN);
    if(main.getPlayer().getIng(i) ==0)
     g.setColor(Color.RED);


    g.setFont(new Font("Impact", Font.PLAIN,14)); 

    g.drawImage(Images.getIngredient(i),x + size*i+3,y,size,size,null);  
    g.drawString(""+   main.getPlayer().getIng(i),x+(i+1)*size-22,y+14);
    g.setColor(Color.black);
    g.drawString(main.getSeedName(i),x+(i)*size+10,y+size + 17);
   }

   int x2 = x+40;
   int y2 = y-80;
   g.setColor(Color.LIGHT_GRAY);
   if(level ==1)
    g.setColor(Color.RED);
   g.fillRect(x2,y2-size,size,size);
   g.setColor(Color.LIGHT_GRAY);
   g.fillRect(x2,y2-size*2,size,size);
   g.fillRect(x2,y2-size*3,size,size);
   g.fillRect(600,195,size*2,size*2 +10);
   g.setColor(Color.BLACK);
   g.drawRect(x2,y2-size,size,size);
   g.drawRect(x2,y2-size*2,size,size);
   g.drawRect(x2,y2-size*3,size,size); 
   if(potion != -1)
    g.setColor(Color.GREEN);
   if(badBrew)
    g.setColor(Color.RED);
   g.drawRect(600,195,size*2,size*2 +10);   
   g.drawRect(601,196,size*2-2,size*2-2 +10);   
   if(ing1 != -1)
    g.drawImage(Images.getIngredient(ing1),x2+2,y2-size*3,size,size,null);  
   if(ing2 != -1)
    g.drawImage(Images.getIngredient(ing2),x2+2,y2-size*2,size,size,null);  
   if(ing3 != -1)
    g.drawImage(Images.getIngredient(ing3),x2+2,y2-size,size,size,null);  


   g.setColor(Color.ORANGE);  
   g.fillRect(520,y-50,160,40);
   g.setColor(Color.BLACK); 
   g.drawRect(520,y-50,160,40);
   g.setFont(new Font("Impact", Font.PLAIN,24)); 
   g.drawString("BREW!",570,y-20);

   if(potion != -1){
    g.setColor(Color.white);
    g.drawImage(Images.getPotion(potion),600,200,size*2,size*2,null);  
    g.drawString(main.getPotionName(potion),600,400);
   }
   g.setColor(Color.RED);
   g.fillRect(400,y-50,80,35);
   g.setColor(Color.BLACK); 
   g.drawRect(400,y-50,80,35);
   g.drawString("CLEAR",410,y-22);
  }
 }
 //M = mouse
 public void addIngs(int xM, int yM){
  if(yM >= y && yM <= y + size +20 && xM >= x && xM <= (x + size*6)){
   if(!(main.getPlayer().getIng((xM - x-1)/size)==0))       
    for(int i = 0;i<1;i++){

     if(ing1 == -1){
      ing1 = (xM - x-1)/size;
      main.getPlayer().removeIng(ing1);

      break;
     }
     if(ing2 == -1){
      ing2 = (xM - x-1)/size;
      main.getPlayer().removeIng(ing2);
      break;     
     }

     if(ing3 == -1 && level != 1){
      ing3 = (xM - x-1)/size;     
      main.getPlayer().removeIng(ing3);
      break;
     }
    }
  }


 }

 public void clear(int xM, int yM){

  if(yM >= y-50 && yM <= y-15  && xM >= 400 && xM <= (480)){
   reset();
  }
 }
 public void reset(){
  if(ing1  != -1)
   main.getPlayer().addIng(ing1);
  if(ing2  != -1)
   main.getPlayer().addIng(ing2);
  if(ing3  != -1)
   main.getPlayer().addIng(ing3);
  ing1 =-1;
  ing2 =-1;
  badBrew = false;
  ing3 =-1;
  potion = -1;
 }

 public static int getLevel(){
  return level;  
 }

 public static void incLevel(){

  if(level < 3){
   level++;
   cost*=2;
  }
  if(level ==3){

   cost = 0;}

 }

 public static int getCost(){
  return cost;
 }
}