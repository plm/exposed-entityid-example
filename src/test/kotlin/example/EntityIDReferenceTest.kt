package example

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class EntityIDReferenceTest : StringSpec({

    Database.connect("jdbc:postgresql://localhost:54321/example?user=example&password=example", driver = "org.postgresql.Driver")

    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users, Messages)
    }

    "Create message using DAO API" {
        val user = transaction {
            User.new {
                name = "Example"
            }.also { user ->
                Message.new {
                    owner = user
                    content = "Hello world!"
                }
            }
        }
        transaction { Message.find { Messages.owner eq user.id }.count() } shouldBe 1
    }

    "Create message using DSL API" {
        val userId = transaction {
            val id = Users.insertAndGetId {
                it[name] = "Example"
            }
            Messages.insert {
                it[owner] = id
                it[content] = "Hello world!"
            }
            id
        }
        transaction { Message.find { Messages.owner eq userId }.count() } shouldBe 1
    }
})
