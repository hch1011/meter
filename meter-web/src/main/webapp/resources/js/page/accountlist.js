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
        	var urlPath = window.basePath + '/admin/createAccount'
            $.ajax({
                url: urlPath,
                type: 'post',
                cache:false,
        	    dataType:'json',
        	    contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
                //contentType: 'application/json;charest=utf-8',
                data:$("#form_create").serializeArray(),
                success: function(data){
        	    	if(data.result == 'sucess'){
            	    	//alert("添加成功")
        	    		location.reload()
        	    		return;
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
        	var urlPath = window.basePath + '/admin/updateAccount'
            $.ajax({
                url: urlPath,
                type: 'POST',
                cache:false,
        	    dataType:'json',
        	    contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
                //contentType: 'application/json;charest=utf-8',
                data:$("#form_"+id).serializeArray(),
                success: function(data){
        	    	if(data.result == 'sucess'){        	    		
        	    		alert("修改成功")
        	    		location.reload()
        	    		return;
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
        	var urlPath = window.basePath + '/admin/deleteAccount?id='+id
            $.ajax({
                url: urlPath,
                type: 'delete',
                cache:false,
        	    dataType:'json',
                success: function(data){
        	    	if(data.result == 'sucess'){
        	    		location.reload()
        	    		return;
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
