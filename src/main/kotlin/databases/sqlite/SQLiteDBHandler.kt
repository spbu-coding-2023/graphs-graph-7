package databases.sqlite

import graph.model.Graph
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class SQLiteDBHandler {
    fun open(file: File){
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")
        val newGraph = Graph()
        transaction {

        }
    }

    fun save(file: File){
        Database.connect("jdbc:sqlite:$file", driver = "org.sqlite.JDBC")

    }
}