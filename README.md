# better-elk

- elk
- knife4j





# 本地debug（编译）提示不通过，指的是找到lombok依赖包：
    
    具体查看： https://github.com/DemoMeng/better-skills/blob/master/SpringBoot%E5%90%88%E9%9B%86/SpringBoot%E5%BC%80%E5%8F%91%E9%81%87%E5%88%B0%E9%97%AE%E9%A2%98.md
    
    解决办法： 
        设置idea编译虚拟机参数 ： 
            Preferences -> Complier -> 加入参数：-Djps.track.ap.dependencies=false

