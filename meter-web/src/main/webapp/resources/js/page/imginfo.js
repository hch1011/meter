/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this

            self.__init();
            self.__initEvent();

            
            
        },
    		
        __initEvent: function () {
            $('.leftmenu li').click(function(e){
                e.preventDefault();
                var liDom = $(this);
                var liDeviceId = liDom.find('a').attr("data-deviceId");
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
                        if(res.deviceData = null || res.deviceData.pictureUrl == null || res.deviceData.pictureUrl == "") {
                            alert("无对应数据")
                        }
                        var imgUrl = window.basePath + res.deviceData.pictureUrl
                        $('.img-info-body img').attr("src", imgUrl);

                    },
                    error: function(errorcode){
                        throw new Error(errorcode.status)
                    }
                })

            })
		},

        __init: function() {
            $('.leftmenu dd').eq(0).find('ul li').eq(0).addClass('active');
            /*var deviceId = $('.device_id').val();
            if(deviceId == null || deviceId == "") {
                $('.leftmenu dd').eq(0).find('.title').trigger('click')
                $('.leftmenu dd').eq(0).find('ul li').eq(0).addClass('active');
            } else {
                $('.leftmenu li').each(function(key, value){
                    var liDom = $(value);
                    var str = liDom.find('a').attr("href");
                    var index = str.indexOf('=');
                    var liDeviceId = str.substr(parseInt(index)+1);
                    if(deviceId == liDeviceId) {
                        liDom.parent('.menuson').prev('.title').trigger('click')
                        liDom.addClass("active");
                    }
                })
            }*/
        }

        
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
