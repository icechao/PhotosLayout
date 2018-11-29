#PhotosLayout

    图片展示的ViewGroup 当图片被拖动后会自动加入到ViewGroup的最后面一个,进行循环播放
  
##如果需要设置边框距离需要使用自定义属性 可在代码中设置

    命名空间使用: xnmls:xx = "http://schemas.android.com/apk/res-auto"

    xx:horizental_offset="15"

    xx:horizental_space="15"

    xx:show_count="4"

    xx:top_offset="15"

    xx:vertical_space="15"
##设置页面改变的监听
    setOnViewChangerListener(OnViewChangerListener listener)
    
###当子View个数为0时会抛出异常,
##默认
    每个子view向上下偏移15,
    横向左右偏移20,
    显示子View个数为4,
    左右上下边框距离为0
<iframe height=1280 width=720 src="https://github.com/icechao/PhotosLayout/blob/master/1.gif">
