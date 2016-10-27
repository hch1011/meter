function bind(){
	var cameraSerial = $("#cameraSerial").val();
	var deviceInfoId = $("#deviceInfoId").val();
	var basePath = $("#basePath").val();
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
	    	alert("修改成功！");
	    	
	    },
	    error : function() {
	    	 alert("异常！");  
	    }
	});
}

function preview(){
	var cameraSerial = $("#cameraSerial").val();
	var deviceInfoId = $("#deviceInfoId").val();
	var basePath = $("#basePath").val(); 
	var url = basePath+"/camera/preview?cameraSerial="+cameraSerial+"&deviceInfoId="+deviceInfoId;
	$.ajax({
	    url: url,
	    type: 'get',
	    cache:false,
	    dataType:'json',
	    success:function(data) {
	    	if(data.result == 'sucess'){
	    		var url = data.data;
	    		$("#preView").html("<img style='width: 600px;height:500px;' src='"+url+"'>" );
	    		return;
	    	}else{
	    		alert(data.msg)
	    		return;
	    	}
	    	
	    	alert("服务器错误")
	    },
	    error : function() {
	    	 alert("异常！");  
	    }
	});
}

