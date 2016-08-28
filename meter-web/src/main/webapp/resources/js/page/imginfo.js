/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this
            self.__jcrop_api;
            self.__init();
            self.__initEvent();



        },

        __initEvent: function () {
            var self = this
            $('.leftmenu li').click(function(e){
                e.preventDefault();
                if(self.__jcrop_api != null || self.__jcrop_api!= undefined) {
                    self.__jcrop_api.destroy()
                }
                var liDom = $(this);
                var liDeviceId = liDom.find('a').attr("data-deviceId");
                self.__getImgInfo(liDeviceId);
                $('.device_id').val(liDeviceId)

            })

            //选取识别区域
            $('.img-info-body .btn-b').click(function(){
                self.__jcrop_api = $.Jcrop('.img-info-body img',{
                    onSelect: self.__showCoords
                });
            })

            //图形重新采集
            $('.img-info-body .btn-a').click(function(){
                var deviceId = $('.device_id').val()
                var urlPath = window.basePath + '/device/data/img/recollect?deviceId=' + deviceId
                $.ajax({
                    url: urlPath,
                    type: 'get',
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function(res){
                        alert(res.success)

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
            self.__getImgInfo(liDeviceId);
        },

        __getImgInfo: function(deviceId) {
            var liDeviceId = deviceId;
            var urlPath = window.basePath + '/device/data/img/info/one?deviceId=' + liDeviceId
            $.ajax({
                url: urlPath,
                type: 'get',
                dataType: 'json',
                contentType: 'application/json',
                success: function(res){
                    $('.img-info-body .i-a').val(res.deviceInfo.snapData)
                    $('.img-info-body .i-b').val(res.date)
                    $('.img-info-body .i-c').val(res.deviceInfo.changeRate)
                    $('.img-info-body .i-d').val(res.time)
                    if(res.deviceData == null || res.deviceData.pictureUrl == null || res.deviceData.pictureUrl == "") {
                        alert("无对应数据")
                    }
                    var imgUrl = window.basePath + res.deviceData.pictureUrl
                    $('.img-info-body img').attr("src", imgUrl);

                },
                error: function(errorcode){
                    throw new Error(errorcode.status)
                }
            })
        },
        __showCoords : function (obj) {
            var x = obj.x
            var y = obj.y
            var w = obj.w
            var h = obj.h
            var imagePath = $('.img-info-body img').attr("src");
            var urlPath = window.basePath + '/device/data/img/recognition?imagePath=' + imagePath
                    + '&x=' + x + '&y=' + y + '&w=' + w + '&h=' + h
            $.ajax({
                url: urlPath,
                type: 'get',
                dataType: 'json',
                contentType: 'application/json',
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
