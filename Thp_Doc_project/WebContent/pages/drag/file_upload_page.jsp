<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="../../js/drag/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css"/>
<title>Plupload - Events example</title>
<style type="text/css">
	body {
		font-family:Verdana, Geneva, sans-serif;
		font-size:13px;
		color:#333;
		background:url(../../image/common/bg.jpg);
	}
</style>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://bp.yahooapis.com/2.4.21/browserplus-min.js"></script>

<script type="text/javascript" src="../../js/drag/plupload.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.gears.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.silverlight.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.flash.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.browserplus.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.html4.js"></script>
<script type="text/javascript" src="../../js/drag/plupload.html5.js"></script>
<script type="text/javascript" src="../../js/drag/jquery.plupload.queue/jquery.plupload.queue.js"></script>

</head>
<body>

<form method="post" action="/doc/regi/uploadMultiFile.do" enctype="multipart/form-data">
<!--

	<h3>Log messages</h3>
	<textarea id="log" style="width: 100%; height: 150px; font-size: 11px" spellcheck="false" wrap="off"></textarea>
 -->
	<liveany:title titleName="File Attachment" level="2"/>
	<div align="right"><a id="clear" href="#">Clear File</a></div>
	<div id="uploader" style="height: 200px;">You browser doesn't support upload.</div>

</form>
<script type="text/javascript">
$(function() {
	function log() {
		var str = "";
		plupload.each(arguments, function(arg) {
			var row = "";

			if (typeof(arg) != "string") {
				plupload.each(arg, function(value, key) {
					// Convert items in File objects to human readable form
					if (arg instanceof plupload.File) {
						// Convert status to human readable
						switch (value) {
							case plupload.QUEUED:
								value = 'QUEUED';
								break;
							case plupload.UPLOADING:
								value = 'UPLOADING';
								break;
							case plupload.FAILED:
								value = 'FAILED';
								break;
							case plupload.DONE:
								value = 'DONE';
								break;
						}
					}

					if (typeof(value) != "function") {
						row += (row ? ', ': '') + key + '=' + value;
					}
				});

				str += row + " ";
			} else {
				str += arg + " ";
			}
		});

		$('#log').val($('#log').val() + str + "\r\n");
	}

	$("#uploader").pluploadQueue({
		// General settings
		runtimes: 'html5,gears,browserplus,silverlight,flash,html4',
		url: 'uploadMultiFile.do',
		max_file_size: '1000mb',
		max_file_count: 100, // user can add no more then 20 files at a time
		//chunk_size: '100mb',
		unique_names: true,
		multiple_queues : true,

		// Resize images on clientside if we can
		resize: {width: 320, height: 240, quality: 90},

		// Rename files by clicking on their titles
		rename: true,

		// Specify what files to browse for
		//filters: [
			//{title: "Image files", extensions: "jpg,gif,png"},
			//{title: "Zip files", extensions: "zip"}
		//],

		// Flash/Silverlight paths
		flash_swf_url: '../../js/drag/plupload.flash.swf',
		silverlight_xap_url: '../../js/drag/plupload.silverlight.xap',

		// PreInit events, bound before any internal events
		preinit: {
			Init: function(up, info) {
				log('[Init]', 'Info:', info, 'Features:', up.features);
			},

			UploadFile: function(up, file) {
				log('[UploadFile]', file);

				// You can override settings before the file is uploaded
				// up.settings.url = 'upload.php?id=' + file.id;
				log('Youn Debug  Url: ', up.settings.url );
				up.settings.multipart_params = {'id': file.id, 'name': file.name, 'file':file };
				log('Youn Debug  File: ', file );
				log('Youn Debug  Params: ', up.settings.multipart_params );
			}
		},

		// Post init events, bound after the internal events
		init: {
			Refresh: function(up) {
				// Called when upload shim is moved
				log('[Refresh]');
			},

			StateChanged: function(up) {
				// Called when the state of the queue is changed
				log('[StateChanged]', up.state == plupload.STARTED ? "STARTED": "STOPPED");
			},

			QueueChanged: function(up) {
				// Called when the files in queue are changed by adding/removing files
				log('[QueueChanged]');
			},

			UploadProgress: function(up, file) {
				// Called while a file is being uploaded
				log('[UploadProgress]', 'File:', file, "Total:", up.total);
			},

			FilesAdded: function(up, files) {
				// Callced when files are added to queue
				log('[FilesAdded]');

				plupload.each(files, function(file) {
					log('  File:', file);
					log(' Youn Debug : ', file.name);
				});
			},

			FilesRemoved: function(up, files) {
				// Called when files where removed from queue
				log('[FilesRemoved]');

				plupload.each(files, function(file) {
					log('  File:', file);
				});
			},

			FileUploaded: function(up, file, info) {
				// Called when a file has finished uploading
				log('[FileUploaded] File:', file, "Info:", info);
			},

			ChunkUploaded: function(up, file, info) {
				// Called when a file chunk has finished uploading
				log('[ChunkUploaded] File:', file, "Info:", info);
			},

			Error: function(up, args) {
				// Called when a error has occured

				// Handle file specific error and general error
				if (args.file) {
					log('[error]', args, "File:", args.file);
				} else {
					log('[error]', args);
				}
			}
		}
	});

	$('#log').val('');
	$('#clear').click(function(e) {
		e.preventDefault();
		$("#uploader").pluploadQueue().splice();
		clearFile();
	});

});
</script>
</body>
</html>