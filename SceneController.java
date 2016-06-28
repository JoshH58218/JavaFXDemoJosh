import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;


public class SceneController
{    
    /* The stage that the scene belongs to, required to catch stage events and test for duplicate controllers. */
    private static Stage stage;     

    /* These FXML variables exactly corrispond to the controls that make up the scene, as designed in Scene 
     * Builder. It is important to ensure that these match perfectly or the controls won't be interactive. */  
    @FXML   private Button addButton;
    @FXML   private Button removeButton;
    @FXML   private Button exitButton;
    @FXML   private Button editButton;
    @FXML   private Button goButton;
    @FXML   private TextField searchField;
    @FXML   private ListView listView;

    public SceneController()          // The constructor method, called first when the scene is loaded.
    {
        System.out.println("Initialising controllers...");

        /* Our JavaFX application should only have one initial scene. The following checks to see
         * if a scene already exists (deterimed by if the stage variable has been set) and if so 
         * terminates the whole application with an error code (-1). */        
        if (stage != null)
        {
            System.out.println("Error, duplicate controller - terminating application!");
            System.exit(-1);
        }        

    } 

    @FXML   void initialize()           // The method automatically called by JavaFX after the constructor.
    {            
        /* The following assertions check to see if the JavaFX controls exists. If one of these fails, the
         * application won't work. If the control names in Scene Builder don't match the variables this fails. */ 
        System.out.println("Asserting controls...");
        try
        {
            assert addButton != null : "Can't find add button.";
            assert removeButton != null : "Can't find yesremovebutton.";
            assert exitButton != null : "Can't find exit button.";
            assert editButton != null : "Can't find edit button.";
            assert goButton != null : "Can't find go button.";
            assert searchField!= null : "Can't find search field.";
            assert listView != null : "Can't find list box.";
        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }

        /* Next, we load the list of fruit from the database and populate the listView. */
        System.out.println("Populating scene with items from the database...");        
        @SuppressWarnings("unchecked")
        List<VideoGame> targetList = listView.getItems();  // Grab a reference to the listView's current item list.
        VideoGame.readAll(targetList);                     // Hand over control to the fruit model to populate this list.
    }

    /* In order to catch stage events (the main example being the close (X) button being clicked) we need
     * to setup event handlers. This happens after the constructor and the initialize methods. Once this is
     * complete, the scene is fully loaded and ready to use. */
    public void prepareStageEvents(Stage stage)
    {
        System.out.println("Preparing stage events...");

        this.stage = stage;

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("Close button was clicked!");
                    Application.terminate();
                }
            });
    }       

    /* The next three methods are event handlers for clicking on the buttons. 
     * The names of these methods are set in Scene Builder so they work automatically. */    
    @FXML   void addClicked()
    {
        System.out.println("Add was clicked, opening secondary scene.");
        openNewScene(0);
    }

    @FXML   void removeClicked()
    {
        System.out.println("remove was clicked!");
    }

    @FXML   void exitClicked()
    {
        System.out.println("Exit was clicked!");        
        Application.terminate();        // Call the terminate method in the main Application class.
    }
    
    @FXML   void editClicked()
    {
        System.out.println("Edit was clicked, opening secondary scene.");
        VideoGame selectedItem = (VideoGame) listView.getSelectionModel().getSelectedItem();
        openNewScene(selectedItem.id);       
    }
    
    @FXML   void goClicked()
    {
        System.out.println("search method needed");        
    }

    /* This method, set in SceneBuilder to occur when the listView is clicked, establishes which
     * item in the view is currently selected (if any) and outputs it to the console. */    
    @FXML   void listViewClicked()
    {
        VideoGame selectedItem = (VideoGame) listView.getSelectionModel().getSelectedItem();

        if (selectedItem == null)
        {
            System.out.println("Nothing selected!");
        }
        else
        {
            System.out.println(selectedItem + " (id: " + selectedItem.id + ") is selected.");
        }
    }  
    
    void openNewScene(int id)
    {

        FXMLLoader loader = new FXMLLoader(Application.class.getResource("Edit.fxml"));

        try
        {
            Stage stage2 = new Stage();
            stage2.setTitle("Details");
            stage2.setScene(new Scene(loader.load()));
            stage2.show();           
            EditController controller2 = loader.getController();
            controller2.prepareStageEvents(stage2);

            controller2.setParent(this);
            if (id != 0) controller2.loadItem(id);            

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

