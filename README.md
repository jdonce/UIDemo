# UIDemo
主要是实践记录的一些UI效果demo，功能：指纹解锁、手势密码（设置手势密码和手势密码解锁）、新手引导、Lottie动画、移动按钮到指定区域进行验证、轮播图
# 说明：
1. 指纹解锁  功能在fingerprint目录下  使用的是系统提供的FingerprintManager

2. 手势密码 功能在gesture目录下  UI效果使用的是：https://github.com/sinawangnan7/GestureLockView  demo项目中是将相关代码复制到项目中(复制部分在目录gesture/gestureView)

3. 新手引导  功能在guideView目录下  使用的是：https://github.com/huburt-Hu/NewbieGuide  demo中将相关代码放在module的userguide中

4. 动画 1). 使用svg格式展示动画的库：https://github.com/geftimov/android-pathview  demo中使用在MainActivity
       2). 使用动画json格式展示的动画库：https://github.com/airbnb/lottie-android  demo中使用在animationView目录下
       其中json动画预览地址在：https://svgsprite.com/demo/bm/player.php?render=canvas
       json动画资源在：https://www.lottiefiles.com/

5. 移动按钮验证  功能在dragView目录下 主要监听touch事件

6. 轮播图切换   测试功能在bannerView目录,测试中有两种：1. 自定义 只实现了单一的轮播效果 只适合某个单一的场景 如指示器、轮播切换效果等不符合需要自行添加 ; 2. 引用第三方库 [banner](https://github.com/youth5201314/banner) 功能多样灵活
