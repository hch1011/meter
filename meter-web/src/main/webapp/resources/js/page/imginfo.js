/**
 * 
 */
(function ($) {

    'use strict'

    var page = { 
    	
		init: function () {
            var self = this
            self.initData = {};
            self.__initjcrop_api;
            self.__hasInitjcrop_api = false;
            self.__init();
            self.__initEvent();
        },

        
        __initEvent: function () {
            var self = this
            $('.leftmenu li').click(function(e){
                e.preventDefault();
                var liDom = $(this);
                var liDeviceId = liDom.find('a').attr("data-deviceId");
                $('.device_id').val(liDeviceId)
                self.__loadImgInfo(liDeviceId);
            })

            // 选取识别区域
            $('.img-info-body .btn-b').click(function(){
                self.__submitRang( )
            })

            //图形重新采集
            $('.img-info-body .btn-a').click(function(){
                $('.img-info-body img').attr("src", window.basePath + "/resources/images/demo/shapshot_loading.jpg");
                var urlPath = window.basePath + '/camera/preview?deviceInfoId=' + $('.device_id').val()
                $.ajax({
                    url: urlPath,
                    type: 'get',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function(data){
            	    	if(data.result == 'sucess'){
            	    		var url = data.data;
            	    		$('.img-info-body img').attr("src", url)
            	    		self.__initjcrop_api()
            	    		return;
            	    	}else{
            	    		alert(data.screenMessage)
            	    		return;
            	    	}
            	    	alert("服务器错误")
                    },
                    error: function(errorcode){
                        throw new Error(errorcode.status)
                    }
                })
            })
		},

        __init: function() {
            var self = this
            $('.leftmenu dd').eq(0).find('ul li').eq(0).addClass('active');
            var liDeviceId = $('.leftmenu dd').eq(0).find('ul li').eq(0).find('a').attr("data-deviceId");
            $('.device_id').val(liDeviceId)
            self.__loadImgInfo(liDeviceId)
        },

        __loadImgInfo: function(deviceId) {
            var self = this

            if(self.__jcrop_api != null || self.__jcrop_api!= undefined) {
                self.__jcrop_api.destroy()
            }
            
            var liDeviceId = deviceId;
            var urlPath = window.basePath + '/device/data/img/info/one?deviceId=' + liDeviceId
            $.ajax({
                url: urlPath,
                type: 'get',
                dataType: 'json',
                contentType: 'application/json',
                success: function(res){
                	self.initData = res
                    if(res.deviceInfo.pictureLocalPath != null) {
                        $('.img-info-body img').attr("src", window.basePath + "/deviceSnapshot/" + res.deviceInfo.pictureLocalPath)
                        self.__initjcrop_api()
                    } else {
                        $('.img-info-body img').attr("src", window.basePath + "/resources/images/demo/shapshot_notfound.jpg")
                    }
                    $('.img-info-body .label-a').html(res.deviceType.dataName+"("+res.deviceType.dataUnit+")")
                    $('.img-info-body .i-a').val(res.deviceInfo.snapData)
                    $('.img-info-body .i-b').val(res.date)
                    $('.img-info-body .label-c').html(res.deviceType.dataName+"变化率("+res.deviceType.changeRateUnit+")")
                    $('.img-info-body .i-c').val(res.deviceInfo.changeRate)
                    $('.img-info-body .i-d').val(res.time) 
                     
                },
                error: function(errorcode){
                    throw new Error(errorcode.status)
                }
            })
        },

        __initjcrop_api : function (){
            var self = this

        	//初始化原来的区域
        	var rang_x = self.initData.deviceInfo.x;
        	var rang_y = self.initData.deviceInfo.y;
        	var rang_w = self.initData.deviceInfo.w;
        	var rang_h = self.initData.deviceInfo.h;
        	
        	if(rang_x == '' || rang_x <= 0)rang_x = 80;
        	if(rang_y == '' || rang_y <= 0)rang_y = 80;
        	if(rang_w == '' || rang_w <= 0)rang_w = 440;
        	if(rang_h == '' || rang_h <= 0)rang_h = 200;
        	rang_x = parseInt(rang_x);
        	rang_y = parseInt(rang_y);
        	rang_w = parseInt(rang_w);
        	rang_h = parseInt(rang_h);
        	
        	if(self.__jcrop_api != null){
        		self.__jcrop_api.destroy()
        	}
            self.__jcrop_api = $.Jcrop('.img-info-body img',{
                onChange: showCoords,  
                //onSelect: showCoords,
                //onSelect: self.__submitRang,
                setSelect: [ rang_x, rang_y, rang_x + rang_w, rang_y + rang_h]
            });
        },
        
        showCoords : function (c){
            $('#rang_x').val(c.x);  
            $('#rang_y').val(c.y); 
            $('#rang_w').val(c.w);
            $('#rang_h').val(c.h);  
        },
        
        __submitRang : function () {
            var x = $('#rang_x').val();
            var y = $('#rang_y').val();
            var w = $('#rang_w').val();
            var h = $('#rang_h').val();
            if(x<0 || y<0 || m<10 || h <10){
            	alert("请选择正确的范围");
            }
            var deviceId = $('.device_id').val()
            var imagePath = $('.img-info-body img').attr("src");
            var urlPath = window.basePath + '/camera/range?deviceInfoId=' + deviceId  + '&x=' + x + '&y=' + y + '&w=' + w + '&h=' + h
             $.ajax({
                url: urlPath,
                type: 'put',
                dataType: 'json',
                success: function(res){
                    alert(res.success)

                },
                error: function(errorcode){
                    throw new Error(errorcode.status)
                }
            })
        }
        
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
