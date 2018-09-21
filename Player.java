import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
  private BufferedImage img;
  private StartBrewery sb;
  private BufferedImage playerSprite;
  private int x, y, xa, ya, size, frame, facing;
  private int[] seeds = new int[6];
  private int money = 0;
  private int[] ingredients = new int[6];
  private int[] potions = new int[10];
  private boolean brew, shop, inventory, sell, recipeList, help;
  
  public Player(StartBrewery sb) throws IOException {
    money = 50;
    brew = false;
    shop = false;
    help = true;
    recipeList = false;
    inventory = false;
    this.sb = sb;
    size = 80;
    x = 268;
    y = 193;
    xa = 0;
    ya = 0;
    frame = 0;
    playerSprite = ImageIO.read(new File("pictures/Playersprites.png"));
    img = playerSprite.getSubimage(0, 0, 32, 32);
  }
  
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public void addSeed(int seed) {
    seeds[seed]++;
    
  }
  
  public void removeSeed(int seed) {
    seeds[seed]--;
    
  }
  
  public int getSeeds(int seed) {
    return seeds[seed];
  }
  
  public boolean getBrew() {
    return brew;
    
  }
  
  public boolean getShop() {
    return shop;
  }
  
  public boolean getInventory() {
    return inventory;
    
  }
  
  public boolean getHelp() {
    return help;
  }
  
  public void addIng(int ing) {
    ingredients[ing]++;
  }
  
  public void recipeList() {
    recipeList = !recipeList;
    frame = 0;
  }
  
  public boolean getRecipe() {
    return recipeList;
    
  }
  
  public void inventory() {
    inventory = !inventory;
    frame = 0;
  }
  
  public void help() {
    help = !help;
    frame = 0;
  }
  
  public int getIng(int ing) {
    return ingredients[ing];
  }
  
  public void removeIng(int ing) {
    ingredients[ing]--;
    
  }
  
  public int getPotion(int potion) {
    return potions[potion];
  }
  
  public void removePotion(int potion) {
    potions[potion]--;
    
  }
  
  public void addPotion(int seedNum) {
    potions[seedNum]++;
    
  }
  
  public int getMoney() {
    return money;
  }
  
  public void brew() {
    if (!brew) {
      int dx = 600 - x - 64;
      int dy = 450 - 16 - y - 64;
      if (Math.sqrt(dx * dx + dy * dy) <= 150 && sb.getScenery() == 0) {
        frame = 0;
        brew = true;
      }
    } else {
      brew = false;
      sb.getCauldron().reset();
    }
  }
  
  public void shop() {
    if (!shop) {
      if (x < 35 && sb.getScenery() == 0) {
        frame = 0;
        shop = true;
      }
    } else {
      shop = false;
      sb.getCauldron().reset();
    }
    
  }
  
  public void changeMoney(int change) {
    money += change;
  }
  
  // check if close to cauldron
  public void brewCheck() {
    int dx = 600 - x - 64;
    int dy = 450 - 16 - y - 64;
    if (Math.sqrt(dx * dx + dy * dy) <= 150 && sb.getScenery() == 0) {
      sb.getCauldron().setClose(true);
    } else
      sb.getCauldron().setClose(false);
    
  }
  
  public void sellCheck() {
    if (!sell) {
      if (sb.getScenery() == 0 && x > 1000){
        sell = true;
        frame = 0;
      }
    } else
      sell = false;
  }
  
  public boolean getSell() {
    return sell;
  }
  
  public void paint(Graphics2D g) {
    
    g.drawImage(img, x, y, 128, 128, null);
    
  }
  
  public void paintINV(Graphics2D g) {
    
    if (help) {
      int left = 100;
      int top = 150;
      g.setColor(Color.WHITE);
      g.fillRect(left - 2, top - 2, 1000, 654);
      g.setColor(Color.gray);
      g.drawImage(Images.getRock(), left,top,996,650, null);
      
      g.setColor(Color.ORANGE);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString("You are a brew master.", left + 40, top + 40);
      g.drawString("You have recently aquired your own shop and garden.",
                   left + 40, top + 70);
      g.drawString(
                   "Your mission is to create the legendary transcendence potion.",
                   left + 40, top + 100);
      g.setFont(new Font("Impact", Font.PLAIN, 20));
      g.setColor(Color.WHITE);
      g.drawString("There are 6 ingredients, 6 seeds and 10 potions.",
                   left + 40, top + 150);
      g.drawString(
                   "Press I to open/close your inventory.",
                   left + 40, top + 170);
      g.drawString("You can buy seeds and upgrades at the shop.",
                   left + 40, top + 210);
      g.drawImage(Images.getSeed(1), left + 790, top + 130, 80, 80, null);
      g.drawString(
                   "Press space when near the left wall to open/close the shop.",
                   left + 40, top + 230);
      g.drawString(
                   "You can plant seeds in the garden. You can get there by walking upwards",
                   left + 40, top + 270);
      g.drawString(
                   "Select the seed you want to plant in the top left by clicking on it.",
                   left + 40, top + 290);
      g.drawString(
                   "Walk on top of some soil then press space to plant your selected seed.",
                   left + 40, top + 310);
      g.drawImage(Images.getLand(), left + 730, top + 265, 80, 80, null);
      g.drawImage(Images.getPlanted(1), left + 730, top + 265, 80, 80,
                  null);
      g.drawImage(Images.getLand(), left + 860, top + 265, 80, 80, null);
      g.drawImage(Images.getGrown(1), left + 860, top + 265, 80, 80, null);
      g.drawString(
                   "When the seed has fully grown, it can be harvested the same way it was planted.",
                   left + 40, top + 350);
      g.drawString(
                   "By harvesting a seed, you will acquire the corresponding ingredient.",
                   left + 40, top + 370);
      g.drawString(
                   "You can brew potions in the cauldron using those ingredients.",
                   left + 40, top + 410);
      g.drawString(
                   "When close to the cauldron, press space to open/close the brew menu.",
                   left + 40, top + 430);
      g.drawString(
                   "Press r to toggle your view of your potion recipes; unlock more recipes by upgrading your cauldron.",
                   left + 40, top + 450);
      g.drawImage(Images.getPotion(0), left + 850, top + 400, 80, 80,
                  null);
      g.drawString("You can sell your potions for money on the right.",
                   left + 40, top + 490);
      g.drawString(
                   "Press space when near the right wall to open the sell menu",
                   left + 40, top + 510);
      g.drawString(
                   "Use the arrow keys or WASD to move.",
                   left + 40, top + 550);
      g.drawString(
                   "Moving will close any open menu except for the help menu.",
                   left + 40, top + 570);
      g.drawString(
                   "Upgrading your shop will grant you two bonus seeds. 1 stabilis, and 1 other random seed.",
                   left + 40, top + 590);
      g.drawString("Press H to toggle the help menu.", left + 700,
                   top + 630);
    }
    
    if (inventory) {
      int left = 270;
      int top = 100;
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(left - 2, top - 2, size * 8 + 4, size * 8 + 4);
      //g.setColor(Color.DARK_GRAY);
      // g.fillRect(left, top, size * 8, size * 8);
      g.drawImage(Images.getRock(), left,top,size*8,size*8, null);
      g.setColor(Color.GRAY);
      
      g.setFont(new Font("Impact", Font.PLAIN, 12));
      
      for (int j = 0; j < 6; j++) {
        g.setColor(Color.GRAY);
        g.fillRect(left + (j + 1) * size, top + size, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(left + (j + 1) * size, top + size, size, size);
        g.drawImage(Images.getSeed(j), left + 10 + (j + 1) * size, top
                      + 5 + size, (size - 20), (size - 20), null);
        g.drawString(sb.getSeedName(j), left + 10 + (j + 1) * size, top
                       + size * 2 - 5);
        if (getSeeds(j) < 1)
          g.setColor(Color.RED);
        else
          g.setColor(Color.GREEN);
        g.drawString("" + getSeeds(j), left + (j + 2) * size - 20, top
                       + size + 15);
      }
      
      for (int j = 0; j < 6; j++) {
        g.setColor(Color.GRAY);
        g.fillRect(left + (j + 1) * size, top + size * 3, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(left + (j + 1) * size, top + size * 3, size, size);
        g.drawImage(Images.getIngredient(j),
                    left + 10 + (j + 1) * size, top + 5 + size * 3,
                    (size - 20), (size - 20), null);
        g.drawString(sb.getSeedName(j), left + 10 + (j + 1) * size, top
                       + size * 4 - 5);
        if (getIng(j) < 1)
          g.setColor(Color.RED);
        else
          g.setColor(Color.GREEN);
        g.drawString("" + getIng(j), left + (j + 2) * size - 20, top
                       + size * 3 + 15);
      }
      
      for (int i = 0; i < 10; i++) {
        g.setColor(Color.GRAY);
        g.fillRect(left + (i + 1 - ((int) (i / 6) * 6)) * size, top
                     + ((int) (i / 6) + 5) * size, size, size);
        g.setColor(Color.BLACK);
        g.drawRect(left + (i + 1 - ((int) (i / 6) * 6)) * size, top
                     + ((int) (i / 6) + 5) * size, size, size);
        g.drawImage(Images.getPotion(i), left + 10
                      + (i + 1 - ((int) (i / 6) * 6)) * size, top + 5
                      + ((int) (i / 6) + 5) * size, (size - 20), (size - 20),
                    null);
        g.drawString(sb.getPotionName(i), left + 10
                       + (i + 1 - ((int) (i / 6) * 6)) * size, top - 5 + size
                       * ((int) (i / 6) + 6));
        if (getPotion(i) < 1)
          g.setColor(Color.RED);
        else
          g.setColor(Color.GREEN);
        g.drawString("" + getPotion(i), left - 20
                       + (i + 2 - ((int) (i / 6) * 6)) * size, top + 10 + size
                       * ((int) (i / 6) + 5));
      }
    }
    
    if (recipeList) {
      int left = 15;
      int top = 29;
      int size = 80;
      g.setColor(Color.WHITE);
      g.fillRect(left - 2, top - 2, size * 4 + 14,
                 size * (Cauldron.getLevel() * 3 + 1) + 14);
      //g.setColor(Color.DARK_GRAY);
      g.drawImage(Images.getRock(), left,top,size*4+10,size* (Cauldron.getLevel() * 3 + 1) + 10, null);
      // g.fillRect(left, top, size * 4 + 10, size
      // * (Cauldron.getLevel() * 3 + 1) + 10);
      g.setColor(Color.GRAY);
      
      g.setFont(new Font("Impact", Font.PLAIN, 12));
      
      for (int i = 0; i <= Cauldron.getLevel() * 3; i++) {
        for (int j = 0; j <= sb.getPotion(i).getReq().length; j++) {
          g.setColor(Color.gray);
          g.fillRect(left + j * size + 5, top + i * size + 5, size,
                     size);
          g.setColor(Color.black);
          g.drawRect(left + j * size + 5, top + i * size + 5, size,
                     size);
          if (j == 0) {
            g.drawImage(Images.getPotion(i), left + 15, top + i
                          * size + 7, size - 10, size - 10, null);
            g.drawString(sb.getPotion(i).getName(), left + 10, top
                           + (i + 1) * size);
          } else {
            g.drawImage(Images.getIngredient(sb.getPotion(i)
                                               .getReq()[j - 1]), left + j * size + 12, top
                          + i * size + 5, size - 10, size - 10, null);
          }
        }
      }
    }
  }
  
  public void move() {
    if (!brew && !shop && !inventory && !help && !recipeList && !sell) {
      if (frame > 78 || xa == 0 && ya == 0) {
        frame = 0;
      } else {
        frame++;
      }
      
      x += xa;
      y += ya;
      
      if (ya > 0 && xa == 0) {
        facing = 0;
      } else if (ya < 0 && xa == 0) {
        facing = 1;
      } else if (ya == 0 && xa > 0) {
        facing = 2;
      } else if (ya == 0 && xa < 0) {
        facing = 3;
      } else if (ya > 0 && xa < 0) {
        facing = 4;
      } else if (ya < 0 && xa > 0) {
        facing = 5;
      } else if (ya > 0 && xa > 0) {
        facing = 6;
      } else if (ya < 0 && xa < 0) {
        facing = 7;
      }
      
      if (x < 30)
        x = 30;
      
      if (x > 1038)
        x = 1038;
      
      if (y < 10
            && !(x < (200 + 34) * 3 - 120 && x > (200 - 40) * 3 && sb
                   .getScenery() == 0))
        y = 10;
      
      if (y > 728
            && !(x < (200 + 34) * 3 - 120 && x > (200 - 40) * 3 && sb
                   .getScenery() == 1))
        y = 728;
      
      if (y < 9 && sb.getScenery() == 0) {
        y = 727;
        sb.setScenery(1);
      }
      if (y > 729 && sb.getScenery() == 1) {
        y = 11;
        sb.setScenery(0);
      }
      int dx = 600 - x - 64;
      int dy = 450 - 16 - y - 64;
      if (Math.sqrt(dx * dx + dy * dy) <= 140 && sb.getScenery() == 0) {
        x -= xa;
        y -= ya;
        
      }
    }
    img = playerSprite.getSubimage((int) (frame / 20) * 32, facing * 32,
                                   32, 32);
  }
  
  // move
  public void keyPressed(KeyEvent e) {
    shop = false;
    brew = false;
    inventory = false;
    sb.getCauldron().reset();
    sell = false;
    recipeList = false;
    if (e.getKeyCode() == KeyEvent.VK_A
          || e.getKeyCode() == KeyEvent.VK_LEFT)
      xa = -1;
    if (e.getKeyCode() == KeyEvent.VK_D
          || e.getKeyCode() == KeyEvent.VK_RIGHT)
      xa = 1;
    if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
      ya = -1;
    if (e.getKeyCode() == KeyEvent.VK_S
          || e.getKeyCode() == KeyEvent.VK_DOWN)
      ya = 1;
    
  }
  
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT && xa > 0
          || e.getKeyCode() == KeyEvent.VK_LEFT && xa < 0)
      xa = 0;
    if (e.getKeyCode() == KeyEvent.VK_UP && ya < 0
          || e.getKeyCode() == KeyEvent.VK_DOWN && ya > 0)
      ya = 0;
    if (e.getKeyCode() == KeyEvent.VK_D && xa > 0
          || e.getKeyCode() == KeyEvent.VK_A && xa < 0)
      xa = 0;
    if (e.getKeyCode() == KeyEvent.VK_W && ya < 0
          || e.getKeyCode() == KeyEvent.VK_S && ya > 0)
      ya = 0;
  }
  
}