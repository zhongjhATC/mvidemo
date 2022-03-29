package com.zhongjh.mvidemo.data

import io.reactivex.Observable
import java.util.Random

object HelloWorldRepository {

    fun loadHelloWorldText(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val messages = listOf("Hello World", "Hello Zhongjh", "Hallo Welt", "Bonjour le monde")
        return messages[Random().nextInt(messages.size)]
    }
}

