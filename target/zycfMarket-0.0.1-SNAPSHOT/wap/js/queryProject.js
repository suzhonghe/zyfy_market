//标的详情
;define(function(require,exports,module){
	require("./common.min.js");
	var id=window.location.search.substring(1);
	var req = Request(); 
	req.ajax({	
		url:"/zycfMarket/queryProjectById",  
		type:'post',  
		data:{"loanId":id}, 
		success:function(str){
			var obj = JSON.parse(str);
			document.querySelector(".firmInfo").innerHTML = obj.firmInfo;
			document.querySelector(".operationRange").innerHTML = obj.operationRange;
			document.querySelector(".repaySource").innerHTML = obj.repaySource;
			document.querySelector(".riskInfo").innerHTML = obj.description;
			document.querySelector(".description").innerHTML = obj.riskInfo;
		},
		error:function(str){
		    alert(str.message)
		}
	});

});
