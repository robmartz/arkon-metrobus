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
    val q1 = "SELECT  DISTINCT \"limite-de-las-alcaldias\".nomgeo "
    val q2 = "FROM \"limite-de-las-alcaldias\", \"prueba_fetchdata_metrobus\" "
    val q3 = "WHERE ST_CONTAINS(\"limite-de-las-alcaldias\".geom, \"prueba_fetchdata_metrobus\".geom) = True "
    val q4 = "ORDER BY \"limite-de-las-alcaldias\".nomgeo "
    val query = q1 + q2 + q3 + q4
    val resultSet = statement.executeQuery(query)
    println("**********************************************")
    println("           Alcaldías disponibles:  ")
    println("**********************************************")
    while ( resultSet.next() ) {
        val alcaldias = resultSet.getString("nomgeo")
        println("> " + alcaldias )
    }
} catch {
	case e: Exception => e.printStackTrace
} finally {
	// Closing database connection
	connection.close()
}

try {
    // Database connection
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val q1_1 = "SELECT \"prueba_fetchdata_metrobus\".vehicle_id , \"limite-de-las-alcaldias\".nomgeo "
    val q2_1 = "FROM \"prueba_fetchdata_metrobus\", \"limite-de-las-alcaldias\" "
    val q3_1 = "WHERE ST_WITHIN(\"prueba_fetchdata_metrobus\".geom, \"limite-de-las-alcaldias\".geom) = True "
    val q4_1 = "ORDER BY \"limite-de-las-alcaldias\".nomgeo "
    val query_1 = q1_1 + q2_1 + q3_1 + q4_1
    val resultSet_1 = statement.executeQuery(query_1)
    println("**********************************************")
    println("           Unidades por alcaldía:             ")
    println("**********************************************")
    while ( resultSet_1.next() ) {
        val alcaldia = resultSet_1.getString("nomgeo")
        val unidad = resultSet_1.getString("vehicle_id")
        println("> " + alcaldia + " \t| " + unidad )
    }
} catch {
    case e: Exception => e.printStackTrace
} finally {
    // Closing database connection
    connection.close()
}
}
}
