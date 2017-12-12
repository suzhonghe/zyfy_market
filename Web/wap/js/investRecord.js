//标的详情
;define(function(require,exports,module){
	require("./common.min.js");
	var id=window.location.search.substring(1);
	var req = Request(); 
	req.ajax({	
		url:"/zycfMarket/queryInvestRecord",  
		type:'post',  
		data:{"field":"loanId",
	          "value":id,
	          "start":1,
	          "length":100,
	          "sort":"desc"}, 
		success:function(str){
			var obj = JSON.parse(str);
			var oRecord = document.querySelector(".record");
			if(!obj || !obj.data || !obj.data.length ){
				var oDiv = document.createElement("div");
				oDiv.className = "alignCenter";
				oDiv.innerHTML = "暂无数据";
				oRecord.appendChild(oDiv);
			}else{
				for(var i=0,len=obj.data.length;i<len;i++){
					var oDiv = document.createElement("div");
					oDiv.className = "one";
				    oDiv.innerHTML = '<div class="seq">'+(i+1)+'</div>\
			    					<div class="alignCenter">\
			    						<p class="user">'+obj.data[i].userName+'</p>\
			    						<p class="time">'+obj.data[i].time+'</p>\
			    					</div>\
			    					<div class="alignRight">'+parseFloat(obj.data[i].amount).toFixed(2)+'元</div>';
			    	oRecord.appendChild(oDiv);
				}
			}
		},
		error:function(str){
		    alert(str.message)
		}
	});

});
