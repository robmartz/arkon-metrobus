package jdbc

import java.sql.DriverManager
import java.sql.Connection

/**
 * A Scala JDBC connection
 */
object ScalaJdbcConnectSelect {
	def main(args: Array[String]) {
    // connect to the database named "gis" on the docker container
    val driver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://mbus_db_container:5432/gis"
    val username = "mbus_user"
    val password = "mbus123"
    var connection:Connection = null
    try {
    // Database connection
    
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val query = "SELECT srtext FROM spatial_ref_sys"
    val resultSet = statement.executeQuery(query)
    
    while ( resultSet.next() ) {
        val srt = resultSet.getString("srtext")
        println("> " + srt )
    }
	} 
	catch {
    	case e: Exception => e.printStackTrace
	} finally {
    // Closing database connection
    connection.close()
	}
  }
}
