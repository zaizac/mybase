/* ===========================================================
 * bootstrap-fileupload.js j2
 * http://jasny.github.com/bootstrap/javascript.html#fileupload
 * ===========================================================
 * Copyright 2012 Jasny BV, Netherlands.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

!function ($) {

	"use strict"; // jshint ;_

	/* FILEUPLOAD PUBLIC CLASS DEFINITION
	 * ================================= */

	var Fileupload = function (element, options) {
		this.$element = $(element)
		this.type = this.$element.data('uploadtype') || (this.$element.find('.thumbnail').length > 0 ? "image" : "file")
    
		this.$input = this.$element.find(':file')
		if (this.$input.length === 0) {
			return
		}

		this.$hidden = this.$element.find('input[type=hidden][name="'+this.name+'"]')
		if (this.$hidden.length === 0) {
			this.$hidden = $('<input type="hidden" />')
			this.$element.prepend(this.$hidden)
		}

		this.$preview = this.$element.find('.fileupload-preview')
		
		this.$noImage = this.$preview.children('img')
		
		var height = this.$preview.css('height')
    
		if (this.$preview.css('display') != 'inline' && height != '0px' && height != 'none') {
			this.$preview.css('line-height', height)
		}
    
		var input = this.$element.find(':file')
		if(input != undefined) {
			var pfxId = input.attr("id");
			pfxId = pfxId.substring(0 , pfxId.length - 5);
		}

		var updmCropper = this.$element.attr("data-updm-cropper");
		this.$hasCropper = (updmCropper === 'true');
    
		var updmDocId = $('input[type=hidden][id="' + pfxId + '.docId"]')
		var updmDocMgtId = $('input[type=hidden][id="' + pfxId + '.docMgtId"]')
		var updmRefNo = $('input[type=hidden][id="' + pfxId + '.refNo"]')
    
		var onclick = this.$element.find('.form-control').attr('onclick');
		var filename = $('input[type=hidden][id="' + pfxId + '.file.filename"]').val();
    
		var inputData = { filename : filename, pfxId : pfxId, onclick : onclick };
	
		if(updmDocId.val()) $.extend(inputData,{docid: updmDocId.val()});
		if(updmRefNo.val()) $.extend(inputData,{refno: updmRefNo.val()});
		if(updmDocMgtId.val()) $.extend(inputData,{id: updmDocMgtId.val()});
    
		this.original = {
    		'exists': this.$element.hasClass('fileupload-exists'),
    		'preview': this.$preview.html(),
    		'hiddenVal': this.$hidden.val(),
    		'fileInfo' : inputData
		}

		this.$remove = this.$element.find('[data-dismiss="fileupload"]')
		this.$element.find('[data-trigger="fileupload"]').on('click.fileupload', $.proxy(this.trigger, this))
		this.listen()
	}
  
	Fileupload.prototype = {

		/* LISTEN */
		listen: function() {
			this.$input.on('change.fileupload', $.proxy(this.change, this))
			$(this.$input[0].form).on('reset.fileupload', $.proxy(this.reset, this))
			if (this.$remove) {
				this.$remove.on('click.fileupload', $.proxy(this.clear, this))
			}
		},

		/* CHANGE */
		change: function(e, invoked) {
			var element = this.$element
  
			var file = e.target.files !== undefined ? e.target.files[0] : (e.target.value ? { name: e.target.value.replace(/^.+\\/, '') } : null);
      
			if (!file) {
				this.clear()
				return
			}
  
			var maxSize = this.$element.attr("data-updm-maxSize") || 500000;
			var dimIsCompulsary = this.$element.attr("data-dimension-compulsary") || false;
			var dimObj;
			if(dimIsCompulsary == "0" && file.type!="application/pdf"){
				dimObj = {
						minWidth: this.$element.attr("data-updm-minwidth") || 300,
						maxWidth: this.$element.attr("data-updm-maxwidth") || 500,
						minHeight: this.$element.attr("data-updm-minheight") || 300,
						maxHeight: this.$element.attr("data-updm-maxheight") || 500 
				}
			}
	  
			var pError = this.$element.siblings(".errors"); 
    
			// Check File Size
			if(file.size > maxSize) {
				if(pError[0] == undefined) {
					$('<p class="errors">' + (prop.errDocSize).replace("{0}", maxSize / 1000 + "KB") +'</p>').insertAfter(this.$element)
					this.reset()
					return;
				}
			}
    
			var updmFt = this.$input.attr("accept").toUpperCase();
    
			// Check File type
			if(file.type == '' || !updmFt.includes(file.type.toUpperCase())){
				if(pError[0] == undefined) {
					var errDocType = prop.errDocType.includes("{0}") ? (prop.errDocType).replace("{0}") : prop.errDocType;
					$('<p class="errors">' + errDocType +'</p>').insertAfter(this.$element)
					this.reset()
					return;
				}
			}
    
			// Check If Initialize CROPPER
			if(this.$hasCropper) {
				this.$file = file;
				this.cropperInit(file, this)
				
			} else {
				
				if (this.type === "image" && this.$preview.length > 0 && (typeof file.type !== "undefined" ? file.type.match('image.*') : file.name.match(/\.(gif|png|jpe?g)$/i)) && typeof FileReader !== "undefined") {
					var preview = this.$preview
    		
					readFileAsUrl(file, function(e) {
						preview.html('<img src="' + e.target.result + '" class="fileupload-img-thumbnail-view img-thumbnail" />')
						element.addClass('fileupload-exists').removeClass('fileupload-new')
						$(element).trigger('imagechange')
					})
    		
				} else {
					var self = this;
					this.$element.siblings('p').remove()
					
					// Check File Dimension
					checkDimension(file, function(w,h) {
						if(dimIsCompulsary == "0" && "IMAGE/PNG,IMAGE/GIF,IMAGE/JPG".includes(file.type.toUpperCase())) {
							if((w > 0 && h > 0) && (w > dimObj.maxWidth || w < dimObj.minWidth) || ( h > dimObj.maxHeight || h < dimObj.minHeight)){
								var pError = self.$element.siblings(".errors")
								if(pError[0] == undefined){
									$('<p class="errors">' +  (prop.errDimension).replace("{0}", dimObj.minHeight)
											.replace("{1}", dimObj.maxHeight)
											.replace("{2}", dimObj.minWidth)
											.replace("{3}",dimObj.maxWidth) +'</p>').insertAfter(self.$element)
											self.reset();
								}
								return;
							}
						}
					});
    		
					// Read File
					readFileAsBinary(file, function(e) {
						// manipulate with result...
						self.upload(e, self, file);
					});
    		
					element.find('.fileupload-upload').val(file.name)
					element.find('.fileupload-upload-type').val(file.type)
					element.find('.fileupload-upload-size').val(file.size)
					self.$element.find('.form-control').removeAttr('onclick')
					self.$element.find('.fileupload-filename').text(file.name)
					self.$preview.text(file.name)
					self.$element.addClass('fileupload-exists').removeClass('fileupload-new')
				}
			}
    
		},
		
		upload: function(e, self, file, bytes) {
			var updm = this.$element.attr("data-updm-projId")
			var pfxId = this.original.fileInfo.pfxId;

			// Direct Upload to DM
			if(updm != undefined) {
				var updmDocPopTitle = this.$element.attr("data-updm-docPopTitle");
				var updmDocId = $('input[type=hidden][id="' + pfxId + '.docId"]')
				var updmDocMgtId = $('input[type=hidden][id="' + pfxId + '.docMgtId"]')
				var updmRefNo = $('input[type=hidden][id="' + pfxId + '.refNo"]')
				if(bytes == undefined) {
					bytes = e.target.result;
				}
				var base64Data = btoa(bytes);
				
				var inputURL = contextPath + "/documents/upload/" + updm;
				var inputData = { filename : file.name, contentType : file.type, length : file.size, content: base64Data };
				if(updmDocId.val()) $.extend(inputData,{docid: updmDocId.val()});
				if(updmRefNo.val()) $.extend(inputData,{refno: updmRefNo.val()});
				if(updmDocMgtId.val()) $.extend(inputData,{id: updmDocMgtId.val()});
				console.log("inputURL: " + inputURL)
				console.log(inputData)
				var result = uploadDoc(inputURL, inputData);
				console.log(result)
				if(result != undefined) {
					var docPopup = "javascript:documentPopup('documents/download/" + result.id + "/projId/" + updm + "','" + updmDocPopTitle + "', '" + file.type + "','" + updmDocPopTitle +"');";
					updmDocMgtId.val(result.id)
				}
				self.$element.children("div").attr("onclick", docPopup);
			} else {
				self.$element.find('.fileinput-upload-content').val(base64Data)
			}
		},
  
		/* CLEAR */
		clear: function(e) {
			this.$hidden.val(null)
			this.$hidden.attr('name', '')
			this.$input.attr('name', this.name)
	
			// ie8+ doesn't support changing the value of input with type=file so clone instead
			if (navigator.userAgent.match(/msie/i)){ 
				var inputClone = this.$input.clone(true);
				this.$input.after(inputClone);
				this.$input.remove();
				this.$input = inputClone;
			} else {
				this.$input.val('')
			}

			this.$preview.html('')
			if(this.$noImage[0] != undefined) {
				this.$preview.append(this.$noImage[0])
			}
			this.$element.addClass('fileupload-new').removeClass('fileupload-exists')
			this.$element.find('.fileupload-upload').val(null)
			this.$element.find('.fileupload-upload-size').val(0)
			this.$element.find('.fileupload-upload-content').val('')
			this.$element.find('.fileupload-filename').text('')
			this.$element.find('.form-control').removeAttr('onclick')

			if (e) {
				this.$input.trigger('change', [ 'clear' ])
				e.preventDefault()
			}
		},

		/* RESET */
		reset: function(e) {
			this.clear();
			
			this.$hidden.val(this.original.hiddenVal)
			this.$preview.html(this.original.preview)
			this.$element.find('.form-control').attr('onclick',this.original.fileInfo.onclick)
			$('input[type=hidden][id="' + this.original.fileInfo.pfxId + '.file.filename"]').val(this.original.fileInfo.filename)
			this.$hidden.attr('name', this.original.fileInfo.filename)
  
			if (this.original.exists) {
				this.$element.addClass('fileupload-exists').removeClass('fileupload-new')
			} else {
				this.$element.addClass('fileupload-new').removeClass('fileupload-exists')
			}
		},

		/* TRIGGER */
		trigger: function(e) {
			this.$input.trigger('click')
			e.preventDefault()
		},

		/* CROPPER INIT*/
		cropperInit: function(e, self) {
			var   $container;
	
			var settings = {
					width: 200,
					height: 300,
					debug: false
			};
			
			var div = function() {
				return $("<div/>");
			};
			
			var a = function(text) {
				return $("<a href=\"#\">" + text + "</a>");
			};
			
			$container = div().addClass('awesome-cropper');
			var $applyButton = a('Apply').addClass('btn btn-primary');
			$applyButton.attr('id', 'btn-crop');
			var $cancelButton = a('Cancel').addClass('btn btn-danger').attr({
				'data-dismiss': "modal"
			});
    
			var $cropSandbox = document.createElement("canvas");
			$cropSandbox.id = "img-crop";
			$cropSandbox.width =  settings.width;
			$cropSandbox.height = settings.height;
    
			var context = $cropSandbox.getContext("2d");
    
			var _URL = window.URL || window.webkitURL;
			var img = new Image();
			img.onload = function() {
				context.drawImage(img, 0, 0, $cropSandbox.width, $cropSandbox.height);
				$('#img-crop').cropper({
					dragMode: 'move',
					aspectRatio: 2 / 3,
					cropBoxResizable: false,
					autoCropArea: 1,
					strict: false,
					guides: false,
					maxCropBoxWidth: $cropSandbox.width,
					maxCropBoxHeight: $cropSandbox.height,
					restore: false,
					center: false,
					highlight: false,
					cropBoxMovable: false,
					toggleDragModeOnDblclick: false,
				});
			
				$('#btn-crop').on('click', function (e) {
					self.crop();
				});
			}
    
			img.src = _URL.createObjectURL(e);
			
			$cropSandbox.append(img)
			var $imagesContainer = div().append($cropSandbox);
			$container.append($imagesContainer);
    
			$container.append(div().addClass('btn-group').append($cancelButton).append($applyButton));
			portalUtil.showPopup("Image Cropper", $container)
		},

		crop: function(e) {
			var croppedCanvas = $("#img-crop").cropper('getCroppedCanvas');
			if(croppedCanvas != undefined) {
				console.log(croppedCanvas)
				var canvasURL = croppedCanvas.toDataURL();
				this.$preview.html('<img src="' + canvasURL+ '" class="fileupload-img-thumbnail-view img-thumbnail" />')
				var element = this.$element;
				element.addClass('fileupload-exists').removeClass('fileupload-new');
				$(element).trigger('imagechange');
				bootbox.hideAll()
				var fileByte = atob(canvasURL.split(',')[1]);
				this.upload(null, this, this.$file, fileByte);
			}
		}

	}

  
	/* FILEUPLOAD PLUGIN DEFINITION
	 * =========================== */

	$.fn.fileupload = function (options) {
		return this.each(function () {
			var $this = $(this)
			, data = $this.data('fileupload')
			if (!data) $this.data('fileupload', (data = new Fileupload(this, options)))
			if (typeof options == 'string') data[options]()
		});
	}

	$.fn.fileupload.Constructor = Fileupload

	/* FILEUPLOAD DATA-API
	 * ================== */

	$(document).on('click.fileupload.data-api', '[data-provides="fileupload"]', function (e) {
		var $this = $(this)
		if ($this.data('fileupload')) {
			return
		}
		$this.fileupload($this.data())
      
		var $target = $(e.target).closest('[data-dismiss="fileupload"],[data-trigger="fileupload"]');
		if ($target.length > 0) {
			$target.trigger('click.fileupload')
			e.preventDefault()
		}
	});
	
	/* FILEUPLOAD UTILITY
	 * ================== */

	function uploadDoc(inputURL, inputData) {
		var aadata = null;
		$.ajax({
			'async': false,
			'global': false,
			headers: { 'X-CSRF-Token': csrf_token },
			type: "POST", 
			action: 'xhttp',
			url: inputURL, 
			data: inputData,
			success : function(data) {
				aadata = data;
			}
		});
		return aadata;
	}
  
	function readFileAsBinary(file, callback){
		var reader = new FileReader();
		reader.onload = callback
		reader.readAsBinaryString(file);
	}
	
	function readFileAsUrl(file, callback){
		var reader = new FileReader();
		reader.onload = callback
		reader.readAsDataURL(file)
	}
	
	function checkDimension(file, callback) {
		var _URL = window.URL || window.webkitURL;
		var img = new Image();
		img.onload = function(){        
			if(callback) {
				callback(img.width, img.height);    
			}
		}   
		img.src = _URL.createObjectURL(file);
	}
  
}(window.jQuery);
