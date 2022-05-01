package com.jessy.foodmap.member.paging

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.databinding.FragmentCollectPagingBinding
import com.jessy.foodmap.home.HomeAdapter
import com.jessy.foodmap.home.HomeViewModel

class CollectPagingFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentCollectPagingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner


        var manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.collectRecyclerView.layoutManager = manager  //佈局管理
        manager.setAutoMeasureEnabled(true)
        binding.collectRecyclerView.setHasFixedSize(true)
        binding.collectRecyclerView.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.viewModel = viewModel

        viewModel.getFireBaseArticleCollect()

        viewModel.getAllArticlesCollectLiveData.observe(viewLifecycleOwner){
            Log.v("it","$it")
            (binding.collectRecyclerView.adapter as HomeAdapter).submitList(it)

        }

        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.homeToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )

//        val departments = listOf("甜點1號店", "天將甜機", "古代皇帝的甜食")
//
//        val classes = listOf(
//            listOf( "上班族有福了，不限時營業到晚上九點的漫拾完全是下班的好去處呀！店裡提供各式的甜點，尤其芋頭可頌更是" +
//                    "店內人氣必點商品，採用法國的奶油、烤的酥酥軟軟的可頌配上綿密的芋頭讓人一口接著一口欲罷不能！另外" +
//                    "編輯個人私心推薦他們家的榴槤千層，選用進口金枕頭榴蓮與法式香提結合內含超過60%的果肉每一口都能吃" +
//                    "到榴槤的香氣，另外抹茶跟芝麻口味的也很不賴，如果想拍照的朋友，他們家還有可愛的棉花糖可以讓你做陪襯喔！"),
//            listOf( "新竹超高人氣的咖啡廳 FOX.CONE 也推出了各式搭配的司康組合，" +
//                    "不管是早餐、下午茶、原味或者是全口味通通都有，現在透過波波選物所\b購買還能享有全台免運的優惠，物超" +
//                    "所值，不買對不起自己！讓發胖編最驚艷的是楓糖培根、抹茶和太妃檸檬，咬起來外酥內軟綿，完全不會有乾巴巴" +
//                    "的感覺，搭配香醇濃郁的味道，吃起來就非常滿足！"),
//            listOf("來自高雄年輕女孩的手作餅乾，打開鐵盒後完全是滿滿的驚喜，餅乾盒裡面非常豐富，" +
//                    "有人見人愛的檸檬糖霜、圓圓的厚餡夾心餅、充滿肉桂香氣的糖雪球等等，從配色到造型都可以看到闆娘的用心" +
//                    "，讓人不知如何下手，完全捨不得吃啊，其中最讓編輯驚艷的就是這款厚餡夾心餅，厚實的餡料加上精心設計配色" +
//                    "，簡直是甜點界的夢幻逸品！")
//        )
//
//
//        val ExpandableListView = binding.collectExpandableListView
//         val adapter = CollectPagingAdapter(activity as Activity, departments, classes)
//
//
//          ExpandableListView.setAdapter(adapter)


//        ExpandableListView.setOnGroupClickListener { parent, v, groupPosition, id ->
//            val departmentName = adapter.getGroup(groupPosition).toString()
//            Toast.makeText(this@MainActivity, departmentName, Toast.LENGTH_SHORT).show()
//
//            false
//        }
//
//        ExpandableListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
//            val departmentName = adapter.getGroup(groupPosition).toString()
//            val className = adapter.getChild(groupPosition, childPosition).toString()
//            val classTitle = "$departmentName$className"
//            Toast.makeText(this@MainActivity, classTitle, Toast.LENGTH_SHORT).show()
//
//            false
//        }

        return binding.root

    }

}