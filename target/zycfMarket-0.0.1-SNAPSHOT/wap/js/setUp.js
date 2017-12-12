//设置
;define(function(require,exports,module){
	require("./common.min.js");

	require("./loginOut.js");

	
    var oIdentInfo = document.querySelector(".setUp .identInfo");
    var oBankInfo = document.querySelector(".setUp .bankInfo");
	var setUpAjax = Request(); 
	setUpAjax.ajax({	
		url:"/zycfMarket/user/userSettings",  
		type:'post',  
		data:{}, 
		success:function(str){
			var obj = JSON.parse(str);
			if(obj.code == "USER_NOLOGIN"){
	    		window.location.href = "./../login.html";
	    	};
			if(!obj.idauthenticated){
				oIdentInfo.innerHTML = "";
				oBankInfo.parentNode.setAttribute("href","javascript:");
				oBankInfo.parentNode.onclick = function(){
					var floatWindow = new PopUpBox();
			        var content = "<p style='text-align:center'>请先实名认证</p>";
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				};
			}else{
				oIdentInfo.innerHTML = "<span class='marginRight'>"+obj.name+"</span><span>"+obj.idnumber+"</span>";
				oIdentInfo.parentNode.setAttribute("href","javascript:");
			};
			if(!obj.cardauthenticated){
				oBankInfo.innerHTML = "";
			}else{
				oBankInfo.innerHTML = obj.account;
			};
		}
	});
});
