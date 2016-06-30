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
            })
            
            
        },
    		
        __initEvent: function () {
			
		}
        
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
