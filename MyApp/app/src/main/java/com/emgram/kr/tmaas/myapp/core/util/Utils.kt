package com.emgram.kr.tmaas.myapp.core.util

import android.view.View
import kotlinx.coroutines.GlobalScope

object Utils {
//    private fun View.onClick(action: suspend (View) -> Unit) {
//        // 5. 이 때 Higher-Order function 정의는 suspend가 포함되어야 함.
//        val event = GlobalScope.actor<View>(Dipatchers.Main) { // 1. Singletone의 GlobalScope 활용
//            for(event in channel) {						// 2. actor 이용 event 받기
//                action(event) // 4. 받은 event를 Higher-Order function으로 넘겨서 정의하도록 함.
//            }
//        }
//        setOnClickListener {
//            event.offer(it)								// 3. actor에 offer로 event 보내기
//        }
//    }
//    var currentIndex = 0
//    fab.onClick {
//        10.countDown(currentIndex++)				// 6. 람다 표현으로 countDonw 구현
//    }
}