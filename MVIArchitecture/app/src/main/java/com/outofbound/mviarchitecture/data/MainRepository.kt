package com.outofbound.mviarchitecture.data

import io.reactivex.Observable
import java.util.*

object MainRepository{

    fun loadHelloWorldText(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
        return messages[Random().nextInt(messages.size)]
    }
}
