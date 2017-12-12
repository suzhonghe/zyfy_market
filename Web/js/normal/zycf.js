var zycfZ = {
	name:"",
	exception:function(obj){
        if(obj.code){
            if(obj.code == "UNKNOW_EXCEPTION" || obj.code == "UNKNOW_NAME" || obj.code == "USETTLE_FAIL" || obj.code == "NO_UUSERINVEST" || obj.code == "NO_INFORMATIONLOAN" || obj.code == "PREPARE_DEPOSIT_FAILED" || obj.code == "FILE_READ_WRITE_EXCEPTION" || obj.code == "EN_CODE_MD5_EXCEPTION" || obj.code == "SERVER_REFUSED" || obj.code == "LOGIN_ERROR" || obj.code == "USER_NOT_EXISTS" || obj.code == "USER_PASSWORD_VAILD_FAILURE" || obj.code == "NOREGISTERED_UMPACCOUNT" || obj.code == "USER_NOLOGIN" || obj.code == "LOGIN_SUCCESS" ){
            	if(obj.message){
            		alert(obj.message);
            	};
            	return;
            }
        }
    },
    /*indexAcrivity:function(){
    	if(getCookie('at')){
			$(".indexActivity").hide();
		}else{
			$(".indexActivity").show();
		};
		$(".indexActivity .close").click(function(){
			setCookie('at', "1",{"path":"/"});
			$(this).parent().hide();
			return false;
		});
    },*/
	userName:function(fn,accountLink,loginLink){
		$.ajax({
			url: '/zycfMarket/loginStatus',
			type: 'post',
			data: {},
			success: function(str){
				zycfZ.exception(str);
				fn&&fn(str,accountLink,loginLink);
				if(str.userName!=null){
					name = str.userName;
					$("#loginStatus").html("你好,");
					$("#userName").attr("href","/zycfMarket/html/myAccount.html").removeClass("login").addClass("loginName").html(str.userName);
					$('#userOut').attr("href","javascript:void(0);").addClass("userOut").html("退出");
					$("#userOut").click(function(){
						$.ajax({
							url: '/zycfMarket/outLogin',
							type: 'post',
							data: {},
							success: function(str){
								zycfZ.exception(str);
								if(str.code==1){
									window.location.href="/zycfMarket/index.html";
								}
							}
						});
					});
					$('#userOut').removeClass("register");
				}
			}
		});
	},
	linkMyAccount:function(obj,accountLink,loginLink){
		$(".headNav .myAccount").click(function(){
			zycfZ.alertLogin(obj,accountLink,loginLink);
		});
	},
	myAccountLoginStatus:function(obj,accountLink,loginLink){
		//zycfZ.alertLogin(obj,accountLink,loginLink);
		if(obj.userName!=null){
			$(".headNav .myAccount").attr("href",accountLink);
		}else{
			$(".headNav .myAccount").attr("href","javascript:void(0);");
			window.location.href = "/zycfMarket/html/"+loginLink;
		}
	},
	alertLogin:function(obj,accountLink,loginLink){
		if(obj.userName!=null){
			$(".headNav .myAccount").attr("href",accountLink);
		}else{
			$(".headNav .myAccount").attr("href","javascript:void(0);");
			var content = "<p style='text-align:center;line-height:50px;font-size:14px;margin:20px 0;'>你还未登录，请先<a href="+loginLink+" style='color:blue;'>登录</a></p>";
			var addStaff = new PopUpBox();
			addStaff.init({
				w:300,
				h:200,
				iNow:0,          // 确保一个对象只创建一次
				opacity:0.7,
				content:content,
				makesure:false, 
	            cancel:false
			});
		}
	},
	// 首页新闻Tab
	newsImg:function(){
		function getImg(strImg,fn){
			$.ajax({
				url: '/zycfMarket/queryBannerPhotos',
				type: 'post',
				data: {"type":strImg},
				success: function(str){
					zycfZ.exception(str);
					fn && fn(str);
				}
			});
		};
		getImg("news",creatNewImg);
		getImg("advertisement",creatAD);
		function creatAD(obj){
			$(".banner .banner1").attr("src",obj[0].pathAddress);
			$(".banner .banner2").attr("src",obj[1].pathAddress);
		};
		function creatNewImg(obj){
			var len = (obj.length<=3)?obj.length:3;
			$("#newsDotList").html("");
			$("#newsImg").html("");
			for(var i=0;i<len;i++){
				var oA = $("<a href="+obj[i].jumpAddress+"><img src="+obj[i].pathAddress+" alt='新闻图片' /></a>");
				oA.appendTo("#newsImg");
				var litDot = $("<li></li>");
				litDot.appendTo("#newsDotList");
			};
			var iTime = null;
			var num = 0;
			$("#newsImg a").css({"opacity":0,"z-index":0}).eq(0).css({"opacity":1,"z-index":1});
			$("#newsDotList li").removeClass("active").eq(0).addClass("active");
			$("#newsDotList li").click(function(){
				$("#newsDotList li").removeClass("active").eq($(this).addClass("active"));
				$("#newsImg a").stop().eq($(this).index()).animate({"opacity":1,"z-index":1},600).siblings().animate({"opacity":0,"z-index":0},600);
			});
			function myTime(){
				iTime = setInterval(function(){
					num++;
					if(num>=len){
						num = 0;
					};
					$("#newsDotList li").removeClass("active").eq(num).addClass("active");
				    $("#newsImg a").stop().eq(num).animate({"opacity":1,"z-index":1},600).siblings().animate({"opacity":0,"z-index":0},600);
				},4000);
			};
			if(len>1){
				myTime();
				$("#newsImg").hover(function(){clearInterval(iTime)},function(){myTime()});    // 鼠标放上去停止动画
				$("#newsDotList").hover(function(){clearInterval(iTime)},function(){myTime()});  // 鼠标放上去停止动画
			};
		};
	},
	getTestBid:function(cb){
		$.ajax({
			type:"POST",
			url:"/zycfMarket/queryVirtualLoanOpened",
			data:{},
			success:function(str){
				cb && cb(str);
			}
		});
	},
	indexTestBid:function(){
		zycfZ.getTestBid(testBidData);
		function testBidData(obj){
			if(obj.code == 0){
				$(".testBid").hide();
			}else{
				$(".testBid").show();
				$(".testBid .rate strong").html(obj.data.rate);
				$(".testBid .times strong").html(obj.data.loanDay);
			}
		};
	},
	// 首页投标小喇叭
	bidList:function(){
		$.ajax({
			type:"POST",
			url:"/zycfMarket/queryByTime",
			data:{},
			success:function(str){
				zycfZ.exception(str);
				if(!str.length || str.length<1){
					$("#bidList").html("<li>暂无数据</li>");
				}else{
					var len = str.length;
					$("#bidList").height(len*40);
					for(var i=0;i<len;i++){
						var myHour = (Math.abs(str[i].hour)<24)?Math.abs(str[i].hour)+"小时前":"1天前";
						if(Math.abs(str[i].hour)==0){
							myHour = "1小时内"
						};
						var oLi = $("<li><span>"+str[i].name+"</span><span class='money'>"+str[i].amount+"</span><span>"+myHour+"</span></li>")
						oLi.appendTo($("#bidList"));
					};
					function autoPlay(){
						$("#bidList").stop().animate({top:H-80+"px"},(-H)*40,"linear",function(){
							$("#bidList").css({top:"0px"});
							setTimeout(function(){
							    autoPlay();
							},2000);
						});
					};
					var H = $("#bidList").parent().height()-$("#bidList").height();
					if(H<0){
						autoPlay();
						$("#bidList").hover(function(){
						    $("#bidList").stop()
						},function(){
							H = $("#bidList").parent().height()-$("#bidList").height();
							autoPlay();
						});
					}
				}
			}
		});
	},
	// 返回顶部
	backTop:function(){
		var oBackTop = document.getElementById('backTop');
		oBackTop.onclick = function(){
			var t = document.documentElement.scrollTop || document.body.scrollTop;
			var iTime = null;
			clearInterval(iTime);
			iTime = setInterval(function(){
				t-=20;
				if(t<=0){t=0};
			    document.documentElement.scrollTop = document.body.scrollTop =t;
			    if(t==0) clearInterval(iTime);
			},10);
		};
		window.onscroll = function(){
			var t = document.documentElement.scrollTop || document.body.scrollTop;
			if(t>100){
				oBackTop.style.display = 'block';
			}else{
				oBackTop.style.display = 'none';
			};
		};
	},
	// 计算器
	jishuqi:function(){
		var oCalculator = document.getElementById('Calculator');
		var oH3 = oCalculator.getElementsByTagName('h3')[0];
		drag(oH3,oCalculator);
		var isMoney = false,isMonth = false,isRate = false;
		$("#jishuqi").click(function(){
			$("#Calculator").show();
		});
		$("#calClose").click(function(){
			$("#Calculator").hide();
		});
		$("#Calculator .yuan input").focus(function(){
			$('#Calculator .yuanMsg').css('display','none');
			if($(this).val() == '000.00'){
				$(this).val('');
			};
		});
		$("#Calculator .yuan input").blur(function(){
			if($(this).val() == ''){
				$(this).val('000.00');
				$('#Calculator .yuanMsg').css('display','inline-block').html('不能为空！');
				isMoney = false;
			}else if( !isNaN( $(this).val() ) ){
				isMoney = true;
			}else if( isNaN( $(this).val() )){
				isMoney = false;
				$('#Calculator .yuanMsg').css('display','inline-block').html('请输入数字！');
			};
		});
		$("#Calculator .time input").eq(0).focus(function(){
			$('#Calculator .monMsg').css('display','none');
		});
		$("#Calculator .time input").eq(0).blur(function(){
			if($(this).val() == ''){
				$('#Calculator .monMsg').css('display','inline-block').html('不能为空！');
				isMonth = false;
			}else if( !isNaN( $(this).val() ) && parseInt( $(this).val() ) ==  parseFloat( $(this).val() ) ){
				isMonth = true;
			}else if( isNaN( $(this).val() ) ){
				isMonth = false;
				$('#Calculator .monMsg').css('display','inline-block').html('请输入数字！');
			}else if(parseInt( $(this).val() ) !=  parseFloat( $(this).val() )){
				isMonth = false;
				$('#Calculator .monMsg').css('display','inline-block').html('请输入整数！');
			};
		});
		$("#Calculator .time input").eq(1).focus(function(){
			$('#Calculator .rateMsg').css('display','none');
		});
		$("#Calculator .time input").eq(1).blur(function(){
			if($(this).val() == ''){
				$('#Calculator .rateMsg').css('display','inline-block').html('不能为空！');
				isRate = false;
			}else if( !isNaN( $(this).val() ) ){
				isRate = true;
			}else if( isNaN( $(this).val() )){
				isRate = false;
				$('#Calculator .rateMsg').css('display','inline-block').html('请输入数字！');
			};
		});
		$(".start").click(function(){
			if(isMoney && isMonth && isRate){
				var interest = parseFloat($("#Calculator .yuan input").val())*parseFloat($("#Calculator .time input").eq(0).val())*parseFloat($("#Calculator .time input").eq(1).val())/100/12;
				var principal = interest+parseFloat($("#Calculator .yuan input").val());
				$(".interest em").html(interest.toFixed(2));
				$(".principal em").html(principal.toFixed(2));
			}else if(!isMoney){
				$('#Calculator .yuanMsg').css('display','inline-block').html('请输入正确数字！');
			}else if(!isMonth){
				$('#Calculator .monMsg').css('display','inline-block').html('请输入正确数字！');
			}else if(!isRate){
				$('#Calculator .rateMsg').css('display','inline-block').html('请输入正确数字！');
			}
		});
		$(".reset").on("click",function(){
			isMoney=false;isMonth=false;isRate=false;
			$(".interest em").html("0.00元");
			$(".principal em").html("0.00元");
		});
		$('#Calculator .option p').click(function(){
			$('#Calculator .option ul').toggle();
			$(document).click(function(){
				$('#Calculator .option ul').hide();
			});
			return false;
		});
		$('#Calculator .option li').click(function(){
			$('#Calculator .option p').html($(this).html());
			$('#Calculator .option ul').hide();

		});
	},
	// 首页投资列表
	indexProjectList:function(){  // 首页产品和标的加载
		zycfZ.getProduct();
        zycfZ.getDataInvestment({
        	page:1,
        	num:5
        });
	},
	getProduct:function(){   // 动态获取产品项
		$.ajax({
             type:"POST",
             url:"/zycfMarket/queryAllProducts",
             data:{},
             dataType:"json",
             success:function(str){
             	zycfZ.exception(str);
             	$(".recommend ul").html("");
             	for(var i=0;i<str.length;i++){
             		productList(str[i]);
             	};
             }
        });
        function productList(obj){   // 动态加载产品项
        	if(obj.id == ''){
        		var oLi = $("<li class='on'><a code='' onclick='zycfZ.indexProjectSearch(this)' href='javascript:void(0);'>全部</a></li>");
        	}else{
        		var oLi = $("<li><a code="+obj.id+" onclick='zycfZ.indexProjectSearch(this)' href='javascript:void(0);'>"+obj.name+"</a></li>");
        	};
        	oLi.appendTo($(".recommend ul"));
        };
	},
	indexProjectSearch:function(This){   // 点击产品添加标的（分首页和列表页）
		$('.more').show();
		$(".projectList").html("");
		$(".paging").hide();
		$(".recommend ul li").removeClass("on");
		$(This).parent().addClass("on")
		var reg = new RegExp("investment.html");
		if(reg.test(window.location.href)){
			zycfZ.getDataInvestment({page:1,num:10,productId:$(This).attr('code')});
		}else{
			zycfZ.getDataInvestment({page:1,num:5,productId:$(This).attr('code')});
		};
		
	},
	getDataInvestment:function(opt){   // 标的数据获取
		var settings = {
			page:1,
			num:5,
			productId:""
		};
		for (var i in opt) {
			settings[i] = opt[i];
		};
		$(".projectList").html("");
		$.ajax({
             type:"POST",
             url:"/zycfMarket/investList",
             data:{"start":settings.page,
                  "length":settings.num,
                  "field":"productId",
                  "value":settings.productId},
             dataType:"json",
             success:function(str){
             	zycfZ.exception(str);
             	$(".projectList").html("");
             	$('.paging').html("").hide();
             	if(!str.data || !str.data.length || str.data.length <= 0){
					var reg = new RegExp("investment.html");
					if(reg.test(window.location.href)){
						$('.projectListWrap .noData').show().html("暂无数据");
						$('.more').hide();
					}else{
						$('.projectListWrap .noData').show().html("暂无数据");
					};
             	}else{
					var reg = new RegExp("investment.html");
					if(reg.test(window.location.href)){
						zycfZ.getData(str);
	             		if(settings.num == 20){
	             			if(str.totalPage>1){
	             				$('.paging').html("").show();
	             				page({
									id:'paging',
									nowNum:settings.page,
									allNum:str.totalPage,
									callBack:function(now,all){
										zycfZ.getDataInvestment({
								            page:now,
								            num:20,
								            productId:settings.productId
										});
									}
								});
	             			};
	             		}else if(settings.num == 10){
	             			if(str.data.length<10){
	             				$('.more').hide();
	             			};
	             		};
					}else{
						zycfZ.getData(str);
					};
             	};
             }
        });
	},
	getData:function(obj){   // 标的加载
		$('.projectListWrap .noData').hide();
 		for(var i=0;i<obj.data.length;i++){
 			getDataReader(obj.data[i]);
 		};
 		function getDataReader(arg){
 			var method,status,fnClick="javascript:void(0);",bgColor="bgColor";
 			var oDiv = $('<div class="timimg"></div>');
 			arg.oLi = $("<li class='one'></li>");
			if(arg.method=='MonthlyInterest'){
				method = '按月付息到期还本';
			}else if(arg.method=='EqualInstallment'){
				method = '按月等额本息';
			}else if(arg.method=='EqualPrincipal'){
				method = '按月等额本金';
			}else if(arg.method=='BulletRepayment'){
				method = '一次性还本付息';
			}else if(arg.method=='EqualInterest'){
				method = '月平息';
			};
			if(arg.addRate){
				arg.oLi.addClass("addRate");
			};
			if(arg.status=='SCHEDULED'){
				status = '开标倒计时';
				arg.t = parseInt(parseInt(arg.divTime)/1000);
	            if(arg.t<=0){
	            	arg.t = 0;
	            };
	            arg.countDown = Math.floor(arg.t/86400)+'天'+Math.floor(arg.t%86400/3600)+'时'+Math.floor(arg.t%86400%3600/60)+'分'+arg.t%60+'秒';
	            oDiv.html(arg.countDown);
	            arg.time = setInterval(function(){
			        arg.t-=1;
			        if(arg.t<=0){
			            arg.t=0;
			            clearInterval(arg.time);
			            setTimeout(function(){
			            	window.location.reload();
			            },1000);
			        };
			        arg.countDown = Math.floor(arg.t/86400)+'天'+Math.floor(arg.t%86400/3600)+'时'+Math.floor(arg.t%86400%3600/60)+'分'+arg.t%60+'秒';
			        arg.oLi.find(".timimg").html(arg.countDown);
			    },1000);
			}else if(arg.status=='OPENED'){
				status = '投资';
				fnClick = "zycfZ.toProjectDetails(this)";
				bgColor = "";
			}else if(arg.status=='FINISHED'){
				status = '已满标';
				fnClick = "zycfZ.toProjectDetails(this)";
			}else if(arg.status=='SETTLED'){
				status = '还款中';
				fnClick = "zycfZ.toProjectDetails(this)";
			}else if(arg.status=='CLEARED'){
				status = '已还清';
				fnClick = "zycfZ.toProjectDetails(this)";
			};
			if(!arg.planing){
				arg.planing=0;
			};
			arg.oLi.html("<div class='title'><span class='titleYin'>"+arg.productName+"</span><a class='titleContent' onclick='"+fnClick+"' code="+arg.loanId+" href='javascript:void(0);'>"+arg.loanName+"</a></div><div class='quanNianBottom clear'><div class='quanNianLeft'><div class='yearRates'><span>预计年化收益：<em>"+arg.rate+"%</em><em class='minSize'>"+(arg.addRate?'+'+arg.addRate+'%':'')+"</em></span><span>期限：<em>"+arg.months+"</em>个月</span><span class='schedule'>进度：&nbsp;<span class='scheduleBar'><span class='scheduleBarTop' style='width:"+Math.round(94*arg.planing/100)+"px"+"'></span></span>"+arg.planing+"%"+"</span></div><div class='total'><span>总额：<em>"+arg.amount/10000+"万"+"</em></span><span>还款方式：<em>"+method+"</em></span><span>剩余可投：<em>"+arg.suplusAmount+"元"+"</em></span></div></div><div class='quanNianRight "+bgColor+"' onclick="+fnClick+" code="+arg.loanId+">"+status+"</div></div>");
			oDiv.appendTo(arg.oLi);
			arg.oLi.appendTo($(".projectList"));
 		};
	},
	toProjectDetails:function(This){  // 首页和列表页跳转到标的详情页
		var reg = new RegExp("investment.html");
		if(reg.test(window.location.href)){
			window.location.href = "../html/account/investmentDetails.html#"+$(This).attr('code');
		}else{
			window.location.href = "html/account/investmentDetails.html#"+$(This).attr('code');
		};
	},
	// 我要投资页 查看更多
	inverMore:function(){
		zycfZ.getProduct();
		zycfZ.getDataInvestment({
            page:1,
            num:10
		});
		zycfZ.moreBtn();
	},
	moreBtn:function(){
		$('.more').click(function(){   // 点击更多，加载标的
			$(this).hide();
			zycfZ.getDataInvestment({
	            page:1,
	            num:20,
	            productId:$('.investment li.on').find('a').attr('code')
			});
		});
	},
	// 投资详情页
	investmentDetails:function(){
		var id = window.location.hash.substring(1);
		var money=0,flag=false,suplusAmount,rate,months,status,minAmount,stepAmount,availableAmount,isAdd = false;
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults .rechargeAgain").click();
		});
		$("#RechargeResults .success a").click(function(){
			$("#RechargeResults .rechargeAgain").click();
		});
		$("#RechargeResults .rechargeAgain").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$.ajax({
            type:"POST",
            url:"/zycfMarket/loanDetail",
            data:{"loanId":id},
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
             	var method;
             	suplusAmount = str.suplusAmount;
             	status = str.status
             	rate = str.rate;
             	months = str.months;
             	if(str.method=='MonthlyInterest'){
					method = '按月付息到期还本';
				}else if(str.method=='EqualInstallment'){
					method = '按月等额本息';
				}else if(str.method=='EqualPrincipal'){
					method = '按月等额本金';
				}else if(str.method=='BulletRepayment'){
					method = '一次性还本付息';
				}else if(str.method=='EqualInterest'){
					method = '月平息';
				};
             	$(".projectDetails .title").html("【"+str.productName+"】"+str.loanName);
             	$(".projectDetails .rate").html("<strong>"+str.rate+"%</strong>"+(str.addRate?'+'+str.addRate+'%':''));
             	$(".projectDetails .total strong").html(str.amount/10000);
             	$(".projectDetails .deadline strong").html(str.months);
             	$(".projectDetails .method .left em").html(method);
             	$(".projectDetails .method .right strong").html(str.suplusAmount);
             	$(".projectDetails .schedule .top").css("width",Math.round(98*str.planing/100)+"px");
             	$(".projectDetails .schedule .planing").html(str.planing+"%");
             	minAmount = str.minAmount || 100;
             	stepAmount = str.stepAmount || 100;
             	$("input[name='invesmentMoney']").val(minAmount+"元起投，递增金额"+stepAmount+"元");
             	if(str.addRate){
             		isAdd = false;
             	}else{
             		isAdd = true;
             	}
            }
        });
		$("input[name='invesmentMoney']").focus(function(){
 			if( $(this).val() == (minAmount+"元起投，递增金额"+stepAmount+"元") ){
 				$(this).val("");
 			};
 		});
 		$("input[name='invesmentMoney']").blur(function(){
 			if($(this).val() == ""){
 				$(this).val(minAmount+"元起投，递增金额"+stepAmount+"元");
 				$(".signInState .profits strong").html(0);
 			}else{
 				profit();
 			};
 			if(parseFloat($(this).val()) == $(this).val() && $(this).val()>=(parseFloat(minAmount)+parseFloat(stepAmount))){
 				$(".signInState .inputMoney .minus").css("background-position","0 -36px");
 			}else{
 				$(".signInState .inputMoney .minus").css("background-position","0 0px");
 			};
 		});
 		$("input[name='invesmentMoney']").on("change",function(){
 			$(".invesmentMoney .redPacketsBtn span").html("使用红包").attr("code","");
 		});
 		$(".signInState .inputMoney .minus").click(function(){
 			var n = 0;
 			n--;
 			if($("input[name='invesmentMoney']").val()<=minAmount){
 				return false;
 			};
 			changeMoney(n);
 		});
 		$(".signInState .inputMoney .plus").click(function(){
 			var n = 0;
 			n++;
 			changeMoney(n);
 		});
 		function changeMoney(n){
 			if($("input[name='invesmentMoney']").val() == "" || $("input[name='invesmentMoney']").val() == (minAmount+"元起投，递增金额"+stepAmount+"元") ){
 				$("input[name='invesmentMoney']").val("0");
 			};
 			money = parseFloat($("input[name='invesmentMoney']").val()) + n*parseFloat(minAmount);
 			if(money<0){
 				money=0;
 			};
 			$("input[name='invesmentMoney']").val(money);
 			if(parseFloat($("input[name='invesmentMoney']").val()) == $("input[name='invesmentMoney']").val() && $("input[name='invesmentMoney']").val()>=(parseFloat(minAmount)+parseFloat(stepAmount))){
 				$(".signInState .inputMoney .minus").css("background-position","0 -36px");
 			}else{
 				$(".signInState .inputMoney .minus").css("background-position","0 0px");
 			};
 			profit();
 			return false;
 		};
		$.ajax({
            type:"POST",
            url:"/zycfMarket/queryUserFundByUserId",
            data:{},
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
             	if(str.message.code == 0){  //未登录
             	}else if(str.message.code == 1){  //未认证
             		$(".signInState").hide();
             		$(".noSignIn").show();
             	}else if(str.message.code == 2){  //登录和认证完
             		flag=true;
             		availableAmount = str.availableAmount;
             		invesment(str);
             		getRedPackets(str);
             	};
            }
        });
        $(".invesmentMoney .redPacketsBtn").click(function(event){
        	event.stopPropagation();
        	if(!isAdd){
        		alert("红包不能与超指标同时使用！");
        		return false;
        	}
        	$(".invesmentMoney .redPacketsWrap").toggle();
        	$(document).click(function(){
        		$(".invesmentMoney .redPacketsWrap").hide();
        	});
        	if($(".invesmentMoney .redPacketsWrap").css("display") == "block"){
        		$(".invesmentMoney .redPacketsList li").each(function(){
        			$(this).unbind("click");
        			if( parseFloat($("input[name='invesmentMoney']").val()) >= parseFloat($(this).attr("lit")) && $("input[name='invesmentMoney']").val() != (minAmount+"元起投，递增金额"+stepAmount+"元") ){
        				$(this).css("cursor","pointer");
        				$(this).find(".setColor strong").css("color","#ff6f0a");
        				$(this).insertBefore($(".invesmentMoney .redPacketsList li").eq(0));
        				$(this).click(function(event){
        					$(".invesmentMoney .redPacketsBtn span").html($(this).attr("code")+"元").attr("code",$(this).attr("id"));
        					$(".invesmentMoney .redPacketsWrap").hide();
        					event.stopPropagation();
        					return false;
        				});
        			}else{
        				$(this).css("cursor","not-allowed");
        				$(this).find(".setColor strong").css("color","#999");
        				$(this).click(function(event){
        					event.stopPropagation();
        					return false;
        				});
        			};
        		});
        	};
        	return false;
        });
        function getRedPackets(){
        	$.ajax({
        		type:"POST",
	            url:"/zycfMarket/queryFreshAmounts",
	            data:{"loanId":id},
	            success:function(str){
	            	if(str.length && str.length>0){
	            		$(".invesmentMoney .profits").addClass("left");
	            		$(".invesmentMoney .redPacketsBtn").show();
	            		for (var i = 0,len=str.length; i < len; i++) {
	            			var oLi = $('<li class="clear" code="'+str[i].amount+'" id="'+str[i].id+'" lit="'+str[i].amountLimit+'"></li>');
	            			oLi.html('<div class="left">\
				                        <p class="setColor"><strong>￥'+str[i].amount+'</strong></p>\
				                        <p class="pdLeft">金额</p>\
				                    </div>\
				                    <div class="right">\
				                        <p>起投金额￥'+str[i].amountLimit+'</p>\
				                        <p>到期日'+new Date(parseInt(str[i].endTime,10)).toLocaleDateString()+'</p>\
				                    </div>');
	            			oLi.appendTo($(".redPacketsList"));
	            		};
	            	}
	            }
        	});
        };
        function invesment(obj){
        	$(".signInState .balance span").html(obj.availableAmount+"元"+"<a href='../account/recharge.html'>充值</a>");
    		if(obj.availableAmount<parseFloat(minAmount)){
    			$(".signInState .invesmentBtn input").css("background-color","#d9d9d9");
    		};
        };
        function profit(){
        	var profit = parseFloat($("input[name='invesmentMoney']").val())*rate*months/100/12;
        	$(".signInState .profits strong").html(profit.toFixed(2));
        };
        $(".signInState .invesmentBtn input").click(function(){   // 点击投资
        	var linkURL = null;
        	var investAmount = ($("input[name='invesmentMoney']").val() != (minAmount+"元起投，递增金额"+stepAmount+"元"))?parseFloat($("input[name='invesmentMoney']").val()):0;
        	if(status == "SCHEDULED"){
        		alert("未到开标时间，请持续关注！");
        	}else{
		    	if(flag==true &&investAmount%minAmount==0 && investAmount>=minAmount && investAmount<=suplusAmount && investAmount<=availableAmount){
		    		$.ajax({
			            type:"POST",
			            url:"/zycfMarket/invest",
			            data:{"investAmount":investAmount,"loan.id":id,"freshAmountId":$(".invesmentMoney .redPacketsBtn span").attr("code")},
			            dataType:"json",
			            async: false,
			            success:function(str){
			            	zycfZ.exception(str);
			            	if(str.code == "UNKNOW_EXCEPTION"){
			            		return;
			            	}else{
			            		$("#RechargeResults").show();
								linkURL = str.invest;
			            	};
			            }
			        });
		    	};
		    	if(flag==false){
	        		alert("用户未登录或未认证");
	        	}else if(investAmount<minAmount){
	        		alert("请输入大于"+minAmount+"的整数");
	        	}else if(investAmount%minAmount!=0){
	        		alert("请输入"+minAmount+"的倍数");
	        	}else if(investAmount>suplusAmount){
	        		alert("请输入小于剩余可投金额");
	        	}else if(investAmount>availableAmount){
	        		alert("你输入的金额已超出可用余额");
	        	};
        	};
        	if(linkURL){
				window.open(linkURL,'_blank');
			};
        });
        $.ajax({
            type:"POST",
            url:"/zycfMarket/queryProjectById",
            data:{"loanId":id},
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
             	$(".infoImg .infoImgList").html("");
             	$(".infoContent .resetMargin").html(str.firmInfo);
             	$(".infoContent .operationRange span").html(str.operationRange);
             	$(".infoContent .repaySource span").html(str.repaySource);
             	$(".infoContent .riskInfo span").html(str.riskInfo);
             	$(".infoContent .description span").html(str.description);
             	if(str.legalPersonPhotoUrl){
	            	var LPho = str.legalPersonPhotoUrl.split(",");
	            	for(var i=0;i<LPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+LPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.enterpriseInfoPhotoUrl){
	            	var EPho = str.enterpriseInfoPhotoUrl.split(",");
	            	for(var i=0;i<EPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+EPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.assetsPhotoUrl){
	            	var APho = str.assetsPhotoUrl.split(",");
	            	for(var i=0;i<APho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+APho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.contractPhotoUrl){
	            	var CPho = str.contractPhotoUrl.split(",");
	            	for(var i=0;i<CPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+CPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            if(str.othersPhotoUrl){
	            	var OPho = str.othersPhotoUrl.split(",");
	            	for(var i=0;i<OPho.length;i++){
	            		var oLi = $("<li><a href='javascript:void(0);'><img src="+OPho[i]+" alt='认证书' /></a></li>");
	            		oLi.appendTo($(".infoImg .infoImgList"));
	            	};
	            };
	            table();
            }
        });
        $.ajax({
            type:"POST",
            url:"/zycfMarket/queryInvestRecord",
            data:{"field":"loanId","value":id,"start":1,"length":999},
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
             	$(".record .recordInfo").html("");
             	$(".record .num strong").html(str.recordsTotal);
             	for(var i=0;i<str.data.length;i++){
             		creatTab(str.data[i]);
             	};
            }
        });
        function creatTab(obj){
        	var oLi = $("<li><span>"+obj.userName+"</span><span class='red'>"+"￥"+obj.amount+"</span><span>"+obj.time+"</span></li>");
        	oLi.appendTo($(".record .recordInfo"));
        };
		$('.invesmentInfo .infoTab li').click(function(){
			$('.invesmentInfo .infoTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.invesmentInfo .infoContent').show();
					$('.invesmentInfo .infoImg').hide();
					$(this).css('border-right','none');
				break;
				case 1:
				    $(this).css('border-left','1px solid #e4e4e4');
					$('.invesmentInfo .infoContent').hide();
					$('.invesmentInfo .infoImg').show();
				break;
			};
		});
		$('.safe_Problem .infoTab li').click(function(){
			$('.safe_Problem .infoTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.safe_Problem .safe').show();
					$('.safe_Problem .problem').hide();
					$(this).css('border-right','none');
				break;
				case 1:
				    $(this).css('border-left','1px solid #e4e4e4');
					$('.safe_Problem .safe').hide();
					$('.safe_Problem .problem').show();
				break;
			};
		});
		function table(){
			var num = 0;
			var w = $('.invesmentInfo .infoImg .infoImgList').find('li').outerWidth();
			var len = $('.invesmentInfo .infoImg .infoImgList').find('li').size();
			$(".infoImgList").css("width",len*w);
			$('.invesmentInfo .infoImg .prev').click(function(){
				num--;
				if(num<0){
					num = len-4;
				};
				$('.invesmentInfo .infoImg .infoImgList').stop().animate({
					left:-w*num
				},500);
			});
			$('.invesmentInfo .infoImg .next').click(function(){
				num++;
				num%=len-3;
				$('.invesmentInfo .infoImg .infoImgList').stop().animate({
					left:-w*num
				},500);
			});
			if(len){
				$('.infoImgList li img').each(function(){
					enlarge( $(this) );
				});
			};
			function enlarge(obj){
				obj.click(function(){
					var oDiv = $("<div class='bigImg'></div>");
					oDiv.html("<img src="+$(this).attr("src")+" alt='资质文件图片' /><i class='bigImgClose'>x</i>");
					oDiv.appendTo( $("body") );
					$(".bigImgClose").click(function(){
						$(this).parent().remove();
					});
				});
			};
		};
	},
	teste:function(){
        zycfZ.getTestBid(testBidData);
		function testBidData(obj){
			$(".testeDetail .rate strong").html(obj.data.rate);
			$(".testeDetail .total strong").html(obj.data.loanDay);
			if(obj.data.login){
				$(".loginStatus").hide();
				$(".testMoneyNum").show().html(obj.data.experienceAmount+"元");
				$(".profit strong").html( parseFloat(parseFloat(obj.data.experienceAmount)*parseFloat(obj.data.rate)*parseFloat(obj.data.loanDay)/36500).toFixed(2) );
				if(obj.data.experienceAmount > 0){
					$(".invesmentBtn input").click(function(){
						$.ajax({
							type:"POST",
							url:"/zycfMarket/investVirtualLoan",
							success:function(str){
								alert(str.message);
								if(str.code == 1){
									window.location.href = "https://www.zuoyoufy.com/";
								};
							}
						})
					});
				}else{
					$(".invesmentBtn input").css("background-color","#999");
				};
			}else{
				$(".invesmentBtn input").css("background-color","#999");
			}
		};
	},
	/* 从充值和提现页面到我的账户 */
	myAccountUrl:function(){
		var hash = window.location.hash.substring(1);
		if(hash){
			$('.rechargeMain .sidebarNav li').removeClass('active').eq(hash).addClass('active');
			$('.accountContent .Tabl').hide().eq(hash).show()
		};
	},
	// 注册页弹出框居中
	regsiterHeight:function(){
		var oPhoTestCon = $('#phoTest .phoTestCon');
		var oOpenSuccess = $('.openSuccess');
		var t = (document.documentElement.clientHeight-oPhoTestCon[0].offsetHeight)/2;
		var w = (document.documentElement.clientWidth-oPhoTestCon[0].offsetWidth)/2;
		var t1 = (document.documentElement.clientHeight-oOpenSuccess[0].offsetHeight)/2;
		var w1 = (document.documentElement.clientWidth-oOpenSuccess[0].offsetWidth)/2;
		oPhoTestCon[0].style.left = w+'px';
		oPhoTestCon[0].style.top = t+'px';
		oOpenSuccess[0].style.left = w1+'px';
		oOpenSuccess[0].style.top = t1+'px';
	},
	// 注册流程
	regsiterWorkFlow:function(){
		if(window.location.search){   //注册时判断地址栏是否有推荐人
			var refereeMobile = window.location.search.substring(1);
			var mobileArr = refereeMobile.split("=");
			if( checkMobile(mobileArr[1]) ){
				$('.referee input').val(mobileArr[1]);
				$('.referee input').focus();
			}
		};
		$("input[type!='button']").focus(function(){   //光标进入时，输入框边框颜色统一变红
			$(this).css('border-color','#ff6f0a');
		});
		$("input[type!='button']").blur(function(){    //光标离开时，输入框边框颜色统一变灰
			$(this).css('border-color','#ccc');
		});
        var btn = false;   //密码的眼睛是否开启
        var off = false;   //密码
        var phoValue = false;  //手机号码
        var ref = true;   //推荐人
        var timeOut = null;   //定时器
		$("input[name='pho']").focus(function(){
			$('.pho .msg').css('display','none');
			$('.pho .msgInfo').css('display','none');
			if($(this).val() == '手机号'){
				$(this).val('');
			};
		});
		$("input[name='pho']").blur(function(){
			if($(this).val() == ''){
				$(this).val('手机号');
				$('.pho .msgInfo').css('display','inline-block').html('手机号不能为空');
				phoValue = false;
			}else if( checkMobile($(this).val()) ){
				$('.pho .msg').css('display','inline-block');
				phoValue = true;
			}else if( !checkMobile($(this).val()) ){
				$('.pho .msgInfo').css('display','inline-block').html('请输入正确手机号码');
				phoValue = false;
			};
		});
		$(".password input[name='password']").focus(function(){
			$('.password .msg').css('display','none');
			$('.password .msgInfo').css('display','none');
			if($(this).val() == "6~16位数字与字母组合"){
				$(".password input[name='password1']").css("display","inline-block").focus();
			    $('.regsiterList .eyes').css('background-position','0 0');
			    btn = true;
			};
		});
		$(".password input[name='password']").blur(function(){
			if($(this).val() == ''){
				$('.password .msgInfo').css('display','inline-block').html('密码不能为空');
				off = false;
			}else if( testNum($(this).val()) ){
				$('.password .msg').css('display','inline-block');
				off = true;
			}else if(!testNum($(this).val())){
				$('.password .msgInfo').css('display','inline-block').html('请输入6~16位数字与字母组合');
				off = false;
			};
		});
		$(".password input[name='password']").change(function(){
			$("input[name='password1']").val($(this).val());
		});
		$(".password input[name='password1']").change(function(){
			$("input[name='password']").val($(this).val());
		});
		$("input[name='password1']").focus(function(){
			$('.password .msg').css('display','none');
			$('.password .msgInfo').css('display','none');
		});
		$("input[name='password1']").blur(function(){
			if($(this).val() == ''){
				$('.password .msgInfo').css('display','inline-block').html('密码不能为空');
				off = false;
			}else if( testNum($(this).val()) ){
				$('.password .msg').css('display','inline-block');
				off = true;
			}else if( !testNum($(this).val()) ){
				$('.password .msgInfo').css('display','inline-block').html('请输入6~16位数字与字母组合');
				off = false;
			};
		});
		$('.regsiterList .eyes').click(function(){
			if(!btn){
				$(this).css('background-position','0 0');
				if($("input[name='password']").val() == "6~16位数字与字母组合"){
					$("input[name='password1']").val("");
				}else{
					$("input[name='password1']").val($("input[name='password']").val());
				};
				$("input[name='password1']").css("display","inline-block").focus();
				btn = !btn;
			}else{
				$(this).css('background-position','0 -30px');
				if($("input[name='password1']").val() == ""){
					$("input[name='password']").val("6~16位数字与字母组合");
				}else{
					$(".password input[name='password']").val($("input[name='password1']").val());
				};
				$("input[name='password1']").css("display","none");
				$(".password input[name='password']").focus();
				btn = !btn;
			};
		});
		$('.referee input').focus(function(){
			$('.referee .msg').css("display","none");
			$('.referee .msgInfo').css("display","none");
			if($(this).val() == '选填'){
				$(this).val('');
			};
		});
		$('.referee input').blur(function(){
			if($(this).val() == ''){
				$(this).val('选填');
				ref = true;
			}else if( checkMobile($(this).val()) ){    // 验证推荐人手机号唯一
				$.ajax({	
					url:'/zycfMarket/queryUserByParams',  //请求路径
					type:'post',  //请求方式  get||post
					data:{"mobile":$('.referee input').val()},  //请求数据
					success:function(str){
						zycfZ.exception(str);
						if(str.code == 0){  // 用户不存在
							$('.referee .msgInfo').css("display","inline-block").html(str.message);
							ref = false;
						}else if(str.code == 1){
							$('.referee .msg').css("display","inline-block");
							ref = true;
						};
					}
				});
			}else{
				$('.referee .msgInfo').css("display","inline-block").html("请输入正确手机号码");
				ref = false;
			};
		});
		var vf = false;  //验证码
		$('.code input').focus(function(){
			$('.regsiterList .code .msg').css('display','none');
			$('.regsiterList .code .msgInfo').css('display','none');
			if($(this).val() == '右侧验证码'){
				$(this).val('');
			};
		});
		$('.code input').blur(function(){
			var re = /^\w{4}$/;
			if($(this).val() == ''){
				$(this).val('右侧验证码');
				$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码不能为空');
				vf = false;
			}else if( re.test($(this).val()) ){
				$.ajax({	
					url:'/zycfMarket/codeValalidate',  //请求路径
					type:'post',  //请求方式  get||post
					data:{"imgCode":$('.code input').val()},  //请求数据
					success:function(str){
						zycfZ.exception(str);
						if(str.code == 0){
							$('.regsiterList .code .msg').css('display','inline-block');
				    	    vf = true;   // 成功
						}else{
					    	$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码错误');
				            vf = false;
						};
					}
				});
			}else if( !re.test($(this).val()) ){
				vf = false;
				$('.regsiterList .code .msgInfo').css('display','inline-block').html('验证码格式错误');
			};
		});
        $('.regsiterList .code .Refresh').click(function(){
        	$('.regsiterList .code').find('img').attr('src','/zycfMarket/imgValalidate'+'?'+ Math.random());
        	$('.code input').val("");
        });
		$('.nextPage input').click(function(){
			if(off && phoValue && vf && ref && $("input[type='checkbox']").is(':checked')){
			    $.ajax({	
					url:'/zycfMarket/mobileRepeat',  //验证手机号唯一
					type:'post',  //请求方式  get||post
					data:{"mobile":$("input[name='pho']").val()},  //请求数据
					success:function(str){
						zycfZ.exception(str);
						if(str.code != 0){
							$('.pho .msg').css('display','none');
							$('.pho .msgInfo').css('display','inline-block');
							$('.pho .msgInfo').html('手机号码已存在');
					    }else{
					    	$('.pho .msg').css('display','inline-block');
					    	$('.pho .msgInfo').css('display','none');
					    	$('#phoTest').show();
			                zycfZ.regsiterHeight();   // 居中
			                var changeStar = $("input[name='pho']").val().replace(/(\d{3})\d{4}(\d{4})/,"$1****$2");
			                $('.phoForm .changStar').html(changeStar);
			                getMessage();
					    };
					}
				});
			}else if(!off){
				$('.password .msg').css('display','none');
				$('.password .msgInfo').css('display','inline-block');
				$('.password .msgInfo').html('密码格式错误或为空');
			}else if(!phoValue){
				$('.pho .msg').css('display','none');
				$('.pho .msgInfo').css('display','inline-block');
				$('.pho .msgInfo').html('手机号码错误或已注册');
			}else if(!vf){
				$('.regsiterList .code .msg').css('display','none');
		    	$('.regsiterList .code .msgInfo').css('display','inline-block');
	            $('.regsiterList .code .msgInfo').html('验证码错误或为空');
			}else if(!ref){
				$('.referee .msg').css("display","none");
				$('.referee .msgInfo').css("display","inline-block").html("手机号码错误或未注册");
			}else if($("input[type='checkbox']").is(':checked') == false){
				alert("请勾选协议，以便更好的为您服务")
			};
		});
		$('.TelInfo input').focus(function(){
			$('.phoForm .msg').css("visibility","hidden");
			if($(this).val() == '短信验证码'){
				$(this).val('');
			};
		});
		$('.TelInfo input').blur(function(){
			if($(this).val() == ''){
				$(this).val('短信验证码');
			};
		});
        $('.finishRegsiter input').click(function(){
    		if($("input[name='idnumber']").val() == '' || $("input[name='idnumber']").val() == '短信验证码'){
    			$('.phoForm .msg').html("请输入正确短信验证码").css("visibility","visible");
		    }else{
		    	sendInfo();
		    };
		    function sendInfo(){
		    	if($('.referee input').val() == '选填' || $('.referee input').val() == ''){
			    	$('.referee input').val('15311340737');
			    };
			    $.ajax({	
					url:'/zycfMarket/register',  //注册提交
					type:'post', 
					data:{
						"mobile":$("input[name='pho']").val(),
						"passphrase":$("input[name='password1']").val(),
						"referralMobile":$('.referee input').val(),
						"regcode":$("input[name='idnumber']").val(),
						"source":"web"
					},  //请求数据
					success:function(str){
						zycfZ.exception(str);
						if(str.code == 0){   // 注册成功
							$('#phoTest').hide();
							$('.regsiterList').hide();
							$('.open').show();
							$('.regsiterStep li').addClass('bg');
						}else{
							$('.phoForm .msg').html(str.message).css("visibility","visible");
						};
					}
				});
		    };
		});
		function getMessage(){
			$('.phoForm .reAbtain').css('display','none');
			$('.phoForm .reSend').css('display','inline-block');
			$.ajax({	
				url:'/zycfMarket/mobolileCertWeb',  //手机短信验证
				type:'post',
				data:{},
				success:function(str){
					zycfZ.exception(str);
					clearInterval(timeOut);
					if(str.code == 0){
						var num = 60;
						$('.phoForm .reSend').html(num+'秒后重新发送');
						timeOut = setInterval(function(){
							num--;
							if(num == 0){
								clearInterval(timeOut);
								$('.phoForm .reAbtain').css('display','inline-block');
								$('.phoForm .reSend').css('display','none');
							}
							$('.phoForm .reSend').html(num+'秒后重新发送');
						},1000);
					}else if(str.code == 2){
						$('.phoForm .msg').css("visibility","visible").html("时间间隔不足60秒");  
					}else if(str.code == 1){
						$('.phoForm .msg').css("visibility","visible").html("提交次数大于4次");  
					};
				}
			});
		};
		$('.phoForm .reAbtain').click(function(){   // 重新获取验证码
			getMessage();
		});
		var idCode = false;   //身份证号码验证
		$(".name input").focus(function(){
			$('.name .msg').css('display','none');
			$('.name .msgInfo').css('display','none');
		});
		$(".name input").blur(function(){
			if( $(this).val() == "" ){
				$('.name .msgInfo').css('display','inline-block').html("请输入中文姓名");
			}else if( checkChinese($(this).val()) ){
				$('.name .msg').css('display','inline-block');
			}else{
				$('.name .msgInfo').css('display','inline-block').html("请输入中文姓名");
			}
		});
		$("input[name='nameId']").focus(function(){
			$('.nameId .msg').css('display','none');
			$('.nameId .msgInfo').css('display','none');
			if($(this).val() == '请输入正确身份证号'){
				$(this).val('');
			};
		});
		$("input[name='nameId']").blur(function(){
			if($(this).val() == ''){
				$(this).val('请输入正确身份证号');
				$('.nameId .msgInfo').css('display','inline-block').html('请输入正确身份证号');
				idCode = false;
			}else if( checkCard($(this).val()) && zycfZ.eighteen($(this).val()) ){
				$('.nameId .msg').css('display','inline-block');
				idCode = true;
			}else if( !checkCard($(this).val()) ){
				$('.nameId .msgInfo').css('display','inline-block').html('您输入的证件号码或用户姓名有误，请重新输入');
				idCode = false;
			}else if( !zycfZ.eighteen($(this).val()) ){
				$('.nameId .msgInfo').css('display','inline-block').html('用户未满18岁，不能认证');
				idCode = false;
			};
		});
		var cardBtn = true;
		if(cardBtn){
			$('.openNow input').click(function(){
				if( idCode && checkChinese($(".name input").val()) ){
					cardBtn = false;
					$.ajax({	
						url:'/zycfMarket/idCertificate',  //实名验证
						type:'post',  //请求方式  get||post
						data:{"idnumber":$("input[name='nameId']").val(),
						    "name":$(".name input").val()
						},
						success:function(str){
							zycfZ.exception(str);
							cardBtn = true;
							if(str.code == 1){   // 实名验证成功
								$('.openSuccess').show();
								zycfZ.regsiterHeight();
								$('.open').hide();
								$('.regsiterSuccess').show();
								$('.regsiterStep li').removeClass('bg').addClass('bg1');
								var num = 3;
								setInterval(function(){
									num--;
									$('.regsiterSuccess .threeMin strong').html(num);
									if(num<=0){
										window.location.href = '../index.html';
									};
								},1000);          
							}else{
								$('.open .name .msgInfo').html(str.message).css("display","inline-block");
							};
						}
					});
				}else if(!idCode){
					$('.nameId .msgInfo').html('您输入的证件号码或用户姓名有误，请重新输入');
				}else if( !checkChinese($(".name input").val()) ){
					$('.nameId .msgInfo').css('display','inline-block').html('您输入的证件号码或用户姓名有误，请重新输入');
				};
			});
        }else{
        	alert("正在连接服务器，请稍后")
        };
		$('.openNow .jumped').click(function(){
			$('.open').hide();
			$('.regsiterSuccess').show();
			$('.regsiterStep li').removeClass('bg').addClass('bg1');
			var num = 3;
			setInterval(function(){
				num--;
				$('.regsiterSuccess .threeMin strong').html(num);
				if(num<=0){
					window.location.href = '../index.html';
				};
			},1000);
		});
	},
	goBack:function(){  
	    if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){ // IE  
	        if(history.length > 0){  
	            window.history.go( -1 );  
	        }else{  
	            window.location.href = '../index.html'  
	        }  
	    }else{ //非IE浏览器  
	        if (navigator.userAgent.indexOf('Firefox') >= 0 ||  
	            navigator.userAgent.indexOf('Opera') >= 0 ||  
	            navigator.userAgent.indexOf('Safari') >= 0 ||  
	            navigator.userAgent.indexOf('Chrome') >= 0 ||  
	            navigator.userAgent.indexOf('WebKit') >= 0){  
	  
	            if(window.history.length > 1){  
	                window.history.go( -1 );  
	            }else{  
	                window.location.href = '../index.html' 
	            }  
	        }else{ //未知的浏览器  
	            window.history.go( -1 );  
	        }  
	    }  
	},
	/* 我的账户 */
	myAccount:function(){
		zycfZ.userName(zycfZ.myAccountLoginStatus,"myAccount.html","zyfyLogin.html");
		$('.userFrame .user span').html("");
		$('.myAccountInfo .prevLogin').html("");
		$.ajax({
            type:"POST",
            url:"/zycfMarket/userAccountInfo",
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
				$('.myAccountInfo .money strong').html(str.availableAmount);   //现金余额
				$('.myAccountInfo .allAssets strong').html(str.allCapital);   //总资产
				$('.myAccountInfo .allProfit strong').html(str.allRevue);   //总收益
				$('.myAccountInfo .toProfit span').html(str.undueAmountInterest);   //待收收益
				$('.myAccountInfo .toPrincipal span').html(str.undueAmountCapital);   //待收本金
				$('.myAccountInfo .frozen span').html(str.frozenAmount);   //冻结金额
				$('.myAccountInfo .testMoney span').html(str.freshAmount);   //冻结金额
			}
        });
        $.ajax({
            type:"POST",
            url:"/zycfMarket/user/userSettings",
            dataType:"json",
            success:function(str){
            	if(str.code == "USER_NOLOGIN"){
            		window.location.href = "zyfyLogin.html";
            	};
            	$('.myAccountInfo .prevLogin').html("上次登陆时间："+str.strLastLoginDate);
				if(!str.idauthenticated){
					$(".userFrame .one").removeClass("active").find("a").html("未认证");
					$(".personalSetting .idauthenticated .state").removeClass("yes").html("未认证");
					$(".personalSetting .idauthenticated .text").html("");
					$(".personalSetting .idauthenticated .identityBtn").show();
					$('.userFrame .user span').html("您好，"+str.mobile+"！");
					$('.rechargeBtn').css("background-color","#a5a5a5").attr("title","请先实名认证");
					$(".personalSetting .bankBtn").css("background-color","#a5a5a5").attr("title","请先实名认证");
				}else{
					$(".userFrame .one").addClass("active").find("a").html("已认证");
					$(".personalSetting .idauthenticated .state").addClass("yes").html("已认证");
					$(".personalSetting .idauthenticated .text").html(str.name+"("+str.idnumber+")");
					$(".personalSetting .idauthenticated .identityBtn").hide();
					$('.userFrame .user span').html("您好，"+str.name+"！");
					/* 充值、提现 */
					$('.rechargeBtn').css("background-color","#ff9f16").attr("title","");
					$('.rechargeBtn').click(function(){
			            window.location.href = '../html/account/recharge.html';
					});
					$(".personalSetting .bankBtn").css("background-color","#ff9f16").attr("title","");
					$(".personalSetting .bankBtn").click(function(){
						window.location.href = '../html/account/bank.html'
					});
				};
				if(!str.emailauthenticated){
					$(".userFrame .four").removeClass("active").find("a").html("未绑定");
					$(".personalSetting .emailBar .state").removeClass("yes").html("未绑定");
					$(".personalSetting .emailBar .text").html("");
				}else{
					$(".userFrame .four").addClass("active").find("a").html("已绑定");
					$(".personalSetting .emailBar .state").addClass("yes").html("已绑定");
					$(".personalSetting .emailBar .text").html(str.email);
					$(".personalSetting .emailBar .emailBtn").val("修改");
				};
				if(!str.mobileauthenticated){
					$(".userFrame .tow").removeClass("active").find("a").html("未绑定");
					$(".personalSetting .mobileBar .state").removeClass("yes").html("未绑定");
					$(".personalSetting .mobileBar .text").html("");
				}else{
					$(".userFrame .tow").addClass("active").find("a").html("已绑定");
					$(".personalSetting .mobileBar .state").addClass("yes").html("已绑定");
					$(".personalSetting .mobileBar .text").html(str.mobile);
					$(".personalSetting .mobileBar .mobileBtn").val("修改");
				};
				if(!str.cardauthenticated){
					$(".userFrame .three").removeClass("active").find("a").html("未绑定");
					$(".personalSetting .bankCard .state").removeClass("yes").html("未绑定");
					$(".personalSetting .bankCard .text").html("");
					$('.withdrawalsBtn').css("background-color","#a5a5a5").attr("title","请先绑定银行卡");
					$(".agreementBtn").css("background-color","#a5a5a5").attr("title","请先绑定银行卡");
				}else{
					$(".userFrame .three").addClass("active").find("a").html("已绑定");
					$(".personalSetting .bankCard .state").addClass("yes").html("已绑定");
					$(".personalSetting .bankCard .text").html(str.bank+"("+str.account+")"); 
					$('.withdrawalsBtn').css("background-color","#289dff").attr("title","");
					$('.withdrawalsBtn').click(function(){
						window.location.href = '../html/account/withdrawals.html'
					}); 
					$(".agreementBtn").click(function(){
				    	window.location.href = "account/agreement.html";
				    });
				    $(".personalSetting .bankBtn").val("更换");  //银行卡更换
				};
				if(str.debit || str.instant || str.invest || str.repay){
					$(".agreement .text").html("已签定").addClass("yes");
					$(".agreementBtn").val("修改签订");
				};
				/*if(str.instant || str.debit){
					$(".personalSetting .bankBtn").hide();
				};*/
				if(!str.loginname || str.loginname == ""){
					$(".personalSetting .userBar .state").removeClass("yes").html("未设置");
					$(".personalSetting .userBar .text").val("");
					$(".personalSetting .userBar .userBtn").show();
					$(".personalSetting .userBar .text").attr("disabled",false).css({"border":"1px solid #dcdcdc"});
					$(".personalSetting .userBar .userBtn").click(function(){
						if($(".personalSetting .userBar .text").val() != "" && checkUserName($(".personalSetting .userBar .text").val())){
							$.ajax({
					            type:"POST",
					            url:"/zycfMarket/user/updateUserName",
					            data:{"userName":$(".personalSetting .userBar .text").val()},
					            dataType:"json",
					            success:function(str){
					            	zycfZ.exception(str);
					            	if(str.code == 0){
					            		alert(str.message);
					            		window.location.reload();
					            	}else if(str.code == 1){
					            		alert(str.message);
					            	}else{
					            		alert("用户名不能设置为空");
					            	}
								}
					        });
						}else if($(".personalSetting .userBar .text").val() == ""){
							alert("请输入用户名");
						}else if($(".personalSetting .userBar .text").val().length<2 || $(".personalSetting .userBar .text").val().length>30){
							alert("用户名长度不在2-30字符之间");
						}else if(!illegalCharactors($(".personalSetting .userBar .text").val())){
							alert("用户名不正确，只能包含字母、数字或下划线,以字母或下划线开头");
						}else if(!isNaN($(".personalSetting .userBar .text").val().charAt(0))){
							alert("用户名不能以数字开头,只能包含字母或下划线开头");
						};
					});
				}else{
					$(".personalSetting .userBar .state").addClass("yes").html("已设置");
					$(".personalSetting .userBar .text").val(str.loginname);
					$(".personalSetting .userBar .text").attr("disabled","disabled").css({"background-color":"#fff","border":"none"});
					$(".personalSetting .userBar .userBtn").hide();
				};
				$(".InviteRebateBar #copyInfo").val("https://www.zuoyoufy.com/html/regsiter.html?ref="+str.mobile);    //推广地址
				$(".personalSetting .referralId .text").html(str.referralRealm);   // 推荐人
				window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdDesc":"","bdUrl":"https://www.zuoyoufy.com/html/regsiter.html?ref="+str.mobile,"bdSign":"off","bdMini":"2","bdMiniList":false,"bdSize":"16"},"share":{},"bdStyle":"0"};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='https://www.zuoyoufy.com/zycfMarket/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
			}
        });
		$(".servicesTab li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
			myAward();
		});
		$(".beUsed li").click(function(){
			$(this).addClass("active").siblings().removeClass("active");
			myAward();
		});
		function myAward(){
			if($(".servicesTab li").eq(0).hasClass("active") && $(".beUsed li").eq(0).hasClass("active")){
				$(".testBidWrap ul").hide().eq(0).show();
				$(".redPacketsWrap ul").hide();
			}else if($(".servicesTab li").eq(0).hasClass("active") && $(".beUsed li").eq(1).hasClass("active")){
				$(".testBidWrap ul").hide().eq(1).show();
				$(".redPacketsWrap ul").hide();
			}else if($(".servicesTab li").eq(1).hasClass("active") && $(".beUsed li").eq(0).hasClass("active")){
				$(".testBidWrap ul").hide();
				$(".redPacketsWrap ul").hide().eq(0).show();
			}else if($(".servicesTab li").eq(1).hasClass("active") && $(".beUsed li").eq(1).hasClass("active")){
				$(".testBidWrap ul").hide();
				$(".redPacketsWrap ul").hide().eq(1).show();
			}
		};
		$(".servicesBar .ruleDescription").click(function(){  //红包和体验标使用规则显示隐藏
			$(".servicesBar .ruleDetails").show();
		});
		$(".ruleDetails .close").click(function(){
			$(".servicesBar .ruleDetails").hide();
		});
		accountListAjax({     // 账户总览里的最近投资项目
			page:1,
		    num:5,
		    url:"/zycfMarket/userInvestLoanList",
		    callBack:function(obj){investLoanZ(obj)}
		});
		function investLoanZ(obj){    // 账户总览里的最近投资项目
        	$(".lately tbody").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".lately tbody").html("<td colspan='9'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var timeSettled;
		        	if(obj.results[i].strTimeSettled){
		        		timeSettled = obj.results[i].strTimeSettled;
		        	}else{
		        		timeSettled = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].rate+(obj.results[i].addRate?"+"+obj.results[i].addRate:"")+"</td><td>"+obj.results[i].months+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].dueInterest+"</td><td>"+obj.results[i].repayMethod+"</td><td>"+obj.results[i].investTime+"</td><td>"+timeSettled+"</td><td>"+obj.results[i].loanStatus+"</td>");
			    	if(obj.results[i].del){
			    		var oTd = $("<td><a href='/zycfMarket/user/downLoadContract?investId="+obj.results[i].investId+"' target='_blank' >下载合同</a></td>");
			    	}else{
			    		var oTd = $("<td>未生成</td>");
			    	};
			    	oTd.appendTo(oTr);
					oTr.appendTo($(".lately tbody"));
	        	};
	        };
        };
        accountListAjax({      // 账户总览里的资金记录
			page:1,
		    num:5,
		    url:"/zycfMarket/userFundRecordList",
		    callBack:function(obj){fundRecordZ(obj)}
		});
		function fundRecordZ(obj){    // 账户总览里的资金记录
        	$(".fundRecord tbody").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".fundRecord tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var description,name,amount;
		        	amount = parseFloat(obj.results[i].amount).toFixed(2);
		        	if(obj.results[i].operation == "OUT"){
		        		amount = "-"+amount;
		        	};
		        	if(obj.results[i].description){
		        		description = obj.results[i].description;
		        	}else{
		        		description = "";
		        	};
		        	if(obj.results[i].status == "成功"){
		        		name = 'success';
		        	}else{
		        		name = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].orderid+"</td><td>"+obj.results[i].type+"</td><td>"+amount+"</td><td>"+obj.results[i].strTimeRecorded+"</td><td><span class="+name+">"+obj.results[i].status+"</span></td><td>"+description+"</td>");
					oTr.appendTo($(".fundRecord tbody"));
	        	};
	        };
        };
		/* 左侧导航 */
		loadAccountData();
		$('.rechargeMain .sidebarNav li').eq(0).addClass('active');
		$('.accountContent .Tabl').eq(0).show();
		$('.rechargeMain .sidebarNav li').click(function(){
			$('.rechargeMain .sidebarNav li').removeClass('active');
			$(this).addClass('active');
			$('.accountContent .Tabl').hide().eq($(this).index()).show();
			loadAccountData();
		});
        $("#latelyMore").click(function(){   //更多
        	$('.rechargeMain .sidebarNav li').removeClass('active').eq(2).addClass('active');
			$('.accountContent .Tabl').hide().eq(2).show();
			loadAccountData();
        });
        $("#fundRecordMore").click(function(){   //更多
        	$('.rechargeMain .sidebarNav li').removeClass('active').eq(3).addClass('active');
			$('.accountContent .Tabl').hide().eq(3).show();
			loadAccountData();
        });
        function loadAccountData(){
        	var hash = window.location.hash.substring(1);
        	if($(".investmentPro").css("display") == "block" || hash == "2"){                    // 已投项目
				$(".investmentPro .proQuery .left span").removeClass("on").eq(0).addClass("on");
				$("#proWrapInpS").val("");
				$("#proWrapInpE").val("");
				accountListAjax({
					page:1,
				    num:10,
				    id:"proWrapPaging",
				    url:"/zycfMarket/userInvestLoanList",
				    callBack:function(obj){investLoan(obj)}
				});
			};
			if($(".backPlanBar").css("display") == "block" || hash == "5"){         // 回款计划
				$(".backPlanBar .repayment span").removeClass("on").eq(0).addClass("on");
				$("#dataStar").val("");
				$("#dataEnd").val("");
				accountListAjax({
					page:1,
				    num:10,
				    id:"backPlanPaging",
				    params:{"repayStatus":"UNDUE','OVERDUE","status":"UN"},
				    url:"/zycfMarket/userInvestRecordList",
				    callBack:function(obj){investRecord(obj)}
				});
			};
			if($(".fundRecordBar").css("display") == "block" || hash == "3"){           // 资金记录
				$(".fundRecordBar .left span").html("全部资金类型").attr("code","");
				$("#fundRecorS").val("");
				$("#fundRecorE").val("");
				accountListAjax({
					page:1,
				    num:10,
				    id:"fundRecordPaging",
				    url:"/zycfMarket/userFundRecordList",
				    callBack:function(obj){fundRecord(obj)}
				});
			};
			if($(".InviteRebateBar").css("display") == "block" || hash == "6"){           // 邀请返利
		        accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url:"/zycfMarket/queryInviterInvest",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InviteInvestment tbody").html("");
		                if(!obj.data || obj.data.length<1){
		                	$(".InviteInvestment tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<obj.data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	var title = obj.data[i].title?obj.data[i].title:"无",amount = obj.data[i].amount?obj.data[i].amount:0,investDate = obj.data[i].investDate?obj.data[i].investDate:"无";
			                	oTr.html("<td>"+obj.data[i].name+"</td><td>"+title+"</td><td>"+amount+"</td><td>"+investDate+"</td>");
								oTr.appendTo($(".InviteInvestment tbody"));
			                }
		                }
				    }
				});
			};
			if($(".servicesBar").css("display") == "block" || hash == "7"){           // 我的福利
		        $.ajax({    // 体验标和红包
		            type:"POST",
		            url:"/zycfMarket/queryMyAward",
		            data:{},
		            dataType:"json",
		            success:function(str){
		                if(str.data && str.data.length && str.data.length>0){
		                	$(".redPacketsWrap .wasUsed").html("");
		                	$(".redPacketsWrap .noUsed").html("");
		                	$(".testBidWrap .wasUsed").html("");
		                	$(".testBidWrap .noUsed").html("");
		                	for(var i=0,len=str.data.length;i<len;i++){
		                		var oLi = $("<li class='left'></li>");
		                		if(str.data[i].status == 1){
		                			oLi.addClass("overDue");
		                		};
		                		var months = "";
		                		if(str.data[i].months == 1){
		                			months = "月月发";
		                		}else if(str.data[i].months == 3){
		                			months = "季度红";
		                		}else if(str.data[i].months == 6){
		                			months = "半年财";
		                		}else if(str.data[i].months == 12){
		                			months = "全年盈";
		                		}else{
		                			months = "适用所有";
		                		};
			                	if(str.data[i].type == 1){
			                		oLi.html('<div class="content">\
		                                <h3>红包（'+months+'）</h3>\
		                                <div class="clear">\
		                                    <p class="left">金额 <strong>¥'+str.data[i].amount+'</strong></p>\
		                                    <p class="right">到期日 <span>'+str.data[i].endTime+'</span></p>\
		                                </div>\
		                            </div>\
		                            <div class="clear date">\
		                                <p class="left">投资≥￥'+str.data[i].limitAmount+'可用</p>\
		                            </div>');
		                            if(str.data[i].status == 1){
		                            	oLi.appendTo($(".redPacketsWrap .wasUsed"));
		                            }else{
		                            	var oDiv = $('<div></div>');
		                            	oDiv.attr("class",(str.data[i].status==2?"used":"link"));
		                            	if(str.data[i].status == 0){
		                            		oDiv.html('<a href="https://www.zuoyoufy.com/html/investment.html">立即使用</a>');
		                            	};
		                            	oDiv.appendTo(oLi);
		                            	oLi.appendTo($(".redPacketsWrap .noUsed"));
		                            };
			                	}else if(str.data[i].type == 2){
			                		oLi.html('<div class="content">\
		                                <h3>体验金</h3>\
		                                <div class="clear">\
		                                    <p class="left">体验金 <strong>¥'+str.data[i].amount+'</strong></p>\
		                                    <p class="right">到期日 <span>'+str.data[i].endTime+'</span></p>\
		                                </div>\
		                            </div>\
		                            <div class="clear date">\
		                                <p class="left">仅限体验标使用</p>\
		                            </div>');
		                            if(str.data[i].status == 1){
		                            	oLi.appendTo($(".testBidWrap .wasUsed"));
		                            }else{
		                            	var oDiv = $('<div></div>');
		                            	oDiv.attr("class",(str.data[i].status==2?"used":"link"));
		                            	if(str.data[i].status == 0){
		                            		oDiv.html('<a href="https://www.zuoyoufy.com/html/account/teste.html">立即使用</a><a class="use" href="https://www.zuoyoufy.com/html/activityZonePage/question.html">使用规则>></a>');
		                            	};
		                            	oDiv.appendTo(oLi);
		                            	oLi.appendTo($(".testBidWrap .noUsed"));
		                            };
			                	};
			                }
		                }
		            }
		        });
			};
        };
        $(".invitedList li").click(function(){
        	$(this).addClass("active").siblings().removeClass("active");
        	if($(this).index() == 0){
        		$(".InviteInvestment").show();
        		$(".InvestmentList").hide();
        		accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url:"/zycfMarket/queryInviterInvest",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InviteInvestment tbody").html("");
		                if(!obj.data || obj.data.length<1){
		                	$(".InviteInvestment tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<obj.data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	var title = obj.data[i].title?obj.data[i].title:"无",amount = obj.data[i].amount?obj.data[i].amount:0,investDate = obj.data[i].investDate?obj.data[i].investDate:"无";
			                	oTr.html("<td>"+obj.data[i].name+"</td><td>"+title+"</td><td>"+amount+"</td><td>"+investDate+"</td>");
								oTr.appendTo($(".InviteInvestment tbody"));
			                }
		                }
				    }
				});
        	}else if($(this).index() == 1){
        		$(".InviteInvestment").hide();
        		$(".InvestmentList").show();
        		accountListAjax({
					page:1,
				    num:10,
				    id:"InviteRebatePaging",
				    url:"/zycfMarket/queryUserByUserId",
				    callBack:function(obj){
				    	$("#InviteRebatePaging").html("");
				    	$(".InvestmentList tbody").html("");
		                if(!obj.data || obj.data.length<1){
		                	$(".InvestmentList tbody").html("<td colspan='4'>暂无数据</td>");
		                }else{
		                	for(var i=0;i<obj.data.length;i++){
			                	var oTr = $("<tr></tr>");
			                	oTr.html("<td>"+obj.data[i].MOBILE+"</td><td>"+(obj.data[i].NAME?obj.data[i].NAME:"")+"</td><td>"+(obj.data[i].REGISTERDATE?obj.data[i].REGISTERDATE:"")+"</td><td>"+(obj.data[i].LASTLOGINDATE?obj.data[i].LASTLOGINDATE:"")+"</td>");
								oTr.appendTo($(".InvestmentList tbody"));
			                }
		                }
				    }
				});
        	};
        });
		$(".backPlanBar .repayment span").click(function(){   // 回款计划查询
			var status = "",sta="";
			$(".backPlanBar .repayment span").removeClass("on");
			$(this).addClass("on");
			if($(this).html() == "未还"){
				status = "UNDUE','OVERDUE";
				sta = "UN";
			}else if($(this).html() == "已还"){
				status = "PREPAY','REPAYED";
				sta = "PR";
			};
			accountListAjax({
				page:1,
			    num:10,
			    params:{"repayStatus":status,"status":sta,"dueDateStart":$("#dataStar").val(),"dueDateEnd":$("#dataEnd").val()},
			    id:"backPlanPaging",
			    url:"/zycfMarket/userInvestRecordList",
				callBack:function(obj){investRecord(obj)}
			});
		});
		$(".investmentPro .proQuery .left span").click(function(){
			var status = "";
			$(".investmentPro .proQuery .left span").removeClass("active");
			$(this).addClass("active");
			if($(this).html() == "全部"){
				status = "";
			}else if($(this).html() == "未起息"){
				status = "'FINISHED','OPENED'";
			}else if($(this).html() == "回款中"){
				status = "'SETTLED'";
			}else if($(this).html() == "已回款"){
				status = "'CLEARED'";
			};
			accountListAjax({
				page:1,
			    num:10,
			    params:{"loanStatus":status,"dateStart":$("#proWrapInpS").val(),"dateEnd":$("#proWrapInpE").val()},
			    id:"proWrapPaging",
			    url:"/zycfMarket/userInvestLoanList",
				callBack:function(obj){investLoan(obj)}
			});
		});
		$(".investmentPro .proQuery .btn").click(function(){
			var status = "";
			if($(".investmentPro .proQuery .left span").eq(0).hasClass("active")){
				status = ""
			}else if($(".investmentPro .proQuery .left span").eq(1).hasClass("active")){
				status = "FINISHED"
			}else if($(".investmentPro .proQuery .left span").eq(2).hasClass("active")){
				status = "SETTLED"
			}else if($(".investmentPro .proQuery .left span").eq(3).hasClass("active")){
				status = "CLEARED"
			}
			accountListAjax({
				page:1,
			    num:10,
			    params:{"loanStatus":status,"dateStart":$("#proWrapInpS").val(),"dateEnd":$("#proWrapInpE").val()},
			    id:"proWrapPaging",
			    url:"/zycfMarket/userInvestLoanList",
				callBack:function(obj){investLoan(obj)}
			});
		});
		$(".backPlanBar .export").click(function(){
			var status = "";
			if($(".backPlanBar .repayment span").eq(0).hasClass("on")){
				status = "UNDUE','OVERDUE"
			}else if($(".backPlanBar .repayment span").eq(1).hasClass("on")){
				status = "PREPAY','REPAYED"
			}else{
				status = ""
			}
			accountListAjax({
				page:1,
			    num:10,
			    params:{"repayStatus":status,"dueDateStart":$("#dataStar").val(),"dueDateEnd":$("#dataEnd").val()},
			    id:"backPlanPaging",
			    url:"/zycfMarket/userInvestRecordList",
				callBack:function(obj){investRecord(obj)}
			});
		});
		$(".fundRecordBar .export").click(function(){
			accountListAjax({
				page:1,
			    num:10,
			    params:{"type":$(".fundRecordBar .left span").attr("code"),"dateStart":$("#fundRecorS").val(),"dateEnd":$("#fundRecorE").val()},
			    id:"fundRecordPaging",
			    url:"/zycfMarket/userFundRecordList",
				callBack:function(obj){fundRecord(obj)}
			});
		});
        function accountListAjax(opt){
        	var settings = {
				page:1,
				num:10,
				params:{},
				url:"",
				id:"",
				callBack:function(){}
			};
			$.extend(settings,opt);
        	$.ajax({
	            type:"POST",
	            url:settings.url,
	            data:{"pageNo":settings.page,
				    "pageSize":settings.num,
				    "params":settings.params
				    },
	            dataType:"json",
	            success:function(str){
	            	settings.callBack(str);
	            	if(str.totalPage>1){
				    	page({
							id:settings.id,
							nowNum:settings.page,
							allNum:str.totalPage,
							callBack:function(now,all){
								accountListAjax({
									page:now,
								    num:10,
								    params:settings.params,
								    id:settings.id,
								    url:settings.url,
				                    callBack:settings.callBack
								});
							}
						});
				    };
				}
	        });
        };
        function investLoan(obj){    // 已投项目
        	$(".proWrap tbody").html("");
			$("#proWrapPaging").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".proWrap tbody").html("<td colspan='9'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var timeSettled;
		        	if(obj.results[i].strTimeSettled){
		        		timeSettled = obj.results[i].strTimeSettled;
		        	}else{
		        		timeSettled = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].title+"</td><td>"+obj.results[i].rate+(obj.results[i].addRate?"+"+obj.results[i].addRate:"")+"</td><td>"+obj.results[i].months+"</td><td>"+obj.results[i].amount+"</td><td>"+obj.results[i].dueInterest+"</td><td>"+obj.results[i].repayMethod+"</td><td>"+obj.results[i].investTime+"</td><td>"+timeSettled+"</td><td>"+obj.results[i].loanStatus+"</td>");
			    	if(obj.results[i].del){
			    		var oTd = $("<td><a href='/zycfMarket/user/downLoadContract?investId="+obj.results[i].investId+"' target='_blank' >下载合同</a></td>");
			    	}else{
			    		var oTd = $("<td>未生成</td>");
			    	};
			    	oTd.appendTo(oTr);
					oTr.appendTo($(".investmentPro .proWrap tbody"));
	        	};
	        };
        };
        function investRecord(obj){    // 回款计划
        	$(".backPlanBar tbody").html("");
			$("#backPlanPaging").html(""); 
			if(!obj.results.length || obj.results.length<1){
	        	$(".backPlanBar tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
			    	var repayDate,currentPeriod,amount,status;
			    	if(obj.results[i].repayDate){
			    		repayDate = new Date(parseInt(obj.results[i].repayDate,10)).toLocaleDateString();
			    	}else{
			    		repayDate = "";
			    	};
			    	if(obj.results[i].amountPrincipal != "0"){
			    		currentPeriod = "本金+利息";
			    	}else{
			    		currentPeriod = "利息";
			    	};
			    	amount = parseFloat(obj.results[i].amountInterest)+parseFloat(obj.results[i].amountPrincipal);
			    	oTr.html("<td>"+obj.results[i].dueDate+"</td><td>"+amount+"</td><td>"+currentPeriod+"</td><td>"+obj.results[i].title+"--"+obj.results[i].currentPeriod+"期"+"</td><td>"+repayDate+"</td><td>"+obj.results[i].repayStatus+"</td>");
					oTr.appendTo($(".backPlanBar tbody"));
	        	};
	        };
        };
        function fundRecord(obj){    // 资金记录
        	$(".fundRecordBar tbody").html("");
			$("#fundRecordPaging").html("");
			if(!obj.results.length || obj.results.length<1){
	        	$(".fundRecordBar tbody").html("<td colspan='6'>暂无数据</td>");
	        }else{
	        	for(var i=0;i<obj.results.length;i++){
	        		var oTr = $("<tr></tr>");
		        	var description,name,amount;
		        	amount = parseFloat(obj.results[i].amount).toFixed(2);
		        	if(obj.results[i].operation == "OUT"){
		        		amount = "-"+amount;
		        	};
		        	if(obj.results[i].description){
		        		description = obj.results[i].description;
		        	}else{
		        		description = "";
		        	};
		        	if(obj.results[i].status == "成功"){
		        		name = 'success';
		        	}else{
		        		name = "";
		        	};
			    	oTr.html("<td>"+obj.results[i].orderid+"</td><td>"+obj.results[i].type+"</td><td>"+amount+"</td><td>"+obj.results[i].strTimeRecorded+"</td><td><span class="+name+">"+obj.results[i].status+"</span></td><td>"+description+"</td>");
					oTr.appendTo($(".fundRecordBar tbody"));
	        	};
	        };
        };
		$(".messageBar .messagelist p:even").css('background','#edf7ff');
		/* 右边Tab */
		$('.investmentPro .frameTab li').click(function(){
			$('.investmentPro .frameTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.investmentPro .proWrap').show();
					$('.investmentPro .assignment').hide();
				break;
				case 1:
					$('.investmentPro .proWrap').hide();
					$('.investmentPro .assignment').show();
				break;
			};
		});
		/*$('.messageBar .frameTab li').click(function(){
			$('.messageBar .frameTab li').removeClass('active');
			$(this).addClass('active');
			switch ($(this).index()){
				case 0:
					$('.messageBar .messagewrap').show();
					$('.messageBar .messageNotice').hide();
				break;
				case 1:
					$('.messageBar .messagewrap').hide();
					$('.messageBar .messageNotice').show();
				break;
			};
		});*/
		/* 修改 */
		$(".personalSetting .passwordBtn").click(function(){
			window.location.href = '../html/account/modifyPassword.html'
		});
		$(".personalSetting .mobileBtn").click(function(){
			window.location.href = '../html/account/mobileMumber.html'
		});
		$(".personalSetting .identityBtn").click(function(){
			window.location.href = '../html/account/identity.html'
		});
		$(".personalSetting .emailBtn").click(function(){
			window.location.href = '../html/account/email.html'
		});
	    
	    /* 资金记录 下拉列表 */
	    $('.fundRecordBar .function .left span').click(function(){
	    	$('.fundRecordBar .function ul').toggle();
	    });
	    $('.fundRecordBar .function li').click(function(){
	    	$('.fundRecordBar .function .left span').html($(this).html());
	    	$('.fundRecordBar .function .left span').attr("code",$(this).attr("code"));
	    	$('.fundRecordBar .function ul').hide();
	    });
	    // 复制链接
	    $('.InviteRebateBar .copyBtn').click(function(){
	    	zycfZ.copy();
	    });
	},
	calendarRecord:function(){
		var myDate = new Date();
		$(".calendar .year").html( myDate.getFullYear() );
		$(".calendar .month").html( myDate.getMonth()+1 );

		var month = $(".calendar .month").html(),year = $(".calendar .year").html();
		creatDateList(year,month);
		$(".calendar .prev").click(function(){
			month--;
			if(month<=0){
				month=12;
				year--;
				$(".calendar .year").html(year);
			};
			$(".calendar .month").html(month);
			creatDateList(year,month);
		});
		$(".calendar .next").click(function(){
			month++;
			if(month>12){
				month = 1;
				year++;
				$(".calendar .year").html(year);
			};
			$(".calendar .month").html(month);
			creatDateList(year,month);
		});
		function creatDateList(Year,Month){
			$(".calendar td").removeClass("active today");
			$(".calendar td span").html("");
			$(".calendar td em").remove();
			$(".weekList li").removeClass("active");
			var monthDay = DayNumOfMonth(Year,Month);
			var oneDay = getDateMonth(Year,Month);
			for(var i=0,len=$(".calendar tbody tr").size();i<len;i++){
				if(i>=Math.ceil( (monthDay+oneDay)/7 )){
					$(".calendar tbody tr").eq(i).css("display","none");
				}else{
					$(".calendar tbody tr").eq(i).css("display","block");
				};
			};
			for(var i=oneDay,len=(monthDay+oneDay);i<len;i++){
				$(".calendar td span").eq(i).html(i-oneDay+1);
			};
			if( myDate.getFullYear() == $(".calendar .year").html() && (myDate.getMonth()+1) == $(".calendar .month").html() ){
				$(".calendar td span").each(function(){
					if( myDate.getDate() == $(this).html() ){
						$(this).parent().addClass("today");
						var em = $("<em>今</em>");
						em.appendTo($(this).parent());
						$(".weekList li").eq(myDate.getDay()).addClass("active");
					};
				});
			};
			getCalendarDate(Year,Month);
		};
		function DayNumOfMonth(Year,Month){
		    Month--;
		    var d = new Date(Year,Month,1);
		    d.setDate(d.getDate()+32-d.getDate());
		    return (32-d.getDate());
		};
		function getDateMonth(Year,Month){
			var d = new Date(Year,(Month-1),1);
			return d.getDay();
		};
		function getCalendarDate(Year,Month){
			$.ajax({
				type:"POST",
				url:"/zycfMarket/userFundRecordCalendar",
				data:{"newData":Year+"-"+((Month<10)?"0"+Month:Month)},
				success:function(str){
					$(".calendar td").each(function(){
						$(this).removeClass().unbind("mouseenter").unbind("mouseleave");
					});
					if(str.length && str.length>0){
						$(".calendar td span").each(function(){
							for (var j = 0,len=str.length; j < len; j++) {
								if( (($(this).html()<10)?"0"+$(this).html():$(this).html()) == str[j].dates && !$(this).parent().hasClass("active") ){
									$(this).parent().addClass("active");
								}
							};
						});
					};
					$(".calendar td span").each(function(){
						if($(this).parent().hasClass("active")){
							$(this).parent().hover(function(){
								$(".calendarRecord .recordList").html("");
								$(".calendarRecord h3").html(Year+"年"+Month+"月"+$(this).find("span").html()+"日");
								for (var j = 0,len=str.length; j < len; j++) {
									var type;
									if(str[j].type == "还款"){
										type = "repay"
									}else if(str[j].type == "投资"){
										type = "invest"
									}
									else if(str[j].type == "回款"){
										type = "pay"
									}
									else if(str[j].type == "提现"){
										type = "withdrawals"
									}
									else if(str[j].type == "充值"){
										type = "recharge"
									}
									else if(str[j].type == "放款"){
										type = "loan"
									};
									if( (($(this).find("span").html()<10)?"0"+$(this).find("span").html():$(this).find("span").html()) == str[j].dates){
										if(type == "pay"){
											var p = $('<p class="'+type+'"><em></em><span class="time">'+str[j].dudate+'</span><span>回款本金：'+str[j].amountPrincipal+'</span><span>回款利息：'+str[j].amountInterest+'</span><span>回款总计：'+(parseFloat(str[j].amountPrincipal)+parseFloat(str[j].amountInterest))+'</span></p>');
										}else{
											var p = $('<p class="'+type+'"><em></em><span class="time">'+str[j].timeRecordeds+'</span><span>'+str[j].type+"："+str[j].amount+'</span><span>状态：'+str[j].statuss+'</span></p>');
										}
										p.appendTo($(".calendarRecord .recordList"));
									}
								};
								$(".calendarRecord").show().css("margin-left","-"+$(".calendarRecord").outerWidth()/2);
							},function(){
								$(".calendarRecord").hide();
							});
						};
					});
					$(".calendarRecord").hover(function(){
						$(this).show();
					},function(){
						$(this).hide();
					});
				}
			});
		};
	},
	modifyPassword:function(){    // 修改密码
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		$(".passwordFrame .oldPassWord input").focus(function(){
			$(".passwordFrame .oldPassWord .msg").css("display","none");
			$(".passwordFrame .oldPassWord .msgInfo").css("display","none");
		});
		/*$(".passwordFrame .oldPassWord input").blur(function(){
			if(testNum($(this).val())){
				$(".passwordFrame .oldPassWord .msg").css("display","inline-block");
			}else{
				$(".passwordFrame .oldPassWord .msgInfo").css("display","inline-block").html("6~16位数字与字母组合");
			};
		});*/
		$(".passwordFrame .newPssWord input").focus(function(){
			if($(this).val() == "6~16位数字与字母组合"){
				$(this).val("");
			};
			$(".passwordFrame .newPssWord .msg").css("display","none");
			$(".passwordFrame .newPssWord .msgInfo").css("display","none");
		});
		$(".passwordFrame .newPssWord input").blur(function(){
			if($(this).val() == ""){
				$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block");
			};
			if(testNum($(this).val())){
				$(".passwordFrame .newPssWord .msg").css("display","inline-block");
			}else{
				$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block").html("6~16位数字与字母组合");
			}
		});
		$(".passwordFrame .newPssWord2 input").focus(function(){
			if($(this).val() == "与新密码保持一致"){
				$(this).val("");
			};
			$(".passwordFrame .newPssWord2 .msg").css("display","none");
			$(".passwordFrame .newPssWord2 .msgInfo").css("display","none");
		});
		$(".passwordFrame .newPssWord2 input").blur(function(){
			if($(this).val() == ""){
				$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
			};
			if($(".passwordFrame .newPssWord input").val() == $(this).val() && $(this).val() != ""){
				$(".passwordFrame .newPssWord2 .msg").css("display","inline-block");
			}else{
				$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
			}
		});
		var btn = true;
		if(btn){
			$(".passwordFrame .reviseBtn input").click(function(){
				if(testNum($(".passwordFrame .newPssWord input").val()) && $(".passwordFrame .newPssWord input").val() == $(".passwordFrame .newPssWord2 input").val()){
					btn = false;
					$.ajax({	
						url:'/zycfMarket/user/updatePassword',  
						type:'post',  //请求方式  get||post
						data:{"oldPassword":$(".passwordFrame .oldPassWord input").val(),"password":$(".passwordFrame .newPssWord input").val()},
						success:function(str){
							zycfZ.exception(str);
							btn = true;
							if(str.code == 0){
								alert(str.message);
								window.history.go(-1);
							}else if(str.code == 1 || str.code == 2){
								alert(str.message);
							}else if(str.code == "UNKNOW_EXCEPTION"){
								alert("未登录，请先登录后再尝试更改密码");
							};
						}
					});
				};
				if(!testNum($(".passwordFrame .newPssWord input").val())){
					$(".passwordFrame .newPssWord .msgInfo").css("display","inline-block").html("6~16位数字与字母组合");
				}else if($(".passwordFrame .newPssWord input").val() != $(".passwordFrame .newPssWord2 input").val()){
					$(".passwordFrame .newPssWord2 .msgInfo").css("display","inline-block");
				};
			});
		}else{
			alert("正在连接服务器，请稍后")
		}
		
	},
	identity:function(){
		var btn = true;
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$("input[name='nameId']").focus(function(){
			$(this).css('border-color','#ff6f0a');
			$('.idCodeFrame .nameMsg').css('display','none');
		});
		$("input[name='nameId']").blur(function(){
			$(this).css('border-color','#ccc');
			if( !checkChinese($(this).val()) ){
				$('.idCodeFrame .nameMsg').css('display','block').find("span").html("请输入中文姓名");
			};
		});
		$("input[name='IdCard']").focus(function(){
			$(this).css('border-color','#ff6f0a');
			$('.idCodeFrame .nameMsg').css('display','none');
		});
		$("input[name='IdCard']").blur(function(){
			$(this).css('border-color','#ccc');
			if(!checkCard($(this).val())){
				$('.idCodeFrame .nameMsg').css('display','block').find("span").html("请输入正确的身份证号码！");
			}else if(!zycfZ.eighteen($(this).val())){
				$('.idCodeFrame .nameMsg').css('display','block').find("span").html("用户未满18岁，不能认证");
			};
		});
		if(btn){
			$('.idCodeFrame .acBtn input').click(function(){    // 个人设置里实名认证
				if(checkCard($("input[name='IdCard']").val()) && checkChinese($("input[name='nameId']").val()) && zycfZ.eighteen($("input[name='IdCard']").val()) ){
					btn = false;
					$.ajax({	
						url:'/zycfMarket/idCertificate',  //实名验证
						type:'post',  //请求方式  get||post
						data:{"name":$("input[name='nameId']").val(),
						    "idnumber":$("input[name='IdCard']").val()
						},
						success:function(str){
							zycfZ.exception(str);
							btn = true;
							if(str.code == 1){   // 实名验证成功
								alert(str.message);
								window.history.go(-1);
							}else if(str.code == 160037){
								$('.idCodeFrame .nameMsg').css("display","block").find("span").html(str.message);
							}else{
								$('.idCodeFrame .nameMsg').css("display","block").find("span").html("发生异常，请重新操作");
							};
						}
					});
				};
				if(!checkCard($("input[name='IdCard']").val())){
					$('.idCodeFrame .nameMsg').css('display','block').find("span").html("请输入正确的身份证号码！");
				}else if( !checkChinese($("input[name='nameId']").val()) ){
					$('.idCodeFrame .nameMsg').css('display','block').find("span").html("请输入中文姓名");
				}else if( !zycfZ.eighteen($("input[name='IdCard']").val()) ){
					$('.idCodeFrame .nameMsg').css('display','block').find("span").html("用户未满18岁，不能认证");
				};
			});
		}else{
			alert("正在连接服务器，请稍后")
		}
	},
	agreement:function(){
		var linkURL = null;
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$.ajax({
            type:"POST",
            url:"/zycfMarket/user/userSettings",
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
				if(str.debit && str.instant){
					$(".agreement .quickPay input").val("解约协议");
				};
				if(str.invest){
					$(".agreement .invest input").val("解约协议");
				};
				if(str.repay){
					$(".agreement .repayment input").val("解约协议");
				};
				$(".agreement .invest .btn").click(function(){
					thirdParty(str.invest?"/zycfMarket/user/cancleAgreement":"/zycfMarket/user/agreement",[3]);
					linkMessage(linkURL);
				});
				$(".agreement .repayment .btn").click(function(){
					thirdParty(str.repay?"/zycfMarket/user/cancleAgreement":"/zycfMarket/user/agreement",[4]);
					linkMessage(linkURL);
				});
				$(".agreement .quickPay .btn").click(function(){
					thirdParty((str.debit&&str.instant)?"/zycfMarket/user/cancleAgreement":"/zycfMarket/user/agreement",[1,2]);
					linkMessage(linkURL);
				});
				function linkMessage(linkURL){
					if(typeof linkURL === "string"){
						$("#RechargeResults").show();
						window.open(linkURL,'_blank');
					}else if(linkURL.code == 1){
						$("#RechargeResults").show();
						window.open(linkURL.message,'_blank');
					}else{
						alert(linkURL.message);
					};
				};
			}
        });
		function thirdParty(URL,arr){
			$.ajax({
	            type:"POST",
	            url:URL,
	            data:{"agreements":arr},
	            dataType:"json",
	            async: false,
	            success:function(str){
	            	zycfZ.exception(str);
					linkURL = str;
				}
	        });
		};
	},
	bank:function(){
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		var off = false,onOff=false;
		var bindCard = false;
		var bankList = "01";
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$.ajax({
            type:"POST",
            url:"/zycfMarket/user/userSettings",
            dataType:"json",
            async:false,
            success:function(str){
            	zycfZ.exception(str);
            	bindCard = str.cardauthenticated;
				if(!str.idauthenticated){
					$(".bankBar .yourName .backgroundColor").html("");
					$(".bankBar .userID .backgroundColor").html("");
					onOff=false;
					alert("请先实名认证，再绑定银行卡，谢谢！");
				}else{
					$(".bankBar .yourName .backgroundColor").html(str.name);
					$(".bankBar .userID .backgroundColor").html(str.idnumber);
					onOff=true;
				};
				if(str.cardauthenticated){
					$(".minNav span").html("更换银行卡");
					$(".rechargeContent .title span").html("更换银行卡");
					$(".rechargeContent .reviseBtn input").val("更换");
				}
				if(str.debit || str.instant || str.invest || str.repay){
					bankList = "00";
				}
			}
        });
        zycfZ.getBankList(bankList,function(obj){
            $(".bankList").html("");
            $(".selectedBank").attr("forSort",obj[0].bankCode).html(obj[0].bankName);
        	for(var i=0;i<obj.length;i++){
        		var oLi = $("<li forSort="+obj[i].bankCode+">"+obj[i].bankName+"</li>")
        		oLi.appendTo($(".bankList"));
        	}
        	$('.bankBar .bankList li').click(function(){
		    	$('.bankBar .selectedBank').html($(this).html()).attr('forSort',$(this).attr('forSort'));
		    	$('.bankBar .bankList').hide();
		    });
		});
		/* 绑定银行卡 下拉列表 */
	    $('.bankBar .selectedBank').click(function(){
	    	$('.bankBar .bankList').toggle();
	    	$(document).click(function(){
				$('.bankBar .bankList').hide();
			});
			return false;
	    });
	    $(".bankBar .cardNum input").focus(function(){
	    	$(".bankBar .cardNum .msg").css("display","none");
	    	$(".bankBar .cardNum .msgInfo").css("display","none");
	    	off = false;
	    });
	    $(".bankBar .cardNum input").blur(function(){
	    	var reg = /^(\d{16}|\d{19})$/;
	    	if(reg.test($(this).val())){
	    		$(".bankBar .cardNum .msg").css("display","inline-block");
	    		off = true;
	    	}else{
	    		$(".bankBar .cardNum .msgInfo").css("display","inline-block");
	    		off = false;
	    	};
	    });
	    $("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
	    $(".bankBar .reviseBtn input").click(function(){
	    	var linkURL = null;
	    	var cardURL = bindCard?"/zycfMarket/modifyBankCard":"/zycfMarket/bankCardBinding";  //修改银行卡、绑定银行卡URL
	    	var bankObj = distinctionBankCard( $(".bankBar .cardNum input").val() );
	    	if( $(".bankBar .selectedBank").html() != bankObj.bank ){
	    		alert("您填写的卡号与发卡银行不一致，请重新选择发卡银行");
	    		return;
	    	};
	    	if(!off){
	    		$(".bankBar .cardNum .msgInfo").css("display","inline-block");
	    		return;
	    	}else if(!onOff){
	    		alert("请先实名认证，再绑定银行卡，谢谢！");
	    		return;
	    	};
	    	if(off && onOff){
	    		$.ajax({    // 绑定银行卡
		            type:"POST",
		            url:cardURL,
		            data:{"bank":$(".bankBar .selectedBank").attr("forSort"),"account":$(".bankBar .cardNum input").val()},
		            dataType:"json",
		            async: false,
		            success:function(str){
		            	zycfZ.exception(str);
		            	if(str.code == 1){
		            		$("#RechargeResults").show();
		            		linkURL = str.message;
		            	}else{
		            		alert(str.message)
		            	};
					}
		        });
	    	};
	    	if(linkURL){
				window.open(linkURL,'_blank');
			};
	    });
	},
	email:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		var flag = false,flag1 = false;
		$(".maiFrame .first input").focus(function(){
			$(".maiFrame .first .msg").css("display","none");
			$(".maiFrame .first .msgInfo").css("display","none");
			flag = false;
		});
		$(".maiFrame .first input").blur(function(){
			if(isEmail($(this).val())){
				$(".maiFrame .first .msg").css("display","inline-block");
				flag = true;
			}else{
				$(".maiFrame .first .msgInfo").css("display","inline-block");
				flag = false;
			}
		});
		$(".maiFrame .second input").focus(function(){
			$(".maiFrame .second .msg").css("display","none");
			$(".maiFrame .second .msgInfo").css("display","none");
			flag1 = false;
		});
		$(".maiFrame .second input").blur(function(){
			if($(this).val() == $(".maiFrame .first input").val() && $(this).val() != ""){
				$(".maiFrame .second .msg").css("display","inline-block");
				flag1 = true;
			}else{
				$(".maiFrame .second .msgInfo").css("display","inline-block");
				flag1 = false;
			}
		});
		$(".maiFrame .reviseBtn input").click(function(){
			if(flag == true && flag1 == true){
				$.ajax({
		            type:"POST",
		            url:"/zycfMarket/user/updateMail",
		            data:{"email":$(".maiFrame .first input").val()},
		            dataType:"json",
		            success:function(str){
		            	zycfZ.exception(str);
						if(str.code == 0){
							alert(str.message);
							window.history.go(-1);
						}else if(str.code == 1){
							alert(str.message);
						}else if(str.code == "UNKNOW_EXCEPTION"){
							alert("未登录，请先登录后再尝试绑定邮箱");
						};
					}
		        });
			};
			if(flag == false){
				$(".maiFrame .first .msgInfo").css("display","inline-block");
			};
			if(flag1 == false){
				$(".maiFrame .second .msgInfo").css("display","inline-block");
			};
		})
	},
	mobileNum:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
	},
	copy:function(){
		var clip = null; 
		function init() { 
			clip = new ZeroClipboard.Client(); 
			ZeroClipboard.setMoviePath("../html/ZeroClipboard.swf");
			clip.setHandCursor( true ); 
			clip.addEventListener('load', function (client) { 
			    debugstr("Flash movie loaded and ready."); 
			}); 
			clip.addEventListener('mouseOver', function (client) { 
				clip.setText( $('#copyInfo').val() ); 
			}); 
			clip.addEventListener( "complete", function(){ 
		        alert("复制成功，可以发给你的好朋友了！");  
		    }); 
			clip.glue( 'copyBtn' ); 
		} 
		setTimeout(function(){ 
		    init(); 
		},1500); 
	},
	getBankList:function(num,fn){
		$.ajax({
			type:"POST",
			url:"/zycfMarket/queryBankList",
			data:{"number":num},
			success:function(str){
				zycfZ.exception(str);
				fn && fn(str);
			}
		});
	},
	recharge:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.recharge .userName span').html(name);
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		zycfZ.getBankList("01",function(obj){
            $(".bankListAll").html("");
            if(!obj.length || obj.length<1){
            	$(".bankListAll").html("<span>请刷新重试</span>");
            }else{
            	for(var i=0;i<obj.length;i++){
            		var oSpan = $("<span class="+obj[i].bankCode+" title="+obj[i].bankName+" abbr="+obj[i].bankCode+"></span>");
            		if(i==0){
            			oSpan.addClass("active");
            		}
            		oSpan.appendTo($(".bankListAll"));
            	}
            	$('.bankListAll span').click(function(){
					$('.bankListAll span').removeClass('active').eq($(this).toggleClass('active'));
				});
            }
		});
		$('.recharge .addMoney input').focus(function(){
			$('.recharge .msg').css("color","#A9A9A9");
		});
		$("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$("#RechargeResults .rechargeAgain").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		var minrecharge = 0;
		$.ajax({
            type:"POST",
            url:"/zycfMarket/userFundRecharge",
            dataType:"json",
            success:function(str){
            	zycfZ.exception(str);
				if(str.availableAmount){
					$('.recharge .title strong').html(str.availableAmount);
				    $('.recharge .balance strong').html(str.availableAmount);
				}else{
					$('.recharge .title strong').html(0);
				    $('.recharge .balance strong').html(0);
				};
				$('.recharge .msg').html("单笔充值金额应大于等于"+str.minrecharge+"元且小于等于100万元");
				minrecharge = str.minrecharge;
			}
        });
		
		$('.recharge .btn input').click(function(){
			var linkURL = null;
			var re=/^[0-9]*$/;
			var num = parseInt($('.recharge .addMoney input').val());
			if(num == 0){
				alert("输入金额不能为0");
				return;
			};
			if($( '.recharge .bankListAll span.active').attr('abbr') != '' && num >= minrecharge && num<=1000000){
				$.ajax({
	                type:"POST",
	                data : {"operatingLimit":num,"bankCode":$('.recharge .bankListAll span.active').attr('abbr')},
	                url:"/zycfMarket/umpRecharge",
	                dataType:"json",
	                async: false,
	                success:function(data){
	                	zycfZ.exception(data);
	                	$("#RechargeResults").show();
						linkURL = data.prepareUmp;
	                	$('#uc').html(data.accountname);
	              	    $('#userId').html(data.userid); 
	                }
	            });
			}else{
				$('.recharge .msg').css("color","red");
			};

			if(linkURL){
				window.open(linkURL,'_blank');
			};

		});
	},
	loanManagement: function(){
		//数据初始化

		/* 下拉列表 */
	    $('.loanManage .function .left span').click(function(e){
	    	//$('.loanManage .function ul').toggle();
	    	e.stopPropagation();
	    	$(this).parent().find("ul").toggle();
	    });
	    $('.loanManage .function li').click(function(e){
	    	e.stopPropagation();
	    	
	    	$(this).parent().parent().find("span").html($(this).html());
	    	$(this).parent().parent().find("span").attr("code",$(this).attr("code"));
	    	$(this).parent().hide();
	    });
	    //时间插件
	    laydate({elem: '#fundRecorLs'});
	    laydate({elem: '#fundRecorLe'});
	    laydate({elem: '#fundRecorRs'});
	    laydate({elem: '#fundRecorRe'});
	    //查询
	    $(".loanBox:eq(0) .export").click(function(){
	    	var status = $(this).parent().parent().children().eq(0).find("span").attr("code");
	    	var startTime =$("#fundRecorLs").val();
	    	var endTime = $("#fundRecorLe").val();
	    	if(startTime == '' && endTime != ''){
	    		return;
	    	}
	    	if(startTime != '' && endTime == ''){
	    		return;
	    	}
	    	
	    	
	    	$.ajax({
	    		type: 'post',
	    		url: '/zycfMarket/loanManagement',
	    		data: {
	    			"status": status,
	    			"startTimes": startTime,
	    			"endTimes": endTime
	    		},
	    		success: function(str){
	    			zycfZ.exception(str);
	    			if(str){
	 					var loanData = str;
	 					$(".loanBox").eq(0).find("tbody").html("");
	 					for(var i = 0; i < loanData.length; i++){
	 						var tr = $("<tr></tr>");
	 						tr.html("");
	 						tr.html("<td>"+loanData[i].title+"</td><td>"+loanData[i].amount+"</td><td>"+loanData[i].rate+"%"+(loanData[i].addRate?"+"+loanData[i].addRate+"%":"")+"</td><td>"+loanData[i].months+"个月</td><td>"+loanData[i].method+"</td><td>"+loanData[i].strStatus+"</td>");
	 						$(".loanBox").eq(0).find("tbody").append(tr);
	 					}
	 					//分页
						$("div.holder").jPages({
							containerID : "loadTab",
							previous : "上一页",
							next : "下一页",
							perPage : 10,
							delay : 20
						});
	 				}else{
		 					$("#loadTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
		 					$("div.holder").html("");
		 				}
	    		}
	    	});
	    });
	     $(".loanBox:eq(1) .export").click(function(){
	    	 	
	    	 	var status = $(this).parent().parent().children().eq(0).find("span").attr("code").toUpperCase();
		    	var startTime = $("#fundRecorRs").val();
		    	var endTime = $("#fundRecorRe").val();
		    	if(startTime == '' && endTime != ''){
		    		return;
		    	}
		    	if(startTime != '' && endTime == ''){
		    		return;
		    	}
		    	
	    	$.ajax({
	    		type: 'post',
	    		url: '/zycfMarket/loanRepaymentPlan',
	    		data: {
	    			"status": status,
	    			"startTimes": startTime,
	    			"endTimes": endTime
	    		},
	    		success: function(str){
	    			zycfZ.exception(str);
	    			if(str){
						var repayData = str;
						var repayAmount = 0;
						$(".loanBox").eq(1).find("tbody").html("");
						for(var i = 0; i < repayData.length; i++){
							
							if(repayData[i].status == "已还"){
								continue;
							}
							repayAmount += repayData[i].toTleAmount;
						}
						$("#repayDetail span:eq(0)").html("共"+repayData.length+"笔");
						$("#repayDetail span:eq(1)").html("待还"+parseFloat(repayAmount).toFixed(2)+"元");
						for(var i = 0; i < repayData.length; i++){
							var tr = $("<tr></tr>");
							tr.html("");
							tr.html("<td>"+repayData[i].duedate+"</td><td>"+repayData[i].amountinterest+"</td><td>"+repayData[i].amountprincipal+"</td><td>"+repayData[i].toTleAmount+"</td><td>"+repayData[i].status+"</td><td>"+repayData[i].title+"</td>");
							$(".loanBox").eq(1).find("tbody").append(tr);
						}
						$("div.holder").jPages({
							containerID : "repayTab",
							previous : "上一页",
							next : "下一页",
							perPage : 10,
							delay : 20
						});
					}else{

		 					$("#repayTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
		 					$("div.holder").html("");
		 				
					}
	    		}
	    	});
	    });
	    
	    	//1借款记录
	     $(".sidebarNav ul li:eq(4)").click(function(){
	    	 $.ajax({
		 			type: 'post',
		 			url: '/zycfMarket/loanManagement',
		 			async: false,
		 			data: {
		 				"status": "ALLSTATUS"
		 			},
		 			success: function(str){
		 				zycfZ.exception(str);
		 				if(str){
		 					var loanData = str;
		 					$(".loanBox").eq(0).find("tbody").html("");
		 					for(var i = 0; i < loanData.length; i++){
		 						var tr = $("<tr></tr>");
		 						tr.html("");
		 						tr.html("<td>"+loanData[i].title+"</td><td>"+loanData[i].amount+"</td><td>"+loanData[i].rate+"%"+(loanData[i].addRate?"+"+loanData[i].addRate+"%":"")+"</td><td>"+loanData[i].months+"个月</td><td>"+loanData[i].method+"</td><td>"+loanData[i].strStatus+"</td>");
		 						$(".loanBox").eq(0).find("tbody").append(tr);
		 					}
		 					//分页
		 					
							$("div.holder").jPages({
								containerID : "loadTab",
								previous : "上一页",
								next : "下一页",
								perPage : 10,
								delay : 20
							});
		 				}else{
		 					$("#loadTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
		 					$("div.holder").html("");
		 				}
		 			}
		 		});
	     });
	 		
	    
		
		$(".loanManage ul.nav li").eq(0).click(function(){
			
			$.ajax({
				type: 'post',
				url: '/zycfMarket/loanManagement',
				data: {
					"status": "ALLSTATUS"
				},
				success: function(str){
					zycfZ.exception(str);
					if(str){
						var loanData = str;
						$(".loanBox").eq(0).find("tbody").html("");
						for(var i = 0; i < loanData.length; i++){
							var tr = $("<tr></tr>");
							tr.html("");
							tr.html("<td>"+loanData[i].title+"</td><td>"+loanData[i].amount+"</td><td>"+loanData[i].rate+"%"+(loanData[i].addRate?"+"+loanData[i].addRate+"%":"")+"</td><td>"+loanData[i].months+"个月</td><td>"+loanData[i].method+"</td><td>"+loanData[i].strStatus+"</td>");
							$(".loanBox").eq(0).find("tbody").append(tr);
						}
						//分页
						$("div.holder").jPages({
							containerID : "loadTab",
							previous : "上一页",
							next : "下一页",
							perPage : 10,
							delay : 20
						});
					}else{
		 					$("#loadTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
		 					$("div.holder").html("");
		 			}
				}
			});
		});
		
		//2还款计划
		$(".loanManage ul.nav li").eq(1).click(function(){
			$.ajax({
				type: 'post',
				url: '/zycfMarket/loanRepaymentPlan',
				data: {
					"status": "ALLSTATUS"
				},
				success: function(str){
					zycfZ.exception(str);
					if(str){
						var repayData = str;
						var repayAmount = 0;
						$(".loanBox").eq(1).find("tbody").html("");
						for(var i = 0; i < repayData.length; i++){
							
							if(repayData[i].status == "已还"){
								continue;
							}
							repayAmount += repayData[i].toTleAmount;
						}
						$("#repayDetail span:eq(0)").html("共"+repayData.length+"笔");
						$("#repayDetail span:eq(1)").html("待还"+parseFloat(repayAmount).toFixed(2)+"元");
						for(var i = 0; i < repayData.length; i++){
							var tr = $("<tr></tr>");
							tr.html("");
							tr.html("<td>"+repayData[i].duedate+"</td><td>"+repayData[i].amountinterest+"</td><td>"+repayData[i].amountprincipal+"</td><td>"+repayData[i].toTleAmount+"</td><td>"+repayData[i].status+"</td><td>"+repayData[i].title+"</td>");
							$(".loanBox").eq(1).find("tbody").append(tr);
							
						}
						//分页
						$("div.holder").jPages({
							containerID : "repayTab",
							previous : "上一页",
							next : "下一页",
							perPage : 10,
							delay : 20
						});
						
					}else{

		 					$("#repayTab").html("<tr><td colspan='6'>暂无数据</td></tr>");
		 					$("div.holder").html("");
		 				
					}
				}
			});
		});
		
		//3账户金额
		$(".loanManage ul.nav li").eq(2).click(function(){
			$.ajax({
				type: 'post',
				url: '/zycfMarket/userToBusinessPage',
				success: function(str){
					zycfZ.exception(str);
					if(str.availableAmount){
						$("#allAmount").html(str.availableAmount);
						$("#surplusAmount").html(str.availableAmount);
					}
				}
			});
		});
		
		$(".loanManage ul.nav li").eq(0).addClass("act");
		$(".loanBox").eq(0).show();
		$(".loanManage ul.nav li").click(function(){
			$(".loanManage ul.nav li").removeClass("act");
			$(this).addClass("act");
			$(".loanBox").hide();
			$(".loanBox").eq($(this).index()).show();
		});

		//转账金额
		var regAmount = /^[1-9][0-9]{1,5}$|^1000000$/;//应大于10元且小于100万元
		var isSub = false;
		$("#useAmount").keydown(function(){
			isSub = false;
			$(".loanBox .tip").html("*单笔转账金额应大于10元且小于100万元");
		});
		$("#useAmount").keyup(function(){
			var allAmount =Number($("#allAmount").html());
			var userAmount =$("#useAmount").val();
			if(!regAmount.test(userAmount)){
				$(".loanBox .tip").html("请输入正确的金额");
				return;
			}
			if(Number(userAmount) > allAmount){
				$(".loanBox .tip").html("转账金额必须小于当前可用余额");
				return;
			}
			$("#surplusAmount").html((allAmount- Number(userAmount)).toFixed(2));
			isSub = true;
		});
		
		//转账按钮
		$("#tranBtn").click(function(){
			if(!isSub){return}
			var upmUrl = null;
			if(!regAmount.test($("#useAmount").val())){
				return;
			}
			$.ajax({
				url: '/zycfMarket/userToBusiness',
				type: 'post',
				async: false,
				data: 'TransferAmount='+$("#useAmount").val(),
				success: function(str){
					zycfZ.exception(str);
					//console.log(str.prepareUmp)
					//window.location.href = str.prepareUmp;
					upmUrl = str.prepareUmp;
				}
			});
			var newWin = window.open('_blank');
        	newWin.location = upmUrl;
			$("#RechargeResults").show();
			$("#RechargeResults i").on('click',function(){
				$("#RechargeResults").hide();
			});
			$("#RechargeResults .success a").on('click',function(){
				window.location.reload();
				window.location.hash = 3;
			});
			$("#RechargeResults .failed a").on('click',function(){
				window.location.reload();
				window.location.hash = 4;
				$(".loanManage ul.nav li").removeClass("act");
				$(".loanManage ul.nav li:eq(2)").addClass("act");
				$(".loanManage .loanBox").hide();
				$(".loanManage .loanBox:eq(2)").show();
			});
		});
	},
	withdrawals:function(){
		zycfZ.userName(zycfZ.linkMyAccount,"../myAccount.html","../zyfyLogin.html");
		$('.rechargeMain .sidebarNav li').click(function(){
			window.location.href = "../myAccount.html#"+$(this).index();
		});
		var f = null,orNot=false;
		var minAmount = 0;
		$.ajax({
	        type:"POST",
	        url:"/zycfMarket/userFundWithdraw",
	        dataType:"json",
	        async: false,
	        success:function(data){
	        	zycfZ.exception(data);
	        	if(data.bank == ""){
	        		var content = "<p style='text-align:center;line-height:50px;font-size:14px;margin:20px 0;'>你还未绑定银行卡，请先<a href='../myAccount.html#1' style='color:blue;'>绑卡</a></p>";
					var addStaff = new PopUpBox();
					addStaff.init({
						w:300,
						h:200,
						iNow:1,          // 确保一个对象只创建一次
						opacity:0.7,
						content:content,
						makesure:false, 
			            cancel:false
					});
					$('.rechargeContent .balance strong').html("");
					$('.rechargeContent .bankCard .bankId').html("");
					$('.rechargeContent .bankCard .bank').html("");
					$('.rechargeContent .bankCard a').html("绑定银行卡").attr("href","../myAccount.html#1");
	        	};
	        	minAmount = parseFloat(data.withdrawRates);
	        	$('.rechargeContent .msg').html("手续费："+minAmount.toFixed(2)+"元 ( 由联动优势第三方支付平台扣除 )");
	        	$('.rechargeContent .msgInfo').html("输入的金额应大于"+minAmount.toFixed(2)+"元小于或等于100万元，不能大于账户余额！");
	        	$('.rechargeContent .balance strong').html(data.availableAmount);
	        	$('.rechargeContent .bankCard .bankId').html(data.account);
	        	$('.rechargeContent .bankCard a').html("更换银行卡").attr("href","../myAccount.html#1");
	        	f = data.availableAmount;
	        	$('.rechargeContent .bankCard .bank').html(data.bank).attr("abbr",data.bankCoade);
	        	orNot=true;
	        }
	    });
	    $('.rechargeContent .addMoney input').focus(function(){
	    	if($(this).val()=='请输入提现金额'){
	    		$(this).val('');
	    	};
	    	$('.rechargeContent .msgInfo').hide();
	    });
	    $('.rechargeContent .addMoney input').blur(function(){
	    	if($(this).val()==''){
	    		$(this).val('请输入提现金额');
	    	};
	    });
	    $("#RechargeResults .title i").click(function(){
			$("#RechargeResults").hide();
			window.location.reload();
		});
		$('.rechargeContent .btn input').click(function(){
			var linkURL = null;
			if( !!isNaN( $(".rechargeContent .addMoney input").val() ) ){
				$('.rechargeContent .msgInfo').show();
				return;
			};
			var withdrawLimit = parseFloat($(".rechargeContent .addMoney input").val()); 
			var re = /^[0-9]*$/;
	  	    if( withdrawLimit<=1000000 && withdrawLimit>minAmount && withdrawLimit<=f && f!=0 && orNot ){
	  	    	$.ajax({
	                type:"POST",
	                data : {"operatingLimit":withdrawLimit},
	                url:"/zycfMarket/umpWithdraw",
	                dataType:"json",
	                async: false,
	                success:function(data){
	                	zycfZ.exception(data);
	                	$("#RechargeResults").show();
						linkURL = data.prepareUmp;
	                	$('#uc').html(data.accountname);
	              	    $('#userId').html(data.userid); 
	                }
	            });
	  	    }else{
	  	    	$('.rechargeContent .msgInfo').show();
	  	    };
	  	    if(linkURL){
				window.open(linkURL,'_blank');
			};
		});
	},
	eighteen:function(str){
		var number = str;
		var year = number.substring(6,10);
		if(str.length<=15){
			year = "19"+number.substring(6,8);
		};
		var month = number.substring(10,12);
		var day = number.substring(12,14);
		var myDate = new Date();
		if( (parseInt(year)+18)<myDate.getFullYear() ){
			return true;
		}else if( (parseInt(year)+18)===myDate.getFullYear() ){
			if( parseInt(month)<(myDate.getMonth()+1) ){
				return true;
			}else if( parseInt(month)===(myDate.getMonth()+1) ){
				if( parseInt(day)<=myDate.getDate() ){
					return true;
				}else{
					return false;
				};
			}else{
				return false;
			};
		}else{
			return false;
		};
	},
	myData:function(){
		!function(){
			laydate({elem: '#dataStar'});//绑定元素
		}();
		!function(){
			laydate({elem: '#dataEnd'});//绑定元素
		}();
		!function(){
			laydate({elem: '#proWrapInpS'});//绑定元素
		}();
		!function(){
			laydate({elem: '#proWrapInpE'});//绑定元素
		}();
		!function(){
			laydate({elem: '#fundRecorS'});//绑定元素
		}();
		!function(){
			laydate({elem: '#fundRecorE'});//绑定元素
		}();
	}
};

