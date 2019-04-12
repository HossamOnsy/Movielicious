package com.sam.movielicious.ui.moviedetails

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.bumptech.glide.Glide
import com.sam.movielicious.R
import com.sam.movielicious.databinding.ActivityMovieDetailsBinding
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.util.*


class MovieDetailsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {

        if (status === TextToSpeech.SUCCESS) {

            val result = t1?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported")
            } else {
                speakOut()
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    private fun speakOut() {
        t1?.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
    }

    private lateinit var binding: ActivityMovieDetailsBinding


    private lateinit var viewModel: MovieDetailsVM
    private var errorSnackbar: Snackbar? = null
    var t1: TextToSpeech? = null
    var movieTitle = " Movie "
    var msg = ""
    lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)


        if(savedInstanceState==null) {

            binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

            viewModel = ViewModelProviders.of(this).get(MovieDetailsVM::class.java)
            if (intent.hasExtra("id")) {
                viewModel.id = intent.getStringExtra("id")

            }
            if (intent.hasExtra("movieTitle")) {
                movieTitle = intent.getStringExtra("movieTitle")
            }
            viewModel.loadMovieDetails()

            binding.viewModel = viewModel
            msg = "Loading $movieTitle Data"
            dialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage(msg)
                .setCancelable(false)
                .build()

            initiateSpeaker();
            observing();
        }


    }

    private fun initiateSpeaker() {
        t1 = TextToSpeech(this, this)
    }

    private fun observing() {
        viewModel.loadingVisibility.observe(this, Observer { loadingVisibility ->
            if (loadingVisibility == View.VISIBLE) {
                dialog.show()

            } else {
                dialog.dismiss()
            }
        })

        viewModel.movieURL.observe(this, Observer { movieURL ->
            if (movieURL != null) showImage(movieURL)
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

    }

    private fun showImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(android.R.drawable.ic_menu_info_details)
            .into(poster_image)
    }


    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateFade(this);
    }
}
