$(function(){function o(o){var i=o.url||"",n=o.data||"",e=null;return $.ajax({url:i,type:"post",data:n,async:!1,success:function(o){e=o?o:"加载超时"},error:function(){e="加载失败"}}),e}$.cookie("loginname")&&$("#loginName").val($.cookie("loginname")),$.cookie("status")&&$("#rem").addClass("checked"),$(".placeholder").inputDefault(),$(".remAcc span").click(function(){$("#rem").toggleClass("checked")}),$("#loginName").focus(function(){$(".tipBox p.tip").hide()}),$("#loginPass").focus(function(){$(".tipBox p.tip").hide()}),$(document).keydown(function(o){13==o.keyCode&&$(".loginBtn").click()});var i=!0;i&&$(".loginBtn").click(function(){i=!1;var n=$("#loginName").val(),e=$("#loginPass").val();if(""==n||""==e)return $(".tipBox p.tip").show().html("用户名或密码不能为空"),void(i=!0);var t;t=o({url:"/zycfMarket/login",data:{loginname:n,passphrase:e}}),t||(i=!0),"加载超时"==t?$(".tipBox p.tip").show().html("连接超时"):"加载失败"==t?$(".tipBox p.tip").show().html("连接失败"):1==t.code?($("#rem").hasClass("checked")&&($.cookie("loginname",n,{expires:7}),$.cookie("status","1")),window.location.href="/index.html"):$(".tipBox p.tip").show().html(t.message)})});