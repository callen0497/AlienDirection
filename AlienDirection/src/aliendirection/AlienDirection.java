package aliendirection;
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//************************************************************************
//  AlienDirection.java       Author: Lewis/Loftus
//
//  Demonstrates the handling of keyboard events.
//************************************************************************

public class AlienDirection extends Application
{
    public final static int JUMP = 10;
    private final static int MAX_GEN_NUMBERS = 10;
    private final static int MIN_NUMBERS_RANGE = 1;
    private final static int MAX_NUMBERS_RANGE = 100;
    private final static int SCENE_X = 1000;
    private final static int SCENE_Y = 800;
    
    private ImageView imageView;
    
    private int total_eaten;
    private int total_num_groups;
    private int total_groups_eaten;
    private Alien[] alienNum;
    private Text countText, numText;
    private Group txt_num_group = new Group();

    public AlienDirection() {
        
    }
    @Override
    public void start(Stage primaryStage)
    {
        Image alien = new Image("aliendirection\\alien.png");
        
        countText = new Text(20, 30, " ");
        imageView = new ImageView(alien);
        Initialize();
        
        Group root = new Group(imageView, countText, txt_num_group);

        Scene scene = new Scene(root, SCENE_X, SCENE_Y, Color.BLACK);
        scene.setOnKeyPressed(this::processKeyPress);

        primaryStage.setTitle("Alien Direction");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //--------------------------------------------------------------------
    //  Modifies the position of the image view when an arrow key is
    //  pressed.
    //--------------------------------------------------------------------
    
    public void processKeyPress(KeyEvent event)
    {
        if(total_groups_eaten != total_num_groups)
        switch (event.getCode())
        {
            case UP:
                imageView.setY(imageView.getY() - JUMP);
                break;
            case DOWN:
                imageView.setY(imageView.getY() + JUMP);
                break;
            case RIGHT:
                imageView.setX(imageView.getX() + JUMP);
                break;
            case LEFT:
                imageView.setX(imageView.getX() - JUMP);
                break;
            default:
                break;  // do nothing if it's not an arrow key
        }  
        
        eatNumber();
        
        if(total_groups_eaten == total_num_groups)
        {
            userPlay();  
        }    
         else
        {
            userPlay();
    
        }
    }
         
    private void Initialize()
    {
        total_eaten = 0;
        total_num_groups = 0;
        total_groups_eaten = 0;
        imageView.setX(20);
        imageView.setY(40);
        countText.setText("Total Eaten: " + total_eaten);
        //countText.setFont(new Font(20));
        countText.setFill(Color.WHITE);
        
        generateNumbers();
        displayNums();
    }
    
    private void generateNumbers()
    {
        Integer txt_value;
        alienNum = new Alien[MAX_GEN_NUMBERS];
        
        Random generator = new Random();
            for(int count = 0; count < MAX_GEN_NUMBERS; count++)
            {
                txt_value = generator.nextInt(MAX_NUMBERS_RANGE) + MIN_NUMBERS_RANGE;
                total_num_groups++;
                alienNum[count] = new Alien(txt_value);
            }
    }
    
    private void displayNums()
    {
        int txt_x = 0, txt_y = 0;
        int gen_count;
        
        Random gen = new Random();
        txt_num_group.getChildren().clear();
        
            for(int num = 0; num < alienNum.length; num++)
            { if (!alienNum[num].isEaten())
            {
                gen_count = 0;
                do
                {
                    txt_x = gen.nextInt(800)+10;
                    txt_y = gen.nextInt(500)+100;
                    gen_count++;
                }
                while(num > 0);
                
                numText = new Text(txt_x, txt_y, alienNum[num].get_txtNum().toString());
                numText.setFill(Color.CYAN);
                
                txt_num_group.getChildren().add(numText);
                
                alienNum[num].setXY(txt_x, txt_y);
                
            }
         }
    }
    
  
  private void eatNumber()
  {
      for(int num = 0; num < alienNum.length; num++)
      {
          if (!alienNum[num].isEaten())
          {
                Point2D point = new Point2D(alienNum[num].get_xValue(), alienNum[num].get_yValue());
                if( imageView.contains(point) ) 
                {
                    total_eaten += alienNum[num].get_txtNum();
                    total_groups_eaten++;
                    alienNum[num].setEaten();
                    countText.setText("Total Eaten: " + total_eaten);
                    displayNums();
                } 
          }
      }
  }
  
public void userPlay()
  {
      boolean doAnother = true;
      Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
      confirmDialog.setHeaderText(null);
      confirmDialog.setContentText("Alien ate all the numbers. Would you like to play again?");
      Optional<ButtonType> playAgain = confirmDialog.showAndWait();
      
      if (playAgain.get() != ButtonType.OK )
          doAnother = false;
      
      if (doAnother)
          Initialize();
  }

  
public static void main(String[] args)
    {
       launch(args);
    }
}
