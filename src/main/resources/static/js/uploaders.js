(function ($) {
    // 当domReady的时候开始初始化

    $(function () {
        var $wrap = $('#uploader'),

        // 图片容器
           //$queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
           $queue = $('<ul class="filelist"></ul>'),
        // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find('.statusBar'),

        // 文件总体选择信息。
            $info = $statusBar.find('.info'),

        // 上传按钮
            $upload = $wrap.find('.uploadBtn'),
            $uploadNew = $(document.body).find('.webUploadBtn'),
            $cancelBtn = $(document.body).find('.webUploadCancelBtn'),
        // 没选择文件之前的内容。
            $placeHolder = $wrap.find('.placeholder'),

        // 总体进度条
            $progress = $statusBar.find('.progress').hide(),

        // 添加的文件数量
            fileCount = 0,

        // 添加的文件总大小
            fileSize = 0,

        // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,

        // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,

        // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',

        // 所有文件的进度信息，key为file id
            percentages = {},
        // 判断浏览器是否支持图片的base64
            isSupportBase64 = (function () {
                var data = new Image();
                var support = true;
                data.onload = data.onerror = function () {
                    if (this.width != 1 || this.height != 1) {
                        support = false;
                    }
                }
                data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                return support;
            })(),

        // 检测是否已经安装flash，检测flash的版本
            flashVersion = (function () {
                var version;

                try {
                    version = navigator.plugins['Shockwave Flash'];
                    version = version.description;
                } catch (ex) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                    } catch (ex2) {
                        version = '0.0';
                    }
                }
                version = version.match(/\d+/g);
                return parseFloat(version[0] + '.' + version[1], 10);
            })(),

            supportTransition = (function () {
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                s = null;
                return r;
            })(),

        // WebUploader实例
            uploader,
            GUID = WebUploader.Base.guid(); //当前页面是生成的GUID作为标示

        if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {

            // flash 安装了但是版本过低。
            if (flashVersion) {
                (function (container) {
                    window['expressinstallcallback'] = function (state) {
                        switch (state) {
                            case 'Download.Cancelled':
                                alert('您取消了更新！')
                                break;

                            case 'Download.Failed':
                                alert('安装失败')
                                break;

                            default:
                                alert('安装已成功，请刷新！');
                                break;
                        }
                        delete window['expressinstallcallback'];
                    };

                    var swf = '../Sources/js/expressInstall.swf';
                    // insert flash object
                    var html = '<object type="application/' +
                            'x-shockwave-flash" data="' + swf + '" ';

                    if (WebUploader.browser.ie) {
                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                    }

                    html += 'width="100%" height="100%" style="outline:0">' +
                        '<param name="movie" value="' + swf + '" />' +
                        '<param name="wmode" value="transparent" />' +
                        '<param name="allowscriptaccess" value="always" />' +
                    '</object>';

                    container.html(html);

                })($wrap);

                // 压根就没有安转。
            } else {
                $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
            }

            return;
        } else if (!WebUploader.Uploader.support()) {
            alert('Web Uploader 不支持您的浏览器！');
            return;
        }
        //当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
        WebUploader.Uploader.register({
            'before-send-file': 'preupload'
        }, {
            preupload: function (file) {
             
                var me = this,
                   owner = this.owner,
                   deferred = WebUploader.Deferred(),
                   blob = file.source.getSource();
                for (var i = 0; i < MD5Array.length; i++) {
                    if (MD5Array[i].id == file.id) {
                        me.options.formData.MD5 = MD5Array[i].MD5;
                        file.wholeMd5 = MD5Array[i].MD5;
                        var sendData = $wrap.attr("data-send");
                        if (sendData && sendData != "" && sendData != "undefined") {
                            var obj = eval("(" + sendData + ")");
                            for (var k in obj) {
                                me.options.formData[k] = obj[k];
                            }
                        }
                        break;
                    }
                }
            }
        })
        var btnLable = "选择文件";
        var mimeTypes = "image/gif,image/jpg,image/jpeg,image/png,image/bmp";
        var extensions = "gif,jpg,jpeg,bmp,png";
        var sizeLimit = "1";
        if ($wrap.attr("data-source") && $wrap.attr("data-source") != "") {
            var obj = eval("(" + $wrap.attr("data-source") + ")");
            btnLable = obj.btnLabel;
            mimeTypes = obj.mimeTypes;
            extensions = obj.extensions;
            sizeLimit = obj.sizeLimit;
        }
        //每个分片发送前
        WebUploader.Uploader.register({
            'before-send': 'checkchunk'
        }, {
            checkchunk: function (block) {
       
                var me = this;
                var owner = this.owner;
                var deferred = $.Deferred();
                var chunkFile = block.blob;
                var file = block.file;
                var chunk = block.chunk;
                var chunks = block.chunks;
                var start = block.start;
                var end = block.end;
                var total = block.total;

                file.chunks = chunks;
                if (file.isExist == "True") {
                    deferred.reject();
                } else {
                    if (file.chunkIndex) {
                        if (chunk < file.chunkIndex) {
                            deferred.reject();
                        } else {
                            deferred.resolve();
                        }
                    } else {
                        deferred.resolve();
                    }
                }
                var sendData = $wrap.attr("data-send");
                if (sendData && sendData != "" && sendData != "undefined") {
                    var obj = eval("(" + sendData + ")");
                    for (var k in obj) {
                        owner.options.formData[k] = obj[k];
                    }
                }
                owner.options.formData.MD5 = file.wholeMd5;
                owner.options.formData.fileMd5 = file.wholeMd5;
                return deferred.promise();
            }
        });
        // 实例化
        var uploader = WebUploader.create({

    	    // 选完文件后，是否自动上传。
    	    auto: true,

    	    swf: '../Sources/js/Uploader.swf',// swf文件路径

     	    // 文件接收服务端。
     	    server: '/common/uploadFile',

    	    // 选择文件的按钮。可选。
    	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    	    pick: '#filePicker',
    	    
    	    fileSingleSizeLimit: parseInt(sizeLimit) * 1024 * 1024,    //1M
            accept: {
                extensions: extensions,
                mimeTypes: mimeTypes
            }

        });


        // 拖拽时不接受 js, txt 文件。
        uploader.on('dndAccept', function (items) {
            var denied = false,
                len = items.length,
                i = 0,
            // 修改js类型
                unAllowed = 'text/plain;application/javascript ';

            for (; i < len; i++) {
                // 如果在列表里面
                if (~unAllowed.indexOf(items[i].type)) {
                    denied = true;
                    break;
                }
            }

            return !denied;
        });

        // 添加“添加文件”的按钮，
        uploader.addButton({
            id: '#filePicker2',
            label: '继续添加'
        });

        uploader.on('ready', function () {
            window.uploader = uploader;
        });

        // 当有文件添加进来时执行，负责view的创建
        function addFile(file) {
            var $li = $('<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('p.imgWrap'),
                $info = $('<p class="error"></p>'),

                showError = function (code) {
                    switch (code) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text(text).appendTo($li);
                };

            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                // @todo lazyload
                $wrap.text('预览中');
                uploader.makeThumb(file, function (error, src) {
                    var img;

                    if (error) {
                        $wrap.text('不能预览');
                        return;
                    }

                    if (isSupportBase64) {
                        img = $('<img src="' + src + '">');
                        $wrap.empty().append(img);
                    } else {
                        $.ajax('preview.ashx', {
                            method: 'POST',
                            data: src,
                            dataType: 'json'
                        }).done(function (response) {
                            if (response.result) {
                                img = $('<img src="' + response.result + '">');
                                $wrap.empty().append(img);
                            } else {
                                $wrap.text("预览出错");
                            }
                        });
                    }
                }, thumbnailWidth, thumbnailHeight);

                percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }

            file.on('statuschange', function (cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }

                // 成功
                if (cur === 'error' || cur === 'invalid') {
                    //console.log(file.statusText);
                    showError(file.statusText);
                    percentages[file.id][1] = 1;
                } else if (cur === 'interrupt') {
                    showError('interrupt');
                } else if (cur === 'queued') {
                    $info.remove();
                    $prgress.css('display', 'block');
                    percentages[file.id][1] = 0;
                } else if (cur === 'progress') {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                    $prgress.hide().width(0);
                    $li.append('<span class="success"></span>');
                }

                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function () {
                $btns.stop().animate({ height: 30 });
            });

            $li.on('mouseleave', function () {
                $btns.stop().animate({ height: 0 });
            });

            $btns.on('click', 'span', function () {
                var index = $(this).index(),
                    deg;

                switch (index) {
                    case 0:
                        uploader.removeFile(file);
                        return;

                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~ ~((file.rotation / 90) % 4 + 4) % 4) + ')');
                }


            });

            $li.appendTo($queue);
        }
        //-------自行修改
        $cancelBtn.on('click', function () {
            try {
                var files = uploader.getFiles();
                for (var i = 0; i < files.length; i++) {
                    uploader.removeFile(files[i], true);
                }
                //调用页面函数，通知状态
                cancelBtnClickFun();
                $li.appendTo($queue);
            } catch (err) { }

        })
        //---------------
        // 负责view的销毁
        function removeFile(file) {  
            var $li = $('#' + file.id);
            delete percentages[file.id];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;
            $.each(percentages, function (k, v) {
                total += v[0];
                loaded += v[0] * v[1];
            });
            percent = total ? loaded / total : 0;
            spans.eq(0).text(Math.round(percent * 100) + '%');
            spans.eq(1).css('width', Math.round(percent * 100) + '%');
            updateStatus();
        }

        function updateStatus() {
            var text = '', stats;
            if (state === 'ready') {
                //text = '选中' + fileCount + '张图片，共' +
                //        WebUploader.formatSize(fileSize) + '。';
                if (uploader.getFiles().length > 0) {
                    text = uploader.getFiles()[0]["name"] + '（' + WebUploader.formatSize(fileSize) + '）';
                }
            } else if (state === 'confirm') {
                stats = uploader.getStats();
                if (stats.uploadFailNum) {
                    //text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
                    //    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>';
                    text = '文件上传失败，<a class="retry" href="javascript:;">重新上传</a>失败文件或<a class="ignore" href="javascript:;">忽略</a>'
                }
            } else {
                stats = uploader.getStats();
                if (uploader.getFiles().length > 0) {
                    text = uploader.getFiles()[0]["name"] + '（' + WebUploader.formatSize(fileSize) + '）';
                }
                //text = '共' + fileCount + '张（' +
                //        WebUploader.formatSize(fileSize) +
                //        '），已上传' + stats.successNum + '张';
                if (stats.uploadFailNum) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }
            $info.html(text);
        }

        function setState(val) {
            var file, stats;

            if (val === state) {
                return;
            }

            $upload.removeClass('state-' + state);
            $upload.addClass('state-' + val);
            state = val;

            switch (state) {
                case 'pedding':
                    $placeHolder.removeClass('element-invisible');
                    $queue.hide();
                    $statusBar.addClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.removeClass('element-invisible');
                    $('#filePicker2').removeClass('element-invisible');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');

                    uploader.refresh();
                    break;

                case 'uploading':
                    $placeHolder.addClass('element-invisible');

                    $('#filePicker2').addClass('element-invisible');
                    $progress.show();
                    $upload.text('暂停上传');

                    $info.show();
                    break;

                case 'paused':
                    $progress.hide();
                    $('#filePicker2').removeClass('element-invisible');
                    $upload.text('开始上传');

                    stats = uploader.getStats();
                    setState('finish');
                    break;

                case 'confirm':
                    $progress.hide();
                    $('#filePicker2').removeClass('element-invisible');
                    $upload.text('开始上传');

                    $info.hide();
                    $placeHolder.removeClass('element-invisible');
                    $(".webuploader-pick").html(btnLable);

                    stats = uploader.getStats();
                    if (stats.successNum && !stats.uploadFailNum) {
                        setState('finish');
                        return;
                    }
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if (stats.successNum) {
                        //alert('本次上传结束');
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    }
                    try {
                        var files = uploader.getFiles();
                        for (var i = 0; i < files.length; i++) {
                            uploader.removeFile(files[i], true);
                        }

                    } catch (err) { }
                    break;
            }

            updateStatus();
        }
        // 文件上传过程中创建进度条实时显示。
        uploader.onUploadProgress = function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            $percent.css('width', percentage * 100 + '%');
            //console.log(percentages);
            try {
                percentages[file.id][1] = percentage
            } catch (err) {

            }
            updateTotalProgress();
        };
        // 当有文件被添加进队列的时候
        var MD5Array = [];
        uploader.onFileQueued = function (file) {
            //console.log("11"+file);
            if (null == file.ext || "" == file.ext) {
                alert("请选择规范文件");
                try {
                    uploader.removeFile(file, true);
                } catch (err) {
                }
                return;
            }
            fileCount++;

            if (fileCount > 1) {
                var files = uploader.getFiles();
                for (var i = 0; i < files.length; i++) {
                    uploader.removeFile(files[i], true);
                }
                fileCount = 0;
                fileSize = 0;
                MD5Array = [];
                uploader.addFiles(file);
                return;
            }
            fileSize += file.size;

            if (fileCount === 1) {
                //$placeHolder.addClass('element-invisible');
                $statusBar.show();
            }
            //计算文件 md5 值
            uploader.md5File(file)// 及时显示进度
                .progress(function (percentage) {
                    console.log('Percentage:', percentage);
                })
                // 完成
                .then(function (val) {
                    MD5Array.push({ id: file.id, MD5: val });
                    //判断是否存在MD5
                    try {
                        selmd5(val);
                    } catch (err) { }
                    addFile(file);
                    setState('ready');
                    updateTotalProgress();
                    //计算完MD值，开始上传
                    if ($wrap.attr("data-autostart") == "true") {
                        uploader.upload();
                    }
                    try {
                        //调用前台页面函数
                        selectNewFile(file);
                    } catch (err) { }
                });
        };
        // 当有文件被移除队列的时候
        uploader.onFileDequeued = function (file) {
            if (fileCount <= 0) return;
            fileCount--;
            fileSize -= file.size;
            if (!fileCount) {
                setState('pedding');
            }
            removeFile(file, true);
            updateTotalProgress();

        };

        //all算是一个总监听器
        uploader.on('all', function (type, arg1, arg2) {
            console.log("all监听：", type, arg1, arg2);
            var stats;
            switch (type) {
                case 'uploadFinished':
                    setState('confirm');
                    break;
                case 'startUpload':
                    setState('uploading');
                    break;
                case 'stopUpload':
                    setState('paused');
                    break;
            }
        });

        // 文件上传成功,合并文件。
        uploader.on('uploadSuccess', function (file, response) {
            //uploader.reset();
            // setState("ready");
        	uploader.refresh();
        	closeLoading();
            if (response) {
            	console.log(response);
                if (response.type == "success") {
                    try {
                    	uploadSucess(response.date);
                    } catch (err) { }
                } else {
                    try {
                    	uploadError(response.content);
                    } catch (err) {

                    }
                }
            }
        });

        //文件上传失败会派送uploadError事件
        uploader.onError = function (code) {
            switch (code) {
                case "F_EXCEED_SIZE": { alert("文件大小超出范围"); break; }
                case "F_DUPLICATE": { alert("请勿上传相同文件"); break; }
                default: { alert("错误：" + code); }
            }
        };

        //当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。如果此事件handler返回值为false, 则此文件将派送server类型的uploadError事件。
        uploader.on("uploadAccept", function (object, response) {
            if (response.hasError == true) {
                alert(response.msg);

            } else {
                if (response.chunked == "True" && response.chunkIndex && response.chunkIndex != "") {
                    object.file.chunkIndex = parseInt(response.chunkIndex);
                }
                object.file.isExist = response.isExist;

                if ((response.isExist == "true" || response.isExist == "True") && !object.file.chunkIndex) {
                    //已经上传过，调用上传成功函数
                    try {
                        uploadSucess(response.fileSource);
                    } catch (err) {
                    }

                }
            }

        })
        //上传按钮点击处理函数
        $upload.on('click', function () {
            if ($(this).hasClass('disabled')) {
                return false;
            }

            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop(true);
            }
        });
        //-----自行添加的
        $uploadNew.on('click', function () {
            //||  $(this).hasClass("wait")
            if ($(this).hasClass('disabled') || $wrap.attr("data-autostart") == "false" || $(this).hasClass("wait")) {
                return false;
            }
            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop(true);
            }
        });
        //--------
        $info.on('click', '.retry', function () {
            uploader.retry();
        });

        $info.on('click', '.ignore', function () {
            uploader.reset();
        });

        $upload.addClass('state-' + state);
        updateTotalProgress();


        function startUploadFun() {
            if ($(this).hasClass('disabled')) {
                return false;
            }
            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop(true);
            }
        }
    });

})(jQuery);
