$(function(){var a=[];if($.ajax({url:"/zycfMarket/queryBannerPhotos",type:"post",data:{type:"pc_banner"},async:!1,success:function(e){a=e}}),a.length&&a.length>1){for(var e=0,t=a.length;t>e;e++){var r=$("<a class='item'></a>"),s=$('<li data-target="#myCarousel" data-slide-to="'+e+'"></li>');0==e&&(r.addClass("active"),s.addClass("active")),r.attr("href",a[e].jumpAddress?a[e].jumpAddress:"javascript:");var n=$("<img />");n.attr("src",a[e].pathAddress?a[e].pathAddress:"#"),n.appendTo(r),r.appendTo($(".carousel-inner")),s.appendTo($(".carousel-indicators"))}$(".tabBanner").carousel({interval:5e3})}});