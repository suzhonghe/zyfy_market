//实名认证
;define(function(require,exports,module){
	require("./common.min.js");

    var oRealName = document.querySelector(".identity .realName input");
    var oPeopleID = document.querySelector(".identity .peopleID input");
    var oBtn = document.querySelector(".identity .bottomBtn span");
    oBtn.onclick = function(){
    	if( oRealName.value != "" && oPeopleID.value != "" && checkChinese(oRealName.value) && checkCard(oPeopleID.value) ){
    		var reques = Request(); 
			reques.ajax({	
				url:"/zycfMarket/idCertificate",  
				type:'post',  
				data:{"name":oRealName.value,"idnumber":oPeopleID.value}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					var floatWindow = new PopUpBox();
			        var content = "";
					if(loginObj.code == 1){   // 实名验证成功
						content = "<p style='text-align:center'>实名认证成功</p>";
					}else{
						content = "<p style='text-align:center'>"+loginObj.message+"</p>";
					};
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				}
			});
    	}else{
    		var floatWindow = new PopUpBox();
	        var content = "";
	        if(oRealName.value == ""){
                content = "<p style='text-align:center'>请输入姓名</p>";
	        }else if( oPeopleID.value == "" ){
	        	content = "<p style='text-align:center'>请输入身份证号</p>";
	        }else if( !checkChinese(oRealName.value) ){
	        	content = "<p style='text-align:center'>请输入中文姓名</p>";
	        }else if( !checkCard(oPeopleID.value) ){
	        	content = "<p style='text-align:center'>请输入正确身份证号</p>";
	        };
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
    	};
    };
});
