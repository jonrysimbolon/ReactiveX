package id.reactivex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.reactivex.databinding.ActivityMainBinding
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.currentThread

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val disposable1 = Observable.just("1", "2", "3", "4", "5", "6")
            .map { string -> string.toInt() }
            .filter { number -> number % 2 == 1 }
            .doOnNext { println("$it adalah bilangan ganjil") }
            .count()
            .subscribe { result -> binding.hasil1Txt.text = "Total bilangan ganjil : $result" }

        val disposable2 = getEmployeeNames()
            .subscribeOn(Schedulers.io())
            .doOnNext{ println("emitting in ${currentThread().name}") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { name ->
                binding.hasil2Txt.text = "$name (consuming in ${currentThread().name})"
            }

        disposables.addAll(disposable1, disposable2)
    }

    private fun getEmployeeNames(): Flowable<String> {
        val name = mutableListOf("Buchori", "Dimas", "Tia", "Gilang", "Widy")
        return Flowable.fromIterable(name)
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}