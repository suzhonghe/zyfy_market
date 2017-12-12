//提现记录
;define(function(require,exports,module){
	require("./common.min.js");
	var oWithdrawalsRecord = document.querySelector(".withdrawalsRecord");
	var oWrap = document.querySelector(".wrap");
    
    var iPage = 1;
	var flag = true;

	var getWithdrawalsRecord = require("./fundRecord.js");
	getWithdrawalsRecord({"page":iPage,"num":10,"type":"WITHDRAW"},ReRecordData); //获取取现记录type=WITHDRAW
	function ReRecordData(obj){
		if(!obj.results.length || obj.results.length<1){
	    	var noMore = document.createElement("div");
	    	noMore.className = "noMore";
	    	if(iPage == 1){
	    		noMore.innerHTML = "暂无数据";
	    	}else{
	    		noMore.innerHTML = "数据已加载完毕";
	    	};
	    	oWrap.appendChild(noMore);
	    }else{
	    	setData(obj);
	    	flag = true;
	    };
	    function setData(obj){
			for(var i=0;i<obj.results.length;i++){
				var oARecord = document.createElement("div");
                oARecord.className = "aRecord";
				oARecord.innerHTML = '<div class="aRows">\
					<p class="alignLeft"><em>订单号</em><span>'+obj.results[i].orderid+'</span></p>\
					<p class="alignRight success">'+obj.results[i].status+'</p>\
				</div>\
				<div class="aRows">\
					<p class="alignLeft">金额</p>\
					<p class="alignRight">￥'+parseFloat(obj.results[i].amount).toFixed(2)+'</p>\
				</div>\
				<div class="aLine"></div>\
				<div class="aRows">\
					<p class="alignLeft">发生时间</p>\
					<p class="alignRight">'+obj.results[i].strTimeRecorded+'</p>\
				</div>';
				oWrap.appendChild(oARecord);
			};
		};
	};
	oWrap.onscroll = function() {
		var oChildren = document.querySelectorAll(".aRecord");
		var len = oChildren.length;
		var oEle = oChildren[len-1];
		if ( (getTop( oEle ) + oEle.offsetHeight) < (oWrap.clientHeight + oWrap.scrollTop) ) {
			if ( flag ) {
				flag = false;
				iPage++;
				getWithdrawalsRecord({"page":iPage,"num":10,"type":"DEPOSIT"},ReRecordData);
			}
		}
	}
	function getTop(obj) {
		var iTop = 0;
		while(obj) {
			iTop += obj.offsetTop;
			obj = obj.offsetParent;
		}
		return iTop;
	};
});
