package ru.devsokovix.investor

import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ru.devsokovix.investor.databinding.ActivityMainBinding
import kotlin.random.Random

fun getExRate(): Double {
    return 90.432
}

class MainActivity : AppCompatActivity() {
    lateinit var countDownTimer: CountDownTimer
    var flag: ObservableBoolean = ObservableBoolean(true)
    lateinit var observableUser: ObservableField<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val user1 = User(50_000, arrayListOf(Stock("Яндекс", 5000, R.drawable.iconyandex), Stock("Mail.ru Group", 2175, R.drawable.iconmail)))
        val user2 = User(12_000,arrayListOf(Stock("Mail.ru Group", 2175, R.drawable.iconmail),Stock("Яндекс", 5000, R.drawable.iconyandex)))
        observableUser = ObservableField(user1)
        binding.user = observableUser
        binding.exRate = getExRate()
        //Change this for test data binding
        observableUser.set(user2)
        binding.f = {
            flag.set(!flag.get())
            if (flag.get()) {
                countDownTimer.start()
            }else {
                countDownTimer.cancel()
            }
        }
        binding.flag = flag
        (binding.recyclerView.adapter) = observableUser.get()?.stockList?.let { StockAdapter(it) }
        val r = Random(System.currentTimeMillis())
        countDownTimer = object : CountDownTimer(10_000, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val u: User? = observableUser.get()
                if(u != null) {
                    u.cash += -1000 + r.nextInt(2000)
                    u.stockList.forEach {
                        it.price += (-0.1 * it.price).toInt() + r.nextInt((0.2 * it.price).toInt())
                    }
                }
            }

            override fun onFinish() {
                countDownTimer.start()
            }
        }
        countDownTimer.start()
    }
}
