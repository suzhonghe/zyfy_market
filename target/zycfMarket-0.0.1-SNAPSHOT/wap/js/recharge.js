//充值
;define(function(require,exports,module){
	require("./common.min.js");

	var oBankBg = document.querySelector(".recharge .bankBg");
	var oBankName = document.querySelector(".recharge .bankName");
	var oAccount = document.querySelector(".recharge .account");
	var oTimeLimit = document.querySelector(".recharge .timeLimit span");
	var oDailyLimit = document.querySelector(".recharge .dailyLimit span");
	var oRechargeMoney = document.querySelector(".recharge .rechargeMoney input");
	var oReallyMoney = document.querySelector(".recharge .reallyMoney");
	var oBtn = document.querySelector(".recharge .bottomBtn span");

	var flag = false;
	var rechargeRates = "";

	var getLoginStatus = require("./getLoginStatus.js");
	getLoginStatus(fnLink);
	function fnLink(obj){
		if( obj.userName && obj.userName!="" ){  //用户为登录状态
			getBankInfo();
		}else{                                   //用户为未登录状态
			window.location.href = "../login.html";
		};
	};
    
    oRechargeMoney.onfocus = function(){
    	flag = false;
    };
    function getBankInfo(){
    	var userInfo = Request(); 
		userInfo.ajax({	
			url:"/zycfMarket/mobileUserFundRecharge",  
			type:'post',  
			data:{}, 
			success:function(str){
				var obj = JSON.parse(str);
				oBankBg.className = "alignLeft setWidth bankBg "+obj.bankCoade;
				oBankName.innerHTML = obj.bankName;
				oAccount.innerHTML = obj.bankNum;
				oTimeLimit.innerHTML = obj.timeLimit;
				oDailyLimit.innerHTML = obj.dailyLimit;
				rechargeRates = obj.rechargeRates;
				oRechargeMoney.onblur = function(){
					if( !isNaN(this.value) && this.value!="" && this.value!="0" && parseFloat(this.value)>=parseFloat(obj.minrecharge) && parseFloat(this.value)<=parseFloat(obj.timeLimit*10000) ){
						oReallyMoney.innerHTML = (parseFloat(this.value)-parseFloat(obj.rechargeRates)).toFixed(2)+"元";
						flag = true;
					}else{
						var floatWindow = new PopUpBox();
						var content = "";
						if( this.value=="" || !!isNaN(this.value) || this.value=="0" ){
							content = "<p style='text-align:center'>请输入充值金额</p>";
						}else if( parseFloat(this.value)<parseFloat(obj.minrecharge) ){
							content = "<p style='text-align:center'>充值金额不能小于"+obj.minrecharge+"</p>";
						}else if( parseFloat(this.value)>parseFloat(obj.timeLimit*10000) ){
							content = "<p style='text-align:center'>充值金额不能大于"+obj.timeLimit*10000+"</p>";
						};
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
					}
				};
			}
		});
    };
	oBtn.onclick = function(){
		if( flag ){
			var rechargeAjax = Request(); 
			rechargeAjax.ajax({	
				url:"/zycfMarket/umpRecharge",  
				type:'post',  
				data:{"operatingLimit":oRechargeMoney.value,"sourceV":"HTML5","wap":"wap"}, 
				success:function(str){
					var obj = JSON.parse(str);
					if(obj.code == "USER_NOLOGIN"){
						var floatWindow = new PopUpBox();
						var content = "<p style='text-align:center'>未登录或登录状态已过期，请重新登录</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							content:content,     // 内容
							callback:function(){
								window.location.href = "../login.html";
							}
						});
					}else{
						setCookie('money', oRechargeMoney.value,{"path":"/zycfMarket"});
						setCookie('rates', rechargeRates,{"path":"/zycfMarket"});
						window.location.href = obj.prepareUmp;
					};
				}
			});
		}else{
			var floatWindow = new PopUpBox();
			var content = "<p style='text-align:center'>请输入正确充值金额</p>";
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
		}
	};
});
