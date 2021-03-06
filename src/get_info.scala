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
    println("     Unidades disponibles por alcaldía:       ")
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

try {
    // Database connection
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val q1_3 = "SELECT \"lineas-metrobus\".name , \"limite-de-las-alcaldias\".nomgeo "
    val q2_3 = "FROM \"lineas-metrobus\", \"limite-de-las-alcaldias\" "
    val q3_3 = "WHERE St_Intersects(\"lineas-metrobus\".geom, \"limite-de-las-alcaldias\".geom) = True "
    val q4_3 = "ORDER BY \"limite-de-las-alcaldias\".nomgeo "
    val query_3 = q1_3 + q2_3 + q3_3 + q4_3
    val resultSet_3 = statement.executeQuery(query_3)
    println("**********************************************")
    println("          Rutas por alcaldía:            ")
    println("**********************************************")
    while ( resultSet_3.next() ) {
        val ruta = resultSet_3.getString("name")
        val alcaldia = resultSet_3.getString("nomgeo")
        println("> " + ruta + " \t\t| " + alcaldia )
    }
} catch {
    case e: Exception => e.printStackTrace
} finally {
    // Closing database connection
    connection.close()

try {
    // Database connection
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement()
    val q1_2 = "SELECT \"estaciones-metrobus\".nombre, \"limite-de-las-alcaldias\".nomgeo "
    val q2_2 = "FROM \"estaciones-metrobus\", \"limite-de-las-alcaldias\" "
    val q3_2 = "WHERE ST_WITHIN(\"estaciones-metrobus\".geom, \"limite-de-las-alcaldias\".geom) = True "
    val q4_2 = "ORDER BY \"limite-de-las-alcaldias\".nomgeo "
    val query_2 = q1_2 + q2_2 + q3_2 + q4_2
    val resultSet_2 = statement.executeQuery(query_2)
    println("**********************************************")
    println("          Estaciones por alcaldía:            ")
    println("**********************************************")
    while ( resultSet_2.next() ) {
        val alcaldia = resultSet_2.getString("nomgeo")
        val estacion = resultSet_2.getString("nombre")
        println("> " + alcaldia + " \t| " + estacion )
    }
} catch {
    case e: Exception => e.printStackTrace
} finally {
    // Closing database connection
    connection.close()
}
}
}
}
