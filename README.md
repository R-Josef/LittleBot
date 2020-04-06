# LittleBot

QQWebHook的GUI程序

## 使用方法

1. 前往[releases页面](https://github.com/R-Josef/LittleBot/releases)下载最新的发行版

2. 双击运行，第一次运行会在当前目录下创建key.txt文件

3. 在key.txt文件中填好qqwebhook的Key，格式为{名称}={KEY}，一行一个

4. 关闭并重新运行程序

## 特性

- 回车快速发送消息

- ctrl+回车输入换行符

- 可同时对多个key进行操作

## UI切换

默认的UI是Swing的，另外还可以选择JavaFx的UI。通过在启动时给参数就可以指定UI，例如：

`java -jar LittleBot.jar fx` 启用JavaFx的UI

`java -jar LittleBot.jar swing` 启用Swing的UI

Windows下可在程序同目录下新建文本文件，命名为`run.bat`，然后写入以下内容：

```
@echo off 　　
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin
java -jar LittleBot.jar fx
```

之后再双击`run.bat`就能以JavaFx版本的ui启动了。
