var fileTypes = [{
	'logo': "./img/file-type/iso.png",
	'types': "iso,gho"
},{
	'logo': "./img/file-type/mv.png",
	'types': "rm,wav,mpg,rmvb,mov,mp4"
},{
	'logo': "./img/file-type/txt.png",
	'types': "txt,conf,log"
},{
	'logo': "./img/file-type/excel.png",
	'types': "xls,xlsx"
},{
	'logo': "./img/file-type/exe.png",
	'types': "exe,sh"
},{
	'logo': "./img/file-type/html.png",
	'types': "html,htm"
},{
	'logo': "./img/file-type/image.png",
	'types': "png,jpg,jpeg,bpm"
},{
	'logo': "./img/file-type/music.png",
	'types': "mp3,m4a"
},{
	'logo': "./img/file-type/pdf.png",
	'types': "pdf"
},{
	'logo': "./img/file-type/psd.png",
	'types': "psd"
},{
	'logo': "./img/file-type/sql.png",
	'types': "sql"
},{
	'logo': "./img/file-type/word.png",
	'types': "doc,docx"
},{
	'logo': "./img/file-type/ppt.png",
	'types': "ppt,pptx"
},{
	'logo': "./img/file-type/zip.png",
	'types': "zip,7z,gz"
},{
	'logo': "./img/file-type/apk.png",
	'types': "apk"
}];

function getFileLogo(suffix) {
	for (let ft of fileTypes) {
		if(ft.types.indexOf(suffix) != -1){
			return ft.logo;
		}
	}
	return "./img/file-type/other.png"
}

let clickNum = 0;

/**
 * 附件下载功能
 * @param fileId
 */
function downloadFile( fileId ){
	clickNum = 0;
	window.open(urls.server + '/file/download/?fileId=' + fileId + "&userId=1")
}

function downloadKownledgeFile( fileId ){
	clickNum = 0;
	window.open(urls.server + '/kownledgeFile/download/?fileId=' + fileId + "&userId=1")
}

/**
 * 日志文件下载功能
 * @param fileName
 */
function downloadLog( fileName ){
	clickNum = 0;
	window.open(urls.server + '/file/downloadLog?fileName=' + fileName )
}

/**
 * 附件播放功能
 */
function player(fileName,fileId,type) {
	clickNum++;
	setTimeout(()=>{
		if( clickNum == 2 || clickNum == 0 ) return;
		clickNum = 0;
		if(type == 'mp3'){
			window.open("./palyer-mp3.html?Id="+ fileId)

		}else {
			window.open("./palyer-mp4.html?Id="+ fileId)
		}
	},300);
}