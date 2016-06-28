import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;

/* Each table you wish to access in your database requires a model class, like this example: */
public class VideoGame
{
    /* First, map each of the fields (columns) in your table to some public variables. */
    public int id;
    public String name;
    public int genreId;
    public String developer;
    public String description;

    /* Next, prepare a constructor that takes each of the fields as arguements. */
    public VideoGame(int id, String name, int genreId, String developer, String description)
    {
        this.id = id;
        this.name = name;
        this.genreId = genreId;
        this.developer = developer;
        this.description = description;
    }

    /* A toString method is vital so that your model items can be sensibly displayed as text. */
    @Override public String toString()
    {
        return name;
    }

    /* Different models will require different read and write methods. Here is an example 'loadAll' method 
     * which is passed the target list object to populate. */
    public static void readAll(List<VideoGame> list)
    {
        list.clear();       // Clear the target list first.

        /* Create a new prepared statement object with the desired SQL query. */
        PreparedStatement statement = Application.database.newStatement("SELECT id, name, genreId, developer, description FROM VideoGames"); 

        if (statement != null)      // Assuming the statement correctly initated...
        {
            ResultSet results = Application.database.runQuery(statement);       // ...run the query!

            if (results != null)        // If some results are returned from the query...
            {
                try {                               // ...add each one to the list.
                    while (results.next()) {                                               
                        list.add( new VideoGame(results.getInt( "id"),
                                results.getString("name"),
                                results.getInt("genreId"),
                                results.getString("developer"),
                                results.getString("description")));
                    }
                }
                catch (SQLException resultsexception)       // Catch any error processing the results.
                {
                    System.out.println("Database result processing error: " + resultsexception.getMessage());
                }
            }
        }
    }
    
     public static VideoGame getById(int id)
    {
        VideoGame videoGame = null;

        PreparedStatement statement = Application.database.newStatement("SELECT id, name, genreId, developer, description FROM videoGames WHERE id = ?"); 

        try 
        {
            if (statement != null)
            {
                statement.setInt(1, id);
                ResultSet results = Application.database.runQuery(statement);

                if (results != null)
                {
                    videoGame = new VideoGame(results.getInt("id"), results.getString("name"), results.getInt("genreId"), results.getString("developer"), results.getString("description"));
                }
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }

        return videoGame;
    }
    
    public void save()    
    {
        PreparedStatement statement;

        try 
        {

            if (id == 0)
            {

                statement = Application.database.newStatement("SELECT id FROM VideoGames ORDER BY id DESC");             

                if (statement != null)	
                {
                    ResultSet results = Application.database.runQuery(statement);
                    if (results != null)
                    {
                        id = results.getInt("id") + 1;
                    }
                }

                statement = Application.database.newStatement("INSERT INTO VideoGames (id, name, genreId, developer, description) VALUES (?, ?, ?, ?, ?)");             
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setInt(3, genreId);
                statement.setString(4, developer);  
                statement.setString(5, description);  

            }
            else
            {
                statement = Application.database.newStatement("UPDATE VideoGames SET name = ?, genreId = ?, developer = ?, description = ? WHERE id = ?");             
                statement.setString(1, name);
                statement.setInt(2, genreId);  
                statement.setString(3, developer);  
                statement.setString(4, description);  
                statement.setInt(5, id);  
            }

            if (statement != null)
            {
                Application.database.executeUpdate(statement);
            }
        }
        catch (SQLException resultsexception)
        {
            System.out.println("Database result processing error: " + resultsexception.getMessage());
        }
    }
}
