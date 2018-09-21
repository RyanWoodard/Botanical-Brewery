import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class StartBrewery extends JPanel {
  
  private int scenery, win;
  private Shop shop;
  private Seed[] seeds = new Seed[6];
  private Potion[] potions = new Potion[10];
  private Cauldron cauldron = new Cauldron(potions, this);
  private Player player;
  private BufferedImage breweryGround, gardenGround;
  private Land[] lands = new Land[16];
  private Customer customer;
  private boolean menu = true;
  private int menuCount = 0;
  
  public StartBrewery() throws IOException {
    // Set lands
    for (int i = 0; i < 4; i++) {
      lands[i] = new Land(this, 170 + (140 * (i - (int) (i / 2) * 2)),
                          (int) (i / 2 + 1) * 140 - 50);
      lands[i + 4] = new Land(this,
                              780 + (140 * (i - (int) (i / 2) * 2)),
                              (int) (i / 2 + 1) * 140 - 50);
      lands[i + 8] = new Land(this,
                              170 + (140 * (i - (int) (i / 2) * 2)),
                              (int) (i / 2 + 1) * 140 + 380);
      lands[i + 12] = new Land(this,
                               780 + (140 * (i - (int) (i / 2) * 2)),
                               (int) (i / 2 + 1) * 140 + 380);
    }
    // constructs the seeds
    seeds[0] = new Seed("Stabilis", 0, 8, 2000);
    seeds[1] = new Seed("Curare", 1, 22, 4000);
    seeds[2] = new Seed("Malum", 2, 22, 4000);
    seeds[3] = new Seed("Veloces", 3, 30, 6000);
    seeds[4] = new Seed("Pellucidum", 4, 42, 8000);
    seeds[5] = new Seed("Supra", 5, 1000, 24000);
    
    // arrays to represent the recipes for each potion
    int[] tempIng0 = { 0, 1 };
    int[] tempIng1 = { 0, 2 };
    int[] tempIng2 = { 0, 3 };
    int[] tempIng3 = { 0, 4 };
    int[] tempIng4 = { 0, 1, 2 };
    int[] tempIng5 = { 0, 2, 4 };
    int[] tempIng6 = { 0, 1, 4 };
    int[] tempIng7 = { 2, 3, 4 };
    int[] tempIng8 = { 1, 2, 4 };
    int[] tempIng9 = { 1, 3, 5 };
    
    potions[0] = new Potion("Healing", 30, tempIng0);
    potions[1] = new Potion("Harm", 30, tempIng1);
    potions[2] = new Potion("Swiftness", 38, tempIng2);
    potions[3] = new Potion("Illusion", 50, tempIng3);
    potions[4] = new Potion("Strength", 52, tempIng4);
    potions[5] = new Potion("Confusion", 72, tempIng5);
    potions[6] = new Potion("Awareness", 72, tempIng6);
    potions[7] = new Potion("Chaos", 94, tempIng7);
    potions[8] = new Potion("Luck", 86, tempIng8);
    potions[9] = new Potion("Transcedence", 1044, tempIng9);
    
    shop = new Shop(this);
    player = new Player(this);
    customer = new Customer(this);
    breweryGround = ImageIO.read(new File("pictures/Shop.png"));
    gardenGround = ImageIO.read(new File("pictures/Garden.png"));
    //scenery is if the player is in the shop or garden. (0 = shop, 1 = garden)
    scenery = 0;
    
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        
        //the player's actions
        player.keyReleased(e);
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          player.brew();
          player.shop();
          player.sellCheck();
        }
        
        if (scenery == 1 && e.getKeyCode() == KeyEvent.VK_SPACE) {
          for (int i = 0; i < Land.getSpaceLevel() * 4; i++) {
            lands[i].plant();
            lands[i].harvest();
          }
        }
        
        //the menus
        if(!menu &&  !(menuCount > 0)){
        if (e.getKeyCode() == KeyEvent.VK_I)
          player.inventory();
        if (e.getKeyCode() == KeyEvent.VK_R)
          player.recipeList();
        if (e.getKeyCode() == KeyEvent.VK_H )
          player.help();
        if (player.getSell() && e.getKeyCode() == KeyEvent.VK_P)
          customer.sell();}
        
        if(menu)
          menuCount = 600;
           menu = false;
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A
              || e.getKeyCode() == KeyEvent.VK_LEFT
              || e.getKeyCode() == KeyEvent.VK_D
              || e.getKeyCode() == KeyEvent.VK_RIGHT
              || e.getKeyCode() == KeyEvent.VK_W
              || e.getKeyCode() == KeyEvent.VK_UP
              || e.getKeyCode() == KeyEvent.VK_S
              || e.getKeyCode() == KeyEvent.VK_DOWN)
          player.keyPressed(e);
        
      }
    });
    setFocusable(true);
    
    addMouseListener(new MouseListener() {
      @Override
      public void mousePressed(MouseEvent e) {
        //mouse actions
        if (!player.getHelp()) {
          if (player.getBrew() && !player.getInventory()) {
            cauldron.addIngs(e.getPoint().x, e.getPoint().y);
            cauldron.brew(e.getPoint().x, e.getPoint().y, 420);
            cauldron.clear(e.getPoint().x, e.getPoint().y);
          }
          if (player.getShop() && !player.getInventory()) {
            shop.buy(e.getPoint().x, e.getPoint().y, player);
          }
          
          if (scenery == 1 && !player.getInventory()
                && !player.getRecipe()) {
            Land.setCurSeed(e.getPoint().x, e.getPoint().y);
          }
          if (scenery == 0 && player.getSell())
            shop.sell(e.getPoint().x, e.getPoint().y);
        }
      }
      
      @Override
      public void mouseReleased(MouseEvent e) {
      }
      
      @Override
      public void mouseEntered(MouseEvent e) {
      }
      
      public void mouseExited(MouseEvent e) {
      }
      
      public void mouseClicked(MouseEvent e) {
      }
    });
    setFocusable(true);
    
  }
  
  //getters
  
  public Player getPlayer() {
    return player;
  }
  
  public Seed[] getSeeds() {
    return seeds;
    
  }
  
  public Cauldron getCauldron() {
    return cauldron;
    
  }
  
  public void paint(Graphics g) {
    
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    //paints the shop
    if (scenery == 0) {
      
      
      g.drawImage(breweryGround, -2, -16, 1200, 900, null);
      cauldron.paint2(g);
      g.drawImage(Images.getMoney(), 5,25,37,48, null);
      
      g.drawImage(Images.getMoney(), 1155,25,37,48, null);
      
      player.paint(g2d);
      
      cauldron.paint(g);
      shop.paint(g);
      shop.paintSell(g);
      player.paintINV(g2d);
      customer.paint(g);
      
    }
    //paints the garden
    else if (scenery == 1) {
      g.drawImage(gardenGround, -2, -16, 1200, 900, null);
      for (int i = 0; i < Land.getSpaceLevel() * 4; i++)
        lands[i].paint(g);
      player.paint(g2d);
      
      Land.paintINV(g);
      player.paintINV(g2d);
    }
    
    //displays money
    g.setFont(new Font("Impact", Font.PLAIN, 22));
    g.setColor(Color.green);
    g.drawString("$" + player.getMoney(), 1050, 20);
    player.brewCheck();
    
    if(win > 0){
      g.setFont(new Font("Impact", Font.PLAIN, 200));
      g.setColor(Color.red);
      g.drawString("YOU WIN!!!", 150, 450);
      win--;
    }
    
    if(menuCount > 0){
   g.drawImage(Images.getRock(), 0, 0, 1200, 900, null);
         g.setColor(Color.GREEN);
      g.fillRect(300, 550,(int)(600*((600-menuCount*1.0)/(600.0))), 50);   
           g.setColor(Color.WHITE);
      g.drawRect(300, 550,(int)(600), 50);
   menuCount--;
 g.setFont(new Font("Impact", Font.PLAIN, 30));
    g.setColor(Color.WHITE);
  
  if(menuCount > 400)
    g.drawString("Rendering graphics", 300,535);
  else
      g.drawString("Connecting to servers", 300,535);
    }
      
      
      if(menu) 
      g.drawImage(Images.getMenu(), 0, 0, 1200, 875, null);
  }
  
  public void move() {
    player.move();
    
  }
  
  // new
  public void grow() {
    for (int i = 0; i < Land.getSpaceLevel() * 4; i++) {
      lands[i].grow();
    }
    customer.time();
    customer.request();
  }
  
  public void setScenery(int i) {
    scenery = i;
  }
  
  public int getScenery() {
    return scenery;
  }
  
  public static void main(String[] args) throws InterruptedException,
    IOException {
    Images.setImages();
    JFrame f = new JFrame("Botanical Brewery");
    StartBrewery bb = new StartBrewery();
    f.add(bb);
    f.setSize(1200, 900);
    f.setLocationRelativeTo(null);
    f.setResizable(false);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    while (true) {
      bb.repaint();
      Thread.sleep(5);
      bb.move();
      bb.grow();
    }
  }
  
  public void setWin(){
    win = 500;
  }
  
  public String getSeedName(int i) {
    return seeds[i].getName();
  }
  
  public String getPotionName(int i) {
    return potions[i].getName();
  }
  
  public Potion getPotion(int potion) {
    return potions[potion];
  }
  
  public Customer getCustomer() {
    return customer;
  }
}
