import java.awt.*; //drawing package
import java.util.*; //to use Scanner

//requires DrawingPanel.java as class within same directory
public class MovingShapes {
  
  /*GLOBAL VARIABLES
  * I didn't make these static because it makes more sense to make all other methods non static so they could be called on any
   * array realistically, like in project 2
   * the static main{} method creates an instance of MovingShapes class to run non static methods
   */
  int numShapes = 0; //number of shapes to be displayed
  int numMoves = 0; //total number of moves shapes make
  //global scanner scnr is created in main and used in other methods
  /***************/
  
  /*GLOBAL ARRAYS*/
  /*each shape's info is in same index across arrays
   * ex. 1st shape input is always in index 0, 2nd shape input is in index 1 across arrays
   * max 20 shapes input */
  String[] shapeTypes = new String[20]; //stores shapes, only accepts "Square" and "Circle"
  int[] shapeSizes = new int[20]; //size of every shape
  String[] shapeColors = new String[20]; //values can be ("Red", "Blue", "Pink", "Yellow", "Green", "Magenta", "Orange", "Cyan", "Dark_gray", "Light_gray", or "Gray")
  int[] shapeDirections = new int[20]; //bearing each shape will move towards (0 - left, 2 - up, 4 - right, 6 - down) [1 - up & left, 3 - up & right, 5 - bottom & right, 7 - bottom & left]
  int[] shapeSpeeds = new int[20]; //from 0 - 10
  int[] shapeX = new int[20]; //stores x-position of top left corner of every shape
  int[] shapeY = new int[20]; //stores y-position of top left corner of every shape
  /***************/
  
  public void getShapeInformation(Scanner scnr) {    
    for (int i = 0; i < numShapes; ++i) {
      shapeTypes[i] = scnr.next();
      shapeSizes[i] = scnr.nextInt();
      shapeColors[i] = scnr.next();
      shapeDirections[i] = scnr.nextInt();
      shapeSpeeds[i] = scnr.nextInt();
    }
  }
  
  //centers all shapes
  public void initialPosition(DrawingPanel panel, int panelWidth, int panelHeight) {
    //goes through every shape, centers them
    //center of each shape should be at (250, 250) because left half and top half are offset to above (250, 250)
    for (int i = 0; i < numShapes; ++i) {
      shapeX[i] = (panelWidth / 2) - (shapeSizes[i] / 2);
      shapeY[i] = (panelHeight / 2) - (shapeSizes[i] / 2);
    }
    
    showShapes(panel, true);
    panel.sleep(100);
  }
  
  //covers shape with background, moves it, then redraws in new position for total num of moves
  public void showShapesMoving(DrawingPanel panel) {
    for (int i = 1; i <= numMoves; ++i) {
      showShapes(panel, false);
      changePositions();
      showShapes(panel, true);
      panel.sleep(100);
    }
  }
  
  //recalculates every shapes movement for exactly 1 move
  public void changePositions() {
    for (int i = 0; i < numShapes; ++i) {
      //diagonals x and y are based on isoceles right triangle formula (a = sqrt((hypot^2)/2))
      double sideDistForDiagTravel = Math.sqrt((shapeSpeeds[i] * shapeSpeeds[i]) / 2);
      
      //switch has cases for 8 directions to move in each
      switch (shapeDirections[i]) {
        case 0: //left
          shapeX[i] -= shapeSpeeds[i];
          break;
        case 2: //up
          shapeY[i] -= shapeSpeeds[i];
          break;
        case 4: //right
          shapeX[i] += shapeSpeeds[i];
          break;
        case 6: //down
          shapeY[i] += shapeSpeeds[i];
          break;
          
        case 1: //up left
          shapeY[i] -= sideDistForDiagTravel;
          shapeX[i] -= sideDistForDiagTravel;
          break;
        case 3: //up right
          shapeY[i] -= sideDistForDiagTravel;
          shapeX[i] += sideDistForDiagTravel;
          break;
        case 5: //down left
          shapeY[i] += sideDistForDiagTravel;
          shapeX[i] -= sideDistForDiagTravel;
          break;
        case 7: //down right
          shapeY[i] += sideDistForDiagTravel;
          shapeX[i] += sideDistForDiagTravel;
          break;
      }
    }
  }
  
  //draws all shapes (in white if fillShape == false; in color if fillShape == true;)
  public void showShapes(DrawingPanel panel, boolean fillShape) {
    for (int i = 0; i < numShapes; ++i) {
      //if else sets color based on fillShape
      if (fillShape) {
        graphicsSetColor(panel, i);
      }
      else {
        setNoColor(panel);
      }
      
      //calls method to draw based on string shape type from input
      if (shapeTypes[i].equals("Square")) {
        showSquare(panel, i, fillShape);
      }
      else if (shapeTypes[i].equals("Circle")) {
        showCircle(panel, i, fillShape);
      }
      
    }
  }
  
  //sets pen color to background color to simulate 1/2 of animation frames
  public void setNoColor(DrawingPanel panel) {
    Graphics pen = panel.getGraphics();
    pen.setColor(Color.WHITE);
  }
  
  //recieves panel and i, i is index of shape in arrays
  public void graphicsSetColor(DrawingPanel panel, int i) {
    Graphics pen = panel.getGraphics();
    
    //setting pen color based on color in shapeColors[]
    switch (shapeColors[i]) {
      case "Red":
        pen.setColor(Color.RED);
        break;
      case "Blue":
        pen.setColor(Color.BLUE);
        break;
      case "Pink":
        pen.setColor(Color.PINK);
        break;
      case "Yellow":
        pen.setColor(Color.YELLOW);
        break;
      case "Green":
        pen.setColor(Color.GREEN);
        break;
      case "Magenta":
        pen.setColor(Color.MAGENTA);
        break;
      case "Orange":
        pen.setColor(Color.ORANGE);
        break;
      case "Dark_gray":
        pen.setColor(Color.DARK_GRAY);
        break;
      case "Light_gray":
        pen.setColor(Color.LIGHT_GRAY);
        break;
      case "Gray":
        pen.setColor(Color.GRAY);
        break;
      case "Cyan":
        pen.setColor(Color.CYAN);
        break;
      default:
        pen.setColor(Color.BLACK);
        break;
    }
  }
  
  //receives panel to draw on, index of shape to reference, and whether or not shape is drawn, or blank shape
  //draws square according to dimensions either same color as background or filled in
  public void showSquare(DrawingPanel panel, int i, boolean fillSquare) {
    Graphics pen = panel.getGraphics();
    
    //draws rectangle no matter if background color or filled in currently
    pen.fillRect(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    
    //if filled in color, draws outline
    if (fillSquare) {
      pen.setColor(Color.BLACK);
      pen.drawRect(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    }
    //else erases previous outline
    else {
      pen.setColor(Color.WHITE);
      pen.drawRect(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    }
  }
  
  //same as showSquare() but an circle instead
  public void showCircle(DrawingPanel panel, int i, boolean fillSquare) {
    Graphics pen = panel.getGraphics();
    
    //draws circle no matter if background color or filled in currently
    pen.fillOval(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    
    if (fillSquare) {
      pen.setColor(Color.BLACK);
      pen.drawOval(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    }
    else {
      pen.setColor(Color.WHITE);
      pen.drawOval(shapeX[i], shapeY[i], shapeSizes[i], shapeSizes[i]);
    }
   }
  
  public static void main(String[] args) {
    System.out.println("UTSA - Fall 2021 - CS1083 - Project 3 - written by Amos Munteanu");
    MovingShapes movingShapes = new MovingShapes(); //movingShapes variable creates instance of MovingShapes class to call nonstatic methods inside static main{}
    
    Scanner scnr = new Scanner(System.in); //global scanner used in all methods
    
    System.out.print("\nPlease input width, height of the panel, # of shapes, # of times to move followed by the shape, size, color, direction, and speed of every shape: ");
    int panelWidth = scnr.nextInt(); //first 2 numbers are panel size
    int panelHeight = scnr.nextInt();
    movingShapes.numShapes = scnr.nextInt(); //next input is number of shapes (stored globally)
    movingShapes.numMoves = scnr.nextInt(); //next input is number of moves (stored globally)
    //rest of input is shape info
    movingShapes.getShapeInformation(scnr);
    
    DrawingPanel panel = new DrawingPanel(panelWidth, panelHeight); //creates new window of dimensions read in using class DrawingPanel
    Graphics pen = panel.getGraphics();
    
    movingShapes.initialPosition(panel, panelWidth, panelHeight); //first instance of shapes centered
    movingShapes.showShapesMoving(panel); //animates all shapes
    
  }
}