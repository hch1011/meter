function bindCameraAndDevice(){
	var basePath = $("#basePath").val(); 
	var cameraSerial = $("#cameraSerial").val();
	var deviceInfoId = $("#deviceInfoId").val();
	if(deviceInfoId == ''){
		alert("请选择设备");
		return;
	}
	var url = basePath+"/camera/bind?cameraSerial="+cameraSerial+"&deviceInfoId="+deviceInfoId;
	$.ajax({
	    url: url,
	    type: 'post',
	    cache:false,
	    dataType:'json',
	    success:function(data) {
	    	alert("修改成功");
	    },
	    error : function() {
	    	alert("通信错误");  
	    }
	});
}

function preview(){
	$("#divPreView").html("正在获取图片..." );
	
	var basePath = $("#basePath").val(); 
	var cameraSerial = $("#cameraSerial").val();
	var deviceInfoId = $("#deviceInfoId").val();
	var url = basePath+"/camera/preview?cameraSerial="+cameraSerial+"&deviceInfoId="+deviceInfoId;
	$.ajax({
	    url: url,
	    type: 'get',
	    cache:false,
	    dataType:'json',
	    success:function(data) {
	    	if(data.result == 'sucess'){
	    		var url = data.data;
	    		$("#divPreView").html("<img id='img_preview' width='600px'  src='"+url+"'>" );
	    		$("#img_preview").load(
	    			initJcrop()
	    		);
	    		return;
	    	}else{
	    		alert(data.screenMessage)
	    		return;
	    	}
	    	alert("服务器错误")
	    },
	    error : function() {
	    	 alert("通信错误");  
	    }
	});
}


function saveRang(){
	var basePath = $("#basePath").val(); 
	var deviceInfoId = $("#deviceInfoId").val();
	var x = $("#rang_x").val();
	var y = $("#rang_y").val();
	var w = $("#rang_w").val();
	var h = $("#rang_h").val();
	
	if(w < 10 || h < 10){
		alert("识别范围不正确，请从新选择");
		return;
	}
	
	var url = basePath+"/camera/range?deviceInfoId="+deviceInfoId+"&x="+x+"&y="+y+"&w="+w+"&h="+h;
	$.ajax({
	    url: url,
	    type: 'put',
	    cache:false,
	    dataType:'json',
	    success:function(data) {
	    	if(data.result == 'sucess'){
	    		var url = data.data;
	    		alert("成功保存");
	    		return;
	    	}else{
	    		alert(data.screenMessage)
	    		return;
	    	}
	    	alert("服务器错误")
	    },
	    error : function() {
	    	 alert("通信错误");  
	    }
	});
}

function initJcrop(){
	//initvalue
	var img = $("#img_preview");
	//var showWidth = 600; //显示的宽度,这里的宽度要与存盘文件一致，否则切割时存在偏差,属性文件设置
	//img.css("width",showWidth+'px').css("height","auto");
 
	var rang_x = $("#rang_x").val();
	var rang_y = $("#rang_y").val();
	var rang_w = $("#rang_w").val();
	var rang_h = $("#rang_h").val();
	
	if(rang_x == '' || rang_x <= 0)rang_x = 0;
	if(rang_y == '' || rang_y <= 0)rang_y = 0;
	if(rang_w == '' || rang_w <= 0)rang_w = 600;
	if(rang_h == '' || rang_h <= 0)rang_h = 500;
	rang_x=parseInt(rang_x);
	rang_y=parseInt(rang_y);
	rang_w=parseInt(rang_w);
	rang_h=parseInt(rang_h);
	
	$('#img_preview').Jcrop({  
        onChange: showCoords,  
        onSelect: showCoords,
        //bgColor:'red'
        bgOpacity: 0.8,  
        borderOpacity: 0.8,
        setSelect: [ rang_x, rang_y, rang_x + rang_w, rang_y + rang_h]
    }); 
}

function showCoords(c){
    jQuery('#rang_x').val(c.x);  
    jQuery('#rang_y').val(c.y); 
    jQuery('#rang_w').val(c.w);
    jQuery('#rang_h').val(c.h);  
};  

function recognition(){ 
	$("#divResult").html("识别中...");
	
	var basePath = $("#basePath").val(); 
	var deviceInfoId = $("#deviceInfoId").val();
	var imageUrl =  $("#img_preview")[0].src;
 
	var json = {};
	json.needRecognition = true;
	json.ysUrl = imageUrl; 
	json.camaraSerial = $("#cameraSerial").val(); 
	json.deviceInfoId = $("#deviceInfoId").val(); 
	
	var url = basePath+"/camera/captureSuite";
	
	$.ajax({
	    url: url,
	    type: 'post',
	    data: JSON.stringify(json),
	    dataType:'json',
	    contentType: "application/json", 
	    success:function(data) {
	    	if(data.result == 'sucess'){  //controller的返回值
	    		data = data.data;//识别结果对象
	    		if(data.result == 'success'){
	    			$("#divResult").html(data.value);
	    			if(data.value >= 100){
	    				$("#divResult").html(data.value + "(这是模拟结果，"+data.screenMessage+")");
	    			}
	    		}else{
	    			$("#divResult").html(data.screenMessage); 
	    		}
	    		return;
	    	}else{
    			$("#divResult").html(data.screenMessage); 
	    		return;
	    	}
			$("#divResult").html("服务器错误");
	    },
	    error : function() {
			$("#divResult").html("通信错误");
	    }
	});
}; 

preview();