function message(code) {
    if (code != null) {
        var content = messages[code] != null ? messages[code] : code;
        if (arguments.length == 1) {
            return content;
        } else {
            if ($.isArray(arguments[1])) {
                $.each(arguments[1], function(i, n) {
                    content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
                });
                return content;
            } else {
                $.each(Array.prototype.slice.apply(arguments).slice(1), function(i, n) {
                    content = content.replace(new RegExp("\\{" + i + "\\}", "g"), n);
                });
                return content;
            }
        }
    }
}

var messages = {
    // common
    "admin.common.action": "操作",
    "admin.common.createDate": "创建时间",
    "admin.common.creator": "创建人",
    "admin.common.modifyDate": "修改时间",
    "admin.common.modifier": "修改人",

    // Admin
    "admin.admin.userName": "用户名",
    "admin.admin.email": "email",
    "admin.admin.avatar": "头像",
    "admin.admin.realName": "真实姓名",
    "admin.admin.phone": "手机号",
    "admin.admin.gender": "性别",
    "admin.admin.isEnable": "是否启用",
    "admin.admin.deptId": "所属部门",
    "admin.admin.createDate": "创建时间",
    "admin.admin.creator": "创建人",
    "admin.admin.modifyDate": "修改时间",
    "admin.admin.modifier": "修改人",
    "admin.admin.openOrStop": "开启|停用",
    // 任务
    "admin.jobTask.name": "任务名称",
    "admin.jobTask.group": "任务所在组",
    "admin.jobTask.cron": "表达式",
    "admin.jobTask.code": "任务类名",
    "admin.jobTask.desc": "描述",
    "admin.jobTask.isValid": "启动/暂停",
    //
};