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
            var d1 = [];
			for (var i = 0; i < 14; i += 0.5) d1.push([i, Math.sin(i)]);
			var d2 = [[0, 3],[2, 10], [4, 8],[6, 9], [8, 5], [9, 13],[11, 10], [12, 12]];
			var d3 = [[0, 12], [7, 12], null, [7, 2.5], [12, 2.5]];
            $.plot($("#placeholder"), [
               {
                   data: d2,
                   lines: { show: true },
                   points: { show: true }
               }
           ]);
            
            
        },
    		
        __initEvent: function () {
			
		}
        
       
    }

    $(function () {
        page.init()
    })
})(jQuery)
