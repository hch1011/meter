/**
 * 
 */
(function ($) {

    

    var page = {
    		
		init: function () {
            var self = this
            
            self.__initEvent();
            
            
        },
    		
        __initEvent: function () {
			$('#select_deviceType').change(function(e){
				var type = $(this).children('option:selected').val(); 

	            var urlPath = window.basePath + '/test/data?type'
	            $.ajax({
	                url: urlPath,
	                type: 'get',
	                dataType: 'json',
	                success: function(res){
	                    var appsEl = $('#list-2 .panel-body')
	                    var appList = res.items
	                    var html_list = ''
	                    $.each(appList, function(index, value){
	                        var appName = value.name
	                        var appId = value.id
	                        html_list =
	                        '<a id="'+appId+'" href="#" class="list-group-item" data-id="'+appId+'">'+appName+
	                        '</a>'
	                        appsEl.append(html_list)
	                    })
	                    $('#list-2 .panel-title').html('选择应用')
	                    self.bindAppAuth()
	                },
	                error: function(errorcode){
	                    throw new Error(errorcode.status)
	                }
	            })
	            
  
			})
			$('#param .current-plus').click(function(e){
				e.stopPrapagation
				var oldValue = $(this).siblings('input').val() || 0
				var currentValue = (parseFloat(oldValue) + 0.1).toFixed(1)
				$(this).siblings('input').val(currentValue)
			})
			
			$('#param .times-del').click(function(e){
				e.stopPrapagation
				var oldValue = $(this).siblings('input').val() || 0
				var currentValue = parseInt(oldValue) - 1
				$(this).siblings('input').val(currentValue<= 0 ? 0 : currentValue)
			})
			$('#param .times-plus').click(function(e){
				e.stopPrapagation
				var oldValue = $(this).siblings('input').val() || 0
				var currentValue = parseInt(oldValue) + 1
				$(this).siblings('input').val(currentValue)
			})
			
			$('#param .time-del').click(function(e){
				e.stopPrapagation
				var oldValue = $(this).siblings('input').val() || "00:00:00"
				var old = oldValue.split(":")
				var newValue = parseInt(old[1]) > 0 ? (parseInt(old[1]) -1) : 59;
				newValue = 	(newValue+"").length <	2 ? "0"+ newValue : newValue;
				old[0] = (old[0]+"").length <	2 ? "0"+ old[0] : old[0];
				old[0] = parseInt(old[0]) < 0 ? "00" : old[0]
				var currentValue = parseInt(old[1]) > 0 ? (old[0]+":"+newValue+":"+old[2]) : (((""+(parseInt(old[0])-1)).length<2?"0"+(parseInt(old[0])-1) : parseInt(old[0])-1) +":"+newValue+":"+old[2])
				$(this).siblings('input').val(currentValue)
				
			})
			
			$('#param .time-plus').click(function(e){
				e.stopPrapagation
				var oldValue = $(this).siblings('input').val() || "00:00:00"
				var old = oldValue.split(":")
				var newValue = parseInt(old[1]) < 59 ? (parseInt(old[1]) +1 +"") : (0);
				newValue = 	(newValue+"").length <	2 ? "0"+ newValue : newValue;
				old[0] = (old[0]+"").length <	2 ? "0"+ old[0] : old[0];
				old[0] = parseInt(old[0]) > 59 ? "00" : old[0]
				var currentValue = parseInt(old[1]) < 59 ? (old[0]+":"+newValue+":"+old[2]) : (("0"+(parseInt(old[0])+1)) +":"+newValue+":"+old[2])
				$(this).siblings('input').val(currentValue)
			})
			
		}
        
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
