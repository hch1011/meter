/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this
            
            self.__initEvent();
            $('.device-data-body').find('.beginDate, .endDate').datetimepicker({
                format: 'yyyy-mm-dd',
                minView: 'month',
                showButtonPanel: true,
                autoclose: true,
                language: 'zh-CN',
                todayHighlight:true,
                initialDate: new Date()
            });
        },
        
        __initEvent: function () {
            var self = this
			$('.btn-primary').click(function(e){
				e.stopPrapagation 
				self.queryDate();
			})
		}, 
		queryDate: function () {
            var self = this
        	var input_deviceId=$("#input_deviceId").val();
        	if(input_deviceId == null || input_deviceId == ""){
        		alert("请选择设备");
        		return;
        	}

            $("#placeholder").html("正在查询数据..." );
        	var basePath = window.basePath;
        	var url = basePath+"/device/data/info/json";
        	$.ajax({
        	    url: url,
        	    type: 'get',
        	    cache:false,
        	    dataType:'json',
        	    data:$("#deviceDateForm").serialize(),
        	    success:function(data) {
        	    	if(data.result == 'sucess'){
        	    		var titleTest =  '历史数据曲线图';
        	    		//titleTest = deviceType.dataName() + titleTest;
        	            var xData = [];
        	            var yData = [];
        	            data.list.forEach(function(e){
        	            	xData.push(e.o2);
        	            	yData.push(e.o3);
        	            });  
         	    		var myChart = echarts.init(document.getElementById('placeholder'));
         	    		var option = {
         	    			    title: {
         	    			        text: titleTest,
         	    			        show:true
         	    			    },
         	    			    tooltip: {
         	    			    	show: true,
         	    			        trigger: 'axis'
         	    			    },
         	    			    xAxis: {
         	    			        type: 'category',
         	    			        boundaryGap: false,
         	    			        data: xData
         	    			    },
         	    			    yAxis: {
         	    			        type: 'value'
         	    			    },
         	    		        toolbox: {
         	    		            left: 'center',
         	    		            feature: {
         	    		                dataZoom: {
         	    		                    yAxisIndex: 'none'
         	    		                },
         	    		                restore: {},
         	    		                saveAsImage: {}
         	    		            }
         	    		        }, 
         	    			    series: [{
         	    			        name: '',
         	    			        type: 'line',
         	    			        smooth: false,
         	    			        data:yData
         	    			    }]
         	    			};
 
        	    	        myChart.setOption(option);
        	    	   return;
        	    	}else{
        	    		alert(data.screenMessage)
        	    		return;
        	    	}
        	    	$("#placeholder").html("服务器错误!" );
        	    },
        	    error : function() {
         	    	$("#placeholder").html("通信错误!" );
        	    	 return;
        	    }
        	});
        }
    }

    $(function () {
        page.init()
    })
})(jQuery)
