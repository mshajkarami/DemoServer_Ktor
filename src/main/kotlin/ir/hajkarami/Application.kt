package ir.hajkarami

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.URL


fun main() {
    embeddedServer(Netty, port = 8081) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "iem-dev"
            verifier(
                JwkProviderBuilder(URL("http://localhost:8080/realms/iem-dev/protocol/openid-connect/certs"))
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("preferred_username").asString() != "")
                    JWTPrincipal(credential.payload)
                else null

            }
        }
    }
    routing {
        get("/") {
            call.respondText("HELLO WORLD!")
        }
        authenticate("auth-jwt") {
            get("/secure") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("preferred_username")?.asString()
                call.respondText("Hello $username! You are authenticated.")
            }
        }
    }

}