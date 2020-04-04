function showDeleteMsg(data, obj) {
    if(data.status == 1 ){
        $(obj).parents("tr").remove();
        layer.msg(data.result, {
            icon : 1,
            time : 1000
        });
    }else if(data.status == 0){
        layer.msg(data.result, {
            icon : 2,
            time : 1000
        });
    }
}

/**空值转换*/
function isNull(str){
    if(str!=null&&str!==''){
        return str;
    }else{
        return '';
    }
}

/**
 * 消息提示
 */
function showMsg(data){
    if (data.status == 200) {
        layer.msg(data.content, {time: 2000, icon: 5});
        $("#submit").prop("disabled", false);
    } else {
        layer.msg(data.content, {time: 2000, icon: 6}, function () {
            // 父页面刷新
            parent.location.reload();
            // 获得frame索引
            var index = parent.layer.getFrameIndex(window.name);
            //关闭当前frame
            parent.layer.close(index);
        });
    }
}

/**
 * 消息提示删除
 */
function deleteMsg(data){
    if (data.status == 200) {
        layer.msg(data.content, {time: 2000, icon: 5});
        $("#submit").prop("disabled", false);
    } else {
        layer.msg(data.content, {time: 2000, icon: 6}, function () {
            // 父页面刷新
            window.location.reload();
        });
    }
}