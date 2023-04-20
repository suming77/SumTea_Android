# SumTea

🚀 SumTea是一个基于**组件化+模块化+Kotlin+协程+Flow+Retrofit+Jetpack+MVVM+短视频**架构实现的WanAndroid客户端。能提供给初学者学习如何从0到1打造一个符合大型项目的架构模式。

|                             图片                             |                             图片                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![分类体系](https://user-images.githubusercontent.com/20841967/233252110-ebc4fdc0-132a-451a-8f27-29ea92052edf.gif) | ![分类体系](https://user-images.githubusercontent.com/20841967/233252110-ebc4fdc0-132a-451a-8f27-29ea92052edf.gif) |
| ![搜索](https://user-images.githubusercontent.com/20841967/233252130-59da489b-0595-4e8d-87cc-c9c1805ece1b.gif) | ![登录](https://user-images.githubusercontent.com/20841967/233252123-18ed07a7-89bc-42f8-a57d-7a1d4e388b09.gif) |


## 一、 关于SumTea

### 1. 项目架构
1. 项目采用 Kotlin 语言编写，结合 Jetpack 相关控件，`Navigation`，`Lifecyle`，`DataBinding`，`LiveData`，`ViewModel`等搭建的 **MVVM** 架构模式；
2. 通过**组件化**，**模块化**拆分，实现项目更好解耦和复用，[ARouter](https://github.com/alibaba/ARouter) 实现模块间通信；
3. 使用 **协程+Flow+Retrofit+OkHttp** 优雅地实现网络请求；
4. 通过 **mmkv**，**Room** 数据库等实现对数据缓存的管理；
5. 使用谷歌 **ExoPlayer** 实现短视频播放；
6. 使用 **Glide** 完成图片加载；
7. 通过 **WanAndroid** 提供的 API 实现的一款玩安卓客户端。

![SumTea工程架构](https://user-images.githubusercontent.com/20841967/233055330-89e2bdd6-3111-4726-bf5a-e77aa2ebdc2a.png)

项目使用**MVVM架构模式**，基本上遵循 Google 推荐的架构，对于 `Repository`，Google 认为 `ViewModel` 仅仅用来做数据的存储，数据加载应该由 `Repository` 来完成。通过 **Room** 数据库实现对数据的缓存，在无网络或者弱网的情况下优先展示缓存数据。

### 2. 收获

除去可以学到 **Kotlin + MVVM + Android Jetpack + 协程 + Flow + 组件化 + 模块化 + 短视频** 的知识，相信你还可以在我的项目中学到：

1.  **ConstraintLayout**的使用，几乎每个界面布局都采用的**ConstraintLayout**。
2.  符合阿里巴巴Java开发规范和阿里巴巴Android开发规范，并有良好的注释。
3.  如何使用**Chlars**抓包。
4.  利用 **ViewOutlineProvider** 给控件添加圆角，大大减少手写 shape 圆角xml。
5.  提供大量扩展函数，快速开发，提高效率。
6.  无论是模块化或者组件化，它们本质思想都是一样的，都是化整为零，化繁为简，两者的目的都是为了重用和解耦，只是叫法不一样。

### 3. 感谢

**API：**  鸿洋提供的 [**WanAndroid API**](https://www.wanandroid.com/blog/show/2)

**主要使用的开源框架:**

*   [**Retrofit**](https://github.com/square/retrofit)
*   [**OkHttp**](https://github.com/square/okhttp)
*   [**Glide**](https://github.com/bumptech/glide)
*   [**ARouter**](https://github.com/alibaba/ARouter)
*   [**MMKV**](https://github.com/Tencent/MMKV)
*   [**RxPermission**](https://github.com/tbruyelle/RxPermissions)
*   [**SmartRefreshLayout**](https://github.com/scwang90/SmartRefreshLayout)

## 二、Kotlin 协程学习三部曲

> [Kotlin 协程实战进阶(一、筑基篇)](https://juejin.cn/post/6987724340775108622)
- 协程的概念和原理、协程框架的基础使用、挂起函数以及挂起与恢复等
> [Kotlin 协程实战进阶(二、进阶篇)](https://juejin.cn/post/6992629783674748936)
- 协程的高级用法、Flow、Channel等
> [Kotlin 协程实战进阶(三、原理篇)](https://juejin.cn/post/7143386748783968292)
- 协程的底层原理：状态机，挂起与恢复，线程切换原理

分享不易，如果本项目对您有帮助，麻烦点个**Star**，您的**Star**将是我继续创作和写博客的动力！

欢迎在 **Issue** 中提交对本仓库的改进建议~

## 三、版权声明

* 所有原创文章(未进行特殊标识的均属于原创) 的著作权属于 **Sumiya**。
* 所有译文文章(标题注明`[译]`的所有文章) 的原文著作权属于原作者，译文著作权属于 **Sumiya**。

#### 注意事项

本项目API均来源于[**WanAndroid**](https://www.wanandroid.com)，禁止商用。

您可以在非商业的前提下免费转载，但同时您必须：

* 保持文章原文，不作修改。
* 明确署名，即至少注明 `作者：Sumiya` 字样以及文章的原始链接。
* 微信公众号转载一律不授权 `原创` 标志。

### About me

- #### 微信：`SUM_817`
- #### Email：`su_mingyan@163.com`
- #### Blog：[https://blog.csdn.net/m0_37796683/](https://blog.csdn.net/m0_37796683/)

如您有任何问题，也可以添加我的微信，进行学习上的交流。

感谢您的阅读~

## 赞赏

如果这个库对您有很大帮助，您愿意支持这个项目的进一步开发和持续维护。您可以扫描下面的二维码，打赏我一颗糖果或者一杯咖啡，非常感谢您的捐赠。祝您百尺竿头更进一步！

<div align="center">
<img src="https://github.com/FollowExcellence/Android-Core-Realm/blob/main/alpay.jpg" width=20%>
<img src="https://github.com/FollowExcellence/Android-Core-Realm/blob/main/wechatpay.jpg" width=20%>
</div>
