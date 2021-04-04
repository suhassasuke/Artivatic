package com.wnet.artivatic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wnet.artivatic.R
import com.wnet.artivatic.adapter.RecyclerAdapter
import com.wnet.artivatic.data.api_model.Row
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var recyclerAdapter: RecyclerAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter = RecyclerAdapter(ArrayList())
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //refresh listener
        swipeLayout.setOnRefreshListener(this)

        initializeRecycler()
        loadData()
        initMvvm()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initMvvm() {
        viewModel.upgradeResult().observe(this, Observer {
            val data: ArrayList<Row> = arrayListOf()
            for (singleRow in it.rows){
                if(singleRow.title != null || singleRow.description != null || singleRow.imageHref != null){
                    data.add(singleRow)
                }
            }
            updateRecycler(data)

            //setting actionbar title
            requireActivity().setTitle(it.title)

            //disable refresh icon
            swipeLayout.isRefreshing = false
        })
    }

    private fun loadData() {
        viewModel.loadData()
    }

    fun updateRecycler(list: List<Row>){
        recyclerAdapter.addupdates(list)
        recycler.adapter = recyclerAdapter
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            layoutManager = gridLayoutManager
        }
    }

    override fun onRefresh() {
        recyclerAdapter.clear()
        loadData()
    }

}