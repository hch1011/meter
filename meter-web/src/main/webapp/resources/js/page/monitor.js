/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this
            
            self.__initEvent();
            $("#myModal").draggable({
		        handle: ".modal-header",
		        cursor: 'move',
		        refreshPositions: false
		    });
            
        },
    		
        __initEvent: function () {
			var self = this
        	$('.content-body .block').click(function(e){
				e.stopPropagation()
				var infoId = $(this).attr('data-id')
				$('#myModal .body-detail .tl').attr('data-id',infoId)

				var url = $(this).find('a').attr('data-id') + "?deviceId=" + infoId;
				$(this).find('a').attr('href', url);

				var infoName = $(this).attr("data-infoname")
				$('#myModal .body-detail .tl .info-name').html(infoName)
				
				var snapStatus = $(this).attr('data-snapstatus') 
				var imgUrl = '/meter/resources/images/' + 'light' + snapStatus +'.png'
				$('#myModal .body-detail .tl .tl-image').find('img').attr('src', imgUrl)
				
				var dataName = infoName.split('#')[0]  
				$(".body-detail .data-name").html(dataName)
				var dataUnit = $(this).attr('data-dataunit')
				$(".body-detail .data-unit").html(dataUnit)
				
				var snapdata = $(this).attr('data-snapdata') 
				$(".body-detail .snap-data input").val(snapdata)
				
				var dataRate = $(this).attr('data-rate') 
				$(".body-detail .change-rate input").val(dataRate)
				
				var dataRateUnit = $(this).attr('data-rateunit') 
				$(".body-detail .change-rate-unit").html(dataRateUnit)
				
				var dataDate = $(this).attr('data-date') 
				$(".body-detail .create-data input").val(dataDate)
				
				var dataTime = $(this).attr('data-time') 
				$(".body-detail .create-time input").val(dataTime)
				
				var dataTypeName = infoName.split('#')[0] 
				$(".body-detail .type-name").html(dataTypeName)
				
				var dataFrequency = $(this).attr('data-frequency') 
				$(".body-detail .frequency input").val(dataFrequency)

				var type = $(this).attr('data-type')
				if(type != null && type != 2) {
					$(".body-detail .hid").hide();
				}

				$('#myModal').modal( 'show').css({
		             "margin-top": function () {
			             return ($(this).height() / 4);
			           }
				})

				$('#myModal .btn-close').click(function(){
					self.__initModal();
				})
			})
		},
		__initModal: function(){
			$(".body-detail .hid").show();
		}
        
		
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
