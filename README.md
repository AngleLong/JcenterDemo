# 本文知识点
- aar的一些基础知识
- Mac下搭建nexus私服
- Android Studio下使用nexus并上传aar
- 一些问题说明

## mac搭建nexus私服

### mac中nexus的安装
> 其中搭建步骤为:
> - 下载nexus的tgz文件
> - 安装相应的文件

#### 1. 下载nexus的tgz文件
> [nexus的下载地址](https://download.sonatype.com/nexus/3/latest-mac.tgz)可能下载有点缓慢,其实你也可以直接用迅雷下载,我就是这么下载的.然后解压到你需要的位置.

#### 2. 安装相应的tgz文件
> 解压相应的tgz文件,然后cd到相应的bin路径,执行以下命令

```
./nexus start
./nexus status
```

![22c883d753cf305692bec54b7a556165.png](https://github.com/AngleLong/JcenterDemo/images/image_1.png)

之后如果[http://localhost:8081/](http://localhost:8081/)能打开,说明启动成功了!

### nexus的使用
> 这个东西还是挺有意思的,登录之后你需要在sonatype-work/nexus3/admin.password这里的文件找到密码,打开设置一下!
理解了基本的概念,接下来我们就说说怎么用吧!

关于使用有一下几个步骤:
1. 要有一个类库,这个比较重要!
2. 配置相应类库的gradle(这里主要是篇日志相应的设置)
3. 上传相应的类库

#### 1. 创建一个类库(Android的类库)
> 其实这个比较简单,如果你是弄Android的话,应该不在话下.

#### 2. 在创建一个库(aar保存的库)
> 关于存储库的设置,其实就是用来保存类库的位置

![177b39a1bfae738f89fc82602be8eca3.png](https://github.com/AngleLong/JcenterDemo/images/image_2.png)

![3f54eed3896d4bbdf8eb2ae3ca5e4dc5.png](https://github.com/AngleLong/JcenterDemo/images/image_3.png)

其实就填写这一个就好了,其他的不用设置!

![d5f8478b0c954ba3f3c365c714b822a6.png](https://github.com/AngleLong/JcenterDemo/images/image_4.png)

然后你会在这里看到你创建的仓库了,之后就可以愉快的上传aar了,有木有很开森!!!


#### 2. 配置相应类库的gradle 
> 其实这个才是重头戏,因为所有关于maven的都是从这个配置中获取的.所以这个篇日志才是重点.

1. 在类库的gradle中添加
```
apply plugin: 'maven'
```

2. 设置存储库

3. 在文件末尾添加相应配置

```
uploadArchives {
    repositories {
        mavenDeployer {
            //配置用户名和密码
            repository(url: uri("http://localhost:8081/repository/maven-releases/")) {
                authentication(userName: 'admin', password: 'admin123')
            }
            pom.groupId = "com.angle.testlib" //随便写，但是一般为包名(这个会影响到之后引用的名称)
            pom.artifactId = "testLib" //随便写，但是一般为lib名字(这个会影响到之后引用的名称)
            pom.version = "1.0.1" //随便写，有点规律不好吗？
            pom.project {
                //设置许可证
                licenses {
                    license {
                        name 'The Apache Software License, Version 2.0'
                        url 'http://www.apache.org/licenses/LICENSE-2.0.txt'
                    }
                }
            }
        }
    }
}
```

| 仓库名 | 作用 |
| --- | --- |
| hosted(宿主仓库) | 存放本公司开发的版本(正式版,测试版) |
| proxy(代理仓库) | 代理中央仓库,Apache库 |
| group(组仓库) | 使用时连接组仓库,包含Hosted(宿主仓库)和Proxy(代理仓库) |
| virtual(虚拟仓库) | 基本用不到 |


![90d871a6f3d5db746f20308f1a0a8a07.jpeg](https://github.com/AngleLong/JcenterDemo/images/image_5.jpg)

看上面这张图就能明白上面说的一些问题了!

其实这里**repository**设置的地址,就是你创建库时候的地址
![35bf6533f2b06914f129de08a74869a8.png](https://github.com/AngleLong/JcenterDemo/images/image_6.png)
点这里就能看到地址了!

之后吧,就简单了! 

![2e609f0d8891924fea1bea545f42f2fe.png](https://github.com/AngleLong/JcenterDemo/images/image_7.png)

点一下就好了! 然后你会神奇的发现,你的仓库底下多了一个相应的类库.有了这个类库,我们就能使用maven管理相应的aar文件了!

## 使用aar文件
> 其实这里开始的时候我一直引用不到,以为自己写错了,但是后来我才发现,不是我写错了,是账号和密码没有设置!

在项目的build.gradle的里面添加
```
        maven {
            url 'http://localhost:8081/repository/maven-releases/'
            credentials {
                username 'admin'
                password 'admin123'
            }
        }
```

最开始的时候,我没有credentials这个内容,因为没有它所以就没有设置密码,没有设置密码引用不到也是正常的(其实是可以不用这么设置的!后面再说).然后直接来项目中引用就可以了!以上就是本地Nexus部署和使用!



## 一些问题说明
> 上面说过,关于设置用户名和密码的问题!其实是可以设置的!

![22469e537edd8b6680ab269fd7ca6266.png](https://github.com/AngleLong/JcenterDemo/images/image_8.png)

通过上面的设置,就可以不使用密码进行获取类库!

[关于nexus中的一些配置参考](https://blog.csdn.net/yingaizhu/article/details/83007747)

后续发生什么问题在进行添加
