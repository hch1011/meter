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
            self.__initCleanCache()
			self.__systemInit()
            
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
		},
		
		__initCleanCache: function(){
			$('#tbCleanCache').click(function(){
				var url = window.basePath+"/cc";
				$.ajax({
				    url: url,
				    type: 'get',
				    cache:false,
				    dataType:'json',
				    success:function(data) {
				    	if(data.result == 'sucess'){
				    		var url = data.data;
				    		alert("操作成功");
				    		return;
				    	}else{
				    		alert(data.screenMessage)
				    		return;
				    	}
				    	alert("服务器错误")
				    },
				    error : function() {
				    	alert("通信错误");  
				    }
				});
			});
		},

		__systemInit: function() {
			$("#top").find(".ce > li > a").click(function(){
				$(this).addClass("xz").parent().siblings().find("a").removeClass("xz");
				$(this).parent().siblings().find(".er").hide(300);
				$(this).siblings(".er").toggle(300);
				$(this).parent().siblings().find(".er > li > .thr").hide().parents().siblings().find(".thr_nr").hide();

			})

			$("#top").find(".er > li > a").click(function(){
				$(this).addClass("sen_x").parents().siblings().find("a").removeClass("sen_x");
				$(this).parents().siblings().find(".thr").hide(300);
				$(this).siblings(".thr").toggle(300);
			})

			$("#top").find(".thr > li > a").click(function(){
				$(this).addClass("xuan").parents().siblings().find("a").removeClass("xuan");
				$(this).parents().siblings().find(".thr_nr").hide();
				$(this).siblings(".thr_nr").toggle();
			})
		}

			
    }

    $(function () {
        page.init()
    })
})(jQuery)
