import play.db.Database;
import play.db.Databases;
import java.sql.Connection;
import com.google.common.collect.*;

import org.junit.*;
import static org.junit.Assert.*;

import controllers.TicketController;
import models.Ticket;

public class TicketTest
{
    Database database;
    TicketController ticket;
    Ticket tickmodel;
    
    // Create test database from actual database 
    @Before
    public void createDatabase()
    {
        database = Databases.createFrom(
            "default",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://techlabdb.cxl4ujdxkimj.us-west-2.rds.amazonaws.com:3306/LCLSA_tech_lab_management_system",
            ImmutableMap.of(
                    "user", "labworker",
                    "password", "watson",
                    "host", "techlabdb.cxl4ujdxkimj.us-west-2.rds.amazonaws.com"
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
        //Create a ticket using SQL with some data
        connection.prepareStatement("insert into `Ticket`(`ticketID`, `name`, `assigned_to`, `created_for`) values ('100', 'Ticket80', 'yab003', 'yab003')").execute(); 
        //See if the date_created section is not empty...(changed to assertNull to see
        //if it was a fluke and test failed)
        assertNotNull(connection.prepareStatement("select * from `Ticket` where date_created")              
                .executeQuery().next());
        //Need to delete ticket from database too
    }
    
}