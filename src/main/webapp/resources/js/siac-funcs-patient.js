function initCalendarPatient(a){

    $("#calendar-patient").fullCalendar(
        {
            header:{left:"prev",center:"title",right:"next"},
            businessHours:!0,
            editable:!1,
            dayClick:null,
            eventClick:null,
            dayClick:function(a,b,c,d){
                $(".modal-title").html(a.format("DD/MM/YYYY"))
            },
            events:a,
            eventClick:function(a,b,c){
                chargeScheduleDay(a.id),
                $("#my-calendar-title").html("Meu Calendário"),
                $("#modal-event").modal("show")
            },
            eventMouseover:function(a,b,c){
                $("#aaaa").tooltip()
            }
        }
    )
}

function chargeScheduleDay(a){
    $(".tr-horary").remove();
    var b={id:a};
    ajaxCall("/siac/getConsultationById",b,function(b){
        var c,d,e,f;
        if($.each(b,function(a,b){"hour"==a&&(c=b),"state"==a&&(d=b),"isRatingNull"==a&&(e=b),"idReserve"==a&&(f=b)}),"Agendado"==d){
            var g=$("<tr class='tr-horary info'> </tr>");
            g.append($("<td>"+c+"</td>")),
            g.append($("<td>"+d+"</td>")),
            g.append($("<td><button type='button' class='btn btn-danger btn-sm' data-id='"+a+"' id='cancel-consultation'>Cancelar</button></td>")),
            $("#body-table-event").append(g)
        }else if("Cancelado"==d){
            var g=$("<tr class='tr-horary danger'> </tr>");
            g.append($("<td>"+c+"</td>")),
            g.append($("<td>"+d+"</td>")),
            g.append($("<td> - </td>")),
            $("#body-table-event").append(g)
        }else if("Realizado"==d){
            var g=$("<tr class='tr-horary active'> </tr>");
            g.append($("<td>"+c+"</td>")),
            g.append($("<td>"+d+"</td>")),
            e?g.append($("<td><button type='button' class='btn btn-success btn-sm rating-button' id='"+a+"' data-target='#modal-rating' data-toggle='modal'>Avaliar</button></td>")):g.append($("<td><button type='button' class='btn btn-success show-rating' data-id='"+a+"'>Ver avaliação</button></td>")),
            $("#body-table-event").append(g)
        }else if("Reservado"==d){
            var g=$("<tr class='tr-horary warning'> </tr>");
            g.append("<td>"+c+" </td> <td>"+d+"</td>"),
            g.append($("<td><button type='button' class='btn btn-danger btn-sm cancel-reserve' data-id-reserve='"+f+"' id='cancel-reserve'>Cancelar</button></td>")),$("#body-table-event").append(g)
        }else if("Disponivel"==d){
            var g=$("<tr class='tr-horary success'></tr>");
            g.append($("<td>"+c+" </td>")),
            g.append($("<td>Disponível</td>")),
            g.append($("<td><button type='button' class='btn btn-primary btn-sm' data-id='"+a+"' id='schedule-consultation'>Agendar</button></td>")),
            $("#body-table-event").append(g)
        }else if("Ocupado"==d){
            var g=$("<tr class='tr-horary warning'> </tr>");
            g.append($("<td>"+c+"</td>")),
            g.append($("<td>"+d+"</td>")),
            g.append($("<td><button type='button' class='btn btn-info btn-sm reserve-button' data-id='"+a+"' id='reserve-consultation'>Reservar</button></td>")),
            $("#body-table-event").append(g)
        }
    })
}

function chargeEvents(){
    ajaxCall("/siac/getMyConsultations",null,function(a){
        initCalendarPatient(a)
    })
}

function chargeServices(){
    ajaxCall("/siac/getActiveServices",null,function(a){
        var b,c,d;
        a.sort(function(a,b){
            return a.name<b.name?-1:a.name>b.name?1:0
        }),
        $.each(a,function(a,e){
            $.each(e,function(a,e){"name"==a&&(b=e),"active"==a&&(c=e),"id"==a&&(d=e)}),
            $("#ul-services").append("<li class='service'><a class='link-service social-service service-item' id='"+d+"' data-name='"+b+"'>"+b+"</a></li>")
        })
    })
}

function myConsultations(){
    $("#my-consults").click(function(){
        $(".content-calendar").css("display","none"),
        $("#div-my-groups").css("display","none"),
        $("#my-consultations").css("display","block"),
        $(".tr-my-consultations").remove(),
        ajaxCall("/siac/getMyConsultations",null,function(a){
            var b,c,d,e,f,g,h;
            $.each(a,function(a,i){
                $.each(i,function(a,i){"start"==a&&(c=i),"title"==a&&(b=i),"hour"==a&&(d=i),"state"==a&&(e=i),"id"==a&&(f=i),"isRatingNull"==a&&(g=i),"idReserve"==a&&(h=i)});
                var j=$("<tr class='tr-my-consultations'></tr>");
                j.append($("<td>"+b+"</td>")),
                j.append($("<td>"+c+"</td>")),
                j.append($("<td>"+d+"</td>")),
                j.append($("<td>"+e+"</td>")),
                "Realizado"!=e||g?"Realizado"==e&&j.append($("<td><button type='button' class='btn btn-warning rating-button' id='"+f+"' data-target='#modal-rating' data-toggle='modal'>Avaliar</button></td>")):j.append($("<td><button type='button' class='btn btn-success show-rating' data-id='"+f+"'>Ver avaliação</button></td>")),"Agendado"==e?(j.append($("<td><button type='button' class='btn btn-warning' disabled='disabled'> Avaliar </button> </td>")),
                j.append($("<td><button type='button' class='btn btn-danger' id='cancel-consultation' data-id='"+f+"'> Cancelar </button> </td>"))):"Realizado"==e?j.append($("<td><button disabled='disabled' type='button' class='btn btn-danger'> Cancelar </button> </td>")):"Reservado"==e&&(j.append($("<td><button type='button' class='btn btn-warning' disabled='disabled'> Avaliar </button> </td>")),
                j.append($("<td><button type='button' class='btn btn-danger cancel-reserve' data-id-reserve='"+h+"'> Cancelar </button> </td>"))),
                $("#my-consultations-table").append(j)
            })
        })
    })
}

function myCalendar(){
    $("#my-calend").click(function(){
        $(".content-calendar").css("display","block"),
        $("#div-my-groups").css("display","none"),
        $("#my-consultations").css("display","none"),
        $(".calendar").remove(),
        $(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>")),
        
        chargeEvents()
    })
}

function addRating(){
    $("#rating-grade").barrating({theme:"bootstrap-stars"}),
    $(document).on("click",".rating-button",function(){
        $("#input-rating-id").val($(this).attr("id"))
    }),
    $("#save-rating").click(function(){
        var a=new Object;
        a["rating.rating"]=$("#rating-grade option:selected").val(),
        a["rating.comment"]=$("#rating-comment").val(),
        a.id=$("#input-rating-id").val(),
        console.log(JSON.stringify(a)),
        
        ajaxCall("/siac/updateConsultationRating",a,function(a){
            a.code==RESPONSE_SUCCESS?alertMessage(a.message,null,ALERT_SUCCESS):alertMessage(a.message,null,ALERT_ERROR)
        },function(){
            alertMessage("ERRO",null,ALERT_ERROR)
        }),
            
        $("#modal-rating").modal("hide"),
        $("#modal-event").modal("hide")
    })
}

function onServiceClick(){
    $(document).on("click",".link-service",function(){
        if($(".service").removeClass("active"),$(this).parent().addClass("active"),$(this).hasClass("social-service")){
            $("#my-consultations").css("display","none");
            var a=$(this).attr("id");
            
            $("#my-calendar-title").html("Calendário "+$(this).attr("data-name")),
            
            ajaxCall("/siac/getConsultationBySocialService",{id:a},function(a){
                $(".content-calendar").css("display","block"),
                $(".calendar").remove(),
                $(".content-calendar").append($("<div class='calendar' id='calendar-patient'></div>")),
                initCalendarPatient(a)
            })
        }else
            $("#my-calendar-title").html("Meu Calendário")
    })
}

function scheduleConsultation(){
    $(document).on("click","#schedule-consultation",function(){
        var a=$(this).attr("data-id");
        ajaxCall("/siac/scheduleConsultation",{id:a},function(b){
            b.code==RESPONSE_SUCCESS?(changeEvent(a,"#4682B4"),alertMessage(b.message,null,ALERT_SUCCESS)):alertMessage(b.message,null,ALERT_ERROR)
        },function(){
            alertMessage("Ops, Não foi possível agendar essa consulta!",null,ALERT_ERROR)}),
            $("#modal-event").modal("hide"),chargeEvents()
        })
}

function showRating(){
    $(document).on("click",".show-rating",function(){
        $("#content-rating").remove(),
        $(".modal-body-rating").append($("<div id='content-rating'></div>"));
        var b,c,a=$(this).attr("data-id"),d={id:a};
        
        ajaxCall("/siac/showRating",d,function(a){
            b=a.comment,
            c=a.rating,
            $("#content-rating").append("<h4><strong>Comentário:</strong></h4>"),
            $("#content-rating").append(b),
            $("#content-rating").append("<h4><strong>Nota</strong>: "+c+"</h4>")
        }),
            
        $("#modal-read-rating").modal("show")
    })
}

function cancelConsultation(){
    $(document).on("click","#cancel-consultation",function(){
        var a=$(this).attr("data-id");
        ajaxCallNoJSON("/siac/cancelConsultationPatient",{id:a},function(){
            alertMessage("Consulta cancelada com sucesso",null,ALERT_SUCCESS),
            
            "Meu Calendário"===$("#my-calendar-title").text()?$("#calendar-patient").fullCalendar("removeEvents",a):changeEvent(a,"#32CD32")
        },function(){
            alertMessage("Desculpe, a operação falhou",5e3,ALERT_ERROR)
        }),
            
        $("#modal-event").modal("hide")
    })
}

function reserveConsultation(){
    $(document).on("click",".reserve-button",function(){
        var a=$(this).attr("data-id");
        ajaxCallNoJSON("/siac/reserveConsultation",{id:a},function(){
            changeEvent(a,"#D9D919"),
            alertMessage("Consulta reservada com sucesso",5e3,ALERT_SUCCESS)
        },function(){
            alertMessage("Desculpe, a operação falhou",5e3,ALERT_ERROR)
        }),
            
        $("#modal-event").modal("hide")
    })
}

function cancelReserve(){
    $(document).on("click",".cancel-reserve",function(){
        var a=$(this).attr("data-id-reserve");
        ajaxCallNoJSON("/siac/cancelReserve",{id:a},function(){
            "Meu Calendário"===$("#my-calendar-title").text()?$("#calendar-patient").fullCalendar("removeEvents",id):changeEvent(id,"#FF7F00"),alertMessage("Reserva cancelada com sucesso",5e3,ALERT_SUCCESS)
        },function(){
            alertMessage("Desculpe, a operação falhou",5e3,ALERT_ERROR)
        }),
            
        $("#modal-event").modal("hide")
    })
}

function changeEvent(a,b){
    var c=$("#calendar-patient").fullCalendar("clientEvents",a)[0];
    console.log(c),
    c.color=b,
    $("#calendar-patient").fullCalendar("updateEvent",c)
}



function myGroups(){
    $("#my-groups").click(function(){
        $(".content-calendar").css("display","none");
        $("#my-consultations").css("display","none");
        $("#div-my-groups").css("display","block");
        
       
        ajaxCall("/siac/getMyGroups",null,function(json){
        	var linha = "";
        	var m = JSON.parse(json.message);
            var count = Object.keys(json.message).length;
            
           for (var i = 0; i < count; i++){
        	 if(typeof m[i] !== 'undefined'){
        		 var msg = m[i].title;
        		
        		  linha += "<tr><td>"+msg+"</td><td><button class='btn btn-danger sairGrupo ' onClick = 'leaveMyGroups("+ m[i].id +")'>Sair</button></td></tr>";
        		  
        		   
        	   }
           }
           $('#corpo-mygrupo').html(linha);
          

        });
        
        ajaxCall("/siac/getGroupsFree",null,function(json){
        	var linha_free = "";
        	var mj = JSON.parse(json.message);
            var count = Object.keys(json.message).length;
            var countNumber = mj[1].patients.length;
           // console.log(mj[0]);
            
            
           for (var i = 0; i < count; i++){
        	 if(typeof mj[i] !== 'undefined'){
        		 var msg_f = mj[i].title;
        		
        		 linha_free += "<tr><td>"+msg_f+"</td><td><button class='btn btn-danger sairGrupo ' >Sair</button></td></tr>";
        		  	   
        	   }
           }
           $('#corpo-grupo-disponivel').html(linha_free);

        });
        
        
    });
}

function leaveMyGroups(id_group){
	
	var cpf = window.localStorage['userCPF'];
    
	console.log("ID_GROUP; "+ id_group+" cpf "+cpf);
	
	
	var id_patient="";
	var json = '{"id":'+id_group+',"patients":[{"id":'+id_patient+'}]}';
	console.log(json);
}

$("document").ready(function(){
	chargeEvents(),
    chargeServices(),
    myConsultations(),
    myCalendar(),
    addRating(),
    onServiceClick(),
    scheduleConsultation(),
    showRating(),
    cancelConsultation(),
    reserveConsultation(),
    cancelReserve(),
    myGroups(),
    leaveMyGroups()
    
});

