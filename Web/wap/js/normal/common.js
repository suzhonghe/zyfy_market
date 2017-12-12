;(function(){
function minMaxRandom(under,over){
	switch(arguments.length){
		case 1: return parseInt(Math.random()*under);
		case 2: return (parseInt(Math.random()*(over-under+1))+parseInt(under));
		default:return 0;
	};
};

function Request() {
};
Request.prototype = {
	ajax : function(args) {
		this.options = {
			type : 'GET',
			dataType : 'text',
			async : true,
			avatar : null,
			contentType : 'application/x-www-form-urlencoded',
			url : 'about:blank',
			data : {},
			success : {},
			error : {}
		};
		if (!args) {
			console.error('please fill in any parameters first!');
			return;
		} else if (!args.url) {
			console.error('url is required parameters, please check your parameters!');
			return;
		} else if (!args.success || typeof args.success != 'function') {
			console.error('the callback function is lost!');
			return;
		}
		this.shift(this.options, args);
		this.send();
	},
	jsonp : function(args) {
		this.options = {
			type : 'JSONP',
			jsonpName : '',
			dataType : 'text',
			async : true,
			avatar : null,
			url : 'about:blank',
			success : function(){},
			data : {}
		};
		if (!args) {
			console.error('please fill in any parameters first!');
			return;
		} else if (!args.url) {
			console.error('url is required parameters, please check your parameters!');
			return;
		} else if (!args.jsonpName) {
			args.jsonpName = 'jsonpCallbackFunctionNo' + new Date().getTime() + minMaxRandom(0, 9999);
		};

		this.shift(this.options, args);
		if (window[this.options.jsonpName] && window[this.options.jsonpName] !== this.options.success) {
			console.error('jsonpName already exists!');
			return;
		}
		window[this.options.jsonpName] = this.options.success;
		this.create();
	},
	create : function() {
		var script = document.createElement('script'), argStr = /[\?]/g.test(this.options.url) ? '&' : '?';
		for (var key in this.options.data) {
			argStr += key + '=' + this.options.data[key] + '&';
		}
		argStr = argStr + 'callback=' + this.options.jsonpName;
		script.async = this.options.async;
		script.src = this.options.url + (argStr == '?' ? '' : argStr);
		document.getElementsByTagName('head')[0].appendChild(script);
	},
	XmlHttp : function() {
		var xmlHttp;
		try {
			xmlhttp = new XMLHttpRequest();
		} catch(e) {
			try {
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		};
		if (!xmlhttp) {
			return false;
		};
		return xmlhttp;
	},
	send : function() {
		var xmlHttp = this.XmlHttp(), linkSign = /[\?]/g.test(this.options.url) ? '&' : '?', argStr = '', _this = this, length = this.options.data ? this.options.data.length : 0;
		for (var key in this.options.data) {
			argStr += key + '=' + this.options.data[key] + '&';
		}
		argStr = argStr.replace(/\&$/g, '');
		if (this.options.type.toUpperCase() == 'GET') {
			xmlHttp.open(this.options.type, this.options.url + (argStr == '' ? '' : linkSign + argStr), this.options.async);
		} else {
			xmlHttp.open(this.options.type, this.options.url, this.options.async);
		}
		xmlHttp.setRequestHeader('Content-Type', this.options.contentType);
		xmlHttp.onreadystatechange = function() {
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200 || xmlHttp.status == 0) {
					if ( typeof _this.options.success == 'function') {
						var responseData = _this.options.dataType == 'text' ? xmlHttp.responseText : xmlHttp.responseXML;
						_this.options.success(responseData, _this.options.dataType,_this.options.avatar);
					}
					xmlHttp = null;
				} else {
					if ( typeof _this.options.error == 'function')
						_this.options.error('Server Status: ' + xmlHttp.status);
				}
			};
		};
		xmlHttp.send(this.options.type.toUpperCase() == 'POST' ? argStr : null);
	},
	shift : function(o, args) {
		for (var i in args) {
			o[i] = args[i];
		};
		return o;
	}
}; 
	
 window['Request'] = function(){
 	return new Request();
 };
})();

/*用法：
var req = Request();  //方法实例化
req.jsonp({
	url:iUrl+'&content='+str, //请求路径及数据 
	success:function(d){
	    //请求成功
	}
})

req.ajax({	
	url:iUrl,  //请求路径
	type:'post',  //请求方式  get||post
	data:{proID:proID,type:type},  //请求数据
	success:function(str){
	    //请求成功
	},
	error:function(){
	    alert('加载失败')
	}
})
*/

;function bind(obj, evname, fn) {
	if (obj.addEventListener) {
		obj.addEventListener(evname, fn, false);
	} else {
		obj.attachEvent('on' + evname, function() {
			fn.call(obj);
		});
	}
	return false;
};

function addEvents(target, type, func) {  
    if (target.addEventListener)    //非ie 和ie9  
        target.addEventListener(type, func, false);  
    else if (target.attachEvent)   //ie6到ie8  
        target.attachEvent("on" + type, func); 
};  

function removeEvents(target, type, func){  
    if (target.removeEventListener)  
        target.removeEventListener(type, func, false);  
    else if (target.detachEvent)  
        target.detachEvent("on" + type, func);  
    else target["on" + type] = null;  
};  

/*var floatWindow = new PopUpBox();
    var content = "我是内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容"
	floatWindow.init({
		iNow:0,          // 确保一个对象只创建一次
		tBar:true,      // 是否有标题条，默认有标题条
		title:"我是标题",
		content:content,     // 内容
		callback:function(){alert(1)}   // 点击确定按钮后的回调函数
	});*/
function PopUpBox(){
	this.t = null;
	this.oParent = null;
	this.oDiv = null;
	this.oTDiv = null;
	this.settings = {   // 默认参数
		w:null,          // 弹窗宽，不填默认
		h:null,          // 弹窗高，不填默认
		mark : true,    // 是否有遮罩，默认有
		time:null,      // 自动关闭窗口的时间 为空或者'undefined'则不关闭,时间是毫秒
		tBar:true,      // 是否有标题条，默认有标题条
		title :'',     // 标题名称
		cBar:true,      // 是否有内容，默认有内容
		content:'',     // 内容
		workBar:true,  // 是否有操作栏（确定和取消）
		makesure:true,  // 是否有确定按钮，默认有确定按钮
		makesureInner:"确定",
		cancel:true,    // 是否有取消按钮，默认有取消按钮
		cancelInner:"取消", 
		callback:null   // 点击确定按钮后的回调函数
	};
};
PopUpBox.prototype = {
	constructor:PopUpBox,
	json:{},
	init:function(opt){
		this.extend(this.settings,opt);
		if( this.json[opt.iNow] == undefined ){
			this.json[opt.iNow] = true;
		};
		if( this.json[opt.iNow] ){
			this.createFrame();
			this.json[opt.iNow] = false;
		};
	},
	createFrame:function(){    // 创建大外框
		this.oParent = document.createElement('div');
		this.oParent.className = "popup";
		this.oDiv = document.createElement('div');
		this.oDiv.className = "main";
		if(this.settings.w){
			this.oDiv.style.width = this.settings.w+"px";
		};
		if(this.settings.h){
			this.oDiv.style.height = this.settings.h+"px";
		};
		this.oParent.appendChild(this.oDiv);
		if(this.settings.mark){
			this.createMark();
		};
		if(this.settings.tBar){
			this.createTitle();
		};
		if(this.settings.cBar){
			this.createContent();
		};
		if(this.settings.workBar){
			this.createWorkBar();
		};
		document.body.appendChild(this.oParent);
		if(this.settings.time && this.settings.time != 0){
			var This = this;
			this.t && clearTimeout(this.t);
			this.t = setTimeout(function(){
				document.body.removeChild(This.oParent);
				This.json[This.settings.iNow] = true;
			},This.settings.time);
		};
	},
	createTitle:function(){     // 创建标题
		this.oTDiv = document.createElement('div');
		this.oTDiv.className = "title";
		this.oTDiv.innerHTML = "<span>"+this.settings.title+"</span>";
        if(this.oDiv.children[0]){
        	this.oDiv.insertBefore(this.oTDiv,this.oDiv.children[0]);
        }else{
        	this.oDiv.appendChild(this.oTDiv);
        }
	},
	createContent:function(){    // 内容
		this.oCDiv = document.createElement('div');
		this.oCDiv.className = "content";
		this.oCDiv.innerHTML = this.settings.content;
        this.oDiv.appendChild(this.oCDiv);
	},
	createWorkBar:function(){
		var This = this;
		this.oBDiv = document.createElement('div');
		this.oBDiv.className = "bBtn";
        if(this.settings.makesure){
        	this.makesureBtn = document.createElement('div');
        	this.makesureBtn.className = "makesure";
        	this.makesureBtn.innerHTML = this.settings.makesureInner;
        	this.oBDiv.appendChild(this.makesureBtn);
        	this.makesureBtn.onclick = function(){
        		This.settings.callback && This.settings.callback();
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        if(this.settings.cancel){
        	this.cancelBtn = document.createElement('div');
        	this.cancelBtn.className = "cancel";
        	this.cancelBtn.innerHTML = this.settings.cancelInner;
        	this.oBDiv.appendChild(this.cancelBtn);
        	this.cancelBtn.onclick = function(){
        		document.body.removeChild(This.oParent);
        		This.json[This.settings.iNow] = true;
        	};
        };
        this.oDiv.appendChild(this.oBDiv);
	},
	createMark:function(){    // 创建遮罩层
		this.oMark = document.createElement('div');
		this.oMark.className = "mark";
		this.oMark.style.zIndex = 3;
		this.oParent.appendChild(this.oMark);
	},
	viewWidth:function(){    // 可视区宽
		return document.documentElement.clientWidth;
	},
	viewHeight:function(){   // 可视区高
		return document.documentElement.clientHeight;
	},
	extend:function(obj1,obj2){
		for(var i in obj2){
			obj1[i]=obj2[i];
		};
	}
};

//判断手机号
function checkMobile(s){
	var regu =/^[1][34578][0-9]{9}$/;
	return regu.test(s);
};
//判断6-16位数字与字母组合
function testNum(s){
	var reg=/^(([a-zA-Z]+[0-9]+)|([0-9]+[a-zA-Z]+))[a-zA-Z0-9]*$/;
	var reg1=/^\S{6,16}$/;
	return reg.test(s) && reg1.test(s);
};
//验证中文
function checkChinese(s) {
    var re = /^[\u4e00-\u9fa5]+$/; 
    return re.test(s);
};
//判断身份证号码
function checkCard(s) {
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
    return reg.test(s);
};

function setCookie(name, value, opts){
    //opts maxAge, path, domain, secure
    if(name && value){
        var cookie = escape(name) + '=' + escape(value);
        //可选参数
        if(opts){
            if(opts.maxAge){
                cookie += '; max-age=' + opts.maxAge; 
            }
            if(opts.path){
                cookie += '; path=' + opts.path;
            }
            if(opts.domain){
                cookie += '; domain=' + opts.domain;
            }
            if(opts.secure){
                cookie += '; secure';
            }
        }
        document.cookie = cookie;
        return cookie;
    }else{
        return '';
   }
}

function getCookie(c_name){
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=");
		if (c_start!=-1){ 
		    c_start=c_start + c_name.length+1 ;
		    c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1) c_end=document.cookie.length
		    return unescape(document.cookie.substring(c_start,c_end));
		} ;
	};
	return "";
};

function removeCookie(key) {
	setCookie(key, '', -1);
};

function addClass(obj, sClass) { 
    var aClass = obj.className.split(' ');
    if (!obj.className) {
        obj.className = sClass;
        return;
    };
    for (var i = 0; i < aClass.length; i++) {
        if (aClass[i] === sClass) return;
    };
    obj.className += ' ' + sClass;
};

function removeClass(obj, sClass) { 
    var aClass = obj.className.split(' ');
    if (!obj.className) return;
    for (var i = 0; i < aClass.length; i++) {
        if (aClass[i] === sClass) {
            aClass.splice(i, 1);
            obj.className = aClass.join(' ');
            break;
        }
    };
};

function hasClass(obj, sClass) { 
    var aClass = obj.className.split(' ');
    if (!obj.className) {
        return false;
    };
    for (var i = 0; i < aClass.length; i++) {
        if (aClass[i] === sClass) return true;
    };
    return false;
};
