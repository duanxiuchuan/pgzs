layui.define(["jquery","form"],function (exports) {
    var form = layui.form;
    var obj = {
        initSelect : function () {
            var $dictTypes = $("select[data-dictType]");
            for(var i = 0 ; i < $dictTypes.length; i++){
                var $dict  = $($dictTypes[i]);
                var dictType = $dict.attr("data-dictType");
                var initValue  =  $dict.attr("data-initValue");
                ajaxUtil.postAjax("/common/getDictListByType.do",{"dictType":dictType},function (data) {
                    if (data.type == '200'){
                        var optionHtml = "<option >--请选择--</option>";
                        var list = data.date;
                        for(var j = 0 ; j < list.length; j++){
                           var dict =  list[j];
                           var value = dict.value;
                           var text = dict.name;
                           if(initValue == value){
                               optionHtml+="<option value='"+value+"' selected >"+text+"</option>";
                           } else{
                               optionHtml+="<option value='"+value+"' >"+text+"</option>";
                           }
                        }
                        $dict.append(optionHtml);
                        form.render();
                    } else {
                        layTips.error(data.content,false);
                    }

                });
            }
        }
    }
    exports("dictSelect",obj);
});