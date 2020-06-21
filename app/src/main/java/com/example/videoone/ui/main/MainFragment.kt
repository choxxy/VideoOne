package com.example.videoone.ui.main


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.videoone.R
import com.example.videoone.model.Video
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), ListCallBack {

    val mainViewModel: MainViewModel by viewModel()

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(videolist){
            adapter = VideoAdapter(this@MainFragment)
        }

        mainViewModel.videoList.observe(viewLifecycleOwner, Observer { list->
            (videolist.adapter as VideoAdapter).setVideos(list)
        })
    }

    override fun onItemClick(position: Int) {
           val intent = Intent(requireActivity(), VideoActivity::class.java).apply {
               putExtra(VideoActivity.MEDIA_INDEX,position)
           }
           startActivity(intent)
    }

}