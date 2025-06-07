package dinner_invitation.vertical_slicing

import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant


@Controller
class MasteringHtmxController {

    @GetMapping("/lorem")
    @ResponseBody
    fun loremIpsum(): String {
        return "<b>lorem-ipsum</b>"
    }

    @PostMapping("mouse_entered")
    @ResponseBody
    fun mouseMoved(): String {
        return "<b>mouse-moved</b>"
    }


    @GetMapping("data")
    @ResponseBody
    fun data(): String {
        return "<b>data</b>"
    }

    @GetMapping("trigger_delay")
    @ResponseBody
    fun trigger_delay(): String {
        return """
            <table>
              <tr><td>Siem</td> <td>9 jaar</td></tr>
              <tr><td>Isa</td> <td> 7 jaar</td></tr>
              <tr><td>Sofie</td> <td> 5 jaar</td></tr>
              <tr><td>Jesse</td> <td> 3 jaar</td></tr>
            </table>  
           """.trimIndent()
    }


    @PostMapping("/validate-email")
    @ResponseBody
    fun validateEmail(@RequestParam email: String?): String {
        return if (email.isNullOrBlank()) {
            // Return an error message as HTML
            """<div id="email-error" style="color:red;">Email is required.</div>"""
        } else if (!Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matches(email)) {
            // Return an error message as HTML
            """<div id="email-error" style="color:red;">Invalid email format.</div>"""
        } else {
            // Return an empty div if valid
            """<div id="email-error"></div>"""
        }
    }

    @PostMapping("/validate-phone-number")
    @ResponseBody
    fun validatePhoneNumber(@RequestParam phoneNumber: String?): String {
        val pattern = Regex("""^0[6]{1}-?\d{8}$""")
        return if (phoneNumber.isNullOrBlank()) {
            """<div id="phone-number-error"></div>"""
        } else if (!pattern.matches(phoneNumber)) {
            """<div id="phone-number-error" style="color:red;">Invalid phone number format. Use 06-12345678 or 0612345678.</div>"""
        } else {
            """<div id="phone-number-error"></div>"""
        }
    }

    @GetMapping("/chatroom", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun chatroom(): Flux<ServerSentEvent<String>> {
        return Flux.interval(Duration.ofSeconds(1)) // Keeps emitting
            .map {  ServerSentEvent.builder<String>()
                .event("message")
                .data("<p>Hello from SSE at ${Instant.now()}</p>")
                .build() }
    }
}