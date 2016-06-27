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
			var moduleNum =  JSON.parse(localStorage['moduleNum'] || null)
			if(moduleNum != null){
				$(".nav li a.selected").removeClass("selected")
				$(".nav").children().eq(moduleNum).addClass("selected");
				delete localStorage['moduleNum ']
			}
			
			//顶部导航切换
			$(".nav li a").click(function(){
				var num = $(".nav li a").attr("data-num").val()
				localStorage['moduleNum'] = JSON.stringify(num)
				$(".nav li a.selected").removeClass("selected")
				//$(this).addClass("selected");
			})
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
