import play.db.Database;
import play.db.Databases;
import java.sql.Connection;
import com.google.common.collect.*;

import org.junit.*;
import static org.junit.Assert.*;

import controllers.TicketController.*;

public class TicketTest
{
    Database database;
    
    // Create test database from actual database 
    @Before
    public void createDatabase()
    {
        Database database = Databases.createFrom(
            "tickettest",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://techlabdb.cxl4ujdxkimj.us-west-2.rds.amazonaws.com:3306/LCLSA_tech_lab_management_system",
            ImmutableMap.of(
                    "user", "labworker",
                    "password", "watson"
            )
        );
    }
    
    // Shut down the connection to database
    @After
    public void closeDatabase()
    {
        database.shutdown();
    }
    
    @Test
    public void createdDateTest() throws Exception
    {
        Connection connection = database.getConnection();
        create();
        save();
        assertNotNull(date_created);
        
    }
    
}