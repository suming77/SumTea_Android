package com.sum.main.ui.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.sum.common.constant.KEY_VIDEO_PLAY_LIST
import com.sum.common.constant.VIDEO_ACTIVITY_PLAYER
import com.sum.framework.decoration.StaggeredItemDecoration
import com.sum.common.model.VideoInfo
import com.sum.framework.base.BaseMvvmFragment
import com.sum.framework.utils.dpToPx
import com.sum.main.R
import com.sum.main.databinding.FragmentHomeVideoBinding
import com.sum.main.ui.home.adapter.HomeVideoItemAdapter
import com.sum.main.ui.home.viewmodel.HomeViewModel
import java.util.ArrayList

/**
 * @author mingyan.su
 * @date   2023/3/5 20:11
 * @desc   首页视频列表
 */
class HomeVideoFragment : BaseMvvmFragment<FragmentHomeVideoBinding, HomeViewModel>() {

    override fun initView(view: View, savedInstanceState: Bundle?) {

        val list = mutableListOf<VideoInfo>()
        val info1 = VideoInfo(
            "鲜活波士顿龙虾",
            "鲜活进口大龙虾，美味可口……",
            "粤菜师傅",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic4.zhimg.com%2Fv2-36249b426ad7f246d14d51f8a9844016_1440w.jpg&refer=http%3A%2F%2Fpic4.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661767737&t=dfe5a7a908164f771dca7c33958f5302",
            "10"
        )
        val info2 =
            VideoInfo(
                "樱木花道和赤木晴子",
                "经典再现荧屏……",
                "灌篮高手",
                "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F11464763925%2F1000.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661749867&t=024e12db91dff8d30febc6581b36407b",
                "99"
            )
        val info3 = VideoInfo(
            "潮人全套床单",
            "新潮床单全场首发……",
            "拼多多",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimgservice.suning.cn%2Fuimg1%2Fb2c%2Fimage%2F_SJQcH4mkUDoox9K4QzwBg.jpg_800w_800h_4e&refer=http%3A%2F%2Fimgservice.suning.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661768265&t=e04f7f0b996b8828300d46d7aad15c50",
            "42"
        )
        val info4 = VideoInfo(
            "早餐营养吐司",
            "营养早餐牛奶吐司……",
            "江南皮革厂",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.alicdn.com%2Fbao%2Fuploaded%2Fi1%2F52353880%2FO1CN0182xqN91eX5I4APE7S-52353880.jpg&refer=http%3A%2F%2Fimg.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661768404&t=21d529ab25637dc1f1d40af384b00ec1",
            "48"
        )
        val info5 = VideoInfo(
            "大希地黑椒牛排",
            "性价比超高",
            "大希地集团",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fspic1.51fanli.net%2Fgroup2%2FM05%2F2E%2F55%2FwKgDiVuwQ92AIduUAAD6kE4X8TE479_350x350.jpg&refer=http%3A%2F%2Fspic1.51fanli.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661769184&t=679578a5bb99e34768dda7a611c25050",
            "86"
        )
        val info6 = VideoInfo(
            "进口波士顿龙虾",
            "俄罗斯进口……",
            "俄罗斯",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fphoto.tuchong.com%2F2355780%2Ff%2F271834918.jpg&refer=http%3A%2F%2Fphoto.tuchong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661767735&t=f00de5794aafca6503eb0c68e9b6dabd",
            "24"
        )
        val info7 = VideoInfo(
            "多功能不锈钢烤肉烧烤炉",
            "澳洲Chantelle多功能不锈钢烤肉烧烤炉能不锈钢烤肉烧烤炉",
            "澳大利亚",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "http://t15.baidu.com/it/u=4114745083,3646513740&fm=224&app=112&f=JPEG?w=500&h=500",
            "0"
        )
        val info8 = VideoInfo(
            "冰鲜波士顿龙虾",
            "拯救大虾(小龙虾)经典尝鲜装熟食素食串串方便自热火锅套装",
            "青岛",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://img1.baidu.com/it/u=2575507011,111795425&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=298",
            "22"
        )
        val info9 = VideoInfo(
            "蒜蓉波士顿龙虾",
            "清蒸",
            "盒马鲜生",
            "https://vdn1.vzuu.com/HD/c8af2fd6-438d-11eb-991f-da1190f1515e.mp4",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F503d269759ee3d6d9e33ec6348166d224e4adea5&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661767734&t=75f840434a94a0c42ba972f67920afea",
            "66"
        )
        list.add(info1)
        list.add(info2)
        list.add(info3)
        list.add(info4)
        list.add(info5)
        list.add(info6)
        list.add(info7)
        list.add(info8)
        list.add(info9)
        val spanCount = 2
        val manager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        val videoAdapter = HomeVideoItemAdapter(requireContext())
        mBinding?.recyclerView?.apply {
            layoutManager = manager
            addItemDecoration(StaggeredItemDecoration(dpToPx(10)))
            adapter = videoAdapter
        }
        videoAdapter.setData(list)

        videoAdapter.onItemClickListener = { view: View, position: Int ->
            ARouter.getInstance().build(VIDEO_ACTIVITY_PLAYER)
                    .withParcelableArrayList(KEY_VIDEO_PLAY_LIST, list as ArrayList<VideoInfo>)
                    .navigation()
        }
    }


}