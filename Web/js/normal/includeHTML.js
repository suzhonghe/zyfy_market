;function fnHead(num){       // 头部html  
    var arr = [{"a":""},{"a":""},{"a":""},{"a":""},{"a":""},{"a":""}]; // 对应下面的5个li，num为0或为空表示都不选，第一项从1开始
	if(num){
		arr[num].a = "active";
	}; 
	var headContent = '<div class="wrap">\
		<div class="topBar clear">\
			<div class="topLeft">\
				<span>服务热线：</span><strong>400-6901-123</strong><span>服务时间：每工作日9:30-17:30</span>\
			</div>\
			<div class="topRight">\
				<span id="loginStatus" class="loginStatus"></span>\
				<a class="login" href="https://www.zuoyoufy.com/html/zyfyLogin.html" id="userName" >立即登录</a>\
				<a class="register" href="https://www.zuoyoufy.com/html/regsiter.html" id="userOut">快速注册</a>\
				<a class="AppDownload" href="javascript:"><span class="download">手机App</span><div class="QRcode">\
					<div class="alignLeft">\
					    <img src="https://www.zuoyoufy.com/img/App.png" alt="App" />\
						<p class="ipho">App下载</p>\
					</div>\
				</div></a>\
				<a href="https://www.zuoyoufy.com/html/activityZonePage/Introduction.html">关于我们</a>\
				<a href="https://www.zuoyoufy.com/html/activityZonePage/question.html">帮助中心</a>\
			</div>\
		</div>\
	</div>\
	<div class="logBar clear">\
		<a href="https://www.zuoyoufy.com/index.html"><img class="log" src="https://www.zuoyoufy.com/img/log.png" alt="log" /></a>\
		<ul class="headNav">\
			<li class="'+arr[1].a+'" >\
				<a href="https://www.zuoyoufy.com/index.html">首页</a>\
			</li>\
			<li class="'+arr[2].a+'" >\
				<a href="https://www.zuoyoufy.com/html/investment.html">我要投资</a>\
			</li>\
			<li class="'+arr[3].a+'" >\
				<a class="myAccount" heft="https://www.zuoyoufy.com/html/myAccount.html">我的账户</a>\
			</li>\
			<li class="'+arr[4].a+'" >\
				<a href="https://www.zuoyoufy.com/html/activityZonePage/safetyControl.html">安全保障</a>\
			</li>\
			<li class="'+arr[5].a+'" >\
				<a href="https://www.zuoyoufy.com/html/newPeople.html">新手指引</a>\
			</li>\
		</ul>\
	</div>';
	document.write( headContent );
	document.close();
};

function fnFoot(){     // 尾部html   
	var footContent = '<div id="footer">\
	        <div class="w1000">\
	                <div class="footerTop clear">\
	                    <ul class="left clear webMap">\
	                        <li class="left">\
	                            <h4>关于我们</h4>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/Introduction.html">公司介绍</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/team.html">运营团队</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/contactUs.html">联系我们</a></p>\
	                        </li>\
	                        <li class="left">\
	                            <h4>法律保障</h4>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/policiesAndRegulations.html">政策法规</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/safetyControl.html">安全保障</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/legalStatement.html">法律声明</a></p>\
	                        </li>\
	                        <li class="left">\
	                            <h4>常见问题</h4>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/question.html">关于投资</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/question.html">关于借款</a></p>\
	                            <p><a href="https://www.zuoyoufy.com/html/activityZonePage/question.html">充值提现</a></p>\
	                        </li>\
	                    </ul>\
	                    <div class="weixin left clear">\
	                        <div class="alignLeft appMargin">\
						        <img src="https://www.zuoyoufy.com/img/App.png" alt="App" />\
						        <p class="ipho">App下载</p>\
						    </div>\
	                        <div class="alignLeft">\
	                            <img src="https://www.zuoyoufy.com/img/weixin.jpg" alt="Android" />\
	                            <p class="android">微信</p>\
	                        </div>\
	                    </div>\
	                    <ul class="left info">\
	                        <li class="tel">400-690-1123</li>\
	                        <li class="qq">\
	                            <a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODAxNzk4OV8yMDg0MjZfNDAwNjkwMTEyM18yXw" target="_blank">\
	                                <div class="social-row qq">4006901123</div>\
	                            </a>\
	                        </li>\
	                        <li class="email"><a href="mailto:service@zhongyangcf.com">service@zhongyangcf.com</a></li>\
	                    </ul>\
	                </div>\
	                <div class="footerBottom">\
	                    <p class="firs">\
	                        <a href="http://www.zhongyangcf.com/" target="_blank">中阳财富</a>\
	                        <span class="splitor">|</span>\
	                        <a href="https://www.aliyun.com/" target="_blank">阿里云</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.umpay.com/" target="_blank">联动优势</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.king-capital.com/" target="_blank">京都律师事务所</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.gthx.cn/" target="_blank">国投弘鑫基金</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.dbsrzdb.com/" target="_blank">湖北大别山融资担保</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaizhijia.com/" target="_blank">网贷之家</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://csjj.union.china.com.cn/" target="_blank">中国网城市经济频道</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2pchina.com/" target="_blank">网贷中国</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.licaizhijia.com/" target="_blank">理财之家</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2p001.com/" target="_blank">第一网贷</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2pjd.com/" target="_blank">网贷经典</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.zongls.cn/" target="_blank">棕榈树</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2ptouhang.com/" target="_blank">网贷投行</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaijiamen.com/" target="_blank">网贷家门</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaitiandi.com/" target="_blank">网贷天地</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wd361.com/" target="_blank">网贷互联</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2p110.com" target="_blank">网贷110</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wdjhu.com" target="_blank">网贷江湖</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaidongtai.com" target="_blank">网贷动态</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wdlm.cn/" target="_blank">网贷联盟网</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wdtianxia.com/" target="_blank">网贷天下</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdai3.com/" target="_blank">网贷第三方</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaibuluo.com/portal.php" target="_blank">网贷部落</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.76676.com/" target="_blank">76676.com</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.01p2p.net/" target="_blank">第一网贷资讯</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.p2pwo.com/" target="_blank">网贷沃</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.hip2p.cn/" target="_blank">网贷通</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.naturepublic.com/" target="_blank">网贷乐园</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdainengliang.com/" target="_blank">网贷能量</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaishequ.cn/" target="_blank">网贷社区</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.jinydcn.com/" target="_blank">网贷专家</a>\
	                        <a href="http://www.naturepublic.com/" target="_blank">网贷乐园</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdainengliang.com/" target="_blank">网贷能量</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.wangdaishequ.cn/" target="_blank">网贷社区</a>\
	                        <span class="splitor">|</span>\
	                        <a href="http://www.jinydcn.com/" target="_blank">网贷专家</a>\
	                    </p>\
	                    <p class="mid">Copyright 2014 by 中阳财富投资控股有限公司 京ICP备14045649-2号</p>\
	                    <p class="las"><a href="https://seal.wosign.com/Signature/Profiles/bp/CN/b1e79843aaba4d52a0f4ef3078d1f3ea.htm?ref=aHR0cHM6Ly93d3cuenVveW91ZnkuY29t"  target="_blank" ><img src="https://www.zuoyoufy.com/img/footer_bg.jpg"/></a>\
	                        <a id="___szfw_logo___" href="https://credit.szfw.org/CX20150714010321370130.html" target="_blank" ><img src="https://www.zuoyoufy.com/img/chengxin.png" /></a>\
	                        <a href="http://www.itrust.org.cn/home/index/itrust_certifi/wm/1918794358.html"  target="_blank" ><img src="https://www.zuoyoufy.com/img/busnessP.png"/></a>\
	                    </p>\
	                </div>\
	        </div>\
	</div>';
	document.write( footContent );
	document.close();
};

function fnRigthFloat(){    // 右下角浮动窗，包括计算器（html）  
	var floatContent = '<ul class="floatWindow">\
		<li id="jishuqi" class="jishuqi"><span>理财计算</span></li>\
		<li class="qq"><a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODAxNzk4OV8yMDg0MjZfNDAwNjkwMTEyM18yXw" target="_blank">在线客服</a></li>\
		<li class="weixin"><span>关注微信</span><img src="https://www.zuoyoufy.com/img/weixin.jpg" alt="微信二维码" /></li>\
		<li class="backTop" id="backTop"><span>回到顶部</span></li>\
	</ul>\
	<div id="Calculator">\
		<h3 class="clear"><span>收益计算器</span><i id="calClose"></i></h3>\
		<form action="" method="get">\
			<div class="yuan"><input type="text" value="000.00"/><em>元</em><span class="yuanMsg">请输入数字！</span></div>\
			<div class="time"><input type="text" /><em class="mon">月</em><input type="text" /><em>%</em><span class="monMsg">请输</span><span class="rateMsg">请输入数字！</span></div>\
			<div class="option">\
				<p>一次性还本付息</p>\
				<ul>\
					<li>一次性还本付息</li>\
					<li>按月等额本息</li>\
					<li>按月付息到期还本</li>\
					<li>按月等额本金</li>\
					<li>月平息</li>\
				</ul>\
			</div>\
			<div class="btn clear"><input class="start" type="button" value="开始计算"/><input class="reset" type="reset" value="重置" /></div>\
		</form>\
		<p class="interest">合计利息：<em>0.00元</em></p>\
		<p class="principal">合计本息：<em>0.00元</em></p>\
	</div>';
	document.write( floatContent );
	document.close();
};

function accountNav(num){    //个人账户里面的nav列表
	var arr = [{"a":""},{"a":""},{"a":""},{"a":""},{"a":""},{"a":""},{"a":""},{"a":""},{"a":""}]; // 对应下面的8个li，num为0或空时，表示都没有active状态
	if(num){
		arr[num].a = "active";
	};
	var navContent = "<ul>\
			<li class='bg1 "+arr[1].a+"' ><span>账户总览</span></li>\
			<li class='bg2 "+arr[2].a+"' ><span>个人设置</span></li>\
			<li class='bg3 "+arr[3].a+"' ><span>已投项目</span></li>\
			<li class='bg4 "+arr[4].a+"' ><span>资金记录</span></li>\
            <li class='"+arr[5].a+"' ><span>借款管理</span></li>\
			<li class='bg5 "+arr[6].a+"' ><span>回款计划</span></li>\
			<li class='bg6 "+arr[7].a+"' ><span>邀请列表</span></li>\
			<li class='bg4 "+arr[8].a+"' ><span>我的福利</span></li>\
		</ul>"
	document.write( navContent );
	document.close();
};
