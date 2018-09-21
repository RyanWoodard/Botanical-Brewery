import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Shop {
 private Seed[] seeds;
 private int x, y, size, xB, yB, sizeB, y3, x3, x4, y4;
 private StartBrewery main;

 public Shop(StartBrewery main) {
  this.main = main;
  x = 360;
  y = 200;
  size = 80;
  xB = 360;
  yB = 340;
  sizeB = 140;
  y4 = 425;
  x4 = x + 40;
  this.seeds = main.getSeeds();
 }
 public void bonus(){
    main.getPlayer().addSeed(0);
       main.getPlayer().addSeed((int)(4 * Math.random())+1);
 }

 public void buy(int xM, int yM, Player player) {
  int seedNum;

  if (yM >= y && yM <= y + size && xM >= x && xM <= (x + size * 6)) {
   seedNum = (xM - x - 1) / size;
   if (player.getMoney() >= seeds[seedNum].getCost()) {
    player.addSeed(seedNum);
    player.changeMoney(-seeds[seedNum].getCost());
   } else {
  //  System.out.println("you broke");
   }

  }

  if ((yM >= yB && yM <= yB + sizeB && xM >= xB && xM <= (xB + (sizeB + 30) * 3))
    && (xM - x) % (sizeB + 30) < sizeB) {

   switch ((xM - x) / (sizeB + 30)) {
   case 0: {
    if (player.getMoney() >= Land.getCostSpace()) {
        if(Land.getSpaceLevel() !=4)
     bonus();
      player.changeMoney(-Land.getCostSpace());
     Land.increaseSpaceLevel();

    }
    break;
   }
   case 1: {
    if (player.getMoney() >= Land.getCostTime()) {
           if(Land.getTimeLevel() !=4)
     bonus();
 
      player.changeMoney(-Land.getCostTime());
     Land.increaseTimeLevel();

    }
    break;
   }
   case 2: {
    if (player.getMoney() >= Cauldron.getCost()) {
      
     player.changeMoney(-Cauldron.getCost());
        if(Cauldron.getLevel() != 3)
     bonus();
     Cauldron.incLevel();
 
    }
    break;
   }
   default: {
    System.out.println("how'd you get here?");
    break;
   }
   }

  }

 }

 public void sell(int xM, int yM) {

  if (yM >= y4 && yM <= y4 + size && xM >= x4 && xM <= (x4 + size * 5)) {

   int potionNum = (xM - x4 - 1) / size;

   if (main.getPlayer().getPotion(potionNum) > 0) {
    main.getPlayer().changeMoney((int) (main.getPotion(potionNum).getCost() * 4 / 3));

    main.getPlayer().removePotion(potionNum);

   }

  }
  if (yM >= y4 + size + 40 && yM <= y4 + size + size + 40 && xM >= x4 && xM <= (x4 + size * 5)) {
   int potionNum = (xM - x4 - 1) / size + 5;
   if (main.getPlayer().getPotion(potionNum) > 0) {
    main.getPlayer().changeMoney((int) (main.getPotion(potionNum).getCost() * 4 / 3));

    main.getPlayer().removePotion(potionNum);

   }

  }
  // fdszf
  if (yM >= y && yM <= y + size && xM >= x && xM <= (x + size * 6)) {
   int ingNum = (xM - x - 1) / size;
   if (main.getPlayer().getIng((xM - x - 1) / size) > 0) {
    main.getPlayer().removeIng(ingNum);
    main.getPlayer().changeMoney(((int)(seeds[ingNum].getCost()*9/8)));
   }

  }

 }

 public void paint(Graphics g) {
     
  if (main.getPlayer().getShop()) {

    g.setColor(Color.WHITE);
   g.fillRect(348, 98, size * 6 + 20 + 4, 454);


  g.drawImage(Images.getRock(), 350,100,size*6+20,450, null);

   for (int i = 0; i < 6; i++) {

    g.setColor(Color.GRAY);
    g.fillRect(x + size * i, y, size, size);

    g.setColor(Color.BLACK);
    g.drawRect(x + size * i, y, size, size);

    g.drawImage(Images.getSeed(i), x + size * i, y, size, size, null);
    g.setFont(new Font("Impact", Font.PLAIN, 16));
    g.setColor(Color.GREEN);
    if (main.getPlayer().getMoney() < seeds[i].getCost())
     g.setColor(Color.RED);
    g.drawString("$" + seeds[i].getCost() , x + 30 + i * size, y + size + 16);
    g.setColor(Color.WHITE);
    g.drawString(seeds[i].getName(), x + 10 + i * size, y - 16);
   }

   for (int i = 0; i < 3; i++) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(xB + (sizeB + 30) * i, yB, sizeB, sizeB);

    g.setColor(Color.BLACK);
    g.drawRect(xB + (sizeB + 30) * i, yB, sizeB, sizeB);

    g.setFont(new Font("Impact", Font.PLAIN, 20));
    g.setColor(Color.black);
    if (i == 0) {
     g.drawString("Farming Space", (xB + 5 + (sizeB + 30) * i), yB + 30);
     g.drawString("Upgrade:", (xB + 5 + (sizeB + 30) * i), yB + 50);
     g.drawString("Current Level: " + Land.getSpaceLevel(), (xB + 5 + (sizeB + 30) * i), yB + 90);
     if (Land.getSpaceLevel() > 3) {
      g.setColor(Color.red);
      g.drawString("MAX", (xB + 5 + (sizeB + 30) * i), yB + 110);
     } else {
      g.drawString("Cost: " + Land.getCostSpace(), (xB + 5 + (sizeB + 30) * i), yB + 110);
     }
    }
    if (i == 1) {
     g.drawString("Growing Speed", (xB + 5 + (sizeB + 30) * i), yB + 30);
     g.drawString("Upgrade:", (xB + 5 + (sizeB + 30) * i), yB + 50);
     g.drawString("Current Level: " + Land.getTimeLevel(), (xB + 5 + (sizeB + 30) * i), yB + 90);
     if (Land.getTimeLevel() > 3) {
      g.setColor(Color.red);
      g.drawString("MAX", (xB + 5 + (sizeB + 30) * i), yB + 110);
     } else {
      g.drawString("Cost: " + Land.getCostTime(), (xB + 5 + (sizeB + 30) * i), yB + 110);
     }
    }
    if (i == 2) {
     g.drawString("Cauldron", (xB + 5 + (sizeB + 30) * i), yB + 30);
     g.drawString("Upgrade:", (xB + 5 + (sizeB + 30) * i), yB + 50);
     g.drawString("Current Level: " + Cauldron.getLevel(), (xB + 5 + (sizeB + 30) * i), yB + 90);
     if (Cauldron.getLevel() > 2) {
      g.setColor(Color.red);
      g.drawString("MAX", (xB + 5 + (sizeB + 30) * i), yB + 110);
     } else {
      g.drawString("Cost: " + Cauldron.getCost(), (xB + 5 + (sizeB + 30) * i), yB + 110);
     }
    }

   }

   g.setFont(new Font("Impact", Font.PLAIN, 50));
   g.setColor(Color.ORANGE);
   g.drawString("SHOP", 548, 150);
  }
 }

 // fwejjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjeeeeeeeeeeeeeeeeeksld.zjfweapsjf[oaw;ehsfpawls>EGFUcupilsfgWOLEISBDKZFWILAES,GFOWUAEL,GSDFfsdiooooocgh

 public void paintSell(Graphics g) {
  if (main.getPlayer().getSell()) {

   g.setColor(Color.WHITE);
   g.fillRect(348, 98, size * 6 + 20 + 4, 575);

  g.drawImage(Images.getRock(), 350,100,size*6+20,571, null);

   // fasdhuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
   y3 = 425;
   x3 = x + 40;
   int count = 0;
   for (int i = 0; i < 10; i++) {
    if (i == 5) {
     y3 = y3 + size + 40;
     count = 0;
    }
    g.setColor(Color.GRAY);
    g.fillRect(x3 + size * count, y3, size, size);

    g.setColor(Color.BLACK);
    g.drawRect(x3 + size * count, y3, size, size);

    g.drawImage(Images.getPotion(i), x3 + size * count, y3, size, size, null);
    g.setFont(new Font("Impact", Font.PLAIN, 16));
    g.setColor(Color.GREEN);

    g.drawString("$" + (int) (main.getPotion(i).getCost() * 4 / 3), x3 + 2 + size * count,
      y3 + size + 16);
    g.setColor(Color.WHITE);
    g.drawString(main.getPotionName(i), x3 + 2 + size * count, y3 - 5);

    g.setColor(Color.GREEN);
    if (main.getPlayer().getPotion(i) == 0)
     g.setColor(Color.RED);

    g.setFont(new Font("Impact", Font.PLAIN, 14));

    g.drawString("" + main.getPlayer().getPotion(i), x3 + (count + 1) * size - 22, y3 + 14);

    count++;
   }
   // feqsdddddddddddddddddddddddddddddddddddddddddddddddddddddd
   for (int i = 0; i < 6; i++) {

    g.setColor(Color.GRAY);
    g.fillRect(x + size * i, y, size, size);

    g.setColor(Color.BLACK);
    g.drawRect(x + size * i, y, size, size);

    g.drawImage(Images.getIngredient(i), x + 3 + size * i, y, size, size, null);
    g.setFont(new Font("Impact", Font.PLAIN, 16));
    g.setColor(Color.GREEN);

    g.drawString("$" + ((int)(seeds[i].getCost()*9/8)), x + 30 + i * size, y + size + 16);
    g.setColor(Color.WHITE);
    g.drawString(seeds[i].getName(), x + 10 + i * size, y - 16);
    // daskfdsdf

    g.setColor(Color.GREEN);
    if (main.getPlayer().getIng(i) == 0)
     g.setColor(Color.RED);

    g.setFont(new Font("Impact", Font.PLAIN, 14));

    g.drawString("" + main.getPlayer().getIng(i), x + (i) * size + 5, y + 14);

   }
   
   g.setFont(new Font("Impact", Font.PLAIN, 50));
   g.setColor(Color.ORANGE);
   g.drawString("Market", 540, 150);

   g.setFont(new Font("Impact", Font.PLAIN, 20));
   g.setColor(Color.white);
   if(main.getCustomer().getRequested()){
    g.drawString("Press P to sell a potion of "+main.getPotionName(main.getCustomer().getRequest())+" to "+main.getCustomer().getName()+" for $"+main.getPotion(main.getCustomer().getRequest()).getCost()*2, x+5, y+160);
   }

  }

 }
}
