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

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getEmployeeNames()
            .subscribeOn(Schedulers.io()) //mengirim data dilakukan dimana
            .observeOn(AndroidSchedulers.mainThread()) //menerima data dilakukan dimana
            .subscribe{
                //println(it)
                binding.hasilTxt.text = it
            }
    }

    private fun getEmployeeNames(): Flowable<String> {
        val name = mutableListOf("Buchori", "Dimas", "Tia", "Gilang", "Widy")
        return  Flowable.fromIterable(name) // untuk membuat Observable dari Array
    }
}