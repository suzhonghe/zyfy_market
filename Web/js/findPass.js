$(function(){$(".placeholder").inputDefault();var i=!1,e=!1,s=!1,l=!1;$("input[name='mobileNumber']").focus(function(){$(".mobileNum .tip").css("display","none"),$(".mobileNum i").css("display","none")}),$("input[name='mobileNumber']").blur(function(){""==$(this).val()&&$(".mobileNum .tip").css("display","inline-block").html("请输入手机号")}),$(".getMobCode").click(function(){""!=$("input[name='mobileNumber']").val()&&checkMobile($("input[name='mobileNumber']").val())&&$.ajax({url:"/zycfMarket/resetPasswdMsg",type:"post",data:{mobile:$("input[name='mobileNumber']").val()},success:function(e){if(0==e.code)$(".mobileNum .tip").css("display","inline-block").html("手机号还未在平台注册");else if(1==e.code){i=!0,$(".mobileNum i").css("display","inline-block"),$(".getMobCode").hide();var s=60,l=null;$(".reGet").show().html(s+"秒后可重新获取"),l=setInterval(function(){s--,0>=s&&(clearInterval(l),$(".reGet").hide(),$(".getMobCode").show().html("重新获取验证码")),$(".reGet").html(s+"秒后可重新获取")},1e3)}else 5==e.code&&($(".mobileNum .tip").css("display","inline-block").html("请求次数过多，每天不能多于2次"),$(".getMobCode").hide(),$(".mobileNum i").css("display","none"))}}),""==$("input[name='mobileNumber']").val()?$(".mobileNum .tip").css("display","inline-block").html("请输入手机号"):checkMobile($("input[name='mobileNumber']").val())||$(".mobileNum .tip").css("display","inline-block").html("请输入正确手机号")}),$("input[name='verCode']").focus(function(){$(".verifyCode .tip").css("display","none"),$(".verifyCode i").css("display","none")}),$("input[name='verCode']").blur(function(){""==$(this).val()?$(".verifyCode .tip").css("display","inline-block").html("验证码不能为空"):i?$.ajax({type:"POST",url:"/zycfMarket/resetPasswdValidate",data:{resetCode:$(this).val()},success:function(i){0==i.code?(e=!0,$(".verifyCode i").css("display","inline-block")):1==i.code?$(".verifyCode .tip").css("display","inline-block").html("验证码不正确"):2==i.code&&$(".verifyCode .tip").css("display","inline-block").html("验证码已失效，请重新获取")}}):$(".verifyCode .tip").css("display","inline-block").html("请先获取手机验证码")}),$("input[name='newPass']").focus(function(){$(".newPassword .tip").css("display","none"),$(".newPassword i").css("display","none")}),$("input[name='newPass']").blur(function(){""==$(this).val?$(".newPassword .tip").css("display","inline-block").html("密码不能为空"):testNum($(this).val())?(s=!0,$(".newPassword i").css("display","inline-block")):$(".newPassword .tip").css("display","inline-block").html("登陆密码格式不正确")}),$("input[name='rePass']").focus(function(){$(".confPassword .tip").css("display","none"),$(".confPassword i").css("display","none")}),$("input[name='rePass']").blur(function(){$(this).val()==$("input[name='newPass']").val()?($(".confPassword i").css("display","inline-block"),l=!0):$(".confPassword .tip").css("display","inline-block").html("2次输入密码不一致")}),$("#alertFloor").click(function(){e&&s&&l?$.ajax({type:"POST",url:"/zycfMarket/user/resetPasswd",data:{password:$("input[name='newPass']").val()},success:function(i){alert(i.message),0==i.code&&$(".nextFloor").show()}}):e?s?l||$(".confPassword .tip").css("display","inline-block").html("2次输入密码不一致"):$(".newPassword .tip").css("display","inline-block").html("登陆密码格式不正确"):$(".verifyCode .tip").css("display","inline-block").html("验证码不正确")}),$("#login").click(function(){window.location.href="zyfyLogin.html"})});