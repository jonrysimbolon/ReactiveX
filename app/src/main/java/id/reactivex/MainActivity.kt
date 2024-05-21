package id.reactivex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.reactivex.databinding.ActivityMainBinding
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val oddNumber = IntArray(3)
    private var firstIndex = 0

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Observable.just("1", "2", "3", "4", "5", "6")
            .map { string -> string.toInt() }
            .filter { number -> number % 2 == 1 }
            .doOnNext {
                oddNumber[firstIndex] = it
                firstIndex ++
                if(oddNumber.isNotEmpty()){
                    binding.prosesTxt.text = "${oddNumber.joinToString(",")} adalah bilangan ganjil"
                }
            }
            .count()
            .subscribe { result -> binding.hasilTxt.text = "Total bilangan ganjil : $result" }

    }

}