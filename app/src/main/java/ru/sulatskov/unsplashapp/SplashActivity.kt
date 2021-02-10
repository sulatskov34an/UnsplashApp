package ru.sulatskov.unsplashapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import ru.sulatskov.unsplashapp.common.setStatusBarColor
import ru.sulatskov.unsplashapp.databinding.ActivitySplashBinding
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

class SplashActivity : AppCompatActivity(), CoroutineScope {
    lateinit var binding: ActivitySplashBinding
    private var backgroundThread: Thread? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setStatusBarColor(R.color.color_black_transparent50, this)
        val backgrounds = arrayOfNulls<Drawable>(3)
        backgrounds[0] = ContextCompat.getDrawable(this, R.drawable.gradient1)
        backgrounds[1] = ContextCompat.getDrawable(this, R.drawable.gradient2)
        backgrounds[2] = ContextCompat.getDrawable(this, R.drawable.gradient3)
        crossfade(binding.background, backgrounds, 10000)

        launch {
            delay(5000L)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun crossfade(image: ImageView?, layers: Array<Drawable?>, speedInMs: Int) {
        class BackgroundGradientThread(var mainContext: Context) : Runnable {
            override fun run() {
                val mHandler = Handler(mainContext.mainLooper)
                var reverse = false
                while (true) {
                    if (!reverse) {
                        for (i in 0 until layers.size - 1) {
                            val tLayers = arrayOfNulls<Drawable>(2)
                            tLayers[0] = layers[i]
                            tLayers[1] = layers[i + 1]
                            val tCrossFader = TransitionDrawable(tLayers)
                            tCrossFader.isCrossFadeEnabled = true
                            val transitionRunnable = Runnable {
                                image?.setImageDrawable(tCrossFader)
                                tCrossFader.startTransition(speedInMs)
                            }
                            mHandler.post(transitionRunnable)
                            try {
                                Thread.sleep(speedInMs.toLong())
                            } catch (e: Exception) {
                            }
                        }
                        reverse = true
                    } else if (reverse) {
                        for (i in layers.size - 1 downTo 1) {
                            val tLayers = arrayOfNulls<Drawable>(2)
                            tLayers[0] = layers[i]
                            tLayers[1] = layers[i - 1]
                            val tCrossFader = TransitionDrawable(tLayers)
                            tCrossFader.isCrossFadeEnabled = true
                            val transitionRunnable = Runnable {
                                image?.setImageDrawable(tCrossFader)
                                tCrossFader.startTransition(speedInMs)
                            }
                            mHandler.post(transitionRunnable)
                            try {
                                Thread.sleep(speedInMs.toLong())
                            } catch (e: Exception) {
                            }
                        }
                        reverse = false
                    }
                }
            }
        }

        backgroundThread = Thread(BackgroundGradientThread(this))
        backgroundThread?.start()
    }

}