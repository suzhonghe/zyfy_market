//注册
;define(function(require,exports,module){
	require("./common.min.js");
	var oFirst = document.querySelector(".register .first");
	var oSecond = document.querySelector(".register .second");
	var oThree = document.querySelector(".register .three");
	var oSuccess = document.querySelector(".register .success");

	var firstBtn = document.querySelector(".register .first .bottomBtn span");
	var getPho = document.querySelector(".register .first .user input");
	var oPassword = document.querySelector(".register .first .password input");
	var oReferee = document.querySelector(".register .first .referee input");

	var oReSend = document.querySelector(".register .second .reSend");
	var oNumber = document.querySelector(".register .second .number");
	var secondBtn = document.querySelector(".register .second .bottomBtn span");
	var oSmsCocd = document.querySelector(".register .second .smsCocd input");
	var oPho = document.querySelector(".register .second .pho");

	var oJunp = document.querySelector(".register .three header em");
	var oRealName = document.querySelector(".register .three .realName input");
	var oPeopleID = document.querySelector(".register .three .peopleID input");
	var threeBtn = document.querySelector(".register .three .bottomBtn span");

	var successBtn = document.querySelector(".register .success .bottomBtn span");

    var oRefereeVal = "";
	var flag = true;
	var referFlag = false;    //判断验证码通过与否
	var onlyPho = false;      //手机号唯一
	var timeOut = null;       //定时器

    getPho.onfocus = function(){
    	onlyPho = false;
    };
	getPho.onblur = function(){
		if(this.value == ""){
			return;
		};
		var reqOnlyPho = Request(); 
		reqOnlyPho.ajax({	
			url:"/zycfMarket/mobileRepeat",  
			type:'post',  
			data:{"mobile":getPho.value}, 
			success:function(str){
				var onlyPhoObj = JSON.parse(str);
		        if(onlyPhoObj.code != 0){
		        	var floatWindow = new PopUpBox();
	                var content = "<p style='text-align:center'>手机号码已存在</p>";
	                floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
		        }else{
		        	onlyPho = true;
		        };
			}
		});
	};

    if(flag){
    	firstBtn.onclick = function(){
    		flag = false;
            if(getPho.value == "" || oPassword.value == "" || !checkMobile(getPho.value) || !testNum(oPassword.value) ){
                var floatWindow = new PopUpBox();
			    var content = "";
			    if(getPho.value == ""){
			    	content = "<p style='text-align:center'>手机号或用户名不能为空</p>";
			    }else if( oPassword.value == "" ){
			    	content = "<p style='text-align:center'>密码不能为空</p>";
			    }else if( !checkMobile(getPho.value) ){
			    	content = "<p style='text-align:center'>请输入正确手机号</p>";
			    }else if( !testNum(oPassword.value) ){
			    	content = "<p style='text-align:center'>请输入6-16位数字与字母组合密码</p>";
			    };
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
				flag = true;
                return;
            };
            if( oReferee.value!="" && checkMobile(oReferee.value) ){
            	var req = Request(); 
				req.ajax({	
					url:"/zycfMarket/queryUserByParams",  
					type:'post',  
					async:false,
					data:{"mobile":oReferee.value}, 
					success:function(str){
						var loginObj = JSON.parse(str);
				        if(loginObj.code == 0){
				        	var floatWindow = new PopUpBox();
				            var content = "<p style='text-align:center'>"+loginObj.message+"</p>";
			                floatWindow.init({
								iNow:0,          // 确保一个对象只创建一次
								tBar:false,  
								time:1500,  
								content:content,     // 内容
								workBar:false
							});
				        }else if( loginObj.code == 1 ){
				        	referFlag = true;
				        };
					}
				});
            };
            if(referFlag){
            	oRefereeVal = oReferee.value;
            }else if(oReferee.value == ""){
            	oRefereeVal = "15311340737";
            }else{
            	flag = true;
            	return;
            };
            if(onlyPho){
            	oFirst.style.display = "none";
            	oSecond.style.display = "block";
            	oPho.innerHTML = getPho.value;
            	sendMessage();
            }else{
				var floatWindow = new PopUpBox();
		        var content = "<p style='text-align:center'>手机号还未在平台注册</p>";
				floatWindow.init({
					iNow:0,          // 确保一个对象只创建一次
					tBar:false,  
					time:1500,  
					content:content,     // 内容
					workBar:false
				});
            };
    	};
    };
    oReSend.onclick = function(){
    	sendMessage();
    };
    function sendMessage(){
    	var requ = Request(); 
		requ.ajax({	
			url:"/zycfMarket/mobolileCertWeb",  
			type:'post',  
			data:{}, 
			success:function(str){
				var loginObj = JSON.parse(str);
				clearInterval(timeOut);
				if(loginObj.code == 0){
					var num = 60;
					oNumber.style.display = "block";
					oNumber.innerHTML = num+"s";
					timeOut = setInterval(function(){
						num--;
						if(num <= 0){
							clearInterval(timeOut);
							oNumber.style.display = "none";
	                        oReSend.style.display = "block";
						}
						oNumber.innerHTML = num+"s";
					},1000);
				}else{
					var floatWindow = new PopUpBox();
			        var content = "";
			        if(loginObj.code == 1){
		                content = "<p style='text-align:center'>超出今日提交次数</p>";
			        }else if( loginObj.code == 2 ){
			        	content = "<p style='text-align:center'>操作太频繁了，请稍后</p>";
			        };
					floatWindow.init({
						iNow:0,          // 确保一个对象只创建一次
						tBar:false,  
						time:1500,  
						content:content,     // 内容
						workBar:false
					});
				};
			}
		});
    };
    
    secondBtn.onclick = function(){
    	var reg = /^[a-zA-Z0-9]+$/;
    	if(oSmsCocd.value == "" || !reg.test(oSmsCocd.value) ){
	        var floatWindow = new PopUpBox();
	        var content = "";
	        if(oSmsCocd.value == ""){
                content = "<p style='text-align:center'>短信验证码不能为空</p>";
	        }else if( !reg.test(oSmsCocd.value) ){
	        	content = "<p style='text-align:center'>请输入正确短信验证码</p>";
	        };
			floatWindow.init({
				iNow:0,          // 确保一个对象只创建一次
				tBar:false,  
				time:1500,  
				content:content,     // 内容
				workBar:false
			});
	        return;
	    }else{
    		var reque = Request(); 
			reque.ajax({	
				url:"/zycfMarket/register",  
				type:'post',  
				data:{"mobile":getPho.value,"passphrase":oPassword.value,"referralMobile":oRefereeVal,"regcode":oSmsCocd.value,"source":"wap"}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					if(loginObj.code == 0){   // 注册成功
						oSecond.style.display = "none";
						oThree.style.display = "block";
					}else{
						var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+loginObj.message+"</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
					};
				}
			});
	    };
    };

    oJunp.onclick = function(){
    	oThree.style.display = "none";
    	oSuccess.style.display = "block";
    };
    threeBtn.onclick = function(){
    	if( oRealName.value != "" && oPeopleID.value != "" && checkChinese(oRealName.value) && checkCard(oPeopleID.value) ){
    		var reques = Request(); 
			reques.ajax({	
				url:"/zycfMarket/idCertificate",  
				type:'post',  
				data:{"name":oRealName.value,"idnumber":oPeopleID.value}, 
				success:function(str){
					var loginObj = JSON.parse(str);
					if(loginObj.code == 1){   // 实名验证成功
						oThree.style.display = "none";
    	                oSuccess.style.display = "block"; 
    	                setTimeout(function(){
    	                	window.location.href = "./../index.html";
    	                },3000);   
					}else{
						var floatWindow = new PopUpBox();
				        var content = "<p style='text-align:center'>"+loginObj.message+"</p>";
						floatWindow.init({
							iNow:0,          // 确保一个对象只创建一次
							tBar:false,  
							time:1500,  
							content:content,     // 内容
							workBar:false
						});
					};
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

    successBtn.onclick = function(){
    	window.location.href = "./../index.html";
    };
});
