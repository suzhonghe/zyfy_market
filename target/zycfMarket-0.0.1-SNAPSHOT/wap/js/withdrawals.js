//提现
;define(function(require,exports,module){
	require("./common.min.js");

	var oBankBg = document.querySelector(".withdrawalsPage .bankBg");
	var oBankName = document.querySelector(".withdrawalsPage .bankName");
	var oAccount = document.querySelector(".withdrawalsPage .account");
	var oAvailableAmount = document.querySelector(".withdrawalsPage .availableAmount strong");
	var oWithdrawalsAmount = document.querySelector(".withdrawalsPage .withdrawalsAmount input");
	var oWithdrawRates = document.querySelector(".withdrawalsPage .withdrawRates strong");
	var oBtn = document.querySelector(".withdrawalsPage .bottomBtn span");

	var oMin = document.querySelectorAll(".withdrawalsPage .msg em");

	var flag = false;
    var withdrawRates = "";
    
    oWithdrawalsAmount.onfocus = function(){
    	flag = false;
    };
    
    var withdrawalsInfo = Request(); 
	withdrawalsInfo.ajax({	
		url:"/zycfMarket/userFundWithdraw",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.code == "USER_NOLOGIN"){
	    		window.location.href = "../login.html";
	    	};
			oBankBg.className = "alignLeft setWidth bankBg "+obj.bankCoade;
			oBankName.innerHTML = obj.bank;
			oAccount.innerHTML = obj.account;
			oAvailableAmount.innerHTML = parseFloat(obj.availableAmount).toFixed(2);
			oWithdrawRates.innerHTML = parseFloat(obj.withdrawRates).toFixed(2);
			oMin[0].innerHTML = parseFloat(obj.withdrawRates).toFixed(2);
			oMin[1].innerHTML = parseFloat(obj.withdrawRates).toFixed(2);
			withdrawRates = obj.withdrawRates;
			oWithdrawalsAmount.onblur = function(){
				if( !isNaN(this.value) && this.value!="" && this.value!="0" && parseFloat(this.value)>=parseFloat(obj.withdrawRates) && parseFloat(this.value)<=1000000 && parseFloat(this.value)<=parseFloat(obj.availableAmount) ){
					flag = true;
				}else{
					var floatWindow = new PopUpBox();
					var content = "";
					if( this.value=="" || !!isNaN(this.value) || this.value=="0" ){
						content = "<p style='text-align:center'>请输入提现金额</p>";
					}else if( parseFloat(this.value)<parseFloat(obj.withdrawRates) ){
						content = "<p style='text-align:center'>提现金额不能小于"+obj.withdrawRates+"</p>";
					}else if( parseFloat(this.value)>1000000 ){
						content = "<p style='text-align:center'>提现金额不能大于1000000</p>";
					}else if( parseFloat(this.value)>parseFloat(obj.availableAmount) ){
						content = "<p style='text-align:center'>提现金额不能大于"+obj.availableAmount+"</p>";
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
	oBtn.onclick = function(){
		if( flag ){
			var withdrawalsAjax = Request(); 
			withdrawalsAjax.ajax({	
				url:"/zycfMarket/umpWithdraw",  
				type:'post',  
				data:{"operatingLimit":oWithdrawalsAmount.value,"sourceV":"HTML5","wap":"wap"}, 
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
						setCookie('money', oWithdrawalsAmount.value,{"path":"/zycfMarket"});
						setCookie('rates', withdrawRates,{"path":"/zycfMarket"});
						window.location.href = obj.prepareUmp;
					};
				}
			});
		}else{
			var floatWindow = new PopUpBox();
			var content = "<p style='text-align:center'>请输入正确提现金额</p>";
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
