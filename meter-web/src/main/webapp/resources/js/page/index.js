/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this
            
            self.__initEvent();
            
            
        },
    		
        __initEvent: function () {
			//var self = this
        	
        	page.__initModal();
        	$('.content-body .block').click(function(e){
				e.stopPropagation()
				/*var content = '<form class="form-horizontal">' + 
						          '<div class="control-group">'+
						           '<label for="inputEmail" class="control-label">电子邮件</label>' +
						            '<div class="controls">'+
						             '<input type="text" placeholder="邮件地址" id="inputEmail">'+ 
						            '</div>'+
						          '</div>'+
						       '</form>'
				$('#myModal .modal-content').html(content)*/
				$('#myModal').modal( 'show')/*.css({
		             "margin-top": function () {
			             return ($(this).height() / 2);
			           }
				})*/
			})
		},
		__initModal: function(){
			$("#myModal").draggable({
		        handle: ".modal-header",
		        cursor: 'move',
		        refreshPositions: false
		    });
		}
        
		
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
