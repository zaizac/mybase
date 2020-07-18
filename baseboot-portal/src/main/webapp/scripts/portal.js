function maxLengthCheck(object) {
	if (object.value.length > object.maxLength) {
		object.value = object.value.slice(0, object.maxLength)
	}
}

function documentPopupForWallet(url, title, type, method) {

	var createDateFrom = $('input[name="createDateFrom"]').val();
	var createDateTo = $('input[name="createDateTo"]').val();
	var paymentMode = $('#paymentMode').val();
	var paymentRefNo = $('#paymentRefNo').val();
	var companyName = $('#profileTypeMruSelectName').val();
	var profileType = $('input[name="profileType"]:checked').val();

	var payDateFrom = document.getElementById('errorPaymentDateFrom');
	var payDateTo = document.getElementById('errorPaymentDateTo');
	var profileError = document.getElementById('errorProfile');
	
	if (createDateFrom == "") {
		payDateFrom.className +="errors ";
		payDateFrom.innerHTML = "Payment Date From is required";
		return false;
	}

	if (createDateTo == "") {
		payDateTo.className +="errors ";
		payDateTo.innerHTML = "Payment Date From is required";
		return false;
	}
	if (createDateFrom == "" && createDateTo == "") {
		payDateFrom.className +="errors ";
		payDateFrom.innerHTML = "Payment Date From is required";
		
		payDateTo.className +="errors ";
		payDateTo.innerHTML = "Payment Date From is required";
		return false;
	}
	if (companyName == "") {
		profileError.className +="errors ";
		profileError.innerHTML = "Company Name is required";
		return false;
	}

	url = url + '?createDateFrom=' + createDateFrom + '&createDateTo='
			+ createDateTo + '&paymentMode=' + paymentMode + '&paymentRefNo='
			+ paymentRefNo + '&optrId=' + companyName + '&profileType='
			+ profileType;
	documentPopup(url, title, type, method);
}

function documentPopupForWalletSOA(url, title, type, method) {

	var createDateFrom = $('input[name="createDateFrom"]').val();
	var createDateTo = $('input[name="createDateTo"]').val();
	var companyName = $('#profileTypeMruSelectName').val();
	var profileType = $('input[name="profileType"]:checked').val();

	var payDateFrom = document.getElementById('errorPaymentDateFrom');
	var payDateTo = document.getElementById('errorPaymentDateTo');
	var profileError = document.getElementById('errorProfile');
	
	if (createDateFrom == "") {
		payDateFrom.className +="errors ";
		payDateFrom.innerHTML = "Payment Date From is required";
		return false;
	}

	if (createDateTo == "") {
		payDateTo.className +="errors ";
		payDateTo.innerHTML = "Payment Date From is required";
		return false;
	}
	if (createDateFrom == "" && createDateTo == "") {
		payDateFrom.className +="errors ";
		payDateFrom.innerHTML = "Payment Date From is required";
		
		payDateTo.className +="errors ";
		payDateTo.innerHTML = "Payment Date From is required";
		return false;
	}
	if (companyName == "") {
		profileError.className +="errors ";
		profileError.innerHTML = "Company Name is required";
		return false;
	}

	url = url + '?createDateFrom=' + createDateFrom + '&createDateTo='
			+ createDateTo + '&optrId=' + companyName + '&profileType='
			+ profileType;
	documentPopup(url, title, type, method);
}

function documentPopupForRegListRpt(url, title, type, method) {

	var regDateFrom = $('input[name="regDateFrom"]').val();
	var regDateTo = $('input[name="regDateTo"]').val();
	var sector = $('#sectorSelectId').val();
	var registeredBy = $('#regSelectId').val();
	var roc = $('#roc').val();
	var cmpany = $('#companySelectedId').val();
	var passportNo = $('#passportNo').val();

	url = url + '?regDateFrom=' + regDateFrom + '&regDateTo=' + regDateTo
			+ '&sector=' + sector + '&registeredBy=' + registeredBy + '&roc='
			+ roc + '&cmpany=' + cmpany + '&passportNo=' + passportNo;
	documentPopup(url, title, type, method);
}

function documentPopupForAevRegListRpt(url, title, type, method) {

	var regDateFrom = $('input[name="regDateFrom"]').val();
	var regDateTo = $('input[name="regDateTo"]').val();
	var synDate = $('input[name="synDate"]').val();
	var passportNo = $('#aevEmployerList[name="passportNo"]').val();
	var manualDataEntry = $('input[name="manualDataEntry"]').val();
	var companyRoc = $('#aevEmployerList[name="companyRoc"]').val();
	var companyStatus = $('#employerStatus').val();
	var aevStatus = $('#aevWorkerStatus').val();
	var registrationNo = $('#aevEmployerList[name="registrationNo"]').val();

	url = url + '?regDateFrom=' + regDateFrom + '&regDateTo=' + regDateTo + '&synDate=' + synDate
	+ '&passportNo=' + passportNo + '&manualDataEntry=' + manualDataEntry 
	+ '&companyRoc=' + companyRoc + '&companyStatus=' + companyStatus
	+ '&aevStatus=' + aevStatus + '&registrationNo=' + registrationNo ;
	documentPopup(url, title, type, method);
}

var MimeType = {
	pdf : {
		mediaType : "application/pdf",
		fileExt : ".pdf"
	},
	gif : {
		mediaType : "image/gif",
		fileExt : ".gif"
	},
	png : {
		mediaType : "image/png",
		fileExt : ".png"
	},
	jpg : {
		mediaType : "image/jpeg",
		fileExt : ".jpeg, .jpg"
	},
	doc : {
		mediaType : "application/msword",
		fileExt : ".doc"
	},
	docx : {
		mediaType : "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
		fileExt : ".docx"
	},
	xls : {
		mediaType : "application/vnd.ms-excel",
		fileExt : ".xls"
	},
	xlsx : {
		mediaType : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		fileExt : ".docx"
	}
};

function backToInbox() {
	window.location.href = contextPath + '/inbox';
}

function dataTableInit(id) {
	$(id).dataTable({
		"dom" : '<"top"i>t<"bottom"p><"clear">',
		"oLanguage" : {
			"sEmptyTable" : prop.errNoRecFnd,
			"sLoadingRecords" : "Please wait - loading...",
			"sProcessing" : "DataTables is currently busy",
			"sZeroRecords" : "No records to display"
		},
		"fnDrawCallback" : function(oSettings) {
			hidePagination(this, id);
			processRowNum(oSettings);
		}
	});
}

jQuery(document)
		.ready(
				function() {
					$("#topUpAmt").change(
							function(e) {
								var amt = this.value;
								var gstPrcnt = parseInt($("#gstPrcnt").val());
								var gst = 0;
								if (gstPrcnt > 0) {
									gst = parseInt(amt-(amt /((100+gstPrcnt)/100)));
								}
								console.log
								var creditedAmount = amt - gst;
								$("#gst").val(gst);
								$("#lblGst").val(gst);
								$("#creditedAmount").val(creditedAmount);
								$("#lblCreditedAmount").val(
										numberWithCommas(creditedAmount));

							});

					dataTableInit('tblCrdHstryLst');
					dataTableInit('#prdLstId');
					$('#prdLstId tbody').on('click', 'tr', function() {
						var rowId = this.id;
						if (rowId != null) {
							location.href = contextPath + "/products/" + rowId;
						}
					});

					dataTableInit('#tblRefProdLstId');
					$('#tblRefProdLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/product/" + rowId;
								}
							});

					dataTableInit('#tblRefProdCatLstId');
					$('#tblRefProdCatLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/product-cat/"
											+ rowId;
								}
							});

					dataTableInit('#tblReceiverLst');
					$('#tblReceiverLst tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								console.log(rowId);
								if (rowId != null) {
									location.href = contextPath + "/receivers/"
											+ rowId;
								}
							});

					dataTableInit('#tblMerchantLst');
					$('#tblMerchantLst tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath + "/merchants/"
											+ rowId;
								}
							});

					dataTableInit('#tblRefCityLstId');
					// $('#tblRefCityLstId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#tblRefCityLstId");
					// processRowNum(oSettings);}});
					$('#tblRefCityLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/city/" + rowId;
								}
							});

					dataTableInit('#tblRefDistLstId');
					// $('#tblRefDistLstId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#tblRefDistLstId");
					// processRowNum(oSettings);}});
					$('#tblRefDistLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/district/" + rowId;
								}
							});

					dataTableInit('#tblRefStateLstId');
					// $('#tblRefStateLstId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#tblRefStateLstId");
					// processRowNum(oSettings);}});
					$('#tblRefStateLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/state/" + rowId;
								}
							});

					dataTableInit('#tblRefBankLstId');
					// $('#tblRefBankLstId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#tblRefBankLstId");
					// processRowNum(oSettings);}});
					$('#tblRefBankLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/bank/" + rowId;
								}
							});

					dataTableInit('#tblRefStatusLstId');
					// $('#tblRefStatusLstId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#tblRefStatusLstId");
					// processRowNum(oSettings);}});
					$('#tblRefStatusLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/status/" + rowId;
								}
							});

					// reg
					dataTableInit('#mruListTableId');
					// $('#mruListTableId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#mruListTableId");
					// processRowNum(oSettings);}});
					$('#mruListTableId tbody').on('click', 'tr', function() {
						var rowId = this.id;
						if (rowId != null) {
							location.href = contextPath + rowId;
						}
					});

					dataTableInit('#apsListTableId');
					// $('#mruListTableId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#mruListTableId");
					// processRowNum(oSettings);}});
					$('#apsListTableId tbody').on('click', 'tr', function() {
						var rowId = this.id;
						if (rowId != null) {
							location.href = contextPath + rowId;
						}
					});

					dataTableInit('#oscListTableId');
					// $('#mruListTableId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#mruListTableId");
					// processRowNum(oSettings);}});
					$('#oscListTableId tbody').on('click', 'tr', function() {
						var rowId = this.id;
						if (rowId != null) {
							location.href = contextPath + rowId;
						}
					});

					dataTableInit('#cruListTableId');
					// $('#mruListTableId').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings )
					// {hidePagination(this,"#mruListTableId");
					// processRowNum(oSettings);}});
					$('#cruListTableId tbody').on('click', 'tr', function() {
						var rowId = this.id;
						if (rowId != null) {
							location.href = contextPath + rowId;
						}
					});

					dataTableInit('#walletListTableId');
					dataTableInit('#regListTableId');
					dataTableInit('#aevWorkerListId');

					var oTableColl = $('#surrCollectListTableId')
							.dataTable(
									{
										"dom" : '<"top"i>t<"bottom"p><"clear">',
										"processing" : true,
										"fnDrawCallback" : function(oSettings) {
											hidePagination(this,
													"#surrCollectListTableId");
											$('.chk')
													.click(
															function() {
																var allPages = oTableColl
																		.fnGetNodes();
																var pmtTxnIds = [];
																var total = 0;
																var valsInput = $(
																		allPages)
																		.map(
																				function() {
																					var inp = $(
																							this)
																							.find(
																									'input[type="checkbox"]');
																					if (inp
																							.is(':checked')) {
																						pmtTxnIds
																								.push(inp
																										.val());
																						var inpAmt = $(
																								this)
																								.find(
																										'td:last-child')
																								.text();
																						total += Number(inpAmt);
																					}
																					return $(this);
																				})
																		.get();
																var items = Number($(
																		'input[type="checkbox"]',
																		allPages).length);
																var itemCheck = Number($(
																		'input[type="checkbox"]:checked',
																		allPages).length);
																if (itemCheck < items) {
																	$('#chkAll')
																			.prop(
																					'checked',
																					false);
																} else if (itemCheck == items) {
																	$('#chkAll')
																			.prop(
																					'checked',
																					true);
																}

																var decimalTotal = total
																		.toFixed(2);
																$('#total')
																		.text(
																				decimalTotal);
																document
																		.getElementById("hiddenInp").value = pmtTxnIds;
															});
											$('#chkAll')
													.click(
															function() {
																var allPages = oTableColl
																		.fnGetNodes();

																var total = 0;
																var pmtTxnIds = [];
																if ($('#chkAll')
																		.is(
																				':checked')) {
																	$(
																			'input[type="checkbox"]',
																			allPages)
																			.prop(
																					'checked',
																					true);

																	var valsInput = $(
																			allPages)
																			.map(
																					function() {
																						var inp = $(
																								this)
																								.find(
																										'input[type="checkbox"]');
																						if (inp
																								.is(':checked')) {
																							pmtTxnIds
																									.push(inp
																											.val());
																							var inpAmt = $(
																									this)
																									.find(
																											'td:last-child')
																									.text();
																							total += Number(inpAmt);
																						}
																						return $(this);
																					})
																			.get();
																} else {
																	$(
																			'input[type="checkbox"]',
																			allPages)
																			.prop(
																					'checked',
																					false);
																}
																var decimalTotal = total
																		.toFixed(2);
																$('#total')
																		.text(
																				decimalTotal);
																document
																		.getElementById("hiddenInp").value = pmtTxnIds;
															});
										}
									});

					$('#surrSummaryListTableId').dataTable({
						"dom" : '<"top"i>t<"bottom"p><"clear">',
						"fnDrawCallback" : function(oSettings) {
							hidePagination(this, "#surrSummaryListTableId");
							processRowNum(oSettings);
						}
					});

					jQuery(document)
							.ready(
									function() {

										var imiOverwriteBranchIds = [];
										var tmpBranchIds = [];
										var tmpBranchIds2 = [];
										var rdioBtnVal = '';

										$(".remarksClass")
												.keyup(
														function() {
															var textinput = $(
																	'.remarksClass')
																	.val();
															document
																	.getElementById("hiddenInpRemarks").value = textinput;

														});

										$(".overwrite")
												.change(
														function() {

															// $('option[value='
															// + $(this).val() +
															// ']').attr('disabled',
															// 'disabled');
															var valOverwrite = this.value;
															$(
																	"#"
																			+ this.id
																			+ "imi")
																	.prop(
																			"checked",
																			true);
															var valTempBrnch = ($('input[class="defaultSelected"]:checked')
																	.val());
															createListBranchSelected(valOverwrite);
															createListTempBrnchSelected(valTempBrnch);

														});

										function createListBranchSelected(
												valOverwrite) {
											imiOverwriteBranchIds
													.push(valOverwrite);
											document
													.getElementById("hiddenInp").value = imiOverwriteBranchIds;
											console
													.log("imiOverwriteBranchIds>"
															+ imiOverwriteBranchIds);

										}

										function createListTempBrnchSelected(
												valTempBrnch) {
											tmpBranchIds.push(valTempBrnch);
											document
													.getElementById("hiddenInpTemp").value = valTempBrnch;
											console.log("tmpBranchIds>"
													+ valTempBrnch);

										}

										$(".btnSelectStatus")
												.change(
														function() {
															var selectedIndex = this.value;
															console
																	.log("selectedIndex>>>>>"
																			+ selectedIndex);
															document
																	.getElementById("hiddenInpRbtn").value = selectedIndex;

														});

										$(".defaultSelected")
												.change(
														function() {
															var val = this.value;
															tmpBranchIds2
																	.push(val);
															console
																	.log("TEST::::"
																			+ tmpBranchIds2);
															document
																	.getElementById("hiddenInpTemp2").value = val;
														});

									})

					$('#branchIdEmp tr')
							.each(
									function() {
										$(this)
												.find('select:eq(0)')
												.change(
														function() {
															if ($(
																	'#branchIdEmp')
																	.find(
																			'select option[value='
																					+ $(
																							this)
																							.val()
																					+ ']:selected').length > 1) {
																alert('Option is already selected');
																$(this)
																		.val(
																				$(
																						this)
																						.find(
																								"option:first")
																						.val());
															}
														});
									});

					dataTableInit('#tblTranLst');
					$('#tblTranLst tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									portalUtil.showMainLoading(true);
									var inputURL = contextPath
											+ "/trans-list/details/" + rowId;
									console.log(inputURL);
									$.ajax({
										headers : {
											'X-CSRF-Token' : csrf_token
										},
										type : "GET",
										action : 'xhttp',
										url : inputURL,
										success : function(data) {
											$('#popup_content').html(data);
											$('#popup_modal').modal('show');
										}
									}).done(function() {
										portalUtil.showMainLoading(false);
									});
								}
							});

					dataTableInit('#tblTranRepLst');

					if ($('#userGroupCode').length > 0) {
						onChangeGroup();
					}

					$('#aTag').click(function() {
						document.getElementById('appForm').submit();
					});

					$('.app-flow').click(function() {
						location.href = contextPath + $(this).attr("href");
					});

					dataTableInit('#tblRegList');
					$('#tblRegList tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								console.log(rowId);
								if (rowId != null) {
									location.href = contextPath
											+ "/application/registrationList/"
											+ rowId;
								}
							});

					dataTableInit('#trasSuplist');
					$('#trasSuplist tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								console.log(rowId);
								if (rowId != null) {
									location.href = contextPath
											+ "/application/supervisorInput/"
											+ rowId;
								}
							});

					// $('#tblUsers').dataTable({ "dom":
					// '<"top"i>t<"bottom"p><"clear">', "fnDrawCallback":
					// function ( oSettings ) {hidePagination(this,"#tblUsers");
					// processRowNum(oSettings);}});
					dataTableInit('#trasSuplist');
					dataTableInit('#tblUsers');

					$('#UserList1').dataTable({
						"dom" : '<"top"i>t<"bottom"p><"clear">',
						"aoColumnDefs" : [ {
							"bSortable" : false,
							"aTargets" : [ 0 ]
						} ],
						"order" : [ [ 1, "asc" ] ],
						"fnDrawCallback" : function(oSettings) {
							hidePagination(this, "#UserList1");
						}
					});

					$('#licCertExpiryPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						startDate : getDatePlusDays(5)
					});

					$('#endDatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#unitAsgDtFrm').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#unitAsgDtFrm').daterangepicker().on(
							'changeDate',
							function(ev) {
								var minDate = new Date(ev.date.valueOf());
								$('#unitAsgDtToPckr').daterangepicker(
										'setStartDate', minDate);
							});

					$('#unitAsgDtToPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#applctnDtFromPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#applctnDtFromPckr').daterangepicker().on(
							'changeDate',
							function(ev) {
								var minDate = new Date(ev.date.valueOf());
								$('#applctnDtToPckr').daterangepicker(
										'setStartDate', minDate);
							});

					$('#applctnDtToPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#dobPckr').daterangepicker({
					    singleDatePicker: true,
					    showDropdowns: true,
					    endDate : new Date(),
					    maxDate: new Date(),
					    minYear: 1901,
					    maxYear: parseInt(moment().format('YYYY'),10)
					}, function(start, end, label) {
						$('input[name="dob"]').val(start.format('DD/MM/YYYY'))
					});
									
					$('#yearPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "yyyy",
						endDate : new Date()
					});
					
					/*$('#dobJlsPckr').daterangepicker().on(
							'changeDate',
							function(ev) {
								var minDate = new Date(ev.date.valueOf());
								var currYear = new Date();
								var birth_year = minDate.getFullYear();
								var current_year = currYear.getFullYear();

								console.log("calculateAge  ===== birth_year" +	birth_year);
								console.log("calculateAge  ===== current_year" + current_year);
								
								var age = current_year - birth_year;
								document.getElementById("ageId").value = age;


							});
					
					$('#expdatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
					});
					
					$('#issuedatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
					});*/
					
					$('#verDobPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});
					
					 $('#verDobPckr').daterangepicker().on('changeDate', function (ev) {
						 var msj = document.getElementById("errDate");
						 
						 var dobDate = $('#dobPckr').daterangepicker('getDate');
						 var checkDate = dobDate.valueOf();
						 var verDate = ev.date.valueOf();
					     if (verDate == checkDate) {
					    	 msj.style.display = "none";
					     }else{
					    	 msj.innerHTML="Date of Birth does not match";
					    	 msj.style.display = "block";
					    	
					    	 
					    	 
					     }
					    	 
					     
					 });
					
					$('#expPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						startDate : new Date()
					});

					$('#startDatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy"
					});

					$('#apprveDtFrmPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#apprveDtFrmPckr').daterangepicker().on(
							'changeDate',
							function(ev) {
								var minDate = new Date(ev.date.valueOf());
								$('#apprveDtToPckr').daterangepicker('setStartDate',
										minDate);
							});

					$('#apprveDtToPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#dateFrmPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					}).on('changeDate', function(ev) {
						console.log('on Change!!!')
						var minDate = new Date(ev.date.valueOf());
						$('#dateToPckr').daterangepicker('setStartDate', minDate);
					});

					$('#dateToPckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#interviewDatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						startDate : new Date()
					});
					
					 $('#interviewDatePckr').daterangepicker().on('changeDate', function (ev) {
						 var msj = document.getElementById("errorDate");
						 
						 var dobDate = $('#expPckr').daterangepicker('getDate');
						 var checkDate = dobDate.valueOf();
						 var verDate = ev.date.valueOf();
					     if (verDate == checkDate) {
					    	 msj.style.display = "none";
					     }else{
					    	 msj.innerHTML="Expired Date does not match";
					    	 msj.style.display = "block";
					    	
					    	 
					    	 
					     }
					    	 
					     
					 });
					$('#srchInterviewDatePckr').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy"
					});

					$('#applctnDtFrom').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#applctnDtFrom').daterangepicker().on(
							'changeDate',
							function(ev) {
								var minDate = new Date(ev.date.valueOf());
								$('#applctnDtTo').daterangepicker('setStartDate',
										minDate);
							});

					$('#applctnDtTo').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#mruAsgDtFrm').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#mruAsgDtTo').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#paymentDateFrom').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#paymentDateTo').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#paymentDate').daterangepicker({
						autoclose : true,
						todayHighlight : true,
						format : "dd/mm/yyyy",
						endDate : new Date()
					});

					$('#tblCardLst tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								console.log(rowId);
								if (rowId != null) {
									location.href = contextPath + "/card-info/"
											+ rowId;
								}
							});

					$('#brndLstId tbody').on(
							'click',
							'tr',
							function() {
								var rowId = this.id;
								if (rowId != null) {
									location.href = contextPath
											+ "/maintenance/brand/" + rowId;
								}
							});

					var tblInbox = $('#MyInboxList').DataTable({
						"dom" : '<"top"i>t<"bottom"p><"clear">',
						"order" : [ [ 3, 'asc' ] ],
						"fnDrawCallback" : function(oSettings) {
							hidePagination(this, "#MyInboxList");
						}
					});
					jQuery('#MyInboxList tbody').on('click', 'tr a',
							function() {
								trLink = false;
							});
					jQuery("#MyInboxList tbody").on('click',
							"tr input[type='checkbox']", function() {
								trLink = false;
							});
					jQuery('#MyInboxList tbody')
							.on(
									'click',
									'tr',
									function() {
										if (trLink) {
											var data = tblInbox.row(this)
													.data();
											var refNo = data[0].indexOf('/') > -1 ? data[0]
													.replace(new RegExp('/',
															'g'), '-')
													: data[0];
											var transType = data[3];
											console
													.log("transType ::::> "
															+ transType
															+ " - refNo ::::> "
															+ refNo);
											window.location.href = contextPath
													+ '/inbox/' + transType
													+ '/' + refNo
													+ '?isClaim=true';
										}
										trLink = true;
									});

					var tblInboxHistory = $('#HistoryList').DataTable({
						"dom" : '<"top"i>t<"bottom"p><"clear">',
						"order" : [ [ 3, 'asc' ] ],
						"fnDrawCallback" : function(oSettings) {
							hidePagination(this, "#HistoryList");
						}
					});
					jQuery('#HistoryList tbody').on('click', 'tr a',
							function() {
								trLink = false;
							});
					jQuery("#HistoryList tbody").on('click',
							"tr input[type='checkbox']", function() {
								trLink = false;
							});
					jQuery('#HistoryList tbody')
							.on(
									'click',
									'tr',
									function() {
										if (trLink) {
											var data = tblInboxHistory
													.row(this).data();
											var refNo = data[0].indexOf('/') > -1 ? data[0]
													.replace(new RegExp('/',
															'g'), '-')
													: data[0];
											var transType = data[3];
											console
													.log("transType ::::> "
															+ transType
															+ " - refNo ::::> "
															+ refNo);
											window.location.href = contextPath
													+ '/inbox/' + transType
													+ '/' + refNo
													+ '?isClaim=true';
										}
										trLink = true;
									});

					var tblInboxPool = $('#PoolList').DataTable({
						"dom" : '<"top"i>t<"bottom"p><"clear">',
						"order" : [ [ 3, 'asc' ] ],
						"fnDrawCallback" : function(oSettings) {
							hidePagination(this, "#PoolList");
						}
					});
					jQuery('#PoolList tbody').on('click', 'tr a', function() {
						trLink = false;
					});
					jQuery("#PoolList tbody").on('click',
							"tr input[type='checkbox']", function() {
								trLink = false;
							});
					jQuery('#PoolList tbody')
							.on(
									'click',
									'tr',
									function() {
										if (trLink) {
											var data = tblInboxPool.row(this)
													.data();
											var refNo = data[0].indexOf('/') > -1 ? data[0]
													.replace(new RegExp('/',
															'g'), '-')
													: data[0];
											var transType = data[3];
											console
													.log("transType ::::> "
															+ transType
															+ " - refNo ::::> "
															+ refNo);
											window.location.href = contextPath
													+ '/inbox/' + transType
													+ '/' + refNo
													+ '?isClaim=true';
										}
										trLink = true;
									});

				});

var chkboxCheck = 0;
var selctedType = "-1";
var selectedIds = new Array();
if (document.getElementById("selectedRoles") != null) {
	var dataLst = document.getElementById("selectedRoles").value.split(",");
	for (var index = 0; index < dataLst.length; index++) {
		selectedIds.push(dataLst[index]);
	}
}

function onSearchAcptColl() {
	var trxNo = document.getElementById('trxNo').value;
	window.location.href = contextPath + '/collections/accept/' + trxNo;
}

function redirectURL(value) {
	console.log("redirectURL>>" + contextPath + value);
	location.href = contextPath + value;
}

function onChangeState(state) {
	var stateCod = $('#state').val();

	var url = contextPath + '/district/stateCode/' + stateCod;
	$.getJSON(url, {
		ajax : 'true'
	}, function(data) {
		// alert(data);
		var html = prop.selOptDef;
		var len = data.length;
		for (var i = 0; i < len; i++) {
			console.log(data[i].districtCode, data[i].districtDesc);
			html += '<option value="' + data[i].districtCode + '">'
					+ data[i].districtDesc + '</option>';
		}
		html += '</option>';
		$('#districtCode').html(html);
	});
}

function onChangeCategoryType() {
	var prodCatType = $('#prodCatType').val();

	var url = contextPath + '/product-list/prdCatType/' + prodCatType;
	$.getJSON(url, {
		ajax : 'true'
	}, function(data) {
		// alert(data);
		var html = prop.selOptDef;
		var len = data.length;
		for (var i = 0; i < len; i++) {
			console.log(data[i].prodCatId, data[i].prodCatDesc);
			html += '<option value="' + data[i].prodCatId + '">'
					+ data[i].prodCatDesc + '</option>';
		}
		html += '</option>';
		$('#prodCatId').html(html);
	});
}

function onChangeCollectionStatus(e) {
	var selectedIndex = e.value;
	var url = contextPath + '/collections/accept/onchangecolst/'
			+ selectedIndex;
	$.ajax({
		type : "GET",
		url : url,
		success : function(response) {
			$("#refreshId").html(response);
		}
	});
}

function submitCollectionForm() {
	alert("in");
}

function onChangeCategory() {
	var prodCatId = $('#prodCatId').val();
	var url = contextPath + '/product-list/prdCat/' + prodCatId;
	$.getJSON(url, {
		ajax : 'true'
	}, function(data) {
		var html = prop.selOptDef;
		var len = data.length;
		for (var i = 0; i < len; i++) {
			html += '<option value="' + data[i].prodId + '">'
					+ data[i].prodDesc + '</option>';
		}
		html += '</option>';
		$('#prodId').html(html);
	});
}

function onChangeGroup() {
	var groupCode = $('#userGroupCode').val();
	if (groupCode == 'BANK') {
		$('#divBranch').show();
	} else {
		$('#divBranch').hide();
	}
}

function onChangeStateJSProfile(state) {
	console.log("onChangeStateJSProfile " );
	var stateCd = $('#stateCd').val();
	console.log("onChangeStateJSProfile ===="+stateCd );

	var url = contextPath + '/jobSeekerProfile/city/' + stateCd;
	$.getJSON(url, {
		ajax : 'true'
	}, function(data) {
		var html = prop.selOptDef;
		var len = data.length;
		for (var i = 0; i < len; i++) {
			html += '<option value="' + data[i].cityCode + '">' + data[i].descEn + '</option>';
		}
		html += '</option>';
		$('#city').html(html);
	});
}

function documentPopupProject(url, title, docType, type) {
	var newURL = '';
	if (type == 'null')
		newURL = contextPath + "/" + url;
	else
		newURL = contextPath + "/" + url + "/" + type;

	console.log("newURL--- " + newURL);
	jQuery("#rptTtl").html(title);
	jQuery("#pdfUrl").attr('data', newURL);
	jQuery("#pdfUrl").attr('type', docType);
	jQuery("#embdPdfUrl").attr('src', newURL);
	jQuery("#pdfAncUrl").attr('href', newURL);
	jQuery("#rptDialog").modal("show");
}

function documentPopup(url, title, type, method) {
	portalUtil.showMainLoading(true);
	setTimeout(
			function() {
				var inputUrl = contextPath;
				if (type == 'aform' || type == 'aslip') {
					inputUrl = inputUrl + "/" + url
					if (type == 'aform') {
						jQuery("#titleText").html(prop.lblRegFrm);
					} else if (type == 'aslip') {
						jQuery("#titleText").html(prop.lblAsgSlip);
					}
				} else {
					inputUrl = inputUrl + "/" + url
				}
				inputUrl = encodeURI(inputUrl);
				$
						.ajax(
								{
									async : false,
									global : false,
									headers : {
										'X-CSRF-Token' : csrf_token
									},
									type : "GET",
									action : 'xhttp',
									url : inputUrl,
									success : function(data) {
										console.log(data)
										var doc = data;
										jQuery("#rptTtl").html(title);
										var srcObj = contextPath
												+ "/images/no-document.png";
										var mimeType = "image/png";
										var fileName = "no-document.png";
										if (doc.content != undefined) {
											srcObj = 'data:' + doc.contentType
													+ ';base64,' + doc.content;
											mimeType = doc.contentType;
											fileName = doc.filename;
										} else if (doc.reportBytes != undefined) {
											srcObj = 'data:' + doc.mimeType
													+ ';base64,'
													+ doc.reportBytes;
											mimeType = doc.mimeType;
											fileName = doc.fileName;
										}
										var object = '<object id="pdfUrl" width="100%" height="100%"'
												+ ' data="'
												+ srcObj
												+ '" '
												+ ' type="'
												+ mimeType
												+ '" >'
												+ '<embed '
												+ '  src="'
												+ srcObj
												+ '" '
												+ '  type="'
												+ mimeType
												+ '" '
												+ '  width="100%" '
												+ '  height="100%" />'
												+ '</object>';
										jQuery("#pdfUrl").replaceWith(object);
										jQuery("#rptDialog").modal("show");
									}
								}).done(function() {
							portalUtil.showMainLoading(false);
						});
			}, 1000);
}

function documentPopupQuotaAppReport(url, title, type, method) {
	var appDateFrom = $('#applctnDtFrom').val();
	var appDateTo = $('#applctnDtTo').val();
	var aksName = $('#aksName').val();
	var sector = $('#sector').val();
	var subSector = $('#subSector').val();
	var jobCategory = $('#jobCategory').val();
	var appStatus = $('#appStatus').val();
	var companyRegNo = $('#companyRegNo').val();
	var intvwDate = '';
	var apprDate = '';
	$('#srchInterviewDate').val();
	$('#apprveDtFrm').val();
	url = url + '?appDateFrom=' + appDateFrom + '&appDateTo=' + appDateTo
			+ '&aksName=' + aksName + '&sector=' + sector + '&subSector='
			+ subSector + '&jobCategory=' + jobCategory + '&appStatus='
			+ appStatus + '&companyRegNo=' + companyRegNo + '&intvwDate='
			+ intvwDate + '&apprDate=' + apprDate;
	documentPopup(url, title, type, method);

}

function onChangeEmployerStatus(e) {

	var selectedIndex = e.value;
	console.log(selectedIndex);
	if (selectedIndex == "REJECTED") {

		$("#textAreaId").hide();
	} else {
		$("#textAreaId").show();

	}

}

function documentPopupSurrenderCollReport(url, title, type, method) {
	var collectionTxnNo = $('#collectionTxnNo').val();
	var surrenderDt = $('#surrenderDt').val();
	var surrenderBy = $('#surrenderBy').val();
	url = url + '?collectionTxnNo=' + collectionTxnNo + '&surrenderDt='
			+ surrenderDt + '&surrenderBy=' + surrenderBy;
	documentPopup(url, title, type, method);
}

var appUtil = (function() {
	return {
		showMainLoading : function(isShow) {
			if ((typeof isShow) === "undefined") {
				isShow = true;
			}
			if (isShow) {
				$('#main_loading').attr('style', "display: block !important");
			} else {
				$('#main_loading').attr('style', "display: none !important");
			}
		}
	}
})();

function ajaxCall(inputType, inputURL, inputData, todo, dataType) {
	appUtil.showMainLoading(true);
	if (todo == 'addWorker' || todo == 'removeWorker') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addWorker') {
					$('#popup_modal').modal('hide');
				}
				$('#addWorker').dataTable().fnDestroy();
				$('#addWorker').dataTable({
					/*
					 * responsive: true, "dom": '<"top"i>t<"bottom"p><"clear">',
					 * "data": newData.aaData, "aoColumns": [ null, null, null,
					 * null, null ], "aoColumnDefs": [{ "bSortable": false }]
					 */

					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#addWorker");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addProduct' || todo == 'removeProduct') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addProduct') {
					$('#popup_modal').modal('hide');
				}
				$('#addProduct').dataTable().fnDestroy();
				$('#addProduct').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addSlghtr' || todo == 'removeSlghtr') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addSlghtr') {
					$('#popup_modal').modal('hide');
				}
				$('#addSlghtr').dataTable().fnDestroy();
				$('#addSlghtr').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#addSlghtr");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addLicense' || todo == 'removeLicense') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addLicense') {
					$('#popup_modal').modal('hide');
				}
				$('#addLicense').dataTable().fnDestroy();
				$('#addLicense').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addCrop' || todo == 'removeCrop') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addCrop') {
					$('#popup_modal').modal('hide');
				}
				$('#addCrop').dataTable().fnDestroy();
				$('#addCrop').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addLivestock' || todo == 'removeLivestock') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addLivestock') {
					$('#popup_modal').modal('hide');
				}
				$('#addLivestock').dataTable().fnDestroy();
				$('#addLivestock').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addNursery' || todo == 'removeNursery') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addNursery') {
					$('#popup_modal').modal('hide');
				}
				$('#addNursery').dataTable().fnDestroy();
				$('#addNursery').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});

	} else if (todo == 'addPremise' || todo == 'removePremise') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addPremise') {
					$('#popup_modal').modal('hide');
				}
				$('#addPremise').dataTable().fnDestroy();
				$('#addPremise').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});

	} else if (todo == 'addFinancial' || todo == 'removeFinancial') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addFinancial') {
					$('#popup_modal').modal('hide');
				}
				$('#tblFinancial').dataTable().fnDestroy();
				$('#tblFinancial').dataTable({
					/*
					 * responsive: true, "dom": '<"top"i>t<"bottom"p><"clear">',
					 * "data": newData.aaData, "aoColumns": [ null, null, null,
					 * null, null ], "aoColumnDefs": [{ "bSortable": false }]
					 */

					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#tblFinancial");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addActivity' || todo == 'removePlnttActivity') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addActivity') {
					$('#popup_modal').modal('hide');
				}
				$('#addActivity').dataTable().fnDestroy();
				$('#addActivity').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addProject' || todo == 'removeProject') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addProject') {
					$('#popup_modal').modal('hide');
				}
				$('#addProject').dataTable().fnDestroy();
				$('#addProject').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addSubCon' || todo == 'removeSubCon') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addSubCon') {
					$('#popup_modal').modal('hide');
				}
				$('#addSubCon').dataTable().fnDestroy();
				$('#addSubCon').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#addSubCon");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addSubConFeedmill' || todo == 'removeSubConFeedmill') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addSubConFeedmill') {
					$('#popup_modal').modal('hide');
				}
				$('#addSubConFeedmill').dataTable().fnDestroy();
				$('#addSubConFeedmill').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#addSubConFeedmill");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo == 'addSubConSlghtr' || todo == 'removeSubConSlghtr') {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			success : function(data) {
				var newData = JSON.parse(data);
				if (todo == 'addSubConSlghtr') {
					$('#popup_modal').modal('hide');
				}
				$('#addSubConSlghtr').dataTable().fnDestroy();
				$('#addSubConSlghtr').dataTable({
					"dom" : '<"top"i>t<"bottom"p><"clear">',
					"fnDrawCallback" : function(oSettings) {
						hidePagination(this, "#addSubConSlghtr");
						processRowNum(oSettings)
					},
					"aoColumnDefs" : [ {
						"bSortable" : false,
						"aTargets" : [ 0 ]
					} ],
					"aaSorting" : [ [ 1, 'asc' ] ],
					"data" : newData.aaData
				});
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else if (todo) {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData,
			dataType : dataType,
			success : function(data) {
				if (todo == 'showDialog') {
					$('#popup_content').html(data);
					$('#popup_modal').modal('show');
				} else if (todo == 'showWorker') {
					$("#mainPopupType").html("custom");
					$(".modal-title").html("Current Manpower");
					$('#popup_content').html(data);
					$('#popup_modal').modal('show');
				}
			}
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	} else {
		$.ajax({
			headers : {
				'X-CSRF-Token' : csrf_token
			},
			type : inputType,
			action : 'xhttp',
			url : inputURL,
			data : inputData
		}).done(function() {
			appUtil.showMainLoading(false);
		});
	}
}

function checkDate(field) {
	var allowBlank = true;
	var minYear = 1900;
	var maxYear = (new Date()).getFullYear();

	var errorMsg = "";

	re = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/;

	if (field.value != '') {
		if (regs = field.value.match(re)) {
			if (regs[1] < 1 || regs[1] > 31) {
				errorMsg = prop.invDay.replace("{0}", regs[1]);
			} else if (regs[2] < 1 || regs[2] > 12) {
				errorMsg = prop.invMonth.replace("{0}", regs[2]);
			} else if (regs[3] < minYear || regs[3] > maxYear) {
				errorMsg = prop.invYear.replace("{0}", regs[3]).replace("{1}",
						minYear).replace("{2}", maxYear);
			}
		} else {
			errorMsg = prop.invFmtDt.replace("{0}", field.value);
		}
	} else if (!allowBlank) {
		errorMsg = prop.invEmpDt;
	}
}

function getDatePlusDays(days) {
	var dt = new Date();
	dt.setDate(dt.getDate() + days);
	dt = new Date(dt);
	return dt;
}

function checkDateNoFuture(dt) {
	var now = new Date();
	if (dt.indexOf('/') > -1) {
		var parts = dt.split('/');
		var dt = new Date(parts[2], parts[1] - 1, parts[0]);
	}
	if (dt > now) {
		return true;
	} else {
		return false;
	}
}

function checkDateNoPast(dt) {
	var now = new Date();
	if (dt.indexOf('/') > -1) {
		var parts = dt.split('/');
		var dt = new Date(parts[2], parts[1] - 1, parts[0]);
	}
	if (dt < now) {
		return true;
	} else {
		return false;
	}
}

function validateEmailAddress(email) {
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	return emailReg.test(email);
}

function validateSearch(e) {
	var hasSrchError = false;
	if (e == "mrchnt") {
		var regNo = $("#companyReg").val();
		var cmpNam = $("#companyName").val();
		if ((regNo == null && cmpNam == null) || (regNo == '' && cmpNam == '')) {
			hasSrchError = true;
		}

	} else if (e == "prdct") {
		var prodCatType = $("#prodCatType").val();
		var pcode = $("#prodCode").val();
		var pname = $("#prodName").val();
		var brandDesc = $("#brandDesc").val();
		if ((prodCatType == null && pcode == null && pname == null && brandDesc == null)
				|| (prodCatType == '' && pcode == '' && pname == '' && brandDesc == '')) {
			hasSrchError = true;
		}

	} else if (e == "rcvr") {
		var btchType = $("#batch").val();
		var icno = $("#icNo").val();
		var name = $("#name").val();
		if ((btchType == null && icno == null && name == null)
				|| (btchType == '' && icno == '' && name == '')) {
			hasSrchError = true;
		}

	} else if (e == "trxn") {
		var cardType = $("#cardType").val();
		var mechantId = $("#mechantId").val();
		var icNo = $("#icNo").val();
		if ((cardType == null && mechantId == null && icNo == null)
				|| (cardType == '' && mechantId == '' && icNo == '')) {
			hasSrchError = true;
		}

	} else if (e == "prdctRep") {
		var startDate = $("#dateFrmPckr").val();
		var endDate = $("#dateToPckr").val();
		if (startDate == '' || startDate == null) {
			hasSrchError = true;
		}
		if (endDate == '' || endDate == null) {
			hasSrchError = true;
		}

	}
	if (hasSrchError) {
		$("#srchErrMsg").show();
		return false;
	} else {
		$("#srchErrMsg").hide();
		return true;
	}

}

function initDatatable(e) {
	$('#' + e).dataTable({
		"dom" : '<"top"i>t<"bottom"p><"clear">',
		"fnDrawCallback" : function(oSettings) {
			hidePagination(this, "#" + e);
			processRowNum(oSettings);
		}
	});

}

function handleMruChange(element) {
	var serviceResponse = "";
	var success = false;
	var apiUrl = "";
	try {
		$('#selSmartSch').val('');
	} catch (err) {

	}
	var formData = {
		'profileType' : element.value
	};
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},

		url : contextPath + '/mru/assignment/getProfileTypeList',
		data : formData,
		type : 'POST',
		async : false,
		success : function(data) {
			success = true;
			serviceResponse = data;
			var $select = $('#profileTypeMruSelectName');
			$select.empty();
			var p = $('<option/>', {
				value : ''
			}).text('- Please Select -').prop('selected', 'selected').appendTo(
					$select);
			for (var i = 0; i < serviceResponse.length; i++) {
				var o = $('<option/>', {
					value : serviceResponse[i].oprtrId
				}).text(serviceResponse[i].operatorFullName);
				o.appendTo($select);
			}
		},
		error : function() {
		}
	});
}

function handleDruChange(element) {
	var serviceResponse = "";
	var success = false;
	var apiUrl = "";
	var formData = {
		'profileType' : element.value
	};
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},

		url : contextPath + '/cru/assignment/getProfileTypeList',
		data : formData,
		type : 'POST',
		async : false,
		success : function(data) {
			success = true;
			serviceResponse = data;
			var $select = $('#profileTypeDruSelectName');
			$select.empty();
			var p = $('<option/>', {
				value : ''
			}).text('- Please Select -').prop('selected', 'selected').appendTo(
					$select);
			for (var i = 0; i < serviceResponse.length; i++) {
				var o = $('<option/>', {
					value : serviceResponse[i].oprtrId
				}).text(serviceResponse[i].operatorFullName);
				o.appendTo($select);
			}
		},
		error : function() {
		}
	});
}

function getDivisionVal(districtVal) {
	var formData = new FormData();
	formData.append("districttype", districtVal)
	appUtil.showMainLoading(true);
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},
		url : contextPath + '/register/osc/getDistByDivList',
		dataType : 'script',
		cache : false,
		contentType : false,
		processData : false,
		data : formData,
		type : 'POST'
	}).done(function() {
		appUtil.showMainLoading(false);
	});
}

function getDivisionVal(districtVal) {
	var serviceResponse = "";
	var success = false;
	var formData = {
		'divisionCode' : districtVal.value
	};
	appUtil.showMainLoading(true);
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},
		url : contextPath + '/register/osc/getDistByDivList',
		data : formData,
		type : 'POST',
		async : false,
		success : function(data) {
			success = true;
			serviceResponse = data;
			var $select = $('#districtCode');
			$select.empty();
			var p = $('<option/>', {
				value : ''
			}).text('-Please Select-').prop('selected', true);
			p.appendTo($select);
			for (var i = 0; i < serviceResponse.length; i++) {
				var o = $('<option/>', {
					value : serviceResponse[i].districtCode
				}).text(serviceResponse[i].districtDesc);
				o.appendTo($select);
			}
		},
		error : function() {
		}
	});
	appUtil.showMainLoading(false);
}

function smartSearch(element, URLname) {
	var success = false;
	var serviceResponse = "";
	var formData = {
		'searchValue' : ''
	};
	$.ajax({
		headers : {
			'X-CSRF-Token' : csrf_token
		},

		url : contextPath + URLname,
		data : formData,
		type : 'POST',
		async : false,
		success : function(data) {
			success = true;
			serviceResponse = data;
			$(element).autocomplete({
				source : serviceResponse
			});

		},
		error : function() {
		}
	});
}

function restrictSpace() {
	if (event.keyCode == 32) {
		event.returnValue = false;
		return false;
	}
}
function removeNonAlphaNumeric(t, k) {
	var eleVal = document.getElementById(t.id);
	eleVal.value = eleVal.value.replace(/[^a-zA-Z\d\s]/g, '');
}

function changeToUpperCaseNoSpace(t) {
	var eleVal = document.getElementById(t.id);
	eleVal.value = eleVal.value.toUpperCase().replace(/ /g, '');
}

/** ******* added by asif start ************* */
// creating combo box for smart search
/*(function($) {
	$
			.widget(
					"custom.combobox",
					{
						_create : function() {
							this.wrapper = $("<span>").addClass(
									"custom-combobox")
									.insertAfter(this.element);

							this.element.hide();
							this._createAutocomplete();
						},

						_createAutocomplete : function() {
							var selected = this.element.children(":selected"), value = selected
									.val() ? selected.text() : "";

							this.input = $("<input>").appendTo(this.wrapper)
									.val(value).attr("title", "").attr("id",
											"selSmartSch").attr("name",
											"companyName").addClass(
											"form-control text-uppercase")
									// .attr( "size", "50px" )
									// .attr("class", "form-control
									// text-uppercase")
									.autocomplete({
										delay : 0,
										minLength : 0,
										source : $.proxy(this, "_source")
									}).tooltip({
										tooltipClass : "ui-state-highlight"
									});

							this._on(this.input, {
								autocompleteselect : function(event, ui) {
									ui.item.option.selected = true;
									this._trigger("select", event, {
										item : ui.item.option
									});
								},

								autocompletechange : "_removeIfInvalid"
							});
						},

						_source : function(request, response) {
							var matcher = new RegExp($.ui.autocomplete
									.escapeRegex(request.term), "i");
							response(this.element.children("option").map(
									function() {
										var text = $(this).text();
										if (this.value
												&& (!request.term || matcher
														.test(text)))
											return {
												label : text,
												value : text,
												option : this
											};
									}));
						},

						_removeIfInvalid : function(event, ui) {

							// Selected an item, nothing to do
							if (ui.item) {
								return;
							}

							// Search for a match (case-insensitive)
							var value = this.input.val(), valueLowerCase = value
									.toLowerCase(), valid = false;
							this.element
									.children("option")
									.each(
											function() {
												if ($(this).text()
														.toLowerCase() === valueLowerCase) {
													this.selected = valid = true;
													return false;
												}
											});

							// Found a match, nothing to do
							if (valid) {
								return;
							}

							// Remove invalid value
							this.input.val("").attr("title",
									value + " didn't match any item").tooltip(
									"open");
							this.element.val("");
							this._delay(function() {
								this.input.tooltip("close").attr("title", "");
							}, 2500);
							this.input.autocomplete("instance").term = "";
						},

						_destroy : function() {
							this.wrapper.remove();
							this.element.show();
						}
					});
})(jQuery);*/
/*
 * //appending combo box with drop down list //so drop down would hide n smart
 * select would be visible
 */

var currrentPathName = window.location.pathname;
$(function() {

	if (currrentPathName.indexOf("/pppab-portal/mru/assignment/new") != -1) {
		try {
			$("#profileTypeMruSelectName").combobox();
		} catch (e) {
		}
	}

	if (currrentPathName.indexOf("/pppab-portal/cru/assignment/new") != -1) {
		try {
			$("#profileTypeDruSelectName").combobox();
		} catch (e) {
		}
	}

});

/** ********* added by asif end **************** */
$('#dateSynPckr').daterangepicker({
		autoclose : true,
		todayHighlight : true,
		format : "dd/mm/yyyy",
		endDate : new Date()
	});
/*$('#dob').daterangepicker({
		autoclose : true,
		todayHighlight : true,
		format : "dd/mm/yyyy",
		endDate : new Date()
	});*/

$('#manualDataEntry').on('change', function(){
	   this.value = this.checked ? 1 : 0;
	   // alert(this.value);
	}).change();

function readFile() {
	  
	  if (this.files && this.files[0]) {
	    
		  var max = 500000; // 500KB
		  var jpeg = "jpeg";
		  var jpg = "jpg";
		  var png = "png";
		  
		  var filename = document.getElementById("inp").value;
		  var res = filename.split('.').pop().toLowerCase()
		  var errFile = document.getElementById("errFile");
		  var errServer = document.getElementById("errImgServer");
		  
		  if(errServer != null){
				errServer.innerHTML="";
				errServer.style.display = "none";
			}
		  
		    if (this.files && this.files[0].size > max) {
		        alert("File too large."); // Do your thing to handle the error.
		        
		        var FR= new FileReader();
			    
			    FR.addEventListener("load", function(e) {
			    	document.getElementById("imgView").src = "/pppab-portal/images/no-document.png";
				    document.getElementById("b64").value = "/pppab-portal/images/no-document.png";
			    	document.getElementById("inp").value = "";
			    });  
			    
			    FR.readAsDataURL( this.files[0] );
		   	   
		   	}
		    else if (res != jpeg
					&& res != jpg 
					&& res != png) {
				 
				 errFile.innerHTML="Invalid file type!";
				 errFile.style.display = "block";
				    var FR= new FileReader();
				    
				    FR.addEventListener("load", function(e) {
				    	document.getElementById("imgView").src = "/pppab-portal/images/no-document.png";
					    document.getElementById("b64").value = "/pppab-portal/images/no-document.png";
				    	document.getElementById("inp").value = "";
				    }); 
				    
				    FR.readAsDataURL( this.files[0] );
		    }
		    else{
		    	var FR= new FileReader();
			    
			    FR.addEventListener("load", function(e) {
			      document.getElementById("imgView").src = e.target.result;
			      document.getElementById("b64").value = e.target.result;
			    }); 
			    
			    FR.readAsDataURL( this.files[0] );
			    
			    errFile.innerHTML="";
				errFile.style.display = "none";
		    }
	  }
	  
	}

function readFileObject() {
	
	  if (this.files && this.files[0]) {
		  var max = 500000; // 500KB
		  
			var jpeg = "jpeg";
			var jpg = "jpg";
			var png = "png";
			var pdf = "pdf";
			var filename = document.getElementById("inputFileObject").value;
			var res = filename.split('.').pop().toLowerCase()
			var errFile = document.getElementById("errFile");
			var errServer = document.getElementById("errFileServer");
			
			if(errServer != null){
				errServer.innerHTML="";
				errServer.style.display = "none";
			}
			
			if (this.files && this.files[0].size > max) {
				errFile.innerHTML="File size too large!";
				errFile.style.display = "block";
				var FR= new FileReader();
				    
				FR.addEventListener("load", function(e) {
					document.getElementById("objectView").data = "/pppab-portal/images/no-document.png";
					document.getElementById("objectStore").value = "/pppab-portal/images/no-document.png";
				}); 
				    
				FR.readAsDataURL( this.files[0] );
			}
			else if (res != jpeg
					&& res != jpg 
					&& res != png
					&& res != pdf) {
				 
				 errFile.innerHTML="Invalid file type!";
				 errFile.style.display = "block";
				    var FR= new FileReader();
				    
				    FR.addEventListener("load", function(e) {
				      document.getElementById("objectView").data = "/pppab-portal/images/no-document.png";
				      document.getElementById("objectStore").value = "/pppab-portal/images/no-document.png";
				    }); 
				    
				    FR.readAsDataURL( this.files[0] );
			
			}else{
				    	var FR= new FileReader();
				        
				    	FR.addEventListener("load", function(e) {
						      document.getElementById("objectView").data = e.target.result;
						      document.getElementById("objectStore").value = e.target.result;
				        }); 
				        
				        FR.readAsDataURL( this.files[0] );
				        
				        errFile.innerHTML="";
						errFile.style.display = "none";
				    }
	  }
	  }
	  

var imageUpload = document.getElementById('inp');

if(imageUpload){
	imageUpload.addEventListener('change', readFile, false);
}

var objectUpload = document.getElementById('inputFileObject');

if(objectUpload){
	objectUpload.addEventListener('change', readFileObject, false);
}

//Get the modal
var modal = document.getElementById('myModal');

function validateFunction(id) {
    var x = document.getElementById(id);
    var objName;
    var msj = document.getElementById("msj");
    
    var objName;
	
	if (id == 'pasportNo') {
		objName = 'Passport No.'; 
	} else if (id == 'lastName') {
		objName = 'Last Name'; 
	} else if (id == 'firstName') {
		objName = 'First Name'; 
	}
	
    if(x.value != null && x.value!=''){
    	$(modal).attr("data-validate",id);
    	msj.innerHTML="Please Confirm " + objName + " <span id='popupFieldId' hidden>"+id+"</span>";
        modal.style.display = "block";
        document.getElementById("value2").focus();
    }
}

//When Update valu
function onCheckValue(){
	var type = $(modal).attr('data-validate');
	
	var firtName = document.getElementById(type);
	var checkValue = document.getElementById("value2");
	var msg = document.getElementById("message");
	var tempId = '#'+type+'Temp';

	var objName;
	
	if (type == 'pasportNo') {
		objName = 'Passport No.'; 
	} else if (type == 'lastName') {
		objName = 'Last Name'; 
	} else if (type == 'firstName') {
		objName = 'First Name'; 
	}

	if(firtName.value.toUpperCase()==checkValue.value.toUpperCase()){
		modal.style.display = "none";
		checkValue.value=null;
		msg.style.display = "none";
		$(tempId).val(firtName.value);
	}else if(checkValue.value!=null){
		msg.style.display = "block";
		msg.innerHTML="* Wrong  " + objName + ". Please check the " + objName;
		
	}
}

function onCancelPopup(){
	var type = $(modal).attr('data-validate');
	var firtName = document.getElementById(type);
	var checkValue = document.getElementById("value2");
	var msg = document.getElementById("message");
	var popupId = $('#popupFieldId').text();
	var fieldTemp = $('#'+popupId+'Temp').val();
	if (fieldTemp === undefined) {
		fieldTemp = '';
	}
	firtName.value = fieldTemp;
	checkValue.value=null;
	msg.style.display = "none";
	modal.style.display = "none";
}

//Get the modal
var radioMmodal = document.getElementById('radioModal');


function genderValidate(id) {
    var x = document.getElementById(id);
    
    $(radioMmodal).attr("data-validate",id);
    radioMmodal.style.display = "block";
    msjRadio.innerHTML="Please Confirm Gender";
    document.getElementById("currentMale").focus();
   
}

function onCheckGenValue(){
	var type = $(radioMmodal).attr('data-validate');
	var firtName = document.getElementById(type);
	var msgGen = document.getElementById("messageGen");
	var checkValue = $('input[name="genderPopup"]:checked').val();
    var msjRadio = document.getElementById("msjRadio");
	
	if (firtName.value == "F") {
		//checkValue = document.getElementById("female2");
		if(firtName.value==checkValue){
			$('input[name="genderPopup"]').removeProp('checked');
			radioMmodal.style.display = "none";
			msgGen.innerHTML="";
		}else {
			msgGen.innerHTML="* Wrong Gender. Please check the Gender";
			msgGen.style.display = "block";
			$('input[name="genderPopup"]').removeProp('checked');

		}
	}else{
		//checkValue = document.getElementById("male2");
		if(firtName.value==checkValue){
			radioMmodal.style.display = "none";
			msgGen.innerHTML="";
			$('input[name="genderPopup"]').removeProp('checked');
		}else {
			msgGen.innerHTML="* Wrong Gender. Please check the Gender";
			msgGen.style.display = "block";
			$('input[name="genderPopup"]').removeProp('checked');

		}
	}
	
}

function onCancelGenPopup(){
	var type = $(radioMmodal).attr('data-validate');
	var firtName = document.getElementById(type);
	radioMmodal.style.display = "none";
	messageGen.innerHTML="";
	
}


//Get the modal
var countryMmodal = document.getElementById('countryMmodal');
var labelCountryName;

function countryValidate(id) {
    var e = document.getElementById(id);
    var strUser = e.options[e.selectedIndex].value;
    
    var msjCon = document.getElementById("msjCon");
    
    var labelName;
    
    if(id == 'nationality'){
    	labelCountryName = 'Nationality';
    }
    else{
    	labelCountryName = 'Country Issue';
    }
	
    $(countryMmodal).attr("data-validate",strUser);
    
    if(strUser!=""){
    	msjCon.innerHTML="Please Confirm " + labelCountryName + " <span id='popupId' hidden>"+id+"</span>";;
    	countryMmodal.style.display = "block";
    	document.getElementById("selectModal").focus();
    	
    }
}

function onCheckConValue(){
	var type = $(countryMmodal).attr('data-validate');
	//var firtName = document.getElementById(type);
	var messageCon = document.getElementById("messageCon");
	var checkValue = $('option[name="countryPopup"]:checked').val();
	
	if(type==checkValue){
		countryMmodal.style.display = "none";
		messageCon.innerHTML= "";
		$('option[name="countryPopup"]').removeAttr('selected');
	}else{
		messageCon.style.display = "block";
		messageCon.innerHTML="* Wrong " + labelCountryName + ". Please check the " + labelCountryName;
		$('option[name="countryPopup"]').removeAttr('selected');
	}
}

function onCancelConPopup(){
	countryMmodal.style.display = "none";
	messageCon.innerHTML= "";

}

/*function submitJLSProfileDQ(passportNo){
	console.log("submitJLSProfileDQ>>>>>" + passportNo)
	window.location.href  = contextPath + '/jobSeekerProfile/submitJLSProfileDQ?passportNo=' + passportNo;

}*/


var currentIndex = 0,
items = $('.container_image div'),
itemAmt = items.length;

function cycleItems() {
var item = $('.container_image div').eq(currentIndex);
items.hide();
item.css('display','inline-block');

function nextOn(){
	currentIndex += 1;
	if (currentIndex > itemAmt - 1) {
	  currentIndex = 0;
	}
	cycleItems();
	}

	function prevOn() {
	currentIndex -= 1;
	if (currentIndex < 0) {
	  currentIndex = itemAmt - 1;
	}
	cycleItems();
	}
}

function isValidDate(str) {
    var parts = str.split('/');
    if (parts.length < 3)
        return false;
    else {
        var day = parseInt(parts[0]);
        var month = parseInt(parts[1]);
        var year = parseInt(parts[2]);
        if (isNaN(day) || isNaN(month) || isNaN(year)) {
            return false;
        }
        if (day < 1 || year < 1 || year < 999 || year > 9999)
            return false;
        if(month>12||month<1)
            return false;
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && day > 31)
            return false;
        if ((month == 4 || month == 6 || month == 9 || month == 11 ) && day > 30)
            return false;
        if (month == 2) {
            if (((year % 4) == 0 && (year % 100) != 0) || ((year % 400) == 0 && (year % 100) == 0)) {
                if (day > 29)
                    return false;
            } else {
                if (day > 28)
                    return false;
            }      
        }
        return true;
    }
}

function validateNamePattern() 
{
            var charCode = event.keyCode;

            if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || 
            	charCode == 8 || charCode == 32 || charCode == 50 || (event.shiftKey == true && charCode == 64))

                return true;
            else
                return false;
}

function validatePassportPattern() 
{
            var charCode = event.keyCode;

            if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123) || 
            	charCode == 8 || (charCode > 47 && charCode < 58))

                return true;
            else
                return false;
}
22/08/2019
function getAge(dateString) {
	var dateForm = dateString.substring(3,5) + '/' + dateString.substring(0,2) + '/' + (dateString.substring(6,10) - 1);
	var birth = new Date(dateForm);
	var check = new Date();

	var milliDay = 1000 * 60 * 60 * 24; // a day in milliseconds;


	var ageInDays = (check - birth) / milliDay;

	var ageInYears =  Math.floor(ageInDays / 365 );
	var age =  ageInDays / 365 ;
	
 /*   var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }*/
    return ageInYears;
}

/*****IMAGE SLIDER ******/

$('.slider span.next').click(function() {
	  $current = $(this).siblings('img.active');
	  $next = $current.next('img');
	  if ($next.length != 0) {
	    $current.removeClass('active');
	    $next.addClass('active');
	  }
	})
	$('.slider span.prev').click(function() {
	  $current = $(this).siblings('img.active');
	  $prev = $current.prev('img');
	  if ($prev.length != 0) {
	    $current.removeClass('active');
	    $prev.addClass('active');
	  }
	})
	
$.fn.stars = function() {
    return this.each(function(i,e){$(e).html($('<span/>').width($(e).text()*16));});
};

$('.stars').stars();
