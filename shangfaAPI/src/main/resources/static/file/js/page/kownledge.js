/**
 * 刷新页面
 */
function refresh() {
	window.location.reload();
}


/**
 * 加载文件列表
 */
$.ajax({

	type: "get",
	url: urls.server + '/kownledgeFile/getListByKownledge',
	async: true,
	success: function(res) {
		if (res.success) {
			for (let file of res.data) {
				if(file.fileName.search('.mp3')==file.fileName.length-4){
					if (file.fileName.length > 10) {
						$("#fileList ul").append(
							`<li class="fl-li" ><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" onclick="player(this.name,this.id, 'mp3' )"  name = "${file.fileName}"  ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.fileName.substring(0, 10)}…</label><span class="tooltiptext">${file.fileName}<span style="color: red">- 单击播放 - 双击下载 </span></span></li>`
						);
					} else {
						this.files = file;
						console.log(this.files,file,'asdf', file.fileName  );
						$("#fileList ul").append(
							`<li class="fl-li"   id="${file.id}"><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" onclick="player( this.name,this.id, 'mp3')" ondblclick="downloadKownledgeFile( this.id )" name = "${file.fileName}"  id="${file.id}"><label>${file.fileName}</label><span class="tooltiptext">${file.fileName}<span style="color: red">- 单击播放 - 双击下载 </span></span></li>`
						);
					}
				}else if(file.fileName.search('.mp4') == file.fileName.length-4){
					if (file.fileName.length > 10) {
						$("#fileList ul").append(
							`<li class="fl-li" ><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" onclick="player( this.name,this.id, 'mp4')"  name = "${file.fileName}"  ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.fileName.substring(0, 10)}…</label><span class="tooltiptext">${file.fileName}<span style="color: red">- 单击播放 - 双击下载</span></span></li>`
						);
					} else {
						$("#fileList ul").append(
							`<li class="fl-li" id="${file.id}"><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" onclick="player( this.name,this.id, 'mp4')"  name = "${file.fileName}"  ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.fileName}</label><span class="tooltiptext">${file.fileName}<span style="color: red">- 单击播放 - 双击下载 </span></span></li>`
						);
					}
				}else if (file.fileName.length > 10) {
					$("#fileList ul").append(
						`<li class="fl-li" ><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.fileName.substring(0, 10)}…</label><span class="tooltiptext">${file.fileName}<span style="color: red"> - 双击下载</span></span></li>`
					);
				} else {
					$("#fileList ul").append(
						`<li class="fl-li" id="${file.id}"><i class="fileId">${file.id}</i><img src="${getFileLogo(file.suffix)}" ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.fileName}</label><span class="tooltiptext">${file.fileName}<span style="color: red">- 双击下载 </span></span></li>`
					);
				}
			}
		}
	}
});
$(function() {
	/**
	 * 更新头部时间
	 */
	$(function() {
		$("#curr-date").html(initDate(new Date()));
	});
	/**
	 * 鼠标移入文件事件
	 */
	$('.fl-li').mouseover(function() {
		$(this).children("span").css("display", "block");
	});
	$('.fl-li').mouseout(function() {
		$(this).children("span").css("display", "none");
	});
	/**
	 * 展开/收起进度条
	 */
	$('#up-spread').click(function() {
		if ($('.up-content').css('display') == 'block') {
			$('.up-content').css('display', "none");
			$('#up-spread').attr('src', "./img/上.png");
		} else {
			$('.up-content').css('display', "block");
			$('#up-spread').attr('src', "./img/下.png");
		}
	});
	/**
	 * 断点续传功能
	 */
	let count = 0;
	// 初始化上传控件
	let uploader = $.uploaderInit({
		server: urls.server + '/kownledgeFile/breakpoint-upload',
		pick: {
			id: '#addFile',
			multiple: true
		},
		chunked: true,
		// 文件队列
		fileQueued: (file) => {
			count++;
			// 追加新数据
			if (file.name.length > 10) {
				$("#fileList ul").append(
					`<li class="fl-li" id="${file.id}"><i class="fileId">${file.id}</i><img src="${getFileLogo(file.ext)}" ondblclick="downloadKownledgeFile( this.id )" id="${file.id}" ><label>${file.name.substring(0,10)}…</label><span class="tooltiptext">${file.name}<span style="color: red"> - 新文件刷新后可下载</span></span></li>`
				);
			} else {
				$("#fileList ul").append(
					`<li class="fl-li" id="${file.id}"><i class="fileId">${file.id}</i><img src="${getFileLogo(file.ext)}" ondblclick="downloadKownledgeFile( this.id )" id="${file.id}"><label>${file.name}</label><span class="tooltiptext">${file.name}<span style="color: red"> - 新文件刷新后可下载</span></span></li>`
				);
			}
			$("#upcData").append(
				`<li id="${file.id}"><p>${file.name}</p><div class="progress"><span></span>
					<div class="child"></div></div></li>`
			);
			$('.up-content').css('display', "block");
			$('#up-spread').attr('src', "./img/下.png");
			// 上传文件
			uploader.upload(uploader.getFile($(this).data('fid'), true));
		},
		// 上传文件之前先计算文件的MD5和ID
		uploadBeforeSend: (object, data, headers) => {
			let file = object.file;
			data.md5 = file.md5 || '';
			data.uid = file.uid;
		},
		// 上传进度更新
		uploadProgress: (file, percentage) => {
			let pro = Math.round(percentage * 100);
			//文字提示
			$("#" + file.id + ' span').text("正在上传：" + pro + "%");
			//动态改变div的宽度占比
			$("#" + file.id + ' .child').width(pro + "%");
		},
		// 上传完成后执行
		uploadSuccess: (file, response) => {

			var prefix = "kownledgeType" + "="
			var start = document.cookie.indexOf(prefix)
			if (start == -1) {
				return null;
			}
			var end = document.cookie.indexOf(";", start + prefix.length)
			if (end == -1) {
				end = document.cookie.length;
			}
			var kownledgeType = document.cookie.substring(start + prefix.length, end)

			var faultcaseId = null , synthesisId = null;
			if( kownledgeType == 0 ){
				prefix = "faultcaseId" + "="
				var start = document.cookie.indexOf(prefix)
				if (start == -1) {
					return null;
				}
				var end = document.cookie.indexOf(";", start + prefix.length)
				if (end == -1) {
					end = document.cookie.length;
				}
				faultcaseId = document.cookie.substring(start + prefix.length, end)
			}
			else {
				prefix = "synthesisId" + "="
				var start = document.cookie.indexOf(prefix)
				if (start == -1) {
					return null;
				}
				var end = document.cookie.indexOf(";", start + prefix.length)
				if (end == -1) {
					end = document.cookie.length;
				}
				synthesisId = document.cookie.substring(start + prefix.length, end)
			}

			// kownledgeType = 0
			// faultcaseId = 1

			// console.log( "dataMgrId = " +  datamgrId )
			//动态改变div的宽度占比
			$("#" + file.id + ' .child').width("100%");
			$("#" + file.id + ' span').text("上传完成");
			$.ajax({
				type: "post",
				url: urls.server + '/kownledgeFile/add',
				async: true,
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify({
					fileName: file.name,
					suffix: file.ext,
					faultcaseId:faultcaseId,
					synthesisId:synthesisId
				})
			});
		},
		// 上传出错时执行
		uploadError: (file, reason) => {
			$("#" + file.id + ' span').text("上传出错");
		},
		beforeInit: () => {
			// 这个必须要写在实例化前面
			WebUploader.Uploader.register({
				'before-send-file': 'beforeSendFile',
				'before-send': 'beforeSend'
			}, {
				// 时间点1：所有分块进行上传之前调用此函数
				beforeSendFile: function(file) {
					let deferred = WebUploader.Deferred();
					(new WebUploader.Uploader()).md5File(file, 0, 5242880).progress(function(percentage) {
						// 显示计算进度
						$('#' + file.id).find("td.state").text("校验MD5中...");
					}).then(function(val) {
						file.md5 = val;
						file.uid = WebUploader.Base.guid();
						// 进行md5判断
						$.ajax({
							url: urls.server + '/kownledgeFile/check-file',
							type: 'GET',
							showError: false,
							global: false,
							data: {
								fileName: file.name,
								md5: file.md5
							},
							success: (data) => {
								let status = data.errorCode;
								deferred.resolve();
								switch (status) {
									case 0:
										// 忽略上传过程，直接标识上传成功；
										uploader.skipFile(file);
										file.pass = true;
										break;
									case 16:
										// 部分已经上传到服务器了，但是差几个模块。
										file.missChunks = data.data;
										break;
									default:
										break;
								}
							}
						})
					})
					return deferred.promise();
				},
				// 时间点2：如果有分块上传，则每个分块上传之前调用此函数
				beforeSend: function(block) {
					let deferred = WebUploader.Deferred();
					// 当前未上传分块
					let missChunks = block.file.missChunks;
					// 当前分块
					let blockChunk = block.chunk;
					if (missChunks !== null && missChunks !== undefined && missChunks !== '') {
						let flag = true;
						for (let i = 0; i < missChunks.length; i++) {
							if (blockChunk === parseInt(missChunks[i])) {
								// 存在还未上传的分块
								flag = false;
								break;
							}
						}
						if (flag) {
							deferred.reject();
						} else {
							deferred.resolve();
						}
					} else {
						deferred.resolve();
					}
					return deferred.promise();
				}
			});
		}
	});
});
