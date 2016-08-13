/**
 * 
 */
(function ($) {

    'use strict'

    var page = {
    		
		init: function () {
            var self = this
            self.__initNav();//初始化顶部
            self.__initMenuson()//初始化導航
            
        },
        
		__initNav: function () {
			var type = $('#top .hide-type').val();
			$(".nav li a.selected").removeClass("selected")
			if(type == null || type == "") {
				$(".nav").children().eq(0).find('a').addClass("selected");
			} else {
				$(".nav li").each(function(key, value){
					var dataNum = $(value).find('a').attr("data-num")
					if(dataNum == type){
						$(value).find('a').addClass("selected")
					}
				})
			}
		},
		
		__initMenuson: function(){
			//导航切换
			$(".menuson li").click(function(){
				$(".menuson li.active").removeClass("active")
				$(this).addClass("active");
			});
			
			$('.title').click(function(){
				var $ul = $(this).next('ul');
				$('dd').find('ul').slideUp();
				if($ul.is(':visible')){
					$(this).next('ul').slideUp();
				}else{
					$(this).next('ul').slideDown();
				}
			});
		}
			
    }

    $(function () {
        page.init()
    })
})(jQuery)
