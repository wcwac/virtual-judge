
virtual-judge
=============

Holding contests using problems from other OJs!!  
[点这里跳到中文版](#修改说明)

## Changes

Comparing to [the original version](https://github.com/chaoshxxu/virtual-judge/tree/9dc0be82ed7e05dc17b7f042a935bf3db8435ce4) of virtual-judge source code, there are the following changes in this version:

- Fix the support for ACdream, Aizu, Codeforces, CSU, NBUT, POJ, SCU, SPOJ, URAL and UVA OJs
- Add support for CodeforcesGym, Tyvj and XTUOJ2
- Add support for crawling <del>and submitting</del> problems via [vjudge.net](https://vjudge.net/)
- Add support for MathJax
- Update language information of code submission
- Automatically download the PDF and image files of problem description to server so that clients can view them without the Internet access
- Other small changes


## How to deploy

- Click [here](https://github.com/chaoshxxu/virtual-judge/wiki/How-to-deploy-your-own-Virtual-Judge) to visit the official wiki page and follow the instructions.  
  - If you are unable to access it, you can click [here](https://github.com/hnshhslsh/virtual-judge-files/tree/master/original)  
  - Notice that since this version of virtual-judge add support for more OJs, you can add OJs called `CFGym`, `Tyvj`, `XTUOJ2` and `VJudge` in `remote_accounts.json` like [this](https://github.com/hnshhslsh/virtual-judge-files/blob/master/modified/remote_accounts.json) to provide accounts for them.  
  - As SGU has moved to be a part of Codeforces, **please fill in `SGU` with your Codeforces accounts**  
  - Please delete the useless proxies in the `http_client.json` file and **modify the `user_agent` field to remove the word `robot`** to avoid being blocked by Codeforces, like [this](https://github.com/hnshhslsh/virtual-judge-files/blob/master/modified/http_client.json)
- Or click [here](https://www.myblog.link/2017/01/09/VJudge-On-Windows-X64/) to visit the unofficial rapid deployment guide for Windows x64 (in Chinese).

## 修改说明

与 [原版](https://github.com/chaoshxxu/virtual-judge/tree/9dc0be82ed7e05dc17b7f042a935bf3db8435ce4) virtual-judge 源码相比, 该本版有以下修改：
- 修复对ACdream, Aizu, Codeforces, CSU, NBUT, POJ, SCU, SPOJ, URAL 和 UVA 这些OJ的支持
- 添加对CodeforcesGym、Tyvj以及XTUOJ2这些OJ的支持
- 添加通过 [vjudge.net](https://vjudge.net/)进行抓取<del>以及提交</del>题目的支持（选择VJudge作为OJ，然后填写类似于“HDU-1000”这种“OJ名-题目ID”格式的题目编号，就可以使用vjudge.net支持的所有OJ的题目）
- 添加对MathJax的支持（用于显示公式，默认使用互联网上的版本，需要离线请自行下载并更新`script.min.js`中的路径）
- 更新代码提交时可选的语言
- 自动下载题目描述的PDF文件以及图片到服务器，以便客户端在没有互联网的情况下浏览（题目使用PDF文档显示或者题面包含图片时把PDF或者图片文件离线到服务器上，客户端从VJ服务器上显示PDF及图片而不是原始OJ服务器，以供在只有服务器有互联网访问，客户端只有跟服务器的内网连接时可以正常使用，这是我本人的部署环境，一些代码会为此为目标环境而编写；通过vjudge.net添加的题目不会自动下载PDF）
- 其它小变化

## 部署说明

- **手动编译源码及配置环境**  
  点击 [这里](https://github.com/chaoshxxu/virtual-judge/wiki/How-to-deploy-your-own-Virtual-Judge) 访问官方wiki页面按照指令进行安装。  
  - 如果无法访问，可以点击[这里](https://github.com/hnshhslsh/virtual-judge-files/tree/master/original)  
  - 注意因为本版virtual-judge添加了对更多OJ的支持，所以你可以在`remote_accounts.json`文件中添加叫做`CFGym`, `Tyvj`, `XTUOJ2` 以及 `VJudge`的OJ，像[这样](https://github.com/hnshhslsh/virtual-judge-files/blob/master/modified/remote_accounts.json)，来给这些OJ提供账号。  
  - 由于SGU已迁移至Codeforces，**请在`SGU`中填写Codeforces账号**  
  - 请删除`http_client.json`文件中无用的代理（proxies），并且**修改`user_agent`字段去除`robot`字样**，以免被 Codeforces 拦截，像[这样](https://github.com/hnshhslsh/virtual-judge-files/blob/master/modified/http_client.json)

- **使用已编译的网站及配置好的环境**  
  点击 [这里](https://www.myblog.link/2017/01/09/VJudge-On-Windows-X64/) 访问非官方的Windows x64下的快速部署说明及资源下载。  
  其包含已编译的virtual-judge网站以及适用于Windows x64的JRE、Tomcat、MySQL、Redis等完整运行环境，可以在几分钟内完成部署。

