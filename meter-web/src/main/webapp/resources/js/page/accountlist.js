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
            var self = this
            $('.btn').click(function(e){
                e.preventDefault();
                var method = $(this).data('method')
                if('update' == method){
                	self.updateAccount($(this).data('id'));
                }else if('delete' == method){
                	self.deleteAccount($(this).data('id'));
                }else if('create' == method){
                	self.createAccount()
                }else{
                	alert("非法操作")
                	return;
                }
            })
			
		},
		
        createAccount: function ( ) {
            $.ajax({
                url: urlPath,
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                success: function(data){
        	    	if(data.result == 'sucess'){
                        location.reload()
        	    	}else{
        	    		alert(data.screenMessage)
        	    		return;
        	    	}
        	    	alert("服务器错误")
                },
                error: function(errorcode){
                    throw new Error(errorcode.status)
                }
            })
        },
		
        updateAccount: function (id) {
        	var urlPath = window.basePath + '/admin/account'
            $.ajax({
                url: urlPath,
                type: 'put',
                cache:false,
        	    dataType:'json',
        	    data:$("#form_"+id).serialize(),
                contentType: 'application/json',
                success: function(data){
        	    	if(data.result == 'sucess'){
            	    	alert("修改成功")
        	    		location.reload()
        	    	}else{
        	    		alert(data.screenMessage)
        	    		return;
        	    	}
        	    	alert("服务器错误")
                },
                error: function(errorcode){
                    throw new Error(errorcode.status)
                }
            })
		},
		
        deleteAccount: function (id) {
            $.ajax({
                url: urlPath,
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                success: function(data){
        	    	if(data.result == 'sucess'){
        	    		alert("success")
        	    	}else{
        	    		alert(data.screenMessage)
        	    		return;
        	    	}
        	    	alert("服务器错误")
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
