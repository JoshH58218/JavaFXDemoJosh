import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import java.util.List;


public class EditController
{
    
    private Stage stage;
    private SceneController parent;
    
     @FXML   private Button saveButton;
     @FXML   private TextField nameField;
     @FXML   private ChoiceBox genreField;
     @FXML   private TextField developerField;
     @FXML   private TextField descriptionField;
     
     private VideoGame videoGame;
    
    public void EditController()
    {
        System.out.println("Initialising controllers...");
    }
    
    public void prepareStageEvents(Stage stage)
    {
        System.out.println("Preparing stage events....");
        
        this.stage = stage;
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
               public void handle(WindowEvent we){
                   System.out.println("Close button was clicked!");
                }
            });
    }
    
    @FXML void initialize()
    {
        System.out.println("Asserting controls...");
        try
        {
            assert saveButton != null : "Can't find saveButton";
            assert nameField != null : "Can't find nameField";
            assert genreField != null : "Can't find genreField";
            assert developerField != null : "Can't find developerField";
            assert descriptionField != null : "Can't find descriptionField";
        }
        catch (AssertionError ae)
        {
            System.out.println("FXML assertion failure: " + ae.getMessage());
            Application.terminate();
        }
        
        System.out.println("Populating scene with items from the database...");        
        @SuppressWarnings("unchecked")
        List<Genre> targetList = genreField.getItems();  // Grab a reference to the listView's current item list.
        Genre.readAll(targetList);       
        genreField.getSelectionModel().selectFirst();
        
 
        
    }
    
    public void setParent(SceneController parent)
    {
        this.parent = parent;
    }
    
    public void loadItem(int id)
    {
        videoGame = VideoGame.getById(id);
        nameField.setText(videoGame.name);
        
        List<Genre> targetList = genreField.getItems();
        
        for(Genre g : targetList)
        {
            if (g.id == videoGame.genreId)
            {
                genreField.getSelectionModel().select(g);
            }
        }
        
    }
    
    @FXML void saveClicked()
    {
        System.out.println("Save button clicked!");
        
        if (videoGame == null)
        {
            videoGame = new VideoGame(0, "", 0, "", "");
        }
        
        videoGame.name = nameField.getText();
        
        Genre selectedGenre = (Genre) genreField.getSelectionModel().getSelectedItem();
        videoGame.genreId = selectedGenre.id;
        
        videoGame.save();

        parent.initialize();

        stage.close();
        
    }
}
