function initComponentsReport() {
    $('#date-report-for-month').hide();
    $("#input-dtpckr-start-report").datepicker({
        format: "dd/mm/yyyy",
        language: "pt-BR"
    }), $("#input-dtpckr-end-report").datepicker({
        format: "dd/mm/yyyy",
        language: "pt-BR"
    }), $("#select-servico").find("select").prop("disabled", !0), $("#select-professional").find("select").prop("disabled", !0), $("#select-report-type").change(function() {
        onSelectReportChaged()
    }), $("#select-service-type").change(function() {
        onSelectReportServiceTypeChanged()
    }), ajaxCall("/siac/getActiveServices", null, function(a) {
        var b = $("#select-servico").find("select");
        $.each(a, function() {
            b.append('<option value="' + this.id + '">' + this.name + "</option>")
        })
    })
}

function onSelectReportChaged() {
    var a = $("#select-report-type").val();
    "by-type" === a ? ($("#form-report").attr("action", "relatorio/servico"), $("#select-servico").show().find("select").prop("disabled", !1), $("#select-professional").show()) : "general" === a ? ($("#form-report").attr("action", "relatorio/geral"), $("#select-servico").hide().find("select").prop("disabled", !0), $("#select-professional").hide()) : ($("#select-servico").show().find("select").prop("disabled", !0), $("#select-professional").show())
}

function onSelectReportServiceTypeChanged() {
	showSnack("Consultando profissionais, aguarde...");
    var a = $("#select-servico").find("select"),
        b = $("#select-professional").find("select");
    "option-deafult" === a.val() ? b.prop("disabled", !0) : ajaxCall("/siac/getProfessionalsByService", {
        serviceId: a.val()
    }, function(a) {
        b.prop("disabled", !1), b.find("option").remove(), b.append('<option value="option-default">Escolha o profissional</option>'), $.each(a, function() {
            b.append('<option value="' + this.id + '">' + this.name + "</option>")
        })
    })
}

function onActionClick() {
    $(".link-action").click(function() {
        $(".line-service").remove(), $(".calendar").css("display", "none"), $("#my-calendar").css("display", "none"), $("#alert-schedules").css("display", "none"), $("#generate-report").css("display", "none"), $("#set-professional").css("display", "none"), $("#add-service").css("display", "none"), $(".action").removeClass("active"), $(".service").removeClass("active"), $(this).parent().addClass("active"), 0 == $(this).attr("id") ? $("#generate-report").css("display", "block") : 1 == $(this).attr("id") ? $("#set-professional").css("display", "block") : 2 == $(this).attr("id") && ($("#add-service").css("display", "block"), ajaxCall("/siac/getServices", null, function(a) {
            fillTableServices(a)
        }))
    })
}

function onOptionReportClick() {
    $(".type-report").change(function() {
        "Geral" == $(".type-report option:selected").text() ? $(".select-service").attr("disabled", "disabled") : "Por serviço" == $(".type-report option:selected").text() && ($(".service-option").remove(), ajaxCall("/siac/getServices", function(a) {
            var b, c, d;
            a.sort(function(a, b) {
                return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
            }), $.each(a, function(a, e) {
                $.each(e, function(a, e) {
                    "name" == a && (b = e), "active" == a && (c = e), "id" == a && (d = e)
                }), $(".select-service").append("<option class='service-option' value='" + d + "'>" + b + "</option>")
            }), $(".select-service").removeAttr("disabled")
        }))
    })
}

function onServiceActiveButtonClick() {
    $(document).on("click", ".service-active", function() {
        var a = $(this).attr("id");
        ajaxCall("/siac/setInactiveService", {
            id: a
        }, function(a) {
            fillTableServices(a), alertMessage("Serviço desativado com sucesso", null, ALERT_SUCCESS)
        }, function() {
            alertMessage("Não foi possível realizar a operação", null, ALERT_ERROR)
        })
    })
}

function onServiceInactiveButtonClick() {
    $(document).on("click", ".service-inactive", function() {
        var a = $(this).attr("id");
        ajaxCall("/siac/setActiveService", {
            id: a
        }, function(a) {
            fillTableServices(a), alertMessage("Serviço ativado com sucesso", null, ALERT_SUCCESS)
        }, function() {
            alertMessage("Não foi possível realizar a operação", null, ALERT_ERROR)
        })
    })
}

function onServiceEditButtonClick() {
    $(document).on("click", ".edit-service", function() {
        $("#save-edit-service").val(""), $("#name-edit-service").val($(this).attr("data-name")), $(".save-edit-service").removeAttr("data-id"), $(".save-edit-service").attr("data-id", $(this).attr("data-id"))
    })
}

function onServiceEditSaveButtonClick() {
    $(".save-edit-service").on("click", function() {
        var b = (new Object, $("#name-edit-service").val()),
            c = $(this).attr("data-id");
        ajaxCall("/siac/editService", {
            id: c,
            name: b
        }, function(a) {
            fillTableServices(a), alertMessage("Serviço editado com sucesso", null, ALERT_SUCCESS)
        }, function() {
            alertMessage("Não foi possível editar o serviço", null, ALERT_ERROR)
        }), $("#modal-edit-service").modal("hide")
    })
}

function onServiceAddButtonClick() {
    $(".save-register-service").on("click", function() {
        var a = $("#name-register-service").val();
        $("#name-register-service").val(""), ajaxCall("/siac/registerService", {
            name: a
        }, function(a) {
            fillTableServices(a), alertMessage("Serviço cadastrado com sucesso", null, ALERT_SUCCESS)
        }, function() {
            alertMessage("Não foi possível cadastrar o serviço", null, ALERT_ERROR)
        }), $("#modal-add-service").modal("hide")
    })
}

function fillTableServices(a) {
	showSnack("Consultando serviços");
    var b, c, d;
    $(".line-service").remove(), a.sort(function(a, b) {
        return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
    }), $.each(a, function(a, e) {
        if ($.each(e, function(a, e) {
                "name" == a && (b = e), "active" == a && (c = e), "id" == a && (d = e)
            }), c) var f = $("<tr class='line-service success'></tr>");
        else var f = $("<tr class='line-service active'></tr>");
        f.append("<td>" + b + "</td>"), c ? f.append("<td>Ativo</td>") : f.append("<td>Inativo</td>"), f.append("<td><button class='btn btn-sm btn-warning edit-service' role='button' data-toggle='modal' data-target='#modal-edit-service' data-name='" + b + "' data-id='" + d + "'>Editar</button></td>"), c ? f.append("<td><button class='btn btn-danger btn-sm service-active' id='" + d + "'>Desativar</button></td>") : f.append("<td><button class='btn btn-primary btn-sm service-inactive' id='" + d + "'>Ativar</button></td>"), $("#table-services").append(f)
    })
}

function onFieldSearchProfessionalChange() {
    $("#button-search-professional").on("click", function() {
        var b = ($("#field-search-professional").val(), $("#field-search-professional").val()),
            c = {
                name: b
            };
        ajaxCall("/siac/getUserByName?name", c, function(a) {
            fillTableAddProfessional(a)
        })
    }), $("#field-search-professional").keydown(function() {
        var b = ($("#field-search-professional").val(), $("#field-search-professional").val()),
            c = {
                name: b
            };
        ajaxCall("/siac/getUserByName", c, function(a) {
            fillTableAddProfessional(a)
        })
    })
}

function fillTableAddProfessional(a) {
    var b, c;
    $(".tr-professional").remove();
    ajaxCall("/siac/getServices", null, function(d) {
        var e, f, g, h;
        d.sort(function(a, b) {
            return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
        }), a.sort(function(a, b) {
            return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
        }), $.each(a, function(a, i) {
            $.each(i, function(a, d) {
                "name" == a && (b = d), "email" == a && (c = d), "cpf" == a && (h = d)
            });
            var j = $("<tr class='tr-professional'></tr>");
            j.append("<td>" + b + "</td>");
            var k = $("<select class='form-control' id='service-" + h + "'><option value='-1'>Selecione o serviço</option></select>");
            $.each(d, function(a, b) {
                $.each(b, function(a, b) {
                    "name" == a && (e = b), "active" == a && (f = b), "id" == a && (g = b)
                }), f && k.append("<option value='" + g + "'>" + e + "</option>")
            }), j.append("<td>" + c + "</td>").append(k), j.append("<td><button class='btn btn-sm btn-primary tr-professional btn-add-professional' data-cpf='" + h + "' role='button'>Cadastrar profissional</button></td>"), $("#table-add-professional").append(j)
        })
    }, function() {
        alertMessage("Não foi possível buscar os serviços", null, ALERT_ERROR)
    })
}

function addProfessional() {
    $(document).on("click", ".btn-add-professional", function() {
        var a = $(this).attr("data-cpf"),
            b = $("#service-" + a).val();
        if (b == -1) alert("Escolha um serviço");
        else {
            var c = new Object;
            c.cpf = a, c.idService = b, ajaxCall("/siac/saveProfessional", c, function(a) {
                a.code == RESPONSE_SUCCESS ? alertMessage(a.message, null, ALERT_SUCCESS) : alertMessage(a.message, null, ALERT_ERROR)
            }, function() {
                alertMessage("Não foi possível cadastrar o profissional", null, ALERT_ERROR)
            })
        }
    }), $(document).on("click", ".link-add-professional", function() {
        $(".tr-professional").remove()
    })
}

function getProfessionals() {
    $(document).on("click", ".manage-professional", function() {
    	showSnack("Carregando Profissionais");
        var a, b, c, d, n;
        $(".tr-professional-added").remove(), ajaxCall("/siac/getProfessionals", null, function(e) {
        	console.log(e);
            e.sort(function(a, b) {
                return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
            }), $.each(e, function(e, f) {
                $.each(f, function(e, f) {
                    "name" == e && (a = f), "email" == e && (b = f), "socialService" == e && (d = f, $.each(d, function(a, b) {
                        "name" == a && (c = b), "active" == a && (n = b)
                    }))
                });
                var g = $("<tr class='tr-professional-added'></tr>");
                g.append("<td>" + a + "</td>"), g.append("<td>" + b + "</td>"), g.append("<td>"+ n +"</td>"), g.append("<td>" + (c==true? "Ativo":"Inativo") + "</td>"), $("#table-professional").append(g)
            })
        })
    }), $(document).on("click", ".link-professional-registered", function() {
    	showSnack("Carregando Profissionais");
        var a, b, c, d, n;
        $(".tr-professional-added").remove(), ajaxCall("/siac/getProfessionals", null, function(e) {
        	console.log(e);
            e.sort(function(a, b) {
                return a.name < b.name ? -1 : a.name > b.name ? 1 : 0
            }), $.each(e, function(e, f) {
                $.each(f, function(e, f) {
                    "name" == e && (a = f), "email" == e && (b = f), "socialService" == e && (d = f, $.each(d, function(a, b) {
                        "name" == a && (c = b), "active" == a && (n = b)
                    }))
                });
                var g = $("<tr class='tr-professional-added'></tr>");
                g.append("<td>" + a + "</td>"), g.append("<td>" + b + "</td>"), g.append("<td>"+ n +"</td>"), g.append("<td>" + (c==true? "Ativo":"Inativo") + "</td>"), $("#table-professional").append(g)
            })
        })
    })
}

function onGenerateReportButtonClicked() {
    var a = $("#select-report-type").val(),
        b = $("#select-service-type").val(),
        c = $("#select-professional-id").val(),
        d = $("#input-dtpckr-start-report").val(),
        e = $("#input-dtpckr-end-report").val();
    var y = $('#input-year-report').val();
    var m = $('select[name=month]').val();
    return ("" == d || "" == e) && (y == "" || m == 0) ? (alertMessage("Selecione as datas de início e fim, ou mês e ano, corretamente", 3e3, ALERT_ERROR), !1) : compareDate(stringToDate(d), stringToDate(e)) > 0 ? (alertMessage("A data de início não pode ser depois da data final", 3e3, ALERT_ERROR), !1) : "general" === a || ("by-type" !== a ? (alertMessage("Selecione o tipo de relatório", 3e3, ALERT_ERROR), !1) : "option-deafult" === b ? (alertMessage("Selecione o serviço", 3e3, ALERT_ERROR), !1) : "option-default" !== c || (alertMessage("Selecione o profissional", 3e3, ALERT_ERROR), !1))
}
$("document").ready(function() {
    onActionClick(), onOptionReportClick(), onServiceActiveButtonClick(), onServiceInactiveButtonClick(), onServiceEditButtonClick(), onServiceEditSaveButtonClick(), onServiceAddButtonClick(), onFieldSearchProfessionalChange(), initComponentsReport(), addProfessional(), getProfessionals()
});

function showSnack(text) {
    var x = document.getElementById("snackbar")
    x.textContent= text;
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
